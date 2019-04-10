package fmartin1.pokedex.controller;

import fmartin1.pokedex.service.PokedexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("triggers")
public class TriggersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriggersController.class);

    @Autowired
    private PokedexService pokedexService;

    @PostMapping("dbRefresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void triggerDbRefresh() {
        LOGGER.info("dbRefresh requested");
        pokedexService.dbRefresh();
    }
}
