package com.example.Repository;

import com.example.Model.Task;
import com.example.Model.User;
import com.fasterxml.jackson.core.sym.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
  @Query(value = "select* from user", nativeQuery = true)
  List<User> findAllUsers();
  Optional<User> findByUsername(String username);
  @Query(value = "select* from user where id=?", nativeQuery = true)
  User getUserById(Integer userId);
  @Query(value = "select* from user where org_id=?", nativeQuery = true)
  List<User> findByOrganization(Integer orgId);

  boolean existsByEmail(String email);
  boolean existsByUsername(String username);

  @Query(value="select roles from role r inner join user u on r.id=u.role_id where u.id=?",nativeQuery = true)
  String getUserRole(Integer userId);
    @Query(value="select * from user where reporting_officer_id=?",nativeQuery = true)
    List<User> findByReportingOfficer(Integer reportingOfficerId);
}
