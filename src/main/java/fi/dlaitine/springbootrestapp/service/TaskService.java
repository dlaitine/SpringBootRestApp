package fi.dlaitine.springbootrestapp.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.dlaitine.springbootrestapp.dto.TaskRequest;
import fi.dlaitine.springbootrestapp.dto.TaskResponse;
import fi.dlaitine.springbootrestapp.entity.TaskEntity;
import fi.dlaitine.springbootrestapp.exception.TaskAlreadyExistsException;
import fi.dlaitine.springbootrestapp.exception.TaskNotFoundException;
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
		TaskEntity task = findTask(name);

		return new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.isDone(), task.getCreatedAt());
	}
	
	@Transactional
	public TaskResponse save(TaskRequest task) {
		TaskEntity newTask = new TaskEntity(task.getName(), task.getDescription(), task.isDone());
		
		try {
			TaskEntity savedTask = repository.save(newTask);
			return new TaskResponse(savedTask.getId(), savedTask.getName(), savedTask.getDescription(), savedTask.isDone(), savedTask.getCreatedAt());
		}
		catch (DataIntegrityViolationException e) {
			throw new TaskAlreadyExistsException(task.getName());
		}
	}
	
	@Transactional
	public TaskResponse update(String name, TaskRequest newTask) {
		TaskEntity task = findTask(name);

		if(!name.equals(newTask.getName()) && repository.existsByName(newTask.getName())) {
			throw new TaskAlreadyExistsException(newTask.getName());
		}
		
		task.setName(newTask.getName());
		task.setDescription(newTask.getDescription());
		task.setDone(newTask.isDone());
		TaskEntity updatedTask = repository.save(task);
		
		return new TaskResponse(updatedTask.getId(), updatedTask.getName(), updatedTask.getDescription(), updatedTask.isDone(), updatedTask.getCreatedAt());
	}
	
	@Transactional
	public void delete(String name) {
		TaskEntity task = findTask(name);
		repository.delete(task);
	}
	
	private TaskEntity findTask(String name) {
		TaskEntity task = repository.findByName(name);
		
		if(task == null) {
			throw new TaskNotFoundException(name);
		}
		
		return task;
	}
}
