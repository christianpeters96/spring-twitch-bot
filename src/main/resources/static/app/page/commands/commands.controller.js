(function () {

    function commandController ($state, $uibModal, Command) {
        let ctrl = this;
        ctrl.commands = [];

        ctrl.$onInit = function () {
            ctrl.update();
        }

        ctrl.update = function () {
            Command.getList().then(function (res) {
                if (res.status === 200) {
                    ctrl.commands = res.data;
                }
            });
        }

        ctrl.edit = function (cmdId) {
            $state.go('command_edit', { commandId: cmdId });
        }

        ctrl.delete = function (cmdInfo) {
            $uibModal.open({
                animation: true,
                ariaLabelledBy: 'Delete Command ' + cmdInfo.id,
                ariaDescribedBy: 'Do you want to delete this command?',
                templateUrl: 'deleteCommand.html',
                controller: function ($uibModalInstance, Command, cmdInfo) {
                    this.info = cmdInfo;
                    this.ok = function () {
                        Command.delete(cmdInfo.id).then(function (res) {
                            $uibModalInstance.close(true);
                            ctrl.update();
                        });
                    }
                    this.cancel = function () {
                        $uibModalInstance.dismiss('cancel');
                    }
                },
                controllerAs: '$ctrl',
                size: 'xs',
                resolve: {
                    cmdInfo: function () {
                        return cmdInfo;
                    }
                }
            });
        }

        ctrl.enable = function (cmdId) {
            Command.enable(cmdId).then(function (res) {
                if (res.status === 200 && res.data.body === true) {
                    ctrl.update();
                }
            });
        }

        ctrl.disable = function (cmdId) {
            Command.disable(cmdId).then(function (res) {
                if (res.status === 200 && res.data.body === true) {
                    ctrl.update();
                }
            });
        }
    }

    angular.module('app').controller('CommandController', commandController);

})();