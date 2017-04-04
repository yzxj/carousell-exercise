var app = angular.module('didditApp', []);

app.controller('didditCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.topics = [];

    $scope.init = function() {
        $scope.getTopics();
    };

    $scope.getTopics = function() {
        $http({
            method: 'GET',
            url: '/api/topics'
        }).then(function successCallback(response) {
            // TODO: Check functionality.
            console.log(response.data.topics);
            $scope.topics = response.data.topics.sort(function(a,b){
                if (a.upvotes == b.upvotes) {
                    return b.timeCreated - a.timeCreated;
                }
                return b.upvotes - a.upvotes;
            });
        }, function errorCallback(response) {
        });
    };

    $scope.createTopic = function(topicContent) {
        $scope.postAndRefresh('/api/newtopic', {content: topicContent});
    };

    $scope.upvote = function(topicContent, topicVotes, topicTimeCreated) {
        var data = {
            content: topicContent,
            upvotes: topicVotes,
            timeCreated: topicTimeCreated
        };
        $scope.postAndRefresh('/api/upvote', data);
    };

    $scope.downvote = function(topicContent, topicVotes, topicTimeCreated) {
        var data = {
            content: topicContent,
            upvotes: topicVotes,
            timeCreated: topicTimeCreated
        };
        $scope.postAndRefresh('/api/downvote', data);
    };

    $scope.postAndRefresh = function(url, data) {
        $http({
            method: 'POST',
            url: url,
            data: data
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            // TODO: Refresh page OR fetch list again OR the api returns new list
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
    };

    // Run Initialization
    $scope.init();
}]);
