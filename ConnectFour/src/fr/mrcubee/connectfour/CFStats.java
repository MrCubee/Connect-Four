package fr.mrcubee.connectfour;

/**
 * @author MrCubee
 */
public class CFStats {

    protected int win;
    protected int equals;
    protected int lose;

    protected CFStats() {

    }

    public boolean isBest(CFStats cfStats) {
        return cfStats.lose < this.lose;
    }
}
