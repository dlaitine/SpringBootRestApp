package fi.dlaitine.SpringBootRestApp.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskRequest {

	
	@NotNull(message = "Name can't be empty")
	@Size(min = 4, max = 64, message = "Name length must be between 4 and 64 characters")
	private String name;
	
	private String description;
	private boolean done;

	
	public TaskRequest() {
		
	}
	
	public TaskRequest(String name, String description, boolean done) {
		this.name = name;
		this.description = description;
		this.done = done;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
}
