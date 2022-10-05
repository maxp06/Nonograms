package com.comp301.a09nonograms.view;

import com.comp301.a09nonograms.controller.Controller;
import com.comp301.a09nonograms.model.Puzzle;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final Controller controller;

  public View(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    // MessageView
    MessageView messageView = new MessageView(controller);
    layout.getChildren().add(messageView.render());

    // PuzzleView
    PuzzleView puzzleView = new PuzzleView(controller);
    layout.getChildren().add(puzzleView.render());

    // ControlView
    ControlView controlView = new ControlView(controller);
    layout.getChildren().add(controlView.render());

    layout.setSpacing(10);
    return layout;
  }
}
