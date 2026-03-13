package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Experience;
import com.myportfolio.my_portfolio_backend.repository.IExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements IExperienceService{

    private final IExperienceRepository experienceRepository ;

    @Override
    @Transactional
    public Experience save(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Experience> findById(Long id) {
        return experienceRepository.findById(id) ;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experience> findAll() {
        return (List<Experience>) experienceRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        experienceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experience> findByPersonalInfoId(Long personalInfoId) {
        return experienceRepository.findByPersonalInfoId(personalInfoId) ;
    }

    @Override
    @Transactional
    public Optional<Experience> update(Long id, Experience experience) {
        Optional<Experience> experienceOptional = experienceRepository.findById(id) ;
        if (experienceOptional.isPresent()){
            Experience experienceDb = experienceOptional.orElseThrow() ;
            experienceDb.setTitulo(experience.getTitulo());
            experienceDb.setDescripcion(experience.getDescripcion());
            experienceDb.setEmpresa(experience.getEmpresa());
            experienceDb.setInicio(experience.getInicio());
            experienceDb.setFin(experience.getFin());
            experienceDb.setPersonalInfo(experience.getPersonalInfo());

            return Optional.of(experienceRepository.save(experienceDb)) ;
        }
        return experienceOptional ;
    }
}
