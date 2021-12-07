package com.example.da_01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class MainController implements Initializable {
   @FXML
    Button createButton,deleteButton,readButton,updateButton;
   @FXML
   Label errorLabel;
   @FXML
   TableView<student> tabel;
   @FXML
   TableColumn<student,String> name,password,fDate,tDate,status,age,school,regNum,gender;
   ObservableList<student> oblist = FXCollections.observableArrayList();

   private Stage stage;
   private Parent root;
   private Scene scene;

   public void switchToSceneCreate(ActionEvent e) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Create.fxml"));
       Parent root = loader.load();
       stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
       scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }

    public void switchToSceneUpdate(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Update.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneDelete(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       DbmsConnection dbmsConnection = new DbmsConnection();
        try {
            Connection con = dbmsConnection.setConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from student3");
            while(rs.next()){
                oblist.add(new student(rs.getString("name"),rs.getString("password"),rs.getString("registerNum"),
                        rs.getString("school"),rs.getString("age"),rs.getString("fromdate"),
                        rs.getString("todate"),rs.getString("gender"),rs.getString("curStatus")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        fDate.setCellValueFactory(new PropertyValueFactory<>("Fate"));
        tDate.setCellValueFactory((new PropertyValueFactory<>("Tate")));
        status.setCellValueFactory((new PropertyValueFactory<>("status")));
        age.setCellValueFactory((new PropertyValueFactory<>("age")));
        school.setCellValueFactory((new PropertyValueFactory<>("school")));
        regNum.setCellValueFactory((new PropertyValueFactory<>("reg")));
        gender.setCellValueFactory((new PropertyValueFactory<>("gender")));

        tabel.setItems(oblist);
    }
    public class student{
        String name,reg,password,age,school, Tate, Fate,gender,status;

        public student(String name, String password, String reg, String school,
                       String age, String fDate, String tDate, String gender,
                       String status) {
            this.name = name;
            this.reg = reg;
            this.password = password;
            this.age = age;
            this.school = school;
            this.Tate = tDate;
            this.Fate = fDate;
            this.gender = gender;
            this.status = status;
            System.out.println(fDate+" "+tDate);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getTate() {
            return new String(Tate);
        }

        public void setTate(String tDate) {
            this.Tate = Tate;
        }

        public String getFate() {
            return new String(Fate);
        }

        public void setFate(String fDate) {
            this.Fate = Fate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

