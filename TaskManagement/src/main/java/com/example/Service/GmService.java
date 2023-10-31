package com.example.Service;

import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service

public class GmService {

    private final ProjectRepo projectRepo;
    private final UserRepository userRepository;
    private final ProjectUserRepo projectUserRepo;

    private final OrgProjectRepo orgProjectRepo;
    private final OrganizationRepo organizationRepo;

    @Autowired
    public GmService(ProjectRepo projectRepo, UserRepository userRepository, ProjectUserRepo projectUserRepo, OrgProjectRepo orgProjectRepo, OrganizationRepo organizationRepo) {
        this.projectRepo = projectRepo;
        this.userRepository = userRepository;
        this.projectUserRepo = projectUserRepo;
        this.orgProjectRepo = orgProjectRepo;
        this.organizationRepo = organizationRepo;
    }
    public String createProject(Project project, Integer orgId) {

        Project projectdata=new Project();
        projectdata.setProject_code(project.getProject_code());
        projectdata.setProject_name(project.getProject_name());
        projectdata.setProject_description(project.getProject_description());
        projectdata.setProject_status(project.getProject_status());
        projectdata.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        projectdata.setDue_date(project.getDue_date());

        projectRepo.save(projectdata);
        OrgProject orgProject=new OrgProject();
        Organization organization=organizationRepo.findById(orgId).orElse(null);
        orgProject.setOrg(organization);
        orgProject.setProj(projectdata);
        orgProjectRepo.save(orgProject);
        return "Project Created Successfully";

    }


    public String assignProject(Integer projectId, List<Integer> userIds) {
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project != null) {
            for (Integer userId : userIds) {
                String role = userRepository.getUserRole(userId);
                if (UserRoles.GM.toString().equals(role) || UserRoles.ADMIN.toString().equals(role)) {
                    return "Cannot project assign to GM or Admin!";
                } else {
                    User user = userRepository.findById(userId).orElse(null);

                    ProjectUser projectUser = new ProjectUser();
                    projectUser.setProject(project);
                    projectUser.setUser(user);
                    projectUser.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    projectUserRepo.save(projectUser);
                }
            }
                return "Project Assignment to Users Successful!";
            }
        else{
                return "No Project Found !";
            }
        }
    public Iterable<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public List<Project> getProjectbyStatus(String projStatus) {
        return projectRepo.findByProjectStatus(projStatus);
    }
    public List<Map<String, Object>> getProjectByOrganization(Integer org_id) {
        return orgProjectRepo.findProjectByOrganization(org_id);
    }
}
