package fi.dlaitine.springbootrestapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.HttpStatusCodeException;

import fi.dlaitine.springbootrestapp.dto.TaskRequest;
import fi.dlaitine.springbootrestapp.dto.TaskResponse;
import fi.dlaitine.springbootrestapp.exception.TaskAlreadyExistsException;
import fi.dlaitine.springbootrestapp.exception.TaskNotFoundException;
import fi.dlaitine.springbootrestapp.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	private TaskService service;

	public TaskController(TaskService service) {
		this.service = service;
	}

	@GetMapping(value = "")
	@ApiOperation(value = "Find all tasks", notes = "Retrieves a list of all tasks", response = TaskResponse[].class)
	public ResponseEntity<List<TaskResponse>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{name}")
	@ApiOperation(value = "Find task", notes = "Retrieves a specific task, identified by name", response = TaskResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Task not found")
	})
	public ResponseEntity<TaskResponse> findByName(@PathVariable String name) {
		return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
	}
	
	@PostMapping(value = "")
	@ApiOperation(value = "Save new task", notes = "Saves a new task to database", response = TaskResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Task with the same name already exists"),
			@ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid name length (must be between 4 and 64 characters)")
	})
	public ResponseEntity<TaskResponse> save(@Valid @RequestBody TaskRequest task) {
		return new ResponseEntity<>(service.save(task), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{name}")
	@ApiOperation(value = "Update task", notes = "Updates a specific task, identified by name", response = TaskResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Task with the same name already exists")
	})
	public ResponseEntity<TaskResponse> update(@PathVariable String name, @Valid @RequestBody TaskRequest task) {
		return new ResponseEntity<>(service.update(name, task), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{name}")
	@ApiOperation(value = "Delete task", notes = "Deletes a specific task, identified by name")
	@ApiResponses(value = {
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Task not found")
	})
	public ResponseEntity<Void> delete(@PathVariable String name) {
		service.delete(name);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ExceptionHandler(TaskAlreadyExistsException.class)
	public ResponseEntity<String> handleException(TaskAlreadyExistsException e) {
		LOGGER.error("Task API processing failed with exception: {}", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<String> handleException(TaskNotFoundException e) {
		LOGGER.error("Task API processing failed with exception: {}", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
		LOGGER.error("Task API processing failed with exception: {}", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleException(Exception e) {
		LOGGER.error("Task API processing failed with exception: {}", e.getMessage());
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
