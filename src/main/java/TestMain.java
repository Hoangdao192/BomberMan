import Utils.MinHeap;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TestMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(10);
        Scene scene = new Scene(root, 400,400);
        Label label = new Label("X");
        label.setFont(Font.font("Arial", 23));
        root.getChildren().add(label);
        DoubleProperty doubleProperty = root.prefWidthProperty();
        label.layoutXProperty().bind(doubleProperty);
        stage.setScene(scene);
        stage.show();
    }
}
