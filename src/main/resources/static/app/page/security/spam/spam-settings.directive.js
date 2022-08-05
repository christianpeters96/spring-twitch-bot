(function () {

    function spamSettingsDirective() {
        return {
            restrict: 'E',
            templateUrl: 'app/page/security/spam/spam-settings.html',
            controller: 'SpamSettingsController',
            controllerAs: '$ctrl',
            scope: {
                filterName: '@',
                noTimeoutTime: '=?',
                noLimit: '=?',
                noSilent: '=?',
                noMessage: '=?'
            }
        };
    }

    angular.module('app')
        .directive('spamSettings', spamSettingsDirective);

})();