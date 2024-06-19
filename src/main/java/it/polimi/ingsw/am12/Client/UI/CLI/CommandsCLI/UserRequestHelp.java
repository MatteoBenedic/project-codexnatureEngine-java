package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;

/**
 * Class that responds to a user help request
 */
public class UserRequestHelp implements UserRequest{


    @Override
    public void showRequest(CLI cli) {
        System.out.println("You will be guided during the initialization of a lobby and a match.");
        System.out.println("There are some commands to help the visualization of your cards or objectives:");
        System.out.println(" - 'showmycardsinhand', 'showmygrid', 'showdrawtable' gives you whenever you need the ");
        System.out.println("   visualization of your hand, your grid, or the drawtable;");
        System.out.println(" - 'showobjectives' write your objectives, public or private, whenever you need those;");
        System.out.println(" - 'flipcard x' let's you switch the visualization side of the x card in your hand, where ");
        System.out.println("   the first is defined by the number 0, the second by 1 and the third by 2;");
        System.out.println(" - 'switchgridvisual' let's you change the visualization of your grid in an alternative way, ");
        System.out.println("   where you see a list of all your cards with the coordinates of its position defined for each card;");
        System.out.println(" ");
        System.out.println("The position of the start card is 40 40: the first number indicates the line, and the latter the column.");
        System.out.println("For example, if you want to know at which coordinates you can place cards around the start card, ");
        System.out.println("you should type 'getpos 40 40' ");
        System.out.println(" ");
        System.out.println("When you're in a match, chat between the players is available:");
        System.out.println(" - 'chatpublic', to send a message to all the players;");
        System.out.println(" - 'chatprivate', to send a message privately to a single player;");
        System.out.println(" ");
        System.out.println("The index required to place the card follows the same logic of the position in hand for the flipcard request.");
        System.out.println(" ");
        System.out.println("The command 'drawcard x' it gives you in the hand the card in the x position. The positions are defined like this:");
        System.out.println(" - First line:  0  1  4");
        System.out.println(" - Second line: 2  3  5");

    }

    @Override
    public void setNickname(String nickname) {
    }

    @Override
    public void setPossibleParameter(int value) {

    }
}
