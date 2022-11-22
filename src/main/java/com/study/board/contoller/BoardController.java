package com.study.board.contoller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model){
        boardService.boardWrite(board);
        model.addAttribute("writemessage", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");
        return "writemessage";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id){

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
       public String boardDelete(Integer id, Model model){
        boardService.boardDelete(id);
        model.addAttribute("deletemessage", "글 삭제가 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");
        // return "redirect:/board/list";
        return "deletemessage";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardupdate(@PathVariable("id") Integer id, Board board, Model model){
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        model.addAttribute("modifymessage", "수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        boardService.boardWrite(boardTemp);

        return "modifymessage";
    }
}