package com.myportfolio.my_portfolio_backend.controller;

import com.myportfolio.my_portfolio_backend.entity.*;
import com.myportfolio.my_portfolio_backend.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioRestControllerAPITest {

    private static final Long ID = 1L ;

    @Mock
    private IPersonalInfoService personalInfoService;

    @Mock
    private ISkillService skillsService;

    @Mock
    private IEducationService educationService;

    @Mock
    private IExperienceService experienceService;

    @Mock
    private IProjectsService projectsService;

    @Mock
    private IIdiomasService idiomasService;

    @InjectMocks
    PortfolioRestControllerAPI portfolioRestControllerAPI ;

    @Test
    public void listAllPersonalInfoTest(){
        final PersonalInfo personalInfo = new PersonalInfo() ;
        Mockito.when(personalInfoService.findAll()).thenReturn(List.of(personalInfo)) ;

        final List<PersonalInfo> response = portfolioRestControllerAPI.listAllPersonalInfo() ;

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(personalInfoService, times(1)).findAll();
    }

    @Test
    public void listPersonalInfoTest(){
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123") ;
        final Optional<PersonalInfo> personalInfoOptional = Optional.of(personalInfo) ;

        Mockito.when(personalInfoService.findById(1L)).thenReturn(personalInfoOptional);
        ResponseEntity<?> response = portfolioRestControllerAPI.listPersonalInfo(ID) ;

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PersonalInfo body = (PersonalInfo) response.getBody();
        assertNotNull(body);
        assertEquals("Miguel", body.getNombre());
        assertEquals("Developer", body.getTitulo());
        verify(personalInfoService, times(1)).findById(1L);
    }

    @Test
    public void listPersonalInfoNotFoundTest(){
        final Optional<PersonalInfo> personalInfoOptionalEmpty = Optional.empty() ;

        Mockito.when(personalInfoService.findById(1L)).thenReturn(personalInfoOptionalEmpty);
        ResponseEntity<?> httpResponse = portfolioRestControllerAPI.listPersonalInfo(ID) ;

        assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND) ;
        verify(personalInfoService, times(1)).findById(1L);
    }

    @Test
    public void createPersonalInfoTest(){
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123") ;
        Mockito.when(personalInfoService.save(personalInfo)).thenReturn(personalInfo) ;
        ResponseEntity<PersonalInfo> httpResponse = portfolioRestControllerAPI.createPersonalInfo(personalInfo) ;

        assertEquals(httpResponse.getStatusCode(), HttpStatus.CREATED) ;
        verify(personalInfoService, times(1)).save(personalInfo);

    }

    @Test
    public void updatePersonalInfoTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");
        final Optional<PersonalInfo> personalInfoOptional = Optional.of(personalInfo);

        Mockito.when(personalInfoService.update(ID, personalInfo)).thenReturn(personalInfoOptional);

        ResponseEntity<PersonalInfo> httpResponse = portfolioRestControllerAPI.updatePersonalInfo(ID, personalInfo);

        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Miguel", httpResponse.getBody().getNombre());

        verify(personalInfoService, times(1)).update(ID, personalInfo);
    }

    @Test
    public void updatePersonalInfoNotFoundTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");

        Mockito.when(personalInfoService.update(ID, personalInfo)).thenReturn(Optional.empty());

        ResponseEntity<PersonalInfo> httpResponse = portfolioRestControllerAPI.updatePersonalInfo(ID, personalInfo);

        assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());

        verify(personalInfoService, times(1)).update(ID, personalInfo);
    }

    @Test
    public void deletePersonalInfoTest(){
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123") ;
        final Optional<PersonalInfo> personalInfoOptional = Optional.of(personalInfo) ;

        Mockito.when(personalInfoService.findById(1L)).thenReturn(personalInfoOptional);
        ResponseEntity<?> httpResponse = portfolioRestControllerAPI.deletePersonalInfo(ID) ;

        assertEquals(httpResponse.getStatusCode(), HttpStatus.NO_CONTENT) ;
        verify(personalInfoService, times(1)).findById(ID);
        verify(personalInfoService, times(1)).deleteById(ID);
    }

    @Test
    public void deletePersonalInfoNotFoundTest(){
        final Optional<PersonalInfo> personalInfoOptionalEmpty = Optional.empty() ;

        Mockito.when(personalInfoService.findById(1L)).thenReturn(personalInfoOptionalEmpty);
        ResponseEntity<?> httpResponse = portfolioRestControllerAPI.deletePersonalInfo(ID) ;

        assertEquals(httpResponse.getStatusCode(), HttpStatus.NOT_FOUND) ;
        verify(personalInfoService, never()).deleteById(ID);
    }

    @Test
    public void listAllSkillsTest() {
        final Skills skill = createSkill(1L);
        Mockito.when(skillsService.findAll()).thenReturn(List.of(skill));

        final List<Skills> response = portfolioRestControllerAPI.listAllSkills();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(skillsService, times(1)).findAll();
    }

    @Test
    public void getSkillByIdTest() {
        final Skills skill = createSkill(1L);
        final Optional<Skills> skillOptional = Optional.of(skill);

        Mockito.when(skillsService.findById(1L)).thenReturn(skillOptional);
        ResponseEntity<Skills> response = portfolioRestControllerAPI.getSkillById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Java", response.getBody().getName());
        assertEquals(85, response.getBody().getLevel());

        verify(skillsService, times(1)).findById(1L);
    }

    @Test
    public void getSkillByIdNotFoundTest() {
        Mockito.when(skillsService.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Skills> response = portfolioRestControllerAPI.getSkillById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(skillsService, times(1)).findById(1L);
    }

    @Test
    public void listSkillsByPersonalInfoTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");
        final Skills skill = createSkill(1L);

        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.of(personalInfo));
        Mockito.when(skillsService.findByPersonalInfoId(1L)).thenReturn(List.of(skill));

        ResponseEntity<List<Skills>> response = portfolioRestControllerAPI.listSkillsByPersonalInfo(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Java", response.getBody().get(0).getName());

        verify(personalInfoService, times(1)).findById(1L);
        verify(skillsService, times(1)).findByPersonalInfoId(1L);
    }

    @Test
    public void listSkillsByPersonalInfoNotFoundTest() {
        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Skills>> response = portfolioRestControllerAPI.listSkillsByPersonalInfo(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(personalInfoService, times(1)).findById(1L);
        verify(skillsService, never()).findByPersonalInfoId(1L);
    }

    @Test
    public void createSkillTest() {
        final Skills skill = createSkill(1L);
        Mockito.when(skillsService.save(skill)).thenReturn(skill);

        ResponseEntity<Skills> response = portfolioRestControllerAPI.createSkill(skill);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Java", response.getBody().getName());

        verify(skillsService, times(1)).save(skill);
    }

    @Test
    public void updateSkillTest() {
        final Skills skill = createSkill(1L);
        final Optional<Skills> skillOptional = Optional.of(skill);

        Mockito.when(skillsService.update(ID, skill)).thenReturn(skillOptional);

        ResponseEntity<Skills> response = portfolioRestControllerAPI.updateSkill(ID, skill);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Java", response.getBody().getName());

        verify(skillsService, times(1)).update(ID, skill);
    }

    @Test
    public void updateSkillNotFoundTest() {
        final Skills skill = createSkill(1L);

        Mockito.when(skillsService.update(ID, skill)).thenReturn(Optional.empty());

        ResponseEntity<Skills> response = portfolioRestControllerAPI.updateSkill(ID, skill);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(skillsService, times(1)).update(ID, skill);
    }

    @Test
    public void deleteSkillTest() {
        final Skills skill = createSkill(1L);
        final Optional<Skills> skillOptional = Optional.of(skill);

        Mockito.when(skillsService.findById(1L)).thenReturn(skillOptional);

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteSkill(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(skillsService, times(1)).findById(ID);
        verify(skillsService, times(1)).deleteById(ID);
    }

    @Test
    public void deleteSkillNotFoundTest() {
        Mockito.when(skillsService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteSkill(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(skillsService, never()).deleteById(ID);
    }

    @Test
    public void listAllEducationTest() {
        final Education education = createEducation(1L);
        Mockito.when(educationService.findAll()).thenReturn(List.of(education));

        final List<Education> response = portfolioRestControllerAPI.listAllEducation();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(educationService, times(1)).findAll();
    }

    @Test
    public void getEducationByIdTest() {
        final Education education = createEducation(1L);
        final Optional<Education> educationOptional = Optional.of(education);

        Mockito.when(educationService.findById(1L)).thenReturn(educationOptional);
        ResponseEntity<Education> response = portfolioRestControllerAPI.getEducationById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Grado en Ingeniería Informática", response.getBody().getTitulo());

        verify(educationService, times(1)).findById(1L);
    }

    @Test
    public void getEducationByIdNotFoundTest() {
        Mockito.when(educationService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Education> response = portfolioRestControllerAPI.getEducationById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(educationService, times(1)).findById(1L);
    }

    @Test
    public void listEducationByPersonalInfoTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");
        final Education education = createEducation(1L);

        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.of(personalInfo));
        Mockito.when(educationService.findByPersonalInfoId(1L)).thenReturn(List.of(education));

        ResponseEntity<List<Education>> response = portfolioRestControllerAPI.listEducationByPersonalInfo(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Grado en Ingeniería Informática", response.getBody().get(0).getTitulo());

        verify(personalInfoService, times(1)).findById(1L);
        verify(educationService, times(1)).findByPersonalInfoId(1L);
    }

    @Test
    public void listEducationByPersonalInfoNotFoundTest() {
        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Education>> response = portfolioRestControllerAPI.listEducationByPersonalInfo(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(personalInfoService, times(1)).findById(1L);
        verify(educationService, never()).findByPersonalInfoId(1L);
    }

    @Test
    public void createEducationTest() {
        final Education education = createEducation(1L);
        Mockito.when(educationService.save(education)).thenReturn(education);

        ResponseEntity<Education> response = portfolioRestControllerAPI.createEducation(education);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Grado en Ingeniería Informática", response.getBody().getTitulo());

        verify(educationService, times(1)).save(education);
    }

    @Test
    public void updateEducationTest() {
        final Education education = createEducation(1L);
        final Optional<Education> educationOptional = Optional.of(education);

        Mockito.when(educationService.update(ID, education)).thenReturn(educationOptional);

        ResponseEntity<Education> response = portfolioRestControllerAPI.updateEducation(ID, education);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Grado en Ingeniería Informática", response.getBody().getTitulo());

        verify(educationService, times(1)).update(ID, education);
    }

    @Test
    public void updateEducationNotFoundTest() {
        final Education education = createEducation(1L);

        Mockito.when(educationService.update(ID, education)).thenReturn(Optional.empty());

        ResponseEntity<Education> response = portfolioRestControllerAPI.updateEducation(ID, education);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(educationService, times(1)).update(ID, education);
    }

    @Test
    public void deleteEducationTest() {
        final Education education = createEducation(1L);
        final Optional<Education> educationOptional = Optional.of(education);

        Mockito.when(educationService.findById(1L)).thenReturn(educationOptional);

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteEducation(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(educationService, times(1)).findById(ID);
        verify(educationService, times(1)).deleteById(ID);
    }

    @Test
    public void deleteEducationNotFoundTest() {
        Mockito.when(educationService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteEducation(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(educationService, never()).deleteById(ID);
    }

    @Test
    public void listAllExperienceTest() {
        final Experience experience = createExperience(1L);
        Mockito.when(experienceService.findAll()).thenReturn(List.of(experience));

        final List<Experience> response = portfolioRestControllerAPI.listAllExperience();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(experienceService, times(1)).findAll();
    }

    @Test
    public void getExperienceByIdTest() {
        final Experience experience = createExperience(1L);
        final Optional<Experience> experienceOptional = Optional.of(experience);

        Mockito.when(experienceService.findById(1L)).thenReturn(experienceOptional);
        ResponseEntity<Experience> response = portfolioRestControllerAPI.getExperienceById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Desarrollador Web", response.getBody().getTitulo());

        verify(experienceService, times(1)).findById(1L);
    }

    @Test
    public void getExperienceByIdNotFoundTest() {
        Mockito.when(experienceService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Experience> response = portfolioRestControllerAPI.getExperienceById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(experienceService, times(1)).findById(1L);
    }

    @Test
    public void listExperienceByPersonalInfoTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");
        final Experience experience = createExperience(1L);

        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.of(personalInfo));
        Mockito.when(experienceService.findByPersonalInfoId(1L)).thenReturn(List.of(experience));

        ResponseEntity<List<Experience>> response = portfolioRestControllerAPI.listExperienceByPersonalInfo(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Desarrollador Web", response.getBody().get(0).getTitulo());

        verify(personalInfoService, times(1)).findById(1L);
        verify(experienceService, times(1)).findByPersonalInfoId(1L);
    }

    @Test
    public void listExperienceByPersonalInfoNotFoundTest() {
        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Experience>> response = portfolioRestControllerAPI.listExperienceByPersonalInfo(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(personalInfoService, times(1)).findById(1L);
        verify(experienceService, never()).findByPersonalInfoId(1L);
    }

    @Test
    public void createExperienceTest() {
        final Experience experience = createExperience(1L);
        Mockito.when(experienceService.save(experience)).thenReturn(experience);

        ResponseEntity<Experience> response = portfolioRestControllerAPI.createExperience(experience);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Desarrollador Web", response.getBody().getTitulo());

        verify(experienceService, times(1)).save(experience);
    }

    @Test
    public void updateExperienceTest() {
        final Experience experience = createExperience(1L);
        final Optional<Experience> experienceOptional = Optional.of(experience);

        Mockito.when(experienceService.update(ID, experience)).thenReturn(experienceOptional);

        ResponseEntity<Experience> response = portfolioRestControllerAPI.updateExperience(ID, experience);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Desarrollador Web", response.getBody().getTitulo());

        verify(experienceService, times(1)).update(ID, experience);
    }

    @Test
    public void updateExperienceNotFoundTest() {
        final Experience experience = createExperience(1L);

        Mockito.when(experienceService.update(ID, experience)).thenReturn(Optional.empty());

        ResponseEntity<Experience> response = portfolioRestControllerAPI.updateExperience(ID, experience);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(experienceService, times(1)).update(ID, experience);
    }

    @Test
    public void deleteExperienceTest() {
        final Experience experience = createExperience(1L);
        final Optional<Experience> experienceOptional = Optional.of(experience);

        Mockito.when(experienceService.findById(1L)).thenReturn(experienceOptional);

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteExperience(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(experienceService, times(1)).findById(ID);
        verify(experienceService, times(1)).deleteById(ID);
    }

    @Test
    public void deleteExperienceNotFoundTest() {
        Mockito.when(experienceService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteExperience(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(experienceService, never()).deleteById(ID);
    }

    @Test
    public void listAllProjectsTest() {
        final Projects project = createProject(1L);
        Mockito.when(projectsService.findAll()).thenReturn(List.of(project));

        final List<Projects> response = portfolioRestControllerAPI.listAllProjects();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(projectsService, times(1)).findAll();
    }

    @Test
    public void getProjectByIdTest() {
        final Projects project = createProject(1L);
        final Optional<Projects> projectOptional = Optional.of(project);

        Mockito.when(projectsService.findById(1L)).thenReturn(projectOptional);
        ResponseEntity<Projects> response = portfolioRestControllerAPI.getProjectById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Portfolio Web", response.getBody().getTitulo());

        verify(projectsService, times(1)).findById(1L);
    }

    @Test
    public void getProjectByIdNotFoundTest() {
        Mockito.when(projectsService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Projects> response = portfolioRestControllerAPI.getProjectById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(projectsService, times(1)).findById(1L);
    }

    @Test
    public void listProjectsByPersonalInfoTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");
        final Projects project = createProject(1L);

        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.of(personalInfo));
        Mockito.when(projectsService.findByPersonalInfoId(1L)).thenReturn(List.of(project));

        ResponseEntity<List<Projects>> response = portfolioRestControllerAPI.listProjectsByPersonalInfo(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Portfolio Web", response.getBody().get(0).getTitulo());

        verify(personalInfoService, times(1)).findById(1L);
        verify(projectsService, times(1)).findByPersonalInfoId(1L);
    }

    @Test
    public void listProjectsByPersonalInfoNotFoundTest() {
        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Projects>> response = portfolioRestControllerAPI.listProjectsByPersonalInfo(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(personalInfoService, times(1)).findById(1L);
        verify(projectsService, never()).findByPersonalInfoId(1L);
    }

    @Test
    public void createProjectTest() {
        final Projects project = createProject(1L);
        Mockito.when(projectsService.save(project)).thenReturn(project);

        ResponseEntity<Projects> response = portfolioRestControllerAPI.createProject(project);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Portfolio Web", response.getBody().getTitulo());

        verify(projectsService, times(1)).save(project);
    }

    @Test
    public void updateProjectTest() {
        final Projects project = createProject(1L);
        final Optional<Projects> projectOptional = Optional.of(project);

        Mockito.when(projectsService.update(ID, project)).thenReturn(projectOptional);

        ResponseEntity<Projects> response = portfolioRestControllerAPI.updateProject(ID, project);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Portfolio Web", response.getBody().getTitulo());

        verify(projectsService, times(1)).update(ID, project);
    }

    @Test
    public void updateProjectNotFoundTest() {
        final Projects project = createProject(1L);

        Mockito.when(projectsService.update(ID, project)).thenReturn(Optional.empty());

        ResponseEntity<Projects> response = portfolioRestControllerAPI.updateProject(ID, project);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(projectsService, times(1)).update(ID, project);
    }

    @Test
    public void deleteProjectTest() {
        final Projects project = createProject(1L);
        final Optional<Projects> projectOptional = Optional.of(project);

        Mockito.when(projectsService.findById(1L)).thenReturn(projectOptional);

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteProject(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectsService, times(1)).findById(ID);
        verify(projectsService, times(1)).deleteById(ID);
    }

    @Test
    public void deleteProjectNotFoundTest() {
        Mockito.when(projectsService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteProject(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(projectsService, never()).deleteById(ID);
    }

    @Test
    public void listAllLanguagesTest() {
        final Idiomas idioma = createIdioma(1L);
        Mockito.when(idiomasService.findAll()).thenReturn(List.of(idioma));

        final List<Idiomas> response = portfolioRestControllerAPI.listAllLanguages();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(idiomasService, times(1)).findAll();
    }

    @Test
    public void getLanguageByIdTest() {
        final Idiomas idioma = createIdioma(1L);
        final Optional<Idiomas> idiomaOptional = Optional.of(idioma);

        Mockito.when(idiomasService.findById(1L)).thenReturn(idiomaOptional);
        ResponseEntity<Idiomas> response = portfolioRestControllerAPI.getLanguageById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Inglés", response.getBody().getIdioma());

        verify(idiomasService, times(1)).findById(1L);
    }

    @Test
    public void getLanguageByIdNotFoundTest() {
        Mockito.when(idiomasService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Idiomas> response = portfolioRestControllerAPI.getLanguageById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(idiomasService, times(1)).findById(1L);
    }

    @Test
    public void listLanguagesByPersonalInfoTest() {
        final PersonalInfo personalInfo = createPersonalInfo(1L, "token123");
        final Idiomas idioma = createIdioma(1L);

        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.of(personalInfo));
        Mockito.when(idiomasService.findByPersonalInfoId(1L)).thenReturn(List.of(idioma));

        ResponseEntity<List<Idiomas>> response = portfolioRestControllerAPI.listLanguagesByPersonalInfo(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Inglés", response.getBody().get(0).getIdioma());

        verify(personalInfoService, times(1)).findById(1L);
        verify(idiomasService, times(1)).findByPersonalInfoId(1L);
    }

    @Test
    public void listLanguagesByPersonalInfoNotFoundTest() {
        Mockito.when(personalInfoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<List<Idiomas>> response = portfolioRestControllerAPI.listLanguagesByPersonalInfo(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(personalInfoService, times(1)).findById(1L);
        verify(idiomasService, never()).findByPersonalInfoId(1L);
    }

    @Test
    public void createLanguageTest() {
        final Idiomas idioma = createIdioma(1L);
        Mockito.when(idiomasService.save(idioma)).thenReturn(idioma);

        ResponseEntity<Idiomas> response = portfolioRestControllerAPI.createLanguage(idioma);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Inglés", response.getBody().getIdioma());

        verify(idiomasService, times(1)).save(idioma);
    }

    @Test
    public void updateLanguageTest() {
        final Idiomas idioma = createIdioma(1L);
        final Optional<Idiomas> idiomaOptional = Optional.of(idioma);

        Mockito.when(idiomasService.update(ID, idioma)).thenReturn(idiomaOptional);

        ResponseEntity<Idiomas> response = portfolioRestControllerAPI.updateLanguage(ID, idioma);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Inglés", response.getBody().getIdioma());

        verify(idiomasService, times(1)).update(ID, idioma);
    }

    @Test
    public void updateLanguageNotFoundTest() {
        final Idiomas idioma = createIdioma(1L);

        Mockito.when(idiomasService.update(ID, idioma)).thenReturn(Optional.empty());

        ResponseEntity<Idiomas> response = portfolioRestControllerAPI.updateLanguage(ID, idioma);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(idiomasService, times(1)).update(ID, idioma);
    }

    @Test
    public void deleteLanguageTest() {
        final Idiomas idioma = createIdioma(1L);
        final Optional<Idiomas> idiomaOptional = Optional.of(idioma);

        Mockito.when(idiomasService.findById(1L)).thenReturn(idiomaOptional);

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteLanguage(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(idiomasService, times(1)).findById(ID);
        verify(idiomasService, times(1)).deleteById(ID);
    }

    @Test
    public void deleteLanguageNotFoundTest() {
        Mockito.when(idiomasService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = portfolioRestControllerAPI.deleteLanguage(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(idiomasService, never()).deleteById(ID);
    }


    // ====  Métodos para crear Servicios de Prueba ==== \\

    private PersonalInfo createPersonalInfo(Long id, String token) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(id);
        personalInfo.setEditToken(token);
        personalInfo.setNombre("Miguel");
        personalInfo.setApellidos("Martínez");
        personalInfo.setTitulo("Developer");
        personalInfo.setDescripcion("Descripción válida");
        personalInfo.setEmail("miguel@email.com");
        personalInfo.setTelefono("612345678");
        personalInfo.setLinkedinUrl("https://linkedin.com/in/miguel");
        personalInfo.setImagen("/img/perfil.png");
        return personalInfo;
    }

    private Skills createSkill(Long id) {
        Skills skill = new Skills();
        skill.setId(id);
        skill.setName("Java");
        skill.setLevel(85);
        skill.setIcono("devicon-java-plain");
        return skill;
    }

    private Education createEducation(Long id) {
        Education education = new Education();
        education.setId(id);
        education.setTitulo("Grado en Ingeniería Informática");
        education.setCentro("Universidad de Granada");
        education.setInicio(LocalDate.of(2021, 9, 1));
        education.setFin(LocalDate.of(2025, 6, 30));
        education.setDescripcion("Formación universitaria en informática");
        return education;
    }

    private Experience createExperience(Long id) {
        Experience experience = new Experience();
        experience.setId(id);
        experience.setTitulo("Desarrollador Web");
        experience.setEmpresa("Empresa Tech");
        experience.setInicio(LocalDate.of(2023, 1, 1));
        experience.setFin(LocalDate.of(2024, 1, 1));
        experience.setDescripcion("Desarrollo y mantenimiento de aplicaciones web");
        return experience;
    }

    private Projects createProject(Long id) {
        Projects project = new Projects();
        project.setId(id);
        project.setTitulo("Portfolio Web");
        project.setDescripcion("Proyecto personal con Spring Boot");
        project.setImagenUrl("/img/project.png");
        project.setProyectoUrl("https://miportfolio.com");
        return project;
    }

    private Idiomas createIdioma(Long id) {
        Idiomas idioma = new Idiomas();
        idioma.setId(id);
        idioma.setIdioma("Inglés");
        idioma.setNivel("C1");
        idioma.setIcono("mdi:translate");
        return idioma;
    }

}
