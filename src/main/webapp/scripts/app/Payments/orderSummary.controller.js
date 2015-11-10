
angular.module('restappApp')
    .controller('orderSummaryController', function($scope, ShoppingCartCache) {

        $scope.shoppingCartCache = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            // shoppingCartCache.query({page: $scope.page, per_page: 20}, function(result, headers)
            $scope.shoppingCartCache =    shoppingCartCache.get('CART')
            {
            //    $scope.links = ParseLinks.parse(headers('link'));
                $scope.shoppingCartCache = ShoppingCartCache.get('CART')
            };
        };

        $scope.loadAll();
        $scope.submit = function(){
            window.location ="orderSummary.html";
        }


    });



//.controller(‘Whateveryourcontrollernameis’, function($scope, ShoppingCartCache) {
