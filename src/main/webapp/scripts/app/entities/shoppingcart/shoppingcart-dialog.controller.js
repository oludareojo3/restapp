'use strict';

angular.module('restappApp').controller('ShoppingcartDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Shoppingcart', 'MenuItem',
        function($scope, $stateParams, $modalInstance, entity, Shoppingcart, MenuItem) {

        $scope.shoppingcart = entity;
        $scope.menuitems = MenuItem.query();
        $scope.load = function(id) {
            Shoppingcart.get({id : id}, function(result) {
                $scope.shoppingcart = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('restappApp:shoppingcartUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.shoppingcart.id != null) {
                Shoppingcart.update($scope.shoppingcart, onSaveFinished);
            } else {
                Shoppingcart.save($scope.shoppingcart, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
