package fi.dlaitine.SpringBootRestApp.models.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Task with same name already exists in database")
public class TaskAlreadyExistsException extends RuntimeException {

	private String name;
	
	public TaskAlreadyExistsException(String name) {
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return "Task with name '" + name + "' already exists in database";
	}

}
