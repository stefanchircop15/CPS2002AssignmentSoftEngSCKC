package gamePackage;

import Exceptions.LocationIsOutOfRange;

class MapDirector {
    private int type;

    MapDirector(int t) {
        type = t;
    }

    boolean buildMap() throws LocationIsOutOfRange {
        return build();
    }

    private boolean build() throws LocationIsOutOfRange {
        if (type == 1)
            return new SafeMapBuilder().getSuccess();
        else if (type == 2)
            return new HazardousMapBuilder().getSuccess();
        else return false;
    }
}
