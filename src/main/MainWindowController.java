package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML
    private Pane titlePane;
    @FXML
    private ImageView btnMinimize, btnClose;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblResult2;
    private double x, y;
    private double num1 = 0;
    private String operator = "+";
    private String separator = ".";
    private boolean decimalEntered = false;
    private String expression = "";
    private boolean isFirstNumberEntered = false;

    public void init(Stage stage) {
        lblResult.requestFocus();
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

        lblResult.setText("0");
        lblResult2.setText("");
    }

    private void updateExpression(String value) {
        expression += value;
        lblResult2.setText(expression);
    }


    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane) event.getSource()).getId().replace("btn", ""));

        if (!isFirstNumberEntered) {
            lblResult2.setText(""); // Borra el contenido de lblResult2 al ingresar el primer número
            isFirstNumberEntered = true;
        }

        if (decimalEntered) {
            lblResult.setText(lblResult.getText() + value);
        } else {
            if (Double.parseDouble(lblResult.getText()) == (int) Double.parseDouble(lblResult.getText())) {
                lblResult.setText(String.valueOf((int) Double.parseDouble(lblResult.getText()) * 10 + value));
            } else {
                lblResult.setText(lblResult.getText() + separator + value);
            }
        }

        updateExpression(String.valueOf(value)); // Actualiza la expresión con el número
    }



    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane) event.getSource()).getId().replace("btn", "");
        Map<String, String> symbolMappings = Map.of(
                "Plus", "+",
                "Minus", "-",
                "Multiply", "*",
                "Divide", "/"
        );

        if (symbol.equals("Equals")) {
            double num2 = Double.parseDouble(lblResult.getText());
            switch (operator) {
                case "+" -> lblResult.setText((num1 + num2) + "");
                case "-" -> lblResult.setText((num1 - num2) + "");
                case "*" -> lblResult.setText((num1 * num2) + "");
                case "/" -> lblResult.setText((num1 / num2) + "");
            }
            operator = ".";
            decimalEntered = false;

            // Agrega el símbolo "=" a la expresión en lblResult2
            updateExpression(" " + "=" + " ");
        } else if (symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0));
            decimalEntered = false;
            expression = "";
            lblResult2.setText("");
        } else if (symbol.equals("Decimal")) {
            if (!decimalEntered) {
                lblResult.setText(lblResult.getText() + separator);
                decimalEntered = true;
                updateExpression(separator);
            }
        } else {

            switch (symbol) {
                case "Plus" -> operator = "+";
                case "Minus" -> operator = "-";
                case "Multiply" -> operator = "*";
                case "Divide" -> operator = "/";
            }
            num1 = Double.parseDouble(lblResult.getText());
            lblResult.setText(String.valueOf(0));
            decimalEntered = false;
            String readableSymbol = symbolMappings.get(symbol);
            updateExpression(" " + readableSymbol + " ");

        }
    }




    @FXML
    public void onDecimalClicked(MouseEvent event) {
        if (!lblResult.getText().contains(separator)) {
            lblResult.setText(lblResult.getText() + separator);
            decimalEntered = true;
            updateExpression(separator); // Actualiza la expresión con el punto decimal
        }
    }


    @FXML
    void onKeyPressed(KeyEvent event) {
        String tecla = event.getText();
        if (!tecla.isEmpty()) {
            // Verificar si la tecla presionada es un dígito
            if (tecla.matches("[0-9]")) {
                // Verifica si ya hay un "0" en la pantalla y reemplázalo si es el caso
                if (lblResult.getText().equals("0")) {
                    lblResult.setText(tecla);
                } else {
                    // Agrega el dígito a los números existentes en la pantalla
                    lblResult.setText(lblResult.getText() + tecla);
                }
            } else if (tecla.equals("+") || tecla.equals("-") || tecla.equals("*") || tecla.equals("/")) {
                // Es un operador, puedes realizar la lógica para operaciones
                // Puedes almacenar el operador y los números ingresados hasta ahora para realizar la operación cuando se presione "="
            } else if (tecla.equals("=")) {
                // Es igual, realiza la operación y muestra el resultado en la pantalla
                // Puedes utilizar la lógica que tengas para evaluar la expresión ingresada
            }
        }
    }

}





