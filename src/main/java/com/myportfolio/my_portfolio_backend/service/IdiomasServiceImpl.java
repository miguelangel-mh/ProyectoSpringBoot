package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Education;
import com.myportfolio.my_portfolio_backend.entity.Idiomas;
import com.myportfolio.my_portfolio_backend.repository.IIdiomasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdiomasServiceImpl implements IIdiomasService{

    private final IIdiomasRepository idiomasRepository ;

    @Override
    @Transactional
    public Idiomas save(Idiomas idiomas) {
        return idiomasRepository.save(idiomas);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Idiomas> findById(Long id) {
        return idiomasRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Idiomas> findAll() {
        return (List<Idiomas>) idiomasRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idiomasRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Idiomas> findByPersonalInfoId(Long personal_info_id) {
        return idiomasRepository.findByPersonalInfoId(personal_info_id);
    }

    @Override
    @Transactional
    public Optional<Idiomas> update(Long id, Idiomas idiomas) {
        Optional<Idiomas> idiomasOptional = idiomasRepository.findById(id) ;
        if (idiomasOptional.isPresent()){
            Idiomas idiomasDb = idiomasOptional.orElseThrow() ;
            idiomasDb.setIdioma(idiomas.getIdioma());
            idiomasDb.setNivel(idiomas.getNivel());
            idiomasDb.setIcono(idiomas.getIcono());
            idiomasDb.setPersonalInfo(idiomas.getPersonalInfo());

            return Optional.of(idiomasRepository.save(idiomasDb)) ;
        }
        return idiomasOptional ;
    }
}
