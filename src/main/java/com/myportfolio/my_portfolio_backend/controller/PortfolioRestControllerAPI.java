package com.myportfolio.my_portfolio_backend.controller;

import com.myportfolio.my_portfolio_backend.entity.*;
import com.myportfolio.my_portfolio_backend.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PortfolioRestControllerAPI {

    @Autowired
    private IPersonalInfoService personalInfoService;

    @Autowired
    private ISkillService skillsService;

    @Autowired
    private IEducationService educationService;

    @Autowired
    private IExperienceService experienceService;

    @Autowired
    private IProjectsService projectsService;

    @Autowired
    private IIdiomasService idiomasService;

    // =========================
    // PERSONAL INFO
    // =========================

    @GetMapping("/personalinfo")
    public List<PersonalInfo> listAllPersonalInfo() {
        return personalInfoService.findAll();
    }

    @GetMapping("/personalinfo/{id}")
    public ResponseEntity<?> listPersonalInfo(@PathVariable Long id) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(id);
        if (personalInfoOptional.isPresent()) {
            return ResponseEntity.ok(personalInfoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/personalinfo")
    public ResponseEntity<PersonalInfo> createPersonalInfo(@Valid @RequestBody PersonalInfo personalInfo) {
        PersonalInfo personalInfoNew = personalInfoService.save(personalInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(personalInfoNew);
    }

    @PutMapping("/personalinfo/{id}")
    public ResponseEntity<PersonalInfo> updatePersonalInfo(@PathVariable Long id,
                                                           @Valid @RequestBody PersonalInfo personalInfo) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.update(id, personalInfo);

        if (personalInfoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(personalInfoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/personalinfo/{id}")
    public ResponseEntity<Void> deletePersonalInfo(@PathVariable Long id) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(id);

        if (personalInfoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        personalInfoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // SKILLS
    // =========================

    @GetMapping("/skills")
    public List<Skills> listAllSkills() {
        return skillsService.findAll();
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<Skills> getSkillById(@PathVariable Long id) {
        Optional<Skills> skillOptional = skillsService.findById(id);

        if (skillOptional.isPresent()) {
            return ResponseEntity.ok(skillOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/personalinfo/{personalInfoId}/skills")
    public ResponseEntity<List<Skills>> listSkillsByPersonalInfo(@PathVariable Long personalInfoId) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(personalInfoId);

        if (personalInfoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(skillsService.findByPersonalInfoId(personalInfoId));
    }

    @PostMapping("/skills")
    public ResponseEntity<Skills> createSkill(@Valid @RequestBody Skills skill) {
        Skills newSkill = skillsService.save(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<Skills> updateSkill(@PathVariable Long id,
                                              @Valid @RequestBody Skills skill) {
        Optional<Skills> skillOptional = skillsService.update(id, skill);

        if (skillOptional.isPresent()) {
            return ResponseEntity.ok(skillOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        Optional<Skills> skillOptional = skillsService.findById(id);

        if (skillOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        skillsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // EDUCATION
    // =========================

    @GetMapping("/education")
    public List<Education> listAllEducation() {
        return educationService.findAll();
    }

    @GetMapping("/education/{id}")
    public ResponseEntity<Education> getEducationById(@PathVariable Long id) {
        Optional<Education> educationOptional = educationService.findById(id);

        if (educationOptional.isPresent()) {
            return ResponseEntity.ok(educationOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/personalinfo/{personalInfoId}/education")
    public ResponseEntity<List<Education>> listEducationByPersonalInfo(@PathVariable Long personalInfoId) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(personalInfoId);

        if (personalInfoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(educationService.findByPersonalInfoId(personalInfoId));
    }

    @PostMapping("/education")
    public ResponseEntity<Education> createEducation(@Valid @RequestBody Education education) {
        Education newEducation = educationService.save(education);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEducation);
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<Education> updateEducation(@PathVariable Long id,
                                                     @Valid @RequestBody Education education) {
        Optional<Education> educationOptional = educationService.update(id, education);

        if (educationOptional.isPresent()) {
            return ResponseEntity.ok(educationOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        Optional<Education> educationOptional = educationService.findById(id);

        if (educationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        educationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // EXPERIENCE
    // =========================

    @GetMapping("/experience")
    public List<Experience> listAllExperience() {
        return experienceService.findAll();
    }

    @GetMapping("/experience/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        Optional<Experience> experienceOptional = experienceService.findById(id);

        if (experienceOptional.isPresent()) {
            return ResponseEntity.ok(experienceOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/personalinfo/{personalInfoId}/experience")
    public ResponseEntity<List<Experience>> listExperienceByPersonalInfo(@PathVariable Long personalInfoId) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(personalInfoId);

        if (personalInfoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(experienceService.findByPersonalInfoId(personalInfoId));
    }

    @PostMapping("/experience")
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience) {
        Experience newExperience = experienceService.save(experience);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExperience);
    }

    @PutMapping("/experience/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id,
                                                       @Valid @RequestBody Experience experience) {
        Optional<Experience> experienceOptional = experienceService.update(id, experience);

        if (experienceOptional.isPresent()) {
            return ResponseEntity.ok(experienceOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/experience/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        Optional<Experience> experienceOptional = experienceService.findById(id);

        if (experienceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        experienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // PROJECTS
    // =========================

    @GetMapping("/projects")
    public List<Projects> listAllProjects() {
        return projectsService.findAll();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Projects> getProjectById(@PathVariable Long id) {
        Optional<Projects> projectOptional = projectsService.findById(id);

        if (projectOptional.isPresent()) {
            return ResponseEntity.ok(projectOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/personalinfo/{personalInfoId}/projects")
    public ResponseEntity<List<Projects>> listProjectsByPersonalInfo(@PathVariable Long personalInfoId) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(personalInfoId);

        if (personalInfoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(projectsService.findByPersonalInfoId(personalInfoId));
    }

    @PostMapping("/projects")
    public ResponseEntity<Projects> createProject(@Valid @RequestBody Projects project) {
        Projects newProject = projectsService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Projects> updateProject(@PathVariable Long id,
                                                  @Valid @RequestBody Projects project) {
        Optional<Projects> projectOptional = projectsService.update(id, project);

        if (projectOptional.isPresent()) {
            return ResponseEntity.ok(projectOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<Projects> projectOptional = projectsService.findById(id);

        if (projectOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        projectsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // LANGUAGES
    // =========================

    @GetMapping("/languages")
    public List<Idiomas> listAllLanguages() {
        return idiomasService.findAll();
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Idiomas> getLanguageById(@PathVariable Long id) {
        Optional<Idiomas> languageOptional = idiomasService.findById(id);

        if (languageOptional.isPresent()) {
            return ResponseEntity.ok(languageOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/personalinfo/{personalInfoId}/languages")
    public ResponseEntity<List<Idiomas>> listLanguagesByPersonalInfo(@PathVariable Long personalInfoId) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoService.findById(personalInfoId);

        if (personalInfoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(idiomasService.findByPersonalInfoId(personalInfoId));
    }

    @PostMapping("/languages")
    public ResponseEntity<Idiomas> createLanguage(@Valid @RequestBody Idiomas language) {
        Idiomas newLanguage = idiomasService.save(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLanguage);
    }

    @PutMapping("/languages/{id}")
    public ResponseEntity<Idiomas> updateLanguage(@PathVariable Long id,
                                                    @Valid @RequestBody Idiomas language) {
        Optional<Idiomas> languageOptional = idiomasService.update(id, language);

        if (languageOptional.isPresent()) {
            return ResponseEntity.ok(languageOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/languages/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        Optional<Idiomas> languageOptional = idiomasService.findById(id);

        if (languageOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        idiomasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
