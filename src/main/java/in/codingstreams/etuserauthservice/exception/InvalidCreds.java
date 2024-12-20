package in.codingstreams.etuserauthservice.exception;

import lombok.Getter;

@Getter
public class InvalidCreds extends RuntimeException{

	private String errorCode;

	public InvalidCreds(String message ,String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}


}
