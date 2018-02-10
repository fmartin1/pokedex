package fmartin1.common;

import java.util.List;

public interface NamedAPIContainer {
    public <T> List<NamedAPIResource<T>> getNamedAPIResources();
}
