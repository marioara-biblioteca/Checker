package checker.services;


import checker.entities.Role;
import checker.enums.RoleType;
import checker.exceptions.RoleAlreadyExistsException;
import checker.exceptions.RoleNotFoundException;
import checker.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(Long id) throws RoleNotFoundException {
        return roleRepository.findById(id).stream().findFirst().orElseThrow(() -> new RoleNotFoundException(id));
    }

    public void deleteRole(Role role) {
        this.roleRepository.delete(role);

    }
    public void deleteRoleById(Long id) throws RoleNotFoundException{
        try {
            this.roleRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new RoleNotFoundException(id);
        }

    }

    public void addRole(Role role) throws RoleAlreadyExistsException{
        List<Role> roles = roleRepository.findAll();
        for(Role r : roles){
            if(r.getRoleType().equals(role.getRoleType()))
                throw new RoleAlreadyExistsException(role.getRoleType().toString());
        }
        roleRepository.save(role);
    }

    public Role updateRole(Role newRole, Long id) throws RoleNotFoundException {

        Role updateRole = this.roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        updateRole.setRoleType(newRole.getRoleType());
        this.roleRepository.save(updateRole);
        return updateRole;
    }

    public Role getRoleByType(RoleType roleType) throws RoleNotFoundException {
        List<Role> roles = this.roleRepository.findAll();
        for(Role r : roles) {
            if((r.getRoleType().name()).equals(roleType.name()))
                return r;
        }
        throw new RoleNotFoundException(roleType.toString());
    }


}
