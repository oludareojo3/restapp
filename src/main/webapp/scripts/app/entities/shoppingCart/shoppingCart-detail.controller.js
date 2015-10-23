'use strict';

angular.module('restappApp')
    .controller('ShoppingCartDetailController', function ($scope, $rootScope, $stateParams, entity, ShoppingCart, MyOrder) {
        $scope.shoppingCart = entity;
        $scope.load = function (id) {
            ShoppingCart.get({id: id}, function(result) {
                $scope.shoppingCart = result;
            });
        };
        $rootScope.$on('restappApp:shoppingCartUpdate', function(event, result) {
            $scope.shoppingCart = result;
        });
    });
