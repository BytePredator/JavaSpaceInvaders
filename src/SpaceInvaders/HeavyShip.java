/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * HeavyShip Class
 * @author byte-predator
 */
public class HeavyShip extends Ship{
    /**
     * Creates a new HeavyShip
     * @param x position of the ship on the X axe
     * @param y position of the ship on the Y axe
     */
    public HeavyShip(int x, int y){
        super(x,y,3,30);
        this.image[0]="img/heavy1.png";
        this.image[1]="img/heavy2.png";
    }
    
    
    /**
     * Creates a clone of HeavyShip
     * @return a cloned instance of the HeavyShip 
     */
    public Ship Clone(){
        HeavyShip c = new HeavyShip(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.frame=this.frame;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
    
}
