package gamePackage;

import Exceptions.HTMLGenerationFailure;
import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Game {
    static Player[] players;
    private static Team[] teams;
    private static int turns;
    static int mapFlag = 0;
    static int[] winners;

    private static Random ran = new Random(System.currentTimeMillis());
    private static File[] htmlFiles;

    static Scanner sc = new Scanner(System.in);


    //Start the game
    public static void main(String [] args) {
        System.out.println("Welcome to the Treasure Hunt !");
        startGame();
    }

    //Generates a new map to play on, players, etc.
    private static void startGame() {
        File mapsDir = new File("maps");
        if (!mapsDir.exists()) mapsDir.mkdir();

        //initialise map
        if(mapFlag == 0) while(!initialisePlayersAndMap());
        else Game.generateDefaultMap();

        //Keep track of winners
        winners = new int[players.length];

        // play game
        playTurns(winners);

        //winner found, show winner and end game
        endGame(winners);

    }

    //Initialises player count and map size
    static boolean initialisePlayersAndMap() {
        do {
            //Ask user for no. of players and map size, and place the players on the map in valid positions
            System.out.println("Please enter the amount of players \n----Players allowed: 2-8----");
            String a = sc.nextLine();

            System.out.println("Please enter the desired map size \n" +
                    "----Minimum map size for 2-4 Players = 5\n" +
                    "----Minimum map size for 5-8 Players = 8\n" +
                    "----Maximum map size = 50");
            String b = sc.nextLine();

            System.out.println("Do you want teams? Y/N");
            String teamChoice = sc.nextLine();
            String c;

            if(teamChoice.equals("Y")) {
                System.out.println("Please enter team count");
                c = sc.nextLine();
            }
            else c = a;

            System.out.println("Choose 1 for Safe Map, 2 for Hazardous Map");
            String mapType = sc.nextLine();

            try {
                int noOfPlayers = Integer.parseInt(a);
                if (!setNumberOfPlayers(noOfPlayers))
                    return false;

                int mapsize = Integer.parseInt(b);
                if (!Game.getMap().setMapSize(noOfPlayers, mapsize))
                    return false;

                int teamCount = Integer.parseInt(c);
                if(!createTeams(teamCount))
                    return false;

                //Get list of green tiles that lead to treasure and generate rest of the map
                List<Position> listOfLocationLeadingToTreasure = Game.getMap().generate();

                int type = Integer.parseInt(mapType);
                boolean mapSuccess = new MapDirector(type).buildMap();
                if(!mapSuccess)
                    return false;

                //Place players randomly on the valid green tiles - players could share position
                if(mapFlag != 1) {
                    for (int i = 0; i < noOfPlayers; i++) {
                        int randomIndex = ran.nextInt(listOfLocationLeadingToTreasure.size());
                        players[i].setStart(listOfLocationLeadingToTreasure.get(randomIndex));
                    }
                }
                else {
                    players[0].setStart(new Position(1, 0));
                    players[1].setStart(new Position(1, 0));
                }

                //Assign teams to players randomly
                ArrayList<Player> copy = new ArrayList<>(Arrays.asList(players));
                while(!copy.isEmpty()) {
                    int randomIndex;
                    for (Team t : teams) {
                        if(copy.isEmpty()) break;

                        randomIndex = ran.nextInt(copy.size());
                        t.subscribe(copy.get(randomIndex));
                        copy.remove(randomIndex);
                    }
                }
            }
            catch (NumberFormatException | LocationIsOutOfRange | MapSizeNotSet e) {
                System.out.println("Invalid input. Please enter an integer.");
                return false;
            }
        } while(teams == null);

        return true;
    }

    //Initialises players
    static boolean setNumberOfPlayers (int n) {
        if (n > 1 && n < 9) {
            players = new Player[n];
            for(int i = 0; i < n; i++)
                players[i] = new Player();

            return true;
        }

        System.out.println("Invalid number of Players");
        return false;
    }

    //Initialises Teams
    static boolean createTeams(int c) {
        if(c > 0 && c <= players.length) {
            teams = new Team[c];

            for(int i = 0; i < teams.length; i++)
                teams[i] = new Team();

            return true;
        }

        System.out.println("Invalid team count");
        return false;
    }

    //Generates html map files
    static void generateHTMLFiles() throws HTMLGenerationFailure {
        //Exception to be thrown
        HTMLGenerationFailure exc = new HTMLGenerationFailure("HTML File Generation failure");
        String green = "33cc33";
        String blue = "33ccff";
        String yellow = "ffff00";
        String tileCol = "808080";

        String playerImg = "";
        String absoulutePathImages;

        htmlFiles = new File[players.length];

        //Create files
        for(int i = 0; i < players.length; i++)
            htmlFiles[i] = new File("maps/map_player_" + i + ".html");

        //Path to images folder where player.png is
        absoulutePathImages = htmlFiles[0].getParentFile().getAbsolutePath() + "\\../images";

        //Generate table for every player
        for(int i = 0; i < players.length; i++) {
            //Preset styling
            StringBuilder html = new StringBuilder(
                    "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<body bgcolor=#0099ff>\n" +
                            "\n" +
                            "<style type=\"text/css\">\n" +
                            "table {\n" +
                            "\theight: " + Game.getMap().getMapSize() * 100 + "px;\n" +
                            "\twidth: " + Game.getMap().getMapSize() * 100 + "px;\n" +
                            "\tmargin: 1; padding: 0;\n" +
                            "\tborder-collapse: collapse;\n" +
                            "}\n" +
                            "td { \n" +
                            "\tborder: 2px solid #000000;\n" +
                            "\tborder-spacing: 0;\n" +
                            "\theight: 100px;\n" +
                            "\twidth: 100px;\n" +
                            "\tmargin: 0; padding: 0.5;\n" +
                            "}\n" +
                            "</style>" +
                            "<h2 align=center style=\"color:#ffffff\">" + "Player " + i + " Map" + "</h2>\n" +
                            "\n" +
                            "<table align=\"center\">\n"
            );

            StringBuilder rows = new StringBuilder();

            //Identify current tile color for map
            int[] currentTile = {0, 0};

            //For each row starting from the top row
            for (int j = Game.getMap().getMapSize() - 1; j >= 0; j--) {
                rows.append("<tr>");

                //Populate it with columns (so j = y coord, k = x coord)
                for (int k = 0; k < Game.getMap().getMapSize(); k++) {
                    currentTile[0] = k;
                    currentTile[1] = j;

                    //For every visited tile by current player
                    for(Position visited : players[i].getVisited())
                        if(Arrays.equals(visited.getCoordinates(), currentTile))
                            tileCol = setColour(new Position(currentTile), green, yellow, blue);

                    //Append generated column to current row
                    if(Arrays.equals(players[i].getPosition().getCoordinates(), currentTile))
                        playerImg = "<img style=\"height:100px;width:100px;vertical-align:bottom;\" src=\"" + absoulutePathImages + "/player.png\">";

                    //Add row to table
                    rows.append("<td style=\"height:100px\" style=\"width:100px\" bgcolor=").append(tileCol).append(">").append(playerImg).append("</td>");
                    playerImg = "";
                    tileCol = "808080";
                }

                rows.append("</tr>\n");
            }


            //Append to html page the current state of the table
            html.append(rows.toString()).append("</table>\n").append("\n").append("</body>\n").append("</html>");

            //Write the html text into a file
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("maps/map_player_" + i + ".html"));
                writer.write(html.toString());
                writer.close();
                System.out.println("Map generated for map_player" + i);
            }
            catch(IOException e) {
                System.out.println(e.getMessage() + "\nSomething unexpected happened when writing into HTML file for player " + i);
                throw exc;
            }
        }
    }

    static String setColour(Position p, String green, String yellow, String blue) {
        String tileCol = "808080";
        int k = p.getX();
        int j = p.getY();
        //If a visited tile matches the current tile to be generated, then it's true type can be revealed (player has seen this tile)
        try {
            switch (Game.getMap().getTileType(k, j)) {
                case 'G':
                    tileCol = green;
                    break;
                case 'T':
                    tileCol = yellow;
                    break;
                case 'B':
                    tileCol = blue;
                    break;
            }
        }
        catch(LocationIsOutOfRange e) {
            System.out.println(e.getMessage());
            return "Out of Range";
        }

        return tileCol;
    }

    //Delete generating html files
    static void deleteHTMLFiles(File[] files) {
        for(File f : files) {
            if(!f.delete()) {
                System.out.println("On cleaning HTML files, file " + f.getName() + "failed to delete.");
            }
        }
    }

    static void playTurns(int[] winners) {
        boolean winner = false;

        //While winner flag is false
        while(!winner) {
            //Generate HTML files for current state of the game
            try {
                generateHTMLFiles();
                System.out.println();
            } catch (HTMLGenerationFailure e) {
                System.out.println(e.getMessage() + "\nSince HTML maps failed to generate, the program shall now end.");
                System.exit(1);
            }

            //For each player in the game - ask for move and check if player has won
            int playerID = 0;
            for(int j = 0; j < teams.length; j++) {
                Player p;
                for (int i = 0; i < teams[j].getTeammates().size(); i++) {
                    p = teams[j].getTeammates().get(i);
                    System.out.println("Player " + i + " of Team " + j + " state your move (L -> Left, R -> Right, D -> Down, U -> Up) :");
                    String s;
                    char m = 'Z';
                    int k = 0;

                    do {
                        //While move is invalid
                        if (k == 1)
                            System.out.println("Invalid move, try again :");

                        s = sc.nextLine();


                        if (!s.equals("\\n") && !s.equals(""))
                            m = s.toCharArray()[0];

                        k = 1;
                    } while (!teams[j].movePlayer(p, m));

                    //If a player has found the treasure
                    if (Arrays.equals(p.getPosition().getCoordinates(), Game.getMap().treasureLocation.getCoordinates())) {
                        winner = true;
                        winners[playerID] = 1;
                    }

                    playerID++;
                }
            }
            turns++;
        }
    }

    static int endGame(int[] winners) {
        //Generate final maps for each player
        try {
            generateHTMLFiles();
            System.out.println();
        } catch(HTMLGenerationFailure e) {
            System.out.println(e.getMessage());
            System.out.println("Since HTML maps failed to generate, the program shall now end.");
            return 1;
        }

        //Declare game winner/s - since multiple players may stumble on the treasure at the same turn
        System.out.println("These are the winners!");
        for(int i = 0; i < winners.length; i++) {
            if(winners[i] == 1) {
                System.out.println("Player " + i);
            }
        }

        //Display amount of turns taken
        System.out.println("Turns taken : " + turns);
        System.out.println("Press Enter to finish the game.");

        //Clean up html files and exit
        sc.nextLine();
        deleteHTMLFiles(htmlFiles);
        return 0;
    }

    private static void generateDefaultMap() {
        try {
            initialisePlayersAndMap();
            Game.getMap().setTileType(0, 0, 'T');
            Game.getMap().setTileType(1, 1, 'B');
            Game.getMap().setTileType(1, 0, 'G');
            Game.getMap().setTileType(2,0, 'G');
            Game.getMap().treasureLocation = new Position(0, 0);
        }
        catch(LocationIsOutOfRange e) {return;}

        mapFlag = 1;
    }

    static Map getMap(){
        return Map.getMapInstance();
    }
}