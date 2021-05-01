package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the suit of a card.
 * I chose to represent this as an enum rather than as a field in the Card class
 * after seeing prof's lecture 02/05.
 * The card suits are constants, therefore makes more sense to initialize them here.
 * A card can only take on one suit --> therefore enum makes most sense.
 */
public enum Suit {

  // '♣' '♦' '♥' '♠'

  CLUB("♣"),
  DIAMOND("♦"),
  HEART("♥"),
  SPADE("♠");

  private final String suit;

  Suit(String s) {
    this.suit = s;
  }

  /**
   * Returns suit as string.
   * @return suit
   * @throws IllegalArgumentException if suit is null/invalid
   */
  @Override
  public String toString() {
    if (suit == null) {
      throw new IllegalArgumentException("suit cannot be null");
    }

    if (!(suit.equals("♣") || suit.equals("♦") || suit.equals("♥") || suit.equals("♠"))) {
      throw new IllegalArgumentException("not a valid suit");
    } else {
      return suit;
    }

  }

}
