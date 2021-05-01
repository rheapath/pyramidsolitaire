package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Factory class.
 * Creates a public enum game type, factory creation method, and returns appropriate version.
 */
public class PyramidSolitaireCreator {

  /**
   * Creates an instance of pyramid solitaire to play.
   * @param g the given gametype
   * @return a game
   */
  public static PyramidSolitaireModel create(GameType g) {
    if (g == GameType.BASIC) {
      return new BasicPyramidSolitaire();
    } else if (g == GameType.MULTIPYRAMID) {
      return new MultiPyramidSolitaire();
    } else if (g == GameType.RELAXED) {
      return new RelaxedSolitaire();
    }
    return null;
  }

  /**
   * Enum representing one of 3 gametypes.
   */
  public enum GameType {
    BASIC, MULTIPYRAMID, RELAXED;
  }

}
