angular.module('restappApp')
    .controller('PaymentsController', function($scope) {

        $scope.name = 'Anton';
        $scope.submit = function(){
            window.location ="scripts/app/Payments/orderSummary.html";
        }


    });
