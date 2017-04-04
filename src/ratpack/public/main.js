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

    $scope.upvote = function(topicContent, topicVotes, topicTimeCreated) {
        $scope.vote('/api/upvote', topicContent, topicVotes, topicTimeCreated);
    };

    $scope.downvote = function(topicContent, topicVotes, topicTimeCreated) {
        $scope.vote('/api/downvote', topicContent, topicVotes, topicTimeCreated);
    };

    $scope.vote = function(voteUrl, topicContent, topicVotes, topicTimeCreated) {
        $http({
            method: 'POST',
            url: voteUrl,
            data: {
                content: topicContent,
                upvotes: topicVotes,
                timeCreated: topicTimeCreated
            }
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
