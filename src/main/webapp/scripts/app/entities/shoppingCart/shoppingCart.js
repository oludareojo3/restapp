'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shoppingCart', {
                parent: 'entity',
                url: '/shoppingCarts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ShoppingCarts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shoppingCart/shoppingCarts.html',
                        controller: 'ShoppingCartController'
                    }
                },
                resolve: {
                }
            })
            .state('shoppingCart.detail', {
                parent: 'entity',
                url: '/shoppingCart/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ShoppingCart'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shoppingCart/shoppingCart-detail.html',
                        controller: 'ShoppingCartDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ShoppingCart', function($stateParams, ShoppingCart) {
                        return ShoppingCart.get({id : $stateParams.id});
                    }]
                }
            })
            .state('shoppingCart.new', {
                parent: 'shoppingCart',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/shoppingCart/shoppingCart-dialog.html',
                        controller: 'ShoppingCartDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {userId: null, sessionId: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('shoppingCart', null, { reload: true });
                    }, function() {
                        $state.go('shoppingCart');
                    })
                }]
            })
            .state('shoppingCart.edit', {
                parent: 'shoppingCart',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/shoppingCart/shoppingCart-dialog.html',
                        controller: 'ShoppingCartDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ShoppingCart', function(ShoppingCart) {
                                return ShoppingCart.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('shoppingCart', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
