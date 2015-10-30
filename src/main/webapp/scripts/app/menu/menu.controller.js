angular.module('restappApp')
    .controller('MenuController', function($scope, MenuItem, ShoppingCartCache) {

        $scope.loadAll = function() {
            MenuItem.query(function(result) {
                $scope.menuItems = result;
            });
        };

        $scope.loadAll();

        $scope.addToCart = function(item) {
            var cart = ShoppingCartCache.get('CART') ? ShoppingCartCache.get('CART') : [];

            cart.push(item);

            ShoppingCartCache.put('CART', cart);

            console.log(JSON.stringify(ShoppingCartCache.get('CART')));
        };

    });
