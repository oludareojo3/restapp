/* globals $ */
'use strict';

angular.module('restappApp')
    .directive('restappAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
