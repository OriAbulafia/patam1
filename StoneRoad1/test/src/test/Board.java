package test;

import java.util.ArrayList;

public class Board {
    final int dl;
    final int tl;
    final int dw;
    final int tw;

    private static Board board = null;
    private Tile[][] grid;
    private final int[][] bonus;

    public Board() {

        this.grid = new Tile[15][15];
        dl=2;	// double letter
        tl=3;	// triple letter
        dw=20;	// double word
        tw=30;	// triple word
        bonus= new int[][] {
                {tw,0,0,dl,0,0,0,tw,0,0,0,dl,0,0,tw},
                {0,dw,0,0,0,tl,0,0,0,tl,0,0,0,dw,0},
                {0,0,dw,0,0,0,dl,0,dl,0,0,0,dw,0,0},
                {dl,0,0,dw,0,0,0,dl,0,0,0,dw,0,0,dl},
                {0,0,0,0,dw,0,0,0,0,0,dw,0,0,0,0},
                {0,tl,0,0,0,tl,0,0,0,tl,0,0,0,tl,0},
                {0,0,dl,0,0,0,dl,0,dl,0,0,0,dl,0,0},
                {tw,0,0,dl,0,0,0,dw,0,0,0,dl,0,0,tw},
                {0,0,dl,0,0,0,dl,0,dl,0,0,0,dl,0,0},
                {0,tl,0,0,0,tl,0,0,0,tl,0,0,0,tl,0},
                {0,0,0,0,dw,0,0,0,0,0,dw,0,0,0,0},
                {dl,0,0,dw,0,0,0,dl,0,0,0,dw,0,0,dl},
                {0,0,dw,0,0,0,dl,0,dl,0,0,0,dw,0,0},
                {0,dw,0,0,0,tl,0,0,0,tl,0,0,0,dw,0},
                {tw,0,0,dl,0,0,0,tw,0,0,0,dl,0,0,tw}
        };

    }

    public static Board getBoard() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public Tile[][] getCopyTiles() {
        Tile[][] copyArray = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.arraycopy(grid[i], 0, copyArray[i], 0, 15);
            }
        }
        return copyArray;
    }

    public boolean boardLegal(Word word) {
        boolean isVertical = word.isVertical();
        int wordCount = word.getWordTiles().length;
        int row = word.getRow();
        int col = word.getCol();

        if (row < 0 || col < 0 || row >= 15 || col >= 15)
            return false;

        if (isVertical) {
            if (row + wordCount > 15) {
                return false;
            }
        } else {
            if (col + wordCount > 15) {
                return false;
            }
        }
        if (grid[7][7] == null) {
            if (isVertical) {
                if (col != 7) {
                    return false;
                }
                if (row + wordCount < 7 || row > 7) {
                    return false;
                }
            } else {
                if (row != 7) {
                    return false;
                }
                if (col + wordCount < 7 || col > 7) {
                    return false;
                }
            }
        } else {
            boolean flag = false;
            if (isVertical) {
                if (row - 1 >= 0 && grid[row - 1][col] != null) {
                    flag = true;
                } else if (row + wordCount + 1 < 15 && grid[row + wordCount + 1][col] != null) {
                    flag = true;
                } else {
                    for (int i = row; i < row + wordCount; i++) {
                        if (grid[i][col] != null) {
                            flag = true;
                            break;
                        }
                        if (col - 1 >= 0 && grid[i][col - 1] != null) {
                            flag = true;
                            break;
                        }
                        if (col + 1 < 15 && grid[i][col + 1] != null) {
                            flag = true;
                            break;
                        }
                    }
                }
            } else {
                if (col - 1 >= 0 && grid[row - 1][col] != null) {
                    flag = true;
                } else if (col + wordCount + 1 < 15 && grid[row][col + wordCount + 1] != null) {
                    flag = true;
                } else {
                    for (int i = col; i < col + wordCount; i++) {
                        if (grid[row][i] != null) {
                            flag = true;
                            break;
                        }
                        if (row - 1 >= 0 && grid[row - 1][i] != null) {
                            flag = true;
                            break;
                        }
                        if (row + 1 < 15 && grid[row + 1][i] != null) {
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }
    private int bonusAtPosition(int row, int col) {
        return bonus[row][col];
    }
    private void setBonusAtPosition(int row, int col) {
        bonus[row][col]=1;
    }
    public int getScore(Word word) {

        boolean isWord =false;
        int wordMul =1;
        Tile[] tiles = word.getWordTiles();
        int col = word.getCol();
        int row = word.getRow();
        boolean vertical = word.isVertical();
        int wordScore = 0;

        for (Tile tile : tiles) {

            int bonusMultiplier = bonusAtPosition(row, col);

            int letterScore = tile.getScore();

            if (bonusMultiplier == 2) {
                    letterScore *= 2;

            } else if (bonusMultiplier == 3) {
                letterScore *= 3;
            }

            wordScore += letterScore;

            if (bonusMultiplier == 20) {
                isWord = true;
                wordMul *= 2;
                if (row==7 && col==7){
                    setBonusAtPosition(row,col);
                }
            } else if (bonusMultiplier == 30) {
                isWord = true;
                wordMul *= 3;
            }

            if (vertical) {
                row++;
            } else {
                col++;
            }
        }

        if(isWord){
            wordScore*=wordMul;
        }

        return wordScore;
    }

    public ArrayList<Word> getWords(Word word) {
        ArrayList<Word> words = new ArrayList<Word>();
        int row = word.getRow();
        int col = word.getCol();
        int wordCount = word.getWordTiles().length;
        Tile[] wordTiles = word.getWordTiles();
        if (word.isVertical()) {
            for (int i = row; i < row + wordCount; i++) {
                if (wordTiles[i - row] == null) {
                    wordTiles[i - row] = grid[i][col];
                } else {
                    if (col - 1 >= 0 && grid[i][col - 1] != null) {
                        words.add(collectWord(i, col - 1));
                    } else if (col + 1 < 15 && grid[i][col + 1] != null) {
                        words.add(collectWord(i, col + 1));
                    }
                }
            }
            words.add(new Word(wordTiles, row, col, true));
        } else {
            for (int i = col; i < col + wordCount; i++) {
                if (wordTiles[i - col] == null) {
                    wordTiles[i - col] = grid[row][i];
                } else {
                    if (row - 1 >= 0 && grid[row - 1][i] != null) {
                        words.add(collectWordVertical(row - 1, i));
                    } else if (row + 1 < 15 && grid[row + 1][i] != null) {
                        words.add(collectWordVertical(row + 1, i));
                    }
                }
            }
            words.add(new Word(wordTiles, row, col, false));
        }
        return words;
    }

    private Word collectWordVertical(int row, int col) {
        int j = row;
        while (j >= 0 && grid[j][col] != null) {
            j--;
        }
        j++;
        int k = row;
        while (k < 15 && grid[k][col] != null) {
            k++;
        }
        k--;
        Tile[] tiles = new Tile[k - j + 1];
        int start = j;
        int l = 0;
        while (j <= k) {
            tiles[l] = grid[j][col];
            l++;
            j++;
        }
        return new Word(tiles, start, col, true);
    }

    private Word collectWord(int row, int col) {
        int j = col;
        while (j >= 0 && grid[row][j] != null) {
            j--;
        }
        j++;
        int k = col;
        while (k < 15 && grid[row][k] != null) {
            k++;
        }
        k--;
        Tile[] tiles = new Tile[k - j + 1];
        int start = j;
        int l = 0;
        while (j <= k) {
            tiles[l] = grid[row][j];
            l++;
            j++;
        }
        return new Word(tiles, start, row, false);
    }

    private boolean dictionaryLegal(Word word) {
        return true;
    }

    public int tryPlaceWord(Word word) {
        if (!boardLegal(word)) {
            return 0;
        }
        if (!dictionaryLegal(word)) {
            return 0;
        }
        int row = word.getRow();
        int col = word.getCol();

        for (Tile tile : word.getWordTiles()) {
            if (tile != null)
                grid[row][col] = tile;
            if (word.isVertical()) {
                row++;
            } else {
                col++;
            }
        }

        ArrayList<Word> words = getWords(word);
        int score = 0;
        for (Word w : words) {
            score += getScore(w);
        }
        return score;
    }
}