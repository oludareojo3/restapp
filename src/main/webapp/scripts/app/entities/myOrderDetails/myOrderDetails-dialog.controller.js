'use strict';

angular.module('restappApp').controller('MyOrderDetailsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MyOrderDetails', 'MyOrder',
        function($scope, $stateParams, $modalInstance, entity, MyOrderDetails, MyOrder) {

        $scope.myOrderDetails = entity;
        $scope.myorders = MyOrder.query();
        $scope.load = function(id) {
            MyOrderDetails.get({id : id}, function(result) {
                $scope.myOrderDetails = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('restappApp:myOrderDetailsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.myOrderDetails.id != null) {
                MyOrderDetails.update($scope.myOrderDetails, onSaveFinished);
            } else {
                MyOrderDetails.save($scope.myOrderDetails, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
