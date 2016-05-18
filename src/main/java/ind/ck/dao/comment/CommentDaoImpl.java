package ind.ck.dao.comment;

import ind.ck.model.bean.Comment;
import ind.ck.model.bean.News;
import ind.ck.model.vo.CommentVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("commentDao")
public class CommentDaoImpl implements ICommentDao<Comment>{

	@Autowired
	private SqlSession sqlSessionTemplate;
	
	@Override
	public Map<String,Object> commentNews(Comment comment) {
		// TODO Auto-generated method
		return sqlSessionTemplate.selectOne("touch.comment.insertComment",comment);
	}

	@Override
	public List<CommentVO> ListCommentBy(Comment comment) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("touch.comment.listComment", comment);
	}
	
	
	public void setSqlSessionTemplate(SqlSession sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
