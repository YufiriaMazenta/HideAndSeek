package com.github.yufiriamazenta.hideandseek.game;

import org.bukkit.Location;

import java.util.List;
import java.util.Map;

public class GameMap {

    private Location spawnLocation;
    private double maxX, maxZ, minX, minZ;
    private String name;
    private int maxPlayerNum, minPlayerNum;
    private Map<GameLifeCycle, Long> gameLifeCycleSecondMap;
    private List<String> allowDisguises;

    /**
     * 使用此构造函数必须手动设置各种字段
     * 否则会引起严重后果
     */
    public GameMap() {}

    public GameMap(String name, int maxPlayerNum, int minPlayerNum, Location spawnLocation, Location point1, Location point2) {
        this.name = name;
        this.maxPlayerNum = maxPlayerNum;
        this.minPlayerNum = minPlayerNum;
        this.spawnLocation = spawnLocation;
        this.maxX = Math.max(point1.getX(), point2.getX());
        this.minX = Math.min(point1.getX(), point2.getX());
        this.maxZ = Math.max(point1.getZ(), point2.getZ());
        this.minZ = Math.min(point1.getZ(), point2.getZ());
    }

    public GameMap(String name, int maxPlayerNum, int minPlayerNum, Location spawnLocation, double maxX, double maxZ, double minX, double minZ) {
        this.name = name;
        this.maxPlayerNum = maxPlayerNum;
        this.minPlayerNum = minPlayerNum;
        this.spawnLocation = spawnLocation;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minZ = minZ;
    }

    public Location spawnPoint() {
        return spawnLocation;
    }

    public GameMap setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        return this;
    }

    public Location spawnLocation() {
        return spawnLocation;
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


    public int maxPlayerNum() {
        return maxPlayerNum;
    }

    public GameMap setMaxPlayerNum(int maxPlayerNum) {
        this.maxPlayerNum = maxPlayerNum;
        return this;
    }

    public int minPlayerNum() {
        return minPlayerNum;
    }

    public GameMap setMinPlayerNum(int minPlayerNum) {
        this.minPlayerNum = minPlayerNum;
        return this;
    }

    public String name() {
        return name;
    }

    public GameMap setName(String name) {
        this.name = name;
        return this;
    }

    public Map<GameLifeCycle, Long> gameLifeCycleSecondMap() {
        return gameLifeCycleSecondMap;
    }

    public GameMap setGameLifeCycleSecondMap(Map<GameLifeCycle, Long> gameLifeCycleSecondMap) {
        this.gameLifeCycleSecondMap = gameLifeCycleSecondMap;
        return this;
    }

    public List<String> allowDisguises() {
        return allowDisguises;
    }

    public GameMap setAllowDisguises(List<String> allowDisguises) {
        this.allowDisguises = allowDisguises;
        return this;
    }

}
