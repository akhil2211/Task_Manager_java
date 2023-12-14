package com.example.Repository;

import com.example.Model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;


public interface TaskRepo extends CrudRepository<Task, Integer> {
    @Query(value = "select* from task where t_code=?", nativeQuery = true)
    Task findByTaskCode(String t_code);
    @Query(value = "select* from task where project_id=?", nativeQuery = true)
    List<Task> findByProject(Integer projectId);
    @Query(value = "select* from task where t_status=? order by created_at DESC", nativeQuery = true)
    List<Task> findByTaskStatus(String tStatus);
    @Query(value = "select t.*,a.assigned_to,u.firstname from taskmanagerdb.assignment as a INNER JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id  WHERE assignee_id=?", nativeQuery = true)
    List<Map<String,Object>> findByAssignee(Integer tAssignee);
    @Query(value="select t.* from assignment as a INNER JOIN task as t on a.task_id=t.id WHERE assigned_to=?",nativeQuery = true)
    List<Task> findByAssigned(Integer tAssigned);
}
