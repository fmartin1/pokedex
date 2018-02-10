package fmartin1.pokemon.type;


import fmartin1.pokemon.Pokemon;

import java.util.Comparator;

public class PokemonComparator implements Comparator<Pokemon> {
    private Criteria criteria;

    public enum Criteria {
        TYPE,TYPE_GENERATION,TYPE1_TYPE2,TYPE1_TYPE2_GENERATION, GENERATION, NATURAL;

    }

    public PokemonComparator(Criteria criteria) {
        super();
        this.criteria = criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public int compare(Pokemon o1, Pokemon o2) {
        int byCriteria = 0;
        switch(criteria) {
            case TYPE:
                byCriteria = compareByType(o1,o2);
                break;
            case TYPE1_TYPE2:
                byCriteria =  compareByType(o1,o2);
                if (byCriteria==0) byCriteria = compareByType2(o1,o2);
                break;
            case TYPE_GENERATION:
                byCriteria = compareByType(o1,o2);
                if (byCriteria==0) byCriteria = compareByGeneration(o1,o2);
                break;
            case TYPE1_TYPE2_GENERATION:
                byCriteria =  compareByType(o1,o2);
                byCriteria += byCriteria==0 ? compareByType2(o1,o2):0;
                byCriteria += byCriteria==0 ? compareByGeneration(o1,o2):0;
                break;
            case GENERATION:
                byCriteria = compareByGeneration(o1,o2);
                break;
            case NATURAL:
            default: break;
        }
        return byCriteria!=0 ? byCriteria : o1.compareTo(o2);
    }

    private int compareByGeneration(Pokemon o1, Pokemon o2) {
        return o1.getGeneration().compareTo(o2.getGeneration());
    }

    private int compareByType(Pokemon o1, Pokemon o2) {
        return o1.getTypeName(1).compareTo(o2.getTypeName(1));
    }

    private int compareByType2(Pokemon o1, Pokemon o2) {
        String o1t = o1.getTypeName(2);
        String o2t = o2.getTypeName(2);
        if (o1t==null) return o2t==null ? 0:1;
        else return o2t==null ? -1:o1.getTypeName(2).compareTo(o2.getTypeName(2));
    }
}
