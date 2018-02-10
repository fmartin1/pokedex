package fmartin1.service;

import fmartin1.common.NamedAPIResource;
import fmartin1.pokemon.Pokemon;
import fmartin1.pokemon.PokemonType;
import fmartin1.pokemon.type.Type;
import fmartin1.util.PokeLog;

import java.io.*;
import java.util.LinkedHashMap;

public class PokeData {
    private static final String URI = "https://pokeapi.co/api/v2/";
    private static final String CACHE_PATH = "C:/pokedata/pokedata.csv";

    private static final int POKEMON_NAME   = 0;
    private static final int POKEMON_TYPE_1 = 1;
    private static final int POKEMON_TYPE_2 = 2;
    private static final int POKEMON_URL    = 3;

    private static PokeData pokeData;

    private PokeData() {
        super();
    }

    public static PokeData getInstance() {
        if(pokeData==null) pokeData = new PokeData();
        return pokeData;
    }

    public LinkedHashMap<String, Pokemon> getPokemon() {
        LinkedHashMap<String, Pokemon> pokemonMap = getPokemonFromCSV();
        if(pokemonMap.isEmpty()) {
            //pokemonMap = getPokemonFromAPI();
            if(!pokemonMap.isEmpty()) writePokemonToCSV(pokemonMap);
        }
        PokeLog.log("Found " + pokemonMap.size() + " results", false);
        return pokemonMap;
    }
/*
    private LinkedHashMap<String, Pokemon> getPokemonFromAPI() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();
        PokeLog.log("Getting ordered map of all pokemon...", true);
        Endpoint endpoint = pokeApiTarget.path("pokemon")
                .queryParam("limit",802)
                .request(MediaType.APPLICATION_JSON)
                .get(Endpoint.class);
        for(NamedAPIResource<Pokemon> pokeResource : endpoint.getResults()) {
            pokemonMap.put(
                    pokeResource.getName(),
                    new Pokemon(pokeResource.getName(), pokeResource.getUrl()) );
        }

        PokeLog.log("Adding type to pokemon",true);
        endpoint = consume("type/", Endpoint.class);

        for(NamedAPIResource<Type> typeResource : endpoint.getResults()) {

            Type type = consume("type/" + typeResource.getName(), Type.class);
            List<Pokemon> pokemonList = new ArrayList<>(pokemonMap.values());
            type.getPokemon().stream()
                    .filter(typePokemon -> typePokemon.getId()<=pokemonMap.size())
                    .forEach((typePokemon) -> {
                        int slot = typePokemon.getSlot();
                        pokemonList
                                .get( typePokemon.getId()-1 )
                                .getTypes()[slot-1] = new PokemonType(slot,typeResource);
                    });
        }
        PokeLog.log("Done with types\n",false);

        return pokemonMap;
    }
*/
    private LinkedHashMap<String, Pokemon> getPokemonFromCSV() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();
        PokeLog.log("Getting ordered map of all pokemon...", true);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(CACHE_PATH));
            bufferedReader.lines()
                    .forEach((line) -> {
                        Pokemon pokemon = parsePokemonFromCSVLine(line);
                        pokemonMap.put(pokemon.getName(), pokemon);
                    });
        } catch (FileNotFoundException e) {
            PokeLog.log(CACHE_PATH + " not found");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return pokemonMap;
    }
/*
    private <T> T consume(String resource, Class<T> resourceClass) {
        PokeLog.log("Retrieving " + pokeApiTarget.getUri() + resource);
        return pokeApiTarget.path(resource)
                .request(MediaType.APPLICATION_JSON)
                .get(resourceClass);
    }*/

    /*
        LOAD/SAVE FROM/TO CSV FILE.
     */
    public static void writePokemonToCSV(LinkedHashMap<String, Pokemon> pokemonMap) {
        try {
            File file = new File(CACHE_PATH);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(CACHE_PATH);
            for(Pokemon pokemon : pokemonMap.values()) fileWriter.append(csvStringOf(pokemon));
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException ioe) { ioe.printStackTrace();}
    }

    private static String csvStringOf(Pokemon pokemon) {
        StringBuilder stringBuilder = new StringBuilder(pokemon.getName());
        stringBuilder.append("," + pokemon.getTypeName(1));
        stringBuilder.append("," + pokemon.getTypeName(2));
        stringBuilder.append("," + pokemon.getUrl());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private static Pokemon parsePokemonFromCSVLine(String line) {
        String[] array = line.split(",");
        Pokemon pokemon = new Pokemon(
                array[POKEMON_NAME],
                array[POKEMON_URL]);

        PokemonType type = new PokemonType();
        type.setSlot(1);
        type.setType(new NamedAPIResource<Type>());
        type.getType().setName(array[POKEMON_TYPE_1]);

        PokemonType type2 = null;
        if(!"null".equals(array[POKEMON_TYPE_2])) {
            type2 = new PokemonType();
            type2.setSlot(2);
            type2.setType(new NamedAPIResource<Type>());
            type2.getType().setName(array[POKEMON_TYPE_2]);
        }

        pokemon.setTypes(new PokemonType[]{type,type2});

        return pokemon;
    }
}
