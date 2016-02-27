'use strict';

angular.module('travelappApp')
    .controller('BucketDetailController', function ($scope, $rootScope, $stateParams, entity, User, Bucket, Auth, Principal) {
        $scope.bucket = entity;
        $scope.achieve = $scope.bucket.achieve;
        $scope.people = $scope.bucket.people;
        $scope.experiences = $scope.bucket.experiences;
        $scope.useremail = '';
        $scope.currentUser = {};
        $scope.load = function (id) {
            Bucket.get({id: id}, function(result) {
                $scope.bucket = result;
            });
        };
        var unsubscribe = $rootScope.$on('travelappApp:bucketUpdate', function(event, result) {
            $scope.bucket = result;
        });
        $scope.$on('$destroy', unsubscribe);
        Principal.identity().then(function(account) {
            $scope.useremail = account.email;
        });
        $scope.userHasBucket = function(bucket){
        	if (bucket.people != null){
	        	var arrayLength = bucket.people.length;
	        	for (var i = 0; i < arrayLength; i++) {
	        	    if (bucket.people[i] === $scope.useremail){
	        	    	return true;
	        	    }
	        	}
        	}
        	return false;
        };
        $scope.getCurrentPerson = function(personId){
        	//TODO logic to fetch from server
        	//return User.get({login : personId});
        	$scope.currentUser.name="test";
        	$scope.currentUser.followCount="100";
        	$scope.currentUser.followingCount="100";
        	$scope.currentUser.imageUrl="http://placehold.it/600x400";
        	return true;
        	
        };
        
        $scope.saveExperience = function(){
        	//TODO save experience
        }
    });
