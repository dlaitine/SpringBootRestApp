package fi.dlaitine.SpringBootRestApp.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.dlaitine.SpringBootRestApp.models.TaskEntity;
import fi.dlaitine.SpringBootRestApp.models.TaskRequest;
import fi.dlaitine.SpringBootRestApp.models.TaskResponse;
import fi.dlaitine.SpringBootRestApp.models.exceptions.TaskAlreadyExistsException;
import fi.dlaitine.SpringBootRestApp.models.exceptions.TaskNotFoundException;
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
		
		try {
			TaskEntity savedTask = repository.save(newTask);
			return new TaskResponse(savedTask.getId(), savedTask.getName(), savedTask.getDescription(), savedTask.isDone(), savedTask.getCreated());
		}
		catch (DataIntegrityViolationException e) {
			throw new TaskAlreadyExistsException(task.getName());
		}
	}
	
	@Transactional
	public TaskResponse update(String name, TaskRequest newTask) {
		if(repository.existsByName(newTask.getName())) {
			throw new TaskAlreadyExistsException(newTask.getName());
		}
		
		TaskEntity task = findTask(name);
		task.setName(newTask.getName());
		task.setDescription(newTask.getDescription());
		task.setDone(newTask.isDone());
		
		return new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreated());
	}
	
	@Transactional
	public void delete(String name) {
		TaskEntity task = findTask(name);
		
		if(task != null) {
			repository.delete(task);
		}
	}
	
	private TaskEntity findTask(String name) {
		TaskEntity task = repository.findByName(name);
		
		if(task == null) {
			throw new TaskNotFoundException(name);
		}
		
		return task;
	}
}
