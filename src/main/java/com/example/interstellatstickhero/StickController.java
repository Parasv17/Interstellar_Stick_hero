package com.example.interstellatstickhero;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class StickController {
    @FXML
    private Rectangle myRectangle;

    private boolean isMousePressed = false;

    public void incHeight(MouseEvent e) {
        myRectangle.setHeight(myRectangle.getHeight()+5);


    }
    public void growStick(MouseEvent e){

    }
    public void stopStick(MouseEvent e){

    }


}
