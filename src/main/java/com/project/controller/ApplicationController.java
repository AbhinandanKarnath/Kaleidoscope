package com.project.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class ApplicationController {

    int lastX , lastY , oLastX , oLastY;

    int symmetry = 6;
    int angle = 60;
    double brushS = 1.0;
    GraphicsContext gc;

    @FXML
    TextField brushSize ;

    @FXML
    private Label symmetryLabel;

    @FXML
    private Canvas drawCanvas;

    @FXML
    private ColorPicker paintColour ;

    @FXML
    private Color color = Color.rgb(255,165,0);

    @FXML
    private ToggleButton full ;

    @FXML
    private Line lineX , lineY;

    @FXML
    private void clear()
    {
        gc = drawCanvas.getGraphicsContext2D();
        gc.restore();
        gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
    }

    @FXML
    private void changeColour()
    {
        color = paintColour.getValue();
    }


    @FXML
    private void increase()
    {
        symmetry = symmetry + 1;
        symmetryLabel.setText(""+symmetry);
        angle = 360 / symmetry;
        System.out.println(angle);
    }

    @FXML
    private void decrease()
    {
        symmetry = symmetry - 1;
        symmetryLabel.setText(""+symmetry);
        angle = 360 / symmetry;
    }
    @FXML
    private void onPressed(MouseEvent event)
    {
        lastX = (int) event.getX();
        lastY = (int) event.getY();

        oLastX = (int)drawCanvas.getWidth() - lastX;
        oLastY= (int)drawCanvas.getHeight() - lastY;

    }

    @FXML
    private void changeSize()
    {
        try {
            String b = brushSize.getText();
            if(b.equals("") || b.equals("0"))
            {
                brushS = 1;
                return;
            }
            brushS = Double.parseDouble(b);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            brushSize.setText("1");
            brushS = 1;
        }
        System.out.println(brushS);
    }

    @FXML
    private void addGrid()
    {
        lineX.visibleProperty().setValue(!lineX.isVisible());
        lineY.visibleProperty().setValue(!lineY.isVisible());
    }

    @FXML
    public void onDrag(MouseEvent event) {

        gc = drawCanvas.getGraphicsContext2D();

        int size = (int) drawCanvas.getWidth();
        gc.setLineWidth(brushS);

        gc.translate(size/2,size/2);

        int xPos = (int) event.getX();
        int yPos = (int) event.getY();

        int x = xPos - size / 2;
        int y = yPos - size / 2;

        int px = lastX - size / 2;
        int py = lastY - size / 2;

        gc.setStroke(color);

        for(int i = 0 ; i < symmetry ; i++)
        {
            gc.rotate(angle);
            gc.strokeLine(x, y, px, py);
            gc.scale(1,1);

            if(!full.isSelected())
            {
                gc.strokeLine(y, x , py ,px);
            }
        }

        lastX = xPos;
        lastY = yPos;

        gc.rotate(-(angle*symmetry));
        gc.translate(-size / 2 , -size / 2);

    }

    @FXML
    private void saveAsPNG()
    {
        try {

            Stage stage = new Stage();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Location");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

            File file = fileChooser.showSaveDialog(stage);

            if(file != null)
            {
                javafx.scene.image.WritableImage image = drawCanvas.snapshot(null,null);

                ImageIO.write(javafx.embed.swing.SwingFXUtils.fromFXImage(image, null), "png", file);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}