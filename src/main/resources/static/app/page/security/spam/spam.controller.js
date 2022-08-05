(function () {

    function securitySpamController() {
        let ctrl = this;
        ctrl.tab = '';

        ctrl.$onInit = function () {
            let tab = localStorage.getItem('spam_tab');
            if (typeof tab === "string") ctrl.tab = tab;
            else ctrl.tab = 'words';
        }

        ctrl.changeTab = function (tab) {
            ctrl.tab = tab;
            localStorage.setItem('spam_tab', tab);
        }
    }

    angular.module('app')
        .controller('SecuritySpamController', securitySpamController);

})();
