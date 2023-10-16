//package com.example.Model;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//
//public enum UserRoles {
//    ADMIN(
//            Set.of(
//                    Permission.ADMIN_VIEWUSERS,
//                    Permission.ADMIN_ADDMANAGER
//            )
//    ),
//
//    GM(
//            Set.of(
//                    Permission.GM_VIEWTEAM,
//                    Permission.GM_ADDPM,
//                    Permission.GM_ASSIGNPROJECT
//            )
//    ),
//
//    PM(
//         Set.of(
//                 Permission.PM_ASSIGNTASK,
//                 Permission.PM_ADDMEMBER
//         )
//
//    ),
//
//    USER(Collections.emptySet());
//
//    ;
//    @Getter
//
//    private final Set<Permission> permissions;
//
//    public List<SimpleGrantedAuthority> getAuthoriries(){
//      var authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.name()))
//                .collect(Collectors.toList());
//      authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
//      return authorities;
//
//    }
//
//
//}
