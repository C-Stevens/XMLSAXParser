/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlsaxparser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Colin
 */
public class XMLSAXParserFXMLController implements Initializable {

    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    
    @FXML
    private AnchorPane viewPane;
    
    @FXML
    private void handleFileOpen(ActionEvent event) {
        File inputFile = fileChooser.showOpenDialog(stage);
        if(inputFile != null) {
            viewPane.getChildren().clear();
            viewPane.getChildren().add(XMLParser.createTreeViewFromXML(inputFile));
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("File Error");
            alert.setContentText("There was an error opening the specified file.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleAbout(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("This application was written to demonstrate generic XML parsing and display abilities in Java using the SAX parser.\n\nThis application was written by Colin Stevens for MU-CS4330: OO & Software Design II");
        alert.showAndWait();
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {} // Unused
        
    public void start(Stage stage) {
        this.stage = stage;
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );
    }
}
