angular.module('app', []).controller('advertisementController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/online-classifieds';

    $scope.loadAdvertisements = function () {
        $http.get(contextPath + '/advertisements')
            .then(function (response) {
                $scope.AdvertisementsList = response.data;
            });
    };

    $scope.loadAdvertisements();
});