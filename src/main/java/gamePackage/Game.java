package gamePackage;

import Exceptions.HTMLGenerationFailure;
import Exceptions.LocationIsOutOfRange;
import Exceptions.MapSizeNotSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static Player[] players;
    static Map map;
    private static int turns;

    private static Random ran = new Random(System.currentTimeMillis());
    private static File[] htmlFiles;

    //Start the game
    public static void main(String [] args) {
        System.out.println("Welcome to the Treasure Hunt !");

        startGame();
    }

    //Generates a new map to play on, players, etc.
    private static void startGame() {
        Scanner sc = new Scanner(System.in);

        map = new Map();

        //While map not generated
        while (map.getMapSize() == 0) {
            System.out.println("Please enter the amount of players \n----Players allowed: 2-8----");
            //Ask user for no. of players and map size, and place the players on the map in valid positions
            try {
                String input = sc.nextLine();
                int noOfPlayers = Integer.parseInt(input);
                if (!setNumberOfPlayers(noOfPlayers))
                    continue;

                System.out.println("Please enter the desired map size \n" +
                        "----Minimum map size for 2-4 Players = 5\n" +
                        "----Minimum map size for 5-8 Players = 8\n" +
                        "----Maximum map size = 50");

                input = sc.nextLine();

                int mapsize = Integer.parseInt(input);

                if (!map.setMapSize(noOfPlayers, mapsize))
                    continue;

                //Get list of green tiles that lead to treasure and generate rest of the map
                List<Position> listOfLocationLeadingToTreasure = map.generate();
                map.fillRemainingEmptyLocations();

                //Place players randomly on the valid green tiles - players could share position
                for(int i = 0; i < noOfPlayers; i++) {
                    int randomIndex = ran.nextInt(listOfLocationLeadingToTreasure.size());
                    players[i].setStart(listOfLocationLeadingToTreasure.get(randomIndex));
                    System.out.println(map.getTileType(players[i].getPosition().getX(), players[i].getPosition().getY()));
                }
            }
            catch (NumberFormatException | LocationIsOutOfRange | MapSizeNotSet e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }

        //Keep track of winners
        int[] winners = new int[players.length];

        boolean winner = false;

        //While winner flag is false
        while(!winner) {
            //Generate HTML files for current state of the game
            try {
                generateHTMLFiles();
                System.out.println();
            } catch (HTMLGenerationFailure e) {
                System.out.println(e.getMessage());
                System.out.println("Since HTML maps failed to generate, the program shall now end.");
                System.exit(1);
            }

            //For each player in the game - ask for move and check if player has won
            for(int i = 0; i < players.length; i++) {
                System.out.println("Player " + i + " state your move :");
                String s = sc.nextLine();
                char m;

                while(true) {
                    if(!s.equals("\\n")) {
                        m = s.toCharArray()[0];
                        break;
                    }
                }

                //While move is invalid
                while(!players[i].move(m)) {
                    System.out.println("Invalid move, try again :");
                    m = sc.nextLine().toCharArray()[0];
                }

                //If a player has found the treasure
                if(Arrays.equals(players[i].getPosition().getCoordinates(), map.treasureLocation.getCoordinates())) {
                    winner = true;
                    winners[i] = 1;
                }
            }

            turns++;
        }

        //Generate final maps for each player
        try {
            generateHTMLFiles();
            System.out.println();
        } catch (HTMLGenerationFailure e) {
            System.out.println(e.getMessage());
            System.out.println("Since HTML maps failed to generate, the program shall now end.");
            System.exit(1);
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
        System.exit(0);
    }

    //Initialises players
    static boolean setNumberOfPlayers (int n) {
        if (n > 1 && n < 9) {
            players = new Player[n];
            for(int i = 0; i < n; i++) {
                players[i] = new Player();
            }

            return true;
        }

        System.out.println("Invalid number of Players");
        return false;
    }

    //Generates html map files
    private static void generateHTMLFiles() throws HTMLGenerationFailure {
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

        //Path to images folder whwere player.png is
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
                    "\theight: " + map.getMapSize() * 100 + "px;\n" +
                    "\twidth: " + map.getMapSize() * 100 + "px;\n" +
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
            try {
                int[] currentTile = {0, 0};
                //For each row starting from the top row
                for (int j = map.getMapSize() - 1; j >= 0; j--) {
                    rows.append("<tr>");

                    //Populate it with columns (so j = y coord, k = x coord)
                    for (int k = 0; k < map.getMapSize(); k++) {
                        currentTile[0] = k;
                        currentTile[1] = j;

                        //For every visited tile by current player
                        for(Position visited : players[i].getVisited()) {
                            //If a visited tile matches the current tile to be generated, then it's true type can be revealed (player has seen this tile)
                            if(Arrays.equals(visited.getCoordinates(), currentTile)) {
                                switch (map.getTileType(k, j)) {
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
                                break;
                            }
                        }

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
            }
            catch(LocationIsOutOfRange e) {
                System.out.println("Something unexpected happened when generating HTML file for player " + i);
                System.out.println(e.getMessage());
                throw exc;
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
                System.out.println("Something unexpected happened when writing into HTML file for player " + i);
                System.out.println(e.getMessage());
                throw exc;
            }
        }
    }

    //Delete generating html files
    static void deleteHTMLFiles(File[] files) {
        for(File f : files) {
            if(!f.delete()) {
                System.out.println("On cleaning HTML files, file " + f.getName() + "failed to delete.");
            }
        }
    }
}