'use strict';

angular.module('travelappApp')
    .controller('BucketController', function ($scope, $state, Bucket, ParseLinks) {

        $scope.buckets = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            Bucket.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.buckets.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.buckets = [];
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
            $scope.bucket = {
                title: null,
                description: null,
                imageUrls: null,
                peopleCount: null,
                people: null,
                achievedCount: null,
                achieve: null,
                experiences: null,
                id: null
            };
        };
    });
