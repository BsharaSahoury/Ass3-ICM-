package Boundary;

import java.awt.Button;


import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import Client.ClientAllRequestsObserver;
import Client.ClientApproveDuratinObserver;
import Client.ClientApprovePerformanceObserver;

import Client.ClientChairmanApproveObserver;
import Client.ClientCheckApproveDuratinObserver;
import Client.ClientConsole;
import Client.ClientDecisionCommitteMemberObserver;
import Client.ClientDocumentExceptionObserver;
import Client.ClientEvaluationReportObserver;
import Client.ClientGetChairmanObserver;
import Client.ClientGetDurationObserver;
import Client.ClientGetEvaluatorsObserver;
import Client.ClientInitiatorapprovedrequestdecisionObserver;
import Client.ClientLoginObserver;
import Client.ClientMapObserver;
import Client.ClientMessageSentToInitiatorObserver;
import Client.ClientMyRequestsObserver;
import Client.ClientNotificationObserver;
import Client.ClientNotificationdetailsObserver;
import Client.ClientRecruitEvaluatorObserver;
import Client.ClientRejectRequestMessageSendToInitiatorObserver;
import Client.ClientRequestInfoObserver;
import Client.ClientRequestTrack;
import Client.ClientRequestsWorkedOnObserver;
import Client.ClientSetDuratinObserver;
import Client.ClientSubmissionObserver;
import Client.MainForClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;

public class MainClientController {
	public static LoginController login;
	@FXML
	TextField ip;
	@FXML
	Label wrong;
	public void connect() {
		String host=ip.getText();
		System.out.println(Thread.currentThread().toString());
		try {
			ClientConsole cc=new ClientConsole(host);
			ClientLoginObserver clo=new ClientLoginObserver(cc.getClient());
			ClientAllRequestsObserver allReqObserver=new ClientAllRequestsObserver(cc.getClient());
			ClientSubmissionObserver cso=new ClientSubmissionObserver(cc.getClient());
			ClientNotificationObserver cno=new ClientNotificationObserver(cc.getClient());
			ClientMyRequestsObserver ceo=new ClientMyRequestsObserver(cc.getClient());
			ClientGetChairmanObserver chairman=new ClientGetChairmanObserver(cc.getClient());
			ClientRecruitEvaluatorObserver creo=new ClientRecruitEvaluatorObserver(cc.getClient());
			ClientGetEvaluatorsObserver cgeo=new ClientGetEvaluatorsObserver(cc.getClient());
			ClientRequestInfoObserver cri= new ClientRequestInfoObserver(cc.getClient());
			ClientRequestsWorkedOnObserver ss=new ClientRequestsWorkedOnObserver(cc.getClient());
			ClientRequestTrack rt=new ClientRequestTrack(cc.getClient());
			ClientDecisionCommitteMemberObserver qq=new ClientDecisionCommitteMemberObserver(cc.getClient());
			ClientDecisionCommitteMemberObserver xx=new ClientDecisionCommitteMemberObserver(cc.getClient());
			ClientChairmanApproveObserver m=new ClientChairmanApproveObserver(cc.getClient());
			ClientNotificationdetailsObserver n=new ClientNotificationdetailsObserver(cc.getClient());
			ClientSetDuratinObserver cd=new ClientSetDuratinObserver(cc.getClient());
			ClientApprovePerformanceObserver capo=new ClientApprovePerformanceObserver(cc.getClient());
			ClientEvaluationReportObserver sendreport=new ClientEvaluationReportObserver(cc.getClient());
			ClientGetDurationObserver getDuration=new ClientGetDurationObserver(cc.getClient());
			ClientMessageSentToInitiatorObserver ee=new ClientMessageSentToInitiatorObserver(cc.getClient());
			ClientDocumentExceptionObserver cdeo=new ClientDocumentExceptionObserver(cc.getClient());
			ClientMapObserver cmo=new ClientMapObserver(cc.getClient());
			ClientInitiatorapprovedrequestdecisionObserver qqw=new ClientInitiatorapprovedrequestdecisionObserver(cc.getClient());
			ClientRejectRequestMessageSendToInitiatorObserver reject=new ClientRejectRequestMessageSendToInitiatorObserver(cc.getClient());		
			//ClientApproveDuratinObserver approveDuratin=new ClientApproveDuratinObserver(cc.getClient());
			//ClientCheckApproveDuratinObserver check= new ClientCheckApproveDuratinObserver(cc.getClient());
			cc.getClient().openConnection();
			wrong.setVisible(false);
			login=new LoginController();
			login.start(MainForClient.stage,cc);
		}catch(Exception e) {
			wrong.setVisible(true);
			ip.clear();
			System.out.println("connection failed");
		}		
	}
}

