package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a card.
 */
public class Card implements ICard {

  private final Value value;
  private final Suit suit;

  /**
   * Constructor for card.
   * @param value card value
   * @param suit card suit
   */
  public Card(Value value, Suit suit) {

    if (value == null || suit == null) {
      throw new IllegalArgumentException("suit/value cannot be null");
    }
    this.value = value;
    this.suit = suit;
  }

  @Override
  public int getValue() {
    return value.getValue();
  }

  @Override
  public String getSuit() {
    return suit.toString();
  }

  //
  @Override
  public String toString() {
    String str = null;
    if (this.value.getValue() < 10 && this.value.getValue() > 1) {
      str = Integer.toString(this.value.getValue()) + this.suit.toString();
    }
    if (this.value.getValue() == 10) {
      str = Integer.toString(this.value.getValue()) + this.suit.toString();
      // no need to pad a space
    }
    if (this.value.getValue() == 1) {
      str = "A" + this.suit.toString();
    }
    if (this.value.getValue() == 11) {
      str = "J" + this.suit.toString();
    }
    if (this.value.getValue() == 12) {
      str = "Q" + this.suit.toString();
    }
    if (this.value.getValue() == 13) {
      str = "K" + this.suit.toString();
    }
    return str;
  }

  // exposed field is neglected
  // card equality is based on suit and value
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return value == card.value && suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, suit);
  }
}