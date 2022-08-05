(function () {

    function integrationService ($http) {
        function defaultHeaders() {
            return {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            get: function (provider) {
                return $http.get('/api/integration/' + provider, { headers: defaultHeaders() });
            },
            getList: function () {
                return $http.get('/api/integration', { headers: defaultHeaders() });
            },
            remove: function (provider) {
                return $http.delete('/api/integration/' + provider, { headers: defaultHeaders() });
            },
            getAuthorizationUrl: function (provider) {
                return $http.get('/api/integration/' + provider + '/auth-url');
            }
        }
    }

    angular.module('app').service('Integration', integrationService);

})();
