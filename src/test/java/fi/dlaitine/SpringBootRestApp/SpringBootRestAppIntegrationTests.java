package fi.dlaitine.SpringBootRestApp;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import fi.dlaitine.SpringBootRestApp.Application;
import fi.dlaitine.SpringBootRestApp.models.TaskRequest;
import fi.dlaitine.SpringBootRestApp.models.TaskResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringBootRestAppIntegrationTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void findAllTest() {
		ResponseEntity<TaskResponse[]> taskEntity = restTemplate.getForEntity("/api/tasks/findAll", TaskResponse[].class);
		List<TaskResponse> taskResponses = Arrays.asList(taskEntity.getBody());
		
		assertEquals(2, taskResponses.size());
	}
	
	@Test
	public void postTaskTest() {
		
		ResponseEntity<TaskResponse> taskEntity = restTemplate.postForEntity("/api/tasks/save/", new TaskRequest("Test GET", "Create integration test for GET", false), TaskResponse.class);
		TaskResponse taskResponse = taskEntity.getBody();
		
		assertEquals(HttpStatus.OK, taskEntity.getStatusCode());
		assertEquals("Test GET", taskResponse.getName());
		assertEquals("Create integration test for GET", taskResponse.getDescription());
		assertEquals(false, taskResponse.isDone());
	}
	
	@Test
	public void deleteTaskTest() {
		restTemplate.delete("/api/tasks/delete/Add integration tests");
		ResponseEntity<TaskResponse[]> taskEntity = restTemplate.getForEntity("/api/tasks/findAll", TaskResponse[].class);
		List<TaskResponse> taskResponses = Arrays.asList(taskEntity.getBody());
		
		assertEquals(1, taskResponses.size());
		assertEquals("Create REST service", taskResponses.get(0).getName());
	}
	
	@Test
	public void updateTaskTest() {
		TaskResponse originalTask = restTemplate.getForObject("/api/tasks/findByName/Add integration tests", TaskResponse.class);
		
		assertEquals("Add integration tests", originalTask.getName());
		assertEquals(false, originalTask.isDone());
		long id = originalTask.getId();
		
		restTemplate.put("/api/tasks/update/Add integration tests", new TaskRequest("Add integration tests", "Add integration tests to REST service", true));
		TaskResponse updatedTask = restTemplate.getForObject("/api/tasks/findByName/Add integration tests", TaskResponse.class);
		
		assertEquals("Add integration tests", updatedTask.getName());
		assertEquals(true, updatedTask.isDone());
		assertEquals(id, updatedTask.getId());
	}
}
