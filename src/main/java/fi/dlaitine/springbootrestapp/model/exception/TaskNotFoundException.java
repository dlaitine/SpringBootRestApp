package fi.dlaitine.springbootrestapp.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Task not found")
public class TaskNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public TaskNotFoundException(String name) {
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return "Task with name '" + name + "' not found";
	}

}
