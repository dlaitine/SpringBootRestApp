package fi.dlaitine.springbootrestapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		
		@Column(unique=true)
		private String name;
		
		@Column
		private String description;
		
		@Column
		private boolean done;
		
		@Column
		private LocalDateTime created;
		
		public Task() {
			
		}
		
		public Task(String name, String description, boolean done) {
			this.name = name;
			this.description = description;
			this.done = done;
			this.created = LocalDateTime.now();
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
