package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.SelectColourEvent;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;

/**
 * A user selects his colour
 */
public class UserSelectColour implements UserAction{

    private String nickname;

    /**
     * Create a message to select a colour
     * @param params the string command
     * @return a SelectColourEvent
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        String selectedColour = parameters[0];
        for(PlayerColour availableColour : PlayerColour.values()) {
            if (selectedColour.equals(availableColour.getDescription())) {
                commandParametersSent(availableColour.getDescription());
                return new SelectColourEvent(nickname, availableColour);
            }
        }
        System.out.println("Not an available colour. Colours: blue, red, yellow, green, black");
        return null;
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    /**
     * Print a summary of the requested action
     * @param playerColour the selected colour
     */
    public void commandParametersSent(String playerColour){
        System.out.println("Asked for colour: " + playerColour);
    }
}
