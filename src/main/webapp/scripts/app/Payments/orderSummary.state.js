'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderSummary', {
                parent: 'site',
                url: '/Payments',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/Payments/orderSummary.html',
                        controller: 'orderSummaryController'
                    }
                },
                resolve: {

                }
            });
    });
