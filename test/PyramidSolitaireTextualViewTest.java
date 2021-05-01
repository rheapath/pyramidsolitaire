import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import java.util.Arrays;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

/**
 * Tests for Pyramid Solitaire.
 */
public class PyramidSolitaireTextualViewTest {

  private PyramidSolitaireModel<Card> testModel = new BasicPyramidSolitaire();
  private PyramidSolitaireTextualView testView = new PyramidSolitaireTextualView(testModel);

  private Card kingSpade = new Card(Value.KING, Suit.SPADE);
  private Card threeClub = new Card(Value.THREE, Suit.CLUB);
  private Card fourHeart = new Card(Value.FOUR, Suit.HEART);
  private Card nineDiamond = new Card(Value.NINE, Suit.DIAMOND);

  private ArrayList<Card> drawCards = new ArrayList<Card>();
  private ArrayList<Card> stockCards = new ArrayList<Card>();

  private List<Card> deck = testModel.getDeck();
  private List<Card> duplicateDeck = testModel.getDeck();

  //// '♣' '♦' '♥' '♠'
  private String compactView = "    A♣\n"
      + "  A♦  A♥\n"
      + "A♠  2♣  2♦\n"
      + "Draw: 2♥, 2♠";
  private String view2 = "     K♠\n"
      + "   K♥  K♦\n"
      + " K♣  Q♠  Q♥\n"
      + "Q♦  Q♣  J♠  J♥\n"
      + "Draw: J♦, J♣, 10♠";

  @Test(expected = IllegalStateException.class)
  public void testGameStart() {
    // all methods should throw an exception if the game has not started
    testModel.remove(1, 2);
    testModel.remove(1, 2, 3, 4);
    testModel.removeUsingDraw(1, 2, 1);
    testModel.discardDraw(0);
    testModel.getNumDraw();
    testModel.getRowWidth(1);
    testModel.isGameOver();
    testModel.getScore();
    testModel.getCardAt(0, 0);
    testModel.getDrawCards();
  }

  @Test
  public void testPyramidView1() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(compactView, this.testView.toString());
  }

  @Test
  public void testPyramidView2() {
    Collections.reverse(duplicateDeck);
    testModel.startGame(duplicateDeck, false, 4, 3);
    assertEquals(view2, this.testView.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveExceptionsErrors() {
    testModel.startGame(deck, false, 3, 2);
    testModel.remove(2, 1); // can't remove because card does not equal 13
    testModel.remove(5, 3); // can't remove because card does not exist
    testModel.remove(2,1, 2, 2); // can't remove because cards don't = 13
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveExceptionsErrors2() {
    Collections.reverse(duplicateDeck);
    testModel.startGame(duplicateDeck, false, 4, 3);
    testModel.remove(5, 3); // can't remove because card does not exist
    testModel.remove(4, 1); // can't remove because card does not = 13
    testModel.remove(4, 2, 4, 1); // can't remove because cards don't = 13
  }

  @Test
  public void testGetDeck() {
    testModel.startGame(deck, false, 5, 4);
    assertEquals(deck, testModel.getDeck());
  }

  @Test
  public void testGetCardAtValid() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(new Card(Value.ACE, Suit.CLUB), testModel.getCardAt(0, 0));
    assertEquals(new Card(Value.TWO, Suit.CLUB), testModel.getCardAt(2, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtError() {
    testModel.startGame(deck, false, 3, 2);
    testModel.getCardAt(4, 1); // not valid row
    testModel.getCardAt(2, 5); // not valid card position
  }

  @Test
  public void testGetDrawCards() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(new ArrayList<Card>(Arrays.asList(new Card(Value.TWO, Suit.HEART),
        new Card(Value.TWO, Suit.SPADE))), testModel.getDrawCards());
  }

  @Test
  public void testIsGameOver() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(false, testModel.isGameOver());
  }

  @Test
  public void testIsGameOver2() {
    testModel.startGame(deck, false, 1, 51);
    assertEquals(false, testModel.isGameOver());
  }

  @Test
  public void testGetScore() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(8, testModel.getScore());
  }

  @Test
  public void testGetScore2() {
    Collections.reverse(duplicateDeck);
    testModel.startGame(duplicateDeck, false, 4, 3);
    assertEquals(122, testModel.getScore());
  }

  @Test
  public void testGetScore3() {
    testModel.startGame(deck, false, 1, 51);
    assertEquals(1, testModel.getScore());
  }

  @Test
  public void testGetNumRowsGameNotStarted() {
    assertEquals(-1, testModel.getNumRows());
  }

  @Test
  public void testGetNumRowsGameStarted() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(3, testModel.getNumRows());
  }

  @Test
  public void testGetDrawGameNotStarted() {
    assertEquals(-1, testModel.getNumDraw());
  }

  @Test
  public void testGetDrawGameStarted() {
    testModel.startGame(deck, false, 4, 6);
    assertEquals(6, testModel.getNumDraw());
  }

  @Test
  public void testGetRowWidthValid() {
    testModel.startGame(deck, false, 3, 2);
    assertEquals(1, testModel.getRowWidth(0));
    assertEquals(2, testModel.getRowWidth(1));
    assertEquals(3, testModel.getRowWidth(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidthInvalid() {
    testModel.startGame(deck, false, 3, 2);
    testModel.getRowWidth(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscardDrawError() {
    testModel.startGame(deck, false, 3, 2);
    testModel.discardDraw(3);
  }

  @Test
  public void testDiscardDrawValid() {
    testModel.startGame(deck, false, 3, 2);
    testModel.discardDraw(1);
    assertEquals(2, testModel.getDrawCards().get(0).getValue());
  }

  @Test
  public void testDiscardDrawValid2() {
    Collections.reverse(duplicateDeck);
    testModel.startGame(duplicateDeck, false, 4, 3);
    testModel.discardDraw(1);
    assertEquals(10, testModel.getDrawCards().get(1).getValue());
  }

  @Test
  public void testDiscardDrawValid3() {
    Collections.reverse(duplicateDeck);
    testModel.startGame(duplicateDeck, false, 4, 3);
    testModel.discardDraw(0);
    assertEquals(10, testModel.getDrawCards().get(1).getValue());
  }

  // test removeusingdraw()














}




