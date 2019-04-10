package fmartin1.pokedex;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import fmartin1.pokedex.repository.PokemonRepository;
import fmartin1.pokedex.service.PokedexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CachingTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokedexService pokedexService;

    @Test
    public void cachePostPokemonTest() {
        Pokemon bulbasaur = new Pokemon();
        String pokemonName = "bulbasaur";
        bulbasaur.setName(pokemonName);
        bulbasaur.setType1("grass");
        bulbasaur.setGeneration(Generation.GENERATION_1);
        bulbasaur.setId(1);
        bulbasaur.setUrl("http://test/com");

        pokedexService.postPokemon(bulbasaur);

        pokemonRepository.delete(bulbasaur);

        bulbasaur = pokedexService.findPokemon(pokemonName);

        assertNotNull(bulbasaur);
        assertEquals(pokemonName, bulbasaur.getName());
        assertSame(Generation.GENERATION_1, bulbasaur.getGeneration());

        bulbasaur.setType2("poison");
        pokedexService.postPokemon(bulbasaur);
        assertEquals("poison", pokedexService.findPokemon(pokemonName).getType2());

        System.out.println();
    }
}
