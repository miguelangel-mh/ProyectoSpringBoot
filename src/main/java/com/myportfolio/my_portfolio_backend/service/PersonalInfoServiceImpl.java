package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.repository.IPersonalInfoRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalInfoServiceImpl implements IPersonalInfoService{

    private final IPersonalInfoRepository personalInfoRepository ;

    public PersonalInfoServiceImpl(IPersonalInfoRepository personalInfoRepository){
        this.personalInfoRepository = personalInfoRepository ;
    }

    @Override
    @Transactional
    public PersonalInfo save(PersonalInfo personalInfo) {
        return personalInfoRepository.save(personalInfo);
    }

    @Override
    @Transactional
    public Optional<PersonalInfo> update(Long id, PersonalInfo personalInfo){
        Optional<PersonalInfo> personalInfoOptional = personalInfoRepository.findById(id) ;
        if (personalInfoOptional.isPresent()){
            PersonalInfo personalInfoDb = personalInfoOptional.orElseThrow() ;
            personalInfoDb.setNombre(personalInfo.getNombre());
            personalInfoDb.setApellidos(personalInfo.getApellidos());
            personalInfoDb.setDescripcion(personalInfo.getDescripcion());
            personalInfoDb.setTitulo(personalInfo.getTitulo());
            personalInfoDb.setTelefono(personalInfo.getTelefono());
            personalInfoDb.setEmail(personalInfo.getEmail());
            personalInfoDb.setImagen(personalInfo.getImagen());
            personalInfoDb.setLinkedinUrl(personalInfo.getLinkedinUrl());

            return Optional.of(personalInfoRepository.save(personalInfoDb)) ;
        }
        return personalInfoOptional ;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalInfo> findByEditToken(String editToken) {
        return personalInfoRepository.findByEditToken(editToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalInfo> findById(Long id) {
        return personalInfoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalInfo> findAll() {
        return (List<PersonalInfo>) personalInfoRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        personalInfoRepository.deleteById(id);

    }

}
