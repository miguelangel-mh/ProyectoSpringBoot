package com.myportfolio.my_portfolio_backend.service;


import com.myportfolio.my_portfolio_backend.entity.Experience;
import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.repository.IExperienceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ExperienceServiceImplTest {

    @Mock
    private IExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    @Test
    void saveTest() {
        Experience experience = createExperience();

        when(experienceRepository.save(experience)).thenReturn(experience);

        Experience result = experienceService.save(experience);

        assertNotNull(result);
        assertEquals("Desarrollador Web", result.getTitulo());
        assertEquals("Empresa Tech", result.getEmpresa());
        assertEquals(LocalDate.of(2023, 1, 1), result.getInicio());
        verify(experienceRepository, times(1)).save(experience);
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Experience experience = createExperienceWithId(id);

        when(experienceRepository.findById(id)).thenReturn(Optional.of(experience));

        Optional<Experience> result = experienceService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Desarrollador Web", result.get().getTitulo());
        verify(experienceRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotExistTest() {
        Long id = 99L;

        when(experienceRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Experience> result = experienceService.findById(id);

        assertTrue(result.isEmpty());
        verify(experienceRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() {
        Experience experience1 = createExperienceWithId(1L);
        Experience experience2 = createExperienceWithId(2L);
        experience2.setTitulo("Backend Developer");
        experience2.setEmpresa("Startup X");

        when(experienceRepository.findAll()).thenReturn(List.of(experience1, experience2));

        List<Experience> result = experienceService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Desarrollador Web", result.get(0).getTitulo());
        assertEquals("Backend Developer", result.get(1).getTitulo());
        verify(experienceRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;

        doNothing().when(experienceRepository).deleteById(id);

        experienceService.deleteById(id);

        verify(experienceRepository, times(1)).deleteById(id);
    }

    @Test
    void findByPersonalInfoIdTest() {
        Long personalInfoId = 1L;
        Experience experience1 = createExperienceWithId(1L);
        Experience experience2 = createExperienceWithId(2L);
        experience2.setTitulo("Analista de Datos");

        when(experienceRepository.findByPersonalInfoId(personalInfoId))
                .thenReturn(List.of(experience1, experience2));

        List<Experience> result = experienceService.findByPersonalInfoId(personalInfoId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Desarrollador Web", result.get(0).getTitulo());
        assertEquals("Analista de Datos", result.get(1).getTitulo());
        verify(experienceRepository, times(1)).findByPersonalInfoId(personalInfoId);
    }

    @Test
    void updateTest() {
        Long id = 1L;
        Experience experienceDb = createOldExperience(id);
        Experience updatedExperience = createUpdatedExperience();

        when(experienceRepository.findById(id)).thenReturn(Optional.of(experienceDb));
        when(experienceRepository.save(any(Experience.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Experience> result = experienceService.update(id, updatedExperience);

        assertTrue(result.isPresent());
        assertEquals("Ingeniero de Software", result.get().getTitulo());
        assertEquals("Empresa Global", result.get().getEmpresa());
        assertEquals(LocalDate.of(2024, 2, 1), result.get().getInicio());
        assertEquals(LocalDate.of(2025, 3, 1), result.get().getFin());
        assertEquals("Desarrollo de aplicaciones empresariales", result.get().getDescripcion());
        assertEquals(updatedExperience.getPersonalInfo(), result.get().getPersonalInfo());

        verify(experienceRepository, times(1)).findById(id);
        verify(experienceRepository, times(1)).save(experienceDb);
    }

    @Test
    void updateNotExistTest() {
        Long id = 99L;
        Experience updatedExperience = createUpdatedExperience();

        when(experienceRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Experience> result = experienceService.update(id, updatedExperience);

        assertTrue(result.isEmpty());
        verify(experienceRepository, times(1)).findById(id);
        verify(experienceRepository, never()).save(any(Experience.class));
    }

    private Experience createExperience() {
        Experience experience = new Experience();
        experience.setTitulo("Desarrollador Web");
        experience.setEmpresa("Empresa Tech");
        experience.setInicio(LocalDate.of(2023, 1, 1));
        experience.setFin(LocalDate.of(2024, 1, 1));
        experience.setDescripcion("Desarrollo y mantenimiento de aplicaciones web");
        experience.setPersonalInfo(createPersonalInfo(1L));
        return experience;
    }

    private Experience createExperienceWithId(Long id) {
        Experience experience = createExperience();
        experience.setId(id);
        return experience;
    }

    private Experience createOldExperience(Long id) {
        Experience experience = new Experience();
        experience.setId(id);
        experience.setTitulo("Prácticas");
        experience.setEmpresa("Empresa Local");
        experience.setInicio(LocalDate.of(2022, 6, 1));
        experience.setFin(LocalDate.of(2022, 9, 1));
        experience.setDescripcion("Tareas básicas de apoyo");
        experience.setPersonalInfo(createPersonalInfo(1L));
        return experience;
    }

    private Experience createUpdatedExperience() {
        Experience experience = new Experience();
        experience.setTitulo("Ingeniero de Software");
        experience.setEmpresa("Empresa Global");
        experience.setInicio(LocalDate.of(2024, 2, 1));
        experience.setFin(LocalDate.of(2025, 3, 1));
        experience.setDescripcion("Desarrollo de aplicaciones empresariales");
        experience.setPersonalInfo(createPersonalInfo(2L));
        return experience;
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
