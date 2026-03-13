package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.entity.Skills;

import java.util.List;
import java.util.Optional;

public interface ISkillService {
    Skills save(Skills skill) ;
    Optional<Skills> findById(Long id) ;
    List<Skills> findAll() ;
    void deleteById(Long id) ;
    List<Skills> findByPersonalInfoId(Long personalInfoId) ;
    List<Skills> searchByName(String query);
    boolean existsByPersonalInfoId(Long personalInfoId);
    Optional<Skills> update(Long id, Skills skills) ;

}
