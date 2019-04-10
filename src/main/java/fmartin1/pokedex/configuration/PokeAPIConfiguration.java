package fmartin1.pokedex.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("pokeapi")
public class PokeAPIConfiguration {

    public static final String ENDPOINT_POKEMON = "pokemon";
    public static final String ENDPOINT_TYPE = "type";

    private String url;

    private String userAgent;

    private Map<String, String> endpoints;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Map<String, String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Map<String, String> endpoints) {
        this.endpoints = endpoints;
    }
}
