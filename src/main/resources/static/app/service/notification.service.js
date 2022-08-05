(function () {

    function notificationService ($rootScope) {
        if (typeof $rootScope.notifications !== "object" || $rootScope.notifications !== null || !$rootScope.notifications.hasOwnProperty('length')) {
            $rootScope.notifications = [];
        }

        function addNotification (id, icon, type, text, sub, event) {
            var alreadyExists = false;
            for (var index in $rootScope.notifications) {
                var obj = $rootScope.notifications[index];
                if (obj.id === id) {
                    alreadyExists = true;
                }
            }
            if (!alreadyExists) {
                $rootScope.notifications.push({
                    id: id,
                    icon: icon,
                    type: type,
                    text: text,
                    sub: sub,
                    event: event
                });
            }
        }

        return {
            add: function (id, icon, type, text, sub, event) {
                addNotification (id, icon, type, text, sub, event);
            },
            getAll: function () {
                return angular.copy($rootScope.notifications);
            }
        }
    }

    angular.module('app')
        .service('Notification', notificationService);

})();