package gamePackage;

import java.util.Scanner;

public class Game {
    static Player[] players;
    static Map map;
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);

        int truns, noOfPlayers = 0, mapsize=0;

         map= new Map();

        System.out.println("Welcome to the Treasure Hunt !");

        while (map.getMapSize() == 0) {
            System.out.println("Please enter the amount of players \n----Players allowed: 2-8----");
            try {
                String input = sc.next();
                noOfPlayers = Integer.parseInt(input);
                if (!setNumberOfPlayers(noOfPlayers))
                    continue;
                System.out.println("Please enter the desired map size \n" +
                        "----Minimum map size for 2-4 Players = 5\n" +
                        "----Minimum map size for 5-8 Players = 8\n" +
                        "----Maximum map size = 50");
                input = sc.next();
                mapsize = Integer.parseInt(input);

                if(!map.setMapSize(noOfPlayers, mapsize))
                    continue;

                map.generate();





            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            /*}catch (InvalidNumberOfPlayersException e){
                System.out.println(e);
            }*/

            }


        }
    }


    public static int startGame() {
        return 0;
    }
     static boolean setNumberOfPlayers (int n) {
         if (n > 1 && n < 9) {
             players = new Player[n];
             return true;
         }
         System.out.println("Invalid number of Players");
         return false;
     }

    public static void generateHTMLFiles(){};
}