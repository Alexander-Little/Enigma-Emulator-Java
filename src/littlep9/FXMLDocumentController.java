/*
Alexander Little - alittle5@cnm.edu 
Java - CIS 2235 - Ivonne Nelson
Program 9 Enigma with polymorphism
 */
package littlep9;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author alittle5
 */
public class FXMLDocumentController implements Initializable {
    
    //Enigma enig = new Enigma();
    Enigma enig[] = new Enigma[3]; //enigma array of enigma references
    
    //enigma references
    int encryptNum;
    PrimeShiftEnigma pse = new PrimeShiftEnigma();
    ShiftyEnigma shifty = new ShiftyEnigma();
    DaileyEnigma dailey = new DaileyEnigma();
    
    
    int kTemp;
    String mTemp;
    @FXML
    private Label lblMsgtobe;
    @FXML
    private TextArea txtUserMsg;
    @FXML
    private TextArea txtDecodedMsg;
    @FXML
    private TextArea txtEncodedMsg;
    @FXML
    private TextField txtKey;
    @FXML
    private TextField txtUserKey;
    @FXML
    private Button btnEncode;
    @FXML
    private Button btnDecode;
    @FXML
    private Button btnClear;
    @FXML
    private RadioButton rbGenKey;
    @FXML
    private RadioButton rbUseKey;
    @FXML
    private ToggleGroup grpEncryptKey;
    @FXML
    private MenuItem menuSave;
    @FXML
    private MenuItem menuOpen;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private Label label;
    @FXML
    private RadioButton rbPrime;
    @FXML
    private ToggleGroup grpEncryptType;
    @FXML
    private RadioButton rbShifty;
    @FXML
    private RadioButton rbDailey;
    @FXML
    private TextField txtEncryptType;
    @FXML
    private Label lblResultBanner;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnEncode.setOnAction(this::encodeButtonAction);
        btnDecode.setOnAction(this::decodeButtonAction);
        btnClear.setOnAction(this::clearButtonAction);
        menuSave.setOnAction(this::saveMenuAction);
        menuOpen.setOnAction(this::openMenuAction);
        menuAbout.setOnAction(this::aboutMenuAction);
        
        enig[0] = pse;
        enig[1] = shifty;
        enig[2] = dailey;
         encryptNum = 0; //represents user selection 
    }       

    
    private void clearButtonAction(ActionEvent event) {
        txtUserMsg.setText("");
        txtDecodedMsg.setText("");
        txtEncodedMsg.setText("");
        txtKey.setText("");
        txtUserKey.setText("");
        lblResultBanner.setText("---Encrypted Message Info---");
        enig = new Enigma[3];
        enig[0] = pse;
        enig[1] = shifty;
        enig[2] = dailey;
         encryptNum = 0; 

    }
    private void encodeButtonAction(ActionEvent event) {
          
        
        if(rbPrime.isSelected())
        {
            encryptNum = 0;
            txtEncryptType.setText("Prime");
        }
        
        if(rbShifty.isSelected())
        {
            encryptNum = 1;
            txtEncryptType.setText("Shifty");
        }
        
        if(rbDailey.isSelected())
        {
            encryptNum = 2;
            txtEncryptType.setText("Dailey");
        }

        
        
        if (rbGenKey.isSelected())
        {
            mTemp = txtUserMsg.getText();
            enig[encryptNum].setMessage(mTemp);
            enig[encryptNum].setMessage(txtUserMsg.getText());
        }
        else
        {
            mTemp = txtUserMsg.getText();
            if (!"".equals(txtUserKey.getText()) && Integer.parseInt(txtUserKey.getText()) <= 50 && Integer.parseInt(txtUserKey.getText()) >= 0)
            {
                kTemp = Integer.parseInt(txtUserKey.getText());
            }
            else{
                JFrame frame = new JFrame();
                kTemp = (int)(Math.random() * 50);
                JOptionPane.showMessageDialog(frame, "Your encryption key was invalid, so a random number was assigned instead.");
            }
            
            enig[encryptNum].setMessage(mTemp, kTemp);
        }
        lblResultBanner.setText("---Encrypted Message Info---");
        txtDecodedMsg.setText(enig[encryptNum].getDecodedMessage());
        txtEncodedMsg.setText(enig[encryptNum].getCodedMessage());
        txtKey.setText(String.valueOf(enig[encryptNum].getKey()));
            
    }
    private void decodeButtonAction(ActionEvent event) {
        openMenuAction(event);
        //actually needs to actually use txt file, actually 
        // mTemp = txtUserMsg.getText();
        // kTemp = Integer.valueOf(txtUserKey.getText());
        enig[encryptNum].setCodedMessage(mTemp, kTemp);
        
        if (encryptNum == 0){
            txtEncryptType.setText("Prime");                   
        }
        if (encryptNum == 1){
            txtEncryptType.setText("Shifty");
        }
        if (encryptNum == 2){
            txtEncryptType.setText("Dailey");
        }
        txtDecodedMsg.setText(enig[encryptNum].getDecodedMessage());
       // txtEncodedMsg.setText(enig[encryptNum].getCodedMessage());
        txtEncodedMsg.setText(mTemp);
        txtKey.setText(String.valueOf(enig[encryptNum].getKey()));
        
    }
    
    private void saveMenuAction(ActionEvent event){
         //Create Filechooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Save a Coded Message in a File");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);         
        
        //Show the Save File Dialog
        File file = fileChooser.showSaveDialog(null); 
        
        if(file != null)
        {
            PrintWriter outputFile = null;
            try {
                String filename = file.getCanonicalPath();
                File myFile = new File(filename);
                outputFile = new PrintWriter(filename);
                outputFile.println(enig[encryptNum].getCodedMessage());
                outputFile.println(enig[encryptNum].getKey());
                outputFile.println(encryptNum);
               
               outputFile.close();
                
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void openMenuAction(ActionEvent event){
        //txtEncodedMsg.setText("Butts are opening");
                 //Create Filechooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Open a Coded Message and key in a File");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);         
        
        //Show the Open File Dialog
        File file = fileChooser.showOpenDialog(null);  
        
        if(file != null)
        {
            //reads in the message, encryption type and the key from a file
            try {
                String filename = file.getCanonicalPath();
                File myFile = new File(filename);
                Scanner inputFile = new Scanner(myFile);        
                mTemp = inputFile.nextLine();
                kTemp = inputFile.nextInt();
                encryptNum = inputFile.nextInt();
                inputFile.close();
                lblMsgtobe.setText("--Message to be decrypted--");
                lblResultBanner.setText("---Decrypted Message Info---");
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    
    private void aboutMenuAction(ActionEvent event){
        //displays information about the history of the enigma machine
        try {
            File myFile = new File("EnigmaReadMe.txt");
           Desktop.getDesktop().open(myFile);           
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
        
    }
    
    
    @FXML
      private void handleButtonAction(ActionEvent event) {
        //
    }
  
}
