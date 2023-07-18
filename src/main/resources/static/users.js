angular.module('app', []).controller('usersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/online-classifieds';

    $scope.loadUsers = function () {
        $http.get(contextPath + '/users')
            .then(function (response) {
                $scope.UsersList = response.data;
            }).catch(function(error) {
            console.error('Error occurred:', error);
        });
    };

    $scope.loadUsers();
});