(function () {

    function commandPermission () {
        return {
            restrict: 'E',
            templateUrl: 'app/page/commands/permission/command-permission.html',
            controller: 'CommandPermissionController',
            controllerAs: '$ctrl',
            scope: {
                ngModel: '='
            }
        };
    }

    angular.module('app')
        .directive('commandPermission', commandPermission);

})();