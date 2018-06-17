(function() {
	angular.module('letterCounterApp').controller('FilesController',
			[ '$scope', 'NotifyService', 'FileService', ctrl ]);

	function ctrl($scope, NotifyService, FileService) {
		$scope.uploadFiles = function() {
			FileService.query();
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
