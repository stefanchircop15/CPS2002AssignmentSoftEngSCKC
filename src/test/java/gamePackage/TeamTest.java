package gamePackage;

import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

public class TeamTest {
    private Team team;


    @Before
    public void setUp() {
        team = new Team();
    }

    @After
    public void tearDown() {
        team = null;
    }

    @Test
    public void testSubscribe() {
        Player a = new Player();
        team.subscribe(a);
        Player b = new Player();
        team.subscribe(b);

        ArrayList<Player> expected = new ArrayList<>();
        expected.add(a);
        expected.add(b);

        assertArrayEquals(team.getTeammates().toArray(), expected.toArray());
    }

    @Test
    public void testUnsubscribe() {
        Player a = new Player();
        team.subscribe(a);
        Player b = new Player();
        team.subscribe(b);

        ArrayList<Player> expected = new ArrayList<>();

        team.unsubscribe(a);
        team.unsubscribe(b);

        assertArrayEquals(team.getTeammates().toArray(), expected.toArray());
    }

    @Test
    public void testUpdate() {
        Map.tearDownMap();
        Map instance = Map.getMapInstance();
        instance.setMapSize(5,10);
        instance.createEmptyMap();


        try {
            instance.generate();
            new MapDirector(2).buildMap();
        }
        catch(LocationIsOutOfRange | MapSizeNotSet e) {
            System.out.println(e.getMessage());
        }

        Position x = instance.listOfLocationLeadingToTreasure.get(0);
        Position y = instance.listOfLocationLeadingToTreasure.get(1);
        Player a = new Player();
        team.subscribe(a);
        Player b = new Player();
        team.subscribe(b);

        try {
            a.setStart(x);
            b.setStart(y);
            team.updatePlayers(a);
            team.updatePlayers(b);
        }
        catch(LocationIsOutOfRange e) {
            assumeNoException(e);
        }

        Set<Position> setA = new HashSet<>(a.getVisited());
        Set<Position> setB = new HashSet<>(b.getVisited());

        assertEquals(setA.toArray().length, setB.toArray().length);
    }

}