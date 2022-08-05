(function () {

    function spamExemptController ($scope, SpamFilter) {
        let ctrl = this;
        let filter = $scope.filterName;
        ctrl.type = 'role';
        ctrl.value = 'subscriber';
        ctrl.query = '';
        ctrl.lock = true;
        ctrl.roleList = [];
        ctrl.exemptions = [];

        ctrl.$onInit = function () {
            ctrl.updateQuery();
            SpamFilter.getExemptibleRoles().then(function (res) {
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
        }

        ctrl.add = function () {
            ctrl.lock = true;
            SpamFilter.addExemption(filter, ctrl.query).then(function (res) {
                if (res.status === 200 && res.data.hasOwnProperty('body') && res.data.body === true) {
                    ctrl.entry = '';
                }
                ctrl.reload();
            });
        }

        ctrl.reload = function () {
            let newList = [];
            SpamFilter.getExemptions(filter).then(function (res) {
                if (res.status === 200) {
                    if (res.data.length !== 0) {
                        for (let data of res.data) {
                            let spl = data.split(':');
                            let type = spl[0];
                            let value = spl[1];
                            if (spl[0] === 'role') {
                                for (let role of ctrl.roleList) {
                                    if (role.key === value) {
                                        value = role.key;
                                        break;
                                    }
                                }
                            }
                            newList.push({
                                type: type,
                                value: value,
                                query: data
                            });
                        }
                    }
                    ctrl.exemptions = newList;
                }
                ctrl.lock = false;
            });
        }

        ctrl.remove = function (idx) {
            let deleteQuery = {};
            for (let _idx = 0; _idx < ctrl.exemptions.length; _idx++) {
                if (_idx === idx) {
                    deleteQuery = ctrl.exemptions[_idx];
                    break;
                }
            }

            SpamFilter.removeExemption(filter, deleteQuery.query).then(function () {
                ctrl.reload();
            });
        }

        ctrl.exists = function () {
            for (let query of ctrl.exemptions) {
                if (query === ctrl.entry) {
                    return true;
                }
            }
            return false;
        }

        ctrl.typeChanged = function () {
            if (ctrl.type === 'role') {
                ctrl.value = 'subscriber';
            }
            else {
                ctrl.value = '';
            }
            ctrl.updateQuery();
        }

        ctrl.updateQuery = function () {
            ctrl.query = ctrl.type + ':' + ctrl.value;
        }
    }

    angular.module('app')
        .controller('SpamExemptController', spamExemptController);

})();