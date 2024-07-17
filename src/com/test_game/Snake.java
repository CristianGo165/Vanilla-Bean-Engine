package com.test_game;

import java.util.LinkedList;

public class Snake {
    public LinkedList<Cell> snakeCells = new LinkedList<>();

    public Cell head;
    public Cell tail;

    Snake(Cell initialPos){
        snakeCells.add(initialPos);
        head = initialPos;
        head.setTypeCell(CellType.SNAKE_BODY);
        
        //System.out.println("Snake Head At: " + head.GetRowCell() + ", " + head.GetColCell());
    }
    
    public void resetSnake(Cell initialPos) {
    	snakeCells = new LinkedList<>();
    	snakeCells.add(initialPos);
    	head = initialPos;
    	head.setTypeCell(CellType.SNAKE_BODY);
    }

    

    public void moveSnake(Cell moveCell, boolean grow){
        //Update Tail Cell
    	if(!grow){
    		tail = snakeCells.removeLast();
            tail.setTypeCell(CellType.SPACE);
    	}

        //Head Update
        head = moveCell;
        head.setTypeCell(CellType.SNAKE_BODY);
        //Update snake body Linked List
        snakeCells.addFirst(head);
        //System.out.println(snakeCells.size());
    }

    public boolean checkWallCollision(Cell moveCell){
        return moveCell.getTypeCell() == CellType.WALL;
    }
    
    public boolean checkSnakeCollision(Cell moveCell){
        return moveCell.getTypeCell() == CellType.SNAKE_BODY;
    }
    
    public void getCollisionCell(Cell moveCell) {
    	System.out.println(moveCell.getTypeCell()); 
    }
    

    public LinkedList<Cell> getSnakeBody(){
        return snakeCells;
    }

    public Cell getSnakeHead(){
        //return snakeCells.getFirst();
    	return head;
    }
    
}
