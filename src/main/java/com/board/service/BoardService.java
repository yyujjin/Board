package com.board.service;

import java.util.List;

import com.board.domain.BoardDTO;

public interface BoardService {

	//게시글 생성, 수정
	public boolean registerBoard(BoardDTO params);
	//게시글 하나 조회
	public BoardDTO getBoardDetail(Long idx);
	//게시글 삭제
 	public boolean deleteBoard(Long idx);
 	//삭제 되지 않은 전체 게시글 조회
	public List<BoardDTO> getBoardList();

}
