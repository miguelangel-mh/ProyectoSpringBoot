package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Education;
import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;

import java.util.List;
import java.util.Optional;

public interface IEducationService {
    Education save(Education education) ;
    Optional<Education> findById(Long id) ;
    List<Education> findAll() ;
    void deleteById(Long id) ;
    List<Education> findByPersonalInfoId(Long personal_info_id) ;
    boolean existsByPersonalInfoId(Long personalInfoId);
    Optional<Education> update(Long id, Education education) ;

}
