package com.example.myvideoplayer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.OpenOption;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    MediaPlayer player;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playBtn;

    @FXML
    private Button preBtn;

    @FXML
    private Button nxtBtn;
    @FXML
    private Slider timeSlider;

    @FXML
    private Slider volSlider;


    @FXML
    void opensongmenu(ActionEvent event) {
        try {
            System.out.println("Open file clicked");
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);


            Media m = new Media(file.toURI().toURL().toString());

            if (player != null) {
                player.dispose();
            }
            player = new MediaPlayer(m);

            mediaView.setMediaPlayer(player);

            player.setOnReady(() -> {
                try {
                    timeSlider.setMin(0);
                    timeSlider.setMax(player.getMedia().getDuration().toMinutes());
                    timeSlider.setValue(0);

                    playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/ply.jpg"))));

//                volSlider.setMin(0);
//                volSlider.setMax(player.getVolume());
                    volSlider.setValue(player.getVolume() * 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d = player.getCurrentTime();
                    timeSlider.setValue(d.toMinutes());
                }
            });

            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if (timeSlider.isPressed()) {
                        double val = timeSlider.getValue();
                        player.seek(new Duration(val * 60 * 1000));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        volSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volSlider.getValue() / 100);
            }
        });


    }


    @FXML
    void play(ActionEvent event) {
        try {
            MediaPlayer.Status status = player.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                player.pause();
//        playBtn.setText("Play");
                playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/ply.jpg"))));
            } else {
                player.play();
//        playBtn.setText("Pause");
                playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pse.jpg"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/ply.jpg"))));
            preBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/prev.jpg"))));
            nxtBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/fwd.jpg"))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void preBtnClick(ActionEvent event) {
        Double d = player.getCurrentTime().toSeconds();
        d = d - 10;
        player.seek(new Duration(d * 1000));

    }

    @FXML
    void nextBtnClick(ActionEvent event) {
        Double d = player.getCurrentTime().toSeconds();
        d = d + 10;
        player.seek(new Duration(d * 1000));
    }

    @FXML
    void exitSongmenu(ActionEvent event) {
        System.out.println("Exit song clicked");
        if (player != null) {
            player.dispose();
        }
    }
    @FXML
    void playSng(ActionEvent event) {
        try{
            System.out.println("ply sng clicked");
            MediaPlayer.Status status = player.getStatus();
            if(status != MediaPlayer.Status.PLAYING){
                player.play();
                playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pse.jpg"))));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void pauseSongMenu(ActionEvent event) {
    System.out.println("ex song clicked");
        try {
            MediaPlayer.Status status = player.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                player.pause();
//        playBtn.setText("Play");
                playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/ply.jpg"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void knowAbout(ActionEvent event) {
//    System.out.println("s");
        try{

            javafx.scene.text.Text seclabel = new Text("We provide a platform for the purpose of playing various media files ") ;
            StackPane secstacklyuot = new StackPane();
            secstacklyuot.getChildren().add(seclabel);

            Scene secondScene = new Scene(secstacklyuot,500, 300);
//            Image img = new Image(getClass().getResource("ply.jpg"));

            Stage nW = new Stage();
            nW.setTitle("About the Media Player");
            nW.setScene(secondScene);

            nW.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

