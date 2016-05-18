package ind.ck.service.cmt;

import java.util.Map;

import ind.ck.model.bean.Comment;



public interface ICommentService<C extends Comment> {
	
	Map<String,Object> commentNews(C comment);
	
	Map<String,Object> listComment(C comment);//List<CommentVO>
}
