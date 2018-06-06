package com.searchTool.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FamilarDto implements Serializable {

	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}// end of getKeyWord

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}// end of setKeyWord
}//end of class
