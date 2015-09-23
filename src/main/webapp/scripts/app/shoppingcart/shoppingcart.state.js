'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shoppingcart', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/shoppingcart/shoppingcart.html',
                        controller: 'ShoppingCartController'
                    }
                },
                resolve: {

                }
            });
    });
