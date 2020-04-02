package fi.dlaitine.springbootrestapp.exception;

public class TaskAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public TaskAlreadyExistsException(String name) {
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return "Task with name '" + name + "' already exists in the database";
	}

}
