package fsams.grid;

/**
 * Keeps track of what components are in a single tile in the grid, e.g. Fire, 
 * Walls, Sprinkers, etc.
 * @author FSAMS Team
 */
public class Tile {

    /**
     * The column of this tile in the grid.
     */
    public final int grid_x;

    /**
     * The row of this tile in the grid.
     */
    public final int grid_y;
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
    // Cameras
    private boolean camera;
    
    /**
     * Constructs a new tile at the specified coordinates.
     * @param grid_x The column of this tile.
     * @param grid_y The row of this tile.
     */
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
        camera = false;
    }

    /**
     * Constructs a new tile by copying the specified tile.
     * @param tile
     */
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
        // Camera
        this.camera = tile.camera;
    }

    /**
     * Resets the tile to its state at the beginning of the simulation.
     * Unlocks all doors.  Deactivates sensors, alarms, and sprinklers.
     * Removes all active fire suppression (i.e. water).  Puts all humans and
     * intruders into an inactive state.
     */
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
    
    /**
     * Returns true if there is a wall at the top of this tile
     * @return True if there is a wall at the top of this tile.
     */
    public boolean getWallU() { return wallU; }

    /**
     * Returns true if there is a wall at the bottom of this tile.
     * @return True if there is a wall at the bottom of this tile.
     */
    public boolean getWallD() { return wallD; }

    /**
     * Returns true if there is a wall to the right of this tile.
     * @return True if there is a wall to the right of this tile.
     */
    public boolean getWallR() { return wallR; }

    /**
     * Returns true if there is a wall to the left of this tile.
     * @return True if there is a wall to the left of this tile.
     */
    public boolean getWallL() { return wallL; }

    /**
     * Returns true if there is a door at the top of this tile.
     * @return True if there is a door at the top of this tile.
     */
    public boolean getDoorU() { return doorU; }

    /**
     * Returns true if there is a door at the bottom of this tile.
     * @return True if there is a door at the bottom of this tile.
     */
    public boolean getDoorD() { return doorD; }

    /**
     * Returns true if there is a door to the right of this tile.
     * @return True if there is a door to the right of this tile.
     */
    public boolean getDoorR() { return doorR; }

    /**
     * Returns true if there is a door to the left of this tile.
     * @return True if there is a door to the left of this tile.
     */
    public boolean getDoorL() { return doorL; }

    /**
     * Returns true if there is a locked door at the top of this tile.
     * @return True if there is a locked door at the top of this tile.
     */
    public boolean getLockU() { return lockU; }

    /**
     * Returns true if there is a locked door at the bottom of this tile.
     * @return True if there is a locked door at the bottom of this tile.
     */
    public boolean getLockD() { return lockD; }

    /**
     * Returns true if there is a locked door to the right of this tile.
     * @return True if there is a locked door to the right of this tile.
     */
    public boolean getLockR() { return lockR; }

    /**
     * Returns true if there is a locked door to the left of this tile.
     * @return True if there is a locked door to the left of this tile.
     */
    public boolean getLockL() { return lockL; }

    /**
     * Returns true if this tile contains an exit.
     * @return True if this tile contains an exit.
     */
    public boolean getExit() { return exit; }

    /**
     * Returns true if this tile contains a fire.
     * @return True if this tile contains a fire.
     */
    public boolean getFire() { return fire; }

    /**
     * Returns true if this tile contains a fire sensor.
     * @return True if this tile contains a fire.
     */
    public boolean getFireSensor() { return fireSensor; }

    /**
     * Returns true if this tile contains a sensor that has been activated.
     * @return True if this tile contains a sensor that has been activated.
     */
    public boolean getFireSensorActive() { return fireSensorActive; }

    /**
     * Returns true if this tile contains a fire alarm.
     * @return True if this tile contains a fire alarm.
     */
    public boolean getFireAlarm() { return fireAlarm; }

    /**
     * Returns true if this tile contains a fire alarm that is active.
     * @return True if this tile contains a fire alarm that is active.
     */
    public boolean getFireAlarmActive() { return fireAlarmActive; }

    /**
     * Returns true if this tile contains a sprinkler.
     * @return True if this tile contains a sprinkler.
     */
    public boolean getSprinkler() { return sprinkler; }

    /**
     * Returns true if this tile contains a sprinkler that is active.
     * @return True if this tile contains a sprinkler that is active.
     */
    public boolean getSuppressorActive() { return sprinklerActive; }

    /**
     * Returns true if this tile is currently suppressing fire.
     * This indicates whether or not a sprinkler has sprayed water on a tile
     * to extinguish a fire.
     * @return True if this tile is currently suppressing fire.
     */
    public boolean getSuppression() {
        final long suppressionTime = 1000;
        if(suppression==-1 || System.currentTimeMillis()-suppression>suppressionTime) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if this tile contains a person.
     * @return True if this tile contains a person.
     */
    public boolean getHumanAgent() { return humanAgent; }

    /**
     * Returns true if there is a person in this tile and the person is aware of
     * a danger in the building.
     * @return True if there is a person in this tile that is fleeing.
     */
    public boolean getHumanAgentActive() { return humanAgentActive; }

    /**
     * Returns the system time in milliseconds that the a person or intruder 
     * last entered this tile.
     * This information enables the simulation to limit the movement speed of
     * people and intruders.
     * @return The system time in milliseconds that a person last entered this tile.
     */
    public long getLastMoveTime() { return lastMoveTime; }

    /**
     * Returns true if this tile contains equipment.
     * @return True if this tile contains equipment.
     */
    public boolean getEquipment() { return equipment; }

    /**
     * Returns true if this tile contains equipment that is active.
     * @return True if this tile contains equipment that is active.
     */
    public boolean getEquipmentActive() { return equipmentActive; }

    /**
     * Returns true if this tile contains an intruder.
     * @return True if this tile contains an intruder.
     */
    public boolean getIntruder() { return intruder; }

    /**
     * Returns true if this tile contains an intruder that is fleeing.
     * @return True if this tile contains an intruder that is fleeing.
     */
    public boolean getIntruderFleeing() { return intruderFleeing; }
    
    public boolean getCamera() { return camera; }
    /**
     * Adds or removes a wall at the top of this tile.
     * If a wall is added on top of a door, then the door will be removed.
     * @param status If true, add a wall, otherwise remove a wall.
     */
    public void setWallU(boolean status) { 
        wallU = status;
        if(status) doorU = false;
    }

    /**
     * Adds or removes a wall at the bottom of this tile.
     * If a wall is added on top of a door, then the door will be removed.
     * @param status If true, add a wall, otherwise remove a wall.
     */
    public void setWallD(boolean status) { 
        wallD = status;
        if(status) doorD = false;
    }

    /**
     * Adds or removes a wall to the right of this tile.
     * If a wall is added on top of a door, then the door will be removed.
     * @param status If true, add a wall, otherwise remove a wall.
     */
    public void setWallR(boolean status) { 
        wallR = status;
        if(status) doorR = false;
    }

    /**
     * Adds or removes a wall to the left of this tile.
     * If a wall is added on top of a door, then the door will be removed.
     * @param status If true, add a wall, otherwise remove a wall.
     */
    public void setWallL(boolean status) { 
        wallL = status;
        if(status) doorL = false;
    }

    /**
     * Adds or removes a door at the top of this tile.
     * If a door is added on top of a wall, then the wall will be removed.
     * @param status If true, add a door, otherwise remove a door.
     */
    public void setDoorU(boolean status) {  
        doorU = status;
        if(status) wallU = false;
    }

    /**
     * Adds or removes a door at the bottom of this tile.
     * If a door is added on top of a wall, then the wall will be removed.
     * @param status If true, add a door, otherwise remove a door.
     */
    public void setDoorD(boolean status) {  
        doorD = status;
        if(status) wallD = false;
    }

    /**
     * Adds or removes a door to the right of this tile.
     * If a door is added on top of a wall, then the wall will be removed.
     * @param status If true, add a door, otherwise remove a door.
     */
    public void setDoorR(boolean status) {  
        doorR = status;
        if(status) wallR = false;
    }

    /**
     * Adds or removes a door to the left of this tile.
     * If a door is added on top of a wall, then the wall will be removed.
     * @param status If true, add a door, otherwise remove a door.
     */
    public void setDoorL(boolean status) {  
        doorL = status;
        if(status) wallL = false;
    }

    /**
     * Locks or unlocks the door at the top of this tile, if there is one.
     * @param status If true, lock the door, otherwise unlock door.
     */
    public void setLockU(boolean status) {
        if(doorU) lockU = status;
    }

    /**
     * Locks or unlocks the door at the bottom of this tile, if there is one.
     * @param status If true, lock the door, otherwise unlock door.
     */
    public void setLockD(boolean status) {
        if(doorD) lockD = status;
    }

    /**
     * Locks or unlocks the door to the right of this tile, if there is one.
     * @param status If true, lock the door, otherwise unlock door.
     */
    public void setLockR(boolean status) {
        if(doorR) lockR = status;
    }

    /**
     * Locks or unlocks the door to the left of this tile, if there is one.
     * @param status If true, lock the door, otherwise unlock door.
     */
    public void setLockL(boolean status) {
        if(doorL) lockL = status;
    }

    /**
     * Adds or removes an exit from this tile.
     * @param status If true, add an exit, otherwise remove an exit.
     */
    public void setExit(boolean status) { 
        exit = status;
    }

    /**
     * Adds or removes fire from this tile.
     * @param status If true, add a fire, otherwise remove a fire.
     */
    public void setFire(boolean status) {
        fire = status;
    }

    /**
     * Adds or removes an exit from this tile.
     * @param status If true, add an exit, otherwise remove an exit.
     */
    public void setFireSensor(boolean status) {
        fireSensor = status;
    }

    /**
     * Enables or disables the sensor in this tile if there is one.
     * @param status If true, enable the sensor, otherwise disable the sensor.
     */
    public void setFireSensorActive(boolean status) {
        if(fireSensor) fireSensorActive = status;
    }

    /**
     * Adds or removes a fire alarm from this tile.
     * @param status If true, add a fire alarm, otherwise remove the alarm.
     */
    public void setFireAlarm(boolean status) { 
        fireAlarm = status;
    }

    /**
     * Enables or disables the alarm in this tile if there is one.
     * @param status If true, enable the alarm, otherwise disable the alarm.
     */
    public void setFireAlarmActive(boolean status) {
        if(fireAlarm) fireAlarmActive = status;
    }

    /**
     * Adds or removes a sprinkler from this tile.
     * @param status If true, add a sprinkler, otherwise remove it.
     */
    public void setSprinkler(boolean status) {
        sprinkler = status;
    }

    /**
     * Activates or deactivates the sprinkler in this tile if there is one.
     * @param status If true, activate the sensor, otherwise deactivate it.
     */
    public void setSuppressorActive(boolean status) { 
        if(sprinkler) sprinklerActive = status;
    }

    /**
     * Activates or deactivates fire suppression in this tile.
     * @param status If true, activate fire suppression, otherwise deactivate it.
     */
    public void setSuppression(boolean status) {
        suppression = status? System.currentTimeMillis() : -1;
    }

    /**
     * Adds or removes a person from this tile.
     * @param status If true, add a person, otherwise remove a person.
     */
    public void setHumanAgent(boolean status) { 
        humanAgent = status;
        if (status) {
            equipment = false;
            intruder = false;
        }
    }

    /**
     * Causes a person to flee or not.
     * @param status If true, causes the person to flee, otherwise not flee.
     */
    public void setHumanAgentActive(boolean status) {
        if(humanAgent) humanAgentActive = status;
    }

    /**
     * Sets the system time in milliseconds that a person entered the tile.
     * @param time The system time in milliseconds.
     */
    public void setLastMoveTime(long time) {
        lastMoveTime = time;
    }

    /**
     * Adds or removes equipment from this tile.
     * @param status If true, add equipment, otherwise remove equipment.
     */
    public void setEquipment(boolean status) {
        equipment = status;
        if(status) {
            humanAgent = false;
            intruder = false;
        }
    }

    /**
     * Enable or disable equipment in this tile if this tile has equipment.
     * @param status If true, add equipment, otherwise remove it.
     */
    public void setEquipmentActive(boolean status) {
        if(equipment) equipmentActive = status;
    }

    /**
     * Adds or removes an intruder from this tile.
     * @param status If true, add an intruder, otherwise remove an intruder.
     */
    public void setIntruder(boolean status) {
        intruder = status;
        if (status) {
            humanAgent = false;
            equipment = false;
        }
    }

    /**
     * Causes the intruder to flee or not, if this tile contains an intruder.
     * @param status If true, cause the intruder to flee,
     */
    public void setIntruderFleeing(boolean status) {
        if (intruder) intruderFleeing = status;
    }
    
    public void setCamera(boolean status) {
        camera = status;
    }
}