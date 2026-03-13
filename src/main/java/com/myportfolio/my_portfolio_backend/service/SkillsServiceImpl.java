package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Skills;
import com.myportfolio.my_portfolio_backend.repository.ISkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillsServiceImpl implements ISkillService{

    private final ISkillsRepository skillsRepository ;

    @Override
    @Transactional
    public Skills save(Skills skill) {
        return skillsRepository.save(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skills> findById(Long id) {
        return skillsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skills> findAll() {
        return (List<Skills>) skillsRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        skillsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skills> findByPersonalInfoId(Long personalInfoId) {
        return skillsRepository.findByPersonalInfoId(personalInfoId);
    }

    @Override
    public List<Skills> searchByName(String query) {
        return skillsRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    @Transactional
    public boolean existsByPersonalInfoId(Long personalInfoId) {
        return skillsRepository.existsByPersonalInfoId(personalInfoId);
    }

    @Override
    @Transactional
    public Optional<Skills> update(Long id, Skills skills) {
        Optional<Skills> skillsOptional = skillsRepository.findById(id) ;
        if (skillsOptional.isPresent()){
            Skills skillsDb = skillsOptional.orElseThrow() ;
            skillsDb.setName(skills.getName());
            skillsDb.setLevel(skills.getLevel());
            skillsDb.setIcono(skills.getIcono());
            skillsDb.setPersonalInfo(skills.getPersonalInfo());

            return Optional.of(skillsRepository.save(skillsDb)) ;
        }
        return skillsOptional ;
    }
}
