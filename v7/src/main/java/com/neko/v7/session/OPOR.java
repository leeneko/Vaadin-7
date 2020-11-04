package com.neko.v7.session;

import java.util.Date;

public class OPOR {
	private int DocEntry;
	private String DocType; // I = Item, S = Service
	private String Status; // Open 생성됨, Canceled 취소됨, Closed 완료됨
	private Date DocDate; // 생성일
	private Date DocDueDate; // 종료일
	private String Creator; // 생성자
	private String Description;
	
	public OPOR() {
		this.DocDate = new Date();
		this.DocDueDate = new Date();
	}
	public OPOR(String creator) {
		this.Creator = creator;
		this.DocDate = new Date();
		this.DocDueDate = new Date();
	}
	public OPOR(int docEntry, String docType, String status, Date docDate, Date docDueDate, String creator,
			String description) {
		super();
		DocEntry = docEntry;
		DocType = docType;
		Status = status;
		DocDate = docDate;
		DocDueDate = docDueDate;
		Creator = creator;
		Description = description;
	}
	public int getDocEntry() {
		return DocEntry;
	}
	public void setDocEntry(int docEntry) {
		DocEntry = docEntry;
	}
	public String getDocType() {
		return DocType;
	}
	public void setDocType(String docType) {
		DocType = docType;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Date getDocDate() {
		return DocDate;
	}
	public void setDocDate(Date docDate) {
		DocDate = docDate;
	}
	public Date getDocDueDate() {
		return DocDueDate;
	}
	public void setDocDueDate(Date docDueDate) {
		DocDueDate = docDueDate;
	}
	public String getCreator() {
		return Creator;
	}
	public void setCreator(String creator) {
		Creator = creator;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	@Override
	public String toString() {
		return "OPOR [DocEntry=" + DocEntry + ", DocType=" + DocType + ", Status=" + Status + ", DocDate=" + DocDate
				+ ", DocDueDate=" + DocDueDate + ", Creator=" + Creator + ", Description=" + Description + "]";
	}
}
