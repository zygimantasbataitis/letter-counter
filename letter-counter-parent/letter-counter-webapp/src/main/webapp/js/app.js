var app = angular.module('letterCounterApp', 
		['ngRoute', 
		 'ngCookies',
		 'ngInputDate',	
		 'letterCounterApp.services', 
		 'letterCounterApp.utils',
		 'smart-table', 
		 'nya.bootstrap.select', 
		 'ngMockE2E',
		 'angularFileUpload',
		 'pascalprecht.translate']);

	app.constant('urls', {
		APP: 'http://localhost:8080/letter-counter-app',
	    REST_API: 'http://localhost:8080/letter-counter-rest/'
	});

	app.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', '$translateProvider', 
		  function($routeProvider, $locationProvider, $httpProvider, $translateProvider) {
			
			$translateProvider.useStaticFilesLoader({
			    prefix: 'i18n/locale-',
			    suffix: '.json'
			});			
			
			$translateProvider.preferredLanguage('en');
			$translateProvider.useLocalStorage();
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create/create.html'
			});
						
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit/edit.html'
			});
				
			$routeProvider.when('/edit_user/:id', {
				templateUrl: 'partials/edit/edit_user.html'
			});			
			
			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html'
			});
								
			$routeProvider.when('/users', {
				templateUrl: 'partials/view/users.html'
			});				
					
			$routeProvider.when('/news', {
				templateUrl: 'partials/view/news.html'
			});
			
			$routeProvider.when('/files', {
				templateUrl: 'partials/view/files.html'
			});						
			
			$routeProvider.otherwise({
				templateUrl: 'partials/login.html'
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		//var method = config.method;
			        		//var url = config.url;
			      /*
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              */
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (letterCounterAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		} ]		
	);
	
app.run(function($rootScope, $location, $cookieStore, UserService, $httpBackend, $translate, AppInfoService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/files");
		};
		
		$rootScope.changeLanguage = function (langKey) {
			  $translate.use(langKey);
			  $rootScope.selectedLanguage = $translate.use().toUpperCase();
		  };		
		
		if ($rootScope.selectedLanguage == null) {
			$rootScope.selectedLanguage = "EN";
		} else {
			$rootScope.selectedLanguage = $translate.use();	
		}  
		
		
		$rootScope.isLogged = function() {
			if ($rootScope.user === undefined) {
				return true;
			}
			return true;
		};		
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/files");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
		
		$httpBackend.whenGET(/resources/).passThrough();
		$httpBackend.whenGET(/i18n/).passThrough();
		$httpBackend.whenGET(/partials/).passThrough();
		$httpBackend.whenPOST(/rest/).passThrough();
		$httpBackend.whenGET(/rest/).passThrough();
		$httpBackend.whenDELETE(/rest/).passThrough();
		
		$rootScope.appInfo = AppInfoService.get();
	});