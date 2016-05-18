package ind.ck.model.vo;

import ind.ck.model.bean.Comment;

public class CommentVO extends Comment{
	
	//评论的引用其他评论的id
	private String quoteId;
	//评论的引用其他评论的内容
	private String quoteContent;
	
	//物理页分页，从1开始
	private int pgNow = 1;
	//sql分页参数，计算方法见 
	//@getSqlPgNow()
	private int sqlPgNow = 0;
	//每页条数
	private int itemPerPage = 5;

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getQuoteContent() {
		return quoteContent;
	}

	public void setQuoteContent(String quoteContent) {
		this.quoteContent = quoteContent;
	}

	public int getPgNow() {
		return pgNow;
	}

	public void setPgNow(int pgNow) {
		this.pgNow = pgNow;
	}

	public int getItemPerPage() {
		return itemPerPage;
	}

	public void setItemPerPage(int itemPerPage) {
		this.itemPerPage = itemPerPage;
	}

	public int getSqlPgNow() {
		if (this.pgNow < 1){
			this.pgNow = 1;
		}
		return (this.pgNow-1)*this.itemPerPage;
	}

	public void setSqlPgNow(int sqlPgNow) {
		//this.sqlPgNow = sqlPgNow;
		this.sqlPgNow = 0;
		try {
			throw new Exception("SQL 分页仅可进行获取，不可复制");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
