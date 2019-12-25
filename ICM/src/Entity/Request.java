package Entity;

import java.io.Serializable;

public class Request implements Serializable{
	/**
	 * 
	 */
	public int y;//ayman Odeh 1
	public int x;
	private static final long serialVersionUID = 1L;
	private String id;  //The system number for treatment
	private String nameInitiator;
	private String status;
	private String nameWorker;
	private String currentSituation;
	private String explainRequest;
	

	
	public Request(String nameInitiator,String id,String currentSituation,String explainRequest, String status, String nameWorker) {
		this.id = id;
		this.nameInitiator = nameInitiator;
		this.status = status;
		this.nameWorker = nameWorker;
		this.currentSituation = currentSituation;
		this.explainRequest = explainRequest;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameInitiator() {
		return nameInitiator;
	}
	public void setNameInitiator(String nameInitiator) {
		this.nameInitiator = nameInitiator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNameWorker() {
		return nameWorker;
	}
	public void setNameWorker(String nameWorker) {
		this.nameWorker = nameWorker;
	}
	public String getCurrentSituation() {
		return currentSituation;
	}
	public void setCurrentSituation(String currentSituation) {
		this.currentSituation = currentSituation;
	}
	public String getExplainRequest() {
		return explainRequest;
	}
	public void setExplainRequest(String explainRequest) {
		this.explainRequest = explainRequest;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", nameInitiator=" + nameInitiator + ", status=" + status + ", nameWorker="
				+ nameWorker + ", currentSituation=" + currentSituation + ", explainRequest=" + explainRequest + "]";
	}
}

