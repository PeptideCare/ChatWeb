package com.imdev.webchat.repository;

import com.imdev.webchat.domain.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<Long, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoomRepository> findAllRoom(){
        List chatRooms = new ArrayList(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(Long id){
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(Long id, String name){
        ChatRoom chatRoom = ChatRoom.create(id, name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
