package Entity;

import java.io.Serializable;

import java.util.Date;
/**
 * 
 * @author aimanouda
 *
 */
public class Request implements Serializable{

private static final long serialVersionUID = 1L;
private String id;
private String initiatorName;
private String initiatorEmail;
private String initiatorRole;
private String status;
private String existingSituation;
private String explainRequest;  
private String privilegedInfoSys; 
private String reason;
private String comment;
private Date date;

/**
 * 
 * @param id
 * @param initiatorName
 * @param initiatorEmail
 * @param initiatorRole
 * @param status
 * @param existingSituation
 * @param explainRequest
 * @param privilegedInfoSys
 * @param reason
 * @param comment
 * @param date
 * @param currentPhase
 */

private Phase currentPhase;

public Request(String id, String initiatorName, String status,String privilegedInfoSys,Date date) {
	this.id = id;
	this.initiatorName = initiatorName;
	this.status = status;
	this.privilegedInfoSys = privilegedInfoSys;
	this.date = date;
}
public Request(String id, String initiatorName, String initiatorEmail, String initiatorRole, String status,
String existingSituation, String explainRequest, String privilegedInfoSys, String reason, String comment,
Date date, Phase currentPhase) {
	this.id = id;
	this.initiatorName = initiatorName;
	this.initiatorEmail = initiatorEmail;
	this.initiatorRole = initiatorRole;
	this.status = status;
	this.existingSituation = existingSituation;
	this.explainRequest = explainRequest;
	this.privilegedInfoSys = privilegedInfoSys;
	this.reason = reason;
	this.comment = comment;
	this.date = date;
	this.currentPhase = currentPhase;
}

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getInitiatorName() {
	return initiatorName;
}
public void setInitiatorName(String initiatorName) {
	this.initiatorName = initiatorName;
}
public String getInitiatorEmail() {
	return initiatorEmail;
}
public void setInitiatorEmail(String initiatorEmail) {
	this.initiatorEmail = initiatorEmail;
}
public String getInitiatorRole() {
	return initiatorRole;
}
public void setInitiatorRole(String initiatorRole) {
	this.initiatorRole = initiatorRole;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getExistingSituation() {
	return existingSituation;
}
public void setExistingSituation(String existingSituation) {
	this.existingSituation = existingSituation;
}
public String getExplainRequest() {
	return explainRequest;
}
public void setExplainRequest(String explainRequest) {
	this.explainRequest = explainRequest;
}
public String getPrivilegedInfoSys() {
	return privilegedInfoSys;
}
public void setPrivilegedInfoSys(String privilegedInfoSys) {
	this.privilegedInfoSys = privilegedInfoSys;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Phase getCurrentPhase() {
	return currentPhase;
}
public void setCurrentPhase(Phase currentPhase) {
	this.currentPhase = currentPhase;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
   
}