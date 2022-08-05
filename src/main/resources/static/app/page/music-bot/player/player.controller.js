(function () {

    function controller($q, $scope, $interval, MusicBot) {
        let ctrl = this;

        ctrl.dataReady = false;
        ctrl.songRequests = [];
        ctrl.playlist = [];

        let iv_update = null;
        ctrl.$onInit = function () {
            ctrl.fullUpdate();
            iv_update = $interval(ctrl.fullUpdate, 3000);
        };

        $scope.$on("$destroy", function () {
            $interval.cancel(iv_update);
        });

        ctrl.updateRequestedSongs = function (cb) {
            MusicBot.getRequests().then(function (res) {
                if (res.status === 200) {
                    ctrl.songRequests = res.data;
                    if (typeof cb === "function") cb();
                }
            });
        };

        ctrl.updatePlaylist = function (cb) {
            MusicBot.getPlaylist().then(function (res) {
                if (res.status === 200) {
                    ctrl.playlist = res.data;
                    if (typeof cb === "function") cb();
                }
            });
        };

        ctrl.fullUpdate = function () {
            ctrl.updatePlaylist(function () {
                ctrl.updateRequestedSongs(function () {
                    ctrl.dataReady = true;
                });
            });
        }
    }

    angular.module('app')
        .controller('MusicBotPlayerController', controller);

})();