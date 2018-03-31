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

    public static void main(String [] args) {
        System.out.println("Welcome to the Treasure Hunt !");

        startGame();
    }

    private static void startGame() {
        Scanner sc = new Scanner(System.in);

        map = new Map();

        while (map.getMapSize() == 0) {
            System.out.println("Please enter the amount of players \n----Players allowed: 2-8----");
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

                List<Position> listOfLocationLeadingToTreasure = map.generate();
                map.fillRemainingEmptyLocations();

                for(int i = 0; i < noOfPlayers; i++) {
                    int randomIndex = ran.nextInt(listOfLocationLeadingToTreasure.size());
                    players[i].setPosition(listOfLocationLeadingToTreasure.get(randomIndex));
                }
            }
            catch (NumberFormatException | LocationIsOutOfRange | MapSizeNotSet e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }

        int[] winners = new int[players.length];

        boolean winner = false;

        //While winner flag is false
        while(!winner) {
            //Generate HTML files for current state of the game
            try {
                generateHTMLFiles();
            } catch (HTMLGenerationFailure e) {
                System.out.println(e.getMessage());
                System.out.println("Since HTML maps failed to generate, the program shall now end.");
                System.exit(1);
            }

            //For each player in the game - ask for move and check if player has won
            for(int i = 0; i < players.length; i++) {
                System.out.println("Player " + i + " state your move :");
                char m = sc.nextLine().toCharArray()[0];

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

        System.out.println("These are the winners!");
        for(int i = 0; i < winners.length; i++) {
            if(winners[i] == 1) {
                System.out.println("Player " + i);
            }
        }

        deleteHTMLFiles();
        System.exit(0);
    }

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

    private static void generateHTMLFiles() throws HTMLGenerationFailure {
        HTMLGenerationFailure exc = new HTMLGenerationFailure("HTML File Generation failure");
        String green = "003300";
        String blue = "003366";
        String yellow = "ffff00";
        String tileCol = "8f8970";

        String playerImg = "";
        String absoulutePathImages = "";

        htmlFiles = new File[players.length];

        //Create files
        for(int i = 0; i < players.length; i++)
            htmlFiles[i] = new File("maps/map_player_" + i + ".html");

        absoulutePathImages = htmlFiles[0].getParentFile().getAbsolutePath().toString() + "\\../images";
        //Generate tables
        for(int i = 0; i < players.length; i++) {
            StringBuilder html = new StringBuilder(
                    "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<body>\n" +
                    "\n" +
                    "<style type=\"text/css\">\n" +
                    "table {\n" +
                    "\theight: " + map.getMapSize() * 100 + "px;\n" +
                    "\twidth: " + map.getMapSize() * 100 + "px;\n" +
                    "\tmargin: 0; padding: 0;\n" +
                    "\tborder-collapse: collapse;\n" +
                    "}\n" +
                    "td { \n" +
                    "\tborder: 1px solid #CC3;\n" +
                    "\tborder-spacing: 0;\n" +
                    "\theight: 100px;\n" +
                    "\twidth: 100px;\n" +
                    "\tmargin: 0; padding: 0;\n" +
                    "}\n" +
                    "</style>" +
                    "<h2>" + "Player " + i + " Map" + "</h2>\n" +
                    "\n" +
                    "<table align=\"center\">\n");

            StringBuilder rows = new StringBuilder();

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
                                switch (map.getTileType(j, k)) {
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
                            playerImg = "<img style=\"height:100px;width:100px;opacity:0.4;filter:alpha(opacity=40);vertical-align:bottom;\" src=\"" + absoulutePathImages + "/player.png\">";

                        rows.append("<td style=\"height:100px\" style=\"width:100px\" bgcolor=" + tileCol + ">" + playerImg + "</td>");
                        playerImg = "";
                        tileCol = "8f8970";
                    }

                    rows.append("</tr>\n");
                }
            }
            catch(LocationIsOutOfRange e) {
                System.out.println("Something unexpected happened when generating HTML file for player " + i);
                System.out.println(e.getMessage());
                throw exc;
            }

            html.append(
                    rows.toString() +
                    "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");

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

    private static void deleteHTMLFiles() {
        for(File f : htmlFiles) {
            if(!f.delete()) {
                System.out.println("On cleaning HTML files, file " + f.getName() + "failed to delete.");
            }
        }
    }
}