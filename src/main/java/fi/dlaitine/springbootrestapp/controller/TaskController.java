package fi.dlaitine.springbootrestapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.dlaitine.springbootrestapp.model.TaskRequest;
import fi.dlaitine.springbootrestapp.model.TaskResponse;
import fi.dlaitine.springbootrestapp.service.TaskService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET) 
	@ApiOperation(value = "Find all tasks", notes = "Retrieves a list of all tasks", response = TaskResponse[].class)
	public List<TaskResponse> findAll() {
		return service.findAll();		
	}
	
	@RequestMapping(value = "/findByName/{name}", method = RequestMethod.GET)
	@ApiOperation(value = "Find task", notes = "Retrieves a specific task, identified by name", response = TaskResponse.class)
	public TaskResponse findByName(@PathVariable String name) {
		return service.findByName(name);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "Save new task", notes = "Saves a new task to database", response = TaskResponse.class)
	public TaskResponse save(@Valid @RequestBody TaskRequest task) {
		return service.save(task);
	}
	
	@RequestMapping(value = "/update/{name}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update task", notes = "Updates a specific task, identified by name", response = TaskResponse.class)
	public TaskResponse update(@PathVariable String name, @Valid @RequestBody TaskRequest task) {
		return service.update(name, task);
	}
	
	@RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete task", notes = "Deletes a specific task, identified by name")
	public void delete(@PathVariable String name) {
		service.delete(name);		
	}
}
