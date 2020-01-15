package Boundary;

import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Employee;
import Entity.Request;
import Entity.Student;
import Entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ProfileSettingController {
	
	private static ClientConsole cc;

	///PrfileSettingforStudent
	@FXML
	private TextField id_txt;
	@FXML
	private TextField username_txt;	
	@FXML
	private TextField fullname_txt;	
	@FXML
	private TextField email_txt;
	@FXML
	private TextField faculty_txt;
	@FXML
	private CheckBox allowrecSMS;
	@FXML
	private CheckBox allowrecGmail;
	@FXML
	private TextField dest_txt;
	@FXML
	private Button browsebtn;
	@FXML
	private Button applyandsavebtn;
	
	//ProfileSettingForEmployee
	@FXML
	private TextField id_txt1;
	@FXML
	private TextField username_txt1;	
	@FXML
	private TextField fullname_txt1;	
	@FXML
	private TextField email_txt1;
	@FXML
	private TextField job_txt1;
	@FXML
	private TextField faculty_txt1;
	@FXML
	private CheckBox allowrecSMS1;
	@FXML
	private CheckBox allowrecGmail1;
	@FXML
	private TextField dest_txt1;
	@FXML
	private Button browsebtn1;
	@FXML
	private Button applyandsavebtn1;
	
	
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;

	@FXML
	private static SplitPane splitpane;
	private FXMLLoader loader;	
	private static User user;
	private static String job;
	private static ArrayList<String> arrOfProfileSetting;
	File choosenFile;


	public void start(SplitPane splitpane, User user,String userJob) {
			this.splitpane=splitpane;
			this.user=user;
			this.job=userJob;
			primaryStage=LoginController.primaryStage;
			Object[] msg=new String[3];
			this.cc=LoginController.cc;
			try{	
				if(job.equals("Student"))
					loader = new FXMLLoader(getClass().getResource("/Boundary/ProfileSettingForStudent.fxml"));
				else
					loader = new FXMLLoader(getClass().getResource("/Boundary/ProfileSettingForEmployee.fxml"));
				lowerAnchorPane = loader.load();
				splitpane.getItems().set(1, lowerAnchorPane);
				msg[0]="ProfileSetting";
				msg[1]=user.getUsername();
				msg[2]=job;
				cc.getClient().sendToServer(msg);
			} catch(Exception e) {
				e.printStackTrace();
		}		
	}
	public void fillProfileSettingData(ArrayList<String> arr1) {
	arrOfProfileSetting=arr1;	
	loader.<ProfileSettingController>getController().setProfileSetting(arr1);		
	}
	public void setProfileSetting(ArrayList<String> arr){
		if(!arr.equals(null)) {
			if(job.equals("Student"))
			{
			id_txt.setText(arr.get(0));
			id_txt.setEditable(false);
			username_txt.setText(arr.get(1));
			username_txt.setEditable(false);
			fullname_txt.setText(arr.get(2));
			fullname_txt.setEditable(false);
			email_txt.setText(arr.get(3));
			email_txt.setEditable(false);
			faculty_txt.setText(arr.get(4));
			faculty_txt.setEditable(false);
			allowrecSMS.setSelected(true);
			allowrecGmail.setSelected(true);
			allowrecSMS.setDisable(true);
			allowrecGmail.setDisable(true);
			dest_txt.setText("");
			dest_txt.setEditable(false);	
			}
			else
			{
				id_txt1.setText(arr.get(0));
				id_txt1.setEditable(false);
				username_txt1.setText(arr.get(1));	
				username_txt1.setEditable(false);
				fullname_txt1.setText(arr.get(2));
				fullname_txt1.setEditable(false);
				email_txt1.setText(arr.get(3));
				email_txt1.setEditable(false);
				job_txt1.setText(job);
				job_txt1.setEditable(false);
				faculty_txt1.setText(arr.get(4));
				faculty_txt1.setEditable(false);
				allowrecSMS1.setSelected(true);
				allowrecSMS1.setDisable(true);
				allowrecGmail1.setSelected(true);
				allowrecGmail1.setDisable(true);
				dest_txt1.setText("");
				dest_txt1.setEditable(false);			
				}
		}
	}
	
	
	public void ApplyAndSaveAction(ActionEvent event) {
		if(job.equals("Student"))
			if(dest_txt.getText().equals(""))
			{
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Updating picture Failed!");
        alert.setHeaderText("Updating picture Failed!");
        alert.setContentText("please browse an Image");
        alert.showAndWait();
		}
	}

	public void BrowseAction(ActionEvent event) {
			FileChooser fc=new FileChooser();
			fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files","*.png"));
			File selectedFile=fc.showOpenDialog(null);
			if(selectedFile != null) {
				choosenFile=selectedFile;
				if(job.equals("Student"))
					dest_txt.setText(choosenFile.getName());
				else
					dest_txt1.setText(choosenFile.getName());
			}
		}	
}
