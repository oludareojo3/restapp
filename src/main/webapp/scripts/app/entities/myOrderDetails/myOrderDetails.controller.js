'use strict';

angular.module('restappApp')
    .controller('MyOrderDetailsController', function ($scope, MyOrderDetails, ParseLinks) {
        $scope.myOrderDetailss = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            MyOrderDetails.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.myOrderDetailss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MyOrderDetails.get({id: id}, function(result) {
                $scope.myOrderDetails = result;
                $('#deleteMyOrderDetailsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MyOrderDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMyOrderDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.myOrderDetails = {orderId: null, itemId: null, qty: null, unitPrice: null, attribute: null, id: null};
        };
    });
