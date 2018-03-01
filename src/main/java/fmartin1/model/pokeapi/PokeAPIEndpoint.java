package fmartin1.model.pokeapi;


public class PokeAPIEndpoint {
    private Integer _count;
    private String _previous;
    private String _next;
    private PokeAPINamedResource[] _results;

    public Integer getCount() {
        return _count;
    }

    public void setCount(Integer count) {
        _count = count;
    }

    public String getPrevious() {
        return _previous;
    }

    public void setPrevious(String previous) {
        _previous = previous;
    }

    public String getNext() {
        return _next;
    }

    public void setNext(String next) {
        _next = next;
    }

    public PokeAPINamedResource[] getResults() {
        return _results;
    }

    public void setResults(PokeAPINamedResource[] results) {
        _results = results;
    }
}
