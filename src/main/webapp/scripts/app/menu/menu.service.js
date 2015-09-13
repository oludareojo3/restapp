angular.module('restappApp')
    .factory('MenuItemFactory', function($http) {
       var url = 'api/menu';

        return {
            menuItems: function(callback) {
                $http.get(url).success(callback);
            }
        }
    });
