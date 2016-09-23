package com.dev.spring.board.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dev.spring.board.mapper.BoardMapper;
import com.dev.spring.board.model.CommonBoard;
import com.dev.spring.board.model.CommonFile;
import com.dev.spring.board.model.SearchBoard;
import com.dev.spring.common.Global;
import com.dev.spring.common.util.DateUtil;
import com.dev.spring.common.util.FileUtil;

@Service
public class BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired private BoardMapper boardMapper;

	public Integer selectBoardListRow(SearchBoard searchBoard) throws SQLException {
		return boardMapper.selectBoardListRow(searchBoard);
	}

	public List<CommonBoard> selectBoardList(SearchBoard searchBoard) throws SQLException {
		return boardMapper.selectBoardList(searchBoard);
	}

	public CommonBoard selectBoard(int bdSeq) throws SQLException {
		return boardMapper.selectBoard(bdSeq);
	}

	public CommonFile selectDownload(int fileSeq) throws SQLException {
		return boardMapper.selectDownload(fileSeq);
	}

	@Transactional
	public Integer insertBoard(CommonBoard commonBoard) throws SQLException {
		
		int rst = boardMapper.insertBoard(commonBoard);		
		logger.debug("board.getBdSeq() is {}", commonBoard.getBdSeq());
		
		if (commonBoard.getUploads() != null) { //!commonBoard.getUploads().isEmpty() : NullPointException (@InitBinder)
			saveFiles(commonBoard);
		}
		
		return rst;
	}

	@Transactional
	public Integer updateBoard(CommonBoard commonBoard) throws SQLException {
		
		int rst = boardMapper.updateBoard(commonBoard);
		
		if (commonBoard.getUploads() != null) {
			deleteFiles(commonBoard.getBdSeq());
			saveFiles(commonBoard);
		}
		
		return rst;
	}

	@Transactional
	public Integer deleteBoard(int bdSeq) throws SQLException {
		
		//delete child(reply) files (bdSeq <= prSeq) : deleteBoard() -> delete together PR_SEQ.. priority
		List<Integer> replyList = boardMapper.selectBoardReply(bdSeq);
		for(Integer bdSeq_prSeq : replyList){
			deleteFiles(bdSeq_prSeq);
		}
		
		int rst = boardMapper.deleteBoard(bdSeq); //BD_SEQ, PR_SEQ
		
		deleteFiles(bdSeq);

		return rst;
	}

	private void saveFiles(CommonBoard commonBoard) throws SQLException { //@Transactional : Exception -> SQLException
		
		String savePath = Global.resource.getString("FILE_ROOT") + "/BOARD";
		FileUtil fileUtil = new FileUtil();
		
		for(MultipartFile upload : commonBoard.getUploads()){
			if(upload != null){ //!upload.isEmpty()
				String saveFile = DateUtil.getSimpleDate() + "_" + RandomStringUtils.randomAlphabetic(8) + ".tmp";
				
				CommonFile commonFile = new CommonFile();
				commonFile.setPathNm(savePath);
				commonFile.setRealNm(upload.getOriginalFilename());
				commonFile.setSaveNm(saveFile);
				commonFile.setBdSeq(commonBoard.getBdSeq());
				
				boardMapper.insertFile(commonFile);
				
				try {
					fileUtil.writeFile(upload, savePath, saveFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deleteFiles(int bdSeq) throws SQLException{
		
		List<CommonFile> files = boardMapper.selectBoardFiles(bdSeq);
		
		if(files.size() > 0){
			FileUtil fileUtil = new FileUtil();
			
			boardMapper.deleteFile(bdSeq);
			
			for(CommonFile commonFile : files){
				try {
					fileUtil.deleteFile(commonFile.getPathNm(), commonFile.getSaveNm());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
