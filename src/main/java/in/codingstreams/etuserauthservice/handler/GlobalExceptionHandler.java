package in.codingstreams.etuserauthservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.codingstreams.etuserauthservice.controller.dto.ErrorResponse;
import in.codingstreams.etuserauthservice.exception.InvalidCreds;
import in.codingstreams.etuserauthservice.exception.InvalidTokenException;
import in.codingstreams.etuserauthservice.exception.UserAlreadyExistsException;
import in.codingstreams.etuserauthservice.exception.UserNotFoundException;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
			ErrorResponse.builder()
			.errorcode(e.getErrorCode())
			.errormessage(e.getMessage())
			.build()
				);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				ErrorResponse.builder()
				.errorcode(e.getErrorCode())
				.errormessage(e.getMessage())
				.build()
					); 
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleInvalidCreds(InvalidCreds e)
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				ErrorResponse.builder()
				.errorcode(e.getErrorCode())
				.errormessage(e.getMessage())
				.build()
					); 
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e)
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				ErrorResponse.builder()
				.errorcode(e.getErrorCode())
				.errormessage(e.getMessage())
				.build()
					); 
	}
}
