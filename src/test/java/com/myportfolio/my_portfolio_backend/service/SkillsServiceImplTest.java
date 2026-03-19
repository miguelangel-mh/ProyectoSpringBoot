package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.entity.Skills;
import com.myportfolio.my_portfolio_backend.repository.ISkillsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class SkillsServiceImplTest {

    @Mock
    private ISkillsRepository skillsRepository;

    @InjectMocks
    private SkillsServiceImpl skillsService;

    @Test
    void saveTest() {
        Skills skill = createSkill();

        when(skillsRepository.save(skill)).thenReturn(skill);

        Skills result = skillsService.save(skill);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        assertEquals(85, result.getLevel());
        assertEquals("devicon-java-plain", result.getIcono());
        verify(skillsRepository, times(1)).save(skill);
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Skills skill = createSkillWithId(id);

        when(skillsRepository.findById(id)).thenReturn(Optional.of(skill));

        Optional<Skills> result = skillsService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Java", result.get().getName());
        verify(skillsRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotExistTest() {
        Long id = 99L;

        when(skillsRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Skills> result = skillsService.findById(id);

        assertTrue(result.isEmpty());
        verify(skillsRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() {
        Skills skill1 = createSkillWithId(1L);
        Skills skill2 = createSkillWithId(2L);
        skill2.setName("Spring Boot");
        skill2.setLevel(90);

        when(skillsRepository.findAll()).thenReturn(List.of(skill1, skill2));

        List<Skills> result = skillsService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Spring Boot", result.get(1).getName());
        verify(skillsRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;

        doNothing().when(skillsRepository).deleteById(id);

        skillsService.deleteById(id);

        verify(skillsRepository, times(1)).deleteById(id);
    }

    @Test
    void findByPersonalInfoIdTest() {
        Long personalInfoId = 1L;
        Skills skill1 = createSkillWithId(1L);
        Skills skill2 = createSkillWithId(2L);
        skill2.setName("Spring Boot");

        when(skillsRepository.findByPersonalInfoId(personalInfoId)).thenReturn(List.of(skill1, skill2));

        List<Skills> result = skillsService.findByPersonalInfoId(personalInfoId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Spring Boot", result.get(1).getName());
        verify(skillsRepository, times(1)).findByPersonalInfoId(personalInfoId);
    }

    @Test
    void searchByNameTest() {
        String query = "java";
        Skills skill = createSkillWithId(1L);

        when(skillsRepository.findByNameContainingIgnoreCase(query)).thenReturn(List.of(skill));

        List<Skills> result = skillsService.searchByName(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(skillsRepository, times(1)).findByNameContainingIgnoreCase(query);
    }

    @Test
    void existsByPersonalInfoIdTest() {
        Long personalInfoId = 1L;

        when(skillsRepository.existsByPersonalInfoId(personalInfoId)).thenReturn(true);

        boolean result = skillsService.existsByPersonalInfoId(personalInfoId);

        assertTrue(result);
        verify(skillsRepository, times(1)).existsByPersonalInfoId(personalInfoId);
    }

    @Test
    void existsByPersonalInfoIdNotExistTest() {
        Long personalInfoId = 99L;

        when(skillsRepository.existsByPersonalInfoId(personalInfoId)).thenReturn(false);

        boolean result = skillsService.existsByPersonalInfoId(personalInfoId);

        assertFalse(result);
        verify(skillsRepository, times(1)).existsByPersonalInfoId(personalInfoId);
    }

    @Test
    void updateTest() {
        Long id = 1L;
        Skills skillDb = createOldSkill(id);
        Skills updatedSkill = createUpdatedSkill();

        when(skillsRepository.findById(id)).thenReturn(Optional.of(skillDb));
        when(skillsRepository.save(any(Skills.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Skills> result = skillsService.update(id, updatedSkill);

        assertTrue(result.isPresent());
        assertEquals("Spring Boot", result.get().getName());
        assertEquals(95, result.get().getLevel());
        assertEquals("devicon-spring-plain", result.get().getIcono());
        assertEquals(updatedSkill.getPersonalInfo(), result.get().getPersonalInfo());

        verify(skillsRepository, times(1)).findById(id);
        verify(skillsRepository, times(1)).save(skillDb);
    }

    @Test
    void updateNotExistTest() {
        Long id = 1L;
        Skills updatedSkill = createUpdatedSkill();

        when(skillsRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Skills> result = skillsService.update(id, updatedSkill);

        assertTrue(result.isEmpty());
        verify(skillsRepository, times(1)).findById(id);
        verify(skillsRepository, never()).save(any(Skills.class));
    }

    private Skills createSkill() {
        Skills skill = new Skills();
        skill.setName("Java");
        skill.setLevel(85);
        skill.setIcono("devicon-java-plain");
        skill.setPersonalInfo(createPersonalInfo(1L));
        return skill;
    }

    private Skills createSkillWithId(Long id) {
        Skills skill = createSkill();
        skill.setId(id);
        return skill;
    }

    private Skills createOldSkill(Long id) {
        Skills skill = new Skills();
        skill.setId(id);
        skill.setName("HTML");
        skill.setLevel(60);
        skill.setIcono("devicon-html5-plain");
        skill.setPersonalInfo(createPersonalInfo(1L));
        return skill;
    }

    private Skills createUpdatedSkill() {
        Skills skill = new Skills();
        skill.setName("Spring Boot");
        skill.setLevel(95);
        skill.setIcono("devicon-spring-plain");
        skill.setPersonalInfo(createPersonalInfo(2L));
        return skill;
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
