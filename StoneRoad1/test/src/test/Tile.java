package test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    @Override
    public String toString() {
        return "Tile [letter=" + letter + ", score=" + score;
    }

    Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public static class Bag {
        private static Bag bag = null;
        private int[] tilesQuaninty;
        private final int[] originalQuantity;
        private final Tile[] tiles;

        @Override
        public String toString() {
            return "Bag [tilesQuaninty=" + Arrays.toString(tilesQuaninty) + ", tiles=" + Arrays.toString(tiles) + "]";
        }

        private Bag() {
            originalQuantity = new int[] {
                    9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
            };

            tilesQuaninty = new int[originalQuantity.length];

            System.arraycopy(originalQuantity, 0, tilesQuaninty, 0, originalQuantity.length);

            tiles = new Tile[] {
                    new Tile('A', 1),
                    new Tile('B', 3),
                    new Tile('C', 3),
                    new Tile('D', 2),
                    new Tile('E', 1),
                    new Tile('F', 4),
                    new Tile('G', 2),
                    new Tile('H', 4),
                    new Tile('I', 1),
                    new Tile('J', 8),
                    new Tile('K', 5),
                    new Tile('L', 1),
                    new Tile('M', 3),
                    new Tile('N', 1),
                    new Tile('O', 1),
                    new Tile('P', 3),
                    new Tile('Q', 10),
                    new Tile('R', 1),
                    new Tile('S', 1),
                    new Tile('T', 1),
                    new Tile('U', 1),
                    new Tile('V', 4),
                    new Tile('W', 4),
                    new Tile('X', 8),
                    new Tile('Y', 4),
                    new Tile('Z', 10)
            };
        }

        public Tile getRand() {
            if (size() == 0)
                return null;

            Random rand = new Random();
            int tileCell = rand.nextInt(26);

            while (tilesQuaninty[tileCell] == 0)
                tileCell = rand.nextInt(26);

            tilesQuaninty[tileCell]--;
            return tiles[tileCell];
        }

        public Tile getTile(char tileChar) {
            if (tileChar < 'A' || tileChar > 'Z')
                return null;

            int tileCell = tileChar - 'A';

            if (tilesQuaninty[tileCell] == 0)
                return null;
            else {
                tilesQuaninty[tileCell]--;
                return tiles[tileCell];
            }
        }

        public int getTileScore(char tileChar) {
            int tileCell = tileChar - 'A';
            return tiles[tileCell].score;
        }

        public void put(Tile tileReturn) {
            int tileCell = tileReturn.letter - 'A';

            if (tilesQuaninty[tileCell] < originalQuantity[tileCell])
                tilesQuaninty[tileCell]++;
        }

        public int size() {
            int count = 0;
            for (int i = 0; i < tilesQuaninty.length; i++) {
                count += tilesQuaninty[i];
            }

            return count;
        }

        public int[] getQuantities() {
            int[] copyTiles = new int[tilesQuaninty.length];
            System.arraycopy(tilesQuaninty, 0, copyTiles, 0, tilesQuaninty.length);
            return copyTiles;
        }

        public static Bag getBag() {
            if (bag == null) {
                bag = new Bag();
            }
            return bag;
        }
    }

    public char getLetter() {
        return letter;
    }

    public int getScore() {
        return score;
    }

}