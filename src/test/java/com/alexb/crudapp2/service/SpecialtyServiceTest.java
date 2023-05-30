package com.alexb.crudapp2.service;

import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.repository.SpecialtyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SpecialtyServiceTest {
    @Mock
    SpecialtyRepository specialtyRepository;
    @InjectMocks
    SpecialtyService specialtyService;

    @Test
    public void createSpecialtyTest() {
        Specialty specialty = new Specialty("Backend developer");
        Mockito.when(specialtyRepository.save(specialty)).thenReturn(specialty);

        Specialty specialtyFromDB = specialtyService.creationSpecialty(specialty);
        Assertions.assertNotNull(specialtyFromDB);
        Assertions.assertEquals(specialty, specialtyFromDB);
        Mockito.verify(specialtyRepository, Mockito.times(1)).save(specialty);
    }

    @Test
    public void readSpecialtyTest() {
        List<Specialty> specialtyList = new ArrayList<>();
        specialtyList.add(new Specialty("Backend developer"));
        specialtyList.add(new Specialty("DevOps"));

        Mockito.when(specialtyRepository.getAll()).thenReturn(specialtyList);

        List<Specialty> specialtiesFromDB = specialtyService.getAllSpecialties();

        Assertions.assertEquals(specialtyList, specialtiesFromDB);
        Mockito.verify(specialtyRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void updateSpecialtyTest() {
        Specialty specialty = new Specialty("Backend developer");

        Mockito.when(specialtyRepository.update(specialty)).thenReturn(specialty);

        Specialty specialtyFromDB = specialtyService.editSpecialty(specialty);
        Assertions.assertNotNull(specialtyFromDB);
        Assertions.assertEquals(specialty, specialtyFromDB);
        Mockito.verify(specialtyRepository, Mockito.times(1)).update(specialty);
    }

    @Test
    public void deleteSpecialtyTest() {
        specialtyService.deleteSpecialty(1L);
        Mockito.verify(specialtyRepository, Mockito.times(1)).deleteById(1L);
    }
}


