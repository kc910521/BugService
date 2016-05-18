package ind.ck.model.vo;

import ind.ck.model.bean.Comment;

public class CommentVO extends Comment{
	
	//���۵������������۵�id
	private String quoteId;
	//���۵������������۵�����
	private String quoteContent;
	
	//����ҳ��ҳ����1��ʼ
	private int pgNow = 1;
	//sql��ҳ���������㷽���� 
	//@getSqlPgNow()
	private int sqlPgNow = 0;
	//ÿҳ����
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
			throw new Exception("SQL ��ҳ���ɽ��л�ȡ�����ɸ���");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
