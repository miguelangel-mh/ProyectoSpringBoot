package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Projects;
import com.myportfolio.my_portfolio_backend.repository.IProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements IProjectsService {

    private final IProjectsRepository projectsRepository ;

    @Override
    @Transactional
    public Projects save(Projects projects) {
        return projectsRepository.save(projects);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Projects> findById(Long id) {
        return projectsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projects> findAll() {
        return (List<Projects>) projectsRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        projectsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projects> findByPersonalInfoId(Long personal_info_id) {
        return projectsRepository.findByPersonalInfoId(personal_info_id);
    }

    @Override
    public boolean existsByPersonalInfoId(Long personalInfoId) {
        return projectsRepository.existsByPersonalInfoId(personalInfoId);
    }

    @Override
    @Transactional
    public Optional<Projects> update(Long id, Projects projects) {
        Optional<Projects> projectsOptional = projectsRepository.findById(id) ;
        if (projectsOptional.isPresent()){
            Projects projectsDb = projectsOptional.orElseThrow() ;
            projectsDb.setTitulo(projects.getTitulo());
            projectsDb.setDescripcion(projects.getDescripcion());
            projectsDb.setImagenUrl(projects.getImagenUrl());
            projectsDb.setProyectoUrl(projects.getProyectoUrl());
            projectsDb.setPersonalInfo(projects.getPersonalInfo());

            return Optional.of(projectsRepository.save(projectsDb)) ;
        }
        return projectsOptional ;
    }
}
