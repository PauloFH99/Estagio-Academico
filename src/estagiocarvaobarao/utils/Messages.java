package estagiocarvaobarao.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class Messages {
    
    
    public Messages(){}
    
    public void Affirmation(String header, String text){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Prometheus informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
    }
    
    public boolean Confirmation(String header, String text){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao= new ButtonType("Não");
        a.setTitle("Prometheus informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.getButtonTypes().setAll(btnSim,btnNao);
        a.showAndWait();
        return a.getResult() == btnSim;
    }
    
    public void Error(String header, String text){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Prometheus informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
    }
}
