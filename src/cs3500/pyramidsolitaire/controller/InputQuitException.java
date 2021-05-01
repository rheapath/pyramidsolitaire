package cs3500.pyramidsolitaire.controller;

/**
 * Constructor for throwing exception when game is quit.
 */
public class InputQuitException extends Exception {

  private static final long serialVersion = 1L;


  /**
   * Constructor.
   * @param message exception output.
   */
  public InputQuitException(String message) {
    super(message);
  }

}
