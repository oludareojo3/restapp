angular.module('restappApp')
    .service('ShoppingCartCache', function($rootScope) {

        var cache = {};

        this.put = function(key, value) {
            cache[key] = value;
        };

        this.remove = function(key) {
            delete cache[key];
        };

        this.get = function(key) {
            return cache[key] || null;
        };

    });
