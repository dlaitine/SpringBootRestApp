package fi.dlaitine.springbootrestapp.exception;

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
