package com.comp301.a09nonograms.model;

public class Puzzle {
  private final Clues clues;
  private final Board board;

  public Puzzle(Clues clues) {
    this.clues = clues;
    board = new BoardImpl(clues);
  }

  public Board getBoard() {
    return board;
  }

  public Clues getClues() {
    return clues;
  }
}
