(function () {

    function twitchBotService ($http) {
        function defaultHeaders() {
            return {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            start: function () {
                return $http.get('/api/twitch/bot/start', { headers: defaultHeaders() });
            },
            stop: function () {
                return $http.get('/api/twitch/bot/stop', { headers: defaultHeaders() });
            },
            getStatus: function () {
                return $http.get('/api/twitch/bot/status', { headers: defaultHeaders() });
            },
            getWeeklyStats: function () {
                return $http.get('/api/data/weekly', { headers: defaultHeaders() });
            },
            getTopData: function () {
                return $http.get('/api/data/top', { headers: defaultHeaders() });
            },
            getLatestChat: function (channelName) {
                return $http.get('/api/data/chat?channel='+channelName, { headers: defaultHeaders() });
            }
        }
    }

    angular.module('app')
        .service('TwitchBot', twitchBotService);

})();