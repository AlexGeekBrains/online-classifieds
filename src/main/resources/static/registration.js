angular.module('app', []).controller('regController', function ($scope, $http) {

    $scope.addUser = function () {
        $http.post('http://localhost:8080/online-classifieds/api/v1/authentication/registration', $scope.newUser).then(function (response) {
            $scope.userToken = response.data;
        });
    }
});