(function () {

    function commandService ($http) {
        function defaultHeaders() {
            return {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            getList: function () {
                return $http.get('/api/commands', { headers: defaultHeaders() });
            },
            get: function (id) {
                return $http.get('/api/commands/' + id, { headers: defaultHeaders() });
            },
            delete: function (id) {
                return $http.delete('/api/commands/' + id, { headers: defaultHeaders() });
            },
            enable: function (id) {
                return $http.post('/api/commands/' + id + '/status', { active: true }, { headers: defaultHeaders() });
            },
            disable: function (id) {
                return $http.post('/api/commands/' + id + '/status', { active: false }, { headers: defaultHeaders() });
            },
            create: function (info) {
                return $http.put('/api/commands', info, { headers: defaultHeaders() });
            },
            update: function (id, info) {
                return $http.post('/api/commands/' + id, info, { headers: defaultHeaders() });
            }
        }
    }

    angular.module('app')
        .service('Command', commandService);

})();