package test;


import java.util.Arrays;

public class Word {
    private final Tile[] tiles;
    private final int col;
    private final int row;
    private final boolean vertical;

    public Word(Tile[] tiles, int row  , int col, boolean vertical){
        this.tiles = tiles;
        this.col = col;
        this.row = row;
        this.vertical = vertical;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return col == word.col && row == word.row && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }


    public Tile[] getWordTiles() { return tiles; }
    public int getCol() { return col; }
    public int getRow() { return row; }
    public boolean isVertical() { return vertical; }



}
