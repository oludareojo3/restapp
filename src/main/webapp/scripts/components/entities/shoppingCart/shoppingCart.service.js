'use strict';

angular.module('restappApp')
    .factory('ShoppingCart', function ($resource, DateUtils) {
        return $resource('api/shoppingCarts/:id', {}, {
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
