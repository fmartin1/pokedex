package fmartin1.service;

import fmartin1.model.pokemon.Pokemon;
import fmartin1.model.pokemon.type.Type;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
class PokeCSVService {

    private static final String CACHE_PATH = "C:/pokedata/pokedata.csv";
    private static final int POKEMON_NAME_CSV_INDEX = 0;
    private static final int POKEMON_TYPE_1_CSV_INDEX = 1;
    private static final int POKEMON_TYPE_2_CSV_INDEX = 2;
    private static final int POKEMON_URL_CSV_INDEX = 3;

    LinkedHashMap<String, Pokemon> loadPokemonFromCSV() {
        LinkedHashMap<String, Pokemon> pokemonMap = new LinkedHashMap<>();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(CACHE_PATH));
            bufferedReader.lines()
                    .forEach((line) -> {
                        Pokemon pokemon = parsePokemonFromCSVLine(line);
                        pokemonMap.put(pokemon.getName(), pokemon);
                    });
        } catch (FileNotFoundException ignored) {
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignore) {
                }
            }
        }

        return pokemonMap;
    }

    void writePokemonToCSV(LinkedHashMap<String, Pokemon> pokemonMap) {
        try {
            File file = new File(CACHE_PATH);
            if (file.createNewFile() || (file.delete() && file.createNewFile())) {
                FileWriter fileWriter = new FileWriter(CACHE_PATH);
                for (Pokemon pokemon : pokemonMap.values()) {
                    fileWriter.append(csvStringOf(pokemon));
                }
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException ignore) {
        }
    }

    private String csvStringOf(Pokemon pokemon) {
        return pokemon.getName() + ","
                + pokemon.getType(Type.Slot.FIRST) + ","
                + pokemon.getType(Type.Slot.SECOND) + ","
                + pokemon.getUrl() + "\n";
    }

    private Pokemon parsePokemonFromCSVLine(String line) {
        String[] array = line.split(",");

        Pokemon pokemon = new Pokemon(
                array[POKEMON_NAME_CSV_INDEX],
                array[POKEMON_URL_CSV_INDEX]);

        List<Type> types = new ArrayList<>();
        types.add(new Type(array[POKEMON_TYPE_1_CSV_INDEX], 1));
        if (!"null".equals(array[POKEMON_TYPE_2_CSV_INDEX])) {
            types.add(new Type(array[POKEMON_TYPE_2_CSV_INDEX], 2));
        }
        pokemon.setTypes(types);

        return pokemon;
    }
}
