package com.example.demo.repository;

import com.example.demo.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;


    @BeforeEach
    public void setUp(){
        roomRepository .deleteAll();
    }



    @Test
    public void testSaveAndFindRoomById(){
        Room expectedResult=new Room(1L,"A",true,1000,10);

        roomRepository.save(expectedResult);

        Optional<Room> actualResult=roomRepository.findById(1L);

        assertNotNull(actualResult);
        assertFalse(actualResult.isEmpty());
        assertEquals(1L,actualResult.get().getId());
        assertEquals("A",actualResult.get().getType());
        assertTrue(actualResult.get().getAvailable());
        assertEquals(1000,actualResult.get().getPrice());

    }

    @Test
    public void testFindAllRooms(){


        List<Room> expectedResult=List.of(new Room(1L,"D",true,2000,10),
                new Room(2L,"E",false,1000,10));

        roomRepository.saveAll(expectedResult);

        List<Room> actualResult=roomRepository.findAll();

        assertFalse(actualResult.isEmpty());

        assertEquals(2,actualResult.size());
        assertEquals(1L,actualResult.get(0).getId());
        assertEquals("D",actualResult.get(0).getType());
        assertTrue(actualResult.get(0).getAvailable());
        assertEquals(2000,actualResult.get(0).getPrice());
        assertEquals(2L,actualResult.get(1).getId());
        assertEquals("E",actualResult.get(1).getType());
        assertFalse(actualResult.get(1).getAvailable());
        assertEquals(1000,actualResult.get(1).getPrice());


    }




    @Test
    public void testDeleteRoom(){
        Room expectedResult=new Room(1L,"A",true,1000,10);

        Room savedResult=roomRepository.save(expectedResult);
        roomRepository.deleteById(savedResult.getId());

        Optional<Room> actualResult=roomRepository.findById(savedResult.getId());

        assertFalse(actualResult.isPresent());


    }


}
