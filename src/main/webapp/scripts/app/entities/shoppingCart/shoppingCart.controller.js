'use strict';

angular.module('restappApp')
    .controller('ShoppingCartController', function ($scope, ShoppingCart, ParseLinks) {
        $scope.shoppingCarts = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ShoppingCart.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shoppingCarts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ShoppingCart.get({id: id}, function(result) {
                $scope.shoppingCart = result;
                $('#deleteShoppingCartConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ShoppingCart.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShoppingCartConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.shoppingCart = {userId: null, sessionId: null, id: null};
        };
    });
