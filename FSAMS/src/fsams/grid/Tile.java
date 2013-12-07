package fsams.grid;

public class Tile {
    public final int grid_x, grid_y;
    private boolean wallU, wallD, wallL, wallR;
    private boolean doorU, doorD, doorL, doorR;
    private boolean lockU, lockD, lockL, lockR;
    private boolean fire, fireSensor, humanAgent, exit, fireAlarm;
    private boolean suppressor, suppressorActive;
    private long suppression;
    private long lastMoveTime;

    public Tile(int grid_x, int grid_y) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        wallU = wallD = wallR = wallL = false;
        doorU = doorD = doorR = doorL = false;
        lockU = lockD = lockL = lockR = false;
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
        this.doorU = tile.doorU;
        this.doorD = tile.doorD;
        this.doorL = tile.doorL;
        this.doorR = tile.doorR;
        this.lockU = tile.lockU;
        this.lockD = tile.lockD;
        this.lockL = tile.lockL;
        this.lockR = tile.lockR;
        this.fire = tile.fire;
        this.fireSensor = tile.fireSensor;
        this.humanAgent = tile.humanAgent;
        this.exit = tile.exit;
        this.suppressor = tile.suppressor;
        this.suppressorActive = tile.suppressorActive;
        this.suppression = tile.suppression;
    }

    public void reset() {
        lastMoveTime = 0;
        suppressorActive = false;
        suppression = -1;
        lockU = false;
        lockD = false;
        lockL = false;
        lockR = false;
    }
    
    public boolean getWallU() {
        return wallU;
    }
    public void setWallU(boolean status) {
        wallU = status;
        if(status) doorU = false;
    }
    
    public boolean getWallD() {
        return wallD;
    }
    public void setWallD(boolean status) {
        wallD = status;
        if(status) doorD = false;
    }
    
    public boolean getWallL() {
        return wallL;
    }
    public void setWallL(boolean status) {
        wallL = status;
        if(status) doorL = false;
    }
    
    public boolean getWallR() {
        return wallR;
    }
    public void setWallR(boolean status) {
        wallR = status;
        if(status) doorR = false;
    }
    
    
    public boolean getDoorU() {
        return doorU;
    }
    public void setDoorU(boolean status) {
        doorU = status;
        if(status) wallU = false;
    }
    
    public boolean getDoorD() {
        return doorD;
    }
    public void setDoorD(boolean status) {
        doorD = status;
        if(status) wallD = false;
    }
    
    public boolean getDoorL() {
        return doorL;
    }
    public void setDoorL(boolean status) {
        doorL = status;
        if(status) wallL = false;
    }
    
    public boolean getDoorR() {
        return doorR;
    }
    public void setDoorR(boolean status) {
        doorR = status;
        if(status) wallR = false;
    }
    
    public boolean getLockU() {
        return lockU;
    }
    public void setLockU(boolean status) {
        lockU = doorU? status: false;
    }
    
    public boolean getLockD() {
        return lockD;
    }
    public void setLockD(boolean status) {
        lockD = doorD? status: false;
    }
    
    public boolean getLockL() {
        return lockL;
    }
    public void setLockL(boolean status) {
        lockL = doorL? status: false;
    }
    
    public boolean getLockR() {
        return lockR;
    }
    public void setLockR(boolean status) {
        lockR = doorR? status: false;
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

    public boolean getFireAlarm() {
        return fireAlarm;
    }
    public void setFireAlarm(boolean status) {
        fireAlarm = status;
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