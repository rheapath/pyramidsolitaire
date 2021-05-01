package cs3500.pyramidsolitaire.model.hw02;

/**
 * Generic interface for card.
 */

public interface ICard {

  /**
   * Gets the value of a given card.
   * @return value of card
   */
  int getValue();

  /**
   * Gets the suit of a given card.
   * @return suit of card
   */
  String getSuit();

  /**
   * Returns the card as a string.
   * @return card suit and value as a string
   */
  String toString();

}
