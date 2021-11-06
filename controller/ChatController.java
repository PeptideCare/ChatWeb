package com.imdev.webchat.controller;

import com.imdev.webchat.domain.*;
import com.imdev.webchat.service.ChatItemService;
import com.imdev.webchat.service.ChatService;
import com.imdev.webchat.service.MemberService;
import com.imdev.webchat.repository.ReportRepository;
import com.imdev.webchat.service.MessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Getter
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatItemService chatItemService;
    private final MemberService memberService;
    private final ReportRepository chatRoomRepository;
    private final MessageService messageService;

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
        model.addAttribute("search", new SearchForm());

        return "chat/main";
    }

    // 채팅방 입장
    @GetMapping("/chat/enter/{id}")
    public String chat(@PathVariable Long id, Model model, HttpSession session) {

        // 들어간 채팅방 번호 세션 입력
        session.setAttribute("chatId", id);

        String memberId = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId).get();

        // 채팅방 저장
        chatItemService.save(findMember, id);

        // 메시지들 출력
        List<Message> findMessage = messageService.findAllByChatId(id);
        model.addAttribute("messageList", findMessage);

        // 채팅방 정보 출력
        Chat findChat = chatService.findById(id);
        model.addAttribute("chat", findChat);

        // 메시지 받기 위한 form
        model.addAttribute("form", new MessageForm());

        return "chat/chat";
    }

    // 검색별 모든 채팅 불러오기
    @GetMapping("/chat/find/new/search")
    public String findNewBySearch(SearchForm form, Model model, HttpSession session) {

        String memberId = (String)session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId).get();

        model.addAttribute("search", new SearchForm());

        // 검색 내용별로 불러오기
        List<Chat> chats = chatService.findAllBySearch(form.getSearch(), findMember.getSchool_name());
        model.addAttribute("chatList", chats);
        return "chat/new_chat_search";
    }

    // 검색별 내 채팅 불러오기
    @GetMapping("/chat/find/mine/search")
    public String findMineBySearch(SearchForm form, Model model, HttpSession session) {
        String memberId = (String)session.getAttribute("memberId");

        model.addAttribute("search", new SearchForm());

        // 검색별 내 채팅 불러오기
        List<ChatItem> chats = chatItemService.findAllBySearch(memberId, form.getSearch());
        model.addAttribute("chatList", chats);

        return "chat/main_search";
    }

    // 메세지 입력
    @PostMapping("/chat/message")
    public String message(MessageForm form, HttpSession session) {

        // 회원 불러오기
        String memberId = (String) session.getAttribute("memberId");
        Member findMember = memberService.findById(memberId).get();

        // 채팅방 불러오기
        Long chatId = (Long) session.getAttribute("chatId");
        Chat findChat = chatService.findById(chatId);

        // 메시지 저장
        Message message = new Message(form.getMessage(), LocalDateTime.now(), findMember, findChat);
        messageService.save(message);

        return "redirect:/chat/enter/" + chatId;

    }
}
