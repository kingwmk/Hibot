package cn.ybz21.hibotvoice.bean;

import java.io.Serializable;

public class ResultCode implements Serializable {
	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public ResultCode() {
		super();
	}

	public ResultCode(int result) {
		super();
		this.result = result;
	}

}
