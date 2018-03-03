package fi.dlaitine.SpringBootRestApp.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.dlaitine.SpringBootRestApp.models.TaskEntity;
import fi.dlaitine.SpringBootRestApp.models.TaskRequest;
import fi.dlaitine.SpringBootRestApp.models.TaskResponse;
import fi.dlaitine.SpringBootRestApp.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository repository;
	
	public List<TaskResponse> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(task -> new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreated()))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public TaskResponse save(TaskRequest task) {
		TaskEntity newTask = new TaskEntity(task.getName(), task.getDescription(), task.isDone());
		
		TaskEntity savedTask = repository.save(newTask);
		return new TaskResponse(savedTask.getId(), savedTask.getName(), savedTask.getDescription(), savedTask.isDone(), savedTask.getCreated());
	}
	
	@Transactional
	public TaskResponse update(String name, TaskRequest newTask) throws Exception {
		TaskEntity task = findTask(name);
		task.setName(newTask.getName());
		task.setDescription(newTask.getDescription());
		task.setDone(newTask.isDone());
		
		return new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreated());
	}
	
	@Transactional
	public void delete(String name) throws Exception {
		TaskEntity task = findTask(name);
		
		if(task != null) {
			repository.delete(task);
		}
	}
	
	private TaskEntity findTask(String name) throws Exception {
		TaskEntity task = repository.findByName(name);
		
		if(task == null) {
			throw new Exception("Task with name " + name + " not found");
		}
		
		return task;
	}
}
