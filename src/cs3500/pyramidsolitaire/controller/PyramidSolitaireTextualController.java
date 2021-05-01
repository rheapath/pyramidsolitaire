package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implements controller for game.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private Readable in;
  private Appendable out;
  private PyramidSolitaireView view;
  private boolean gameQuit;


  /**
   * Constructs a Pyramid Solitaire Game Controller.
   *
   * @param in  The Readable/ input for the game
   * @param out The Appendable/output for the game
   */
  public PyramidSolitaireTextualController(Readable in, Appendable out)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null");
    }
    this.in = in;
    this.out = out;
    this.gameQuit = false;
  }


  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (Exception e) {
      throw new IllegalStateException("Wrong game");
    }

    Scanner sc = new Scanner(this.in);
    view = new PyramidSolitaireTextualView(model, this.out);
    this.renderGame(model);

    while (sc.hasNext() && !model.isGameOver() && !this.gameQuit) {
      String curr = sc.next();
      this.gameQuit(curr, model);

      if (this.gameQuit) {
        return;
      }

      if (curr.equals("rm1") || curr.equals("rm2") || curr.equals("rmwd") || curr.equals("dd")) {
        try {
          this.handle(curr, model, sc);
        } catch (IOException e) {
          throw new IllegalStateException("IOException");
        }
      } else {
        try {
          this.out.append("Invalid Command.\n");
        } catch (IOException e) {
          throw new IllegalStateException("IOException");
        }
      }
    }
  }

  private <K> void handle(String action, PyramidSolitaireModel<K> model, Scanner scan)
      throws IOException {
    ArrayList<Integer> vals = new ArrayList<>();
    boolean moveDone = false;

    while (scan.hasNext() && !moveDone && !model.isGameOver()) {
      String curr = scan.next();
      this.gameQuit(curr, model);
      if (gameQuit) {
        return;
      }
      Integer move = this.isInputValid(curr);
      if (move != null) {
        if ("rm1".equals(action)) {
          vals.add(move - 1);
          if (vals.size() == 2) {
            moveDone = true;
            try {
              model.remove(vals.get(0), vals.get(1));
              this.renderGame(model);
            } catch (IllegalArgumentException e) {
              this.out.append("Invalid move\n");
            }
            vals.clear();
          }
        } else if ("rm2".equals(action)) {
          vals.add(move - 1);
          if (vals.size() == 4) {
            moveDone = true;
            try {
              model.remove(vals.get(0), vals.get(1), vals.get(2), vals.get(3));
              this.renderGame(model);
            } catch (IllegalArgumentException e) {
              this.out.append("Invalid move.\n");
            }

          }
        } else if ("rmwd".equals(action)) {
          vals.add(move - 1);
          if (vals.size() == 3) {
            moveDone = true;
            try {
              model.removeUsingDraw(vals.get(0), vals.get(1), vals.get(2));
              this.renderGame(model);
            } catch (IllegalArgumentException e) {
              this.out.append("Invalid move.\n");
            }
            vals.clear();
          }
        } else if ("dd".equals(action)) {
          vals.add(move - 1);
          if (vals.size() == 1) {
            moveDone = true;
            try {
              model.discardDraw(vals.get(0));
              this.renderGame(model);
            } catch (IllegalArgumentException e) {
              this.out.append("Invalid move\n");
            }
            vals.clear();
          }
        } else {
          throw new IllegalArgumentException("Invalid");
        }
      }
    }
  }

  private Integer isInputValid(String curr) {
    try {
      return Integer.parseInt(curr);
    } catch (Exception e) {
      try {
        this.out.append("Invalid input\n");
        return null;
      } catch (IOException ex) {
        return null;
      }
    }
  }

  private void renderGame(PyramidSolitaireModel model) {
    if (model.isGameOver()) {
      try {
        view.render();
      } catch (Exception e) {
        throw new IllegalStateException("IOException");
      }
    } else {
      try {
        view.render();
        this.out.append("\nScore: ").append(this.currentScore(model)).append("\n");
      } catch (Exception e) {
        throw new IllegalStateException("IOException");
      }
    }
  }

  private <K> void gameQuit(String curr, PyramidSolitaireModel<K> model) {
    if (curr.equalsIgnoreCase("q")) {
      try {
        gameQuit = true;
        this.out.append("Game quit!\n");
        this.renderGame(model);
      } catch (IOException e) {
        throw new IllegalStateException("IOException");
      }
    }
  }

  private String currentScore(PyramidSolitaireModel<Card> model) {
    return Integer.toString(model.getScore());
  }

}