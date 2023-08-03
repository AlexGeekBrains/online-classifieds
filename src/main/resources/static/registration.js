angular.module('app', []).controller('regController', function ($scope, $http) {

    $scope.addUser = function () {
        $http.post('http://localhost:8080/online-classifieds/api/v1/authentication/registration', $scope.newUser) .then(function(response) {
         alert(response.data.message);
            }) .catch(function(error) {
                if (error.data && error.data.messages) {
                alert('An error occurred: ' + error.data.messages.join(', '));
                    } else {
                    alert('An error occurred: ' + error.statusText);
                    }
         });
    }
 });

