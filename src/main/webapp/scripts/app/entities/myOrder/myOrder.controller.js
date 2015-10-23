'use strict';

angular.module('restappApp')
    .controller('MyOrderController', function ($scope, MyOrder, ParseLinks) {
        $scope.myOrders = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            MyOrder.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.myOrders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MyOrder.get({id: id}, function(result) {
                $scope.myOrder = result;
                $('#deleteMyOrderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MyOrder.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMyOrderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.myOrder = {orderId: null, userId: null, sessionId: null, orderDate: null, orderValue: null, id: null};
        };
    });
