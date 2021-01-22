package fr.mrcubee.connectfour;

/**
 * @author MrCubee
 */
public class CFPlay {

    protected int column;
    protected int playerIndex;
    protected final CFPlay lastPlay;

    protected CFPlay(CFPlay lastPlay, int playerCount) {
        this.lastPlay = lastPlay;
        if (lastPlay != null)
            this.playerIndex = (lastPlay.playerIndex + 1) % playerCount;
    }

    public int getSize() {
        int result;
        CFPlay current = this;

        for (result = 1; (current = current.lastPlay) != null; result++);
        return result;
    }
}
