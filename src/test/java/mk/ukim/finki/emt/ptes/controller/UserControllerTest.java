package mk.ukim.finki.emt.ptes.controller;

import mk.ukim.finki.emt.ptes.model.User;
import mk.ukim.finki.emt.ptes.model.dto.UserDto;
import mk.ukim.finki.emt.ptes.model.dto.maper.UserMapper;
import mk.ukim.finki.emt.ptes.model.exceptions.PetsNotFound;
import mk.ukim.finki.emt.ptes.model.exceptions.UsersNotFound;
import mk.ukim.finki.emt.ptes.service.UserService;
import mk.ukim.finki.emt.ptes.web.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    @Test
    public void createUser_returnsUser() throws Exception {
        User user = new User();
        user.setName("mahmud");

        when(userService.createFactory()).thenReturn(user);

        mvc.perform(get("/api/users/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mahmud"));

        verify(userService).createFactory();
    }

    @Test
    public void getAllUsers_returnsListUserDto() throws Exception {
        User user = new User();
        user.setName("mahmud");

        User user2 = new User();
        user2.setName("meme");

        UserDto dto = UserDto.builder().name("mahmud").build();
        UserDto dto2 = UserDto.builder().name("meme").build();

        when(userService.findAll()).thenReturn(List.of(user, user2));
        when(userMapper.toListUserDto(anyList())).thenReturn(List.of(dto, dto2));

        mvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("mahmud"))
                .andExpect(jsonPath("$[1].name").value("meme"));

        verify(userService).findAll();
        verify(userMapper).toListUserDto(List.of(user, user2));
    }

    @Test
    public void createUsers_withoutNumber_returnsListOf20Users() throws Exception {
        List<User> users = List.of(new User(), new User());

        when(userService.createUsers(20)).thenReturn(users);
        mvc.perform(get("/api/users/create-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(userService).createUsers(20);
    }

    @Test
    public void createUsers_withNumber_returnsRequestedAmount() throws Exception {
        List<User> users = List.of(new User(), new User(), new User());

        when(userService.createUsers(3)).thenReturn(users);
        mvc.perform(get("/api/users/create-users/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

        verify(userService).createUsers(3);
    }

    @Test
    public void createUsers_withNegativeNumber_returnsOK() throws Exception {
        when(userService.createUsers(-6)).thenReturn(List.of(new User(), new User()));
        mvc.perform(get("/api/users/create-users/-6"))
                .andExpect(status().isOk());

        verify(userService).createUsers(-6);
    }

    @Test
    public void buy_ReturnUsersWithSuccessfulPurchases() throws Exception {
        User user1 = User.builder().id(1L).name("mahmud").budget(100).pets(new ArrayList<>()).build();
        User user2 = User.builder().id(2L).name("meme").budget(1200).pets(new ArrayList<>()).build();

        when(userService.buy()).thenReturn(List.of(user1, user2));

        mvc.perform(get("/api/users/buy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("mahmud"))
                .andExpect(jsonPath("$[1].name").value("meme"));
    }

    @Test
    public void buy_ReturnBadRequestWhenNoUsersExist() throws Exception {
        when(userService.buy()).thenThrow(new UsersNotFound());

        mvc.perform(get("/api/users/buy"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There are 0 users in the database"));
    }

    @Test
    public void buy_ReturnBadRequestWhenNoPetsExist() throws Exception {
        when(userService.buy()).thenThrow(new PetsNotFound());

        mvc.perform(get("/api/users/buy"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There are no pets in your database"));
    }

    @Test
    void buy_ReturnEmptyListNoPurchasesMade() throws Exception {
        when(userService.buy()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/users/buy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}
