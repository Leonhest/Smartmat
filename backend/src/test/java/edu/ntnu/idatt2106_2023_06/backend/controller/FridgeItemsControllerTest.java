package edu.ntnu.idatt2106_2023_06.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.ItemRemoveDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemLoadDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemSearchDTO;
import edu.ntnu.idatt2106_2023_06.backend.dto.items.fridge_items.FridgeItemUpdateDTO;
import edu.ntnu.idatt2106_2023_06.backend.filter.JwtAuthenticationFilter;
import edu.ntnu.idatt2106_2023_06.backend.model.items.Item;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.item.ItemRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.store.StoreRepository;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import edu.ntnu.idatt2106_2023_06.backend.service.fridge.FridgeService;
import edu.ntnu.idatt2106_2023_06.backend.service.items.ItemService;
import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class FridgeItemsControllerTest {


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @MockBean
    private FridgeService fridgeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ItemRepository itemRepository;

    private String jwt;
    private User user;

    @BeforeEach
    public void setUp() {
        try {
            MockitoAnnotations.openMocks(this);

            user = User
                    .builder()
                    .userId(1L)
                    .username("OleN")
                    .password("password")
                    .firstName("Ole")
                    .lastName("Norman")
                    .email("test@gamil.com")
                    .build();

            userRepository.save(user);

            jwt = jwtService.generateToken(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Handle any exceptions here
        }
    }
    @Test
    public void testAddToFridge() throws Exception {
        ItemDTO itemDTO = new ItemDTO( "Tine Melk", "Tine melk kommer fra fri gående, grass matet kuer.",
                "Kiwi", 200000, null, 1, false, "12345678");
        Long fridgeId = 1L;
        Item item = new Item();

        given(itemService.addItem(itemDTO)).willReturn(item);

        mockMvc.perform(post("/item/fridge/add")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemDTO))
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk());


        verify(itemService, times(1)).addToFridge(itemDTO, fridgeId);
    }

    @Test
    public void testGetFridge() throws Exception {
        Long fridgeId = 1L;
        List<FridgeItemLoadDTO> fridgeItemLoadDTOS = new ArrayList<>();
        fridgeItemLoadDTOS.add(new FridgeItemLoadDTO(1L, "Tine Melk",
                "Tine melk kommer fra fri gående, grass matet kuer.", "Kiwi", 200000,
                null, 1, "l", LocalDateTime.now(), LocalDateTime.now(), "123456"));
        fridgeItemLoadDTOS.add(new FridgeItemLoadDTO(2L, "Tine Melk",
                "Tine melk kommer fra fri gående, grass matet kuer.", "Kiwi", 200000,
                null, 1, "l", LocalDateTime.now(), LocalDateTime.now(), "123456"));
        given(itemService.getFridgeItems(fridgeId)).willReturn(fridgeItemLoadDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/item/fridge/get")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .param("fridgeId", fridgeId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(itemService, times(1)).getFridgeItems(fridgeId);
    }

    @Test
    public void testDeleteItemFromFridge() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO("Tine Melk", "Dairy", 1L, 1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/item/fridge/delete")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRemoveDTO)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).removeItemFromFridge(itemRemoveDTO);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void testUpdateFridgeItem() throws Exception {
        FridgeItemUpdateDTO fridgeItemUpdateDTO = FridgeItemUpdateDTO.builder()
                .itemId(1L)
                .fridgeId(1L)
                .amount(2.0)
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        doNothing().when(itemService).updateFridgeItem(fridgeItemUpdateDTO);
        when(userService.isSuperUser(fridgeItemUpdateDTO.fridgeId(), authentication.getName())).thenReturn(true);

        mockMvc.perform(put("/item/fridge/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fridgeItemUpdateDTO))
                        .with(authentication(authentication)))
                .andExpect(status().isOk());

        verify(itemService, times(1)).updateFridgeItem(fridgeItemUpdateDTO);
    }

    @Test
    public void testSearchFridgeItems() throws Exception {
        FridgeItemSearchDTO fridgeItemSearchDTO = new FridgeItemSearchDTO(1L, "test", "expirationDate", "ASC", 0, 1);

        List<FridgeItemLoadDTO> itemList = new ArrayList<>();
        FridgeItemLoadDTO item1 = new FridgeItemLoadDTO(1L, "Tine Melk",
                "Tine melk kommer fra fri gående, grass matet kuer.", "Kiwi", 200000,
                null, 2, "l", LocalDateTime.now(), LocalDateTime.now(), "123456");
        itemList.add(item1);

        Page<FridgeItemLoadDTO> pageItemList = new PageImpl<>(itemList);

        when(itemService.searchFridgeItems(fridgeItemSearchDTO)).thenReturn(pageItemList);

        mockMvc.perform(post("/item/fridge/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fridgeItemSearchDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].itemId").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Tine Melk"))
                .andExpect(jsonPath("$.content[0].amount").value(2));

        verify(itemService, times(1)).searchFridgeItems(fridgeItemSearchDTO);
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
