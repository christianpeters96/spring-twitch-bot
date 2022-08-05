(function () {

    function spamBlacklistDirective() {
        return {
            restrict: 'E',
            templateUrl: 'app/page/security/spam/spam-blacklist.html',
            controller: 'SpamBlacklistController',
            controllerAs: '$ctrl'
        };
    }

    angular.module('app')
        .directive('spamBlacklist', spamBlacklistDirective);

})();