package cn.ybz21.hibotvoice.bean;

import java.io.Serializable;

public class ResultCode implements Serializable{
	public int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public ResultCode() {
		//通过super来调用父类的构造器
		super();
	}

	public ResultCode(int result) {
		super();
		this.result = result;
	}

}
