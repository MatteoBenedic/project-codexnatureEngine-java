package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Utils.Coordinate;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

/**
 * This class is the Model of the game. It contains the
 * state of the game and provides some methods to update this
 * state, according to the game rules.
 */
public class GameModel{
    private final Match match;

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

        //TODO: notify controller
    }

    /**
     * Add a list of users as players of the match
     * @param nicknames    A List of Strings of the nicknames of the users
     *                     to add as players
     * @throws WrongNumberOfPlayersException    if the number of nicknames differs from the
     *                                          number of players of the match.
     *                                          In this case no player is added.
     */
    public void addPlayersToMatch(List<String> nicknames) throws WrongNumberOfPlayersException {
        if(nicknames.size() != match.getNumPlayers())
        {
            throw new WrongNumberOfPlayersException("The number of nicknames does not correspond to the number of players of the match");
        }
        for (String nickname : nicknames) {
            this.match.addPlayer(nickname);
        }
        match.randomizePlayerOrder();
        match.createDecks();
        match.assignStartCards();

        //TODO: notify controller
    }

    /**
     * Place the starter card on the grid of each the player.
     * @param selectedSides A Map<String, Boolean> that associates to each nickname
     *                      the selected side of the starter card: TRUE = front; FALSE = back
     * @throws MissingInformationException  if selectedSides does not contain a selection for all the players of the match.
     *                                      In this case no start card is placed.
     * @throws InvalidPlacementException    if a start card has already been placed for one or more players.
     */
    public void placeStartCards(Map<String, Boolean> selectedSides) throws MissingInformationException, InvalidPlacementException {

        for(String playerName : match.getPlayerNames())
        {
            if(!selectedSides.containsKey(playerName) || selectedSides.get(playerName)==null) {
                throw new MissingInformationException("Missing side selection for one or more players");
            }
        }
        match.placeStartCards(selectedSides);

        //TODO: notify controller
    }

    /**
     * Assign a color to each player
     * @param selectedColors    A Map<String, Color> that associates to each nickname
     *                          the selected color.
     * @throws MissingInformationException  if selectedColors does not contain a selection for all the players of the match.
     *                                      In this case no color is assigned.
     * @throws WrongInformationException    if selectedColors has two players of the match with the same selected color.
     *                                      In this case no color is assigned.
     */
    public void setPlayerColours(Map<String, PlayerColour> selectedColors) throws MissingInformationException, WrongInformationException {

        for(String playerName : match.getPlayerNames())
        {
            if(!selectedColors.containsKey(playerName) || selectedColors.get(playerName) == null) {
                throw new MissingInformationException("Missing color selection for one or more players");
            }
        }

        for(String playerName : match.getPlayerNames())
        {
            for(String playerName2 : match.getPlayerNames()) {
                if(!playerName.equals(playerName2) && selectedColors.get(playerName).equals(selectedColors.get(playerName2)))
                {
                    throw new WrongInformationException("Some players selected the same color");
                }
            }
        }

        match.setPlayerColours(selectedColors);

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
     * Assign the selected secret objective to each player.
     * @param selectedObjectives A Map<String, Boolean> that associates to each nickname
     *                           the selected secret objective: TRUE = first objective; FALSE = second objective.
     * @throws MissingInformationException  if selectedObjective does not contain a selection for all the players of the match.
     *                                      In this case no objective is assigned.
     */
    public void setPlayerObjectives(Map<String, Boolean> selectedObjectives) throws MissingInformationException {

        for(String playerName : match.getPlayerNames())
        {
            if(!selectedObjectives.containsKey(playerName) || selectedObjectives.get(playerName) == null) {
                throw new MissingInformationException("Missing objective selection for one or more players");
            }
        }

        match.setPlayerObjectives(selectedObjectives);

        //TODO: notify controller
    }

    /**
     * Check which positions are available for placing, around a selected card,
     * for the player whose turn is now.
     * @param xpos An int that represents the row of the selected card.
     *             It must be between 0 and 80.
     * @param ypos An int that represents the column of the selected card.
     *             It must be between 0 and 80.
     * @throws InvalidSearchPositionException if the given position is invalid.
     */
    public void getPlaceablePositions(int xpos, int ypos) throws InvalidSearchPositionException{
        if(xpos<0 || xpos>80 || ypos<0 || ypos>80) {
            throw new InvalidSearchPositionException("The given position is not within the playing area.");
        }

        List<Coordinate> availablePositions = match.getPlaceablePositions(xpos, ypos);
    }

    /**
     * Place the selected card on the grid of the player whose turn is now.
     * @param index An int that indicates which card has to be placed. It must be between 0 and 2.
     * @param side  A boolean that indicates the selected side of the card:
     *              TRUE = front; FALSE = back;
     * @param xpos  An int that represents the row of the position where to place the card.
     *              It must be between 0 and 80.
     * @param ypos  An int that represents the column of the position where to place the card.
     *              It must be between 0 and 80.
     *
     * @throws InvalidParameterException if any of the parameters is invalid
     * @throws InvalidPlacementException if the placement fails
     */
    public void placeCard(int index, boolean side, int xpos, int ypos)
            throws InvalidParameterException, InvalidPlacementException {
        if(index<0 || index>2 || xpos<0 || xpos>80 || ypos<0 || ypos>80) {
            throw new InvalidParameterException();
        }
        match.placeCard(index, side, xpos, ypos);

        //TODO: notify controller
    }

    /**
     * Draw a card for the player whose turn is now.
     * @param index An int that indicates which card has to be drawn.
     *              - index = 0  : public gold 1;
     *              - index = 1  : public gold 2;
     *              - index = 2  : public resource 1;
     *              - index = 3  : public resource 2;
     *              - index = 4  : hidden gold;
     *              - index = 5  : hidden resource;
     * @throws InvalidParameterException if index<0 or index>5
     * @throws EmptyDeckException if the selected deck is empty.
     */
    public void drawCard(int index) throws InvalidParameterException, EmptyDeckException {
        if(index<0 || index >5) {
            throw new InvalidParameterException();
        }
        match.drawCard(index);
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