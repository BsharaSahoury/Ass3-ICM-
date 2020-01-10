package Server;

import java.io.IOException;
import java.sql.Connection;

import DBconnection.mysqlConnection;
import ocsf.server.ObservableServer;

public class MainForServer {
	public static Connection con;


	  public static void main(String[] args) 
	  {
        con=mysqlConnection.makeAndReturnConnection();

	    int port = 0; //Port to listen ons
	    try
	    {    
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = 5555; //Set port to 5555
	    }		
	    ObservableServer sv = new ObservableServer(port);
	    ServerObserver so=new ServerObserver(sv);
	    loginHandler loginHandler=new loginHandler(sv);
	    serverSubmissionObserver sso=new serverSubmissionObserver(sv);
	    ServerAllRequestsObserver serverallrequestobserver=new ServerAllRequestsObserver(sv);
	    ServerNotificationsObserver sno=new ServerNotificationsObserver(sv);
        ServerMyRequestsObserver myrequest=new ServerMyRequestsObserver(sv);
        ServerApproveDecsionCommitteeobserver approvedecision=new ServerApproveDecsionCommitteeobserver(sv);
        ServerAutomaticRecruitObserver saro=new ServerAutomaticRecruitObserver(sv);
        ServerGetEvaluatorsObserver sgeo=new ServerGetEvaluatorsObserver(sv);
        ServerManualRecruitObserver smro=new ServerManualRecruitObserver(sv);
        ServerGetChairManObserver chair=new ServerGetChairManObserver(sv);
        ServerRequestInfoObserver requestInfo =new ServerRequestInfoObserver(sv);
        RequestsWorkedOnObserver RequestWorkOn=new RequestsWorkedOnObserver(sv);
        ServerRequestTrackObserver requestTrack= new ServerRequestTrackObserver(sv);
        ServerSetDuratinObserver duration=new ServerSetDuratinObserver(sv);
        ServerCommitteeDecisionObserver CommitteeDecision=new ServerCommitteeDecisionObserver(sv);
        ServerChoosedPerformerObserver scpo= new ServerChoosedPerformerObserver(sv); 
        ServerApprovePerformanceObserver sapo=new ServerApprovePerformanceObserver(sv);
        ServerTestFailedObserver stfo=new ServerTestFailedObserver(sv);
        ServerTestSuccessObserver stso=new ServerTestSuccessObserver(sv);
        ServerNotificationdetailsObserver details=new ServerNotificationdetailsObserver(sv);
        ServerCreateEvaluationReportObserver report=new ServerCreateEvaluationReportObserver(sv);
		ServerGetDurationObserver duratin = new ServerGetDurationObserver(sv);
		//ServerDetectorObserver sdo=new ServerDetectorObserver(sv);
		ServerApproveDuratinObserver approveDuratin=new ServerApproveDuratinObserver(sv);


	      try {
		   sv.listen();
		   //ServerWindow.launchMain(sv, args);//we launch the server's window//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Start listening for connections	  	
	  }

}
