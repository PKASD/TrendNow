package com.example.trend_project.controller;

import com.example.trend_project.apis.oauth.dto.UserDto;
import com.example.trend_project.dto.BoardDto;
import com.example.trend_project.dto.CommentDto;
import com.example.trend_project.service.BoardService;
import com.example.trend_project.service.CommentService;
import com.example.trend_project.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class BoardController {

    private final GalleryService galleryService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final HttpSession session;

    // url 앞에 / 붙으면 절대 경로, 안붙으면 상대 경로
    // redirect = url 이동, return = 템플릿 이동

    //특정 게시판 게시글 목록
    @Transactional
    @GetMapping(path = "/{galleryName}")
    public String boardPage(@PathVariable String galleryName, @PageableDefault(page = 0, size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        log.info("gallery in");
        var finds = boardService.findAll(galleryName, pageable);
        var user = session.getAttribute("user");

        model.addAttribute("paging", finds).addAttribute("user", user);

        return "/board/gallery";
    }

    //특정 게시판의 게시글 검색
    @GetMapping("/{galleryName}/search")
    public String searchBoard(@PathVariable String galleryName, @PageableDefault(page = 0, size = 10, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String searchTitle, Model model) {
        log.info("board search in");
        var finds = boardService.findByTitleContaining(galleryName, searchTitle, pageable);
        var user = session.getAttribute("user");

        model.addAttribute("paging", finds).addAttribute("user", user).addAttribute("searchTitle", searchTitle);

        return "/board/gallery";
    }

    //게시글 클릭하면 글 정보
    @Transactional
    @GetMapping(path = "/{galleryName}/{boardNum}")
    public String viewPage(@PathVariable String galleryName, @PathVariable Long boardNum, Model model) {
        log.info("boardNUm ={}", boardNum);
        try {
            var dto = boardService.findByIdx(boardNum);
            var user = (UserDto) session.getAttribute("user");
            var comments = commentService.findByBoard(dto);

            comments.sort(Comparator.comparing(CommentDto::getIdx).reversed());

            boardService.IncViewCount(boardNum);
            log.info("dto = {}", dto);
            log.info("comments = {}", comments);

            model.addAttribute("board", dto)
                    .addAttribute("user", user)
                    .addAttribute("comments", comments);

            return "/board/board";
        } catch (Exception e) {
            return "redirect:/gallery/자유게시판";
        }
    }

    // 글 작성 폼 이동
    @GetMapping("/write")
    public String boardWrite(HttpSession session, Model model) {
        log.info("write from in");
        var user = (UserDto) session.getAttribute("user");

        model.addAttribute("user", user)
                .addAttribute("board", new BoardDto());
        return "/board/write";
    }

    //특정 게시판의 게시글 작성
    // @RequestBody 사용 : json객체 다뤄야할 때,
    // @RequestBody 사용 X : 단순 쿼리 문자열 파라미터, form 데이터
    @PostMapping(path = "/{galleryName}/write")
    public String writePage(@PathVariable String galleryName, @RequestParam(required = false) Long boardNum, @Validated @ModelAttribute("board") BoardDto board, BindingResult bindingResult, Model model, HttpSession session) throws UnsupportedEncodingException {
        log.info("write in");

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> {
                FieldError fieldError = (FieldError) e;
                log.info(fieldError.getField());
                log.info(fieldError.getDefaultMessage());
            });

            return "board/write";
        }

        var user = (UserDto) session.getAttribute("user");

        board.setIdx(boardNum);
        var dto = boardService.save(board, user, galleryName);
        String gal = URLEncoder.encode(dto.getGallery().getName(), "UTF-8");

        model.addAttribute("board", dto);

        return "redirect:/gallery/" + gal + "/" + dto.getIdx();

    }

    //글 수정 폼 이동
    @Transactional
    @GetMapping("/{galleryName}/{boardNum}/update")
    public String boardUpdate(@PathVariable String galleryName, @PathVariable Long boardNum, HttpSession session, Model model) {
        var user = (UserDto) session.getAttribute("user");
        var board = boardService.findByIdx(boardNum);

        if (user.getEmail().equals(board.getUser().getEmail())) {
            model.addAttribute("board", board)
                    .addAttribute("user", user);
            return "/board/update";
        }
        return "redirect:./";
    }

    //게시글 삭제
    @Transactional
    @ResponseBody
    @DeleteMapping("/{galleryName}/{boardNum}/delete")
    public ResponseEntity deleteBoard(@PathVariable String galleryName, @PathVariable Long boardNum, HttpSession session) throws UnsupportedEncodingException {
        log.info("board delete");
        //현재 유저와 게시글 유저 비교
        var user = (UserDto) session.getAttribute("user");
        var find = boardService.findByIdx(boardNum);

        System.out.println(find);
        log.info("user={}", user);

        if (find.getUser().getEmail().equals(user.getEmail())) {
            boardService.deleteById(boardNum);
            return ResponseEntity.ok("글 삭제 성공");
        } else {
            log.info("작성자가 일치하지 않습니다.");
            return ResponseEntity.ok("글 삭제 실패");
        }
    }

    //특정 게시글의 댓글 작성
    @Transactional
    @PostMapping(path = "/{galleryName}/{boardNum}/comment-write")
    public String writeComment(@PathVariable String galleryName, @PathVariable Long boardNum, CommentDto dto, HttpSession session) {
        log.info("comment-write in");
        var user = (UserDto) session.getAttribute("user");
        var comment = commentService.save(dto, user, boardNum);
        log.info("saved comment = {}", comment);

        return "redirect:./";
    }

    //특정 게시판의 댓글 삭제
    @Transactional
    @DeleteMapping("/comment-delete/{comIdx}")
    public ResponseEntity deleteComment(@PathVariable Long comIdx, HttpSession session) throws UnsupportedEncodingException {
        log.info("comment delete");

        //현재 유저와 댓글 유저 비교
        var user = (UserDto) session.getAttribute("user");
        var find = commentService.findByIdx(comIdx);

        System.out.println(find);
        log.info("user={}", user);

        if (find.getUser().getEmail().equals(user.getEmail())) {
            commentService.deleteByIdx(comIdx);
            return ResponseEntity.ok("댓글 삭제 성공");
        } else {
            log.info("작성자가 일치하지 않습니다.");
            return ResponseEntity.ok("댓글 삭제 실패");
        }
    }
}
