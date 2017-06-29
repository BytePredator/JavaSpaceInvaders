/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * Classe dell' anti aerea
 * @author byte-predator
 * @see Ship
 */
public class AntiAir extends Ship {
    /**
     * Costruttore della classe anti aerea
     * @param x posizione sull'asse x della nave
     * @param y posizione sull'asse y della nave
     */
    public AntiAir(int x, int y){
        super(x,y,1,1);
        this.image[0]="img/aa.png";
        this.image[1]="img/aa.png";
    }
    
    
    /**
     * Metodo per clonare un' anti aerea
     * @return nuova istanza della nave clonata
     */
    public Ship Clone(){
        AntiAir c = new AntiAir(this.GetX(), this.GetY());
        c.life=this.life;
        c.image=this.image;
        c.explosion_counter=this.explosion_counter;
        return c;
    }
    
}
