var services = angular.module('letterCounterApp.services', [ 'ngResource' ]);

services.factory('UserService', function($resource) {
	return $resource('http://localhost:8080/letter-counter-rest/user/:action', {}, {
		authenticate : {
			method : 'POST',
			params : {
				'action' : 'authenticate'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},
		register : {
			method : 'POST',
			params : {
				'action' : 'register'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},
		user_detail : {
			method : 'GET',
			params : {
				'action' : 'detail'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},		
		id : {
			method : 'GET',
			params : {
				'action' : '@id'
			}
		},
		documentIdTypes : {
			method : 'GET',
			params : {
				'action' : 'documentIdTypes'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},
		log_out : {
			method : 'POST',
			params : {
				'action' : 'logout'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},		
	});
});

services.factory('NewsService', function($resource) {
	return $resource('http://localhost:8080/letter-counter-rest/news/:id', {
		id : '@id'	
	});
});

services.factory('FilesService', function($resource) {
	return $resource('http://localhost:8080/letter-counter-rest/files/:id', {
		id : '@id'	
	});
});

services.factory('FilesDownloadService', function($resource) {
	return $resource('http://localhost:8080/letter-counter-rest/files/download_file/:id', {}, {
		downloadFile : {
			method : 'GET',
			params : {
				'id' : 'downloadFile'
			},
			transformResponse: function (data, headers) {
		        var response = {};
		        response.data = data;
		        response.headers = headers();
		        return response;
		    }
		},
	});
});

services.factory('CreateNewsService', function($resource) {
	return $resource('http://localhost:8080/letter-counter-rest/news/create', {});
});

services.factory('AppInfoService', function($resource) {
	return $resource('http://localhost:8080/letter-counter-rest/appInfoResource/appInfo', {});
});