package com.comp301.a09nonograms.view;

import com.comp301.a09nonograms.controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.awt.*;

public class ControlView implements FXComponent {

  private final Controller controller;

  public ControlView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    Button next = new Button();
    Button prev = new Button();
    Button clear = new Button();
    Button rand = new Button();

    // next
    next.setText("Next Puzzle");
    next.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    next.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent mouseEvent) {
            controller.nextPuzzle();
          }
        });

    // prev
    prev.setText("Previous Puzzle");
    prev.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    prev.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent mouseEvent) {
            controller.prevPuzzle();
          }
        });

    // clear
    clear.setText("Clear Board");
    clear.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    clear.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent mouseEvent) {
            controller.clearBoard();
          }
        });

    // rand
    rand.setText("Random Puzzle");
    rand.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
    rand.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent mouseEvent) {
            controller.randPuzzle();
          }
        });
    layout.getChildren().add(next);
    layout.getChildren().add(prev);
    layout.getChildren().add(clear);
    layout.getChildren().add(rand);
    layout.setSpacing(10);
    return layout;
  }
}
