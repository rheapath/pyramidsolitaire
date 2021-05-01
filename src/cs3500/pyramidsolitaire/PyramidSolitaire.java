package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;

/**
 * Main class.
 */
public final class PyramidSolitaire {

  /**
   * Main method.
   * @param args input
   */
  public static void main(String[] args) {
    PyramidSolitaireCreator c = new PyramidSolitaireCreator();
    PyramidSolitaireModel<Card> model;
    PyramidSolitaireTextualController controller;
    int rows = 0;
    int draws = 0;
    if (args.length == 1) {
      rows = 7;
      draws = 3;
    }
    else if (args.length == 3) {
      rows = Integer.parseInt(args[1]);
      draws = Integer.parseInt(args[2]);
    }
    if (rows < 1 || draws < 0) {
      throw new IllegalArgumentException("Rows must be > 1, draws must be > 0");
    }

    if ("basic".equals(args[0])) {
      model = c.create(GameType.BASIC);
      if (rows > 9) {
        throw new IllegalArgumentException("Must be < 9");
      }
    } else if ("multipyramid".equals(args[0])) {
      model = c.create(GameType.MULTIPYRAMID);
      if (rows > 8) {
        throw new IllegalArgumentException("Must be < 8");
      }
    } else if ("relaxed".equals(args[0])) {
      model = c.create(GameType.RELAXED);
      if (rows > 9) {
        throw new IllegalArgumentException("Must be < 9");
      }
    } else {
      throw new IllegalArgumentException("Enter a valid gametype");
    }

    controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    controller.playGame(model, model.getDeck(), false, rows, draws);
  }

}
