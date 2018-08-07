import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BattleshipGame {
    public static void main (String [] args) {
        String[][] map = new String[10][10];
        Boolean[][] guessed = new Boolean[10][10];
        int[] ships = {5, 5};
        ArrayList<String> playerGuesses = new ArrayList<>();
        ArrayList<String> cpuGuesses = new ArrayList<>();

        intro();
        printMap(map);
        deployShips(map);
        deployCShips(map);
        turn(map,ships,playerGuesses,cpuGuesses);
        if (ships[0]==0){
            System.out.println("Welp, you lost... :(");
        } else {
            System.out.println("Yeaaahh! You won, man! :)");
        }



    }

    public static void intro() {
        System.out.println("**** Welcome to Battle Ships game! ****" +
                "\n\nRight now the sea is empty.\n");

    }

    //prints current state of map
    public static void printMap (String[][] array) {
        System.out.println("\n   0123456789   ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(i+" |");
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == null || array[i][j].equals("2")){
                    System.out.print(" ");
                } else if (array[i][j].equals("1")){
                    System.out.print("@");
                } else {
                    System.out.print(array[i][j]);
                }
            }
            System.out.println("| "+i);
        }
        System.out.println("   0123456789   \n");

    }

    //deploys player ships
    public static void deployShips (String[][] pMap) {
        Scanner input = new Scanner(System.in);
        int ships = 1;

        System.out.println("\nDeploy your ships:");

        while (ships <= 5) {
            System.out.print("Enter X coordinate for your "+ships+" ship: ");
            int x = input.nextInt();
            System.out.print("Enter Y coordinate for your "+ships+" ship: ");
            int y = input.nextInt();
            if (x>=0 && x<=9 && y>=0 && y<=9 && pMap[x][y] == null){
                pMap[x][y] = "1";
                ships++;
            }
        }
        printMap(pMap);
    }

    //deploy cpu ships
    public static void deployCShips(String[][] cMap) {
        int ships = 1;
        Random generator = new Random();

        System.out.println("\nComputer is deploying ships");
        while (ships <= 5) {
            int x = generator.nextInt(10);
            int y = generator.nextInt(10);
            if (x>=0 && x<=9 && y>=0 && y<=9 && cMap[x][y] == null){
                cMap[x][y] = "2";
                System.out.println(ships+". ship DEPLOYED");
                ships++;
            }
        }
        System.out.println("--------------------");
    }

    //manages turns
    public static void turn (String[][] map,int[] ships, ArrayList<String> playerG, ArrayList<String> cpuG) {
        //int playerShips = score[0], cpuShips = score[1];
        Scanner input = new Scanner(System.in);
        while (ships[0] > 0 && ships[1] > 0){
            int x = -1, y = -1;
            int x0 = -1, y0 = -1;

            //player turn
            System.out.println("\nYOUR TURN");
            while ((x<0 || x>9) || (y<0 || y>9) || playerG.contains(x+""+y)){
                if (playerG.contains(x+""+y)){
                    System.out.println("Coordinate already guessed. Try again.");
                }
                System.out.print("Enter X coordinate: ");
                x = input.nextInt();
                System.out.print("Enter Y coordinate: ");
                y = input.nextInt();
            }
            pReactMessages(map,ships,x,y);
            playerG.add(x+""+y);

            //cpu turn
            System.out.println("\nCOMPUTER'S TURN");
            Random gen = new Random();
            while ((x0<0 || x0>9) || (y0<0 || y0>9) || cpuG.contains(x0+""+y0)){
                x0 = gen.nextInt(10);
                y0 = gen.nextInt(10);
            }
            cReactMessages(map,ships,x0,y0);
            cpuG.add(x0+""+y0);

            //showing map after turn
            printMap(map);
            System.out.println("Your ships: "+ships[0]+" | "+"Computer ships: "+ships[1]);

        }
    }

    //reaction to player's turn
    public static void pReactMessages (String[][] map, int[] ships, int x, int y) {
        if (map[x][y]==null){
            System.out.println("Sorry, you missed");
            map[x][y] = "-";
        } else {
            if (map[x][y].equals("2")) {
                System.out.println("Boom! You sunk the ship!");
                map[x][y] = "!";
                ships[1]--;
            } else if (map[x][y].equals("1")) {
                System.out.println("Oh no, you sunk your own ship :(");
                map[x][y] = "x";
                ships[0]--;
            }
        }
    }

    //reaction to cpu's turn
    public static void cReactMessages (String[][] map, int[] ships, int x, int y) {
        if (map[x][y]==null || map[x][y].equals("-")) {
            System.out.println("Computer missed");
        } else {
            if (map[x][y].equals("1")) {
                System.out.println("The Computer sunk one of your ships!");
                map[x][y] = "x";
                ships[0]--;
            } else if (map[x][y].equals("2")) {
                System.out.println("The Computer sunk one of its own ships");
                map[x][y] = "!";
                ships[1]--;
            }
        }
    }

}

