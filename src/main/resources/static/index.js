angular.module('app', []).controller('advertisementController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/online-classifieds/api/v1';

    $scope.filter = {selectedCategory: null};
    $scope.loadCategories = function() {
        $http({
            url: contextPath + '/categories/get-categories',
            method: 'GET'
            }).then(function(response) {
    $scope.categories = response.data;
            });
};

    $scope.loadAdvertisements = function (pageIndex = 1) {
        var categoryId = $scope.filter.selectedCategory ? $scope.filter.selectedCategory.id : null;

    $http({
        url: contextPath + '/advertisements/get-advertisements',
        method: 'GET',
        params: {
            page: pageIndex,
            partTitle: $scope.filter ? $scope.filter.partTitle : null,
            categoryId: categoryId,
            minPrice: $scope.filter ? $scope.filter.minPrice : null,
            maxPrice: $scope.filter ? $scope.filter.maxPrice : null
        }
    }).then(function (response) {
        $scope.advertisementsPage = response.data;
        $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.advertisementsPage.totalPages);
        $scope.currentPage =  $scope.advertisementsPage.number+1;
        $scope.AdvertisementsList = response.data.content;
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
    $scope.loadCategories();
});