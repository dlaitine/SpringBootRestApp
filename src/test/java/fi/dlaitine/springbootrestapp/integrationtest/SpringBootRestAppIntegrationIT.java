package fi.dlaitine.springbootrestapp.integrationtest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import fi.dlaitine.springbootrestapp.Application;
import fi.dlaitine.springbootrestapp.dto.TaskRequest;
import fi.dlaitine.springbootrestapp.dto.TaskResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringBootRestAppIntegrationIT {
	
	private static final String TASKS_URL = "/api/v1/tasks/";

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void findAllTest() {
		ResponseEntity<TaskResponse[]> taskEntity = restTemplate.getForEntity(TASKS_URL, TaskResponse[].class);
		List<TaskResponse> taskResponses = Arrays.asList(taskEntity.getBody());
		
		assertEquals(2, taskResponses.size());
	}
	
	@Test
	public void postTaskTest() {
		
		ResponseEntity<TaskResponse> taskEntity = restTemplate.postForEntity(TASKS_URL, new TaskRequest("Test GET", "Create integration test for GET", false), TaskResponse.class);
		TaskResponse taskResponse = taskEntity.getBody();
		
		assertEquals(HttpStatus.OK, taskEntity.getStatusCode());
		assertEquals("Test GET", taskResponse.getName());
		assertEquals("Create integration test for GET", taskResponse.getDescription());
		assertEquals(false, taskResponse.isDone());
	}
	
	@Test
	public void deleteTaskTest() {
		restTemplate.delete(TASKS_URL + "Add integration tests");
		ResponseEntity<TaskResponse[]> taskEntity = restTemplate.getForEntity(TASKS_URL, TaskResponse[].class);
		List<TaskResponse> taskResponses = Arrays.asList(taskEntity.getBody());
		
		assertEquals(HttpStatus.OK, taskEntity.getStatusCode());
		assertEquals(1, taskResponses.size());
		assertEquals("Create REST service", taskResponses.get(0).getName());
	}

	@Test
	public void updateTaskTest() {
		TaskResponse originalTask = restTemplate.getForObject(TASKS_URL + "Add integration tests", TaskResponse.class);
		
		assertEquals("Add integration tests", originalTask.getName());
		assertEquals(false, originalTask.isDone());
		long id = originalTask.getId();
		
		restTemplate.put(TASKS_URL + "Add integration tests", new TaskRequest("Add integration tests", "Add integration tests to REST service", true));
		TaskResponse updatedTask = restTemplate.getForObject(TASKS_URL + "Add integration tests", TaskResponse.class);
		
		assertEquals("Add integration tests", updatedTask.getName());
		assertEquals(true, updatedTask.isDone());
		assertEquals(id, updatedTask.getId());
	}

	@Test
	public void postTaskWithTooShortNameTest() {
		ResponseEntity<TaskResponse> taskResponse = restTemplate.postForEntity(TASKS_URL, new TaskRequest("S", "Test too short name", false), TaskResponse.class);
		HttpStatus status = taskResponse.getStatusCode();
		assertEquals(HttpStatus.BAD_REQUEST, status);
	}

	@Test
	public void postTaskWithTooLongNameTest() {
		ResponseEntity<TaskResponse> taskResponse = restTemplate.postForEntity(TASKS_URL, new TaskRequest(
				"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL",
				"Test too long name",
				false), TaskResponse.class);
		HttpStatus status = taskResponse.getStatusCode();
		assertEquals(HttpStatus.BAD_REQUEST, status);
	}
}
