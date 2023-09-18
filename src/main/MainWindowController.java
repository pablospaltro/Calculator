package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnClose;
    @FXML private Label lblResult;
    private double x, y;
    private double num1 = 0;
    private String operator = "+";
    private String separator = ".";
    private boolean decimalEntered = false;

    public void init(Stage stage){
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });
        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

        lblResult.setText("0");
    }


    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn", ""));

        if (decimalEntered) {
            lblResult.setText(lblResult.getText() + value);
        } else {
            if (Double.parseDouble(lblResult.getText()) == (int) Double.parseDouble(lblResult.getText())) {
                lblResult.setText(String.valueOf((int) Double.parseDouble(lblResult.getText()) * 10 + value));
            } else {
                lblResult.setText(lblResult.getText() + separator + value);
            }
        }
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn", "");
        if(symbol.equals("Equals")){
            double num2 = Double.parseDouble(lblResult.getText());
            switch (operator){
                case "+" -> lblResult.setText((num1 + num2) + "");
                case "-" -> lblResult.setText((num1 - num2) + "");
                case "*" -> lblResult.setText((num1 * num2) + "");
                case "/" -> lblResult.setText((num1 / num2) + "");
            }
            operator = ".";
            decimalEntered = false;
        }
        else if (symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0));
            decimalEntered = false;
        } else if (symbol.equals("Decimal")) {
            if (!decimalEntered) {
                lblResult.setText(lblResult.getText() + separator);
                decimalEntered = true;
            }
        }
        else {
            switch(symbol){
                case "Plus" -> operator = "+";
                case "Minus" -> operator = "-";
                case "Multiply" -> operator = "*";
                case "Divide" -> operator = "/";
            }
            num1 = Double.parseDouble(lblResult.getText());
            lblResult.setText(String.valueOf(0));
            decimalEntered = false;
        }
    }

    @FXML
    void onDecimalClicked(MouseEvent event) {
        if (!lblResult.getText().contains(separator)) {
            lblResult.setText(lblResult.getText() + separator);
            decimalEntered = true;
        }
    }




}
