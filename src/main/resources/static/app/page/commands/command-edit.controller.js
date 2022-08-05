(function () {

    function commandEditController ($state, $stateParams, Command) {
        let ctrl = this;
        let cmdId = $stateParams.commandId;
        ctrl.loaded = false;
        ctrl.aliasCount = 0;
        ctrl.data = {actions:[]};
        if (typeof cmdId !== "string" || cmdId.length === 0 || isNaN(Number(cmdId))) {
            $state.go('commands');
        }
        ctrl.mode = 'edit';
        ctrl.id = Number(cmdId);

        ctrl.$onInit = function () {
            Command.get(ctrl.id).then(function (res) {
                if (res.status !== 200) $state.go('commands');
                else {
                    ctrl.data = res.data;
                    ctrl.aliasCount = typeof ctrl.data.aliases === "object" ? ctrl.data.aliases.length : 0;
                    for (let idx in ctrl.data.aliases) {
                        if (ctrl.data.aliases.hasOwnProperty(idx) && typeof ctrl.data.aliases[idx] === "object") {
                            ctrl.data.aliases[idx] = ctrl.data.aliases[idx].alias;
                        }
                    }
                    ctrl.loaded = true;
                }
            }, function (err) {
                if (err) $state.go('commands');
            })
        }

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
            Command.update(ctrl.id, ctrl.data).then(function (res) {
                console.log(res);
            });
        }
    }

    angular.module('app').controller('CommandEditController', commandEditController);

})();