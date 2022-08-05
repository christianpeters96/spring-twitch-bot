(function () {

    function spamFilterService ($http) {
        function defaultHeaders() {
            return {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            getSettings: function (filter) {
                return $http.get('/api/spam/settings/' + filter, { headers: defaultHeaders() });
            },
            saveSettings: function (filter, settings) {
                return $http.post('/api/spam/settings', settings, { headers: defaultHeaders() });
            },
            getBlacklistedWords: function () {
                return $http.get('/api/spam/blacklist/word', { headers: defaultHeaders() });
            },
            addBlacklistedWord: function (word) {
                return $http.put('/api/spam/blacklist/word', { string: word }, { headers: defaultHeaders() });
            },
            removeBlacklistedWord: function (word) {
                return $http.delete('/api/spam/blacklist/word', {data: { string: word }, headers: defaultHeaders() });
            },
            getExemptibleRoles: function () {
                return $http.get('/api/spam/exemption/roles', { headers: defaultHeaders() });
            },
            getExemptions: function (filter) {
                return $http.get('/api/spam/exemption/' + filter, { headers: defaultHeaders() });
            },
            addExemption: function (filter, query) {
                return $http.put('/api/spam/exemption/' + filter, { string: query }, { headers: defaultHeaders() });
            },
            removeExemption: function (filter, query) {
                console.log('/api/spam/exemption/' + filter, {data: { string: query }, headers: defaultHeaders() });
                return $http.delete('/api/spam/exemption/' + filter, {data: { string: query }, headers: defaultHeaders() });
            }
        }
    }

    angular.module('app')
        .service('SpamFilter', spamFilterService);

})();