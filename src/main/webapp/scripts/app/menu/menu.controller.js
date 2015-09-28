angular.module('restappApp')
    .controller('MenuController', function($scope, MenuItem) {

        $scope.loadAll = function() {
            MenuItem.query(function(result) {
                $scope.menuItems = result;
            });
        };
        $scope.loadAll();

    });
