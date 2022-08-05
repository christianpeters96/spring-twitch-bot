(function () {

    function controller($scope, $interval, MusicBot) {
        let ctrl = this;

        ctrl.state = "pause";
        ctrl.audio = null;
        ctrl.timeInfo = {
            max: "0:00",
            current: "0:00"
        };
        ctrl.pbVolume = 0;
        ctrl.pbMusic = 0;

        ctrl.expandedPlaylist = true;
        ctrl.expandedRequests = true;

        ctrl.ready = false;
        ctrl.initialized = false;
        ctrl.songRequestEnabled = true;
        ctrl.playlistPosition = 0;
        ctrl.currentSong = {
            index: 0,
            playlist: "playlist",
            musicData: {},
            ytData: {}
        };

        let iv_autoPlay = null;
        let iv_waitForData = null;
        let iv_waitForDataTemp = null;
        let iv_updateProgressBar = null;
        ctrl.$onInit = function () {
            let temp = localStorage.getItem("music-player-enable-song-request");
            if (temp != null && /^[01]$/.test(temp)) {
                ctrl.songRequestEnabled = parseInt(temp) == 1;
            }

            if (ctrl.audio == null) {
                ctrl.audio = new Audio();
                let savedVolume = localStorage.getItem('music-player-volume'), loadedVolume = false;
                if (savedVolume !== null) {
                    if (!isNaN(Number(savedVolume))) {
                        let volume = Number(savedVolume);
                        if (volume < 0) volume = 0;
                        if (volume > 1) volume = 1;
                        loadedVolume = true;
                        ctrl.audio.volume = volume;
                    }
                }
                if (!loadedVolume) ctrl.audio.volume = 0.1;
                ctrl.pbVolume = Number((100 * ctrl.audio.volume).toFixed(1));
            }

            ctrl.audio.addEventListener('ended', ctrl.songEnded);

            iv_updateProgressBar = $interval(function () {
                if (!isNaN(ctrl.audio.duration) && !isNaN(ctrl.audio.currentTime)) {
                    ctrl.pbMusic = (ctrl.audio.currentTime / ctrl.audio.duration) * 100;
                    ctrl.timeInfo = {
                        max: secondsToMinuteString(ctrl.audio.duration),
                        current: secondsToMinuteString(ctrl.audio.currentTime)
                    }
                }
                if ($scope.songChanged === true) {
                    $scope.songChanged = false;
                    ctrl.state = "play";
                    ctrl.play(true);
                }
            });

            iv_autoPlay = $interval(function () {
                if (ctrl.state === "play" && ctrl.audio.src.length === 0 && ctrl.songRequestEnabled) {
                    if ($scope.requests.length !== 0) {
                        ctrl.changeCurrentSong("requests", 0);
                    }
                }
            }, 2500);
            
            let waitStart = Date.now();
            iv_waitForData = $interval(function() {
                if (Date.now() - waitStart >= 5_000) {
                    alert("Your request for the music player timed out!");
                    $interval.cancel(iv_waitForData);
                }
                if ($scope.ready) {
                    ctrl.initializePlaylist();
                    $interval.cancel(iv_waitForData);
                }
            });
        };

        ctrl.$onDestroy = function () {
            $interval.cancel(iv_autoPlay);
            $interval.cancel(iv_updateProgressBar);
            $interval.cancel(iv_waitForDataTemp);
            $interval.cancel(iv_waitForData);
            ctrl.audio.pause();
        }

        ctrl.initializePlaylist = function () {
            ctrl.initialized = true;
            if ($scope.requests.length !== 0 && ctrl.songRequestEnabled) {
                ctrl.currentSong.playlist = "requests";
                ctrl.currentSong.index = 0;
                ctrl.currentSong.musicData = $scope.requests[ctrl.currentSong.index];
            }
            else if ($scope.playlist.length !== 0) {
                ctrl.currentSong.playlist = "playlist";
                ctrl.currentSong.index = 0;
                ctrl.currentSong.musicData = $scope.playlist[ctrl.currentSong.index];
            }
        };

        ctrl.togglePlay = function (load) {
            if (ctrl.state === "pause") {
                ctrl.state = "play";
                ctrl.play(load);
            }
            else if(ctrl.state === "play") {
                ctrl.state = "pause";
                ctrl.pause();
            }
        };

        ctrl.play = function (load) {
            if (typeof load !== "boolean") load = false;
            if (ctrl.audio.src.length != 0 && !load) {
                ctrl.audio.play();
            }
            else {
                if (typeof ctrl.currentSong.musicData === "object" && ctrl.currentSong.musicData !== null && ctrl.currentSong.musicData.hasOwnProperty("videoId")) {
                    MusicBot.getSongInfo(ctrl.currentSong.musicData.videoId).then(function (res) {
                        if (res.status === 200) {
                            let thumbnailCount = 0;
                            for (let tn of res.data.info.videoDetails.thumbnails) {
                                thumbnailCount++;
                            }
                            res.data.info.thumbnail = res.data.info.videoDetails.thumbnails[thumbnailCount - 2];

                            ctrl.currentSong.ytData = res.data.info;
                            ctrl.audio.src = res.data.url;
                            ctrl.audio.play();
                        }
                        else console.warn(res);
                    });
                }
            }
        };

        ctrl.pause = function () {
            ctrl.audio.pause();
        };

        ctrl.nextSong = function () {
            if (ctrl.currentSong.playlist === "requests") {
                ctrl.removeByIndex(ctrl.currentSong.index, function () {
                    autoSelectNextSong();
                    ctrl.state = "play";
                    ctrl.play(true);
                });
            }
            else {
                autoSelectNextSong();
                ctrl.state = "play";
                ctrl.play(true);
            }
        };

        ctrl.previousSong = function () {
            if ($scope.playlist.length !== 0) {
                ctrl.playlistPosition--;
                if (ctrl.playlistPosition < 0) ctrl.playlistPosition = $scope.playlist.length - 1;
                ctrl.changeCurrentSong("playlist", ctrl.playlistPosition);
            }
        };

        ctrl.changeCurrentSong = function (type, index) {
            ctrl.currentSong.playlist = type;
            ctrl.currentSong.index = index;
            if (type === "requests") {
                ctrl.currentSong.musicData = $scope.requests[index];
                ctrl.state = "play";
                ctrl.play(true);
            }
            else if (type === "playlist") {
                ctrl.playlistPosition = index;
                ctrl.currentSong.musicData = $scope.playlist[index];
                ctrl.state = "play";
                ctrl.play(true);
            }
            else {
                console.error("Could not change song [invalid playlist type]");
            }
        };

        ctrl.removeByIndex = function (idx, cb) {
            MusicBot.removeRequestedSong($scope.requests[idx].id).then(function () {
                $scope.ready = false;
                $scope.update();

                let _start = Date.now();
                let iv_waitForDataTemp = $interval(() => {
                    if (Date.now() - _start >= 5_000) {
                        $interval.cancel(iv_waitForDataTemp);
                        alert("Your request for the music player timed out!");
                    }
                    if ($scope.ready) {
                        $interval.cancel(iv_waitForDataTemp);
                        if (typeof cb === "function") cb();
                    }
                });
            });
        };

        ctrl.fastForward = function (event) {
            let elementWidth = angular.element('.info.duration .progress').width();
            let clickedX = event.offsetX;
            if (clickedX < 0) clickedX = 0;
            if (clickedX > elementWidth) clickedX = elementWidth;
            let percentage = clickedX / elementWidth;
            ctrl.audio.currentTime = ctrl.audio.duration * percentage;
        };

        ctrl.changeVolume = function (event) {
            let elementWidth = angular.element('.info.volume .progress').width();
            let clickedX = event.offsetX;
            if (clickedX < 0) clickedX = 0;
            if (clickedX > elementWidth) clickedX = elementWidth;
            let percentage = clickedX / elementWidth;
            ctrl.audio.volume = 1 * percentage;
            ctrl.pbVolume = Number((ctrl.audio.volume * 100).toFixed(1));
            localStorage.setItem("music-player-volume", ctrl.audio.volume);
        };

        ctrl.changeVolumeViaWheel = function (direction) {
            let newVolume = ctrl.audio.volume;
            if (direction === 'up') {
                newVolume += 0.001;
            }
            if (direction === 'down') {
                newVolume -= 0.001;
            }
            if (newVolume >= 100) newVolume = 100;
            if (newVolume <= 0) newVolume = 0;
            ctrl.audio.volume = newVolume;
            ctrl.pbVolume = Number((100 * ctrl.audio.volume).toFixed(1));
            localStorage.setItem("music-player-volume", ctrl.audio.volume);
        };

        ctrl.songEnded = function () {
            ctrl.timeInfo = {
                max: "0:00",
                current: "0:00"
            }
            resetAudioPlayer();
            ctrl.pbMusic = 0;

            ctrl.nextSong();
        };

        ctrl.toggleExpandRequests = function () {
            ctrl.expandedRequests = !ctrl.expandedRequests;
        };

        ctrl.toggleExpandPlaylist = function () {
            ctrl.expandedPlaylist = !ctrl.expandedPlaylist;
        };

        ctrl.toggleVolumeVisibility = function() {
            let controls = angular.element('.info.volume > .controls');
            if ($(controls).is(":visible")) {
                $(controls).hide();
            }
            else {
                $(controls).show();
            }
        };

        ctrl.toggleSongRequestState = function() {
            localStorage.setItem("music-player-enable-song-request", ctrl.songRequestEnabled ? 1 : 0);
        };

        function resetAudioPlayer() {
            let volume = ctrl.audio.volume;
            ctrl.audio = new Audio();
            ctrl.audio.volume = volume;
            ctrl.audio.addEventListener('ended', ctrl.songEnded);
        }

        function autoSelectNextSong() {
            if (ctrl.currentSong.playlist === "playlist") {
                ctrl.playlistPosition++;
                if (ctrl.playlistPosition >= $scope.playlist.length) {
                    ctrl.playlistPosition = 0;
                }
            }
            if ($scope.requests.length !== 0 && ctrl.songRequestEnabled) {
                ctrl.currentSong.playlist = "requests";
                ctrl.currentSong.index = 0;
                ctrl.currentSong.musicData = $scope.requests[0];
            }
            else {
                ctrl.currentSong.playlist = "playlist";
                ctrl.currentSong.index = ctrl.playlistPosition;
                ctrl.currentSong.musicData = $scope.playlist[ctrl.currentSong.index];
            }
        }

        function secondsToMinuteString(seconds) {
            let _seconds = Math.floor(Math.round(seconds));
            let minutes = 0;
            let hours = 0;
            while (_seconds >= 60) {
                minutes++;
                _seconds -= 60;
            }
            while(minutes >= 60) {
                hours++;
                minutes -= 60;
            }
            if (hours !== 0) {
                let hourString = String(hours);
                let minuteString = String(minutes).replace(/^(\d)$/, '0$1');
                let secondString = String(_seconds).replace(/^(\d)$/, '0$1');
                return hourString + ":" + minuteString + ":" + secondString;
            }
            let minuteString = String(minutes);
            let secondString = String(_seconds).replace(/^(\d)$/, '0$1');
            return minuteString + ":" + secondString;
        }
    }

    angular.module('app')
        .controller('DirectiveMusicPlayerController', controller);

})();