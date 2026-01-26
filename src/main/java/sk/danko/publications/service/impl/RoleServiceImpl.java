package sk.danko.publications.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.danko.publications.entity.RoleEntity;
import sk.danko.publications.model.RoleModel;
import sk.danko.publications.repository.RoleRepository;
import sk.danko.publications.service.api.RoleService;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public RoleModel createRole(RoleModel roleModel) {

        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleModel, roleEntity);

        RoleEntity roleEntity1 = roleRepository.save(roleEntity);

        BeanUtils.copyProperties(roleEntity1, roleModel);

        return roleModel;
    }

    @Override
    public List<RoleModel> getAllRoles() {
        Iterable<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleModel> roleModels = new ArrayList<>();
        RoleModel roleModel = null;
        for(RoleEntity re : roleEntities){
            roleModel = new RoleModel();
            BeanUtils.copyProperties(re, roleModel);
            roleModels.add(roleModel);
        }
        return roleModels;
    }

    @Override
    public RoleModel getRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).get();
        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties(roleEntity, roleModel);
        return roleModel;
    }

    @Override
    public void deleteRoleById(Long roleId) {
        this.roleRepository.deleteById(roleId);

    }
}
