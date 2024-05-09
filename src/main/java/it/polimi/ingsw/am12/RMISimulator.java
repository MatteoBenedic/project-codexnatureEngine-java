package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Controller.Events.*;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.ObjectiveCard;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.Utils.JSONParser;
import it.polimi.ingsw.am12.View.Updates.*;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.util.*;
import it.polimi.ingsw.am12.Model.Logic.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static java.lang.Thread.sleep;


public class RMISimulator implements Runnable, Remote, Serializable {
    Update update;
    String nickname;
    int indStartCard;
    VVStub vv;
    List<String> availColours;
    String colour;
    PlayerColour colPlayer;
    Boolean selectedSide = null;
    ClientController client = null;
    ServerStub server;
    String host;

    public RMISimulator(String host) {
        this.host = host;
    }

    public void run() {
        JSONParser parser = new JSONParser();
        List<GameCard> resources = parser.parseResourceCards();
        List<GameCard> starters = parser.parseStartCards();
        List<GameCard> golds = parser.parseGoldCards();
        List<ObjectiveCard> objectives = parser.parseObjectiveCards();

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(host, 1600);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //Ask for nickname
        boolean userCreated = false;
        Scanner myObj = new Scanner(System.in);
        do {
            System.out.println("Enter username: ");
            nickname = myObj.nextLine();
            try {
                client = new ClientController(this);
                userCreated = true;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        } while(!userCreated);


        try {
            server = (ServerStub) registry.lookup("CodexServer");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int choice;
        do{
            System.out.println("Scegli che metodo attivare: ");
            System.out.println(" - 0 per create match;");
            System.out.println(" - 1 per join match;");
            System.out.println(" - 2 per start match;");
            System.out.println(" - 3 per startcard;");
            System.out.println(" - 4 per  colour;");
            System.out.println(" - 5 per distribute;");
            System.out.println(" - 6 per objective;");
            System.out.println(" - 7 per placeable pos;");
            System.out.println(" - 8 per place;");
            System.out.println(" - 9 per draw;");
            System.out.println(" - 10 per endgame;");
            System.out.println(" - 11 per close;");
            System.out.println(" - 12 per chat;");

            choice = Integer.parseInt(myObj.nextLine());
            switch(choice){
                case 0:
                    System.out.println("Creazione nuova partita! Inserire nomeMatch: ");
                    String matchname = myObj.nextLine();
                    System.out.println("inserire numero giocatori: ");
                    int numPlayer = Integer.parseInt(myObj.nextLine());
                    try {
                        server.createMatch(matchname, numPlayer, nickname);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        PlayersAddedUpdate up = (PlayersAddedUpdate) update;
                        List<String> nm = up.getNicknames();
                        for(String n : nm)
                            System.out.println(n);
                        if(up.getState() == State.LOBBY)
                            System.out.println("Right state");

                        try {
                            vv = (VVStub) registry.lookup(nickname+"VirtualView");
                        } catch (RemoteException | NotBoundException e) {
                            throw new RuntimeException(e);
                        }
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 1:
                    System.out.println("Vuoi inserirti in un match già creato! Inserire nomeMatch: ");
                    String matchname1 = myObj.nextLine();
                    try {
                        server.joinMatch(matchname1, nickname);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        PlayersAddedUpdate up1 = (PlayersAddedUpdate) update;
                        List<String> nm1 = up1.getNicknames();
                        for(String n : nm1)
                            System.out.println(n);
                        if(up1.getState() == State.LOBBY)
                            System.out.println("Right state");
                        else if (up1.getState() == State.INITIALIZATION)
                            System.out.println("New state");

                        try {
                            vv = (VVStub) registry.lookup(nickname+"VirtualView");
                        } catch (RemoteException | NotBoundException e) {
                            throw new RuntimeException(e);
                        }
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("La lobby verrà trasferita all'interno della partita, attendere...");

                    StartMatchEvent ev0 = new StartMatchEvent();
                    try {
                        vv.performEvent(ev0);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        MatchStartedUpdate up2 = (MatchStartedUpdate) update;
                        if(up2.getState() == State.STARTCARD) {
                            indStartCard = up2.getStartCards().get(nickname);
                            GameCard startCard = starters.get(indStartCard - 80);
                            startCard.setValidSide(false);
                            int[] res = startCard.getResources();
                            for(int i = 0; i < 7; i++)
                                System.out.println(res[i]);
                        }

                    } catch (WrongNumberOfPlayersException e) {
                        System.out.println(e.getMessage());
                    } catch (DuplicateNicknameException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidPlacementException e) {
                        System.out.println(e.getMessage());
                    } catch (WrongInformationException e) {
                        System.out.println(e.getMessage());
                    } catch (NotYourTurnException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyDeckException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidSearchPositionException e) {
                        System.out.println(e.getMessage());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }




                    break;
                case 3:
                    System.out.println("Stai procedendo a piazzare la carta iniziale");
                    System.out.println("Scegli il lato (0 fronte/1 retro): ");
                    int side = Integer.parseInt(myObj.nextLine());
                    if(side == 0)
                        selectedSide = true;
                    else if(side == 1)
                        selectedSide = false;
                    Event ev1 = new PlaceStartCardEvent(nickname, selectedSide);
                    try {
                        vv.performEvent(ev1);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        StartCardPlacedUpdate up3 = (StartCardPlacedUpdate) update;
                        if(up3.getNickname().equals(nickname))
                            System.out.println("La tua carta iniziale è stata piazzata");

                    } catch (WrongNumberOfPlayersException e) {
                        System.out.println(e.getMessage());
                    } catch (DuplicateNicknameException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidPlacementException e) {
                        System.out.println(e.getMessage());
                    } catch (WrongInformationException e) {
                        System.out.println(e.getMessage());
                    } catch (NotYourTurnException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyDeckException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidSearchPositionException e) {
                        System.out.println(e.getMessage());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }


                    break;
                case 4:
                    System.out.println("Stai procedendo a scegliere il tuo colore");
                    if(availColours == null)
                        System.out.println("Scegli il colore tra red, blue, green and yellow: ");
                    else{
                        System.out.println("Scegli il colore tra red, blue, green: ");
                    }
                    colour = myObj.nextLine();
                    switch(colour) {
                        case "red":
                            colPlayer = PlayerColour.RED;
                            break;
                        case "blue":
                            colPlayer = PlayerColour.BLUE;
                            break;
                        case "green":
                            colPlayer = PlayerColour.GREEN;
                            break;
                        case "yellow":
                            colPlayer = PlayerColour.YELLOW;
                            break;
                        default:
                            System.out.println("You selected an invalid colour");
                            break;
                    }

                    SelectColourEvent ev4 = new SelectColourEvent(nickname, colPlayer);

                    try {
                        vv.performEvent(ev4);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        ColourSelectedUpdate up4 = (ColourSelectedUpdate) update;
                        System.out.println(up4.getNickname() + " choose " + up4.getPlayerColour().toString() + " as its color");


                        if(up4.getState() == State.DISTRIBUTION)
                            System.out.println("New state");
                    } catch (WrongNumberOfPlayersException e) {
                        System.out.println(e.getMessage());
                    } catch (DuplicateNicknameException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidPlacementException e) {
                        System.out.println(e.getMessage());
                    } catch (WrongInformationException e) {
                        System.out.println(e.getMessage());
                    } catch (NotYourTurnException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyDeckException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidSearchPositionException e) {
                        System.out.println(e.getMessage());
                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 5:
                    System.out.println("Stai procedendo a distribuire le carte. Attendere... ");

                    DistributeCardsEvent ev5 = new DistributeCardsEvent();
                    try {
                        vv.performEvent(ev5);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        CardsDistributedUpdate up5 = (CardsDistributedUpdate) update;

                        int[] sObs = up5.getSecretObjectives().get(nickname);
                        System.out.println("Hai le carte obiettivo numero " + sObs[0] + " e numero " + sObs[1]);

                    } catch (WrongNumberOfPlayersException e) {
                        System.out.println(e.getMessage());
                    } catch (DuplicateNicknameException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidPlacementException e) {
                        System.out.println(e.getMessage());
                    } catch (WrongInformationException e) {
                        System.out.println(e.getMessage());
                    } catch (NotYourTurnException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyDeckException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidSearchPositionException e) {
                        System.out.println(e.getMessage());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }


                    break;
                case 6:
                    System.out.println("Stai procedendo a scegliere il tuo obiettivo.");
                    System.out.println("Scegli quale obiettivo vuoi (0 il primo / 1 il secondo): ");
                    int choice1 = Integer.parseInt(myObj.nextLine());
                    if(choice1 == 0)
                        selectedSide = true;
                    else if(choice1 == 1) {
                        selectedSide = false;
                    }

                    SelectObjectiveEvent ev6 = new SelectObjectiveEvent(nickname, selectedSide);
                    try {
                        vv.performEvent(ev6);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        ObjectiveSelectedUpdate up6 = (ObjectiveSelectedUpdate) update;
                        if(up6.getNickname().equals(nickname))
                            System.out.println("Hai scelto il tuo obiettivo, quello con il numero: "+ up6.getSecretObjective());

                        if(up6.getState() == State.PLACING)
                            System.out.println("Inizia la partita!");
                    } catch (WrongNumberOfPlayersException e) {
                        System.out.println(e.getMessage());
                    } catch (DuplicateNicknameException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidPlacementException e) {
                        System.out.println(e.getMessage());
                    } catch (WrongInformationException e) {
                        System.out.println(e.getMessage());
                    } catch (NotYourTurnException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyDeckException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidSearchPositionException e) {
                        System.out.println(e.getMessage());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }



                    break;
                case 7:
                    System.out.println("Attorno a che posizione vuoi vedere dove piazzare?");
                    System.out.println("coordinata x: ");
                    int x = Integer.parseInt(myObj.nextLine());
                    System.out.println("coordinata y: ");
                    int y = Integer.parseInt(myObj.nextLine());
                    GetPlaceablePositionsEvent ev7 = new GetPlaceablePositionsEvent(nickname, x, y);
                    try {
                        vv.performEvent(ev7);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        PlaceablePositionsReturnedUpdate up7 = (PlaceablePositionsReturnedUpdate) update;

                        List<Coordinate> coordinates = up7.getAvailablePositions();
                        System.out.println("Queste sono le coordinate disponibili: ");
                        for(Coordinate c : coordinates)
                            System.out.println("< " + c.getX() + ", " + c.getY() + ">");

                    } catch (WrongNumberOfPlayersException e) {
                        System.out.println(e.getMessage());
                    } catch (DuplicateNicknameException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidPlacementException e) {
                        System.out.println(e.getMessage());
                    } catch (WrongInformationException e) {
                        System.out.println(e.getMessage());
                    } catch (NotYourTurnException e) {
                        System.out.println(e.getMessage());
                    } catch (EmptyDeckException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidSearchPositionException e) {
                        System.out.println(e.getMessage());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 8:
                    System.out.println("Che carta vuoi piazzare dalla mano?");
                    int card = Integer.parseInt(myObj.nextLine());
                    System.out.println("In che posizione vuoi piazzare? Coordinata x: ");
                    int xCard = Integer.parseInt(myObj.nextLine());
                    System.out.println("In che posizione vuoi piazzare? Coordinata y: ");
                    int yCard = Integer.parseInt(myObj.nextLine());
                    System.out.println("Scegli il lato (0 fronte/1 retro): ");
                    int lato = Integer.parseInt(myObj.nextLine());
                    if(lato == 0)
                        selectedSide = true;
                    else if(lato == 1)
                        selectedSide = false;

                    PlaceCardEvent ev8 = new PlaceCardEvent(nickname, card, selectedSide, xCard, yCard);


                    try {
                        vv.performEvent(ev8);

                        try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        CardPlacedUpdate up8 = (CardPlacedUpdate) update;


                        System.out.println("Carta piazzata da" + up8.getNickname());
                        System.out.println("Punti ricevuti " + up8.getPoints());
                        System.out.println("La carta piazzata era la num " + up8.getIndex());

                    } catch (WrongNumberOfPlayersException e) {
                        throw new RuntimeException(e);
                    } catch (DuplicateNicknameException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidPlacementException e) {
                        throw new RuntimeException(e);
                    } catch (WrongInformationException e) {
                        throw new RuntimeException(e);
                    } catch (NotYourTurnException e) {
                        throw new RuntimeException(e);
                    } catch (EmptyDeckException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidSearchPositionException e) {
                        throw new RuntimeException(e);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 9:

                    System.out.println("Che carta vuoi pescare?");
                    int indexDrawing = Integer.parseInt(myObj.nextLine());

                    DrawCardEvent ev9 = new DrawCardEvent(nickname, indexDrawing);

                    try {
                        vv.performEvent(ev9);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        CardDrawnUpdate up9 = (CardDrawnUpdate) update;


                        System.out.println("Carta pescata da" + up9.getNickname());
                        System.out.println("La carta pescata era la num " + up9.getIndexDrawnCard());

                        if(up9.getRemaningRounds() < 3)
                            System.out.println("Siamo nella fase finale del gioco!");

                    } catch (WrongNumberOfPlayersException e) {
                        throw new RuntimeException(e);
                    } catch (DuplicateNicknameException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidPlacementException e) {
                        throw new RuntimeException(e);
                    } catch (WrongInformationException e) {
                        throw new RuntimeException(e);
                    } catch (NotYourTurnException e) {
                        throw new RuntimeException(e);
                    } catch (EmptyDeckException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidSearchPositionException e) {
                        throw new RuntimeException(e);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    System.out.println("Scrivi il destinatario o all se vuoi scrivere a tutti: ");
                    String addressee = myObj.nextLine();
                    boolean publicMess = false;
                    if(addressee.equals("all"))
                        publicMess = true;

                    System.out.println("Scrivi il tuo messaggio: ");
                    String message = myObj.nextLine();

                    ChatEvent ev12 = new ChatEvent(nickname, addressee, publicMess, message);
                    try {
                        vv.performEvent(ev12);

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        ChatUpdate up12 = (ChatUpdate) update;

                        System.out.println("Messaggio di " + up12.getSender() + ": " + up12.getChatMessage());

                    } catch (WrongNumberOfPlayersException e) {
                        throw new RuntimeException(e);
                    } catch (DuplicateNicknameException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidPlacementException e) {
                        throw new RuntimeException(e);
                    } catch (WrongInformationException e) {
                        throw new RuntimeException(e);
                    } catch (NotYourTurnException e) {
                        throw new RuntimeException(e);
                    } catch (EmptyDeckException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidSearchPositionException e) {
                        throw new RuntimeException(e);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 13:
                    ChatUpdate up12 = (ChatUpdate) update;
                    if(!up12.isPublicMess())
                        System.out.println("Private message below");

                    System.out.println("Messaggio di " + up12.getSender() + ": " + up12.getChatMessage());
                    break;
                default:
                    return;
            }

        }while(true);
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
