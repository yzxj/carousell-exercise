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
            $scope.topics = response.data;
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
            $scope.topics = response.data;
        }, function errorCallback(response) {
        });
    };

    // Run Initialization
    $scope.init();
}]);
