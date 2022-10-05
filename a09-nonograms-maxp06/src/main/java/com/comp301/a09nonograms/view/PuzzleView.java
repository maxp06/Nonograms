package com.comp301.a09nonograms.view;

import com.comp301.a09nonograms.controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class PuzzleView implements FXComponent {

  private final Controller controller;

  public PuzzleView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane layout = new GridPane();
    for (int i = 0; i < controller.getClues().getWidth(); i++) {
      for (int j = 0; j < controller.getClues().getHeight(); j++) {
        Button button = new Button();
        button.setPrefSize(40, 40);
        int tempI = i;
        int tempJ = j;

        if (controller.isEliminated(tempJ, tempI)) {
          button.setStyle(
              "-fx-border-color: blue; -fx-background-color: white; -fx-border-width: 2px;");
          button.setText("X");
        } else if (controller.isShaded(tempJ, tempI)) {
          button.setStyle(
              "-fx-border-color: blue; -fx-background-color: black; -fx-border-width: 2px;");
        } else {
          button.setStyle(
              "-fx-border-color: blue; -fx-background-color: white; -fx-border-width: 2px;");
        }
        button.setOnMouseClicked(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                  controller.toggleShaded(tempJ, tempI);
                } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                  controller.toggleEliminated(tempJ, tempI);
                }
              }
            });
        layout.add(button, i, j);
      }
    }

    GridPane rowClues = new GridPane();
    rowClues.setAlignment(Pos.CENTER);
    rowClues.setHgap(10);
    rowClues.setVgap(23);
    for (int i = 0; i < controller.getClues().getHeight(); i++) {
      int[] clue = controller.getClues().getRowClues(i);
      for (int j = 0; j < clue.length; j++) {
        if (clue[j] != 0) {
          Label label = new Label(String.valueOf(clue[j]));
          rowClues.add(label, j, i);
        } else {
          Label label = new Label("");
          rowClues.add(label, j, i);
        }
      }
    }
    GridPane colClues = new GridPane();
    colClues.setAlignment(Pos.CENTER);
    colClues.setVgap(7);
    colClues.setHgap(32);
    for (int i = 0; i < controller.getClues().getWidth(); i++) {
      int[] clue = controller.getClues().getColClues(i);
      for (int j = 0; j < clue.length; j++) {
        if (clue[j] != 0) {
          Label label = new Label(String.valueOf(clue[j]));
          colClues.add(label, i, j);
        } else {
          Label label = new Label("");
          colClues.add(label, i, j);
        }
      }
    }

    GridPane overall = new GridPane();
    overall.add(rowClues, 0, 1);
    overall.add(colClues, 1, 0);
    overall.add(layout, 1, 1);
    return overall;
  }
}
