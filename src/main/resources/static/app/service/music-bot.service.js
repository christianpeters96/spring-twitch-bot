(function () {

    function musicBotService ($http) {
        function defaultHeaders() {
            return {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            };
        }

        return {
            getSettings: function () {
                return $http.get('/api/music/settings', { headers: defaultHeaders() });
            },
            saveSettings: function (settings) {
                return $http.post('/api/music/settings', settings, { headers: defaultHeaders() });
            },
            getRequests: function () {
                return $http.get('/api/music/requests', { headers: defaultHeaders() });
            },
            getSongInfo: function (videoId) {
                return $http.get('/api/music/audio/' + videoId, { headers: defaultHeaders() });
            },
            removeRequestedSong: function (videoDatabaseId) {
                return $http.delete('/api/music/request/' + videoDatabaseId, { headers: defaultHeaders() });
            },
            getPlaylist: function () {
                return $http.get('/api/music/playlist', { headers: defaultHeaders() });
            },
            addSongToPlaylist: function (videoId) {
                return $http.put('/api/music/playlist/song/' + videoId, null, { headers: defaultHeaders() });
            },
            addPlaylistToPlaylist: function (playlistId) {
                return $http.put('/api/music/playlist/playlist/' + playlistId, null, { headers: defaultHeaders() });
            },
            removeSongFromPlaylist: function (videoDatabaseId) {
                return $http.delete('/api/music/playlist/' + videoDatabaseId, { headers: defaultHeaders() });
            }
        }
    }

    angular.module('app')
        .service('MusicBot', musicBotService);

})();