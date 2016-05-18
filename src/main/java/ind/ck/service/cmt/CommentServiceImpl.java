package ind.ck.service.cmt;

import java.util.List;
import java.util.Map;

import ind.ck.common.util.PackageUtils;
import ind.ck.dao.comment.ICommentDao;
import ind.ck.model.bean.Comment;
import ind.ck.model.vo.CommentVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 评论的service
 * @author KCSTATION
 *
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService<Comment>{

	@Autowired 
	ICommentDao<Comment> commentDao;
	
	@Override
	public Map<String,Object> commentNews(Comment comment) {
		// TODO Auto-generated method stub
		Map<String,Object> errorHandler = commentDao.commentNews(comment);
		if (errorHandler != null && errorHandler.get("error_handler") != null){
			return PackageUtils.packageData("0".equals(errorHandler.get("error_handler")+"")?1:0,
					comment,
					"0".equals(errorHandler.get("error_handler")+"")?"评论成功！":"评论失败"
					);
		}
		return PackageUtils.packageData(0,comment,"提交失败");
		
	}


	@Override
	public Map<String,Object> listComment(Comment comment) {
		// TODO Auto-generated method stub
		//Map<String,Object> tMap = //PackageUtils.packageData(, originalData, message)
		return  PackageUtils.packageData(commentDao.ListCommentBy(comment),null);
	}

	
	
	public void setCommentDao(ICommentDao<Comment> commentDao) {
		this.commentDao = commentDao;
	}




	
}
