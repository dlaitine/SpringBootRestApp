package fi.dlaitine.SpringBootRestApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fi.dlaitine.SpringBootRestApp.models.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, String> {
	
	TaskEntity findByName(@Param("name") String name);
}
