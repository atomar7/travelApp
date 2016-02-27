'use strict';

angular.module('travelappApp').controller('BucketDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bucket',
        function($scope, $stateParams, $uibModalInstance, entity, Bucket) {

        $scope.bucket = entity;
        $scope.tempImageUrls = '';
        $scope.tempPeople = '';
        $scope.tempAchieve = '';
        $scope.tempExperiences = '';
        $scope.bucket.imageUrls = [];
        $scope.bucket.people = [];
        $scope.bucket.achieve = [];
        $scope.bucket.experiences = [];
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
        
        $scope.addUrl = function() {
        	$scope.bucket.imageUrls.push($scope.tempImageUrls);
        	$scope.tempImageUrls = '';
        };
        
        $scope.addPeople = function() {
        	$scope.bucket.people.push($scope.tempPeople);
        	$scope.tempPeople = '';
        };
        
        $scope.addAchieve = function() {
        	$scope.bucket.achieve.push($scope.tempAchieve);
        	$scope.tempAchieve = '';
        };
        
        $scope.addExperiences = function() {
        	$scope.bucket.experiences.push($scope.tempExperiences);
        	$scope.tempExperiences = '';
        };
}]);
