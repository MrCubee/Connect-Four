package fr.mrcubee.connectfour;

/**
 * @author MrCubee
 */
public class CFBot {

    private static boolean fillStats(CFStats cfStats, CFTable cfTable, byte[] players) {
        if (cfTable.isFull()) {
            cfStats.equals++;
            return true;
        }
        if (cfTable.isWin(players[0])) {
            cfStats.win++;
            return true;
        }
        for (int i = 1; i < players.length; i++) {
            if (cfTable.isWin(players[i])) {
                cfStats.lose++;
                return true;
            }
        }
        return false;
    }

    private static CFStats getColumnStats(CFTable cfTable, int column, int searchLevel, byte[] players) {
        CFStats cfStats;
        CFPlay cfPlay;

        if (cfTable == null || cfTable.isFull() || searchLevel < 0 || column < 0 || column >= cfTable.getColumns()
        || players == null || players.length < 2)
            return null;
        cfStats = new CFStats();
        cfPlay = new CFPlay(null, players.length);
        cfPlay.column = column;
        if (cfTable.setPlayer(column, players[cfPlay.playerIndex]) < 0)
            return cfStats;
        cfPlay = new CFPlay(cfPlay, players.length);
        do {
            for (; cfPlay.column < cfTable.getColumns() && cfTable.setPlayer(cfPlay.column, players[cfPlay.playerIndex]) < 0; cfPlay.column++);
            if (cfPlay.column >= cfTable.getColumns() || fillStats(cfStats, cfTable, players)) {
                cfTable.removeColumn(cfPlay.column);
                cfPlay = cfPlay.lastPlay;
                if (cfPlay != null)
                    cfTable.removeColumn(cfPlay.column++);
            } else if (cfPlay.getSize() < searchLevel)
                cfPlay = new CFPlay(cfPlay, players.length);
            else
                cfTable.removeColumn(cfPlay.column++);
        } while (cfPlay.lastPlay != null);
        return cfStats;
    }

    public static int getBestColumn(CFTable cfTable, int searchLevel, byte ...players) {
        int column;
        CFStats cfStats;
        CFStats temp;

        if (cfTable == null || searchLevel < 0 || players == null || players.length < 2)
            return -1;
        searchLevel *= 2;
        column = 0;
        cfStats = getColumnStats(cfTable, 0, searchLevel, players);
        for (int i = 1; i < cfTable.getColumns(); i++) {
            temp = getColumnStats(cfTable, i, searchLevel, players);
            if (temp != null && cfStats.isBest(temp)) {
                cfStats = temp;
                column = i;
            }
        }
        return column;
    }
}
