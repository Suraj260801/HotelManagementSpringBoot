package com.example.demo.service;

import com.example.demo.models.Room;
import com.example.demo.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        room=new Room(1L,"A",true,2000,10);
    }


    @Test
    public void testFindAllRooms(){
        List<Room> expectedResult=List.of(room);
        when(roomRepository.findAll()).thenReturn(expectedResult);

        List<Room> actualResult=roomRepository.findAll();

        assertFalse(actualResult.isEmpty());

        assertEquals(1L,actualResult.get(0).getId());
        assertEquals("A",actualResult.get(0).getType());
        assertTrue(actualResult.get(0).getAvailable());
        assertEquals(2000,actualResult.get(0).getPrice());

    }



    @Test
    public void testFindRoomById(){

        when(roomRepository.findById(1L)).thenReturn(Optional.ofNullable(room));

        Optional<Room> actualResult=roomRepository.findById(1L);

        assertFalse(actualResult.isEmpty());

        assertNotNull(actualResult);

        assertEquals(1L,actualResult.get().getId());
        assertEquals("A",actualResult.get().getType());
        assertTrue(actualResult.get().getAvailable());
        assertEquals(2000,actualResult.get().getPrice());

    }

    @Test
    public void testSaveRoom(){

        when(roomRepository.save(room)).thenReturn(room);

        Room actualResult=roomRepository.save(room);

        assertNotNull(actualResult);

        assertEquals(1L,actualResult.getId());
        assertEquals("A",actualResult.getType());
        assertTrue(actualResult.getAvailable());
        assertEquals(2000,actualResult.getPrice());

    }


    @Test
    public void deleteRoom(){

         roomRepository.deleteById(1L);
         verify(roomRepository,times(1)).deleteById(1L);
    }


}
