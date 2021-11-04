package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Chat;
import com.imdev.webchat.domain.ChatItem;
import com.imdev.webchat.domain.Member;
import com.imdev.webchat.service.ChatItemService;
import com.imdev.webchat.service.ChatService;
import com.imdev.webchat.service.MemberService;
import com.imdev.webchat.domain.ChatRoom;
import com.imdev.webchat.repository.ChatRoomRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Getter
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatItemService chatItemService;
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;

    // 방만들기
    @GetMapping("/chat/insert")
    public String saveForm(Model model) {
        model.addAttribute("chat", new ChatForm());
        return "chat/make_room";
    }

    @PostMapping("/chat/insert")
    public String save(@Valid ChatForm form, HttpSession session) {
        String id = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(id).get();

        Chat chat = new Chat(form.getName(), form.getMax_num(), 1, findMember.getSchool_name());
        chatService.save(chat, findMember);

        // 웹소켓방 생성
        chatRoomRepository.createChatRoom(chat.getId(), form.getName());

        return "redirect:/chat/find/mine";
    }

    // 학교별 모든 방 조회
    @GetMapping("/chat/find/new")
    public String findAll(Model model, HttpSession session) {

        // 접속해있는 회원의 학교이름 불러오기
        String memberId = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId).get();

        // 학교별 모든 방 조회
        List<Chat> chats = chatService.findAllBySchoolName(findMember.getSchool_name());
        model.addAttribute("chatList", chats);
        model.addAttribute("search", new SearchForm());
        return "chat/new_chat";
    }

    // 내 채팅방 조회
    @GetMapping("/chat/find/mine")
    public String findAllById(Model model, HttpSession session) {

        String memberId = (String)session.getAttribute("memberId");
        List<ChatItem> chats = chatItemService.findAllById(memberId);
        model.addAttribute("chatList", chats);

        return "chat/main";
    }

    // 채팅방 입장
    @GetMapping("/chat/enter/{id}")
    public String chat(@PathVariable Long id, Model model, HttpSession session) {

        String memberId = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId).get();

        chatItemService.save(findMember, id);

        // 웹소켓 방 불러오기
        ChatRoom room = chatRoomRepository.findRoomById(id);

        model.addAttribute("room", room);

        // 멤버 닉네임 보내기
        RoomForm roomForm = new RoomForm();
        roomForm.setMemberNickname(findMember.getNickname());

        model.addAttribute("roomForm", roomForm);
        return "chat/room";
    }

    // 검색별 모든 채팅 불러오기
    @GetMapping("chat/find/new/search")
    public String findNewByName(SearchForm form, Model model, HttpSession session) {

        String memberId = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId).get();

        System.out.println("ㄹㄹㄹ");

        // 검색 내용별로 불러오기
        List<Chat> chats = chatService.findAllBySearch(form.getSearch(), findMember.getSchool_name());
        model.addAttribute("chatList", chats);
        return "chat/new_chat";
    }
}
