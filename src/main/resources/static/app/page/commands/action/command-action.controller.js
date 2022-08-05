(function () {

    function commandActionController ($scope, $timeout) {
        let ctrl = this;

        ctrl.template = 'chatInput';
        ctrl.templates = {
            chatInput: {
                args: 0,
                params: [
                    {
                        name: 'text',
                        type: 'text',
                        labelKey: 'message',
                        value: ''
                    }
                ],
                actions: [
                    'say %text%'
                ]
            },
            songRequest: {
                args: 1,
                params: [
                    {
                        name: '_success',
                        type: 'text',
                        labelKey: 'success_message',
                        value: '/me \'${song.name}\' von ${song.artist} wurde der Warteschlange hinzugefügt [@${user}]'
                    },
                    {
                        name: '_error',
                        type: 'text',
                        labelKey: 'error_message',
                        value: '/me Es sind schlimme Dinge passiert. Dein gewünschter Song konnte nicht hinzugefügt werden [@${user}]'
                    },
                    {
                        name: '_paused',
                        type: 'text',
                        labelKey: 'pause_message',
                        value: '/me Da momentan keine Musik abgespielt wird, können keine Songs requested werden [@${user}]'
                    }
                ],
                actions: [
                    'set spotify.success %_success%',
                    'set spotify.error %_error%',
                    'set spotify.paused %_paused%',
                    'spotify.request ${1}',
                    'say ${response}'
                ]
            },
            currentSong: {
                args: 0,
                params: [
                    {
                        name: '_success',
                        type: 'text',
                        labelKey: 'success_message',
                        value: '/me Aktuell wird \'${song.name}\' von ${song.artist} gespielt'
                    },
                    {
                        name: '_paused',
                        type: 'text',
                        labelKey: 'pause_message',
                        value: '/me Es wird aktuell kein Song abgespielt'
                    }
                ],
                actions: [
                    'set spotify.success %_success%',
                    'set spotify.paused %_paused%',
                    'spotify.get',
                    'say ${response}'
                ]
            },
            youtubeSongRequest: {
                args: 1,
                params: [],
                actions: [
                    'yt.request ${1}',
                    'say ${response}'
                ]
            },
            raw: {
                args: 0,
                params: [
                    {
                        type: 'number',
                        labelKey: 'argc',
                        value: '0'
                    },
                    {
                        type: 'textarea',
                        labelKey: 'raw',
                        value: ''
                    }
                ],
                actions: []
            }
        }

        ctrl.onInput = function () {
            ctrl.updateActions();
        }

        ctrl.onTemplateChange = function () {
            if (ctrl.template !== 'raw') {
                ctrl.updateActions();
            }
            else {
                let count = 0;
                ctrl.templates['raw'].params[0].value = $scope.args;
                for (let m of $scope.ngModel) {
                    if (count++ === 0) ctrl.templates['raw'].params[1].value = m;
                    else ctrl.templates['raw'].params[1].value += "\n" + m;
                }
                ctrl.updateActions();
            }
        }

        ctrl.$onInit = function () {
            ctrl.fixModel();
            if ($scope.ngModel.length !== 0) {
                if ($scope.ngModel.length === 1
                        && $scope.args === 0
                        && $scope.ngModel[0].indexOf("say ") === 0) {

                    ctrl.template = 'chatInput';
                    ctrl.templates['chatInput'].params[0].value = $scope.ngModel[0].replace(/^say /, '');
                }
                else if ($scope.ngModel.length === 4
                        && $scope.args === 0
                        && $scope.ngModel[0].indexOf("set spotify.success ") === 0
                        && $scope.ngModel[1].indexOf("set spotify.paused ") === 0
                        && $scope.ngModel[2].indexOf("spotify.get") === 0
                        && $scope.ngModel[3].indexOf("say ${response}") === 0) {

                    ctrl.template = 'currentSong';
                    ctrl.templates['currentSong'].params[0].value = $scope.ngModel[0].replace(/^set spotify.success /, '');
                    ctrl.templates['currentSong'].params[1].value = $scope.ngModel[1].replace(/^set spotify.paused /, '');
                }
                else if ($scope.ngModel.length === 5
                        && $scope.args === 1
                        && $scope.ngModel[0].indexOf("set spotify.success ") === 0
                        && $scope.ngModel[1].indexOf("set spotify.error ") === 0
                        && $scope.ngModel[2].indexOf("set spotify.paused ") === 0
                        && $scope.ngModel[3].indexOf("spotify.request ${1}") === 0
                        && $scope.ngModel[4].indexOf("say ${response}") === 0) {

                    ctrl.template = 'songRequest';
                    ctrl.templates['songRequest'].params[0].value = $scope.ngModel[0].replace(/^set spotify\.success /, '');
                    ctrl.templates['songRequest'].params[1].value = $scope.ngModel[1].replace(/^set spotify\.error /, '');
                    ctrl.templates['songRequest'].params[2].value = $scope.ngModel[2].replace(/^set spotify\.paused /, '');
                }
                else if ($scope.ngModel.length === 2
                        && $scope.args === 1
                        && $scope.ngModel[0].indexOf("yt.request ${1}") === 0
                        && $scope.ngModel[1].indexOf("say ${response}") === 0) {
                    
                    ctrl.template = 'youtubeSongRequest';
                }
                else {
                    ctrl.template = 'raw';
                    ctrl.templates['raw'].params[0].value = $scope.args;
                    let count = 0;
                    for (let m of $scope.ngModel) {
                        if (count++ === 0) ctrl.templates['raw'].params[1].value = m;
                        else ctrl.templates['raw'].params[1].value += "\n" + m;
                    }
                }
            }
        }

        $scope.$watch(function () {
            ctrl.fixModel();
        });

        ctrl.fixModel = function () {
            let c = 0;
            for (let m of $scope.ngModel) {
                if (typeof m !== "string" && m.hasOwnProperty("action")) c++;
            }
            if (c !== 0) {
                let nl = [];
                for (let m of $scope.ngModel) {
                    if (typeof m !== "string" && m.hasOwnProperty("action")) {
                        nl.push(m.action);
                    }
                    else nl.push(m);
                }
                $scope.ngModel = nl;
                // fixed (loaded)

            }
        }

        ctrl.updateActions = function () {
            if (ctrl.template !== "raw") {
                $scope.ngModel = angular.copy(ctrl.templates[ctrl.template].actions);
                $scope.args = angular.copy(ctrl.templates[ctrl.template].args);
                for (let idx in $scope.ngModel) {
                    if ($scope.ngModel.hasOwnProperty(idx)) {
                        let re = new RegExp("%([A-z0-9]+)%", "gm");
                        if (re.test($scope.ngModel[idx])) {
                            $scope.ngModel[idx] = $scope.ngModel[idx].replace(re, function (ignored, _var) {
                                for (let param of ctrl.templates[ctrl.template].params) {
                                    if (param.name === _var) {
                                        return param.value;
                                    }
                                }
                                return '';
                            });
                        }
                    }
                }
            }
            else {
                $scope.ngModel = [];
                $scope.args = Number(ctrl.templates.raw.params[0].value) || 0;
                let actions = [];
                let lines = ctrl.templates.raw.params[1].value.split("\n");
                for (let line of lines) {
                    if (line.trim().length !== 0) {
                        actions.push(line.trim());
                    }
                }
                $scope.ngModel = actions;
            }
        }
    }

    angular.module('app')
        .controller('CommandActionController', commandActionController);

})();