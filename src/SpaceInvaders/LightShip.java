/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 *
 * @author byte-predator
 */
public class LightShip extends Ship{
    public LightShip(int x, int y){
        super(x,y,1,10);
        this.image[0]="img/light1.png";
        this.image[1]="img/light2.png";
    }
    
    public Ship Clone(){
        LightShip c = new LightShip(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.frame=this.frame;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
    
}
