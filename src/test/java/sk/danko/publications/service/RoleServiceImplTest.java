package sk.danko.publications.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import sk.danko.publications.entity.RoleEntity;
import sk.danko.publications.model.RoleModel;
import sk.danko.publications.repository.RoleRepository;
import sk.danko.publications.service.impl.RoleServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity sampleEntity;
    private RoleModel sampleModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleEntity = new RoleEntity();
        sampleEntity.setId(1L);
        sampleEntity.setRoleName("ADMIN");

        sampleModel = new RoleModel();
        sampleModel.setId(1L);
        sampleModel.setRoleName("ADMIN");
    }

    @Test
    void testCreateRole() {
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(sampleEntity);

        RoleModel result = roleService.createRole(sampleModel);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ADMIN", result.getRoleName());
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }

    @Test
    void testGetAllRoles() {
        RoleEntity role2 = new RoleEntity();
        role2.setId(2L);
        role2.setRoleName("USER");

        when(roleRepository.findAll()).thenReturn(List.of(sampleEntity, role2));

        List<RoleModel> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
        assertEquals("USER", result.get(1).getRoleName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetRoleById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));

        RoleModel result = roleService.getRoleById(1L);

        assertNotNull(result);
        assertEquals("ADMIN", result.getRoleName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteRoleById() {
        doNothing().when(roleRepository).deleteById(1L);

        roleService.deleteRoleById(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }
}
