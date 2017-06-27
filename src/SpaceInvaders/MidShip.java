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
public class MidShip extends Ship{
    public MidShip(int x, int y){
        super(x,y,2,20);
        this.image[0]="img/mid1.png";
        this.image[1]="img/mid2.png";
    }
    
    public Ship Clone(){
        MidShip c = new MidShip(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.frame=this.frame;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
}
