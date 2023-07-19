angular.module('app', []).controller('advertisementController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/online-classifieds/api/v1';


    $scope.loadAdvertisements = function (pageIndex = 1) {
        $http({
            url: contextPath + '/advertisements/get-advertisements',
            method: 'GET',
            params: {
                page: pageIndex,
                partTitle: $scope.filter ? $scope.filter.partTitle : null,
                minPrice: $scope.filter ? $scope.filter.minPrice : null,
                maxPrice: $scope.filter ? $scope.filter.maxPrice : null
            }
        }).then(function (response) {
            $scope.advertisementsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.advertisementsPage.totalPages);
            $scope.currentPage =  $scope.advertisementsPage.number+1;
            $scope.AdvertisementsList = response.data.content;
            $scope.filter.partTitle = null;
            $scope.filter.minPrice = null;
            $scope.filter.maxPrice = null;

        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadAdvertisements();
});