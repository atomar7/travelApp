'use strict';

angular.module('travelappApp')
	.controller('PeopleDeleteController', function($scope, $uibModalInstance, entity, People) {

        $scope.people = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            People.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
