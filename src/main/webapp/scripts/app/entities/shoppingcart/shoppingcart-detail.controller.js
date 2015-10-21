'use strict';

angular.module('restappApp')
    .controller('ShoppingcartDetailController', function ($scope, $rootScope, $stateParams, entity, Shoppingcart, MenuItem) {
        $scope.shoppingcart = entity;
        $scope.load = function (id) {
            Shoppingcart.get({id: id}, function(result) {
                $scope.shoppingcart = result;
            });
        };
        $rootScope.$on('restappApp:shoppingcartUpdate', function(event, result) {
            $scope.shoppingcart = result;
        });
    });
