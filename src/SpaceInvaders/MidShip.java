/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * Classe della nave intermedia
 * @author byte-predator
 * @see Ship
 */
public class MidShip extends Ship{
    /**
     * Costruttore della classe nave intermedia
     * @param x posizione sull'asse x della nave
     * @param y posizione sull'asse y della nave
     */
    public MidShip(int x, int y){
        super(x,y,2,20);
        this.image[0]="img/mid1.png";
        this.image[1]="img/mid2.png";
    }
    
    
    /**
     * Metodo per clonare una nave intermedia
     * @return nuova istanza della nave clonata
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
