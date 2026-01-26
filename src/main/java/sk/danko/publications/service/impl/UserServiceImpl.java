package sk.danko.publications.service.impl;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sk.danko.publications.entity.RoleEntity;
import sk.danko.publications.entity.UserEntity;
import sk.danko.publications.model.RoleModel;
import sk.danko.publications.model.UserModel;
import sk.danko.publications.repository.RoleRepository;
import sk.danko.publications.repository.UserRepository;
import sk.danko.publications.service.api.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl{

//    @Autowired
//    private final UserRepository userRepository;
//
//    @Autowired
//    private final RoleRepository roleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//
//
//    public UserModel register(UserModel userModel){
//
//        UserEntity userEntity = new UserEntity();
//        BeanUtils.copyProperties(userModel, userEntity);
//
//        Set<RoleEntity> entities = new HashSet<>();
//
//        for(RoleModel roleModel : userModel.getRoles()){
//            Optional<RoleEntity> optionalRole = roleRepository.findById(roleModel.getId());
//            if(optionalRole.isPresent()){
//                entities.add(optionalRole.get());
//            }
//        }
//        userEntity.setRoles(entities);
//        userEntity.setPassword(this.passwordEncoder.encode(userModel.getPassword()));
//        userEntity = userRepository.save(userEntity);
//
//        BeanUtils.copyProperties(userEntity, userModel);
//
//        Set<RoleModel> roleModels = new HashSet<>();
//        RoleModel roleModel = null;
//        for(RoleEntity re : userEntity.getRoles()){
//            roleModel = new RoleModel();
//            roleModel.setRoleName(re.getRoleName());
//            roleModel.setId(re.getId());
//            roleModels.add(roleModel);
//        }
//        userModel.setRoles(roleModels);
//        return userModel;
//    }
//
//    public Iterable<UserEntity> getList() {
//        return userRepository.findAll();
//    }
}

