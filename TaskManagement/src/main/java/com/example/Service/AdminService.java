package com.example.Service;

import com.example.Model.Organization;
import com.example.Repository.OrganizationRepo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service

public class AdminService {

    private final OrganizationRepo organizationRepo;

    public AdminService(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }

    public String createOrganziation(Map<String,String> orgRequest) {
        Organization organization=new Organization();
        organization.setOrg_code(orgRequest.get("name"));
        organization.setOrg_name(orgRequest.get("code"));
        organizationRepo.save(organization);
        return "Organization Added";
    }
}
