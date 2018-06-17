package lt.metasite.bl.utils;

import lt.metasite.model.info.AppInfo;
import lt.metasite.model.utils.Consts;

public class AppUtils {

	private static PropertiesReader pr = PropertiesReader.getPropertiesReader(Consts.VERSION_PROPS);

	private static String getAppInfoByKey(String key) {
		return pr.getRes(key);
	}

	public static AppInfo getAppInfo() {
		return new AppInfo(AppUtils.getAppInfoByKey(Consts.APP_VERSION),
				AppUtils.getAppInfoByKey(Consts.BUILD_TIME));
	}

}
