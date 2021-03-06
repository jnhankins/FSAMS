/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fsams.components;

import fsams.gui.View;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author sgomez118
 */
public class HumanAgent extends FSAMSComponent1D {
    protected double vX;
    protected double vY;
    
    public HumanAgent() {
        super();
        vX = 0;
        vY = 0;
    }
    public HumanAgent(double x1, double y1, double vX, double vY) {
        super(x1, y1);
        this.vX = vX;
        this.vY = vY;
    }
    public HumanAgent(HumanAgent humanAgent) {
        super(humanAgent);
        vX = humanAgent.vX;
        vY = humanAgent.vY;
    }

    @Override
    public String getType() {
        return "human agent";
    }

    @Override
    public void draw(Graphics g, View v) {
    }
    

    /**
     * @return the vX
     */
    public double getVX() {
        return vX;
    }

    /**
     * @param vX the vX to set
     */
    public void setVX(double vX) {
        this.vX = vX;
    }

    /**
     * @return the vY
     */
    public double getVY() {
        return vY;
    }

    /**
     * @param vY the vY to set
     */
    public void setVY(double vY) {
        this.vY = vY;
    }

    @Override
    public FSAMSComponent1D copy() {
        return new HumanAgent(this);
    }

    @Override
    public void update(ComponentManager component, double dTime){
        setX1(getX1() + vX*dTime);
        setY1(getY1() + vY*dTime);
    }
}
