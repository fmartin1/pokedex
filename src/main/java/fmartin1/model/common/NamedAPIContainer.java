package fmartin1.model.common;

import java.util.List;

public interface NamedAPIContainer {
    public <T> List<NamedAPIResource<T>> getNamedAPIResources();
}
