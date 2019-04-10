package fmartin1.pokedex.controller;

import fmartin1.pokedex.service.PokeRepoService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("refresh")
public class RefreshDBController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshDBController.class);

    private final PokeRepoService pokeRepoService;

    public RefreshDBController(PokeRepoService pokeRepoService) {
        this.pokeRepoService = pokeRepoService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshDB() throws NotFoundException {
        LOGGER.info("dbRefresh requested");
        pokeRepoService.refreshDB();
    }

    @PostMapping("/{pokemonName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshPokemon(@PathVariable String pokemonName) {
        LOGGER.info("Refresh {}", pokemonName);
        pokeRepoService.refreshPokemon(pokemonName);
    }
}
