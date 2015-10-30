'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('menu', {
                parent: 'site',
                url: '/menu',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/menu/menu.html',
                        controller: 'MenuController'
                    }
                },
                resolve: {

                }
            });
    });
