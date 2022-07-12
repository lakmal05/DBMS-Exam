package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.student;
import util.CrudUtil;

import java.sql.*;

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
    public JFXTextField searchStudentField;


    public void initialize() {


        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        try {
            loadAllstudent();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadAllstudent() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ijse", "root", "1234");
        String sql = "SELECT * FROM student";
        Statement statement = con.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);

        ObservableList<student> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(
                    new student(
                            result.getString("studentId"),
                            result.getString("studentName"),
                            result.getString("email"),
                            result.getString("contact"),
                            result.getString("address"),
                            result.getString("nic")
                    )


            );
        }
        StudentTable.setItems(obList);

        FilteredList<student> filteredData = new FilteredList<>(obList, b -> true);
        searchStudentField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Medicine -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                ///Search uong ID
                if (Medicine.getStudentId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Medicine.getStudentName().toLowerCase().indexOf(searchKeyword) > -1;

            });


        });
        //Filters data insert sorted list
        SortedList<student> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(StudentTable.comparatorProperty());
        StudentTable.setItems(sortedData);


    }



    public void btnAddToTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


    /*    Image image = new Image("saved.png");

        Notifications notBuilder = Notifications.create()
                .title("Success").title(" Medicine Updating Successful !!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Clicked Oon Notification");
                    }
                });
        notBuilder.darkStyle();
        notBuilder.show();*/


        student m = new student(
               txtStudentId.getText(), txtStudentName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNic.getText()
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ijse", "root", "1234");

            String sql = "INSERT INTO student VALUES('" + m.getStudentId() + "','" + m.getStudentName() + "','" + m.getEmail() + "','" + m.getContact() + "','" + m.getAddress() + "','" + m.getNic() + "')";

            Statement stm = con.createStatement();
            int effectedRowCount = stm.executeUpdate(sql);
            System.out.println(effectedRowCount);


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        loadAllstudent();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        student s = new student(
                txtStudentId.getText(), txtStudentName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(),txtNic.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE ijse.student SET studentName=? , email=? , contact=? , address=?,nic=? WHERE studentId=?", s.getStudentName(), s.getEmail(), s.getContact(), s.getAddress(), s.getStudentId(),s.getNic());
            if (isUpdated) {






            } else {




            }


        } catch (SQLException | ClassNotFoundException e) {

        }
        loadAllstudent();

    }









    public void btnStudentdeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

      /*  Image image = new Image("saved.png");


        Notifications notBuilder = Notifications.create()
                .title("Warning").title("Supplier Has been Deleted !!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Clicked Oon Notification");
                    }
                });
        notBuilder.darkStyle();

        notBuilder.show();*/


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ijse", "root", "1234");
            String sql = "DELETE FROM ijse.student WHERE studentId ='" + txtStudentId.getText() + "'";

            Statement statement = con.createStatement();
            boolean isDeleted = statement.executeUpdate(sql) > 0;


            if (isDeleted) {
                //Conformation
            } else {
                //Conformation
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        clearTextFields();
        loadAllstudent();


    }



 public void clearTextFields(){

 }


    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
            String sql = "SELECT * FROM ijse.student    WHERE studentId ='" + searchStudentField.getText() + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            if (resultSet.next()) {
                txtStudentId.setText(resultSet.getString(1));
                txtStudentName.setText(resultSet.getString(2));
                txtEmail.setText(resultSet.getString(3));
                txtContact.setText(resultSet.getString(4));
                txtAddress.setText(resultSet.getString(5));
                txtNic.setText(resultSet.getString(6));
            } else {
                //Conformation
              /*  Image image = new Image("Delete.png");


                Notifications notBuilder = Notifications.create()
                        .title("Success").title(" Something Went Wrong! Try Again !!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked Oon Notification");
                            }
                        });
                notBuilder.darkStyle();
                notBuilder.show();
*/

            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        loadAllstudent();
        clearTextFields();

    }















}
