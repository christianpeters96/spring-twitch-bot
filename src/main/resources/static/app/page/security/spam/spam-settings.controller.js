(function () {

    function spamSettingsController($scope, $filter, toastr, SpamFilter) {
        let ctrl = this;
        let filter = $scope.filterName;
        ctrl.settings = {};
        ctrl.$onInit = function () {
            if (typeof $scope.noTimeoutTime !== "boolean") $scope.noTimeoutTime = false;
            if (typeof $scope.noLimit !== "boolean") $scope.noLimit = false;
            if (typeof $scope.noSilent !== "boolean") $scope.noSilent = false;
            if (typeof $scope.noMessage !== "boolean") $scope.noMessage = false;
            SpamFilter.getSettings(filter).then(function (res) {
                ctrl.settings = res.data;
            });
        }

        ctrl.toggle = function () {
            ctrl.settings.active = !ctrl.settings.active;
        }

        ctrl.save = function () {
            let tabTitle = $filter('translate')('page.security.spam.' + filter + '.tab');
            SpamFilter.saveSettings(filter, ctrl.settings).then(function (res) {
                if (res.status === 200 && res.data === true) {
                    let text = $filter('translate')('page.security.spam.' + filter + '.toastr.success_text').replace(/%filter%/, tabTitle);
                    toastr.success(text, $filter('translate')('page.security.spam.' + filter + '.toastr.success_title'));
                } else {
                    toastr.error($filter('translate')('page.security.spam.' + filter + '.toastr.error_text'), $filter('translate')('page.security.spam.' + filter + '.toastr.error_title'));
                }
            }, function (err) {
                toastr.error($filter('translate')('page.security.spam.' + filter + '.toastr.error_text'), $filter('translate')('page.security.spam.' + filter + '.toastr.error_title'));
                console.error(err);
            });
        }
    }

    angular.module('app')
        .controller('SpamSettingsController', spamSettingsController);

})();