package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Card;
import java.io.IOException;

/**
 * Represents the textual view of the game.
 * Generates pyramid.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable ap;
  // ... any other fields you need

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.ap = null;
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    //... render the model here
    String out = "";
    if (model.getNumRows() == -1) { // the game is not started if numRows is -1
      return out;
    }
    if (model.isGameOver() && model.getScore() == 0) {
      return "You win!";
    }
    if (model.isGameOver()) {
      return "Game over. Score: " + Integer.toString(model.getScore()) + "\n";
    }

    int rows = model.getNumRows();
    int whitespace = rows - 1;

    for (int i = 0; i < rows; i++) {

      for (int j = 0; j < whitespace + 2; j++) {
        out += " ";
      }
      for (int k = 0; k < model.getRowWidth(i); k++) {
        Card card = (Card) model.getCardAt(i, k);
        out += card.toString();
        if (k < model.getRowWidth(i) - 1) {
          out += "  ";
        }
      }
      out += "\n";
      whitespace -= 2;
    }

    out += "Draw: ";


    for (int i = 0; i < model.getDrawCards().size(); i++) {
      if (i == 0 && (model.getDrawCards().get(i) != null)) {
        out += model.getDrawCards().get(i).toString() + ", ";
      }
      else if (model.getDrawCards().get(i) != null) {
        out += model.getDrawCards().get(i).toString() + ", ";
      }
      else if (model.getDrawCards().get(i) == null && i > 0) {
        out += "   ";
      }
      else if (model.getDrawCards().get(i) == null && i == 0) {
        out += ",  ";
      }
    }


    //return null; // COME BACK TO THIS PART\

    return out.substring(0, out.length() - 2);
  }


  @Override
  public void render() throws IOException {
    this.ap.append(this.toString());
  }
}
