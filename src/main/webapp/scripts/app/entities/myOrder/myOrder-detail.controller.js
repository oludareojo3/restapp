'use strict';

angular.module('restappApp')
    .controller('MyOrderDetailController', function ($scope, $rootScope, $stateParams, entity, MyOrder, MyOrderDetails) {
        $scope.myOrder = entity;
        $scope.load = function (id) {
            MyOrder.get({id: id}, function(result) {
                $scope.myOrder = result;
            });
        };
        $rootScope.$on('restappApp:myOrderUpdate', function(event, result) {
            $scope.myOrder = result;
        });
    });
