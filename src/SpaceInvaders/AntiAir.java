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
public class AntiAir extends Ship {
    
    public AntiAir(int x, int y){
        super(x,y,1,1);
        this.image[0]="img/aa.png";
        this.image[1]="img/aa.png";
    }
    
    public Ship Clone(){
        AntiAir c = new AntiAir(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
    
}
