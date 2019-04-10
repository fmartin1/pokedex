package fmartin1.pokedex.repository;

import fmartin1.pokedex.model.pokemon.Pokemon;
import fmartin1.pokedex.model.pokemon.generation.Generation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    @Cacheable("pokemon")
    Pokemon findByName(String name);

    @Cacheable("pokemon")
    Pokemon findById(int id);

    @Cacheable("pokemon")
    List<Pokemon> findByType1OrType2(String name, String name1);

    @Cacheable("pokemon")
    List<Pokemon> findByGeneration(Generation generation);
}
