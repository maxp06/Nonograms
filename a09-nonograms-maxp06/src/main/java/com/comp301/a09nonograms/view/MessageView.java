package com.comp301.a09nonograms.view;

import com.comp301.a09nonograms.controller.Controller;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MessageView implements FXComponent {

  private final Controller controller;

  public MessageView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    if (controller.isSolved()) {
      Label label = new Label("Success");
      label.setFont(Font.font(20));
      label.setStyle("-fx-text-fill: red;");
      layout.getChildren().add(label);
    } else {
      Label label = new Label("");
      layout.getChildren().add(label);
    }
    Label total = new Label("Total Puzzles: " + controller.getPuzzleCount());
    Label current = new Label("Current Puzzle: " + (controller.getPuzzleIndex() + 1));

    layout.getChildren().add(total);
    layout.getChildren().add(current);
    return layout;
  }
}
