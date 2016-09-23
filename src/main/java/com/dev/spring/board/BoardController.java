package com.dev.spring.board;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.spring.board.model.CommonBoard;
import com.dev.spring.board.model.CommonFile;
import com.dev.spring.board.model.SearchBoard;
import com.dev.spring.board.service.BoardService;
import com.dev.spring.common.ControllerExt;

@Controller
@RequestMapping("board")
public class BoardController extends ControllerExt {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired private BoardService boardService;
	
	@RequestMapping(value = "/{bdGrp}", method = {RequestMethod.GET, RequestMethod.POST}) //list, search
	public String boardList(@PathVariable String bdGrp, @ModelAttribute SearchBoard searchBoard, Model model) {
		
		if(searchBoard == null) searchBoard = new SearchBoard();
		
		//searchBoard.setBdGrp(bdGrp); //자동할당(title, content 등 등록폼 element도 자동할당 -> 변수명 변경 : sbTitle 등..)
		
		//paging start
		final int pageCount = 10;
		final int rowCount = 10;
		int nowPage = (searchBoard.getPage() == null) ? 1 : searchBoard.getPage().getNowPage();
		int rowMax = 0;
		
		try {
			rowMax = boardService.selectBoardListRow(searchBoard);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		initPage(pageCount, rowCount, nowPage, rowMax);
		
		searchBoard.setPage(page);
		//paging end
		
		List<CommonBoard> resultList = null;
		
		try {
			resultList = boardService.selectBoardList(searchBoard);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//model.addAttribute("bdGrp", bdGrp); //자동할당
		model.addAttribute("searchBoard", searchBoard);
		model.addAttribute("resultList", resultList);
		
		return "board/board_list";
	}
	
	@RequestMapping(value = "/{bdGrp}/{bdSeq}", method = {RequestMethod.GET, RequestMethod.POST}) //view
	public String boardView(@PathVariable String bdGrp, @PathVariable int bdSeq, @ModelAttribute SearchBoard searchBoard, Model model) {
		
		CommonBoard resultView = new CommonBoard();
		
		try {
			resultView = boardService.selectBoard(bdSeq);
			
			for(CommonFile commonFile : resultView.getBdFiles()){
				logger.debug("Files: {} {}", commonFile.getFileSeq(), commonFile.getRealNm());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("searchBoard", searchBoard);
		model.addAttribute("resultView", resultView);
		
		return "board/board_view";
	}
	
	@RequestMapping(value = "/{bdGrp}/input", method = {RequestMethod.GET, RequestMethod.POST}) //input(reply), modify form
	public String boardInput(@PathVariable String bdGrp, @ModelAttribute SearchBoard searchBoard, @ModelAttribute CommonBoard commonBoard, Model model) {
		
		CommonBoard resultView = new CommonBoard();
		String cmd = commonBoard.getCmd();
		
		if("modify".equals(cmd)){
			try {
				resultView = boardService.selectBoard(commonBoard.getBdSeq());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("reply".equals(cmd)){
			resultView.setBdSeq(0);
			resultView.setPrSeq(commonBoard.getBdSeq());
			resultView.setTitle("[RE]"+commonBoard.getTitle());
		}
		
		model.addAttribute("searchBoard", searchBoard);
		model.addAttribute("resultView", resultView);
		
		return "board/board_input";
	}
	
	//method="PUT" : "multipart/form-data" 처리못함 (무조건 post로 처리됨) -> commonBoard cmd 구분
	@RequestMapping(value = "/{bdGrp}/submit", method = RequestMethod.POST) //insert, update, delete
	public String boardSubmit(@PathVariable String bdGrp, @ModelAttribute CommonBoard commonBoard) {
		
		String cmd = commonBoard.getCmd();
		logger.debug("commonBoard cmd: {}, seq: {}", cmd, commonBoard.getBdSeq());
		
		try {
			if("insert".equals(cmd) || "reply".equals(cmd)){
				boardService.insertBoard(commonBoard);
			}else if("update".equals(cmd)){
				boardService.updateBoard(commonBoard);
			}else if("delete".equals(cmd)){
				boardService.deleteBoard(commonBoard.getBdSeq());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "redirect:/board/"+bdGrp;
	}
	
	//submit ajax 처리
	@RequestMapping(value = "/{bdGrp}/ajaxSubmit", method = RequestMethod.POST) //insert(reply), update, delete
	@ResponseBody
	public int boardAjaxSubmit(@ModelAttribute CommonBoard commonBoard) {
		
		int rst = 0;
		String cmd = commonBoard.getCmd();
		logger.debug("commonBoard cmd: {}, seq: {}", cmd, commonBoard.getBdSeq());
		
		try {
			if("insert".equals(cmd) || "reply".equals(cmd)){
				rst = boardService.insertBoard(commonBoard);
			}else if("update".equals(cmd)){
				rst = boardService.updateBoard(commonBoard);
			}else if("delete".equals(cmd)){
				rst = boardService.deleteBoard(commonBoard.getBdSeq());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rst;
	}
	
/*
	@RequestMapping(value = "/{bdGrp}/submit", method = RequestMethod.POST) //insert
	public String boardInsert(@PathVariable String bdGrp, @ModelAttribute CommonBoard commonBoard) {
		
		try {
			boardService.insertBoard(commonBoard);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "redirect:/board/"+bdGrp;
	}
	
	@RequestMapping(value = "/{bdGrp}/submit", method = RequestMethod.PUT) //update
	public String boardUpdate(@PathVariable String bdGrp, @ModelAttribute CommonBoard commonBoard) {
		
		try {
			logger.debug("commonBoard: {}, {}", commonBoard.getBdGrp(), commonBoard.getBdSeq());

			boardService.updateBoard(commonBoard);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "redirect:/board/"+bdGrp;
	}
	
	@RequestMapping(value = "/{bdGrp}/submit", method = RequestMethod.DELETE) //delete
	public String boardDelete(@PathVariable String bdGrp, @RequestParam int bdSeq) {
		
		try {
			boardService.deleteBoard(bdSeq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "redirect:/board/"+bdGrp;
	}
*/
	
	//"commonBoard.getUploads().isEmpty()"가 항상 "false" (& .isEmpty() -> != null 변경(??).. & input name 배열로 명시)
	@InitBinder
	public void init(WebDataBinder binder) {
	    binder.setBindEmptyMultipartFiles(false);
	}
}
