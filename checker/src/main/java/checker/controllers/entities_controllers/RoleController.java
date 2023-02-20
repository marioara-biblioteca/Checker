package checker.controllers.entities_controllers;

import checker.entities.Role;
import checker.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    ResponseEntity<Object> getRoles() {
        List<Role> roles = this.roleService.getAllRoles();
        return new ResponseEntity<>(roles,HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    ResponseEntity<Object> getRoleById(@PathVariable Long id) {
       Role role = this.roleService.getRoleById(id);
       return new ResponseEntity<>(role,HttpStatus.OK);
    }


    @PostMapping("/roles")
    ResponseEntity<Object> addRole(@RequestBody Role role) {
        this.roleService.addRole(role);
        return new ResponseEntity<>("Role added succesfully",HttpStatus.CREATED);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<Object> updateRole(@RequestBody Role newRole, @PathVariable Long id) {
        roleService.updateRole(newRole, id);
        return new ResponseEntity<>("Role update succesfully", HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/roles/{id}")
    ResponseEntity<Object>deleteRole(@PathVariable Long id){
        this.roleService.deleteRoleById(id);
        return new ResponseEntity<>("Successfully deleted role",HttpStatus.OK);
    }
}
