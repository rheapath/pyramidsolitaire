package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Relaxed pyramid solitaire model with given rules. Relaxes the rules for which a card can be
 * removed. Override isGameOver() and remove() for 2 cards
 */
public class RelaxedSolitaire extends AbstractPyramidSolitaire {

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    for (int i = 0; i < this.pyramid.size() - 1; i++) {
      for (int j = 0; j < this.pyramid.get(i).size(); j++) {
        if (this.partiallyExposed(i, j)) {
          if (((this.isCardExposed(i + 1, j))
              && (this.getCardAt(i, j).getValue()
              + this.getCardAt(i + 1, j + 1).getValue() == 13))
              || ((this.isCardExposed(i + 1, j + 1))
              && (this.getCardAt(i, j).getValue()
              + this.getCardAt(i + 1, j + 1).getValue()) == 13)) {
            return false;
          }
        }
      }
    }
    return super.isGameOver();
  }

  /**
   * Checks if one card is exposed.
   *
   * @param row  row of card
   * @param card card position
   * @return if card is partially exposed
   */
  private boolean partiallyExposed(int row, int card) {
    if (this.checkPosition(row, card)) {
      throw new IllegalArgumentException("Invalid position");
    }
    if (this.getCardAt(row, card) == null) {
      return false;
    }
    if (row == this.pyramid.size() - 1) {
      return true;
    } else {
      return xorVal(this.getCardAt(row + 1, card) == null,
          this.getCardAt(row + 1, card + 1) == null);
    }
  }

  /**
   * Helper with xor gate for determining whether a card is partially exposed.
   *
   * @param one value one
   * @param two value two
   * @return result xor gate
   */
  private boolean xorVal(boolean one, boolean two) {
    return (one && !two) || (!one && two);
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    try {
      super.remove(row1, card1, row2, card2);
    } catch (IllegalArgumentException e) {
      if (row1 == row2 && card1 == card2) {
        throw new IllegalArgumentException("Same card");
      }
      if (this.checkPosition(row1, card1) || this.checkPosition(row2, card2)) {
        throw new IllegalArgumentException("Invalid position");
      }
      if (!((row1 == row2 + 1) && (card1 == card2 || card1 == card2 + 1)
          || (row2 == row1 + 1) && (card1 == card2 || card2 == card1 + 1))) {
        throw new IllegalArgumentException("Not a valid pair");
      }
      if (!((this.partiallyExposed(row1, card1)) && super.isCardExposed(row2, card2))
          || ((this.partiallyExposed(row2, card2)) && super.isCardExposed(row1, card1))) {
        throw new IllegalArgumentException("Invalid move, the cards aren't paired correctly");
      }

      Card one = this.getCardAt(row1, card1);
      Card two = this.getCardAt(row2, card2);

      if (one.getValue() + two.getValue() != 13) {
        throw new IllegalArgumentException("Cards must add to 13");
      }
      this.pyramid.get(row1).set(card1, null);
      this.pyramid.get(row2).set(card2, null);
    }
  }


}
