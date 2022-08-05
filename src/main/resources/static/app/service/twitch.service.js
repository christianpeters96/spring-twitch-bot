(function () {

    function twitchService ($http) {
        var clientId = "dzcjn07tj2h32hm9g8bn6sday73m57";

        function defaultHeaders() {
            return {
                'Accept': 'application/vnd.twitchtv.v5+json',
                'Client-ID': clientId,
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            submitBotToken: function (token) {
                return $http({
                    method: 'GET',
                    url: '/api/user/tmi?token=' + token,
                    headers: defaultHeaders()
                });
            },
            getInternalUser: function() {
                return $http({
                    method: 'GET',
                    url: '/api/user',
                    headers: defaultHeaders()
                });
            },
            getRoles: function () {
                return $http({
                    method: 'GET',
                    url: '/api/twitch/roles'
                })
            }
        }
    }

    angular.module('app').service('Twitch', twitchService);

})();

// CHECK LIVE                   https://api.twitch.tv/helix/streams?user_login={USERNAME}
// CHECK BROADCASTER TYPE       https://api.twitch.tv/kraken/channel