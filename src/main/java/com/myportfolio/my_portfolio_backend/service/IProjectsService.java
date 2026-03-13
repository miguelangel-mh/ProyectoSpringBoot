package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.entity.Projects;
import java.util.List;
import java.util.Optional;

public interface IProjectsService {

    Projects save(Projects projects) ;
    Optional<Projects> findById(Long id) ;
    List<Projects> findAll() ;
    void deleteById(Long id) ;
    List<Projects> findByPersonalInfoId(Long personal_info_id) ;
    boolean existsByPersonalInfoId(Long personalInfoId);
    Optional<Projects> update(Long id, Projects projects) ;

}
