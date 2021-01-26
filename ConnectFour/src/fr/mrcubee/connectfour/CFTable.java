package fr.mrcubee.connectfour;

import java.util.Arrays;

/**
 * @author MrCubee
 */
public class CFTable {

    private final int columns;
    private final int rows;
    private final byte[] table;

    private CFTable(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.table = new byte[rows * columns];
    }

    private CFTable(CFTable table) {
        this.columns = table.columns;
        this.rows = table.rows;
        this.table = table.table.clone();
    }

    public void reset() {
        Arrays.fill(table, (byte) 0);
    }

    public int getColumns() {
        return this.columns;
    }

    public int getRows() {
        return this.rows;
    }

    public byte getPlayer(int row, int column) {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns)
            return 0;
        return this.table[row * this.columns + column];
    }

    public int setPlayer(int column, int playerId) {
        int row;
        int index = 0;

        if (column < 0 || column >= this.columns)
            return -1;
        row = this.rows;
        while (row > 0 && this.table[index = --row * this.columns + column] != 0);
        if (row < 0 || this.table[index] != 0)
            return -1;
        this.table[index] = (byte) playerId;
        return index;
    }

    protected void removeColumn(int column) {
        int row;
        int index = 0;

        if (column < 0 || column >= this.columns)
            return;
        for (row = 0; row < this.rows && this.table[index = row * this.columns + column] == 0; row++);
        if (row >= this.rows || this.table[index] == 0)
            return;
        this.table[index] = 0;
    }

    private boolean isWin(byte playerId, int index, int vColumn, int vRow, int power) {
        int targetColumn;
        int targetRow;

        if (power < 1)
            return true;
        targetColumn = (index % this.columns) + vColumn;
        targetRow = (index / this.columns) + vRow;
        if (targetColumn < 0 || targetColumn >= this.columns || targetRow < 0 || targetRow >= this.rows)
            return false;
        index = targetRow * this.columns + targetColumn;
        if (index < 0 || index >= this.table.length || this.table[index] != playerId)
            return false;
        return isWin(playerId, index, vColumn, vRow, power - 1);
    }

    private boolean isWin(byte playerId, int index) {
        if (this.table[index] != playerId)
            return false;
        return isWin(playerId, index, 0, 1, 3)
                || isWin(playerId, index, 0, -1, 3)
                || isWin(playerId, index, 1, 0, 3)
                || isWin(playerId, index, -1, 0, 3)
                || isWin(playerId, index, 1, 1, 3)
                || isWin(playerId, index, -1, -1, 3)
                || isWin(playerId, index, 1, -1, 3)
                || isWin(playerId, index, -1, 1, 3);
    }

    public boolean isWin(byte playerId) {
        for (int i = 0; i < this.table.length; i++)
            if (this.table[i] == playerId && isWin(playerId, i))
                return true;
        return false;
    }

    public boolean isFull() {
        for (byte b : this.table)
            if (b == 0)
                return false;
        return true;
    }

    protected byte[] getTable() {
        return table;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CFTable(this);
    }

    public static CFTable create(int rows, int columns) {
        if (rows < 0 || columns < 0 || rows * columns < 5)
            return null;
        return new CFTable(rows, columns);
    }

    private void lineToString(StringBuilder builder, int row) {
        for (int column = 0; column < getColumns(); column++) {
            if (column != 0)
                builder.append(" | ");
            builder.append(Byte.toString(this.table[row * getColumns() + column]));
        }
        builder.append('\n');
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < getRows(); row++)
            lineToString(builder, row);
        return builder.toString();
    }
}
