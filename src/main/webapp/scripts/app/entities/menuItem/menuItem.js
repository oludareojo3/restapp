'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('menuItem', {
                parent: 'entity',
                url: '/menuItems',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'MenuItems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/menuItem/menuItems.html',
                        controller: 'MenuItemController'
                    }
                },
                resolve: {
                }
            })
            .state('menuItem.detail', {
                parent: 'entity',
                url: '/menuItem/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'MenuItem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/menuItem/menuItem-detail.html',
                        controller: 'MenuItemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'MenuItem', function($stateParams, MenuItem) {
                        return MenuItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('menuItem.new', {
                parent: 'menuItem',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/menuItem/menuItem-dialog.html',
                        controller: 'MenuItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {title: null, description: null, cost: null, calories: null, foodType: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('menuItem', null, { reload: true });
                    }, function() {
                        $state.go('menuItem');
                    })
                }]
            })
            .state('menuItem.edit', {
                parent: 'menuItem',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/menuItem/menuItem-dialog.html',
                        controller: 'MenuItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MenuItem', function(MenuItem) {
                                return MenuItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('menuItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
