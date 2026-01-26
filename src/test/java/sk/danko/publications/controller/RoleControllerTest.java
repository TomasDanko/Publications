package sk.danko.publications.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sk.danko.publications.model.RoleModel;
import sk.danko.publications.service.api.RoleService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Test bez security filtrov
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    void testCreateRole_success() throws Exception {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(1L);
        roleModel.setRoleName("ADMIN");

        when(roleService.createRole(any(RoleModel.class))).thenReturn(roleModel);

        mockMvc.perform(post("/api/role/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.roleName").value("ADMIN"));

        verify(roleService, times(1)).createRole(any(RoleModel.class));
    }

    @Test
    void testGetAllRoles_success() throws Exception {
        RoleModel r1 = new RoleModel();
        r1.setId(1L);
        r1.setRoleName("ADMIN");

        RoleModel r2 = new RoleModel();
        r2.setId(2L);
        r2.setRoleName("USER");

        when(roleService.getAllRoles()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/role/roleList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roleName").value("ADMIN"))
                .andExpect(jsonPath("$[1].roleName").value("USER"));

        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void testDeleteRole_success() throws Exception {
        // tu nepotrebujeme when(), lebo delete je void
        mockMvc.perform(delete("/api/role/roles/{userId}/{roleId}", 10L, 2L))
                .andExpect(status().isOk());

        verify(roleService, times(1)).deleteRoleById(2L);
    }
}
