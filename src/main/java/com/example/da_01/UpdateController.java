package com.example.da_01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateController {
    @FXML
    TextField nameField,dataField,updateField;
    @FXML
    Label statusLabel;

    public void checkData() throws SQLException, ClassNotFoundException {
        DbmsConnection dbmsConnection = new DbmsConnection();
        Connection con = dbmsConnection.setConnection();
        String sql = "select * from student3 where name=?;";
        PreparedStatement stmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1,nameField.getText());
        ResultSet rs = stmt.executeQuery();
        if(rs.next() == false){
            statusLabel.setText("No such record found");
            statusLabel.setTextFill(Color.RED);
        }
        else{
            statusLabel.setText("Record updated successfully");
            statusLabel.setTextFill(Color.GREEN);
//            rs.previous();
            String sql1 = "update student3 set password=? where name =?;";
            PreparedStatement stmt1 = con.prepareStatement(sql1);
//            stmt1.setString(1,updateField.getText());
            stmt1.setString(1,dataField.getText());
            stmt1.setString(2,nameField.getText());
            stmt1.executeUpdate();
            stmt1.close();
        }
        stmt.close();
        con.close();
    }
    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
