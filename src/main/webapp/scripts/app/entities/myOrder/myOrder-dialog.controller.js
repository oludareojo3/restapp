'use strict';

angular.module('restappApp').controller('MyOrderDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MyOrder', 'MyOrderDetails',
        function($scope, $stateParams, $modalInstance, entity, MyOrder, MyOrderDetails) {

        $scope.myOrder = entity;
        $scope.myorderdetailss = MyOrderDetails.query();
        $scope.load = function(id) {
            MyOrder.get({id : id}, function(result) {
                $scope.myOrder = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('restappApp:myOrderUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.myOrder.id != null) {
                MyOrder.update($scope.myOrder, onSaveFinished);
            } else {
                MyOrder.save($scope.myOrder, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
