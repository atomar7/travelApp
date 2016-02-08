'use strict';

angular.module('travelappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


