package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;

import java.util.List;
import java.util.Optional;

public interface IPersonalInfoService {

    PersonalInfo save(PersonalInfo personalInfo) ;
    Optional<PersonalInfo> findById(Long id) ;
    List<PersonalInfo> findAll() ;
    void deleteById(Long id) ;
    Optional<PersonalInfo> update(Long id, PersonalInfo personalInfo) ;
    Optional<PersonalInfo> findByEditToken(String editToken);

}
