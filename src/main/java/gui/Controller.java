package gui;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Data;
import simulation.*;

import javax.naming.Binding;
import java.util.Arrays;

public class Controller {

    @FXML
    private JFXButton usersButton;

    @FXML
    private JFXButton addingUserButton;
    @FXML
    private JFXButton addingProductButton;

    @FXML
    private JFXButton distributorsButton;

    @FXML
    private JFXButton productsButton;

    @FXML
    private JFXButton simulationButton;

    @FXML
    private JFXTreeTableView<User> userTable;
    @FXML
    private TreeTableColumn<User, String> birthDateColumn;

    @FXML
    private TreeTableColumn<User, String> userIDColumn;

    @FXML
    private TreeTableColumn<User, String> emailColumn;

    @FXML
    private TreeTableColumn<User, String> subscriptionColumn;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label dayLabel;

    @FXML
    private TreeTableColumn<User, String> creditcardColumn;

    private ObservableList<User> users = FXCollections.observableArrayList();

    private ObservableList<Distributor> distributors = FXCollections.observableArrayList();

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private Pane distributorsPane;
    @FXML
    private Pane productsPane;

    @FXML
    private JFXTreeTableView<Product> productTable;

    @FXML
    private TreeTableColumn<Product, String> productName;

    @FXML
    private TreeTableColumn<Product, String> description;

    @FXML
    private TreeTableColumn<Product, String> realiseDate;

    @FXML
    private TreeTableColumn<Product, String> rating;

    @FXML
    private TreeTableColumn<Product, String> price;

    @FXML
    private JFXTreeTableView<Distributor> distributorTable;

    @FXML
    private TreeTableColumn<Distributor, String> distributorIDColumn;

    @FXML
    private TreeTableColumn<Distributor, String> distributorLicenceID;

    @FXML
    private TreeTableColumn<Distributor, String> payedWatches;

    @FXML
    private TreeTableColumn<Distributor, String> bid;

    @FXML
    private TreeTableColumn<Distributor, String> distributorCreditcardColumn;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane simulationPane;
    @FXML
    private Pane usersPane;
    @FXML
    private JFXToggleButton startSimulationButton;
    @FXML
    private JFXButton pauseSimulationButton;
    @FXML
    private JFXButton addingDistributorButton;
    @FXML
    private Label statusLabel;
    @FXML
    private StackPane stackPane;


    private void resetButtons(JFXButton... buttons){
        for (JFXButton button : buttons){
            button.setPrefHeight(68);
            button.setStyle(
                    "-fx-background-radius: 15 0 15 0;"
                    +"-fx-opacity: 0.5;"
                    + "-fx-background-color:  #4a6b6b;"

            );
        }
    }

    @FXML
    private void changeTab(JFXButton button) {
        resetButtons(usersButton, simulationButton, distributorsButton, productsButton);
        button.setStyle(
                "-fx-background-radius: 15 0 0 0;"
                        + "-fx-opacity: 0.8;"
                        + "-fx-background-color:  #4a6b6b;"
        );
        button.setPrefHeight(76);
    }

    @FXML
    private void changePane(ActionEvent event) {
        simulationPane.setVisible(false);
        usersPane.setVisible(false);
        distributorsPane.setVisible(false);
        simulationPane.setOpacity(0.6);
        usersPane.setOpacity(0.6);
        distributorsPane.setOpacity(0.6);
        productsPane.setVisible(false);
        productsPane.setOpacity(0.6);
        if(event.getSource() == productsButton){
            changeTab((JFXButton)event.getSource());
            refreshTable();
            productsPane.setVisible(true);
            new SlideInLeft(productsPane).play();
            productsPane.setOpacity(0.8);
        }
        if(event.getSource() == simulationButton){
            changeTab((JFXButton)event.getSource());
            simulationPane.setVisible(true);
            new SlideInLeft(simulationPane).play();
            simulationPane.setOpacity(0.8);
        }
        if(event.getSource() == usersButton){
            changeTab((JFXButton)event.getSource());
            refreshTable();
            usersPane.setVisible(true);
            new SlideInLeft(usersPane).play();
            usersPane.setOpacity(0.8);
        }
        if(event.getSource() == distributorsButton){
            changeTab((JFXButton) event.getSource());
            refreshTabledistributor();
            distributorsPane.setVisible(true);
            new SlideInLeft(distributorsPane).play();
            distributorsPane.setOpacity(0.8);
        }


    }

    @FXML
    private void addDistributor() {
        if (startSimulationButton.isSelected())
            Simulation.getInstance().addDistributor();
    }
    @FXML
    private void addUser(){
        if (startSimulationButton.isSelected())
            Simulation.getInstance().addUser();
    }
    @FXML
    private void addProduct(){
        if (startSimulationButton.isSelected() && DistributorManager.getInstance().getDistributorList().size()>0)
            DistributorManager.getInstance().getDistributorList().get(0).addProduct();
    }

    @FXML
    private void startSimulationButton() {
        if (!startSimulationButton.isSelected()) {
            Simulation.getInstance().end();
        } else {
            Simulation.getInstance().run();
        }
    }

    @FXML
    private void pauseSimulationButton() {
        if (startSimulationButton.isSelected()) {
            if (Simulation.getInstance().isPaused() == 0) {
                Simulation.getInstance().pause();
            } else {
                return;
            }

        }
    }

    @FXML
    private void resumeSimulationButton() {
        if (startSimulationButton.isSelected()) {
            if (Simulation.getInstance().isPaused() == 1) {
                Simulation.getInstance().resume();
            }

        }
    }

    @FXML
    private void exit() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(150));
        fadeTransition.setNode(anchorPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(1);
        //new ZoomOutDown(anchorPane).play();
        fadeTransition.play();
        fadeTransition.setOnFinished(actionEvent -> {
            if(Simulation.getInstance().isRun()) Simulation.getInstance().end();
            Platform.exit();
        });
    }

    @FXML
    void initialize() {
        anchorPane.setBackground(Background.EMPTY);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), actionEvent -> {
            dayLabel.setText(String.valueOf(Time.getInstance().getDayNumber()));
            budgetLabel.setText(String.valueOf(Wallet.getINSTANCE().getSum().intValue()) + "$");
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFromStart();
        createUserTable();
        createDistributorTable();
        createProductsTable();
        loadUserTable();
        loadDistributorTable();
        loadProductsTable();
    }

    private void refreshTable(){
        users = FXCollections.observableArrayList();
        users.addAll(UserManager.getInstance().getUserList());
        userTable.setRoot(new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren));
        userTable.refresh();
        refreshTabledistributor();
        products = FXCollections.observableArrayList();
        products.addAll(ProductMenager.getInstance().getProductList());
        productTable.setRoot(new RecursiveTreeItem<>(products, RecursiveTreeObject::getChildren));
    }
    private void loadProductsTable(){
        products.clear();
        users.addAll(UserManager.getInstance().getUserList());
        final TreeItem<Product> root = new RecursiveTreeItem<>(products, RecursiveTreeObject::getChildren);
        productTable.getColumns().setAll(productName, description, realiseDate, rating, price);
        productTable.setRoot(root);
        productTable.setShowRoot(false);
    }

    private void createProductsTable() {
        productTable.setRowFactory(productColumn -> {
            TreeTableRow<Product> productTreeTableRow = new TreeTableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteproduct = new MenuItem();
            deleteproduct.textProperty().bind(Bindings.format("Delete product"));
            contextMenu.getItems().add(deleteproduct);
            productTreeTableRow.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    productTreeTableRow.setContextMenu(null);
                } else {
                    productTreeTableRow.setContextMenu(contextMenu);
                }
            });
            productTreeTableRow.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && !productTreeTableRow.isEmpty()){
                    productDescription(productTreeTableRow.getItem());
                }
            });


            return productTreeTableRow;
        });

        productName.setPrefWidth(150);
        productName.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getName())
        );
        description.setPrefWidth(200);
        description.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getDescription())
        );
        realiseDate.setPrefWidth(150);
        realiseDate.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getReleaseDate())
        );
        rating.setPrefWidth(250);
        rating.setCellValueFactory(
                param -> new SimpleStringProperty(Double.toString(param.getValue().getValue().getRating()))
        );
        price.setPrefWidth(150);
        price.setCellValueFactory(
                param -> new SimpleStringProperty(Double.toString(param.getValue().getValue().getPrise()))
        );
    }

    private void productDescription(Product item) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        Text title = new Text("Product details");
        if(item instanceof Movie) {
            Text text = new Text(
                    "Type : Movie" +
                    "\nTitle: " + item.getName() +
                    "\nDay: " + Time.getInstance().getDayNumber() +
                    "\nWatches: " + item.getWatched() +
                    "\nRelease Date: " + item.getReleaseDate() +
                    "\nCountry: " + item.getCountry() +
                    "\nLicense Nr.: " + item.getLicenseNR() +
                    "\nLength: " + item.getRuntime() +
                    "\nRating: " + item.getRating()+
                    "\nGenre: " + ((Movie) item).getGenre() +
                            "\nCast: " + Arrays.toString(((Movie) item).getCast()) +
                            "\nTrailer : " + ((Movie) item).getTrailer() +
                            "\nTime to watch: " + ((Movie) item).getTimeToWatch()

            );
            title.setFont(new Font("Calibri", 16));
            title.setFill(Color.WHITE);
            text.setFont(new Font("Calibri", 14));
            text.setFill(Color.WHITE);
            dialogLayout.setHeading(title);
            dialogLayout.setBody(text);
            dialogLayout.setBackground(new Background(new BackgroundFill(Color.web( "#4A6B6B"), CornerRadii.EMPTY, Insets.EMPTY)));
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        else{
            Text text = new Text(
                    "Type : Serie" +
                            "\nTitle: " + item.getName() +
                            "\nDay: " + Time.getInstance().getDayNumber() +
                            "\nWatches: " + item.getWatched() +
                            "\nRelease Date: " + item.getReleaseDate() +
                            "\nCountry: " + item.getCountry() +
                            "\nLicense Nr.: " + item.getLicenseNR() +
                            "\nEpisode length: " + item.getRuntime() +
                            "\nRating: " + item.getRating()+
                            "\nGenre: " + ((Series) item).getGenre() +
                            "\nCast: " + Arrays.toString(((Series) item).getCast()) +
                            "\nTrailer : " + ((Series) item).getSeason().getEpisodeNumber()

            );
            title.setFont(new Font("Calibri", 16));
            title.setFill(Color.WHITE);
            text.setFont(new Font("Calibri", 14));
            text.setFill(Color.WHITE);
            dialogLayout.setHeading(title);
            dialogLayout.setBody(text);
            dialogLayout.setBackground(new Background(new BackgroundFill(Color.web( "#4A6B6B"), CornerRadii.EMPTY, Insets.EMPTY)));
            JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }

    }

    private void loadUserTable(){
        users.clear();
        users.addAll(UserManager.getInstance().getUserList());
        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        userTable.getColumns().setAll(userIDColumn, emailColumn, subscriptionColumn, creditcardColumn, birthDateColumn);
        userTable.setRoot(root);
        userTable.setShowRoot(false);
    }

    private void createUserTable(){
        userTable.setRowFactory(userColumn -> {
            TreeTableRow<User> userTreeTableRow = new TreeTableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteUser = new MenuItem();
            deleteUser.textProperty().bind(Bindings.format("Delete user"));
            contextMenu.getItems().add(deleteUser);
            userTreeTableRow.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if(isNowEmpty) {
                    userTreeTableRow.setContextMenu(null);
                } else {
                    userTreeTableRow.setContextMenu(contextMenu);
                }
            });
            return userTreeTableRow;
        });

        userIDColumn.setPrefWidth(150);
        userIDColumn.setCellValueFactory(
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getId()))
        );
        subscriptionColumn.setPrefWidth(200);
        subscriptionColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getSubscription().getType().name())
        );
        emailColumn.setPrefWidth(150);
        emailColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getEmail())
        );
        creditcardColumn.setPrefWidth(250);
        creditcardColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getBankAccount())
        );
        birthDateColumn.setPrefWidth(150);
        birthDateColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getBirthDate())
        );
    }
    private void refreshTabledistributor(){
        distributors = FXCollections.observableArrayList();
        distributors.addAll(DistributorManager.getInstance().getDistributorList());
        distributorTable.setRoot(new RecursiveTreeItem<>(distributors, RecursiveTreeObject::getChildren));
        distributorTable.refresh();
    }

    private void loadDistributorTable(){
        distributors.clear();
        distributors.addAll(DistributorManager.getInstance().getDistributorList());
        final TreeItem<Distributor> root2 = new RecursiveTreeItem<>(distributors, RecursiveTreeObject::getChildren);
        distributorTable.getColumns().setAll(distributorIDColumn, distributorLicenceID, payedWatches, bid, distributorCreditcardColumn);
        distributorTable.setRoot(root2);
        distributorTable.setShowRoot(false);
    }

    private void createDistributorTable(){
        distributorTable.setRowFactory(distributorColumn -> {
            TreeTableRow<Distributor> distributorTreeTableRow = new TreeTableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteDistributor = new MenuItem();
            deleteDistributor.textProperty().bind(Bindings.format("Delete distributor"));
            contextMenu.getItems().add(deleteDistributor);
            distributorTreeTableRow.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if(isNowEmpty) {
                    distributorTreeTableRow.setContextMenu(null);
                } else {
                    distributorTreeTableRow.setContextMenu(contextMenu);
                }
            });
            return distributorTreeTableRow;
        });

        distributorIDColumn.setPrefWidth(150);
        distributorIDColumn.setCellValueFactory(
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getNumber()))
        );
        distributorLicenceID.setPrefWidth(200);
        distributorLicenceID.setCellValueFactory(
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getValue().getLicence().getLicenceId()))
        );
        payedWatches.setPrefWidth(150);
        payedWatches.setCellValueFactory(
                param -> new SimpleStringProperty(Double.toString(param.getValue().getValue().getLicence().countWatches()))
        );
        bid.setPrefWidth(250);
        bid.setCellValueFactory(
                param -> new SimpleStringProperty(Double.toString(param.getValue().getValue().getBid()))
        );
        distributorCreditcardColumn.setPrefWidth(150);
        distributorCreditcardColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getBankAccount())
        );
    }

}
