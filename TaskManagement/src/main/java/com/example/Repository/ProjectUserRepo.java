package com.example.Repository;

import com.example.Model.ProjectUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectUserRepo extends CrudRepository<ProjectUser,Integer> {
    @Query(value =" select u.username from project_user as pu inner join user as u on pu.user_id=u.id where pu.project_id=?",nativeQuery = true )
       List<String> getProjectUsers(Integer projectId);
}
