(function () {

    function controller($rootScope, $scope, $interval, MusicBot) {
        let ctrl = this;
        ctrl.input_url = "";
        ctrl.playlist = [];

        let iv_updatePlaylist = null;
        ctrl.$onInit = function () {
            ctrl.updatePlaylist();
            iv_updatePlaylist = $interval(ctrl.updatePlaylist, 3000);
        };

        $scope.$on("$destroy", function () {
            $interval.cancel(iv_updatePlaylist);
        });

        ctrl.updatePlaylist = function () {
            MusicBot.getPlaylist().then(function (res) {
                if (res.status === 200) {
                    for (let data of res.data) {
                        data.videoDurationSec = secondsToMinuteString(data.videoDurationSec);
                    }
                    ctrl.playlist = res.data;
                }
            });
        };

        ctrl.addSong = function () {
            let re = /https?:\/\/(?:www\.)?youtube\.com\/watch\?(?:.+)?v=([A-Za-z0-9_-]+)(?:&.*)?$/;
            if (re.test(ctrl.input_url)) {
                let videoId = ctrl.input_url.replace(re, '$1');
                $rootScope.$emit('toastr', {
                    type: "success",
                    title: "Erfolg",
                    msg: "VideoID = " + videoId
                });
                ctrl.input_url = "";
                MusicBot.addSongToPlaylist(videoId);
            }
            else {
                $rootScope.$emit('toastr', {
                    type: "error",
                    title: "Ungültige URL",
                    msg: "Die eingegeben Youtube-URL ist ungültig"
                });                
            }
        };

        ctrl.addPlaylist = function () {
            let re = /https?:\/\/(?:www\.)?youtube\.com\/watch\?(?:.+)?list=([A-Za-z0-9_-]+)(?:&.*)?$/;
            if (re.test(ctrl.input_url)) {
                let playlistId = ctrl.input_url.replace(re, '$1');
                $rootScope.$emit('toastr', {
                    type: "success",
                    title: "Erfolg",
                    msg: "PlaylistID = " + playlistId
                });
                MusicBot.addPlaylistToPlaylist(playlistId);
            }
            else {
                $rootScope.$emit('toastr', {
                    type: "error",
                    title: "Ungültige URL",
                    msg: "Die eingegeben Youtube-URL ist ungültig"
                });                
            }
        };

        ctrl.removeByIndex = function (idx) {
            MusicBot.removeSongFromPlaylist(ctrl.playlist[idx].id).then(function () {
                ctrl.updatePlaylist();
            });
        };

        function secondsToMinuteString(seconds) {
            let _seconds = Math.floor(seconds);
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
        .controller('MusicBotPlaylistController', controller);

})();