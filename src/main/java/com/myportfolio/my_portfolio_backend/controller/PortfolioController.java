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

    @GetMapping("/")
    public String home() {
        return "redirect:/form";
    }

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
        return "redirect:/skills-form?token=" + saved.getEditToken();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Skills> searchSkills(@RequestParam String query) {

        return skillsService.searchByName(query);
    }

    @GetMapping("/skills-form")
    public String showSkillsForm(@RequestParam("token") String token, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        model.addAttribute("skill", new Skills());
        model.addAttribute("token", token);
        model.addAttribute("skillsList", skillsService.findByPersonalInfoId(personalInfoId));
        return "skills";
    }

    @PostMapping("/skill/save")
    public String saveSkill(@Valid @ModelAttribute("skill") Skills skill,
                            BindingResult result,
                            @RequestParam("token") String token,
                            Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        PersonalInfo personalInfo = personalInfoOpt.get();
        Long personalInfoId = personalInfo.getId();

        if (result.hasErrors()) {
            model.addAttribute("token", token);
            model.addAttribute("skillsList", skillsService.findByPersonalInfoId(personalInfoId));
            return "skills";
        }

        skill.setPersonalInfo(personalInfo);
        skillsService.save(skill);

        return "redirect:/skills-form?token=" + token;
    }

    @GetMapping("/skills/next")
    public String goToEducation(@RequestParam("token") String token,
                                RedirectAttributes redirectAttributes) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        boolean hasSkills = skillsService.existsByPersonalInfoId(personalInfoId);

        if (!hasSkills) {
            redirectAttributes.addFlashAttribute("nextError",
                    "Debes añadir al menos una skill antes de continuar.");
            return "redirect:/skills-form?token=" + token;
        }

        return "redirect:/education?token=" + token;
    }

    @GetMapping("/education")
    public String showEducationForm(@RequestParam("token") String token, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        model.addAttribute("education", new Education());
        model.addAttribute("token", token);
        model.addAttribute("educationList", educationService.findByPersonalInfoId(personalInfoId));
        return "education";
    }

    @PostMapping("/education/save")
    public String saveEducation(@Valid @ModelAttribute("education") Education education,
                                BindingResult result,
                                @RequestParam("token") String token,
                                Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        PersonalInfo personalInfo = personalInfoOpt.get();
        Long personalInfoId = personalInfo.getId();

        if (result.hasErrors()) {
            model.addAttribute("token", token);
            model.addAttribute("educationList", educationService.findByPersonalInfoId(personalInfoId));
            return "education";
        }

        education.setPersonalInfo(personalInfo);
        educationService.save(education);

        return "redirect:/education?token=" + token;
    }

    @GetMapping("/education/next")
    public String goToProjects(@RequestParam("token") String token,
                               RedirectAttributes redirectAttributes) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        boolean hasEducation = educationService.existsByPersonalInfoId(personalInfoId);

        if (!hasEducation) {
            redirectAttributes.addFlashAttribute("nextError",
                    "Debes añadir al menos una formación antes de continuar.");
            return "redirect:/education?token=" + token;
        }

        return "redirect:/experience?token=" + token;
    }

    @GetMapping("/experience")
    public String showExperienceForm(@RequestParam("token") String token, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        model.addAttribute("experience", new Experience());
        model.addAttribute("token", token);
        model.addAttribute("experienceList", experienceService.findByPersonalInfoId(personalInfoId));
        return "experiencia";
    }

    @PostMapping("/experience/save")
    public String saveExperience(@Valid @ModelAttribute("experience") Experience experience,
                                 BindingResult result,
                                 @RequestParam("token") String token,
                                 Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        PersonalInfo personalInfo = personalInfoOpt.get();
        Long personalInfoId = personalInfo.getId();

        if (result.hasErrors()) {
            model.addAttribute("token", token);
            model.addAttribute("experienceList", experienceService.findByPersonalInfoId(personalInfoId));
            return "experiencia";
        }

        experience.setPersonalInfo(personalInfo);
        experienceService.save(experience);

        return "redirect:/experience?token=" + token;
    }

    @GetMapping("/projects")
    public String showProjectsForm(@RequestParam("token") String token, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        model.addAttribute("project", new Projects());
        model.addAttribute("token", token);
        model.addAttribute("projectsList", projectsService.findByPersonalInfoId(personalInfoId));
        return "proyectos";
    }

    @PostMapping("/projects/save")
    public String saveProject(@Valid @ModelAttribute("project") Projects project,
                              BindingResult result,
                              @RequestParam("token") String token,
                              Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        PersonalInfo personalInfo = personalInfoOpt.get();
        Long personalInfoId = personalInfo.getId();

        if (result.hasErrors()) {
            model.addAttribute("token", token);
            model.addAttribute("projectsList", projectsService.findByPersonalInfoId(personalInfoId));
            return "proyectos";
        }

        project.setPersonalInfo(personalInfo);
        projectsService.save(project);

        return "redirect:/projects?token=" + token;
    }

    @GetMapping("/projects/next")
    public String goToLanguages(@RequestParam("token") String token,
                                RedirectAttributes redirectAttributes) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        boolean hasProjects = projectsService.existsByPersonalInfoId(personalInfoId);

        if (!hasProjects) {
            redirectAttributes.addFlashAttribute("nextError",
                    "Debes añadir al menos un proyecto antes de continuar.");
            return "redirect:/projects?token=" + token;
        }

        return "redirect:/idiomas?token=" + token;
    }

    @GetMapping("/idiomas")
    public String showIdiomasForm(@RequestParam("token") String token, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        Long personalInfoId = personalInfoOpt.get().getId();

        model.addAttribute("idiomas", new Idiomas());
        model.addAttribute("token", token);
        model.addAttribute("idiomasList", idiomasService.findByPersonalInfoId(personalInfoId));
        return "idiomas";
    }

    @PostMapping("/idiomas/save")
    public String saveIdiomas(@Valid @ModelAttribute("idiomas") Idiomas idiomas,
                              BindingResult result,
                              @RequestParam("token") String token,
                              Model model) {

        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        PersonalInfo personalInfo = personalInfoOpt.get();
        Long personalInfoId = personalInfo.getId();

        if (result.hasErrors()) {
            model.addAttribute("token", token);
            model.addAttribute("idiomasList", idiomasService.findByPersonalInfoId(personalInfoId));
            return "idiomas";
        }

        idiomas.setPersonalInfo(personalInfo);
        idiomasService.save(idiomas);

        return "redirect:/idiomas?token=" + token;
    }

    @GetMapping("/portfolio")
    public String showPortfolio(@RequestParam("token") String token, Model model) {
        Optional<PersonalInfo> personalInfoOpt = personalInfoService.findByEditToken(token);

        if (personalInfoOpt.isEmpty()) {
            return "redirect:/form";
        }

        PersonalInfo personalInfo = personalInfoOpt.get();
        Long personalInfoId = personalInfo.getId();

        model.addAttribute("personalInfo", personalInfo);
        model.addAttribute("token", token);
        model.addAttribute("skillsList", skillsService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("educationList", educationService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("experienceList", experienceService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("projectsList", projectsService.findByPersonalInfoId(personalInfoId));
        model.addAttribute("idiomasList", idiomasService.findByPersonalInfoId(personalInfoId));

        return "portfolio";
    }

}
