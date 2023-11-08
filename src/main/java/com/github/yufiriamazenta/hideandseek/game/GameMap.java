package com.github.yufiriamazenta.hideandseek.game;

import org.bukkit.Location;

public class GameMap {

    private Location spawnPoint;
    private double maxX, maxZ, minX, minZ;

    public GameMap(Location spawnPoint, Location point1, Location point2) {
        this.spawnPoint = spawnPoint;
        if (point1.getX() > point2.getX()) {
            this.maxX = point1.getX();
            this.minX = point2.getX();
        } else {
            this.maxX = point2.getX();
            this.minX = point1.getX();
        }

        if (point1.getZ() > point2.getZ()) {
            this.maxZ = point1.getZ();
            this.minZ = point2.getZ();
        } else {
            this.maxZ = point2.getZ();
            this.minZ = point1.getZ();
        }
    }

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
