package com.myportfolio.my_portfolio_backend.controller;

import com.myportfolio.my_portfolio_backend.entity.*;
import com.myportfolio.my_portfolio_backend.repository.IEducationRepository;
import com.myportfolio.my_portfolio_backend.repository.IIdiomasRepository;
import com.myportfolio.my_portfolio_backend.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final IPersonalInfoService personalInfoService ;
    private final ISkillService skillsService ;
    private final IEducationService educationService ;
    private final IExperienceService experienceService ;
    private final IProjectsService projectsService ;
    private final IIdiomasService idiomasService ;

    @GetMapping("/form")
    public String showForm(Model model){
        model.addAttribute("personalInfo", new PersonalInfo()) ;

        return "form" ;
    }

    @PostMapping("/skills")
    public String savePersonalinfo(@Valid @ModelAttribute("personalInfo") PersonalInfo personalInfo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("withinSchedule", true);
            model.addAttribute(
                    "scheduleMessage",
                    "Corrige los errores del formulario para continuar. Aunque el horario haya terminado, puedes completar esta corrección."
            );
            return "form";
        }

        PersonalInfo saved = personalInfoService.save(personalInfo);
        return "redirect:/skills-form?personalInfoId=" + saved.getId();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Skills> searchSkills(@RequestParam String query) {

        return skillsService.searchByName(query);
    }

    @GetMapping("/skills-form")
    public String showSkillsForm(@RequestParam("personalInfoId") Long personalInfoId, Model model) {
        model.addAttribute("skill", new Skills());
        model.addAttribute("personalInfoId", personalInfoId);
        model.addAttribute("skillsList", skillsService.findByPersonalInfoId(personalInfoId));
        return "skills";
    }

    @PostMapping("/skill/save")
    public String saveSkill(@Valid @ModelAttribute("skill") Skills skill,
                            BindingResult result,
                            @RequestParam("personalInfoId") Long personalInfoId,
                            Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findById(personalInfoId);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        if (result.hasErrors()) {
            model.addAttribute("personalInfoId", personalInfoId);
            model.addAttribute("skillsList", skillsService.findByPersonalInfoId(personalInfoId));
            return "skills";
        }

        skill.setPersonalInfo(personalInfoOpt.get());
        skillsService.save(skill);

        return "redirect:/skills-form?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/skills/next/{personalInfoId}")
    public String goToEducation(@PathVariable Long personalInfoId,
                                RedirectAttributes redirectAttributes) {

        boolean hasSkills = skillsService.existsByPersonalInfoId(personalInfoId);

        if (!hasSkills) {
            redirectAttributes.addFlashAttribute("nextError",
                    "Debes añadir al menos una skill antes de continuar.");
            return "redirect:/skills-form?personalInfoId=" + personalInfoId;
        }

        return "redirect:/education?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/education")
    public String showEducationForm(@RequestParam("personalInfoId") Long personalInfoId, Model model) {
        model.addAttribute("education", new Education());
        model.addAttribute("personalInfoId", personalInfoId);
        model.addAttribute("educationList", educationService.findByPersonalInfoId(personalInfoId));
        return "education";
    }

    @PostMapping("/education/save")
    public String saveEducation(@Valid @ModelAttribute("education") Education education,
                                BindingResult result,
                                @RequestParam("personalInfoId") Long personalInfoId,
                                Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findById(personalInfoId);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        if (result.hasErrors()) {
            model.addAttribute("personalInfoId", personalInfoId);
            model.addAttribute("educationList", educationService.findByPersonalInfoId(personalInfoId));
            return "education";
        }

        education.setPersonalInfo(personalInfoOpt.get());
        educationService.save(education);

        return "redirect:/education?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/education/next/{personalInfoId}")
    public String goToProjects(@PathVariable Long personalInfoId,
                               RedirectAttributes redirectAttributes) {

        boolean hasEducation = educationService.existsByPersonalInfoId(personalInfoId);

        if (!hasEducation) {
            redirectAttributes.addFlashAttribute("nextError",
                    "Debes añadir al menos una formación antes de continuar.");
            return "redirect:/education?personalInfoId=" + personalInfoId;
        }

        return "redirect:/experience?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/experience")
    public String showExperienceForm(@RequestParam("personalInfoId") Long personalInfoId, Model model) {
        model.addAttribute("experience", new Experience());
        model.addAttribute("personalInfoId", personalInfoId);
        model.addAttribute("experienceList", experienceService.findByPersonalInfoId(personalInfoId));
        return "experiencia";
    }

    @PostMapping("/experience/save")
    public String saveExperience(@Valid @ModelAttribute("experience") Experience experience,
                                 BindingResult result,
                                 @RequestParam("personalInfoId") Long personalInfoId,
                                 Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findById(personalInfoId);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        if (result.hasErrors()) {
            model.addAttribute("personalInfoId", personalInfoId);
            model.addAttribute("experienceList", experienceService.findByPersonalInfoId(personalInfoId));
            return "experiencia";
        }

        experience.setPersonalInfo(personalInfoOpt.get());
        experienceService.save(experience);

        return "redirect:/experience?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/projects")
    public String showProjectsForm(@RequestParam("personalInfoId") Long personalInfoId, Model model) {
        model.addAttribute("project", new Projects());
        model.addAttribute("personalInfoId", personalInfoId);
        model.addAttribute("projectsList", projectsService.findByPersonalInfoId(personalInfoId));
        return "proyectos";
    }

    @PostMapping("/projects/save")
    public String saveProject(@Valid @ModelAttribute("project") Projects project,
                              BindingResult result,
                              @RequestParam("personalInfoId") Long personalInfoId,
                              Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findById(personalInfoId);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        if (result.hasErrors()) {
            model.addAttribute("personalInfoId", personalInfoId);
            model.addAttribute("projectsList", projectsService.findByPersonalInfoId(personalInfoId));
            return "proyectos";
        }

        project.setPersonalInfo(personalInfoOpt.get());
        projectsService.save(project);

        return "redirect:/projects?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/projects/next/{personalInfoId}")
    public String goToLanguages(@PathVariable Long personalInfoId,
                                RedirectAttributes redirectAttributes) {

        boolean hasProjects = projectsService.existsByPersonalInfoId(personalInfoId);

        if (!hasProjects) {
            redirectAttributes.addFlashAttribute("nextError",
                    "Debes añadir al menos un proyecto antes de continuar.");
            return "redirect:/projects?personalInfoId=" + personalInfoId;
        }

        return "redirect:/idiomas?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/idiomas")
    public String showIdiomasForm(@RequestParam("personalInfoId") Long personalInfoId, Model model) {
        model.addAttribute("idiomas", new Idiomas());
        model.addAttribute("personalInfoId", personalInfoId);
        model.addAttribute("idiomasList", idiomasService.findByPersonalInfoId(personalInfoId));
        return "idiomas";
    }

    @PostMapping("/idiomas/save")
    public String saveIdiomas(@Valid @ModelAttribute("idiomas") Idiomas idiomas,
                              BindingResult result,
                              @RequestParam("personalInfoId") Long personalInfoId,
                              Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findById(personalInfoId);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        if (result.hasErrors()) {
            model.addAttribute("personalInfoId", personalInfoId);
            model.addAttribute("idiomasList", idiomasService.findByPersonalInfoId(personalInfoId));
            return "idiomas";
        }

        idiomas.setPersonalInfo(personalInfoOpt.get());
        idiomasService.save(idiomas);

        return "redirect:/idiomas?personalInfoId=" + personalInfoId;
    }

    @GetMapping("/portfolio")
    public String showPortfolio(@RequestParam("personalInfoId") Long personalInfoId, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findById(personalInfoId);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        model.addAttribute("personalInfo", personalInfoOpt.get());
        model.addAttribute("skillsList", skillsService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("educationList", educationService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("experienceList", experienceService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("projectsList", projectsService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("idiomasList", idiomasService.findByPersonalInfoId(personalInfoId));

        return "portfolio";
    }

}
