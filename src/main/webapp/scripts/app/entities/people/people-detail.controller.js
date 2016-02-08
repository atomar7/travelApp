'use strict';

angular.module('travelappApp')
    .controller('PeopleDetailController', function ($scope, $rootScope, $stateParams, entity, People) {
        $scope.people = entity;
        $scope.load = function (id) {
            People.get({id: id}, function(result) {
                $scope.people = result;
            });
        };
        var unsubscribe = $rootScope.$on('travelappApp:peopleUpdate', function(event, result) {
            $scope.people = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
