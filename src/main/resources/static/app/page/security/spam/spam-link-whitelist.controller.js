(function () {

    function spamLinkWhitelistController(SpamFilter) {
        let ctrl = this;
        ctrl.entry = '';
        ctrl.words = [];
        ctrl.lock = true;

        ctrl.$onInit = function () {
            ctrl.reload();
        }

        ctrl.add = function () {
            ctrl.lock = true;
            SpamFilter.addBlacklistedWord(ctrl.entry).then(function (res) {
                if (res.status === 200 && res.data.hasOwnProperty('body') && res.data.body === true) {
                    ctrl.entry = '';
                }
                ctrl.reload();
            });
        }

        ctrl.reload = function () {
            SpamFilter.getBlacklistedWords().then(function (res) {
                if (res.status === 200) {
                    ctrl.words = res.data;
                }
                ctrl.lock = false;
            });
        }

        ctrl.remove = function (idx) {
            let deleteWord = '';
            for (let _idx = 0; _idx < ctrl.words.length; _idx++) {
                if (_idx === idx) {
                    deleteWord = ctrl.words[_idx];
                    break;
                }
            }

            SpamFilter.removeBlacklistedWord(deleteWord).then(function () {
                ctrl.reload();
            });
        }

        ctrl.exists = function () {
            for (let word of ctrl.words) {
                if (word === ctrl.entry) {
                    return true;
                }
            }
            return false;
        }
    }

    angular.module('app')
        .controller('SpamLinkWhitelistController', spamLinkWhitelistController);

})();