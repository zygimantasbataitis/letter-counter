(function() {
	angular.module('letterCounterApp').controller('FilesController',
			[ '$http', '$scope', 'NotifyService', 'FilesService', 'FilesDownloadService', '$q', '$timeout', '$window', ctrl ]);

	function ctrl($http ,$scope, NotifyService, FilesService, FilesDownloadService, $q, $timeout, $window) {
		$scope.dbFiles = FilesService.query();
		$scope.formdata = new FormData();
        $scope.getTheFiles = function ($files) {
        	console.log($files);
            angular.forEach($files, function (value, key) {                
            	$scope.formdata.append(key, value);
            });
        };

        $scope.uploadFiles = function () {
            var request = {
                method: 'POST',
                url: 'http://localhost:8080/letter-counter-rest/files/upload_files',
                data: $scope.formdata,
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            };

            $http(request)
                .success(function () {
                	$scope.dbFiles = FilesService.query();
                })
                .error(function (d) {
                	alert(d);
                });
        }
		
        $scope.downloadGeneratedFile = function (fileId) {
        	FilesDownloadService.downloadFile({id: fileId}, function (response) {

                var anchor = angular.element('<a/>');
                anchor.attr({
                    href: 'data:application/octet-stream;base64,' + response.data,
                    target: '_self',
                    download: response.headers.filename        
                });

                angular.element(document.body).append(anchor);
                anchor[0].click();

            });
        }
        
        $scope.downloadFile = function(file) {
        	$scope.file_download = FilesDownloadService.get({id: file.id});
            var defer = $q.defer();

            $timeout(function() {
                    $window.location = 'download?name=' + name;

                }, 1000)
                .then(function() {
                    defer.resolve('success');
                }, function() {
                    defer.reject('error');
                });        	
        }
		$scope.deleteFile = function(file) {
			file.$remove(function() {
				$scope.dbFiles = FilesService.query();
				NotifyService.showRemovedItem();
			});
		};
	}
})();
