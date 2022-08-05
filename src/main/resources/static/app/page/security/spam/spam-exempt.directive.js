(function () {

    function spamExemptDirective () {
        return {
            restrict: 'E',
            templateUrl: 'app/page/security/spam/spam-exempt.html',
            controller: 'SpamExemptController',
            controllerAs: '$ctrl',
            scope: {
                filterName: '@'
            }
        };
    }

    angular.module('app')
        .directive('spamExempt', spamExemptDirective);

})();