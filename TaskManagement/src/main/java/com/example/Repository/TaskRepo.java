package com.example.Repository;

import com.example.Model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TaskRepo extends CrudRepository<Task, Integer> {
    @Query(value = "select* from task where t_code=?", nativeQuery = true)
    Task findByTaskCode(String t_code);

    @Query(value = "select* from task where project_id=?", nativeQuery = true)
    List<Task> findByProject(Integer projectId);
    @Query(value = "select* from task where t_status=? order by created_at order by DESC", nativeQuery = true)
    List<Task> findByTaskStatus(String tStatus);
}
