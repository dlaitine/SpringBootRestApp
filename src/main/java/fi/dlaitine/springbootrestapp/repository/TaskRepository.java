package fi.dlaitine.springbootrestapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fi.dlaitine.springbootrestapp.entity.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	
	Task findByName(String name);
	
	boolean existsByName(String name);
}
