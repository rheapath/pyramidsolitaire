package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the value of a card.
 * I chose to represent this as an enum rather than as a field in the Card class
 * after seeing prof's lecture 02/05.
 * The card values are constants, therefore makes more sense to initialize them here.
 * A card can only take on one value --> therefore enum makes most sense.
 */
public enum Value {

  ACE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(11),
  QUEEN(12),
  KING(13);

  private final int value;

  Value(int val) {
    this.value = val;
  }

  /**
   * Gets value of card.
   * @return value of card as an Integer.
   */
  public int getValue() {
    return value;
  }
}
