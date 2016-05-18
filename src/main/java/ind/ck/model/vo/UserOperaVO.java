package ind.ck.model.vo;

import ind.ck.model.bean.CommonUser;

public class UserOperaVO extends CommonUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5797055540641790018L;
	//手机验证码
	private String vertifyCode;

	private boolean checkedOp;
	
	

	public boolean isCheckedOp() {
		return checkedOp;
	}

	public void setCheckedOp(boolean checkedOp) {
		this.checkedOp = checkedOp;
	}

	public String getVertifyCode() {
		return vertifyCode;
	}

	public void setVertifyCode(String vertifyCode) {
		this.vertifyCode = vertifyCode;
	}
	
	
	
}
