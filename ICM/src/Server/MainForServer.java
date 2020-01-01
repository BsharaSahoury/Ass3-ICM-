package Server;

import java.io.IOException;

import ocsf.server.ObservableServer;

public class MainForServer {

	  public static void main(String[] args) 
	  {
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
<<<<<<< HEAD

	    serverSubmissionObserver sso=new serverSubmissionObserver(sv);

	    ServerAllRequestsObserver serverallrequestobserver=new ServerAllRequestsObserver(sv);

=======
	    serverSubmissionObserver sso=new serverSubmissionObserver(sv);
	    ServerAllRequestsObserver serverallrequestobserver=new ServerAllRequestsObserver(sv);
>>>>>>> fbcd9b164e4cb3a4fcb8970ab67073361e830ca1
	      try {
		   sv.listen();
		   //ServerWindow.launchMain(sv, args);//we launch the server's window//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Start listening for connections	  	
	  }

}
