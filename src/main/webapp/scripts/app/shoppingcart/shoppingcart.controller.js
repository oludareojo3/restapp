angular.module('restappApp')
    .controller('ShoppingCartController', function($scope, ShoppingCartFactory) {

        ShoppingCartFactory.menuItems(function(result) {
            $scope.menuItems = result;
        });

    });
