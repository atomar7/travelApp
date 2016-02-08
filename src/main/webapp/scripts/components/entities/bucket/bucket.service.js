'use strict';

angular.module('travelappApp')
    .factory('Bucket', function ($resource, DateUtils) {
        return $resource('api/buckets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
