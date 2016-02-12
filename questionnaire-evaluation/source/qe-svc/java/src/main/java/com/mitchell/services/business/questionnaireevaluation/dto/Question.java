package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  long ancstrQustnnrQustnId;
	private  long ancstrAnswerItemId;
	private  int levelNumber;
	private  long qustnnreQustnId;
	private  int siblingOrder;
	private  long  qustnId;
	private  String  sysQustnType;
	private  String  qustnText;
	private  String answrControlType;
	private  String evltnCategoryDesc;
	private  String isLeaf;	
	private String qustnFormattedText;
	List<Answer> answerList;
	
	public String getQustnFormattedText() {
		return qustnFormattedText;
	}

	public void setQustnFormattedText(String qustnFormattedText) {
		this.qustnFormattedText = qustnFormattedText;
	}
	
	public long getAncstrQustnnrQustnId() {
		return ancstrQustnnrQustnId;
	}

	public void setAncstrQustnnrQustnId(long ancstrQustnnrQustnId) {
		this.ancstrQustnnrQustnId = ancstrQustnnrQustnId;
	}

	public long getAncstrAnswerItemId() {
		return ancstrAnswerItemId;
	}

	public void setAncstrAnswerItemId(long ancstrAnswerItemId) {
		this.ancstrAnswerItemId = ancstrAnswerItemId;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public long getQustnnreQustnId() {
		return qustnnreQustnId;
	}

	public void setQustnnreQustnId(long qustnnreQustnId) {
		this.qustnnreQustnId = qustnnreQustnId;
	}

	public int getSiblingOrder() {
		return siblingOrder;
	}

	public void setSiblingOrder(int siblingOrder) {
		this.siblingOrder = siblingOrder;
	}

	public long getQustnId() {
		return qustnId;
	}

	public void setQustnId(long qustnId) {
		this.qustnId = qustnId;
	}

	public String getSysQustnType() {
		return sysQustnType;
	}

	public void setSysQustnType(String sysQustnType) {
		this.sysQustnType = sysQustnType;
	}

	public String getQustnText() {
		return qustnText;
	}

	public void setQustnText(String qustnText) {
		this.qustnText = qustnText;
	}

	public String getAnswrControlType() {
		return answrControlType;
	}

	public void setAnswrControlType(String answrControlType) {
		this.answrControlType = answrControlType;
	}

	public String getEvltnCategoryDesc() {
		return evltnCategoryDesc;
	}

	public void setEvltnCategoryDesc(String evltnCategoryDesc) {
		this.evltnCategoryDesc = evltnCategoryDesc;
	}

	
	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
	
	
}
