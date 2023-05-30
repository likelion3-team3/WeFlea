package com.ll.weflea.boundedContext.chat.repository;

import com.ll.weflea.boundedContext.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.sender JOIN FETCH cr.receiver")
    List<ChatRoom> findAll();

    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.sender cs WHERE cs.nickname = :nickname")
    List<ChatRoom> findByChatRoom_Sender_Nickname(@Param("nickname") String nickname);

    Optional<ChatRoom> findByRoomId(String id);

}
