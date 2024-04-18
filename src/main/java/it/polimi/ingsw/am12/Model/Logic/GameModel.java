package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Utils.Coordinate;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the Model of the game. It contains the
 * state of the game and provides some methods to update this
 * state, according to the game rules.
 */
public class GameModel{
    private final Match match;
    private final List<String> lobby;
    private List<PlayerColour> availableColours;

    /**
     * Class constructor specifying numPlayers
     * @param numPlayers  An int that represents the number of players that
     *                    is required in order to start the match (min 2, max 4)
     * @throws WrongNumberOfPlayersException If numPlayers < 2 or numPlayers > 4
     */
    public GameModel(int numPlayers) throws WrongNumberOfPlayersException {
        if(numPlayers >= 2 && numPlayers <=4)
        {
            this.match = new Match(numPlayers);
        } else if (numPlayers < 2) {
            throw new WrongNumberOfPlayersException("The minimum number of players is 2");
        }
        else {
            throw new WrongNumberOfPlayersException("The maximum number of players is 4");
        }

        lobby = new ArrayList<>();
        availableColours = Arrays.asList(PlayerColour.RED, PlayerColour.YELLOW, PlayerColour.GREEN, PlayerColour.BLUE);

        //TODO: notify controller
    }

    /**
     * Add a user to the match lobby.
     * @param nickname A String to identify the new user.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongNumberOfPlayersException if there is the maximum number of players in the lobby already.
     */
    public void addPlayerToLobby(String nickname) throws WrongNumberOfPlayersException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(lobby.size() == match.getNumPlayers()) {
            throw new WrongNumberOfPlayersException("The lobby is already full");
        }

        //TODO: notify controller (when the lobby is full)
    }

    /**
     * Add the users in the lobby as players of the match
     * @throws WrongNumberOfPlayersException    if the number of nicknames in the lobby differs from the
     *                                          number of players of the match. In this case no player is added.
     */
    public void addPlayersToMatch() throws WrongNumberOfPlayersException {
        if(lobby.size() != match.getNumPlayers())
        {
            throw new WrongNumberOfPlayersException("The number of players in the lobby does not correspond to the number of players of the match");
        }
        for (String nickname : lobby) {
            this.match.addPlayer(nickname);
        }
        match.randomizePlayerOrder();
        match.createDecks();
        match.assignStartCards();

        //TODO: notify controller
    }

    /**
     * Place the starter card on the grid of each the player.
     * @param nickname A String that identifies the player.
     * @param selectedSide A Boolean that indicates the selected side
     *                     of the starter card: TRUE = front; FALSE = back
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws InvalidPlacementException  if a start card has already been placed for the player
     */
    public void placeStartCard(String nickname, Boolean selectedSide)
            throws InvalidPlacementException, WrongInformationException, NotYourTurnException, InvalidParameterException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(selectedSide==null) {
            throw new InvalidParameterException("The selected side is null");
        }
        if(!match.isTurn(nickname))
        {
            throw new NotYourTurnException("It's not your turn");
        }
        match.placeStartCard(nickname, selectedSide);

        //TODO: notify controller
    }

    /**
     * Assign a colour to a player.
     * @param nickname A String that identifies the player.
     * @param selectedColour the colour chosen by the player
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws WrongInformationException  if the colour is not available, or some other player selected it
     */
    public void setPlayerColour(String nickname, PlayerColour selectedColour) throws WrongInformationException, NotYourTurnException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(selectedColour==null) {
            throw new InvalidParameterException("The selected colour is null");
        }
        if(!match.isTurn(nickname))
        {
            throw new NotYourTurnException("It's not your turn");
        }
        if(!availableColours.contains(selectedColour)) {
            throw new WrongInformationException("This colour is not available");
        }
        match.setPlayerColour(nickname, selectedColour);
        availableColours.remove(selectedColour);

        //TODO: notify controller
    }

    /**
     * Distribute cards to the players, in order to start the match.
     * @throws EmptyDeckException if the objective deck is empty
     */
    public void distributeCards() throws EmptyDeckException {
        match.distributeCards();

        //TODO: notify controller
    }

    /**
     * Assign the selected secret objective to a player.
     * @param nickname A String that identifies the player.
     * @param selectedObjective A Boolean that indicates the selected objective
     *                          TRUE = first objective; FALSE = second objective
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException  if it's not the turn of the player
     */
    public void setPlayerObjective(String nickname, Boolean selectedObjective)
            throws WrongInformationException, NotYourTurnException, InvalidParameterException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(selectedObjective==null) {
            throw new InvalidParameterException("The selected objective is null");
        }
        if(!match.isTurn(nickname))
        {
            throw new NotYourTurnException("It's not your turn");
        }
        match.setPlayerObjective(nickname, selectedObjective);

        //TODO: notify controller
    }

    /**
     * Check which positions are available for placing, around a selected card.
     * @param nickname A String that identifies the player
     * @param xpos An int that represents the row of the selected card.
     *             It must be between 0 and 80.
     * @param ypos An int that represents the column of the selected card.
     *             It must be between 0 and 80.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidSearchPositionException if the given position is invalid.
     */
    public void getPlaceablePositions(String nickname, int xpos, int ypos)
            throws InvalidSearchPositionException, NotYourTurnException, WrongInformationException, InvalidParameterException {
        if(nickname == null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname)) {
            throw new NotYourTurnException("It's not your turn");
        }
        if(xpos<0 || xpos>80 || ypos<0 || ypos>80) {
            throw new InvalidSearchPositionException("The given position is not within the playing area.");
        }

        List<Coordinate> availablePositions = match.getPlaceablePositions(xpos, ypos);
    }

    /**
     * Place the selected card on the grid of the player whose turn is now.
     * @param nickname A String that identifies the player
     * @param index An int that indicates which card has to be placed. It must be between 0 and 2.
     * @param side  A boolean that indicates the selected side of the card:
     *              TRUE = front; FALSE = back;
     * @param xpos  An int that represents the row of the position where to place the card.
     *              It must be between 0 and 80.
     * @param ypos  An int that represents the column of the position where to place the card.
     *              It must be between 0 and 80.
     *
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidParameterException if any of the parameters null or is invalid
     * @throws InvalidPlacementException if the placement fails
     */
    public void placeCard(String nickname, int index, Boolean side, int xpos, int ypos)
            throws InvalidParameterException, InvalidPlacementException, NotYourTurnException, WrongInformationException {
        if(nickname == null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(side == null) {
            throw new InvalidParameterException("The selected side is null");
        }
        if(!match.isTurn(nickname)) {
            throw new NotYourTurnException("It's not your turn");
        }
        if(index<0 || index>2 || xpos<0 || xpos>80 || ypos<0 || ypos>80) {
            throw new InvalidParameterException("Parameter out of bounds");
        }
        match.placeCard(index, side, xpos, ypos);

        //TODO: notify controller
    }

    /**
     * Draw a card for the player whose turn is now.
     * @param nickname A String that identifies the player
     * @param index An int that indicates which card has to be drawn.
     *              - index = 0  : public gold 1;
     *              - index = 1  : public gold 2;
     *              - index = 2  : public resource 1;
     *              - index = 3  : public resource 2;
     *              - index = 4  : hidden gold;
     *              - index = 5  : hidden resource;
     * @throws InvalidParameterException if the nockname is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidParameterException if index<0 or index>5
     * @throws EmptyDeckException if the selected deck is empty.
     */
    public void drawCard(String nickname, int index)
            throws InvalidParameterException, EmptyDeckException, NotYourTurnException, WrongInformationException {
        if(nickname == null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname)) {
            throw new NotYourTurnException("It's not your turn");
        }
        if(index<0 || index >5) {
            throw new InvalidParameterException("Index out of bounds");
        }
        match.drawCard(index);

        //TODO: notify controller
    }

    /**
     * Pass the turn to the next player
     */
    public void nextTurn() {
        int remainingRounds = match.nextTurn();

        //TODO: notify controller.
        // If remainingRounds == 0 it means that there
        // are no turns left to play
    }
}