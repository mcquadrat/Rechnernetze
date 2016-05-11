package enums;
 
public enum State {
  /**
  * The human player wins against the AI
  */
  WINNER_HUMAN,
  /**
  * The AI wins against the human player
  */
  WINNER_AI,
  /**
  * The field is full and nobody has won
  */
  DRAW,
  /**
  * The field isn't full yet and nobody has won yet
  */
  NOT_DONE,
  /**
  * The field the player tried to mark is already in use (is !=0)
  */
  FIELD_NOT_EMPTY;
}
