(function () {

    function spotifyService ($http) {
        function defaultHeaders() {
            return {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            authorize: function () {
                return $http.get('/api/spotify/check', { headers: defaultHeaders() });
            },
            disconnect: function () {
                return $http.get('/api/spotify/disconnect', { headers: defaultHeaders() });
            },
            getAuthorizationUrl: function () {
                return $http.get('/api/spotify/auth/url');
            },
            authorizeWithCode: function (code) {
                return $http.get('/api/spotify/auth/code?code='+code, { headers: defaultHeaders() });
            },
            claimToken: function (token) {
                return $http.get('/api/spotify/auth/claim?token='+token, { headers: defaultHeaders() });
            }
        }
    }

    angular.module('app').service('Spotify', spotifyService);

})();
