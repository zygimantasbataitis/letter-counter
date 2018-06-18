(function() {
	angular.module('letterCounterApp').controller('FilesController',
			[ '$http', '$scope', 'NotifyService', 'FilesService', ctrl ]);

	function ctrl($http ,$scope, NotifyService, FilesService) {
        
		$scope.formdata = new FormData();
        $scope.getTheFiles = function ($files) {
        	console.log($files);
            angular.forEach($files, function (value, key) {                
            	$scope.formdata.append(key, value);
            });
        };

        // NOW UPLOAD THE FILES.
        $scope.uploadFiles = function () {

            var request = {
                method: 'POST',
                url: 'http://localhost:8080/letter-counter-rest/files/uploadFiles',
                data: $scope.formdata,
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            };

            // SEND THE FILES.
            $http(request)
                .success(function (d) {
                    alert(d);
                })
                .error(function () {
                });
        }
		
		/*
		$scope.deleteFile = function(file) {
			file.$remove(function() {
				$scope.files = FileService.query();
				NotifyService.showRemovedItem();
			});
		};*/
	}
})();
