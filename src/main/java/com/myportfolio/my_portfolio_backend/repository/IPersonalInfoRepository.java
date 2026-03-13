package com.myportfolio.my_portfolio_backend.repository;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IPersonalInfoRepository extends CrudRepository<PersonalInfo, Long> {

    Optional<PersonalInfo> findByEditToken(String editToken);

}
