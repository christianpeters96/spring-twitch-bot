(function () {

    function controller(MusicBot) {
        let ctrl = this;

        ctrl.settings = {};

        ctrl.$onInit = function () {
            MusicBot.getSettings().then(function (res) {
                if (res.status === 200) {
                    ctrl.settings = res.data;
                    console.log("MusicBot-Settings", ctrl.settings);
                }
            });
        };

        ctrl.setProvider = function (provider) {
            ctrl.settings.general.provider = provider;
        };

        ctrl.save = function () {
            MusicBot.saveSettings(ctrl.settings);
        };
    }

    angular.module('app')
        .controller('MusicBotSettingsController', controller);

})();