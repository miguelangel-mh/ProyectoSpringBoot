package com.myportfolio.my_portfolio_backend.repository;

import com.myportfolio.my_portfolio_backend.entity.Experience;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IExperienceRepository extends CrudRepository<Experience, Long> {

    List<Experience> findByPersonalInfoId (Long personalInfoId) ;

}
