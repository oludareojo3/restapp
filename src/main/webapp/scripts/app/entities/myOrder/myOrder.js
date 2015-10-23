'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('myOrder', {
                parent: 'entity',
                url: '/myOrders',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'MyOrders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/myOrder/myOrders.html',
                        controller: 'MyOrderController'
                    }
                },
                resolve: {
                }
            })
            .state('myOrder.detail', {
                parent: 'entity',
                url: '/myOrder/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'MyOrder'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/myOrder/myOrder-detail.html',
                        controller: 'MyOrderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'MyOrder', function($stateParams, MyOrder) {
                        return MyOrder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('myOrder.new', {
                parent: 'myOrder',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/myOrder/myOrder-dialog.html',
                        controller: 'MyOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {orderId: null, userId: null, sessionId: null, orderDate: null, orderValue: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('myOrder', null, { reload: true });
                    }, function() {
                        $state.go('myOrder');
                    })
                }]
            })
            .state('myOrder.edit', {
                parent: 'myOrder',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/myOrder/myOrder-dialog.html',
                        controller: 'MyOrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MyOrder', function(MyOrder) {
                                return MyOrder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('myOrder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
