package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Education;
import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.repository.IEducationRepository;
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
public class EducationServiceImplTest {

    @Mock
    private IEducationRepository educationRepository;

    @InjectMocks
    private EducationServiceImpl educationService;

    @Test
    void saveTest() {
        Education education = createEducation();

        when(educationRepository.save(education)).thenReturn(education);

        Education result = educationService.save(education);

        assertNotNull(result);
        assertEquals("Grado en Ingeniería Informática", result.getTitulo());
        assertEquals("Universidad de Granada", result.getCentro());
        assertEquals(LocalDate.of(2021, 9, 1), result.getInicio());
        verify(educationRepository, times(1)).save(education);
    }

    @Test
    void updateTest() {
        Long id = 1L;
        Education educationDb = createOldEducation(id);
        Education updatedEducation = createUpdatedEducation();

        when(educationRepository.findById(id)).thenReturn(Optional.of(educationDb));
        when(educationRepository.save(any(Education.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Education> result = educationService.update(id, updatedEducation);

        assertTrue(result.isPresent());
        assertEquals("Máster en Ingeniería del Software", result.get().getTitulo());
        assertEquals("Universidad de Sevilla", result.get().getCentro());
        assertEquals(LocalDate.of(2025, 9, 1), result.get().getInicio());
        assertEquals(LocalDate.of(2026, 6, 30), result.get().getFin());
        assertEquals("Formación avanzada en ingeniería del software", result.get().getDescripcion());
        assertEquals(updatedEducation.getPersonalInfo(), result.get().getPersonalInfo());

        verify(educationRepository, times(1)).findById(id);
        verify(educationRepository, times(1)).save(educationDb);
    }

    @Test
    void updateNotExistTest() {
        Long id = 99L;
        Education updatedEducation = createUpdatedEducation();

        when(educationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Education> result = educationService.update(id, updatedEducation);

        assertTrue(result.isEmpty());
        verify(educationRepository, times(1)).findById(id);
        verify(educationRepository, never()).save(any(Education.class));
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Education education = createEducationWithId(id);

        when(educationRepository.findById(id)).thenReturn(Optional.of(education));

        Optional<Education> result = educationService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Grado en Ingeniería Informática", result.get().getTitulo());
        verify(educationRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotExistTest() {
        Long id = 99L;

        when(educationRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Education> result = educationService.findById(id);

        assertTrue(result.isEmpty());
        verify(educationRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() {
        Education education1 = createEducationWithId(1L);
        Education education2 = createEducationWithId(2L);
        education2.setTitulo("Máster en Inteligencia Artificial");
        education2.setCentro("Universidad de Málaga");

        when(educationRepository.findAll()).thenReturn(List.of(education1, education2));

        List<Education> result = educationService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Grado en Ingeniería Informática", result.get(0).getTitulo());
        assertEquals("Máster en Inteligencia Artificial", result.get(1).getTitulo());
        verify(educationRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;

        doNothing().when(educationRepository).deleteById(id);

        educationService.deleteById(id);

        verify(educationRepository, times(1)).deleteById(id);
    }

    @Test
    void findByPersonalInfoIdTest() {
        Long personalInfoId = 1L;
        Education education1 = createEducationWithId(1L);
        Education education2 = createEducationWithId(2L);
        education2.setTitulo("Máster en Ciencia de Datos");

        when(educationRepository.findByPersonalInfoId(personalInfoId))
                .thenReturn(List.of(education1, education2));

        List<Education> result = educationService.findByPersonalInfoId(personalInfoId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Grado en Ingeniería Informática", result.get(0).getTitulo());
        assertEquals("Máster en Ciencia de Datos", result.get(1).getTitulo());
        verify(educationRepository, times(1)).findByPersonalInfoId(personalInfoId);
    }

    @Test
    void existsByPersonalInfoIdTest() {
        Long personalInfoId = 1L;

        when(educationRepository.existsByPersonalInfoId(personalInfoId)).thenReturn(true);

        boolean result = educationService.existsByPersonalInfoId(personalInfoId);

        assertTrue(result);
        verify(educationRepository, times(1)).existsByPersonalInfoId(personalInfoId);
    }

    @Test
    void existsByPersonalInfoIdNotExistTest() {
        Long personalInfoId = 99L;

        when(educationRepository.existsByPersonalInfoId(personalInfoId)).thenReturn(false);

        boolean result = educationService.existsByPersonalInfoId(personalInfoId);

        assertFalse(result);
        verify(educationRepository, times(1)).existsByPersonalInfoId(personalInfoId);
    }

    private Education createEducation() {
        Education education = new Education();
        education.setTitulo("Grado en Ingeniería Informática");
        education.setCentro("Universidad de Granada");
        education.setInicio(LocalDate.of(2021, 9, 1));
        education.setFin(LocalDate.of(2025, 6, 30));
        education.setDescripcion("Formación universitaria en informática");
        education.setPersonalInfo(createPersonalInfo(1L));
        return education;
    }

    private Education createEducationWithId(Long id) {
        Education education = createEducation();
        education.setId(id);
        return education;
    }

    private Education createOldEducation(Long id) {
        Education education = new Education();
        education.setId(id);
        education.setTitulo("Bachillerato");
        education.setCentro("IES Ejemplo");
        education.setInicio(LocalDate.of(2018, 9, 1));
        education.setFin(LocalDate.of(2020, 6, 30));
        education.setDescripcion("Estudios previos");
        education.setPersonalInfo(createPersonalInfo(1L));
        return education;
    }

    private Education createUpdatedEducation() {
        Education education = new Education();
        education.setTitulo("Máster en Ingeniería del Software");
        education.setCentro("Universidad de Sevilla");
        education.setInicio(LocalDate.of(2025, 9, 1));
        education.setFin(LocalDate.of(2026, 6, 30));
        education.setDescripcion("Formación avanzada en ingeniería del software");
        education.setPersonalInfo(createPersonalInfo(2L));
        return education;
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
