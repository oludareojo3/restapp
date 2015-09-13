angular.module('restappApp')
    .controller('MenuController', function($scope, MenuItemFactory) {

        MenuItemFactory.menuItems(function(result) {
            $scope.menuItems = result;
        });

    });
