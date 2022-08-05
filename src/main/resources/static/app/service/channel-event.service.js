(function () {

    function channelEventService ($http) {
        function defaultHeaders() {
            return {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            getList: function () {
                return $http.get('/api/channel/events', { headers: defaultHeaders() });
            },
            getFollowers: function () {
                return $http.get('/api/channel/events/followers', { headers: defaultHeaders() });
            }
        }
    }

    angular.module('app')
        .service('ChannelEvent', channelEventService);

})();