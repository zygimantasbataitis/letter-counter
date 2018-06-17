package lt.metasite.bl.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesReader {

	public static final Log log = LogFactory.getLog(PropertiesReader.class);
	private static Map<String, PropertiesReader> instances = new HashMap<>();
	private Properties prop = null;

	public static PropertiesReader getPropertiesReader(String resourcePath) {
		return getPropertiesReader(resourcePath, StandardCharsets.UTF_8.name());
	}

	public static PropertiesReader getPropertiesReader(String resourcePath, String encoding) {
		PropertiesReader res = instances.get(resourcePath);

		if (res == null) {
			synchronized (instances) {
				instances.putIfAbsent(resourcePath, new PropertiesReader(resourcePath, encoding));
				res = instances.get(resourcePath);
			}
		}

		return res;
	}

	private PropertiesReader(String resourcePath, String encoding) {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Reader reader = new InputStreamReader(loader.getResourceAsStream(resourcePath), encoding);
			prop = new Properties();
			prop.load(reader);
		} catch (IOException e) {
			prop = null;
			log.error("Failed to read properties", e);
		}
	}

	public String getRes(String key, String defaultValue) {
		return prop != null ? prop.getProperty(key, defaultValue) : defaultValue;
	}

	public String getRes(String key) {
		return getRes(key, key);
	}

	public <T extends Enum<?>> String getLocalizedEnumValue(T value) {
		return value != null ? getRes(value.getClass().getName() + '.' + value.name()) : null;
	}

}
