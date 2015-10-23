'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('myOrderDetails', {
                parent: 'entity',
                url: '/myOrderDetailss',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'MyOrderDetailss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/myOrderDetails/myOrderDetailss.html',
                        controller: 'MyOrderDetailsController'
                    }
                },
                resolve: {
                }
            })
            .state('myOrderDetails.detail', {
                parent: 'entity',
                url: '/myOrderDetails/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'MyOrderDetails'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/myOrderDetails/myOrderDetails-detail.html',
                        controller: 'MyOrderDetailsDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'MyOrderDetails', function($stateParams, MyOrderDetails) {
                        return MyOrderDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('myOrderDetails.new', {
                parent: 'myOrderDetails',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/myOrderDetails/myOrderDetails-dialog.html',
                        controller: 'MyOrderDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {orderId: null, itemId: null, qty: null, unitPrice: null, attribute: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('myOrderDetails', null, { reload: true });
                    }, function() {
                        $state.go('myOrderDetails');
                    })
                }]
            })
            .state('myOrderDetails.edit', {
                parent: 'myOrderDetails',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/myOrderDetails/myOrderDetails-dialog.html',
                        controller: 'MyOrderDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MyOrderDetails', function(MyOrderDetails) {
                                return MyOrderDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('myOrderDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
