import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.ICard;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import org.junit.Test;

/**
 * Test class for card methods.
 */
public class CardTest {

  private ICard aceHeart = new Card(Value.ACE, Suit.HEART);
  private ICard kingSpade = new Card(Value.KING, Suit.SPADE);
  private ICard threeClub = new Card(Value.THREE, Suit.CLUB);
  private ICard eightDiamond = new Card(Value.EIGHT, Suit.DIAMOND);
  // private ICard emptyCard = new Card(null, null);

  private ICard aceHeartDup = new Card(Value.ACE, Suit.HEART);
  private ICard threeClubDup = new Card(Value.THREE, Suit.CLUB);

  @Test
  public void testGetValue() {
    assertEquals(aceHeart.getValue(), 1);
    assertEquals(kingSpade.getValue(), 13);
    assertEquals(eightDiamond.getValue(), 8);
    assertEquals(threeClub.getValue(), 3);
  }

  // '♣' '♦' '♥' '♠'
  @Test
  public void testGetSuit() {
    assertEquals(aceHeart.getSuit(), "♥");
    assertEquals(kingSpade.getSuit(), "♠");
    assertEquals(threeClub.getSuit(), "♣");
    assertEquals(eightDiamond.getSuit(), "♦");

  }

  @Test
  public void testToString() {
    assertEquals(aceHeart.toString(), "A♥");
    assertEquals(kingSpade.toString(), "K♠");
    assertEquals(eightDiamond.toString(), "8♦");
    assertEquals(threeClub.toString(), "3♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullCardValue() {
    ICard nullCard = new Card(null, Suit.HEART);

  }

  @Test(expected = IllegalArgumentException.class)
  public void nullCardSuit() {
    ICard nullCard2 = new Card(Value.EIGHT, null);
  }

  @Test
  public void testEquals() {
    assertEquals(true, aceHeart.equals(aceHeartDup));
    assertEquals(true, threeClubDup.equals(threeClub));
    assertEquals(false, kingSpade.equals(eightDiamond));
    assertEquals(true, kingSpade.equals(new Card(Value.KING, Suit.SPADE)));
    assertEquals(true, threeClub.equals(new Card(Value.THREE, Suit.CLUB)));
  }

}
