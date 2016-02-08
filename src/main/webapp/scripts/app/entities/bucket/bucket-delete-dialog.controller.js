'use strict';

angular.module('travelappApp')
	.controller('BucketDeleteController', function($scope, $uibModalInstance, entity, Bucket) {

        $scope.bucket = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bucket.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
