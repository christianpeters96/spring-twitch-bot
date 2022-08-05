(function () {

    angular.module('app')
        .directive('musicPlayer', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directive/music-player/music-player.html',
                controller: 'DirectiveMusicPlayerController',
                controllerAs: '$dir',
                scope: {
                    playlist: '=',
                    requests: '=songRequests',
                    ready: '=',
                    update: '&updateMethod'
                }
            };
        });

})();