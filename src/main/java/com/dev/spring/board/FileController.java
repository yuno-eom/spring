package com.dev.spring.board;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.spring.board.model.CommonFile;
import com.dev.spring.board.service.BoardService;

@Controller
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired private BoardService boardService;
	
	@RequestMapping(value = "/download/{fileSeq}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] fileDownoad(HttpServletResponse response, @PathVariable int fileSeq) throws IOException, SQLException {
		
		CommonFile commonFile = boardService.selectDownload(fileSeq);
		
		File file = new File(commonFile.getPathNm() + "/" + commonFile.getSaveNm());
		logger.debug("real name={}, saved name={}", commonFile.getRealNm(), commonFile.getSaveNm());
		
		if(!(file.exists() && file.isFile())){
			logger.info("file is not exists");
			return null;
		}
		
		byte[] bytes = FileCopyUtils.copyToByteArray(file);

		response.setHeader("Content-Disposition", "attachment;filename="+ new String(commonFile.getRealNm().getBytes("euc-kr"), "8859_1")); //8859_6
	    response.setContentLength(bytes.length);

	    return bytes;
	}
}