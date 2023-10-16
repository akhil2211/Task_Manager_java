package com.example.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum Permission {

    ADMIN_ADDMANAGER("admin:addManager"),
    ADMIN_VIEWUSERS("admin:viewUsers"),
    GM_VIEWTEAM("gm:viewTeam"),
    GM_ADDPM("gm:addPm"),
    GM_ASSIGNPROJECT("gm:assignProject"),

    PM_ADDMEMBER("pm:addMember"),

    PM_ASSIGNTASK("pm:assignTask");



    @Getter
    private  final String permission;
}
