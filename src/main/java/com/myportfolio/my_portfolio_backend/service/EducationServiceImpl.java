package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Education;
import com.myportfolio.my_portfolio_backend.repository.IEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements IEducationService{

    private final IEducationRepository educationRepository ;

    @Override
    @Transactional
    public Education save(Education education) {
        return educationRepository.save(education);
    }

    @Override
    @Transactional
    public Optional<Education> update(Long id, Education education) {
        Optional<Education> educationOptional = educationRepository.findById(id) ;
        if (educationOptional.isPresent()){
            Education educationDb = educationOptional.orElseThrow() ;
            educationDb.setTitulo(education.getTitulo());
            educationDb.setDescripcion(education.getDescripcion());
            educationDb.setCentro(education.getCentro());
            educationDb.setInicio(education.getInicio());
            educationDb.setFin(education.getFin());
            educationDb.setPersonalInfo(education.getPersonalInfo());

            return Optional.of(educationRepository.save(educationDb)) ;
        }
        return educationOptional ;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Education> findById(Long id) {
        return educationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Education> findAll() {
        return (List<Education>) educationRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Education> findByPersonalInfoId(Long personal_info_id) {
        return educationRepository.findByPersonalInfoId(personal_info_id);
    }

    @Override
    public boolean existsByPersonalInfoId(Long personalInfoId) {
        return educationRepository.existsByPersonalInfoId(personalInfoId);
    }

}
