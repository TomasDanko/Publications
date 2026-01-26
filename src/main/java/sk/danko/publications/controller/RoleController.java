package sk.danko.publications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.danko.publications.model.RoleModel;
import sk.danko.publications.service.api.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "*")
public class RoleController {

    public static final String CREATE_ROLE_URI = "/roles";
    public static final String ROLE_LIST_URI = "/roleList";
    public static final String DELETE_URI = "/roles/{userId}/{roleId}";

    @Autowired
    private RoleService roleService;

    @PostMapping(CREATE_ROLE_URI)
    public RoleModel createRole(@RequestBody RoleModel roleModel){
        return roleService.createRole(roleModel);
    }

    @GetMapping(ROLE_LIST_URI)
    public List<RoleModel> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasRole('ADMIN') or principal.userId == #userId")
    @DeleteMapping(DELETE_URI)
    public void deleteRole(@PathVariable Long userId, @PathVariable Long roleId){
        roleService.deleteRoleById(roleId);
    }

}
