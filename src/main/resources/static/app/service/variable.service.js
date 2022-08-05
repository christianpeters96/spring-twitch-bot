(function () {

    function variableService ($http) {
        function defaultHeaders() {
            return {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type': 'text/plain'
            };
        }

        return {
            get: function (varName) {
                return $http.get('/api/vars/' + varName, { headers: defaultHeaders() });
            },
            set: function (varName, varValue) {
                return $http.post('/api/vars/' + varName, varValue, { headers: defaultHeaders() });
            },
            remove: function (varName) {
                return $http.delete('/api/vars/' + varName, { headers: defaultHeaders() });
            }
        }
    }

    angular.module('app').service('Variable', variableService);

})();
