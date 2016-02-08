'use strict';

angular.module('travelappApp').controller('PeopleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'People',
        function($scope, $stateParams, $uibModalInstance, entity, People) {

        $scope.people = entity;
        $scope.load = function(id) {
            People.get({id : id}, function(result) {
                $scope.people = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('travelappApp:peopleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.people.id != null) {
                People.update($scope.people, onSaveSuccess, onSaveError);
            } else {
                People.save($scope.people, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
