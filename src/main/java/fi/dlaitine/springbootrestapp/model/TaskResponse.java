package fi.dlaitine.springbootrestapp.model;

import java.time.LocalDateTime;

public class TaskResponse {

	private long id;
	private String name;
	private String description;
	private boolean done;
	private LocalDateTime created;
	
	public TaskResponse() {
		
	}
	
	public TaskResponse(long id, String name, String description, boolean done, LocalDateTime created) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.done = done;
		this.created = created;
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

	public long getId() {
		return id;
	}

	public LocalDateTime getCreated() {
		return created;
	}
}
