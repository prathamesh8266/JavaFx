package com.example.da_01;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML
    TextField nameField,RegistrationNumberField,passwordField;
    @FXML
    Label agelabel;
    @FXML
    ChoiceBox<String> schoolDropdown;
    @FXML
    CheckBox student,intern,placed,studying;
    @FXML
    RadioButton male,female,other;
    @FXML
    Slider ageSlider;
    @FXML
    DatePicker fromPicker,toPicker;

    String gender = "";
    String currentStatus = "";

    public void submit(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        if(male.isSelected()){
            gender = "Male";
        }else if(female.isSelected()){
            gender = "Female";
        }else{
            gender = "Other";
        }
        if(student.isSelected()){
            currentStatus += "Student ";
        }if(intern.isSelected()){
            currentStatus+="Intern ";
        }if(placed.isSelected()){
            currentStatus+= "Placed ";
        }if(studying.isSelected()){
            currentStatus+= "Studying ";
        }
        DbmsConnection dbmsConnection = new DbmsConnection();
        Connection con = dbmsConnection.setConnection();
        String sql = "insert into student3 values(?,?,?,?,?,?,?,?,?);";
        LocalDate date1 = fromPicker.getValue();
        LocalDate date2 = toPicker.getValue();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, nameField.getText());
        stmt.setString(2,passwordField.getText());
        stmt.setString(3,RegistrationNumberField.getText());
        stmt.setString(4,schoolDropdown.getValue());
        stmt.setInt(5,(int)ageSlider.getValue());
        stmt.setString(6, date1.toString());
        stmt.setString(7, date2.toString());
        stmt.setString(8,gender);
        stmt.setString(9,currentStatus);
        stmt.executeUpdate();
        stmt.close();
        dbmsConnection.closeConnection(con);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private int age;
    private String school[] = {"SENSE","SCOPE","SITE","SELECT","SMEC"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schoolDropdown.getItems().addAll(school);
        ageSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                age=(int)ageSlider.getValue();
                agelabel.setText("[ "+Integer.toString(age)+" ]");
            }
        });
    }

}
    class DbmsConnection{
        Connection setConnection() throws ClassNotFoundException, SQLException {
            Connection con = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vit","root","");
            System.out.println("Connection established");
            return con;
        }
        void closeConnection(Connection con) throws SQLException {
            con.close();
        }
    }
