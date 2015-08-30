'use strict';

angular.module('restappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


