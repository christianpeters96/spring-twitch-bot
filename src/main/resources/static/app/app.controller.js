(function () {

    function appController (Integration, Notification, Twitch, TwitchBot, Spotify, $rootScope, $scope, $interval, $timeout, $translate, $cookies, $location, $state, toastr) {
        let ctrl = this;
        ctrl.authUrl = "#/login";
        ctrl.authenticated = true;
        ctrl.userInfo = {};
        ctrl.notifications = [];
        ctrl.spotifyUrl = "";

        ctrl.bot = {
            init: false,
            status: "unknown",
            action: {
                description: '',
                disabled: true,
                method: null
            }
        }

        ctrl.showSpotifyNotification = function () {
            Notification.add(1, "spotify", "success", "Spotify ist nicht verknüpft", "Klicke hier, um in die Einstellungen zu gelangen.",
                function () {
                    $state.go('user_auth');
                    // location.href = ctrl.spotifyUrl;
                });
        }

        ctrl.signOut = function () {
            Integration.remove('twitch').then(function (res) {
                if (res.status === 200) {
                    console.log(res.data);
                    localStorage.removeItem('token');
                    location.reload();
                }
            });
        }

        ctrl.checkAuth = function () {
            ctrl.loadUserInfo();
        }

        ctrl.loadUserInfo = function () {
            Twitch.getInternalUser().then(function (res) {
                ctrl.authenticated = res.status === 200;
                if (ctrl.authenticated) {
                    ctrl.userInfo = res.data;
                    // $state.go($state.current === 'login' ? 'dashboard' : $state.current);
                }
                else $state.go('login');
            }, function (err) {
                if (err) {
                    ctrl.authenticated = false;
                    $state.go('login');
                }
            });
        }

        ctrl.checkNotifications = function () {
            ctrl.notifications = Notification.getAll();
        }

        ctrl.checkBotStatus = function () {
            if (ctrl.authenticated) {
                TwitchBot.getStatus().then(function (res) {
                    if (res.status === 200) {
                        ctrl.bot.status = res.data ? 'online' : 'offline';
                        switch (ctrl.bot.status) {
                            case 'online':
                                ctrl.bot.action.description = 'bot.action.disconnect';
                                ctrl.bot.action.method = ctrl.stopBot;
                                break;
                            case 'offline':
                                ctrl.bot.action.description = 'bot.action.connect';
                                ctrl.bot.action.method = ctrl.startBot;
                                break;
                            default:
                                ctrl.bot.action.description = '';
                                ctrl.bot.action.method = null;
                                break;
                        }
                        if (!ctrl.bot.init) {
                            ctrl.bot.init = true;
                            ctrl.bot.action.disabled = false;
                        }
                    }
                });
            }
        }

        ctrl.checkAuthInterval = $interval(ctrl.checkAuth, 150000); // 2,5 mins
        ctrl.checkNotificationsInterval = $interval(ctrl.checkNotifications, 1000); // 1 sec
        ctrl.checkBotStatusInterval = $interval(ctrl.checkBotStatus, 10000); // 10 sec
        $timeout(ctrl.checkBotStatus, 1000); // 1 sec

        ctrl.startBot = function () {
            ctrl.bot.action.disabled = true;
            $timeout(function () {
                TwitchBot.getStatus().then(function (res) {
                    console.log(res);
                    if (res.data) {
                        toastr.success('Der Bot wurde erfolgreich gestartet', 'Erfolg');
                    }
                    else {
                        toastr.error('Beim Starten des Bots ist ein Fehler aufgetreten', 'Fehler');
                        ctrl.checkBotStatus();
                    }
                });
            }, 1500);
            $timeout(function () {
                ctrl.bot.action.disabled = false;
            }, 5000);

            TwitchBot.start().then(function (res) {
                if (res.status === 200) {
                    if (res.data === true) {
                        console.log("Connected");
                        ctrl.checkBotStatus();
                    }
                    if (res.data === false) {
                        console.log("Could not connect");
                    }
                }
            });
        }

        ctrl.stopBot = function () {
            $timeout(function () {
                ctrl.bot.action.disabled = false;
            }, 5000);

            ctrl.bot.action.disabled = true;
            TwitchBot.stop().then(function (res) {
                if (res.status === 200) {
                    if (res.data === true) {
                        console.log("Disconnected");
                        ctrl.checkBotStatus();
                    }
                    if (res.data === false) {
                        console.log("Could not disconnect");
                    }
                }
            });
        }

        $scope.$watch(function(){
            return $state.$current.name;
        }, function(newVal, oldVal){
            if (!ctrl.authenticated) {
                $state.go('login');
            }
        })

        ctrl.$onInit = function () {
            let authToken = $cookies.get('token');
            if (typeof authToken === 'string' && authToken.length > 0) {
                localStorage.setItem('token', authToken);
                $cookies.remove('token');
            }
            ctrl.checkAuth();

            Integration.get('twitch').then(function (res) {
                if (res.status === 200) {
                    ctrl.authUrl = res.data['authorizationUrl'];
                }
            });

            let storedLanguage = localStorage.getItem('lang');
            if (storedLanguage === null) {
                ctrl.languageList = ['de','us'];
                ctrl.currentLanguage = ctrl.languageList[0];
            }
            else {
                ctrl.languageList = [storedLanguage,(storedLanguage==='de'?'us':'de')];
                ctrl.currentLanguage = storedLanguage;
            }
            $translate.use(ctrl.currentLanguage);
        };

        ctrl.$onDestroy = function () {
            $interval.cancel(ctrl.checkAuthInterval);
            $interval.cancel(ctrl.checkNotificationsInterval);
            $interval.cancel(ctrl.checkBotStatusInterval);
        };

        $rootScope.$on('toastr', function (event, obj) {
            if (obj.type === "success") {
                toastr.success(obj.msg, obj.title);
            }
            if (obj.type === "error") {
                toastr.error(obj.msg, obj.title);
            }
        });

        ctrl.switchLanguage = function (selectedLanguage) {
            if (ctrl.currentLanguage !== selectedLanguage) {
                if (selectedLanguage === ctrl.languageList[0]) {
                    let temp = angular.copy(ctrl.languageList);
                    ctrl.currentLanguage = ctrl.languageList[0];
                    ctrl.languageList = [temp[0], temp[1]];
                }
                if (selectedLanguage === ctrl.languageList[1]) {
                    let temp = angular.copy(ctrl.languageList);
                    ctrl.currentLanguage = ctrl.languageList[1];
                    ctrl.languageList = [temp[1], temp[0]];
                }
                $translate.use(ctrl.currentLanguage).then(function (key) {
                    localStorage.setItem('lang', key);
                }, function (key) {
                    console.error("Something went wrong - 0xABBCEE");
                });

            }
        }
    }

    angular.module('app')
        .controller('AppController', appController);

})();