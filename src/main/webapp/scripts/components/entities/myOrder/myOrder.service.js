'use strict';

angular.module('restappApp')
    .factory('MyOrder', function ($resource, DateUtils) {
        return $resource('api/myOrders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderDate = DateUtils.convertDateTimeFromServer(data.orderDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
