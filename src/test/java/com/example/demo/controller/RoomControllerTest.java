package com.example.demo.controller;
import static org.mockito.Mockito.*;
import com.example.demo.models.Room;
import com.example.demo.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;


    @InjectMocks
    private RoomController roomController;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(roomController).build();
    }


    @Test
    public void testGetAllRooms() throws Exception {


        List<Room> expectedResult=List.of(new Room(1L,"D",true,2000,10),
                new Room(2L,"E",false,1000,10));


        when(roomService.findAllRooms()).thenReturn(expectedResult);

        ResultActions actualResult= mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());


       actualResult.andExpect(jsonPath("$[0].type").value("D"))
                .andExpect(jsonPath("$[0].available").value(true))
                .andExpect(jsonPath("$[0].price").value(2000))
                .andExpect(jsonPath("$[1].type").value("E"))
                .andExpect(jsonPath("$[1].available").value(false))
                .andExpect(jsonPath("$[1].price").value(1000));

        verify(roomService,times(1)).findAllRooms();
    }


    @Test
    public void testGetRoomById() throws Exception {

        Room expectedResult=new Room(1L,"F",true,3000,10);

        when(roomService.findRoomById(1L)).thenReturn(expectedResult);

        ResultActions actualResult=mockMvc.perform(get("/rooms/1")).andExpect(status().isOk());

        actualResult
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("F"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.price").value(3000));

        verify(roomService,times(1)).findRoomById(1L);



    }


    @Test
    public void testCreateRoom() throws Exception {


        Room expectedResult=new Room(1L,"A",true,3000,10);

        when(roomService.saveRoom(any(Room.class))).thenReturn(expectedResult);

        ResultActions actualResult=mockMvc.perform(post("/rooms").
                contentType(MediaType.APPLICATION_JSON).
                content("{\"type\":\"A\",\"available\":true,\"price\":2000}"));

        actualResult.andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("A"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.price").value(3000));

        verify(roomService,times(1)).saveRoom(any(Room.class));

    }

    @Test
    public void testDeleteRoom() throws Exception {

      doNothing().when(roomService).deleteRoom(1L);

      mockMvc.perform(delete("/rooms/1")).andExpect(status().isOk());

      verify(roomService,times(1)).deleteRoom(1L);
    }
}
