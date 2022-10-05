package com.comp301.a09nonograms.controller;

import com.comp301.a09nonograms.PuzzleLibrary;
import com.comp301.a09nonograms.model.Clues;
import com.comp301.a09nonograms.model.CluesImpl;
import com.comp301.a09nonograms.model.Model;
import com.comp301.a09nonograms.model.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControllerImpl implements Controller {

  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public Clues getClues() {
    int[][] row = new int[model.getHeight()][model.getRowCluesLength()];
    int[][] col = new int[model.getWidth()][model.getColCluesLength()];

    for (int i = 0; i < row.length; i++) {
      row[i] = model.getRowClues(i);
    }

    for (int j = 0; j < col.length; j++) {
      col[j] = model.getColClues(j);
    }

    Clues clues = new CluesImpl(row, col);

    return clues;
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public boolean isShaded(int row, int col) {
    return model.isShaded(row, col);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    return model.isEliminated(row, col);
  }

  @Override
  public void toggleShaded(int row, int col) {
    model.toggleCellShaded(row, col);
  }

  @Override
  public void toggleEliminated(int row, int col) {
    model.toggleCellEliminated(row, col);
  }

  @Override
  public void nextPuzzle() {
    if (model.getPuzzleIndex() == model.getPuzzleCount() - 1) {
      model.setPuzzleIndex(0);
    } else {
      model.setPuzzleIndex(getPuzzleIndex() + 1);
    }
  }

  @Override
  public void prevPuzzle() {
    if (model.getPuzzleIndex() == 0) {
      model.setPuzzleIndex(model.getPuzzleCount() - 1);
    } else {
      model.setPuzzleIndex(getPuzzleIndex() - 1);
    }
  }

  @Override
  public void randPuzzle() {
    int current = model.getPuzzleIndex();
    int rand = (int) (Math.random() * getPuzzleCount());
    while (rand == current) {
      rand = (int) (Math.random() * getPuzzleCount());
    }
    model.setPuzzleIndex(rand);
  }

  @Override
  public void clearBoard() {
    model.clear();
  }

  @Override
  public int getPuzzleIndex() {
    return model.getPuzzleIndex();
  }

  @Override
  public int getPuzzleCount() {
    return model.getPuzzleCount();
  }
}
