(function () {

    function spamLinkWhitelistDirective() {
        return {
            restrict: 'E',
            templateUrl: 'app/page/security/spam/spam-link-whitelist.html',
            controller: 'SpamLinkWhitelistController',
            controllerAs: '$ctrl'
        };
    }

    angular.module('app')
        .directive('spamLinkWhitelist', spamLinkWhitelistDirective);

})();