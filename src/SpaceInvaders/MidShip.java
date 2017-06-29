/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 *  MidShip Class
 * @author byte-predator
 */
public class MidShip extends Ship{
    /**
     * Creates a new MidShip
     * @param x ship's position on the X axe
     * @param y ship's position on the Y axe
     */
    public MidShip(int x, int y){
        super(x,y,2,20);
        this.image[0]="img/mid1.png";
        this.image[1]="img/mid2.png";
    }
    
    
    /**
     * Creates a clone of MidShip
     * @return a cloned instance of the MidShip 
     */
    public Ship Clone(){
        MidShip c = new MidShip(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.frame=this.frame;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
}
