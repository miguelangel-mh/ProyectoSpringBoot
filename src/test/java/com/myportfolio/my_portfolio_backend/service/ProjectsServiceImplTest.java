package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.entity.Projects;
import com.myportfolio.my_portfolio_backend.repository.IProjectsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectsServiceImplTest {

    @Mock
    private IProjectsRepository projectsRepository;

    @InjectMocks
    private ProjectsServiceImpl projectsService;

    @Test
    void saveTest() {
        Projects project = createProject();

        when(projectsRepository.save(project)).thenReturn(project);

        Projects result = projectsService.save(project);

        assertNotNull(result);
        assertEquals("Portfolio Web", result.getTitulo());
        assertEquals("Proyecto personal con Spring Boot", result.getDescripcion());
        assertEquals("/img/project.png", result.getImagenUrl());
        assertEquals("https://miportfolio.com", result.getProyectoUrl());
        verify(projectsRepository, times(1)).save(project);
    }

    @Test
    void findByITest() {
        Long id = 1L;
        Projects project = createProjectWithId(id);

        when(projectsRepository.findById(id)).thenReturn(Optional.of(project));

        Optional<Projects> result = projectsService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Portfolio Web", result.get().getTitulo());
        verify(projectsRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotExistTest() {
        Long id = 99L;

        when(projectsRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Projects> result = projectsService.findById(id);

        assertTrue(result.isEmpty());
        verify(projectsRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() {
        Projects project1 = createProjectWithId(1L);
        Projects project2 = createProjectWithId(2L);
        project2.setTitulo("Triptagram");
        project2.setDescripcion("App social de viajes");

        when(projectsRepository.findAll()).thenReturn(List.of(project1, project2));

        List<Projects> result = projectsService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Portfolio Web", result.get(0).getTitulo());
        assertEquals("Triptagram", result.get(1).getTitulo());
        verify(projectsRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;

        doNothing().when(projectsRepository).deleteById(id);

        projectsService.deleteById(id);

        verify(projectsRepository, times(1)).deleteById(id);
    }

    @Test
    void findByPersonalInfoIdTest() {
        Long personalInfoId = 1L;
        Projects project1 = createProjectWithId(1L);
        Projects project2 = createProjectWithId(2L);
        project2.setTitulo("FurgoBebidas");

        when(projectsRepository.findByPersonalInfoId(personalInfoId))
                .thenReturn(List.of(project1, project2));

        List<Projects> result = projectsService.findByPersonalInfoId(personalInfoId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Portfolio Web", result.get(0).getTitulo());
        assertEquals("FurgoBebidas", result.get(1).getTitulo());
        verify(projectsRepository, times(1)).findByPersonalInfoId(personalInfoId);
    }

    @Test
    void existsByPersonalInfoIdTest() {
        Long personalInfoId = 1L;

        when(projectsRepository.existsByPersonalInfoId(personalInfoId)).thenReturn(true);

        boolean result = projectsService.existsByPersonalInfoId(personalInfoId);

        assertTrue(result);
        verify(projectsRepository, times(1)).existsByPersonalInfoId(personalInfoId);
    }

    @Test
    void existsByPersonalInfoIdNotExistTest() {
        Long personalInfoId = 99L;

        when(projectsRepository.existsByPersonalInfoId(personalInfoId)).thenReturn(false);

        boolean result = projectsService.existsByPersonalInfoId(personalInfoId);

        assertFalse(result);
        verify(projectsRepository, times(1)).existsByPersonalInfoId(personalInfoId);
    }

    @Test
    void updateTest() {
        Long id = 1L;
        Projects projectDb = createOldProject(id);
        Projects updatedProject = createUpdatedProject();

        when(projectsRepository.findById(id)).thenReturn(Optional.of(projectDb));
        when(projectsRepository.save(any(Projects.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Projects> result = projectsService.update(id, updatedProject);

        assertTrue(result.isPresent());
        assertEquals("Triptagram", result.get().getTitulo());
        assertEquals("Red social para compartir fotos de viajes", result.get().getDescripcion());
        assertEquals("/img/triptagram.png", result.get().getImagenUrl());
        assertEquals("https://triptagram.com", result.get().getProyectoUrl());
        assertEquals(updatedProject.getPersonalInfo(), result.get().getPersonalInfo());

        verify(projectsRepository, times(1)).findById(id);
        verify(projectsRepository, times(1)).save(projectDb);
    }

    @Test
    void updateNotExistTest() {
        Long id = 99L;
        Projects updatedProject = createUpdatedProject();

        when(projectsRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Projects> result = projectsService.update(id, updatedProject);

        assertTrue(result.isEmpty());
        verify(projectsRepository, times(1)).findById(id);
        verify(projectsRepository, never()).save(any(Projects.class));
    }

    private Projects createProject() {
        Projects project = new Projects();
        project.setTitulo("Portfolio Web");
        project.setDescripcion("Proyecto personal con Spring Boot");
        project.setImagenUrl("/img/project.png");
        project.setProyectoUrl("https://miportfolio.com");
        project.setPersonalInfo(createPersonalInfo(1L));
        return project;
    }

    private Projects createProjectWithId(Long id) {
        Projects project = createProject();
        project.setId(id);
        return project;
    }

    private Projects createOldProject(Long id) {
        Projects project = new Projects();
        project.setId(id);
        project.setTitulo("Proyecto Antiguo");
        project.setDescripcion("Descripción antigua");
        project.setImagenUrl("/img/old.png");
        project.setProyectoUrl("https://oldproject.com");
        project.setPersonalInfo(createPersonalInfo(1L));
        return project;
    }

    private Projects createUpdatedProject() {
        Projects project = new Projects();
        project.setTitulo("Triptagram");
        project.setDescripcion("Red social para compartir fotos de viajes");
        project.setImagenUrl("/img/triptagram.png");
        project.setProyectoUrl("https://triptagram.com");
        project.setPersonalInfo(createPersonalInfo(2L));
        return project;
    }

    private PersonalInfo createPersonalInfo(Long id) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(id);
        personalInfo.setNombre("Miguel Ángel");
        personalInfo.setApellidos("Martínez Herrera");
        personalInfo.setTitulo("Full Stack Developer");
        personalInfo.setDescripcion("Aprendiendo y mejorando cada día");
        personalInfo.setTelefono("612345678");
        personalInfo.setEmail("miguel@email.com");
        personalInfo.setImagen("/img/perfil.png");
        personalInfo.setLinkedinUrl("https://linkedin.com/in/miguel");
        return personalInfo;
    }

}
