package fmartin1.pokedex.repository;

import fmartin1.pokedex.model.pokemon.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

}
