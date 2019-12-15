package kromi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Hash {
	// The reference of inputText will be injected by the FXML loader
	@FXML
	private TextField inputText;	
	@FXML
	private PasswordField pField;
	@FXML
	private TextArea outputText;
	@FXML
	private RadioButton s1;
	@FXML
	private RadioButton s2;
	@FXML
	private CheckBox c1;
	@FXML
	private Label selectText;
	
	private File io;

	public Hash() {

	}
	@FXML
	private void clear() {
		inputText.setText("");
		pField.setText("");
	}
	@FXML
	private void initialize() {
		this.toggleVisible(null);
	}
	@FXML
	public void toggleVisible(ActionEvent event) {
	    if (c1.isSelected()) {
	    	pField.setText(inputText.getText());
		    pField.setVisible(true);
		    pField.setManaged(true);
		    inputText.setVisible(false);
		    inputText.setManaged(false);
	        return;
	    }
	    inputText.setText(pField.getText());
        inputText.setVisible(true);
        inputText.setManaged(true);
        pField.setVisible(false);
        pField.setManaged(false);
	}
	@FXML
	private void printOutput() {
		byte[] bytesOfMessage;
		try {
			if(c1.isSelected())
				bytesOfMessage = pField.getText().getBytes("UTF-8");
			else
				bytesOfMessage = inputText.getText().getBytes("UTF-8");
			
			if (s1.isSelected()) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(bytesOfMessage, 0, bytesOfMessage.length);
				outputText.setText(new BigInteger(1, md.digest()).toString(16));
			} else if (s2.isSelected()) {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(bytesOfMessage, 0, bytesOfMessage.length);
				outputText.setText(new BigInteger(1, md.digest()).toString(16));
			}
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void open() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectText.setText("File Selected : " + selectedFile.getName());
            io=selectedFile;
        }
        else {
            selectText.setText("File selection cancelled.");
            
        }
	}
	@FXML
	private void generateHash() {
		if (s1.isSelected()) {
			byte[] buffer = new byte[1024];
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				InputStream istream = new FileInputStream(io);
			    int numRead;
			       do {
			           numRead = istream.read(buffer);
			           if (numRead > 0) {
			               md.update(buffer, 0, numRead);
			           }
			       } while (numRead != -1);
			       istream.close();
			       byte[] bytes =md.digest();
			        String result = "";
			        for (int i=0; i < bytes.length; i++) {
			            result += Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
			        }
			        outputText.setText("File Checksum : "+result);			        
			} catch (IOException | NoSuchAlgorithmException e) {
				selectText.setText("Error : "+e.toString());
			}
		}
		else if (s2.isSelected()) {
			byte[] buffer = new byte[1024];
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("SHA-1");
				InputStream istream = new FileInputStream(io);
			    int numRead;
			       do {
			           numRead = istream.read(buffer);
			           if (numRead > 0) {
			               md.update(buffer, 0, numRead);
			           }
			       } while (numRead != -1);
			       istream.close();
			       byte[] bytes =md.digest();
			        String result = "";
			        for (int i=0; i < bytes.length; i++) {
			            result += Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
			        }
			        outputText.setText("File Checksum : "+result);			        
			} catch (IOException | NoSuchAlgorithmException e) {
				selectText.setText("Error : "+e.toString());
			}
		}
	}
}
