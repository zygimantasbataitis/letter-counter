package lt.metasite.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DocumentIdType {
	
	BIRTH_CERTIFICATE("Birth certificate"), 
	SOCIAL_SECURITY_CARD("Social security card"),
	DRIVER_LICENCE("Driver licence"),
	PASSPORT("Passport"),
	PASSPORT_CARD("Passport card");
	
	private DocumentIdType(String displayValue) {
		this.displayValue = displayValue;
	}
	
	@JsonValue
	public String getDisplayValue() {
		return displayValue;
	}

	private final String displayValue;

}