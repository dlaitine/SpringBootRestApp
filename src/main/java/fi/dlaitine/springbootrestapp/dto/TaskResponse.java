package fi.dlaitine.springbootrestapp.dto;

public class TaskResponse {

	private long id;
	private String name;
	private String description;
	private boolean done;
	private long createdAt;

	public TaskResponse(long id, String name, String description, boolean done, long createdAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.done = done;
		this.createdAt = createdAt;
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

	public long getCreatedAt() {
		return createdAt;
	}
}
