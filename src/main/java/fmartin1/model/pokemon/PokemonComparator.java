package fmartin1.model.pokemon;


import fmartin1.model.pokemon.type.Type;

import java.util.Comparator;
import java.util.Optional;

public class PokemonComparator implements Comparator<Pokemon> {
    private Criteria _sortCriteria;

    public PokemonComparator(Criteria sortCriteria) {
        super();
        _sortCriteria = sortCriteria;
    }

    @Override
    public int compare(Pokemon o1, Pokemon o2) {
        int byCriteria = 0;
        int NO_RESULT = 0;
        switch (_sortCriteria) {
            case TYPE:
                byCriteria = compareByType(o1, o2, Type.Slot.FIRST);
                break;
            case TYPE1_TYPE2:
                byCriteria = compareByType(o1, o2, Type.Slot.FIRST);
                if (byCriteria == NO_RESULT) byCriteria = compareByType(o1, o2, Type.Slot.SECOND);
                break;
            case TYPE_GENERATION:
                byCriteria = compareByType(o1, o2, Type.Slot.FIRST);
                if (byCriteria == NO_RESULT) byCriteria = compareByGeneration(o1, o2);
                break;
            case TYPE1_TYPE2_GENERATION:
                byCriteria = compareByType(o1, o2, Type.Slot.FIRST);
                byCriteria += byCriteria == NO_RESULT ? compareByType(o1, o2, Type.Slot.SECOND) : NO_RESULT;
                byCriteria += byCriteria == NO_RESULT ? compareByGeneration(o1, o2) : NO_RESULT;
                break;
            case GENERATION:
                byCriteria = compareByGeneration(o1, o2);
                break;
            case NATURAL:
            default:
                break;
        }
        return byCriteria != NO_RESULT ? byCriteria : o1.compareTo(o2);
    }

    private int compareByGeneration(Pokemon o1, Pokemon o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }

    private int compareByType(Pokemon o1, Pokemon o2, Type.Slot slot) {
        Optional<Type> o1Type = o1.getType(slot);
        Optional<Type> o2Type = o2.getType(slot);
        if (o1Type.isPresent() && o2Type.isPresent()) {
            return o1Type.get().compareTo(o2Type.get());
        } else if (o1Type.isPresent()) {
            return -1;
        } else if (o2Type.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    public enum Criteria {
        TYPE, TYPE_GENERATION, TYPE1_TYPE2, TYPE1_TYPE2_GENERATION, GENERATION, NATURAL
    }
}
