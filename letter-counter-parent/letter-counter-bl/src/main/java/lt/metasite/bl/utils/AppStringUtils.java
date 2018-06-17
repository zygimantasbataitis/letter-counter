package lt.metasite.bl.utils;

import org.apache.commons.lang.StringUtils;

import lt.metasite.model.utils.Consts;

public class AppStringUtils {

	public static String addBrackets(String... values) {
		StringBuilder res = new StringBuilder();
		if (values == null) {
			return null;
		}

		res.append(addBrackets(StringUtils.join(values, Consts.COMMA_SEPERATOR)));
		return res.toString();
	}

	public static String addBrackets(String value) {
		StringBuilder res = new StringBuilder();
		if (value == null) {
			return null;
		}

		res.append(Consts.SPACE);
		res.append(Consts.BRACKET_LEFT).append(value).append(Consts.BRACKET_RIGHT);
		return res.toString();
	}

}
