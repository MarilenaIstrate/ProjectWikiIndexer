app.controller('MainController', ['$scope', '$http', function($scope, $http) {
    $scope.list = [];
    $scope.var = "ceva";

    $scope.submit = function() {
        alert( "Pressed submitt" );
        var formData = {
            "fileName" : $scope.fileName,
            "articleName" : $scope.articleName,
        };
        var response = $http.post('/', formData);
        response.success(function(data, status, headers, config) {
            $scope.list.push(data);
        });
        response.error(function(data, status, headers, config) {
            alert( "Exception details: " + JSON.stringify({data: data}));
        });
        //Empty list data after process
        //$scope.list = [];
    };
}]);