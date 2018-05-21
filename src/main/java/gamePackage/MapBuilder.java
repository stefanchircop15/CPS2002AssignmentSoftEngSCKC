package gamePackage;

import Exceptions.LocationIsOutOfRange;

public abstract class MapBuilder {
    MapBuilder(int t) {
        Map.setMapType(t);
    }

    abstract boolean getSuccess();

    public abstract void fillRemainingEmptyLocations() throws LocationIsOutOfRange;
}
