(function () {

    const colorThemes = {
        dark: {
            msgBackground: {
                backgroundColor: 'rgba(0, 0, 0, 0.5)'
            },
            msgText: {
                color: 'white',
                textShadow: '0 0 10px rgba(150, 150, 150, 1)'
            }
        },
        light: {
            msgBackground: {
                backgroundColor: 'rgba(255, 255, 255, 0.4)'
            },
            msgText: {
                color: 'black',
                textShadow: '0 0 10px rgba(50, 50, 50, 0.5)'
            }
        }
    };
    colorThemes.default = colorThemes.dark;
    colorThemes.tdark = angular.copy(colorThemes.dark);
    colorThemes.tdark.msgBackground.backgroundColor = "transparent";
    colorThemes.tlight = angular.copy(colorThemes.light);
    colorThemes.tlight.msgBackground.backgroundColor = "transparent";

    function chatController(TwitchBot, $http, $interval) {
        let ctrl = this;        
        const urlParams = new URLSearchParams(window.location.search);

        /* URL-Params Settings */
        let channelName = urlParams.get('channel');

        let selectedTheme = urlParams.get('theme') || "default";
        if (!colorThemes.hasOwnProperty(selectedTheme)) selectedTheme = 'default';
        ctrl.style = colorThemes[selectedTheme];
        ctrl.style.msgBackground.display = "none";

        let msgFontSize = urlParams.get('fontSize') || "16";
        if (!/^\d+$/.test(msgFontSize)) msgFontSize = "16";
        ctrl.style.msgText.fontSize = parseInt(msgFontSize) + "px";

        let msgEffect = urlParams.get('effect') || "none";
        if (!/^(?:slide|none)$/.test(msgEffect)) msgEffect = "none";
        ctrl.style.effect = msgEffect;

        let hideTimer = urlParams.get('timeout') || "0";
        if (!/^\d+$/.test(hideTimer)) hideTimer = "0";
        hideTimer = parseInt(hideTimer);        

        ctrl.style.chat = {
            width: "400px",
            position: "absolute",
            bottom: "0"
        }

        ctrl.style.msgAuthor = {};

        let alignment = urlParams.get('alignment') || "default";
        if (!/^(?:inline|default)$/.test(alignment)) alignment = "default";
        ctrl.style.alignment = alignment;

        if (ctrl.style.alignment === "inline") {
            ctrl.style.chat.width = "800px";

            ctrl.style.msgAuthor.width = "180px";
            ctrl.style.msgAuthor.float = "left";
            ctrl.style.msgAuthor.textAlign = "right";
            ctrl.style.msgAuthor.paddingRight = "5px";
            ctrl.style.msgAuthor.overflow = "hidden";
            ctrl.style.msgAuthor.whiteSpace = "nowrap";
            ctrl.style.msgAuthor.textOverflow = "ellipsis";
            ctrl.style.msgAuthor.color = ctrl.style.msgText.color;

            ctrl.style.msgText.width = "250px";
            ctrl.style.msgText.float = "left";
        }

        const twitchEmoteUrl = "https://static-cdn.jtvnw.net/emoticons/v2/%ID%/default/light/1.0";
        const bttvEmoteUrl = "https://cdn.betterttv.net/emote/%ID%/1x";
        let bttvAddedChannel = false;
        let bttvUrls = [
            { url:"https://api.betterttv.net/3/cached/emotes/global", loaded: false, loading: false }
        ];
        let bttvEmotes = [];


        ctrl.globalBadges = {};
        ctrl.messages = [];

        let iv_updateChat = null;
        let iv_showMessage = null;
        ctrl.$onInit = function () {
            $http.get('https://badges.twitch.tv/v1/badges/global/display').then(function (res) {
                if (res.status === 200) {
                    ctrl.globalBadges = res.data['badge_sets'];

                    ctrl.updateChat();
                    iv_updateChat = $interval(ctrl.updateChat, 500);
                    iv_showMessage = $interval(function () {
                        $('.chat-message').each(function () {
                            let el = this;
                            let timedout = (hideTimer > 0) && (Date.now() - parseInt($(el).data("ts")) >= hideTimer);

                            if ($(el).css('display') === "none" && !timedout) {
                                //$(el).hide().show("slide", { direction: "left" }, 1500);
                                if (ctrl.style.effect === "slide") {
                                    $(el).css({"opacity":"0","margin-left":"50px"}).show().animate({marginLeft:0,opacity:1}, 500);
                                }
                                else {
                                    $(el).show();
                                }
                            }
                            if ($(el).css('display') !== "none" && timedout) {
                                $(el).fadeOut();
                            }
                            //console.log($(el).data("ts"));
                        });

                    }, 100);
                }
            });
        }

        ctrl.$onDestroy = function () {
            $interval.cancel(iv_updateChat);
            $interval.cancel(iv_showMessage);
        }

        let latestMessageTime = 0;
        ctrl.updateChat = function () {
            for (let bttvIdx = 0; bttvIdx < bttvUrls.length; bttvIdx++) {
                if (!bttvUrls[bttvIdx].loaded && !bttvUrls[bttvIdx].loading) {
                    bttvUrls[bttvIdx].loading = true;
                    $http.get(bttvUrls[bttvIdx].url).then(function (res) {
                        if (res.data.hasOwnProperty("channelEmotes")) {
                            for (let data of res.data['channelEmotes']) {
                                bttvEmotes.push({ code: data.code, id: data.id });
                            }
                        }
                        if (res.data.hasOwnProperty("sharedEmotes")) {
                            for (let data of res.data['sharedEmotes']) {
                                bttvEmotes.push({ code: data.code, id: data.id });
                            }
                        }
                        if (Array.isArray(res.data)) {
                            for (let data of res.data) {
                                bttvEmotes.push({ code: data.code, id: data.id });
                            }
                        }
                        bttvUrls[bttvIdx].loaded = true;
                    });
                }
            }

            function bttvReplaceEmotes(message) {
                for (let emote of bttvEmotes) {
                    let emoteImgCode = "<img src='" + bttvEmoteUrl.replace(/%ID%/, emote.id) + "' />";
                    // needs double replacement because regex messes up with the whitespaces with only one replacement operation
                    for (let r=0;r<2;r++)
                        message = message.replace(new RegExp("(^|\\s)"+emote.code+"(\\s|$)", "g"), "$1" + emoteImgCode + "$2");
                }
                return message;
            }

            let newList = [];
            TwitchBot.getLatestChat(channelName).then(function (res) {
                if (res.status === 200) {
                    let latestMessage = res.data[res.data.length - 1];
                    if (Number(latestMessage['ts'].escapedValue) !== latestMessageTime) {
                        latestMessageTime = Number(latestMessage['ts'].escapedValue);
                        if (res.data.length !== 0) {
                            for (let data of res.data) {
                                let message = data['message'];
                                let author = data['author'];
                                let color = data['color'];
                                let emotes = data['emotes'];
                                let badges = data['badges'];
                                let ts = Number(data['ts'].escapedValue);
                                let roomId = Number(data['room_id'].escapedValue);
    
                                if (!bttvAddedChannel) {
                                    bttvUrls.push({ url: "https://api.betterttv.net/3/cached/users/twitch/" + roomId, loaded: false, loading: false })
                                    bttvAddedChannel = true;
                                }
    
                                if (color.hasOwnProperty('escapedValue') && color.hasOwnProperty('clientOnly')) {
                                    if (!color.clientOnly) {
                                        color = color.escapedValue;
                                    }
                                }
                                if (typeof color !== 'string') color = 'black';
    
                                if (badges.length > 0) {
                                    for (let i = 0; i < badges.length; i++) {
                                        badges[i].url = ctrl.globalBadges[badges[i].name]['versions'][badges[i].version]['image_url_1x'];
                                    }
                                }
    
                                if (emotes.length !== 0) {
                                    if (emotes.length > 1) {
                                        emotes = emotes.sort(function (a, b) {
                                            return a.firstIndex > b.firstIndex ? -1 : 1;
                                        });
                                    }
                                    for (let i = 0; i < emotes.length; i++) {
                                        let startStr = message.substring(0, emotes[i].firstIndex);
                                        let endStr = message.substring(++emotes[i].lastIndex, message.length)
                                        message = startStr + "<img src='" + twitchEmoteUrl.replace(/%ID%/, emotes[i].id) + "' />" + endStr;
                                    }
                                }
    
                                message = bttvReplaceEmotes(message);
    
                                newList.push({message, author, color, ts, emotes, badges});
                            }
                        }
                        ctrl.messages = newList;
                    }
                }
            });
        }
    }

    angular.module('app').controller('ChatController', chatController);

})();