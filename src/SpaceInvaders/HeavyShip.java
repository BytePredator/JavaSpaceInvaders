/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * Classe della nave pesante
 * @author byte-predator
 * @see Ship
 */
public class HeavyShip extends Ship{
    /**
     * Costruttore della classe nave pesante
     * @param x posizione sull'asse x della nave
     * @param y posizione sull'asse y della nave
     */
    public HeavyShip(int x, int y){
        super(x,y,3,30);
        this.image[0]="img/heavy1.png";
        this.image[1]="img/heavy2.png";
    }
    
    
    /**
     * Metodo per clonare una nave pesante
     * @return nuova istanza della nave clonata
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
