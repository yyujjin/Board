package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@SpringBootTest
public class ServiceTests {

	
	@Autowired
	private BoardService boardService;
	
	
	@Test
	public void registerBoard() {
		BoardDTO params = new BoardDTO();
		//idx 52를 바꿀거야. 타입이 Long 래퍼클래스라서 int-> Long 변환해야함
		Long xLong = new Long((int)52);
		params.setIdx(xLong);
		params.setTitle("피곤하다.");
		params.setContent("삶은 힘들다");
		params.setWriter("박유진");
		boolean register = boardService.registerBoard(params);
		System.out.println(register);
	}

	//게시글 조회 테스트 
	@Test
	public void testOfgetBoardDetail() {
		BoardDTO board = boardService.getBoardDetail((long) 2);
		System.out.println("=========================");
		System.out.println(board.getContent());
		System.out.println("=========================");
	}
	
	//게시글 리스트 가져오기
	@Test
	public void testOfgetBoardList() {
		List<BoardDTO> boardList = boardService.getBoardList();
		//비어있냐 검사
		//비어있지 않으면 내용 출력해라, 리스트 수만큼 
		if(CollectionUtils.isEmpty(boardList) == false) {
			for (BoardDTO board : boardList) {
				System.out.println("=========================");
				System.out.println(board.getTitle());
				System.out.println(board.getContent());
				System.out.println(board.getWriter());
				System.out.println("=========================");
			}
		}
	}
}

