package ind.ck.controller.comment;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ind.ck.model.bean.Comment;
import ind.ck.model.vo.CommentVO;
import ind.ck.service.cmt.ICommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired @Qualifier("commentService")
	ICommentService<Comment> commentService;

	/**
	 * 提交评论
	 * @param comment 不使用VO，因为不可通过引用获取list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/post"
			+ "/title/{title}"
			+ "/content/{content}"
			+ "/username/{username}"
			+ "/newsId/{newsId}"
			+ "/quoteId/{quoteId}",
			"/post"
			+ "/title/{title}"
			+ "/content/{content}"
			+ "/username/{username}"
			+ "/newsId/{newsId}"
			},method={RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> postComment(
			CommentVO comment){
		return commentService.commentNews(comment);
		
	}

	/**
	 * 获取所有评论，根据传参
	 * eg:http://localhost:8080/BugService/comment/get/newsId/13/pgNow/3.mk
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/get"
			+ "/username/{username}"
			+ "/newsId/{newsId}"
			+ "/quoteId/{quoteId}"
			+ "/isShow/{isShow}",
			"/get"
			+ "/newsId/{newsId}"
			+ "/pgNow/{pgNow}"
			},method={RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> listPost(CommentVO comment){
		return commentService.listComment(comment);
	}
	
	
	
	public void setCommentService(ICommentService<Comment> commentService) {
		this.commentService = commentService;
	}
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("/get/(.+)");// get/username/{username}    |//newsId//{newsId}
		Matcher matcher = pattern.matcher("/get/ss");//get/username/{username}
		System.out.println(matcher.matches());
		
	}
	
}
