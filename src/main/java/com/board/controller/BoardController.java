package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.mysql.cj.log.Log;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	
	//게시글 작성 
	@GetMapping("/board/write")
	public String openBoardWrite(Model model) {
			model.addAttribute("board", new BoardDTO());
		return "board/write";
	}
	
	
	//게시글 등록 
	@PostMapping("/board/register")
//	final을 붙여서 컴파일러가 dto 자동생성 안해도 어떻게든 생성하라고 하는건가
	public String registerBoard(final BoardDTO params) {
		
		System.err.println("가져온 DTO : "+params.toString());
		if(params.getNoticeYn()!=null && params.getNoticeYn()!="N") {
			params.setNoticeYn("Y");
		}
		
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달
			
		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}
		
		return "redirect:/board/list";
	}
	
	//게시글 상세 내용 조회
	@GetMapping("/board/view")
	public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list";
		}

		BoardDTO board = boardService.getBoardDetail(idx);
		if (board == null || "Y".equals(board.getDeleteYn())||"Y".equals(board.getSecretYn())) {
			// TODO => 없는 게시글이거나, 이미 삭제된 게시글,비밀 글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list";
		}
		model.addAttribute("board", board);

		return "board/write";
	}
	
	
	
	//게시글 리스트 보기
	@GetMapping("/board/list")
	public String openBoardList(Model model) {
		List<BoardDTO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);

		return "board/list";
	}
	
	
	
}


	


