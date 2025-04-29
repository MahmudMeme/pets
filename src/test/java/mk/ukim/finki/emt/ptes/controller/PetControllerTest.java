package mk.ukim.finki.emt.ptes.controller;

import mk.ukim.finki.emt.ptes.model.Pet;
import mk.ukim.finki.emt.ptes.model.dto.PetDto;
import mk.ukim.finki.emt.ptes.model.dto.maper.PetMapper;
import mk.ukim.finki.emt.ptes.service.PetService;
import mk.ukim.finki.emt.ptes.web.controller.PetController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PetService petService;

    @MockitoBean
    private PetMapper petMapper;

    @Test
    public void createPetFactory_returnsPet() throws Exception {
        Pet mockPet = new Pet();
        mockPet.setName("test");

        when(petService.createPetFactory()).thenReturn(mockPet);

        mvc.perform(get("/api/pets/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        verify(petService).createPetFactory();
    }

    @Test
    public void getAllPets_ReturnListOfPetDtos() throws Exception {
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");

        Pet pet2 = new Pet();
        pet2.setName("Rex");

        PetDto dto1 = PetDto.builder().name("Fluffy").build();
        PetDto dto2 = PetDto.builder().name("Rex").build();

        when(petService.getAllPets()).thenReturn(List.of(pet1, pet2));
        when(petMapper.toListDto(anyList())).thenReturn(List.of(dto1, dto2));

        mvc.perform(get("/api/pets/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fluffy"))
                .andExpect(jsonPath("$[1].name").value("Rex"));

        verify(petService).getAllPets();
        verify(petMapper).toListDto(List.of(pet1, pet2));
    }

    @Test
    public void createPets_withoutNumber_Return20Pets() throws Exception {
        List<Pet> mockPets = List.of(new Pet(), new Pet());
        when(petService.createPets(20)).thenReturn(mockPets);

        mvc.perform(get("/api/pets/create-pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(petService).createPets(20);
    }

    @Test
    public void createPets_withNumber_ReturnRequestedAmount() throws Exception {
        List<Pet> mockPets = List.of(new Pet(), new Pet(), new Pet());
        when(petService.createPets(3)).thenReturn(mockPets);

        mvc.perform(get("/api/pets/create-pets/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

        verify(petService).createPets(3);
    }

    @Test
    public void createPets_withNegativeNumber_HandleGracefully() throws Exception {
        when(petService.createPets(-5)).thenReturn(List.of(new Pet()));

        mvc.perform(get("/api/pets/create-pets/-5"))
                .andExpect(status().isOk());

        verify(petService).createPets(-5);
    }
}
