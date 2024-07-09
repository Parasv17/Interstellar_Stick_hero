package com.example.interstellatstickhero;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherries{

    private GameController gameController;
    private ImageView chImg;

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public ImageView getChImg() {
        return chImg;
    }

    public void setChImg(ImageView chImg) {
        this.chImg = chImg;
    }

    public Double getLayX() {
        return layX;
    }

    public void setLayX(Double layX) {
        this.layX = layX;
    }

    private Double layX;

    public Cherries(GameController gameController, double layoutX) {
        this.gameController = gameController;
        this.layX= layoutX;
        spawnCherry();


    }
    private void spawnCherry(){
        chImg= new ImageView(new Image(Main.class.getResourceAsStream("cherry.png")));
        chImg.setLayoutY(275.00);
        chImg.setFitHeight(35);
        chImg.setFitWidth(46);
        chImg.setLayoutX(layX);
        gameController.getGamePane().getChildren().add(chImg);

    }


}