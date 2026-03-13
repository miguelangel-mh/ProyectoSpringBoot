package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Idiomas;
import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;

import java.util.List;
import java.util.Optional;

public interface IIdiomasService {

    Idiomas save(Idiomas idiomas) ;
    Optional<Idiomas> findById(Long id) ;
    List<Idiomas> findAll() ;
    void deleteById(Long id) ;
    List<Idiomas> findByPersonalInfoId(Long personal_info_id) ;
    Optional<Idiomas> update(Long id, Idiomas idiomas) ;

}
