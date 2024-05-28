package it.polimi.ingsw.am12.Utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.am12.Client.UI.CLI.CliCard;
import it.polimi.ingsw.am12.Client.UI.CLI.CliObjCard;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.Side;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.ObjectiveCard;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;


/**
 * This utility class is used for parsing ObjectiveCards and GameCards from .json files
 */
public class JSONParser {
    Gson gson;

    /**
     * Class constructor
     */
    public JSONParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Side.class, new JSONDeserializerWithClass<Side>())
                .registerTypeAdapter(ObjectiveCard.class, new JSONDeserializerWithClass<ObjectiveCard>());
        this.gson = builder.create();
    }

    /**
     * Parse a list of ObjectiveCards from a .json file in resources
     * @return a list of ObjectiveCards
     */
    public List<ObjectiveCard> parseObjectiveCards(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("objectiveCards.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type listOfObjectiveCards = new TypeToken<List<ObjectiveCard>>(){}.getType();
        return gson.fromJson(reader, listOfObjectiveCards);
    }

    /**
     * Parse a list of GameCard from a .json file in resources
     * @param fileName the name of the JsonFile
     * @return a list of ResourceCards
     */
    public List<GameCard> parseGameCards(String fileName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type listOfGameCards = new TypeToken<List<GameCard>>() {
        }.getType();
        return gson.fromJson(reader, listOfGameCards);
    }

    /**
     * Parse a list of GameCards from a goldCards.json
     * @return a list of GameCard
     */
    public List<GameCard> parseGoldCards() {
        return parseGameCards("goldCards.json");
    }

    /**
     * Parse a list of GameCards from a resourceCards.json
     * @return a list of GameCard
     */
    public List<GameCard> parseResourceCards() {
        return parseGameCards("resourceCards.json");
    }

    /**
     * Parse a list of GameCards from a startCards.json
     * @return a list of GameCard
     */
    public List<GameCard> parseStartCards() {
        return parseGameCards("startCards.json");
    }

    /**
     * Parse a list of CliCards from a clicards.json
     * @return a list of CliCards
     */
    public List<CliCard> parseCLICards(){
        InputStream is = getClass().getClassLoader().getResourceAsStream("cliCards.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type listofCLIcards = new TypeToken<List<CliCard>>() {
        }.getType();
        return gson.fromJson(reader, listofCLIcards);
    }

    /**
     * Parse a list of CliObjectiveCards from a cliObjCards.json
     * @return a list of CliObjectiveCards
     */
    public List<CliObjCard> parseCLIObjectiveCards(){
        InputStream is = getClass().getClassLoader().getResourceAsStream("cliObjCards.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type listofCLIObjcards = new TypeToken<List<CliObjCard>>() {
        }.getType();
        return gson.fromJson(reader, listofCLIObjcards);
    }
}

/**
 * Helper class used to deserialize json objects with the correct dynamic type.
 * It creates each object as an instance of the subclass that is indicated
 * in the "type" field in the json.
 * @param <T> the static type of the object (superclass).
 */
class JSONDeserializerWithClass<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get("type").getAsString();

        try {
            Class<?> class1 = Class.forName(className);
            return context.deserialize(jsonObject, class1);
        }
        catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
