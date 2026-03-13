package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Experience;
import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;

import java.util.List;
import java.util.Optional;

public interface IExperienceService {
    Experience save(Experience experience) ;
    Optional<Experience> findById(Long id) ;
    List<Experience> findAll() ;
    void deleteById(Long id) ;
    List<Experience> findByPersonalInfoId (Long personalInfoId) ;
    Optional<Experience> update(Long id, Experience experience) ;

}
