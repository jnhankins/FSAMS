package fsams.grid;

public class Tile {
    // Grid Coordinates
    public final int grid_x, grid_y;
    // Walls and Doors
    private boolean wallU, wallD, wallL, wallR;
    private boolean doorU, doorD, doorL, doorR;
    private boolean lockU, lockD, lockL, lockR;
    // Exits
    private boolean exit;
    // Fire
    private boolean fire;
    // Fire Sensor
    private boolean fireSensor, fireSensorActive;
    // Fire Alarm
    private boolean fireAlarm, fireAlarmActive;
    // Fire Suppression
    private boolean sprinkler, sprinklerActive;
    private long suppression;
    // Human Agents
    private boolean humanAgent, humanAgentActive; 
    private long lastMoveTime;
    // Equipment
    private boolean equipment, equipmentActive;
    // Intruders
    private boolean intruder, intruderFleeing;
    
    public Tile(int grid_x, int grid_y) {
        // Grid Coordinates
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        // Walls and Doors
        wallU = wallD = wallR = wallL = false;
        doorU = doorD = doorR = doorL = false;
        lockU = lockD = lockL = lockR = false;
        // Exits
        exit = false;
        // Fire
        fire = false;
        // Fire Sensor
        fireSensor = fireSensorActive = false;
        // Fire Alarm
        fireAlarm = fireAlarmActive = false;
        // Fire Suppression
        sprinkler = sprinklerActive = false;
        suppression = -1;
        // Human Agents
        humanAgent = humanAgentActive = false;
        lastMoveTime = 0;
        // Equipment
        equipment = false;
        equipmentActive = true;
        // Intruder
        intruder = false;
        intruderFleeing = false;
    }
    public Tile(Tile tile) {
        // Grid Coordinates
        this.grid_x = tile.grid_x;
        this.grid_y = tile.grid_y;
        // Walls and Doors
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
        // Exits
        this.exit = tile.exit;
        // Fire
        this.fire = tile.fire;
        // Fire Sensor
        this.fireSensor = tile.fireSensor;
        this.fireSensorActive = tile.fireSensorActive;
        // Fire Alarm
        this.fireAlarm = tile.fireAlarm;
        this.fireAlarmActive = tile.fireAlarmActive;
        // Fire Suppression
        this.sprinkler = tile.sprinkler;
        this.sprinklerActive = tile.sprinklerActive;
        this.suppression = tile.suppression;
        // Human Agents
        this.humanAgent = tile.humanAgent;
        this.humanAgentActive = tile.humanAgentActive;
        this.lastMoveTime = tile.lastMoveTime;
        // Equipment
        this.equipment = tile.equipment;
        this.equipmentActive = tile.equipmentActive;
        // Intruder
        this.intruder = tile.intruder;
        this.intruderFleeing = tile.intruderFleeing;
    }

    public void reset() {
        // Door Locks
        lockU = false;
        lockD = false;
        lockL = false;
        lockR = false;
        // Sensors
        fireSensorActive = false;
        // Alarms
        fireAlarmActive = false;
        // Fire Suppression
        sprinklerActive = false;
        suppression = -1;
        // Human Agents
        humanAgentActive = false;
        lastMoveTime = 0;
        // Equipment
        equipmentActive = true;
        // Intruder
        intruderFleeing = false;
    }
    
    public boolean getWallU() { return wallU; }
    public boolean getWallD() { return wallD; }
    public boolean getWallR() { return wallR; }
    public boolean getWallL() { return wallL; }
    public boolean getDoorU() { return doorU; }
    public boolean getDoorD() { return doorD; }
    public boolean getDoorR() { return doorR; }
    public boolean getDoorL() { return doorL; }
    public boolean getLockU() { return lockU; }
    public boolean getLockD() { return lockD; }
    public boolean getLockR() { return lockR; }
    public boolean getLockL() { return lockL; }
    public boolean getExit() { return exit; }
    public boolean getFire() { return fire; }
    public boolean getFireSensor() { return fireSensor; }
    public boolean getFireSensorActive() { return fireSensorActive; }
    public boolean getFireAlarm() { return fireAlarm; }
    public boolean getFireAlarmActive() { return fireAlarmActive; }
    public boolean getSprinkler() { return sprinkler; }
    public boolean getSuppressorActive() { return sprinklerActive; }
    public boolean getSuppression() {
        final long suppressionTime = 1000;
        if(suppression==-1 || System.currentTimeMillis()-suppression>suppressionTime) {
            return false;
        }
        return true;
    }
    public boolean getHumanAgent() { return humanAgent; }
    public boolean getHumanAgentActive() { return humanAgentActive; }
    public long getLastMoveTime() { return lastMoveTime; }
    public boolean getEquipment() { return equipment; }
    public boolean getEquipmentActive() { return equipmentActive; }
    public boolean getIntruder() { return intruder; }
    public boolean getIntruderFleeing() { return intruderFleeing; }
    
    public void setWallU(boolean status) { 
        wallU = status;
        if(status) doorU = false;
    }
    public void setWallD(boolean status) { 
        wallD = status;
        if(status) doorD = false;
    }
    public void setWallR(boolean status) { 
        wallR = status;
        if(status) doorR = false;
    }
    public void setWallL(boolean status) { 
        wallL = status;
        if(status) doorL = false;
    }
    public void setDoorU(boolean status) {  
        doorU = status;
        if(status) wallU = false;
    }
    public void setDoorD(boolean status) {  
        doorD = status;
        if(status) wallD = false;
    }
    public void setDoorR(boolean status) {  
        doorR = status;
        if(status) wallR = false;
    }
    public void setDoorL(boolean status) {  
        doorL = status;
        if(status) wallL = false;
    }
    public void setLockU(boolean status) {
        if(doorU) lockU = status;
    }
    public void setLockD(boolean status) {
        if(doorD) lockD = status;
    }
    public void setLockR(boolean status) {
        if(doorR) lockR = status;
    }
    public void setLockL(boolean status) {
        if(doorL) lockL = status;
    }
    public void setExit(boolean status) { 
        exit = status;
    }
    public void setFire(boolean status) {
        fire = status;
    }
    public void setFireSensor(boolean status) {
        fireSensor = status;
    }
    public void setFireSensorActive(boolean status) {
        if(fireSensor) fireSensorActive = status;
    }
    public void setFireAlarm(boolean status) { 
        fireAlarm = status;
    }
    public void setFireAlarmActive(boolean status) {
        if(fireAlarm) fireAlarmActive = status;
    }
    public void setSprinkler(boolean status) {
        sprinkler = status;
    }
    public void setSuppressorActive(boolean status) { 
        if(sprinkler) sprinklerActive = status;
    }
    public void setSuppression(boolean status) {
        suppression = status? System.currentTimeMillis() : -1;
    }
    public void setHumanAgent(boolean status) { 
        humanAgent = status;
        if (status) {
            equipment = false;
            intruder = false;
        }
    }
    public void setHumanAgentActive(boolean status) {
        if(humanAgent) humanAgentActive = status;
    }
    public void setLastMoveTime(long time) {
        lastMoveTime = time;
    }
    public void setEquipment(boolean status) {
        equipment = status;
        if(status) {
            humanAgent = false;
            intruder = false;
        }
    }
    public void setEquipmentActive(boolean status) {
        if(equipment) equipmentActive = status;
    }
    public void setIntruder(boolean status) {
        intruder = status;
        if (status) {
            humanAgent = false;
            equipment = false;
        }
    }
    public void setIntruderFleeing(boolean status) {
        if (intruder) intruderFleeing = status;
    }
}