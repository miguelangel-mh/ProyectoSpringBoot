package com.myportfolio.my_portfolio_backend.repository;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.entity.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ISkillsRepository extends CrudRepository<Skills, Long> {

    List<Skills> findByPersonalInfoId(Long personalInfoId) ;
    List<Skills> searchByName(String query);
    List<Skills> findByNameContainingIgnoreCase(String query);
    boolean existsByPersonalInfoId(Long personalInfoId);

}
