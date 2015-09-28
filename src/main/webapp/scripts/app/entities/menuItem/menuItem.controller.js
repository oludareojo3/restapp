'use strict';

angular.module('restappApp')
    .controller('MenuItemController', function ($scope, MenuItem) {
        $scope.menuItems = [];
        $scope.loadAll = function() {
            MenuItem.query(function(result) {
               $scope.menuItems = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MenuItem.get({id: id}, function(result) {
                $scope.menuItem = result;
                $('#deleteMenuItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MenuItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMenuItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.menuItem = {title: null, description: null, cost: null, calories: null, foodType: null, id: null};
        };
    });
