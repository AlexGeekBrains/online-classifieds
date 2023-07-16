angular.module('app', []).controller('advertisementController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/online-classifieds/api/v1';

    $scope.loadAdvertisements = function (pageIndex = 1) {
        $http({
            url: contextPath + '/advertisements/get-advertisements',
            method: 'GET',
            params: {
                partTitle: $scope.filter ? $scope.filter.partTitle : null,
                minPrice: $scope.filter ? $scope.filter.minPrice : null,
                maxPrice: $scope.filter ? $scope.filter.maxPrice : null
            }
        }).then(function (response) {
            $scope.AdvertisementsList = response.data.content;
            $scope.filter.partTitle = null;
            $scope.filter.minPrice = null;
            $scope.filter.maxPrice = null;

        });
    };
    $scope.loadAdvertisements();
});