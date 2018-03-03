package fi.dlaitine.SpringBootRestApp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.dlaitine.SpringBootRestApp.models.TaskRequest;
import fi.dlaitine.SpringBootRestApp.models.TaskResponse;
import fi.dlaitine.SpringBootRestApp.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET) 
	public List<TaskResponse> findAll() {
		return service.findAll();		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public TaskResponse save(@Valid @RequestBody TaskRequest task) {
		return service.save(task);
	}
	
	@RequestMapping(value = "/update/{name}", method = RequestMethod.PUT)
	public TaskResponse update(@PathVariable String name, @Valid @RequestBody TaskRequest task) throws Exception {
		return service.update(name, task);
	}
	
	@RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String name) throws Exception {
		service.delete(name);		
	}
}
