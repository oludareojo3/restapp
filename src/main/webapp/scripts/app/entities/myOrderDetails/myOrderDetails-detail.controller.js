'use strict';

angular.module('restappApp')
    .controller('MyOrderDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, MyOrderDetails, MyOrder) {
        $scope.myOrderDetails = entity;
        $scope.load = function (id) {
            MyOrderDetails.get({id: id}, function(result) {
                $scope.myOrderDetails = result;
            });
        };
        $rootScope.$on('restappApp:myOrderDetailsUpdate', function(event, result) {
            $scope.myOrderDetails = result;
        });
    });
