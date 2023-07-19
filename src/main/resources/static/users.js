angular.module('app', []).controller('usersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/online-classifieds/api/v1';

    $scope.loadUsers = function (pageIndex = 1) {
        $http({
            url: contextPath + '/users/get-users',
            method: 'GET',
            params: {
                page: pageIndex,
                partUsername: $scope.filter ? $scope.filter.partUsername : null,
                partEmail: $scope.filter ? $scope.filter.partEmail : null,
                partCreatedAt: $scope.filter ? $scope.filter.partCreatedAt : null,
                partUpdatedAt: $scope.filter ? $scope.filter.partUpdatedAt : null
            }
        }).then(function (response) {
            $scope.usersPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.usersPage.totalPages);
            $scope.currentPage =  $scope.usersPage.number+1;
            $scope.UsersList = response.data.content;
            $scope.filter.partUsername = null;
            $scope.filter.partEmail = null;
            $scope.filter.partCreatedAt = null;
            $scope.filter.partUpdatedAt = null;
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadUsers();
});