'use strict';

angular.module('restappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('menu', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
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
