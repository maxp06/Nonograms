package com.comp301.a09nonograms.model;

import com.comp301.a09nonograms.PuzzleLibrary;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private int index;
  private final List<Puzzle> puzzles;
  private Clues clues;
  private Board board;
  private final List<ModelObserver> activeModelObservers;

  public ModelImpl(List<Clues> clues) {
    this.puzzles = new ArrayList<>();
    for (int i = 0; i < clues.size(); i++) {
      puzzles.add(new Puzzle(clues.get(i)));
    }
    this.index = 0;
    this.clues = clues.get(0);
    this.board = puzzles.get(0).getBoard();
    this.activeModelObservers = new ArrayList<>();
  }

  @Override
  public boolean isShaded(int row, int col) {
    return board.isShaded(row, col);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    return board.isEliminated(row, col);
  }

  @Override
  public boolean isSpace(int row, int col) {
    return board.isSpace(row, col);
  }

  @Override
  public void toggleCellShaded(int row, int col) {
    board.toggleCellShaded(row, col);
    for (ModelObserver observer : activeModelObservers) {
      observer.update(this);
    }
  }

  @Override
  public void toggleCellEliminated(int row, int col) {
    board.toggleCellEliminated(row, col);
    for (ModelObserver observer : activeModelObservers) {
      observer.update(this);
    }
  }

  @Override
  public void clear() {
    board.clear();
    for (ModelObserver observer : activeModelObservers) {
      observer.update(this);
    }
  }

  @Override
  public int getWidth() {
    return clues.getWidth();
  }

  @Override
  public int getHeight() {
    return clues.getHeight();
  }

  @Override
  public int[] getRowClues(int index) {
    return clues.getRowClues(index);
  }

  @Override
  public int[] getColClues(int index) {
    return clues.getColClues(index);
  }

  @Override
  public int getRowCluesLength() {
    return clues.getRowCluesLength();
  }

  @Override
  public int getColCluesLength() {
    return clues.getColCluesLength();
  }

  @Override
  public int getPuzzleCount() {
    return puzzles.size();
  }

  @Override
  public int getPuzzleIndex() {
    return index;
  }

  @Override
  public void setPuzzleIndex(int index) {
    if (index >= puzzles.size() || index < 0) {
      throw new IllegalArgumentException();
    }
    this.index = index;
    this.clues = puzzles.get(index).getClues();
    this.board = puzzles.get(index).getBoard();
    for (ModelObserver observer : activeModelObservers) {
      observer.update(this);
    }
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    activeModelObservers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    activeModelObservers.remove(observer);
  }

  @Override
  public boolean isSolved() {
    return correctNumber() && checkRowSpacing2() && checkColSpacing2();
  }

  public boolean checkRowSpacing() {
    int[] clue;
    for (int i = 0; i < clues.getHeight(); i++) { // loops through all row clues
      boolean flag = true;
      boolean flag2 = false;
      clue = getRowClues(i); // sets clue equal to a row clue
      int lenClue = 0;
      for (Integer x : clue) {
        if (x != 0) {
          lenClue++; // 2
        }
      }
      int counter = 0;
      if (lenClue != 0) {
        for (int j = 0; j < clues.getWidth(); j++) { // loops through each column (5)
          if (flag) {
            while (board.isSpace(i, j)) {
              j++;
            }
            while (board.isShaded(i, j)) {
              if (j + 1 < clues.getWidth()) {
                j++;
                flag2 = true;
              } else {
                break;
              }
            }
            if (flag2 && j != clues.getWidth() - 1) {
              j--;
            }
            flag = false;
            counter++;
          } else {
            if (counter != lenClue) {
              if (!board.isSpace(i, j)) {
                return false;
              }
              while (board.isSpace(i, j)) {
                j++;
              }
              while (board.isShaded(i, j)) {
                if (j + 1 < clues.getWidth()) {
                  j++;
                } else {
                  break;
                }
              }
              counter++;
            }
          }
        }
      }
      if (counter != lenClue) {
        return false;
      }
    }
    return true;
  }

  public boolean checkRowSpacing2() {
    int[] clueIn;
    for (int i = 0; i < clues.getHeight(); i++) { // loops through all row clues
      // step 1: get positive clues for the row i
      clueIn = getRowClues(i); // sets clue equal to a row clue
      ArrayList<Integer> listClues = new ArrayList<Integer>();
      for (Integer x : clueIn) // (3, 0, 1)
      {
        if (x > 0) listClues.add(x); // 2
      }
      // step 2: verify each positive clue
      int j = 0;
      for (int iClue = 0; iClue < listClues.size(); iClue++) // for each positive clue
      {
        // verify bound for j
        if (j >= clues.getWidth()) return false; // stop and return: hit the bound already.
        // scan all columns
        int clue = listClues.get(iClue); // first 3, then 1
        int counter = 0; // counter for i-th clue
        for (; j < clues.getWidth() && counter < clue; j++) {
          if (board.isSpace(i, j)) {
            if (counter == 0) continue;
            else return false; // stop and return: only partial match
          } else counter++;
        }
        // determine true/false
        if (counter != clue) return false; // stop and return: NO match
        else if (iClue < listClues.size() - 1) // not the final clue yet
        {
          if (j >= clues.getWidth() - 1)
            return false; // stop and return: match but hit the bound already with more clue(s) next
          else if (board.isShaded(i, j))
            return false; // stop and return: match but the next is NOT a space
          else j++; // move index to the next
        }
      }
    }
    return true;
  }

  public boolean checkColSpacing() {
    int[] clue;

    for (int i = 0; i < clues.getWidth(); i++) { // loops through all row clues
      boolean flag = true;
      boolean flag2 = false;
      clue = getColClues(i); // sets clue equal to a row clue
      int lenClue = 0;
      for (Integer x : clue) {
        if (x != 0) {
          lenClue++; // 2
        }
      }
      int counter = 0;
      if (lenClue != 0) {
        for (int j = 0; j < clues.getHeight(); j++) { // loops through each column (5)
          if (flag) {
            while (board.isSpace(j, i)) {
              j++;
            }
            while (board.isShaded(j, i)) {
              if (j + 1 < clues.getHeight()) {
                j++;
                flag2 = true;
              } else {
                break;
              }
            }
            if (flag2 && j != clues.getHeight() - 1) {
              j--;
            }
            flag = false;
            counter++;
          } else {
            if (counter != lenClue) {
              if (!board.isSpace(j, i)) {
                return false;
              }
              while (board.isSpace(j, i)) {
                j++;
              }
              while (board.isShaded(j, i)) {
                if (j + 1 < clues.getHeight()) {
                  j++;
                } else {
                  break;
                }
              }
              counter++;
            }
          }
        }
      }
      if (counter != lenClue) {
        return false;
      }
    }
    return true;
  }

  public boolean checkColSpacing2() {
    int[] clueIn;
    for (int i = 0; i < clues.getWidth(); i++) { // loops through all column clues
      // step 1: get positive clues for the row i
      clueIn = getColClues(i); // sets clue equal to a column clue
      ArrayList<Integer> listClues = new ArrayList<Integer>();
      for (Integer x : clueIn) // (3, 0, 1)
      {
        if (x > 0) listClues.add(x); // 2
      }
      // step 2: verify each positive clue
      int j = 0;
      for (int iClue = 0; iClue < listClues.size(); iClue++) // for each positive clue
      {
        // verify bound for j
        if (j >= clues.getHeight()) return false; // stop and return: hit the bound already.
        // scan all columns
        int clue = listClues.get(iClue); // first 3, then 1
        int counter = 0; // counter for i-th clue
        for (; j < clues.getHeight() && counter < clue; j++) {
          if (board.isSpace(j, i)) {
            if (counter == 0) continue;
            else return false; // stop and return: only partial match
          } else counter++;
        }
        // determine true/false
        if (counter != clue) return false; // stop and return: NO match
        else if (iClue < listClues.size() - 1) // not the final clue yet
        {
          if (j >= clues.getHeight() - 1)
            return false; // stop and return: match but hit the bound already with more clue(s) next
          else if (board.isShaded(j, i))
            return false; // stop and return: match but the next is NOT a space
          else j++; // move index to the next
        }
      }
    }
    return true;
  }

  public boolean correctNumber() {
    for (int i = 0; i < clues.getHeight(); i++) {
      int htotal = 0;
      int hactual = 0;
      int[] hclue = getRowClues(i); // {3,0,1}
      for (Integer x : hclue) {
        htotal = htotal + x;
      }
      for (int j = 0; j < clues.getWidth(); j++) {
        if (board.isShaded(i, j)) {
          hactual++; //
        } //
      }
      if (hactual != htotal) {
        return false;
      }
    }
    for (int a = 0; a < clues.getWidth(); a++) {
      int vtotal = 0;
      int vactual = 0;
      int[] vclue = getColClues(a);
      for (Integer x : vclue) {
        vtotal = vtotal + x;
      }
      for (int b = 0; b < clues.getHeight(); b++) {
        if (board.isShaded(b, a)) {
          vactual++; //
        } //
      }
      if (vactual != vtotal) {
        return false;
      }
    }
    return true;
  }
}
/*boolean flag = false;
int flag2 = 0;
int flag3 = 0;
int position = 0;
int[] clue;
int f;
for (int i = 0; i < clues.getHeight(); i++) { //loops through clues
    clue = getRowClues(i);
    while (position < clues.getWidth()) { //loops through horizontal row
          for (int j = 0; j < clue.length; j++) { // loops through each clue >>>>{3, 0, 1}<<<<
                flag2 = 0;
                while (clue[j] != 0 && flag2 != 1) {
                  if (flag) {
                    if (!board.isSpace(i, position)) {
                      return false;
                    }
                    while (board.isSpace(i, position)) {
                        if (position + 1 < clues.getWidth()) {
                            position++;
                        } else {
                            f = j;
                            while (f+1<clue.length) {
                                if (clue[f+1] > 0) {
                                    return false;
                                }
                                f++;
                            }
                        }
                    }
                  }
                  flag3 = 0;
                  if (board.isShaded(i, position)) {
                    for (int x = 1; x < clue[j]; x++) { // loops through nonzero number in clue
                        if (position + 1 < clues.getWidth()) {
                            position++;
                        } else {
                            return false;
                        }
                      if (!board.isShaded(i, position)) {
                        return false;
                      }
                    }
                    flag = true;
                    flag2 = 1;
                    flag3 = 1;
                  }
                    if (position + 1 < clues.getWidth()) {
                        position++;
                    } else {
                        if (j + 1 < clue.length) {
                            return false;
                        }
                    }
                }
                if (flag3 == 0) {
                    return false;
                }
          }
    }
}

flag = false;
flag2 = 0;
flag3 = 0;
position = 0;
for (int i = 0; i < clues.getWidth(); i++) { //loops through clues
    clue = getColClues(i);
    while (position < clues.getHeight()) { //loops through vertical row
        for (int j = 0; j < clue.length; j++) { // loops through each clue >>>>{3, 0, 1}<<<<
            flag2 = 0;
            while (clue[j] != 0 && flag2 != 1) {
                if (flag) {
                    if (!board.isSpace(position, i)) {
                        return false;
                    }
                    while (board.isSpace(position, i)) {
                        if (position + 1 < clues.getHeight()) {
                            position++;
                        } else {
                            f = j;
                            while (f+1<clue.length) {
                                if (clue[f+1] > 0) {
                                    return false;
                                }
                                f++;
                            }
                        }
                    }
                }
                flag3 = 0;
                if (board.isShaded(i, position)) {
                    for (int x = 1; x < clue[j]; x++) { // loops through nonzero number in clue
                        if (position + 1 < clues.getHeight()) {
                            position++;
                        } else {
                            return false;
                        }
                        if (!board.isShaded(position, i)) {
                            return false;
                        }
                    }
                    flag = true;
                    flag2 = 1;
                    flag3 = 1;
                }
                if (position + 1 < clues.getHeight()) {
                    position++;
                } else {
                    if (j + 1 < clue.length) {
                        return false;
                    }
                }
            }
            if (flag3 == 0) {
                return false;
            }
        }
    }
}
return true;*/

    /*int temp = j;
          for (int a = 1; a < hclue[a]; a++) { //loop through each number in clue
    temp++;
    if (first) {
    if (!board.isSpace(i, temp)) {
    return false;
    }
    while (board.isSpace(i, temp)) {
    temp++;
    }
    first = false;
    }
    if (!board.isShaded(i, temp)) {
    return false;
    }
    hactual++;
    first = true;
    }*/

    /*int counter = 0;       //<<<<1, 0, 2>>>>>
    boolean flag = false;
    int k = 0;
    int clueElement = 0;
        for (int i = 0; i < clues.getHeight(); i++) { //loops through clues
        int[] clue = getRowClues(i); //obtained first clue
        if (k < clue.length) {
        clueElement = clue[k];
        }
        for (int j = 0; j < clues.getWidth(); j++) { //loops through row
        if (clueElement == 0) {
        k++;
        clueElement = clue[k];
        }
        counter = 0;
        while (board.isShaded(i, j)) {
        counter++;
        j++;
        flag = true;
        }
        if (flag || j == clues.getWidth() - 1) {
        if (counter != clueElement) {
        return false;
        }
        flag = false;
        k++;
        clueElement = clue[k];
        }
        }
        }
        flag = false;
        k = 0;
        clueElement = 0;
        for (int i = 0; i < clues.getWidth(); i++) { //loops through clues
        int[] clue = getColClues(i); //obtained first clue
        if (k < clue.length) {
        clueElement = clue[k];
        }
        for (int j = 0; j < clues.getHeight(); j++) { //loops through row
        if (clueElement == 0) {
        k++;
        clueElement = clue[k];
        }
        counter = 0;
        while (board.isShaded(j, i)) {
        counter++;
        j++;
        flag = true;
        }
        if (flag || j == clues.getHeight() - 1) {
        if (counter != clueElement) {
        return false;
        }
        flag = false;
        k++;
        clueElement = clue[k];
        }
        }
        }*/
