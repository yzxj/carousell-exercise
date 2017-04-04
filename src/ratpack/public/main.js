var app = angular.module('myApp', []);

app.controller('myCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.topics = [];

    $scope.getTopics = function() {
        $http({
            method: 'GET',
            url: '/api/topics'
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.topics = response.data.topics;
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
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
    }
}]);
