package com.myportfolio.my_portfolio_backend.repository;

import com.myportfolio.my_portfolio_backend.entity.Idiomas;
import com.myportfolio.my_portfolio_backend.entity.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IIdiomasRepository extends CrudRepository<Idiomas, Long> {

    List<Idiomas> findByPersonalInfoId(Long personalInfoId) ;

}
