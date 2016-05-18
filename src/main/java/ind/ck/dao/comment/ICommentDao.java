package ind.ck.dao.comment;

import java.util.List;
import java.util.Map;

import ind.ck.model.bean.Comment;
import ind.ck.model.bean.News;
import ind.ck.model.vo.CommentVO;

public interface ICommentDao<C extends Comment> {

	/**
	 * 存储过程返回错误句柄:error_handler  0正常 1已回滚
	 * @param comment
	 * @return
	 */
	Map<String,Object> commentNews(C comment);
	
	//void updateComment(commentId,);
	List<CommentVO> ListCommentBy(C comment);
	
}
