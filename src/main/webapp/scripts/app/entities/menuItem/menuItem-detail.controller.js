'use strict';

angular.module('restappApp')
    .controller('MenuItemDetailController', function ($scope, $rootScope, $stateParams, entity, MenuItem) {
        $scope.menuItem = entity;
        $scope.load = function (id) {
            MenuItem.get({id: id}, function(result) {
                $scope.menuItem = result;
            });
        };
        $rootScope.$on('restappApp:menuItemUpdate', function(event, result) {
            $scope.menuItem = result;
        });
    });
