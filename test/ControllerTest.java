import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Class for tests for controller.
 */

public class ControllerTest {

  private PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
  private List<Card> deck = model.getDeck();

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelGame() {
    StringBuilder s = new StringBuilder();
    Reader inp = new StringReader("");
    PyramidSolitaireTextualController c = new PyramidSolitaireTextualController(inp, s);
    c.playGame(null, model.getDeck(), true, 2, 3);
  }

  // tests an invalid draw index
  /**
   * Tests invalid discard draw.
   * @throws IOException exception
   */
  private void testInvalidDD() throws IOException {
    StringBuilder s = new StringBuilder();
    Reader inp = new StringReader("dd 4");
    PyramidSolitaireTextualController c = new PyramidSolitaireTextualController(inp, s);
    c.playGame(model, model.getDeck(), true, 2, 3);
  }

  // null string input Readable
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidController() {
    StringBuilder s = null;
    Reader inp = new StringReader("rm1 4 5");
    PyramidSolitaireTextualController c = new PyramidSolitaireTextualController(inp, s);
    c.playGame(model, model.getDeck(), true, 5, 6);
  }

  //  null Appendable
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidController2() {
    StringBuilder s = new StringBuilder();
    Reader inp = null;
    PyramidSolitaireTextualController c = new PyramidSolitaireTextualController(inp, s);
    c.playGame(model, model.getDeck(), true, 2, 2);
  }

  // invalid command inputted "howdy"

  /**
   * Tests invalid command input to controller.
   * @throws IOException exception
   */
  private void testInvalidCommand() throws IOException {
    StringBuilder s = new StringBuilder();
    Reader inp = new StringReader("howdy");
    PyramidSolitaireTextualController c = new PyramidSolitaireTextualController(inp, s);
    c.playGame(model, deck, false, 6, 5);
  }

  @Test
  public void testPlayGameDD() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    Collections.reverse(card);
    controller.playGame(game, card, false, 3, 2);
    assertEquals(out.toString(), "    K♠\n"
        + "  K♥  K♦\n"
        + "K♣  Q♠  Q♥\n"
        + "Draw: Q♦, Q♣\n"
        + "Score: 76\n"
        + "    K♠\n"
        + "  K♥  K♦\n"
        + "K♣  Q♠  Q♥\n"
        + "Draw: Q♣\n"
        + "Score: 152\n");
  }

  @Test
  public void testPlayGameBadDD() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 7");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    Collections.reverse(card);
    controller.playGame(game, card, false, 3, 5);
    assertEquals(out.toString(), "    K♠\n"
        + "  K♥  K♦\n"
        + "K♣  Q♠  Q♥\n"
        + "Draw: Q♦, Q♣, J♠, J♥, J♦\n"
        + "Score: 76\n"
        + "Invalid move\n");
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameRm1Bad() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 2 2");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    Collections.reverse(card);
    controller.playGame(game, card, false, 3, 5);
  }

  // this should be a valid move but it's not allowing it
  @Test
  public void testPlayGameRm1() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 2 0");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    Collections.reverse(card);
    controller.playGame(game, card, false, 3, 5);
    assertEquals(out.toString(), "    K♠\n"
        + "  K♥  K♦\n"
        + "K♣  Q♠  Q♥\n"
        + "Draw: Q♦, Q♣, J♠, J♥, J♦\n"
        + "Score: 76\n"
        + "Invalid move\n");
  }

  // this is throwing an error i don't know why
  @Test
  public void testPlayGameRm2() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 6 2 6 3");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    controller.playGame(game, card, false, 7, 5);
    assertEquals(out.toString(), "        A♣\n"
        + "      A♦  A♥\n"
        + "    A♠  2♣  2♦\n"
        + "  2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n"
        + "Draw: 8♣, 8♦, 8♥, 8♠, 9♣\n"
        + "Score: 112\n"
        + "Invalid move.\n");
  }

  @Test
  public void testPlayGameRmwd() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 3 5 5");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    controller.playGame(game, card, false, 6, 5);
    assertEquals(out.toString(), "       A♣\n"
        + "     A♦  A♥\n"
        + "   A♠  2♣  2♦\n"
        + " 2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "Draw: 6♦, 6♥, 6♠, 7♣, 7♦\n"
        + "Score: 66\n"
        + "Invalid move.\n");
  }

  @Test
  public void testPlayGameRmwdBad() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 9 5 5");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    controller.playGame(game, card, false, 6, 5);
    assertEquals(out.toString(), "       A♣\n"
        + "     A♦  A♥\n"
        + "   A♠  2♣  2♦\n"
        + " 2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "Draw: 6♦, 6♥, 6♠, 7♣, 7♦\n"
        + "Score: 66\n"
        + "Invalid move.\n");

  }

  @Test
  public void testGameQuitCaps() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("Q");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    controller.playGame(game, card, false, 6, 5);
    assertEquals(out.toString(), "       A♣\n"
        + "     A♦  A♥\n"
        + "   A♠  2♣  2♦\n"
        + " 2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "Draw: 6♦, 6♥, 6♠, 7♣, 7♦\n"
        + "Score: 66\n"
        + "Game quit!\n"
        + "       A♣\n"
        + "     A♦  A♥\n"
        + "   A♠  2♣  2♦\n"
        + " 2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "Draw: 6♦, 6♥, 6♠, 7♣, 7♦\n"
        + "Score: 132\n");
  }

  @Test
  public void testGameQuitLower() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("q");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    List<Card> card = game.getDeck();
    controller.playGame(game, card, false, 6, 5);
    assertEquals(out.toString(), "       A♣\n"
        + "     A♦  A♥\n"
        + "   A♠  2♣  2♦\n"
        + " 2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "Draw: 6♦, 6♥, 6♠, 7♣, 7♦\n"
        + "Score: 66\n"
        + "Game quit!\n"
        + "       A♣\n"
        + "     A♦  A♥\n"
        + "   A♠  2♣  2♦\n"
        + " 2♥  2♠  3♣  3♦\n"
        + "3♥  3♠  4♣  4♦  4♥\n"
        + "4♠  5♣  5♦  5♥  5♠  6♣\n"
        + "Draw: 6♦, 6♥, 6♠, 7♣, 7♦\n"
        + "Score: 132\n");
  }

}
