package com.example.interstellatstickhero;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isFlipped = false;

    public void switchtoMainScreen() throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage = (Stage) gamePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    public void switchtoHomeScreen() throws IOException {
        root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        stage = (Stage) gamePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    public void switchtoPauseScreen(MouseEvent event) throws IOException {
        if(!isPaused){pauseGame();}
    }
    private ArrayList<Timeline> allAnims= new ArrayList<Timeline>();
    private ArrayList<TranslateTransition> alltrans= new ArrayList<TranslateTransition>();


    private static final Random random = new Random();
    private  boolean isPaused=false;

    @FXML
    private Pane gamePane;

    private boolean gameover=false;
//

    private Rectangle stickbuff;


    @FXML
    private Rectangle stick;
    private boolean canrotate=false;
    private Stick redStick;

    private int clickcount;

    private List<Rectangle> sticks;
    private boolean isIncreasing;
    private int score=0;
    private ScheduledService<Void> collisionCheckService;

    private int cherry=0;


    public GameController() {
        sticks = new ArrayList<>();
    }
    @FXML
    private ImageView Hero;
    private StickHero jaadu;

    private boolean canclick;

    @FXML
    private Label scoreCount;
    @FXML
    private Label cherryCount;
    private Cherries spawnedCherry;
    public static  boolean shouldLoad;
    public void initialize() {
        isPaused=false;
        pillarStart();
        jaadu = new StickHero(Hero, this);
        redStick = new Stick(stick, this);
        spawnRandomCherries();
        if(shouldLoad){
            loadGame();
        }
        collisionCheckService = new ScheduledService<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        checkForCherryCollision();
                        return null;
                    }
                };
            }
        };

        collisionCheckService.setPeriod(Duration.millis(100));
        collisionCheckService.start();



        gamePane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setupKeyPressHandler(newValue);
            }
        });
        resetAssets();
        updateScore();
        updateCherrycount();
    }


    private void setupKeyPressHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                System.out.println("Space key pressed");
                flipPlayer();
            }
        });
    }


    public void handleMouseClicked(MouseEvent event) {
        if(canrotate){
            Rotate rotate = new Rotate();
            rotate.setPivotY(jaadu.getJadu().getY() + jaadu.getJadu().getFitHeight());
            jaadu.getJadu().getTransforms().add(rotate);
            rotate.setAngle(180);

    }
    }


    public void handleMousePressed(MouseEvent event) {
        canrotate=false;
        stickbuff = stick;
        isIncreasing = true;
        Thread increaseStickThread = new Thread(() -> {
            while (isIncreasing && clickcount==0 && !gameover) {
                increaseStickLength();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        increaseStickThread.start();
    }

    public void handleMouseReleased(MouseEvent event) throws IOException {
        isIncreasing = false;
        if(clickcount==0)
            dropStickAndAddNew();
        clickcount=+5;
    }

    private void increaseStickLength() {
        redStick.getStickRectangle().setOpacity(1);
        double newY = redStick.getStickRectangle().getY() - 5;
        redStick.getStickRectangle().setY(newY);
        redStick.getStickRectangle().setHeight(stick.getHeight() + 5);
    }

    private void dropStickAndAddNew() throws IOException {
        if(!gameover){
        Rotate rotate = new Rotate();
        rotate.setPivotY(redStick.getStickRectangle().getY() + redStick.getStickRectangle().getHeight());
        redStick.getStickRectangle().getTransforms().add(rotate);
        rotate.setAngle(0);
        Timeline timeline = new Timeline();
        KeyFrame rotateKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0));
        KeyFrame rotateEndKeyFrame = new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 90));
        timeline.getKeyFrames().addAll(rotateKeyFrame, rotateEndKeyFrame);
        allAnims.add(timeline);
        timeline.setOnFinished(event -> allAnims.remove(timeline));
        timeline.play();
        movehero();

}

    }

    private void addNewStick() {
        Rectangle newStick = new Rectangle(redStick.getStickRectangle().getWidth(), redStick.getStickRectangle().getHeight());
        newStick.setY(redStick.getStickRectangle().getY());
    }




    private Pillar p1;
    private Pillar p2;

    public Pane getGamePane() {
        return gamePane;
    }

    public void setGamePane(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public void pillarStart(){
        p1= addNewPillar(0,100,100);
        p2= addNewPillar(250,200,Pillar.getRandomWidth(70,150));


    }

    public ArrayList<Timeline> getAllAnims() {
        return allAnims;
    }

    public ArrayList<TranslateTransition> getAlltrans() {
        return alltrans;
    }

    public void setAlltrans(TranslateTransition tl) {
        this.alltrans.add(tl);
    }

    public void setAllAnims(Timeline tl) {
        this.allAnims.add(tl);
    }

    public void remAnims(Timeline tl) {
        this.allAnims.remove(tl);
    }
    private Pillar addNewPillar(int dist, int ms, int w) {
        Pillar newPillar = new Pillar(dist, ms,w);
        gamePane.getChildren().add(newPillar.getPillarRectangle());
        return newPillar;
    }

    private void movehero() throws IOException {
        double distanceToWalk = p2.getPillarRectangle().getLayoutX()+ p2.getPillarRectangle().getWidth()-jaadu.getJadu().getFitWidth();
        System.out.println("shoul dmmove to"+distanceToWalk);
        if(!isStickAlignedWithNextPillar(false)){

            distanceToWalk=jaadu.getJadu().getLayoutX()+redStick.getStickRectangle().getHeight()+(jaadu.getJadu().getFitWidth())/2;

        }
        jaadu.moveHorizontally(distanceToWalk, 1);



    }

    public void checkStickAlignment() throws IOException {
        if(isStickAlignedWithNextPillar(true)) {
            removeOldPillar();
            move2ndPillar();
            moveHeroTo2ndPillar();
        } else {
            fallDown();
//
        }
    }



    private void fallDown() {
        TranslateTransition fallTransition = new TranslateTransition(Duration.millis(400), jaadu.getJadu());
        fallTransition.setToY(gamePane.getHeight());
        alltrans.add(fallTransition);
        fallTransition.setOnFinished(event -> alltrans.remove(fallTransition));
        fallTransition.play();
    }


    private boolean isStickAlignedWithNextPillar(boolean bol) throws IOException {
        double p1RightEdge = p1.getPillarRectangle().getLayoutX() + p1.getPillarRectangle().getWidth();
        double p2LeftEdge = p2.getPillarRectangle().getLayoutX();
        double p2RightEdge = p2LeftEdge + p2.getPillarRectangle().getWidth();
        double stickLength = redStick.getStickRectangle().getHeight();
        if (isFlipped && ((isPlayerTouchingPillar(p1) || isPlayerTouchingPillar(p2))) && cherry < 2) {
            System.out.println("Player is flipped and touching a pillar.");
            if (bol) {
                saveHighScore();
                new GameOverPopup(gamePane, this, score, cherry);
                gameover = true;
                return false;
            }
        }
        else if(isFlipped && ((isPlayerTouchingPillar(p1) || isPlayerTouchingPillar(p2))) && cherry>=2){
            cherry-=2;
            updateCherrycount();
            return  true;
        }
        if (p2LeftEdge - p1RightEdge <= stickLength && p2RightEdge - p1RightEdge > stickLength) {
            System.out.println("Stick is aligned with the next pillar.");
            if (bol) {
                score++;
                updateScore();
            }
            return true;
        } else if (cherry >= 2) {
            if (bol) {
                cherry -= 2;
                updateCherrycount();
                return true;
            }
            else {
                return false;
            }
        }
        else {
            System.out.println("Stick is not aligned with the next pillar.");
            if (bol) {
                isPaused = true;
                saveHighScore();
                new GameOverPopup(gamePane, this, score, cherry);
                gameover = true;
                return false;
            } else {
                return false;
            }
        }
    }

    private boolean isPlayerTouchingPillar(Pillar pillar) {
        double playerLeftEdge = jaadu.getJadu().getLayoutX();
        double playerRightEdge = playerLeftEdge + jaadu.getJadu().getFitWidth();
        double pillarLeftEdge = pillar.getPillarRectangle().getLayoutX();
        double pillarRightEdge = pillarLeftEdge + pillar.getPillarRectangle().getWidth();
        return (playerRightEdge >= pillarLeftEdge && playerLeftEdge <= pillarRightEdge);
    }



    private void moveHeroTo2ndPillar() {
        ImageView hero = jaadu.getJadu();
        redStick.getStickRectangle().setLayoutX(0.0);
        redStick.getStickRectangle().setLayoutX((double) (p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth()));
        double newLayoutX =(double) p2.getPillarRectangle().getWidth()-jaadu.getJadu().getFitWidth();
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(hero.layoutXProperty(), newLayoutX);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        allAnims.add(timeline);
        timeline.setOnFinished(event -> allAnims.remove(timeline));
        timeline.play();
    }
    private  void resetAssets(){
        jaadu.getJadu().setX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
        redStick.getStickRectangle().setLayoutX(p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth());
    }
    public void removeOldPillar() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p1.getPillarRectangle());
        transition.setByX(-500);
        alltrans.add(transition);
        transition.play();
        transition.setOnFinished(event -> {
                    alltrans.remove(transition);
                    gamePane.getChildren().remove(p1.getPillarRectangle());
                }
        );
    }
private void move2ndPillar() {
    double distanceToMove = -p2.getPillarRectangle().getLayoutX();
    KeyValue movePillar = new KeyValue(p2.getPillarRectangle().layoutXProperty(), 0);
    KeyValue widthPillar = new KeyValue(p2.getPillarRectangle().widthProperty(), p1.getPillarRectangle().getWidth());
    KeyFrame frame = new KeyFrame(Duration.seconds(2), movePillar, widthPillar);
    Timeline timeline = new Timeline(frame);
    allAnims.add(timeline);
    timeline.setOnFinished(event -> {
        createNewPillar();
        allAnims.remove(timeline);
        canrotate = false;
    });
    timeline.play();
}
    public void resetStick() {
        redStick.getStickRectangle().setOpacity(0);
        redStick.getStickRectangle().getTransforms().clear();
        try {
            if (spawnedCherry != null) {
                gamePane.getChildren().remove(spawnedCherry.getChImg());
                spawnedCherry = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clickcount=0;
    }
    public void resetStick2(){
        redStick.getStickRectangle().setLayoutX(0.0);
        redStick.getStickRectangle().setLayoutX((double) (p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth()));
        decreaseStickHeightFromTop();
        PauseTransition pause = new PauseTransition(Duration.millis(900));
        pause.setOnFinished(event -> {
            spawnRandomCherries();
            clickcount=0;

        });
        pause.play();
    }



    private void createNewPillar() {
        p1 =p2;
        int dist = random.nextInt(350-140 + 1) + 140 ;// Calculate the distance for the new pillar
        p2 = new Pillar(dist, 300,Pillar.getRandomWidth(70,150)); // Adjust duration as needed
        gamePane.getChildren().add(p2.getPillarRectangle());
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(event -> {
            resetStick2();

        });
        pause.play();
    }

    private void decreaseStickHeightFromTop() {
        new Thread(() -> {
            while (redStick.getStickRectangle().getHeight() > 0) {
                double decrement = 1;
                double newHeight = redStick.getStickRectangle().getHeight() - decrement;
                double newY = redStick.getStickRectangle().getY() + decrement;

                Platform.runLater(() -> {
                    redStick.getStickRectangle().setHeight(newHeight);
                    redStick.getStickRectangle().setY(newY);
                });
                try {
                    Thread.sleep(1); // Small delay to slow down the height reduction
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

           // Platform.runLater(() -> clickcount = 0); // Reset click count on JavaFX thread
        }).start();
    }




    public void flipPlayer() {
        isFlipped = !isFlipped;

        Rotate rotate = new Rotate();
        double pivotX = jaadu.getJadu().getX() + jaadu.getJadu().getFitWidth() / 2;
        double pivotY = jaadu.getJadu().getY() + jaadu.getJadu().getFitHeight();

        rotate.setPivotX(pivotX);
        rotate.setPivotY(pivotY);

        jaadu.getJadu().getTransforms().add(rotate);
        rotate.setAngle(180);
    }

    public void pauseGame(){
        isPaused=true;
        for (TranslateTransition tl:alltrans
             ) {
            tl.pause();

        }
        for (Timeline tl:allAnims
             ) {tl.pause();

        }
        new PauseGamePopup(gamePane,this,score,cherry);
        collisionCheckService.cancel();

        // show a pop up
    }

    public void resumeGame(){
        isPaused=false;
        for (TranslateTransition tl:alltrans
        ) {

            tl.play();

        }
        for (Timeline tl:allAnims
        ) {tl.play();

        }
        if (!collisionCheckService.isRunning()) {
            collisionCheckService.restart();
        }
    }
    private void updateScore(){

        scoreCount.setText(String.valueOf(score));
    }



    private void spawnRandomCherries(){
        Double upper=p2.getPillarRectangle().getLayoutX();
        Double lower=104.00;
        System.out.println("bound "+(upper-lower+1));
        Double chlayx= random.nextDouble(upper-lower+1)+lower;
//        System.out.println("layout x "+chlayx);
        spawnedCherry= new Cherries(this,chlayx-50.00);
        checkForCherryCollision();



//        Double chlayx= random.nextDouble(-+1)+p1.getPillarRectangle().getLayoutX() + p1.getPillarRectangle().getWidth();
//        spawnedCherry= new Cherries(this,chlayx);


    }

    private void checkForCherryCollision() {
        Platform.runLater(() -> {
            if (spawnedCherry != null) {
                ImageView cherryImageView = spawnedCherry.getChImg();
                if (jaadu.getJadu().getBoundsInParent().intersects(cherryImageView.getBoundsInParent())) {
                    gamePane.getChildren().remove(cherryImageView); // Remove from the scene
                    spawnedCherry = null; // Clear the reference
                    cherry++; // Increment cherry count
                    updateCherrycount(); // Update the count display
                }
            }
        });
    }

    private void updateCherrycount() {
        Platform.runLater(() -> {
            cherryCount.setText(String.valueOf(cherry));
        });
    }
    private SaveGameState savedGame;
    public static int hScore;
    public void saveGame() throws IOException, ClassNotFoundException{
        savedGame = new SaveGameState(score, cherry);
        ObjectOutputStream savedFile= null;
        try{
            savedFile= new ObjectOutputStream(new FileOutputStream("savegame.txt"));
            savedFile.writeObject(savedGame);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        finally {
            savedFile.close();
        }

//        try (ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream("savegame.txt"))) {
//            savedFile.writeObject(savedGame);
//            System.out.println("Game saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error saving game: " + e.getMessage());
//            e.printStackTrace();
//        }
    }
    public void loadGame() {
        try (ObjectInputStream loadedFIle = new ObjectInputStream(new FileInputStream("savegame.txt"))) {

            SaveGameState loadedState = (SaveGameState) loadedFIle.readObject();


            this.score = loadedState.getScore();
            this.cherry = loadedState.getCherryCount();


            updateScore();
            updateCherrycount();


            System.out.println("Game loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found during game load: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void saveHighScore() throws IOException {
        if(score>hScore){
            hScore=score;
            FileWriter fwt= new FileWriter("hscore.txt");
            fwt.append(String.valueOf(hScore));
            System.out.println("saved hsc "+ hScore);
            fwt.close();
//
//

        }


    }



}






