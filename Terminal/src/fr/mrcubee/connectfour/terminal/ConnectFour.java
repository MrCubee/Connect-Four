package fr.mrcubee.connectfour.terminal;

import fr.mrcubee.connectfour.CFBot;
import fr.mrcubee.connectfour.CFTable;

import java.util.Scanner;

/**
 * @author MrCubee
 */
public class ConnectFour {

    /*
     * Use for test connect four AI
     */
    public static void main(String[] args) {
        CFTable cfTable = CFTable.create(6, 7);
        Scanner scanner = new Scanner(System.in);
        String line;
        int column;

        System.out.println(cfTable.toString());
        while (!cfTable.isFull() && !cfTable.isWin((byte) 1) && !cfTable.isWin((byte) 2)) {
            System.out.print("play: ");
            while (!scanner.hasNextLine());
            line = scanner.nextLine();
            System.out.println();
            column = -1;
            try {
                column = Integer.parseInt(line);
            } catch (Exception ignored) {};
            if (cfTable.setPlayer(column, 1) < 0)
                break;
            System.out.println(cfTable.toString());
            if (!cfTable.isWin((byte) 1)) {
                cfTable.setPlayer(CFBot.getBestColumn(cfTable, 2, (byte) 2, (byte) 1), (byte) 2);
                System.out.println(cfTable.toString());
            }
        }
    }
}
