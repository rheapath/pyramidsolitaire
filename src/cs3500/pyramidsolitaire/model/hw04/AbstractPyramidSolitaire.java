package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Functionality of this class is intended to provide the basis for all models.
 * Includes MultiPyramid, RelaxedPyramid, and BasicPyramidSolitaire.
 * Abstracts some methods so that there is no need for code duplication.
 */
public class AbstractPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected List<ArrayList<Card>> pyramid = new ArrayList<ArrayList<Card>>();
  protected List<Card> draws = new ArrayList<Card>();
  protected List<Card> stock;
  // initialize game not started with -1
  protected int numRow = -1;
  protected int numDraw = -1;
  protected boolean gameStart = false;

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

  /**
   * Determines if the deck is valid.
   * @param deck the deck to be tested
   * @return whether the deck is valid or not
   */
  protected boolean isDeckValid(List<Card> deck) {
    List<Card> validDeck = this.getDeck();
    List<Card> testDeck = new ArrayList<>(deck);
    if (validDeck.size() != testDeck.size()) {
      return false;
    } for (int i = 0; i < testDeck.size(); i++) {
      if (!validDeck.get(i).equals(testDeck.get(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Instantiates each row of the game.
   * @param deck the deck being used in the game
   * @return empty list (copy), or the remaining cards to be dealt
   */
  protected List<Card> build(List<Card> deck) {
    List<Card> copy = new ArrayList<>(deck);
    for (int i = 0; i < this.getNumRows(); i++) {
      List<Card> currentRow = new ArrayList<Card>();
      for (int j = 0; j < i; j++) {
        currentRow.add(copy.get(0));
        copy.remove(0);
      }
      this.pyramid.add((ArrayList<Card>) currentRow);
    }
    return copy;
  }

  /**
   * The maximum rows are 9, so this is a helper to check if they are maxed.
   * @param row the current row
   * @return true if rows are maxed, else false.
   */
  protected boolean isRowMaxed(int row) {
    return row > 9;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)  {
    if (deck == null || numRows < 1 || numDraw < 0 || this.isRowMaxed(numRows)) {
      throw new IllegalArgumentException("Game has not started");
    }
    if (!this.isDeckValid(deck)) {
      throw new IllegalArgumentException("Game cannot be started because deck is invalid");
    }

    this.numDraw = numDraw;
    this.numRow = numRow;
    this.pyramid = new ArrayList<ArrayList<Card>>();
    this.draws = new ArrayList<Card>();
    this.stock = new ArrayList<Card>();
    List<Card> copy = new ArrayList<>(deck);

    if (shuffle) {
      Collections.shuffle(copy);
    }

    copy = this.build(copy);
    for (int i = 0; i < numDraw; i++) {
      if (copy.size() == 0) {
        break;
      }
      this.draws.add(copy.get(0));
      copy.remove(0);
    }
    this.stock = copy;
    this.gameStart = true;
  }

  /**
   * Checks that the card position is valid.
   * @param row the row of the card
   * @param card the card number
   * @return whether the card position is valid
   */
  protected boolean checkPosition(int row, int card) {
    return (row < 0 || row > this.pyramid.size() - 1 || card < 0
        || card > this.getRowWidth(row) - 1 || this.getCardAt(row, card) == null);
  }

  /**
   * Determines whether the given card is exposed in the pyramid.
   * @param row the row of the card
   * @param card card number
   * @return true if the card is exposed.
   */
  protected boolean isCardExposed(int row, int card) {
    if (this.checkPosition(row, card)) {
      throw new IllegalArgumentException("Invalid position");
    }
    if (this.getCardAt(row, card) == null) {
      return false;
    }
    if (row == this.pyramid.size() - 1) {
      return true;
    } else {
      return this.getCardAt(row + 1, card) == null
          && this.getCardAt(row + 1, card + 1) == null;
    }
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Same card");
    }

    if (this.checkPosition(row1, card1) || this.checkPosition(row2, card2)) {
      throw new IllegalArgumentException("Invalid row/card position");
    }
    if (!(this.isCardExposed(row1, card1) && this.isCardExposed(row2, card2))) {
      throw new IllegalArgumentException("The cards are not exposed");
    }

    Card one = this.getCardAt(row1, card1);
    Card two = this.getCardAt(row2, card2);

    if (one.getValue() + two.getValue() != 13) {
      throw new IllegalArgumentException("Cards must add to 13");
    } else {
      this.replace(row1, card1);
      this.replace(row2, card2);
    }
  }

  @Override
  public void remove(int row, int card) {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (this.getCardAt(row, card).getValue() == 13) {
      this.replace(row, card);
    } else {
      throw new IllegalArgumentException("Card must be a king");
    }
  }

  /**
   * Replaces a removed card with null.
   * @param row row of pyramid
   * @param card card position
   */
  protected void replace(int row, int card) {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (this.checkPosition(row, card)) {
      throw new IllegalStateException("Invalid position");
    }
    if (!this.isCardExposed(row, card)) {
      throw new IllegalArgumentException("Card is not exposed");
    }
    this.pyramid.get(row).set(card, null);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) {
    if (!this.gameStart) {
      throw new IllegalArgumentException("Game has not started");
    }
    if (drawIndex > this.draws.size() - 1 || drawIndex < 0 || this.checkPosition(row, card)) {
      throw new IllegalArgumentException("Invalid move");
    }
    if (!this.isCardExposed(row, card)) {
      throw new IllegalArgumentException("Card is not exposed");
    }
    Card one = this.draws.get(drawIndex);
    Card two = this.getCardAt(row, card);

    if (one.getValue() + two.getValue() != 13) {
      throw new IllegalArgumentException("Cards must add to 13");
    } else {
      this.replace(row, card);
      this.discardDraw(drawIndex);
    }
  }

  @Override
  public void discardDraw(int drawIndex) {
    if (!this.gameStart) {
      throw new IllegalArgumentException("Game has not started");
    }
    if (this.stock.size() != 0) {
      this.draws.set(drawIndex, this.stock.get(0));
      this.stock.remove(0);
    } else {
      this.draws.remove(drawIndex);
    }

  }

  @Override
  public int getNumRows() {
    return this.numRow;
  }

  @Override
  public int getNumDraw() {
    return this.numDraw;
  }

  @Override
  public int getRowWidth(int row)  {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (row < 0 || row > this.pyramid.size() - 1) {
      throw new IllegalArgumentException("Invalid row");
    }
    return this.pyramid.get(row).size();
  }

  @Override
  public boolean isGameOver() {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (this.getScore() == 0) {
      return true;
    }
    if (this.stock.size() + this.draws.size() != 0) {
      return false;
    } else {
      ArrayList<Card> exposedCards = new ArrayList<Card>();
      for (int i = 0; i < this.pyramid.size(); i++) {
        for (int j = 0; j < this.pyramid.get(i).size(); j++) {
          if (this.isCardExposed(i, j)) {
            exposedCards.add(this.getCardAt(i, j));
          }
        }
        for (Card one : exposedCards) {
          if (one.getValue() == 13) {
            return false;
          }
          for (Card two : exposedCards) {
            if (one.getValue() + two.getValue() == 13) {
              return false;
            }
          }
        }
      }
      return true;
    }
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    int currScore = 0;
    for (ArrayList<Card> a : this.pyramid) {
      for (Card c : a) {
        currScore += c.getValue();
      }
    }
    return currScore;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalArgumentException, IllegalStateException {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (row < 0 || card < 0 || row > this.numRow - 1 || card > this.getRowWidth(row)) {
      throw new IllegalArgumentException("Card does not exist");
    }
    Card c = this.pyramid.get(row).get(card);
    if (c.getValue() != 0) {
      return c;
    }
    return null;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!this.gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    return this.draws;
  }

}
