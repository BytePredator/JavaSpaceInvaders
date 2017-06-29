/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * AntiAir Class
 * @author byte-predator
 */
public class AntiAir extends Ship {
    /**
     * Creates a new AntiAir Ship
     * @param x position of the ship on the X axe
     * @param y position of the ship on the Y axe
     */
    public AntiAir(int x, int y){
        super(x,y,1,1);
        this.image[0]="img/aa.png";
        this.image[1]="img/aa.png";
    }
    
    
    /**
     * Creates a clone of AntiAir
     * @return a cloned instance of the AntiAir 
     */
    public Ship Clone(){
        AntiAir c = new AntiAir(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
    
}
