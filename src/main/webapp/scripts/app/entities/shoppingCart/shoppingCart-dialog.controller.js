'use strict';

angular.module('restappApp').controller('ShoppingCartDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ShoppingCart', 'MyOrder',
        function($scope, $stateParams, $modalInstance, entity, ShoppingCart, MyOrder) {

        $scope.shoppingCart = entity;
        $scope.myorders = MyOrder.query();
        $scope.load = function(id) {
            ShoppingCart.get({id : id}, function(result) {
                $scope.shoppingCart = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('restappApp:shoppingCartUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.shoppingCart.id != null) {
                ShoppingCart.update($scope.shoppingCart, onSaveFinished);
            } else {
                ShoppingCart.save($scope.shoppingCart, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
