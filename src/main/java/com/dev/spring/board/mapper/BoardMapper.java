package com.dev.spring.board.mapper;

import java.sql.SQLException;
import java.util.List;

import com.dev.spring.board.model.CommonBoard;
import com.dev.spring.board.model.CommonFile;
import com.dev.spring.board.model.SearchBoard;

public interface BoardMapper {

	public abstract Integer selectBoardListRow(SearchBoard searchBoard) throws SQLException;

	public abstract List<CommonBoard> selectBoardList(SearchBoard searchBoard) throws SQLException;

	public abstract CommonBoard selectBoard(int bdSeq) throws SQLException;

	public abstract List<CommonFile> selectBoardFiles(int bdSeq) throws SQLException;

	public abstract CommonFile selectDownload(int fileSeq) throws SQLException;
	
	public abstract Integer insertBoard(CommonBoard commonBoard) throws SQLException;
	
	public abstract void insertFile(CommonFile commonFile) throws SQLException;

	public abstract Integer updateBoard(CommonBoard commonBoard) throws SQLException;
	
	public abstract Integer deleteBoard(int bdSeq) throws SQLException;
	
	public abstract void deleteFile(int bdSeq) throws SQLException;
	
	public abstract List<Integer> selectBoardReply(int bdSeq) throws SQLException;

}