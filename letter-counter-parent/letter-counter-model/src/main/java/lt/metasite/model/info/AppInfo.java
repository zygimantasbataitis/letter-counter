package lt.metasite.model.info;

public class AppInfo {

	private String version;
	private String build;

	public AppInfo(String version, String build) {
		this.version = version;
		this.build = build;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

}
