package com.myportfolio.my_portfolio_backend.repository;

import com.myportfolio.my_portfolio_backend.entity.Projects;
import com.myportfolio.my_portfolio_backend.entity.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProjectsRepository extends CrudRepository<Projects, Long> {

    List<Projects> findByPersonalInfoId(Long personalInfoId) ;
    boolean existsByPersonalInfoId(Long personalInfoId);

}
