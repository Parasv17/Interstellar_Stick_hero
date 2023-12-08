package com.example.interstellatstickhero.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class StickController {
    @FXML
    private Rectangle myRectangle;

    private boolean isMousePressed = false;

    public void incHeight(MouseEvent e) {
//        while()
        myRectangle.setHeight(myRectangle.getHeight()+5);


    }
    public void growStick(MouseEvent e){

    }
    public void stopStick(MouseEvent e){

    }


}
