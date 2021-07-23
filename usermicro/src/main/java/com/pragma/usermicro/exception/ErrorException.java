package apragma.practica.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class ErrorException extends RuntimeException {

	private HttpStatus httpStatus;

	public ErrorException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}

}