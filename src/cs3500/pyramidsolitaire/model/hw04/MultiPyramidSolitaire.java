package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.util.ArrayList;
import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Represents pyramid solitaire with multiple pyramids.
 * Since the pyramids are bigger we have to duplicate decks.
 * Override build(), isRowMaxed(), getDeck()
 */
public class MultiPyramidSolitaire extends AbstractPyramidSolitaire {

  @Override
  protected List<Card> build(List<Card> deck) {
    List<Card> copy = new ArrayList<>(deck);
    int gap = this.truncate((int) (super.getNumRows() / 2.0));
    int initializeGap = super.numRow;
    if (initializeGap % 2 == 0) {
      initializeGap = super.numRow - 1;
    }
    if (super.numRow == 1) {
      initializeGap = 3;
    }
    for (int i = 0; i < this.numRow; i++) {
      List<Card> currRow = new ArrayList<Card>();
      for (int j = 0; j < i + initializeGap; j++) {
        if (j % (gap + i) < i + 1) {
          currRow.add(copy.get(0));
          copy.remove(0);
        } else {
          currRow.add(null);
        }
      }
      gap = this.truncate(gap - 1);
      this.pyramid.add((ArrayList<Card>) currRow);
    }
    return copy;
  }

  @Override
  protected boolean isRowMaxed(int row) {
    return row > 8;
  }

  /**
   * Truncates number to int.
   * @param n number
   * @return truncated val
   */
  protected int truncate(int n) {
    if (n < 2) {
      return 1;
    } else {
      return n;
    }
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    for (Value val : Value.values()) {
      for (Suit su : Suit.values()) {
        Card c = new Card(val, su);
        deck.add(c);
      }
    }
    return deck;
  }
}
