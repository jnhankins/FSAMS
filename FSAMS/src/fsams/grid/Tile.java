package fsams.grid;

public class Tile {
    public final int grid_x, grid_y;
    private boolean wallU, wallD, wallL, wallR;
    private boolean fire, fireSensor, humanAgent,exit, suppressor;
    private boolean suppressorActive;
    private long suppression;
    private long lastMoveTime;

    public Tile(int grid_x, int grid_y) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        wallU = false;
        wallD = false;
        wallL = false;
        wallR = false;
        fire = false;
        fireSensor = false;
        humanAgent = false;
        exit = false;
        suppressor = false;
        suppressorActive = false;
        suppression = -1;
    }
    public Tile(Tile tile) {
        this.grid_x = tile.grid_x;
        this.grid_y = tile.grid_y;
        this.wallU = tile.wallU;
        this.wallD = tile.wallD;
        this.wallL = tile.wallL;
        this.wallR = tile.wallR;
        this.fire = tile.fire;
        this.fireSensor = tile.fireSensor;
        this.humanAgent = tile.humanAgent;
        this.exit = tile.exit;
        this.suppressor = tile.suppressor;
        this.suppressorActive = tile.suppressorActive;
        this.suppression = tile.suppression;
    }

    public boolean getWallU() {
        return wallU;
    }
    public void setWallU(boolean status) {
        wallU = status;
    }
    
    public boolean getWallD() {
        return wallD;
    }
    public void setWallD(boolean status) {
        wallD = status;
    }
    
    public boolean getWallL() {
        return wallL;
    }
    public void setWallL(boolean status) {
        wallL = status;
    }
    
    public boolean getWallR() {
        return wallR;
    }
    public void setWallR(boolean status) {
        wallR = status;
    }

    public boolean getFire() {
        return fire;
    }
    public void setFire(boolean status) {
        fire = status;
    }

    public boolean getFireSensor() {
        return fireSensor;
    }
    public void setFireSensor(boolean status) {
        fireSensor = status;
    }

    public boolean getExit() {
        return exit;
    }
    public void setExit(boolean status) {
        exit = status;
    }

    public boolean getHumanAgent() {
        return humanAgent;
    }
    public void setHumanAgent(boolean status) {
        humanAgent = status;
    }
    
    public long getLastMoveTime() {
        return lastMoveTime;
    }
    public void setLastMoveTime(long moveTime) {
        lastMoveTime = moveTime;
    }

    public boolean getSuppressor() {
        return suppressor;
    }
    public void setSuppressor(boolean status) {
        suppressor = status;
    }
    
    public boolean getSuppressorActive() {
        return suppressorActive;
    }
    public void setSuppressorActive(boolean status) {
        suppressorActive = status;
    }
    
    
    public boolean getSuppression() {
        final long suppressionTime = 1000;
        if(suppression==-1 || System.currentTimeMillis()-suppression>suppressionTime) {
            return false;
        }
        return true;
    }
    public void setSuppression(boolean status) {
        if(status) {
            suppression = System.currentTimeMillis();
        } else {
            suppression = -1;
        }
    }

}