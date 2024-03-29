package com.pacman.system.physical.core;

import java.util.Collection;

import com.pacman.model.support.GameEntity;
import com.pacman.system.physical.ICollisionEngine;

/**
 *
 * Gerenciador de colisoes
 *
 */
public class CollisionEngine implements ICollisionEngine {

	@Override
	public boolean detectColision(GameEntity entity, GameEntity entity2) {
		return (entity.getShape().intersects(entity2.getShape()));
	}

	@Override
	public boolean detectColision(GameEntity entity, Collection<? extends GameEntity> collection) {
		for (GameEntity e : collection)
			if (detectColision(entity, e)) {
				return true;
			}
		return false;
	}

}
