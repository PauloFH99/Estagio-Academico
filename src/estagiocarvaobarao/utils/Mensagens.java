package estagiocarvaobarao.utils;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class Mensagens {

    public Mensagens() {
    }

    public void campoVazio(JFXTextField campo, String Texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo não pode estar vazio!");
        campo.resetValidation();
        campo.getValidators().add(validator);

        campo.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                campo.validate();
            }
        });
        campo.validate();
    }

    public void campoVazio(JFXPasswordField campo, String Texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        campo.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                campo.validate();
            }
        });
    }

    public void desativarLabel(Label label) {
        label.setVisible(false);
    }

    public void campoNumerico(JFXTextField campo, Label label, String Texto) {
        campo.resetValidation();
        label.setDisable(false);
        label.setVisible(true);
        label.setText(Texto);
        campo.setLabelFloat(true);

    }

    public void campoLabel(DatePicker campo, Label label, String Texto) {

        label.setDisable(false);
        label.setVisible(true);
        label.setText(Texto);

    }

    public void campoLabel(JFXTextField campo, Label label, String Texto) {

        label.setDisable(false);
        label.setVisible(true);
        label.setText(Texto);

    }

    public void campoVazioCbx(JFXComboBox campo, String Texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        campo.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                campo.validate();
            }
        });
        campo.validate();
        
    }

    public void Affirmation(String header, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Prometheus informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
    }

    public boolean Confirmation(String header, String text) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        a.setTitle("Prometheus informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.getButtonTypes().setAll(btnSim, btnNao);
        a.showAndWait();
        return a.getResult() == btnSim;
    }

    public void Error(String header, String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Prometheus informa:");
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
    }
}
