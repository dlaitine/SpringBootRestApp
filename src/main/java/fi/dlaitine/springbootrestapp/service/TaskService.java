package fi.dlaitine.springbootrestapp.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.dlaitine.springbootrestapp.entity.Task;
import fi.dlaitine.springbootrestapp.model.TaskRequest;
import fi.dlaitine.springbootrestapp.model.TaskResponse;
import fi.dlaitine.springbootrestapp.model.exception.TaskAlreadyExistsException;
import fi.dlaitine.springbootrestapp.model.exception.TaskNotFoundException;
import fi.dlaitine.springbootrestapp.repository.TaskRepository;

@Service
public class TaskService {
	
	private TaskRepository repository;
	
	public TaskService(TaskRepository repository) {
		this.repository = repository;
	}

	public List<TaskResponse> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(task -> new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreatedAt()))
				.collect(Collectors.toList());
	}
	
	public TaskResponse findByName(String name) {
		Task task = findTask(name);

		return new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreatedAt());
	}
	
	@Transactional
	public TaskResponse save(TaskRequest task) {
		Task newTask = new Task(task.getName(), task.getDescription(), task.isDone());
		
		try {
			Task savedTask = repository.save(newTask);
			return new TaskResponse(savedTask.getId(), savedTask.getName(), savedTask.getDescription(), savedTask.isDone(), savedTask.getCreatedAt());
		}
		catch (DataIntegrityViolationException e) {
			throw new TaskAlreadyExistsException(task.getName());
		}
	}
	
	@Transactional
	public TaskResponse update(String name, TaskRequest newTask) {
		Task task = findTask(name);

		if(!name.equals(newTask.getName()) && repository.existsByName(newTask.getName())) {
			throw new TaskAlreadyExistsException(newTask.getName());
		}
		
		task.setName(newTask.getName());
		task.setDescription(newTask.getDescription());
		task.setDone(newTask.isDone());
		
		return new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreatedAt());
	}
	
	@Transactional
	public void delete(String name) {
		Task task = findTask(name);
		
		if(task != null) {
			repository.delete(task);
		}
	}
	
	private Task findTask(String name) {
		Task task = repository.findByName(name);
		
		if(task == null) {
			throw new TaskNotFoundException(name);
		}
		
		return task;
	}
}
