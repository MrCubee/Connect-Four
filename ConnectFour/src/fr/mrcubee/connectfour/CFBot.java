package fr.mrcubee.connectfour;

/**
 * @author MrCubee
 */
public class CFBot {

    private static boolean selectPlay(CFTable cfTable, CFPlay cfPlay, byte[] players) {
        if (cfTable == null || cfPlay == null || players == null || players.length < 2)
            return false;
        for (; cfPlay.column < cfTable.getColumns()
                && cfTable.setPlayer(cfPlay.column, players[cfPlay.playerIndex]) < 0; cfPlay.column++);
        if (cfPlay.column >= cfTable.getColumns())
            return false;
        return true;
    }

    private static CFPlay playBackward(CFTable cfTable, CFPlay cfPlay, byte[] players) {
        CFPlay result = cfPlay;

        if (cfTable == null || cfPlay == null)
            return null;
        cfTable.removeColumn(result.column);
        while ((result = result.lastPlay) != null) {
            cfTable.removeColumn(result.column++);
            if (result.lastPlay == null || selectPlay(cfTable, result, players))
                return result;
        }
        return null;
    }

    private static CFPlay playForward(CFTable cfTable, CFPlay cfPlay, long searchLevel, byte[] players) {
        CFPlay result;

        if (cfTable == null || players == null || players.length < 2)
            return null;
        else if (cfPlay != null && cfPlay.searchLevel >= searchLevel)
            return playBackward(cfTable, cfPlay, players);
        result = new CFPlay(cfPlay, players.length);
        if (!selectPlay(cfTable, result, players))
            return playBackward(cfTable, cfPlay, players);
        return result;
    }

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

    private static CFStats getColumnStats(CFTable cfTable, int column, long searchLevel, byte[] players) {
        CFStats cfStats;
        CFPlay cfPlay;

        if (cfTable == null || cfTable.isFull() || searchLevel < 0 || column < 0 || column >= cfTable.getColumns()
        || players == null || players.length < 2 || cfTable.setPlayer(column, players[0]) < 0)
            return null;
        cfStats = new CFStats();
        cfPlay = new CFPlay(null, players.length);
        cfPlay.column = column;
        do {
            cfPlay = playForward(cfTable, cfPlay, searchLevel, players);
            while (fillStats(cfStats, cfTable, players))
                cfPlay = playBackward(cfTable, cfPlay, players);
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
            if (temp != null && (cfStats == null || cfStats.isBest(temp))) {
                cfStats = temp;
                column = i;
            }
        }
        return column;
    }
}
