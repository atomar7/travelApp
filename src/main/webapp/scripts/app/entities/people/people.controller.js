'use strict';

angular.module('travelappApp')
    .controller('PeopleController', function ($scope, $state, People, ParseLinks) {

        $scope.peoples = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            People.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.peoples.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.peoples = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.people = {
                followersCount: null,
                followingCount: null,
                bucketCount: null,
                bucketAchievedCount: null,
                followers: null,
                following: null,
                buckets: null,
                bucketAchieved: null,
                rating: null,
                userId: null,
                blogsCount: null,
                blogs: null,
                imageUrl: null,
                id: null
            };
        };
    });
