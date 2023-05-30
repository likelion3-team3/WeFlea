package com.ll.weflea.boundedContext.chat.repository;

import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.sender JOIN FETCH cr.receiver")
    List<ChatRoom> findAll();

    Optional<ChatRoom> findByRoomId(String id);

}
