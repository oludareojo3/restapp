'use strict';

angular.module('restappApp')
    .factory('MyOrderDetails', function ($resource, DateUtils) {
        return $resource('api/myOrderDetailss/:id', {}, {
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
