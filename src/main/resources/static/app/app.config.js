(function () {

    function appConfig($httpProvider, $urlRouterProvider, $locationProvider, $stateProvider, $translateProvider) {
        $httpProvider.interceptors.push(httpInterceptor);
        $locationProvider.hashPrefix('');
        $urlRouterProvider.otherwise('/dashboard');

        $stateProvider
            .state({
                name: 'login',
                url: '/login',
                views: {
                    'page': {
                        templateUrl: 'app/tpl/login.html'
                    }
                }
            })
            .state({
                name: 'e500',
                url: '/500',
                views: {
                    'page': {
                        templateUrl: 'app/page/error/500.html'
                    }
                }
            })
            .state({
                name: 'app',
                abstract: true,
                views: {
                    'page': {
                        templateUrl: 'app/tpl/main.html'
                    }
                }
            })
            .state({
                parent: 'app',
                name: 'dashboard',
                url: '/dashboard',
                templateUrl: 'app/page/dashboard/dashboard.html',
                controller: 'DashboardController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'commands',
                url: '/commands',
                templateUrl: 'app/page/commands/commands.html',
                controller: 'CommandController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'command_create',
                url: '/commands/create',
                templateUrl: 'app/page/commands/command-view.html',
                controller: 'CommandCreateController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'command_edit', // <a ui-sref="command_edit({commandId: '0'})">
                url: '/commands/:commandId/edit',
                templateUrl: 'app/page/commands/command-view.html',
                controller: 'CommandEditController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'music_bot_playlist', // <a ui-sref="command_edit({commandId: '0'})">
                url: '/music-bot/playlist',
                templateUrl: 'app/page/music-bot/playlist/playlist.html',
                controller: 'MusicBotPlaylistController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'music_bot_player', // <a ui-sref="command_edit({commandId: '0'})">
                url: '/music-bot/player',
                templateUrl: 'app/page/music-bot/player/player.html',
                controller: 'MusicBotPlayerController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'music_bot_settings', // <a ui-sref="command_edit({commandId: '0'})">
                url: '/music-bot/settings',
                templateUrl: 'app/page/music-bot/settings/settings.html',
                controller: 'MusicBotSettingsController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'security_spam',
                url: '/security/spam',
                templateUrl: 'app/page/security/spam/spam.html',
                controller: 'SecuritySpamController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'user_auth',
                url: '/user/settings/auth',
                templateUrl: 'app/page/user/settings/auth/auth.html',
                controller: 'UserSettingsController',
                controllerAs: '$ctrl'
            })
            .state({
                parent: 'app',
                name: 'logs',
                url: '/logs',
                template: 'Logs'
            });

        $translateProvider.useStaticFilesLoader({
            prefix: 'i18n/',
            suffix: '.json'
        });

        $translateProvider.preferredLanguage('de');
    }

    // TODO: test & fix(maybe?)
    const httpInterceptor = function ($injector, $rootScope) {
        return {
            request: function (config) {
                return config;
            },
            response: function (res) {
                if (typeof res.data === "object" && res.data.hasOwnProperty("notification")) {
                    let notification = res.data.notification;
                    if (notification !== null && typeof notification === "object" && notification.hasOwnProperty("msg") && typeof notification.msg === "string" && notification.msg.length > 0) {
                        let msg = notification.msg;

                        let type = "error";
                        let title = "Error";
                        if (notification.hasOwnProperty("type")) {
                            type = notification.type;
                            title = type.charAt(0).toUpperCase() + type.slice(1);
                        }
                        if (notification.hasOwnProperty("title")) {
                            title = notification.title;
                        }

                        let sendToast = true;
                        let key = type + ":" + title + ":" + msg;
                        if (localStorage.getItem('toast_memory') === null) {
                            let memory = [{key:key,ts:Date.now()+500}];
                            localStorage.setItem('toast_memory', JSON.stringify(memory));
                        }
                        else {
                            let memory = JSON.parse(localStorage.getItem('toast_memory'));
                            for (let idx in memory) {
                                if (memory.hasOwnProperty(idx)) {
                                    if (memory[idx].key === key) {
                                        sendToast = false;
                                        if (Date.now() >= memory[idx].ts) {
                                            sendToast = true;
                                            memory[idx].ts = Date.now()+500;
                                        }
                                        break;
                                    }
                                }
                            }
                            localStorage.setItem('toast_memory', JSON.stringify(memory));
                        }

                        if (sendToast) {
                            $rootScope.$emit('toastr', {
                                type: type,
                                title: title,
                                msg: msg
                            });
                        }
                    }
                }
                return res;
            },
            responseError: function (res) {
                if (res.status === 500) {
                    $rootScope.$emit('toastr', {
                        type: "error",
                        title: "Error",
                        msg: "Something went wrong (500)"
                    });
                }
                return res;
            }
        }
    }

    angular.module('app')
        .config(appConfig);

})();