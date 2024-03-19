package com.project.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Kaleidoscope");
        stage.getIcons().add(new Image("/com/project/Kaleidoscope.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try
        {
            launch();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
        }
    }
}