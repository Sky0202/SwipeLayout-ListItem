package com.sky.swipelayoutdemo;

/**
 * Created by ccy on 2016/9/10.
 */
public class Msg {
	private String name;
	private String content;
	private int unReadMsgCount;

	public Msg(String name, String content, int unReadMsgCount) {
		this.name = name;
		this.content = content;
		this.unReadMsgCount = unReadMsgCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUnReadMsgCount() {
		return unReadMsgCount;
	}

	public void setUnReadMsgCount(int unReadMsgCount) {
		this.unReadMsgCount = unReadMsgCount;
	}

	@Override
	public String toString() {
		return "Msg{" +
				"name='" + name + '\'' +
				", content='" + content + '\'' +
				", unReadMsgCount=" + unReadMsgCount +
				'}';
	}
}
