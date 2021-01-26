package fr.mrcubee.connectfour;

/**
 * @author MrCubee
 */
public class CFPlay {

    protected int column;
    public final int playerIndex;
    public final long searchLevel;
    public final CFPlay lastPlay;

    protected CFPlay(CFPlay lastPlay, int playerCount) {
        this.lastPlay = lastPlay;
        if (lastPlay != null) {
            this.playerIndex = (lastPlay.playerIndex + 1) % playerCount;
            this.searchLevel = lastPlay.searchLevel + 1;
        } else {
            this.playerIndex = 0;
            this.searchLevel = 0;
        }
    }

    public int getColumn() {
        return this.column;
    }
}
