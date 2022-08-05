(function () {

    function commandPermissionController ($scope, Twitch) {
        let ctrl = this;
        ctrl.type = 'role';
        ctrl.value = 'subscriber';
        ctrl.query = '';
        ctrl.selectedRoles = [];
        ctrl.roleList = [];

        ctrl.$onInit = function () {
            ctrl.updateQuery();
            Twitch.getRoles().then(function (res) {
                if (res.status === 200 && res.data.length !== 0) {
                    for (let data of res.data) {
                        let spl = data.split(':');
                        ctrl.roleList.push({
                            name: spl[0],
                            key: spl[1]
                        });
                    }
                }
                ctrl.reload();
            });
        };

        ctrl.add = function () {
            if (!ctrl.exists()) {
                $scope.ngModel.push(ctrl.query);
                ctrl.reload();
                if (ctrl.type === 'user') {
                    ctrl.value = '';
                }
            }
        };

        ctrl.reload = function () {
            let newList = [];
            for (let data of $scope.ngModel) {
                let dataStr = data;
                if (typeof data !== "string" && data.hasOwnProperty("permission")) {
                    dataStr = data.permission;
                }
                let spl = dataStr.split(':');
                // let type = spl[0] === 'role' ? 'role' : (spl[0] === 'user' ? 'user' : 'unknown');
                let type = (spl[0] === 'role' || spl[0] === 'user') ? spl[0] : 'unknown';
                let value = spl[1];
                if (spl[0] === 'role') {
                    for (let role of ctrl.roleList) {
                        if (role.key === value) {
                            value = role.name;
                            break;
                        }
                    }
                }
                newList.push({
                    type: type,
                    value: value,
                    query: dataStr
                });
            }
            ctrl.selectedRoles = newList;
        };

        ctrl.remove = function (idx) {
            console.log(idx);
            let newList = [];
            for (let _idx = 0; _idx < $scope.ngModel.length; _idx++) {
                if (_idx === idx) continue;
                newList.push($scope.ngModel[_idx]);
            }
            $scope.ngModel = newList;
            ctrl.reload();
        };

        ctrl.exists = function () {
            for (let query of $scope.ngModel) {
                if (query === ctrl.query) {
                    return true;
                }
            }
            return false;
        };

        ctrl.typeChanged = function () {
            if (ctrl.type === 'role') {
                ctrl.value = 'subscriber';
            }
            else {
                ctrl.value = '';
            }
            ctrl.updateQuery();
        };

        ctrl.updateQuery = function () {
            ctrl.query = ctrl.type + ':' + ctrl.value;
        };
    }

    angular.module('app')
        .controller('CommandPermissionController', commandPermissionController);

})();