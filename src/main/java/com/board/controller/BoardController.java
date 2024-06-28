package com.board.controller;

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

	@GetMapping("/board/write")
	//@RequestParam -> 뷰(게시글 리스트)에서 여기서 보낼 때 아이디(required? 는 뭐지) 보냄 -> 게시글 유무 확인하기위해
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		//고유id 없으면 dto 그대로 내보내기
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
			//고유id 있으면 작성된거 가져오기
			BoardDTO board = boardService.getBoardDetail(idx);
			//가져온게 null이면 리스트 보기로 이동하기 NPE 방지!
			if (board == null) {
				//만들어야 하는 리스트 페이지
				return "redirect:/board/list.do";
			}
			//board 보내기
			model.addAttribute("board", board);
		}
		
		System.err.println("처리완료!!");
		
		//조회, 수정 페이지로 이동 
		return "board/write";
		
	}
	
	
	@PostMapping("/board/register")
//	final을 붙여서 컴파일러가 dto 자동생성 안해도 어떻게든 생성하라고 하는건가
	public String registerBoard(final BoardDTO params) {
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
	
	
	
	@GetMapping("/board/list")
	public String listTest() {
		return "board/list";
	}
}


	


