package com.myportfolio.my_portfolio_backend.service;

import com.myportfolio.my_portfolio_backend.entity.PersonalInfo;
import com.myportfolio.my_portfolio_backend.repository.IPersonalInfoRepository;
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
public class PersonalInfoImplTest {

    @Mock
    private IPersonalInfoRepository personalInfoRepository;

    @InjectMocks
    private PersonalInfoServiceImpl personalInfoService;

    @Test
    void saveTest() {
        PersonalInfo personalInfo = createPersonalInfo();

        when(personalInfoRepository.save(personalInfo)).thenReturn(personalInfo);

        PersonalInfo result = personalInfoService.save(personalInfo);

        assertNotNull(result);
        assertEquals("Miguel Ángel", result.getNombre());
        assertEquals("Martínez Herrera", result.getApellidos());
        assertEquals("Full Stack Developer", result.getTitulo());
        verify(personalInfoRepository, times(1)).save(personalInfo);
    }

    @Test
    void updateTest() {
        Long id = 1L;
        PersonalInfo personalInfoDb = createOldPersonalInfo(id);
        PersonalInfo personalInfoUpdated = createUpdatedPersonalInfo();

        when(personalInfoRepository.findById(id)).thenReturn(Optional.of(personalInfoDb));
        when(personalInfoRepository.save(any(PersonalInfo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<PersonalInfo> result = personalInfoService.update(id, personalInfoUpdated);

        assertTrue(result.isPresent());
        assertEquals("Miguel Ángel", result.get().getNombre());
        assertEquals("Martínez Herrera", result.get().getApellidos());
        assertEquals("Senior Developer", result.get().getTitulo());
        assertEquals("Nueva descripción", result.get().getDescripcion());
        assertEquals("612345678", result.get().getTelefono());
        assertEquals("nuevo@email.com", result.get().getEmail());
        assertEquals("/img/nueva.png", result.get().getImagen());
        assertEquals("https://linkedin.com/new", result.get().getLinkedinUrl());

        verify(personalInfoRepository, times(1)).findById(id);
        verify(personalInfoRepository, times(1)).save(personalInfoDb);
    }

    @Test
    void updateNotExistTest() {
        Long id = 1L;
        PersonalInfo personalInfoUpdated = createUpdatedPersonalInfo();

        when(personalInfoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<PersonalInfo> result = personalInfoService.update(id, personalInfoUpdated);

        assertTrue(result.isEmpty());
        verify(personalInfoRepository, times(1)).findById(id);
        verify(personalInfoRepository, never()).save(any(PersonalInfo.class));
    }

    @Test
    void findByEditTokenTest() {
        String token = "abc123";
        PersonalInfo personalInfo = createPersonalInfo();
        personalInfo.setId(1L);
        personalInfo.setEditToken(token);

        when(personalInfoRepository.findByEditToken(token)).thenReturn(Optional.of(personalInfo));

        Optional<PersonalInfo> result = personalInfoService.findByEditToken(token);

        assertTrue(result.isPresent());
        assertEquals("Miguel Ángel", result.get().getNombre());
        assertEquals(token, result.get().getEditToken());
        verify(personalInfoRepository, times(1)).findByEditToken(token);
    }

    @Test
    void findByEditTokenNotExistTest() {
        String token = "token-inexistente";

        when(personalInfoRepository.findByEditToken(token)).thenReturn(Optional.empty());

        Optional<PersonalInfo> result = personalInfoService.findByEditToken(token);

        assertTrue(result.isEmpty());
        verify(personalInfoRepository, times(1)).findByEditToken(token);
    }

    @Test
    void findByIdTest() {
        Long id = 1L;
        PersonalInfo personalInfo = createPersonalInfo();
        personalInfo.setId(id);

        when(personalInfoRepository.findById(id)).thenReturn(Optional.of(personalInfo));

        Optional<PersonalInfo> result = personalInfoService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Miguel Ángel", result.get().getNombre());
        verify(personalInfoRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNotExistTest() {
        Long id = 99L;

        when(personalInfoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<PersonalInfo> result = personalInfoService.findById(id);

        assertTrue(result.isEmpty());
        verify(personalInfoRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() {
        PersonalInfo p1 = createPersonalInfoWithId(1L, "Miguel");
        PersonalInfo p2 = createPersonalInfoWithId(2L, "Ángel");

        when(personalInfoRepository.findAll()).thenReturn(List.of(p1, p2));

        List<PersonalInfo> result = personalInfoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Miguel", result.get(0).getNombre());
        assertEquals("Ángel", result.get(1).getNombre());
        verify(personalInfoRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        Long id = 1L;

        doNothing().when(personalInfoRepository).deleteById(id);

        personalInfoService.deleteById(id);

        verify(personalInfoRepository, times(1)).deleteById(id);
    }

    private PersonalInfo createPersonalInfo() {
        PersonalInfo personalInfo = new PersonalInfo();
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

    private PersonalInfo createOldPersonalInfo(Long id) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(id);
        personalInfo.setNombre("Miguel");
        personalInfo.setApellidos("Viejo");
        personalInfo.setTitulo("Developer");
        personalInfo.setDescripcion("Descripción antigua");
        personalInfo.setTelefono("600000000");
        personalInfo.setEmail("viejo@email.com");
        personalInfo.setImagen("/img/vieja.png");
        personalInfo.setLinkedinUrl("https://linkedin.com/old");
        return personalInfo;
    }

    private PersonalInfo createUpdatedPersonalInfo() {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setNombre("Miguel Ángel");
        personalInfo.setApellidos("Martínez Herrera");
        personalInfo.setTitulo("Senior Developer");
        personalInfo.setDescripcion("Nueva descripción");
        personalInfo.setTelefono("612345678");
        personalInfo.setEmail("nuevo@email.com");
        personalInfo.setImagen("/img/nueva.png");
        personalInfo.setLinkedinUrl("https://linkedin.com/new");
        return personalInfo;
    }

    private PersonalInfo createPersonalInfoWithId(Long id, String nombre) {
        PersonalInfo personalInfo = createPersonalInfo();
        personalInfo.setId(id);
        personalInfo.setNombre(nombre);
        return personalInfo;
    }

}
