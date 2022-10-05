package com.comp301.a09nonograms.model;

public class CluesImpl implements Clues {

  private final int[][] rowClues;
  private final int[][] colClues;

  public CluesImpl(int[][] rowClues, int[][] colClues) {
    if (rowClues == null || colClues == null) {
      throw new IllegalArgumentException();
    }
    this.rowClues = rowClues;
    this.colClues = colClues;
  }

  @Override
  public int getWidth() {
    return colClues.length;
  }

  @Override
  public int getHeight() {
    return rowClues.length;
  }

  @Override
  public int[] getRowClues(int index) {
    if (index <= rowClues.length && index >= 0) {
      for (int i = 0; i < rowClues.length; i++) {
        if (i == index) {
          return rowClues[i];
        }
      }
    }
    throw new IllegalArgumentException();
  }

  @Override
  public int[] getColClues(int index) {
    if (index <= colClues.length && index >= 0) {
      for (int i = 0; i < colClues.length; i++) {
        if (i == index) {
          return colClues[i];
        }
      }
    }
    throw new IllegalArgumentException();
  }

  @Override
  public int getRowCluesLength() {
    return rowClues[0].length;
  }

  @Override
  public int getColCluesLength() {
    return colClues[0].length;
  }
}
