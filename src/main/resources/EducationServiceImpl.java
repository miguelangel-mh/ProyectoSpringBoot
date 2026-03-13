package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.exception.ValidationException;
import com.myportfolio.my_portfolio_backend.entity.Education;
import com.myportfolio.my_portfolio_backend.repository.IEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements IEducationService{

    private final IEducationRepository educationRepository ;
    private final Validator validator ;

    @Override
    @Transactional
    public Education save(Education education) {

        BindingResult result = new BeanPropertyBindingResult(education, "education") ;
        validator.validate(education, result);

        if (result.hasErrors()){
            throw new ValidationException(result) ;
        }

        return educationRepository.save(education);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Education> findById(Long id) {
        return educationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Education> findByPersonalInfoId(Long personal_info_id) {
        return educationRepository.findByPersonalInfoId(personal_info_id) ;
    }
}
