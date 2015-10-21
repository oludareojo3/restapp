'use strict';

angular.module('restappApp')
    .controller('ShoppingcartController', function ($scope, Shoppingcart, ParseLinks) {
        $scope.shoppingcarts = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Shoppingcart.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shoppingcarts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Shoppingcart.get({id: id}, function(result) {
                $scope.shoppingcart = result;
                $('#deleteShoppingcartConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Shoppingcart.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShoppingcartConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.shoppingcart = {sessionId: null, menuItem: null, currentUser: null, totalCost: null, id: null};
        };
    });
