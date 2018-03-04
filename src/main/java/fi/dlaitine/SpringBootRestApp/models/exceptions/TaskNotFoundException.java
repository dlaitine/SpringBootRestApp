package fi.dlaitine.SpringBootRestApp.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Task not found")
public class TaskNotFoundException extends RuntimeException {

	private String name;
	
	public TaskNotFoundException(String name) {
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return "Task with name '" + name + "' not found";
	}

}
