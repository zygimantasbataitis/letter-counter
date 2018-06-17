package lt.metasite.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleType {
	
	ADMIN,
	CONTROLLER,
	JUNIOR,
	GUEST
}