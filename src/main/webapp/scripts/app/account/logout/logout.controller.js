'use strict';

angular.module('restappApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
