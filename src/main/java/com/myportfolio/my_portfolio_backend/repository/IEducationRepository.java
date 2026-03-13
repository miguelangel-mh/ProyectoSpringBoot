package com.myportfolio.my_portfolio_backend.repository;

import com.myportfolio.my_portfolio_backend.entity.Education;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IEducationRepository extends CrudRepository<Education, Long> {

    List<Education> findByPersonalInfoId(Long personal_info_id) ;
    boolean existsByPersonalInfoId(Long personalInfoId);

}
