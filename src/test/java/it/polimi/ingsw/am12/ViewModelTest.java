package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.View.Updates.*;
import org.junit.jupiter.api.Test;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

class ViewModelTest {

    @Test
    public void ViewModelTest() throws RemoteException {
        ClientController controller = new ClientController("127.0.0.1", 5678);
        CLI cli = new CLI(controller);

        List<Update> updates = new ArrayList<>();
        updates.add(new NicknameEstablishedUpdate("player"));
        updates.add(new PlayersAddedUpdate(Arrays.asList("player", "otherplayer"), State.INITIALIZATION));
        updates.add(new MatchStartedUpdate(
                Arrays.asList("player", "otherplayer"),
                Map.ofEntries(entry("player", 81), entry("otherplayer", 82)),
                CardColour.GREEN,
                CardColour.RED,
                new int[] {42, 45, 12, 18},
                "player",
                State.STARTCARD));
        updates.add(new StartCardPlacedUpdate("player", 81, true, "otherplayer", State.STARTCARD));
        updates.add(new StartCardPlacedUpdate("otherplayer", 82, false, "player", State.COLOUR));
        updates.add(new ColourSelectedUpdate("player", PlayerColour.RED, "otherplayer", State.COLOUR));
        updates.add(new ColourSelectedUpdate("otherplayer", PlayerColour.YELLOW, "player", State.DISTRIBUTION));
        updates.add(new CardsDistributedUpdate(
                Map.ofEntries(entry("player", Arrays.asList(9, 34, 67)), entry("otherplayer", Arrays.asList(28, 1, 55))),
                CardColour.RED,
                CardColour.GREEN,
                Map.ofEntries(entry("player", new int[] {88, 100}), entry("otherplayer", new int[] {86, 95})),
                new int [] {94, 90},
                "player",
                State.OBJECTIVE));
        updates.add(new ObjectiveSelectedUpdate("player", 100, "otherplayer", State.OBJECTIVE));
        updates.add(new ObjectiveSelectedUpdate("otherplayer", 86, "player", State.PLACING));

        for(Update u : updates) {
            controller.catchMessage(u);
        }
    }

}