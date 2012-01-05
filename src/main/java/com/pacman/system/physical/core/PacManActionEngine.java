package com.pacman.system.physical.core;

import java.util.Collection;

import com.pacman.model.Block;
import com.pacman.model.Fruit;
import com.pacman.model.PacMan;
import com.pacman.model.enumeration.Direction;
import com.pacman.system.physical.ICollisionEngine;
import com.pacman.system.physical.IEntityActionEngine;
import com.pacman.system.physical.IMovementEngine;

public class PacManActionEngine implements IEntityActionEngine {

	private IMovementEngine movementEnginer;
	private ICollisionEngine collisionEngine;
	private PacMan pacMan;
	private Collection<Block> blockList;
	private Collection<Fruit> fruitList;

	public PacManActionEngine(PacMan pacMan, Collection<Block> blockList, Collection<Fruit> fruitList) {
		this.pacMan = pacMan;
		this.blockList = blockList;
		this.fruitList = fruitList;
	}

	public void setMovementEngine(IMovementEngine movementEngine) {
		this.movementEnginer = movementEngine;
	}

	public void setColisionEngine(ICollisionEngine collisionEngine) {
		this.collisionEngine = collisionEngine;
	}

	@Override
	public void act(Direction direction) {
		pacMan.updateMouthState();
		if(direction != null && canMove(direction)) {
			pacMan.setCurrentDirection(direction);
			move(direction);
			eatFruit(fruitList);
		}

	}

	private boolean canMove(Direction direction) {
		move(direction);
		if (collisionEngine.detectColision(pacMan, blockList)) {
			pacMan.setX(pacMan.getPreviousValidX());
			pacMan.setY(pacMan.getPreviousValidY());
			return false;
		}
		pacMan.setPreviousValidX(pacMan.getX());
		pacMan.setPreviousValidY(pacMan.getY());
		return true;
	}

	private void move(Direction direction) {
		switch (direction) {
		case LEFT:
			movementEnginer.moveLeft(pacMan);
			break;
		case RIGHT:
			movementEnginer.moveRight(pacMan);
			break;
		case UP:
			movementEnginer.moveUp(pacMan);
			break;
		case DOWN:
			movementEnginer.moveDown(pacMan);
			break;
		}
	}

	private void eatFruit(Collection<Fruit> fruits) {
		if (fruits != null)
			for (Fruit fruit : fruits) {
				if (collisionEngine.detectColision(pacMan, fruit)) {
					fruit.setAlive(false);
					break;
				}
			}
	}

}