package fmartin1.service;

import fmartin1.model.common.NamedAPIResource;
import fmartin1.model.Pokemon;
import fmartin1.model.PokemonType;
import fmartin1.model.type.Type;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedHashMap;

@Service
public class PokeDataService {
    private static final String CACHE_PATH = "C:/pokedata/pokedata.csv";
    private static final int POKEMON_NAME_CSV_INDEX = 0;
    private static final int POKEMON_TYPE_1_CSV_INDEX = 1;
    private static final int POKEMON_TYPE_2_CSV_INDEX = 2;
    private static final int POKEMON_URL_CSV_INDEX = 3;

    private LinkedHashMap<String, Pokemon> _pokemonMap = new LinkedHashMap<>();

    public LinkedHashMap<String, Pokemon> getPokemonMap() {
        if(_pokemonMap.isEmpty()) {
            _pokemonMap = getPokemonFromCache();
        }
        return _pokemonMap;
    }

    private LinkedHashMap<String, Pokemon> getPokemonFromCache() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();
        BufferedReader bufferedReader = null;
        try {
            //todo: validate cache file exists and has content, if not load from API
            bufferedReader = new BufferedReader(new FileReader(CACHE_PATH));
            bufferedReader.lines()
                    .forEach((line) -> {
                        Pokemon pokemon = parsePokemonFromCSVLine(line);
                        pokemonMap.put(pokemon.getName(), pokemon);
                    });
        } catch (FileNotFoundException e) {
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

    //todo: pasarlo a otro service especializado en CSV
    public static void writePokemonToCSV(LinkedHashMap<String, Pokemon> pokemonMap) {
        try {
            File file = new File(CACHE_PATH);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(CACHE_PATH);
            for (Pokemon pokemon : pokemonMap.values()) fileWriter.append(csvStringOf(pokemon));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
                array[POKEMON_NAME_CSV_INDEX],
                array[POKEMON_URL_CSV_INDEX]);

        PokemonType type = new PokemonType();
        type.setSlot(1);
        type.setType(new NamedAPIResource<Type>());
        type.getType().setName(array[POKEMON_TYPE_1_CSV_INDEX]);

        PokemonType type2 = null;
        if (!"null".equals(array[POKEMON_TYPE_2_CSV_INDEX])) {
            type2 = new PokemonType();
            type2.setSlot(2);
            type2.setType(new NamedAPIResource<Type>());
            type2.getType().setName(array[POKEMON_TYPE_2_CSV_INDEX]);
        }

        pokemon.setTypes(new PokemonType[]{type, type2});

        return pokemon;
    }
}
