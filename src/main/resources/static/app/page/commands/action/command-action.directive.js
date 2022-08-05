(function () {

    function commandAction () {
        return {
            restrict: 'E',
            templateUrl: 'app/page/commands/action/command-action.html',
            controller: 'CommandActionController',
            controllerAs: '$ctrl',
            scope: {
                ngModel: '=',
                args: '='
            }
        };
    }

    angular.module('app')
        .directive('commandAction', commandAction);

})();