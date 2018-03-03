package fi.dlaitine.SpringBootRestApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fi.dlaitine.SpringBootRestApp.models.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, String> {
	
	Task findByName(@Param("name") String name);
	
	boolean existsByName(@Param("name") String name);
}
