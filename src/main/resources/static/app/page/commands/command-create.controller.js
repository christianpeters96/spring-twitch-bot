(function () {

    function commandCreateController ($state, Command) {
        let ctrl = this;
        ctrl.mode = 'create';
        ctrl.id = 0;
        ctrl.aliasCount = 0;
        ctrl.data = {
            name: '',
            description: '',
            command: '',
            aliases: [],
            global_delay: 0,
            user_delay: 0,
            cost: 0,
            argc: 0,
            actions: [],
            permissions: []
        };

        ctrl.increaseAliasCount = function () {
            ctrl.aliasCount++;
            ctrl.data.aliases.push('');
        }

        ctrl.decreaseAliasCount = function () {
            if (ctrl.aliasCount > 0) {
                ctrl.aliasCount--;
                ctrl.data.aliases.pop();
            }
        }

        ctrl.save = function () {
            console.log(ctrl.data);
            Command.create(ctrl.data).then(function (res) {
                if (res.status === 200 && res.data.body === true) {
                    $state.go('commands');
                }
            });
        }
    }

    angular.module('app').controller('CommandCreateController', commandCreateController);

})();