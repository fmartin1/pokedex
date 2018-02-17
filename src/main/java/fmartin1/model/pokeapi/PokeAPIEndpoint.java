package fmartin1.model.pokeapi;


public class PokeAPIEndpoint {
    private final Integer _count;
    private final String _previous;
    private final String _next;
    private final PokeAPINamedResource[] _results;

    public PokeAPIEndpoint(Integer count, String previous, String next, PokeAPINamedResource[] results) {
        _count = count;
        _previous = previous;
        _next = next;
        _results = results;
    }

    public Integer getCount() {
        return _count;
    }

    public String getPrevious() {
        return _previous;
    }

    public String getNext() {
        return _next;
    }

    public PokeAPINamedResource[] getResults() {
        return _results;
    }
}
