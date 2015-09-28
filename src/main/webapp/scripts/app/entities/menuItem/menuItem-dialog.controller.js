'use strict';

angular.module('restappApp').controller('MenuItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MenuItem',
        function($scope, $stateParams, $modalInstance, entity, MenuItem) {

        $scope.menuItem = entity;
        $scope.load = function(id) {
            MenuItem.get({id : id}, function(result) {
                $scope.menuItem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('restappApp:menuItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.menuItem.id != null) {
                MenuItem.update($scope.menuItem, onSaveFinished);
            } else {
                MenuItem.save($scope.menuItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
