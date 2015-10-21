'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shoppingcart', {
                parent: 'entity',
                url: '/shoppingcarts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Shoppingcarts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shoppingcart/shoppingcarts.html',
                        controller: 'ShoppingcartController'
                    }
                },
                resolve: {
                }
            })
            .state('shoppingcart.detail', {
                parent: 'entity',
                url: '/shoppingcart/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Shoppingcart'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shoppingcart/shoppingcart-detail.html',
                        controller: 'ShoppingcartDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Shoppingcart', function($stateParams, Shoppingcart) {
                        return Shoppingcart.get({id : $stateParams.id});
                    }]
                }
            })
            .state('shoppingcart.new', {
                parent: 'shoppingcart',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/shoppingcart/shoppingcart-dialog.html',
                        controller: 'ShoppingcartDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {sessionId: null, menuItem: null, currentUser: null, totalCost: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('shoppingcart', null, { reload: true });
                    }, function() {
                        $state.go('shoppingcart');
                    })
                }]
            })
            .state('shoppingcart.edit', {
                parent: 'shoppingcart',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/shoppingcart/shoppingcart-dialog.html',
                        controller: 'ShoppingcartDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Shoppingcart', function(Shoppingcart) {
                                return Shoppingcart.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('shoppingcart', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
