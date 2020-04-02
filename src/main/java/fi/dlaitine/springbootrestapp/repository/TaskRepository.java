package fi.dlaitine.springbootrestapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import fi.dlaitine.springbootrestapp.entity.TaskEntity;

@Repository
@RepositoryRestResource(exported = false)
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
	
	TaskEntity findByName(String name);
	
	boolean existsByName(String name);
}
