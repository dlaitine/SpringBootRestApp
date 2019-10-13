package fi.dlaitine.springbootrestapp.model;

import java.time.LocalDateTime;

public class TaskResponse {

	private long id;
	private String name;
	private String description;
	private boolean done;
	private LocalDateTime created;
	
	private TaskResponse() {
		
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

	public String getDescription() {
		return description;
	}

	public boolean isDone() {
		return done;
	}

	public long getId() {
		return id;
	}

	public LocalDateTime getCreated() {
		return created;
	}
}
