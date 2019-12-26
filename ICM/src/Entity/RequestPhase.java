package Entity;

import java.util.Date;

public class RequestPhase {
private Date startDate;
private Date dueDate;
private Request r;
private Phase phase;
public RequestPhase(Date startDate, Date dueDate,Request r,Phase phase) {
	this.phase=phase;
	this.r=r;
	this.startDate = startDate;
	this.dueDate = dueDate;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getDueDate() {
	return dueDate;
}
public void setDueDate(Date dueDate) {
	this.dueDate = dueDate;
}
public Request getR() {
	return r;
}
public void setR(Request r) {
	this.r = r;
}
public Phase getPhase() {
	return phase;
}
public void setPhase(Phase phase) {
	this.phase = phase;
}
public boolean Extension(Date newDueDate)
{
	if(newDueDate.compareTo(dueDate)<0) {
		System.out.println("cann't Extend the due date ");
		return false;
	}
	setDueDate(newDueDate);
	return true;
}
}
