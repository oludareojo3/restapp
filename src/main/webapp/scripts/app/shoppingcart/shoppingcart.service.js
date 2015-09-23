angular.module('restappApp')
    .factory('ShoppingCartFactory', function($http) {
       var url = 'api/shoppingcart';

        return {
            menuItems: function(callback) {
                $http.get(url).success(callback);
            }
        }
    });
