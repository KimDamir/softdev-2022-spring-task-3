package javafx;

import controller.GameListener;
import core.Logic;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

import java.util.Timer;
import java.util.TimerTask;

public class Square extends Button {
    private final String squareStyle = "-fx-background-color: transparent; -fx-background-image: url('/Square.png');";
    private final String clickedSquareStyle = "-fx-background-color: transparent; -fx-background-image: url('/clickedSquare.png');";
    private final String glowingSquareStyle = "-fx-background-color: transparent; -fx-background-image: url('/glowingSquare.png');";
    private final String clickedGlowingSquareStyle = "-fx-background-color: transparent; -fx-background-image: url('/clickedGlowingSquare.png');";
    private Timer fadeTimer;
    private final GameListener listener;

    public Square(double x, double y, GameListener listener) {
        this.listener = listener;
        setPrefSize(49, 49);
        setLayoutX(x);
        setLayoutY(y);
        setFocusTraversable(false);
        normalState();
    }

    public void normalState() {
        if (fadeTimer != null) fadeTimer.cancel();
        setStyle(squareStyle);
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setStyle(clickedSquareStyle);
                setPrefHeight(45);
                setLayoutY(getLayoutY() + 4);
                listener.miss();
            }
        });
        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setStyle(squareStyle);
                setPrefHeight(49);
                setLayoutY(getLayoutY() - 4);
            }
        });
    }

    public void glow(int time) {
        setStyle(glowingSquareStyle);
        this.fadeTimer = new Timer();
        TimerTask fadeTask = new TimerTask() {
            @Override
            public void run() {
                listener.miss();
                normalState();
            }
        };
        fadeTimer.schedule(fadeTask, time * 1000L);
        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setStyle(glowingSquareStyle);
                setPrefHeight(49);
                setLayoutY(getLayoutY() - 4);
            }
        });
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setStyle(clickedGlowingSquareStyle);
                setPrefHeight(45);
                setLayoutY(getLayoutY() + 4);
                listener.click();
                normalState();
            }
        });
    }
}
