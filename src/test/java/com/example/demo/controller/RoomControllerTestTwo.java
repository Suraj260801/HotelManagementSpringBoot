package com.example.demo.controller;
import com.example.demo.models.Room;
import com.example.demo.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(RoomController.class) // Loads only RoomController and necessary Spring components
public class RoomControllerTestTwo {

    @Autowired
    private MockMvc mockMvc; // Auto-configured with Spring context

    @MockBean
    private RoomService roomService; // Mocking Service Layer (auto-managed by Spring)

    @WithMockUser(username = "user", roles = {"USER"}) // Provide authentication
    @Test
    public void testGetAllRooms() throws Exception {
        List<Room> expectedResult = List.of(
                new Room(1L, "D", true, 2000, 10),
                new Room(2L, "E", false, 1000, 10)
        );

        when(roomService.findAllRooms()).thenReturn(expectedResult);

        ResultActions actualResult = mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());

        actualResult
                .andExpect(jsonPath("$[0].type").value("D"))
                .andExpect(jsonPath("$[0].available").value(true))
                .andExpect(jsonPath("$[0].price").value(2000))
                .andExpect(jsonPath("$[1].type").value("E"))
                .andExpect(jsonPath("$[1].available").value(false))
                .andExpect(jsonPath("$[1].price").value(1000));

        Mockito.verify(roomService, Mockito.times(1)).findAllRooms();
    }

    @WithMockUser(username = "user", roles = {"USER"}) // Provide authentication
    @Test
    public void testGetRoomById() throws Exception {
        Room expectedResult = new Room(1L, "F", true, 3000, 10);

        when(roomService.findRoomById(1L)).thenReturn(expectedResult);

        ResultActions actualResult = mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk());

        actualResult
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("F"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.price").value(3000));

        Mockito.verify(roomService, Mockito.times(1)).findRoomById(1L);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Provide authentication
    @Test
    public void testCreateRoom() throws Exception {
        Room expectedResult = new Room(1L, "A", true, 3000, 10);

        when(roomService.saveRoom(any(Room.class))).thenReturn(expectedResult);

        ResultActions actualResult = mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"type\":\"A\",\"available\":true,\"price\":2000}").with(csrf()));

        actualResult.andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("A"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.price").value(3000));

        Mockito.verify(roomService, Mockito.times(1)).saveRoom(any(Room.class));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Provide authentication
    @Test
    public void testDeleteRoom() throws Exception {
        doNothing().when(roomService).deleteRoom(1L);

        mockMvc.perform(delete("/rooms/1").with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(roomService, Mockito.times(1)).deleteRoom(1L);
    }
}
