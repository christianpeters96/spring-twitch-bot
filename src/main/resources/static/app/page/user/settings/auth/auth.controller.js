(function () {

    function userSettingsController($filter, $cookies, $rootScope, Variable, Integration, toastr, Twitch) {
        var ctrl = this;

        ctrl.twitchToken = "";
        ctrl.integrations = {};

        ctrl.var = {
            discord_webhook: {
                key: 'discord_webhook',
                value: ''
            }
        };

        ctrl.saveVar = function (obj) {
            Variable.set(obj.key, obj.value).then(function (res) {
                if (res.status === 200 && res.data) {
                    $rootScope.$emit('toastr', {
                        type: "success",
                        title: "Erfolg",
                        msg: "Dein Discord-Webhook wurde aktualisiert"
                    });
                }
            });
        }

        ctrl.$onInit = function () {
            Integration.getList().then(function (res) {
                if (res.status === 200) {
                    ctrl.integrations = res.data;
                    if (ctrl.integrations['discord']['valid']) {
                        Variable.get('discord_webhook').then(function (res) {
                            ctrl.var.discord_webhook.value = res.data.value;
                        });
                    }
                }
            });

            ctrl.checkConnections();
            Twitch.getInternalUser().then(function (res) {
                if (res.status === 200) {
                    ctrl.twitchToken = res.data.botToken;
                }
            });
        };

        ctrl.submitTwitchToken = function () {
            Twitch.submitBotToken(ctrl.twitchToken).then(function (res) {
                if (res.status === 200) {
                    toastr.success($filter('translate')('page.user.auth.toastr.success_text'), $filter('translate')('page.user.auth.toastr.success_title'));
                } else {
                    toastr.error($filter('translate')('page.user.auth.toastr.error_text'), $filter('translate')('page.user.auth.toastr.error_title'));
                }
            }, function (err) {
                if (err) {
                    toastr.error($filter('translate')('page.user.auth.toastr.error_text'), $filter('translate')('page.user.auth.toastr.error_title'));
                }
            });
        };

        ctrl.removeConnection = function (provider) {
            Integration.remove(provider).then(function () {
                ctrl.checkConnections();
            });
        };

        ctrl.openUrl = function (integration) {
            $cookies.put('token', localStorage.getItem('token'));
            location.href = integration.authorizationUrl;
        };

        ctrl.checkConnections = function () {
            Integration.getList().then(function (res) {
                if (res.status === 200) {
                    ctrl.integrations = res.data;
                }
            });
        }
    }

    angular.module('app')
        .controller('UserSettingsController', userSettingsController);

})();