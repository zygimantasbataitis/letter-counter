package lt.metasite.rest.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.metasite.bl.utils.AppUtils;
import lt.metasite.model.info.AppInfo;

@RestController
@RequestMapping("/appInfoResource")
public class AppInfoResource {

	@GetMapping("/appInfo")
	public AppInfo getAppInfo() {
		return AppUtils.getAppInfo();
	}

}