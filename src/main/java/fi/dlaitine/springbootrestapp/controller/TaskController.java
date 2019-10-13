package fi.dlaitine.springbootrestapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.dlaitine.springbootrestapp.model.TaskRequest;
import fi.dlaitine.springbootrestapp.model.TaskResponse;
import fi.dlaitine.springbootrestapp.model.exception.TaskAlreadyExistsException;
import fi.dlaitine.springbootrestapp.model.exception.TaskNotFoundException;
import fi.dlaitine.springbootrestapp.service.TaskService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	@GetMapping(value = "/")
	@ApiOperation(value = "Find all tasks", notes = "Retrieves a list of all tasks", response = TaskResponse[].class)
	public ResponseEntity<List<TaskResponse>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{name}")
	@ApiOperation(value = "Find task", notes = "Retrieves a specific task, identified by name", response = TaskResponse.class)
	public ResponseEntity<TaskResponse> findByName(@PathVariable String name) {
		return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
	}
	
	@PostMapping(value = "/")
	@ApiOperation(value = "Save new task", notes = "Saves a new task to database", response = TaskResponse.class)
	public ResponseEntity<TaskResponse> save(@Valid @RequestBody TaskRequest task) {
		return new ResponseEntity<>(service.save(task), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{name}")
	@ApiOperation(value = "Update task", notes = "Updates a specific task, identified by name", response = TaskResponse.class)
	public ResponseEntity<TaskResponse> update(@PathVariable String name, @Valid @RequestBody TaskRequest task) {
		return new ResponseEntity<>(service.update(name, task), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{name}")
	@ApiOperation(value = "Delete task", notes = "Deletes a specific task, identified by name")
	public ResponseEntity<Void> delete(@PathVariable String name) {
		service.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ExceptionHandler(TaskAlreadyExistsException.class)
	public ResponseEntity<Void> handleException(TaskAlreadyExistsException e) {
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<Void> handleException(TaskNotFoundException e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Void> handleException(MethodArgumentNotValidException e) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleException(Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
