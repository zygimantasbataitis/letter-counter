(function() {
	angular.module('letterCounterApp').controller('CreateController',
			[ '$rootScope', '$scope', '$location', 'NotifyService', 'CreateNewsService', ctrl ]);

	function ctrl($rootScope, $scope, $location, NotifyService, CreateNewsService) {
		$scope.checkErr = function(startDate,endDate) {
			$rootScope.error = '';
		    var curDate = new Date();

		    if(new Date(startDate) > new Date(endDate)){
		    	$rootScope.error = 'End date should be greater than start date';
		      return false;
		    }
		    if(new Date(startDate) < curDate){
		    	$rootScope.error = 'Start date should not be before today';
		       return false;
		    }
		};		
		
		$scope.newsEntry = new CreateNewsService();
		$scope.save = function() {
			$scope.newsEntry.$save(function() {
				$location.path('/news');
				NotifyService.showSavedItem();
			});
		};
	}
})();
