package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.Idiomas;
import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.repository.IIdiomasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IdiomasServiceImplTest {

    @Mock
    private IIdiomasRepository idiomasRepository;

    @InjectMocks
    private IdiomasServiceImpl idiomasService;

    @Test
    void saveTest() {
        Idiomas idioma = createIdioma();

        when(idiomasRepository.save(idioma)).thenReturn(idioma);

        Idiomas result = idiomasService.save(idioma);

        assertNotNull(result);
        assertEquals("Inglés", result.getIdioma());
        assertEquals("C1", result.getNivel());
        assertEquals("mdi:translate", result.getIcono());
        verify(idiomasRepository, times(1)).save(idioma);
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        Idiomas idioma = createIdiomaWithId(id);

        when(idiomasRepository.findById(id)).thenReturn(Optional.of(idioma));

        Optional<Idiomas> result = idiomasService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Inglés", result.get().getIdioma());
        verify(idiomasRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotExistTest() {
        Long id = 99L;

        when(idiomasRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Idiomas> result = idiomasService.findById(id);

        assertTrue(result.isEmpty());
        verify(idiomasRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() {
        Idiomas idioma1 = createIdiomaWithId(1L);
        Idiomas idioma2 = createIdiomaWithId(2L);
        idioma2.setIdioma("Italiano");
        idioma2.setNivel("B2");

        when(idiomasRepository.findAll()).thenReturn(List.of(idioma1, idioma2));

        List<Idiomas> result = idiomasService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inglés", result.get(0).getIdioma());
        assertEquals("Italiano", result.get(1).getIdioma());
        verify(idiomasRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;

        doNothing().when(idiomasRepository).deleteById(id);

        idiomasService.deleteById(id);

        verify(idiomasRepository, times(1)).deleteById(id);
    }

    @Test
    void findByPersonalInfoIdTest() {
        Long personalInfoId = 1L;
        Idiomas idioma1 = createIdiomaWithId(1L);
        Idiomas idioma2 = createIdiomaWithId(2L);
        idioma2.setIdioma("Francés");
        idioma2.setNivel("B1");

        when(idiomasRepository.findByPersonalInfoId(personalInfoId))
                .thenReturn(List.of(idioma1, idioma2));

        List<Idiomas> result = idiomasService.findByPersonalInfoId(personalInfoId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inglés", result.get(0).getIdioma());
        assertEquals("Francés", result.get(1).getIdioma());
        verify(idiomasRepository, times(1)).findByPersonalInfoId(personalInfoId);
    }

    @Test
    void updateTest() {
        Long id = 1L;
        Idiomas idiomaDb = createOldIdioma(id);
        Idiomas updatedIdioma = createUpdatedIdioma();

        when(idiomasRepository.findById(id)).thenReturn(Optional.of(idiomaDb));
        when(idiomasRepository.save(any(Idiomas.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Idiomas> result = idiomasService.update(id, updatedIdioma);

        assertTrue(result.isPresent());
        assertEquals("Alemán", result.get().getIdioma());
        assertEquals("A2", result.get().getNivel());
        assertEquals("mdi:translate-variant", result.get().getIcono());
        assertEquals(updatedIdioma.getPersonalInfo(), result.get().getPersonalInfo());

        verify(idiomasRepository, times(1)).findById(id);
        verify(idiomasRepository, times(1)).save(idiomaDb);
    }

    @Test
    void updateNotExistTest() {
        Long id = 99L;
        Idiomas updatedIdioma = createUpdatedIdioma();

        when(idiomasRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Idiomas> result = idiomasService.update(id, updatedIdioma);

        assertTrue(result.isEmpty());
        verify(idiomasRepository, times(1)).findById(id);
        verify(idiomasRepository, never()).save(any(Idiomas.class));
    }

    private Idiomas createIdioma() {
        Idiomas idioma = new Idiomas();
        idioma.setIdioma("Inglés");
        idioma.setNivel("C1");
        idioma.setIcono("mdi:translate");
        idioma.setPersonalInfo(createPersonalInfo(1L));
        return idioma;
    }

    private Idiomas createIdiomaWithId(Long id) {
        Idiomas idioma = createIdioma();
        idioma.setId(id);
        return idioma;
    }

    private Idiomas createOldIdioma(Long id) {
        Idiomas idioma = new Idiomas();
        idioma.setId(id);
        idioma.setIdioma("Español");
        idioma.setNivel("Nativo");
        idioma.setIcono("mdi:web");
        idioma.setPersonalInfo(createPersonalInfo(1L));
        return idioma;
    }

    private Idiomas createUpdatedIdioma() {
        Idiomas idioma = new Idiomas();
        idioma.setIdioma("Alemán");
        idioma.setNivel("A2");
        idioma.setIcono("mdi:translate-variant");
        idioma.setPersonalInfo(createPersonalInfo(2L));
        return idioma;
    }

    private PersonalInfo createPersonalInfo(Long id) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(id);
        personalInfo.setNombre("Miguel Ángel");
        personalInfo.setApellidos("Martínez Herrera");
        personalInfo.setTitulo("Full Stack Developer");
        personalInfo.setDescripcion("Aprendiendo y mejorando cada día");
        personalInfo.setTelefono("612345678");
        personalInfo.setEmail("miguel@email.com");
        personalInfo.setImagen("/img/perfil.png");
        personalInfo.setLinkedinUrl("https://linkedin.com/in/miguel");
        return personalInfo;
    }

}
