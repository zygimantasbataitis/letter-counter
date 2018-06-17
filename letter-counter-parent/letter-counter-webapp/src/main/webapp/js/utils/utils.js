var utils = angular.module('letterCounterApp.utils', [ 'ngNotify' ]);

utils.constant('NOTIFICATION', (function() {
	return {
		TITLE_LT : 'Pranešimas',
		NAME_DELETED_LT : 'Įrašas sėkmingai pašalintas',
		NAME_SAVED_LT : 'Įrašas sėkmingai išsaugotas',
		TITLE_EN : 'Notification',
		NAME_DELETED_EN : 'Successfully deleted',
		NAME_SAVED_EN : 'Successfully saved',		
		POSITION : 'bottom-right'
	}
})());

utils.service('NotifyService', function($notify, $rootScope, NOTIFICATION) {
	this.showSavedItem = function() {
		$notify.setTime(2);
		$notify.showCloseButton(true);
		$notify.showProgressBar(true);
		$notify.setPosition(NOTIFICATION.POSITION);
		if ($rootScope.selectedLanguage == "lt") {
			$notify.success(NOTIFICATION.TITLE_LT, NOTIFICATION.NAME_SAVED_LT);			
		} else {
			$notify.setPosition(NOTIFICATION.POSITION);
			$notify.success(NOTIFICATION.TITLE_EN, NOTIFICATION.NAME_SAVED_EN);						
		}
		return $notify
	};
	this.showRemovedItem = function() {
		$notify.setTime(2);
		$notify.showCloseButton(true);
		$notify.showProgressBar(true);
		$notify.setPosition(NOTIFICATION.POSITION);
		if ($rootScope.selectedLanguage == "lt") {
			$notify.success(NOTIFICATION.TITLE_LT, NOTIFICATION.NAME_DELETED_LT);			
		} else {
			$notify.success(NOTIFICATION.TITLE_EN, NOTIFICATION.NAME_DELETED_EN);			
		}
		return $notify
	};
});