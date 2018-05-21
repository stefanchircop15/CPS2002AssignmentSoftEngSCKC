package gamePackage;

import java.util.ArrayList;

class Team {
    private ArrayList<Player> teammates;

    Team() {
        teammates = new ArrayList<>();
    }

    boolean movePlayer(Player p, char m) {
        boolean result = p.move(m);

        updatePlayers(p);

        return result;
    }

    ArrayList<Player> getTeammates() {
        return teammates;
    }

    void updatePlayers(Player p) {
        //Update player visited
        for(Player x : teammates) {
            if(x == p) continue;
            if(!x.getVisited().contains(p.getStart()))
                x.addVisited(p.getStart());
            if(!x.getVisited().contains(p.getPosition()))
                x.addVisited(new Position(p.getPosition().getX(), p.getPosition().getY()));
        }
    }

    void subscribe(Player p) {
        if(!teammates.contains(p))
            teammates.add(p);
    }

    void unsubscribe(Player p) {
        if(teammates.contains(p))
            teammates.remove(p);
    }
}
