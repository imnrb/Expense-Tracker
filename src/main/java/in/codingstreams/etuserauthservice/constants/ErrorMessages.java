package in.codingstreams.etuserauthservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {
	USER_AlREADY_EXISTS("E409","User already exists !"),
	
	USER_NOT_FOUND("E404","User not found !"),
	
	INVALID_CREDS("400","Email and password does'nt match"),
	
	INVALID_TOKEN("T401","User not authorised /invalid token");
	
	String errorCode;
	String errorMessage;
}
