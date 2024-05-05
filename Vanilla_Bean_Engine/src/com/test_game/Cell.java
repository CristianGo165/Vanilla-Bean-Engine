package com.test_game;

public class Cell {

    private final int row;
    private final int col;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRowCell(){
        return row;
    }

    public int getColCell(){
        return col;
    }

    private CellType cellType;

    public CellType getTypeCell(){
        return cellType;
    }
    public void setTypeCell(CellType cellType){
        this.cellType = cellType;
    }
}
