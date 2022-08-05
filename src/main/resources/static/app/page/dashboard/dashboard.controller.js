(function() {

    function dashboardController($rootScope, $scope, $interval, $translate, TwitchBot, ChannelEvent) {
        var ctrl = this;
        ctrl.topChatter = "-";
        ctrl.topChatterMessageCount = 0;
        ctrl.topCommand = "-";
        ctrl.topCommandCallCount = 0;
        ctrl.stats = {
            messages: {
                today: 0,
                plus: 0,
                percent: 0,
                string: '',
                lastString: ''
            },
            commands: {
                today: 0,
                plus: 0,
                percent: 0,
                string: '',
                lastString: ''
            },
            deleted: {
                today: 0,
                plus: 0,
                percent: 0,
                string: '',
                lastString: ''
            },
            timeouts: {
                today: 0,
                plus: 0,
                percent: 0,
                string: '',
                lastString: ''
            }
        };
        ctrl.events = [];
        ctrl.modActions = [];
        ctrl.follows = [];
        ctrl.page = 0;
        ctrl.perPage = 5;

        $rootScope.$on('$translateChangeSuccess', function () {
            ctrl.updateEvents();
        });

        let iv_updateWeeklyGraph = null;
        let iv_updateChannelEvents = null;
        ctrl.$onInit = function () {
            ctrl.updateWeeklyGraph();
            iv_updateWeeklyGraph = $interval(ctrl.updateWeeklyGraph, 5000);
            TwitchBot.getTopData().then(function (res) {
                if (res.status === 200 && res.data) {
                    if (res.data.hasOwnProperty("users") && res.data.users.length) {
                        ctrl.topChatter = res.data.users[0].username;
                        ctrl.topChatterMessageCount = res.data.users[0].messageCount;
                    }
                    if (res.data.hasOwnProperty("commands") && res.data.commands.length) {
                        ctrl.topCommand = "!" + res.data.commands[0].cmd;
                        ctrl.topCommandCallCount = res.data.commands[0].callCount;
                    }
                }
            });

            ctrl.updateEvents();
            ctrl.updateFollowers();
        }

        function dateToString(dateString) {
            let output = "-";
            if (dateString.length > 0) {
                let date = new Date(dateString);
                output = (date.getUTCDate()+"").replace(/^(\d)$/,"0$1");
                output += "." + ((date.getUTCMonth()+1)+"").replace(/^(\d)$/,"0$1");
                output += "." + date.getUTCFullYear();
                output += " - " + (date.getHours()+"").replace(/^(\d)$/,"0$1");
                output += ":" + (date.getMinutes()+"").replace(/^(\d)$/,"0$1");
                output += ":" + (date.getSeconds()+"").replace(/^(\d)$/,"0$1");
            }
            return output;
        }

        $scope.$on("$destroy", function () {
            $interval.cancel(iv_updateWeeklyGraph);
            $interval.cancel(iv_updateChannelEvents);
        });

        ctrl.updateWeeklyGraph = function () {
            TwitchBot.getWeeklyStats().then(function (res) {
                var data = res.data.reverse();

                ctrl.stats.messages.today = data[6].messageCount;
                ctrl.stats.messages.plus = formatNumber (data[6].messageCount - data[5].messageCount, 0, 0, true);
                ctrl.stats.messages.percent = formatNumber ((100 / data[5].messageCount * (data[6].messageCount - data[5].messageCount)), data[6].messageCount * 100, 2, true);
                ctrl.stats.messages.string = ctrl.stats.messages.today + "|" + ctrl.stats.messages.plus + ctrl.stats.messages.percent;

                ctrl.stats.commands.today = data[6].commandCount;
                ctrl.stats.commands.plus = formatNumber (data[6].commandCount - data[5].commandCount, 0, 0, true);
                ctrl.stats.commands.percent = formatNumber ((100 / data[5].commandCount * (data[6].commandCount - data[5].commandCount)), data[6].commandCount * 100, 2, true);
                ctrl.stats.commands.string = ctrl.stats.commands.today + "|" + ctrl.stats.commands.plus + ctrl.stats.commands.percent;

                ctrl.stats.deleted.today = data[6].deletedMessageCount;
                ctrl.stats.deleted.plus = formatNumber (data[6].deletedMessageCount - data[5].deletedMessageCount, 0, 0, true);
                ctrl.stats.deleted.percent = formatNumber ((100 / data[5].deletedMessageCount * (data[6].deletedMessageCount - data[5].deletedMessageCount)), data[6].deletedMessageCount * 100, 2, true);
                ctrl.stats.deleted.string = ctrl.stats.deleted.today + "|" + ctrl.stats.deleted.plus + ctrl.stats.deleted.percent;

                ctrl.stats.timeouts.today = data[6].timeoutCount;
                ctrl.stats.timeouts.plus = formatNumber (data[6].timeoutCount - data[5].timeoutCount, 0, 0, true);
                ctrl.stats.timeouts.percent = formatNumber ((100 / data[5].timeoutCount * (data[6].timeoutCount - data[5].timeoutCount)), data[6].timeoutCount * 100, 2, true);
                ctrl.stats.timeouts.string = ctrl.stats.timeouts.today + "|" + ctrl.stats.timeouts.plus + ctrl.stats.timeouts.percent;

                if (ctrl.stats.messages.string !== ctrl.stats.messages.lastString) {
                    generateLineChart('#stats-line-graph-1', data, 'messageCount');
                    ctrl.stats.messages.lastString = ctrl.stats.messages.string;
                }

                if (ctrl.stats.commands.string !== ctrl.stats.commands.lastString) {
                    generateLineChart('#stats-line-graph-2', data, 'commandCount');
                    ctrl.stats.commands.lastString = ctrl.stats.commands.string;
                }

                if (ctrl.stats.deleted.string !== ctrl.stats.deleted.lastString) {
                    generateLineChart('#stats-line-graph-3', data, 'deletedMessageCount');
                    ctrl.stats.deleted.lastString = ctrl.stats.deleted.string;
                }

                if (ctrl.stats.timeouts.string !== ctrl.stats.timeouts.lastString) {
                    generateLineChart('#stats-line-graph-4', data, 'timeoutCount');
                    ctrl.stats.timeouts.lastString = ctrl.stats.timeouts.string;
                }
            });
        };

        ctrl.updateEvents = function () {
            ChannelEvent.getList().then(function (res) {
                ctrl.events = [];
                ctrl.modActions = [];
                for (let event of res.data) {
                    if (event.topic === "channel-subscribe-events-v1") {
                        let subLevel = $translate.instant('page.dashboard.eventMessage.sub_level_' + event.data.sub_plan);
                        event.action = $translate.instant('page.dashboard.eventMessage.subscription').replace(/%arg1%/, subLevel);
                        event.by = event.data.display_name;
                        if (event.data.sub_message.message.length > 0) {
                            event.action += $translate.instant('page.dashboard.eventMessage.subscriptionArg').replace(/%arg1%/, event.data.sub_message.message);
                        }
                        event.ts = dateToString(event.data.time);
                        ctrl.events.push(event);
                    }
                    if (event.topic === "channel-bits-events-v2") {
                        event.action = $translate.instant('page.dashboard.eventMessage.cheer').replace(/%arg1%/, event.data.data.bits_used);
                        event.by = event.data.data.user_name;
                        if (event.data.data.chat_message.length > 0) {
                            event.action += $translate.instant('page.dashboard.eventMessage.cheerArg').replace(/%arg1%/, event.data.data.chat_message);
                        }
                        event.ts = dateToString(event.data.data.time);

                        ctrl.events.push(event);
                    }
                    if (event.topic === "chat_moderator_actions") {
                        let modAction = event.data.moderation_action;
                        let tEventKey = "mod" + modAction.charAt(0).toUpperCase() + modAction.slice(1);
                        event.action = $translate.instant('page.dashboard.eventMessage.' + tEventKey);
                        if (event.data.moderation_action === "followers") {
                            if (Number(event.data.args[0]) > 0) {
                                event.action += $translate.instant('page.dashboard.eventMessage.modFollowersArg').replace(/%arg1%/, event.data.args[0]);
                            }
                        }
                        if (event.data.args !== null) {
                            for (let idx in event.data.args) {
                                event.action = event.action.replace(new RegExp("%arg" + (Number(idx) + 1) + "%"), event.data.args[idx]);
                            }
                        }
                        event.by = event.data.created_by;
                        event.ts = dateToString(event.data.created_at);

                        ctrl.modActions.push(event);
                    }
                }
            });
        };

        ctrl.updateFollowers = function () {
            ChannelEvent.getFollowers().then(function (res) {
                ctrl.follows = [];
                for (let f of res.data) {
                    ctrl.follows.push({
                        by: f.from,
                        ts: dateToString(f.dateTime)
                    });
                }
            });
        };

        function formatNumber (value, alt, fixed, displaySign) {
            if (typeof displaySign !== "boolean") displaySign = false;
            if (value >= Infinity || isNaN(value)) {
                value = alt;
                if (isNaN(value)) {
                    value = 0;
                }
            }
            value = value.toFixed(fixed);
            if (displaySign) {
                value = ((value >= 0 ? "+" : "") + value).toString();
            }
            return value;
        }

        function generateLineChart(elem, array, key) {
            var lineChartCanvas = $(elem).get(0).getContext("2d");
            var gradientStrokeFill_1 = lineChartCanvas.createLinearGradient(0, 0, 0, 50);
            gradientStrokeFill_1.addColorStop(0, 'rgba(131, 144, 255, 0.5)');
            gradientStrokeFill_1.addColorStop(1, '#fff');
            new Chart(lineChartCanvas, {
                type: 'line',
                data: {
                    labels: ["Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"],
                    datasets: [{
                        label: 'Anzahl',
                        data: [
                            array[0][key],
                            array[1][key],
                            array[2][key],
                            array[3][key],
                            array[4][key],
                            array[5][key],
                            array[6][key]
                        ],
                        borderColor: '#6d7cfc',
                        backgroundColor: gradientStrokeFill_1,
                        borderWidth: 3,
                        fill: true
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            display: false
                        }],
                        xAxes: [{
                            display: false
                        }]
                    },
                    legend: {
                        display: false
                    },
                    elements: {
                        point: {
                            radius: 0
                        },
                        line: {
                            tension: 0
                        }
                    },
                    stepsize: 100
                }
            });
        }
    }

    angular.module('app')
        .controller('DashboardController', dashboardController);

})();