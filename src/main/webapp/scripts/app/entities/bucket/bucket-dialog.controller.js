'use strict';

angular.module('travelappApp').controller('BucketDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bucket',
        function($scope, $stateParams, $uibModalInstance, entity, Bucket) {

        $scope.bucket = entity;
        $scope.load = function(id) {
            Bucket.get({id : id}, function(result) {
                $scope.bucket = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('travelappApp:bucketUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bucket.id != null) {
                Bucket.update($scope.bucket, onSaveSuccess, onSaveError);
            } else {
                Bucket.save($scope.bucket, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
