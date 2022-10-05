package com.comp301.a09nonograms.model;

public class BoardImpl implements Board {

  private final int height;
  private final int width;
  private final int[][] grid;

  public BoardImpl(Clues clues) {
    this.height = clues.getHeight();
    this.width = clues.getWidth();
    this.grid = new int[height][width];
  }

  @Override
  public boolean isShaded(int row, int col) {
    if (row >= height || row < 0 || col >= width || col < 0) {
      throw new IllegalArgumentException();
    }
    return grid[row][col] == 1;
  }

  @Override
  public boolean isEliminated(int row, int col) {
    if (row >= height || row < 0 || col >= width || col < 0) {
      throw new IllegalArgumentException();
    }
    return grid[row][col] == 2;
  }

  @Override
  public boolean isSpace(int row, int col) {
    if (row >= height || row < 0 || col >= width || col < 0) {
      throw new IllegalArgumentException();
    }
    return grid[row][col] == 0;
  }

  @Override
  public void toggleCellShaded(int row, int col) {
    if (row >= height || row < 0 || col >= width || col < 0) {
      throw new IllegalArgumentException();
    }
    if (grid[row][col] != 1) {
      grid[row][col] = 1;
    } else {
      grid[row][col] = 0;
    }
  }

  @Override
  public void toggleCellEliminated(int row, int col) {
    if (row >= height || row < 0 || col >= width || col < 0) {
      throw new IllegalArgumentException();
    }
    if (grid[row][col] != 2) {
      grid[row][col] = 2;
    } else {
      grid[row][col] = 0;
    }
  }

  @Override
  public void clear() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j] = 0;
      }
    }
  }
}
