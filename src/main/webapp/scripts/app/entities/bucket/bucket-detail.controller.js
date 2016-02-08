'use strict';

angular.module('travelappApp')
    .controller('BucketDetailController', function ($scope, $rootScope, $stateParams, entity, Bucket) {
        $scope.bucket = entity;
        $scope.load = function (id) {
            Bucket.get({id: id}, function(result) {
                $scope.bucket = result;
            });
        };
        var unsubscribe = $rootScope.$on('travelappApp:bucketUpdate', function(event, result) {
            $scope.bucket = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
