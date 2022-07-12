package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class StudentForm {


    public AnchorPane studentFormContext;
    public TableView StudentTable;
    
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;

    public JFXTextField txtStudentId;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public JFXTextField txtStudentName;
    public JFXTextField txtNic;
}
