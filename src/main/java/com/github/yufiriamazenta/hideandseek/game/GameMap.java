package com.github.yufiriamazenta.hideandseek.game;

import org.bukkit.Location;

public class GameMap {

    private Location spawnPoint;
    private double maxX, maxZ, minX, minZ;

    public GameMap(Location spawnPoint, double maxX, double maxZ, double minX, double minZ) {
        this.spawnPoint = spawnPoint;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minZ = minZ;
    }

    public Location spawnPoint() {
        return spawnPoint;
    }

    public GameMap setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
        return this;
    }

    public double maxX() {
        return maxX;
    }

    public GameMap setMaxX(double maxX) {
        this.maxX = maxX;
        return this;
    }

    public double maxZ() {
        return maxZ;
    }

    public GameMap setMaxZ(double maxZ) {
        this.maxZ = maxZ;
        return this;
    }

    public double minX() {
        return minX;
    }

    public GameMap setMinX(double minX) {
        this.minX = minX;
        return this;
    }

    public double minZ() {
        return minZ;
    }

    public GameMap setMinZ(double minZ) {
        this.minZ = minZ;
        return this;
    }

}
