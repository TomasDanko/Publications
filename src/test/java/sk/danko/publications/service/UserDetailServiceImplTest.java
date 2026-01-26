package sk.danko.publications.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sk.danko.publications.entity.RoleEntity;
import sk.danko.publications.entity.UserEntity;
import sk.danko.publications.exception.UserNotFoundException;
import sk.danko.publications.model.RoleModel;
import sk.danko.publications.model.UserModel;
import sk.danko.publications.repository.RoleRepository;
import sk.danko.publications.repository.UserRepository;
import sk.danko.publications.service.impl.UserDetailServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDetailServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    private UserEntity userEntity;
    private RoleEntity roleEntity;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setRoleName("ADMIN");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("tomas");
        userEntity.setPassword("pass");
        userEntity.setRoles(Set.of(roleEntity));

        RoleModel roleModel = new RoleModel();
        roleModel.setId(1L);
        roleModel.setRoleName("ADMIN");

        userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUsername("tomas");
        userModel.setPassword("pass");
        userModel.setRoles(Set.of(roleModel));
    }

    @Test
    void testRegisterUser() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode("pass")).thenReturn("pass");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserModel result = userDetailService.register(userModel);

        assertNotNull(result);
        assertEquals("tomas", result.getUsername());
        assertEquals(1, result.getRoles().size());
        verify(passwordEncoder).encode("pass");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testGetList() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        Iterable<UserEntity> result = userDetailService.getList();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(userRepository).findAll();
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        UserModel result = (UserModel) userDetailService.loadUserByUsername("tomas");

        assertNotNull(result);
        assertEquals("tomas", result.getUsername());
        assertEquals(1, result.getRoles().size());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(UserNotFoundException.class, () ->
                userDetailService.loadUserByUsername("username_not_found")
        );
    }
}
