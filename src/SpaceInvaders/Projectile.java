/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * Classe Proiettile.<br>
 * Ogni proiettile ha una posizione nel campo di battaglia, una direzione 
 * e un danno da infliggere al bersaglio.
 * @author byte-predator
 */
public class Projectile {
    private int x;
    private int y;
    private int direction;
    private int damage;
    
    
    /**
     * Costruttore della classe prottile
     * @param x posizione sull'asse x del proiettile
     * @param y posizione sull'asse y del proiettile
     * @param direction direzione del priettile: 1 per l'alto, -1 per il basso
     * @param damage danno da infliggere al bersaglio
     */
    public Projectile(int x, int y, int direction, int damage){
        this.x=x;
        this.y=y;
        this.direction=direction;
        this.damage=damage;
    }
    
    
    /**
     * Metodo da richiamare ogni frame per aggiornare lo stato del proiettile
     */
    public void Update(){
        this.y+=direction;
    }
    
    
    /**
     * Metodo per ottenere la posizione sull'asse x del proiettile
     * @return posizione sull'asse x
     */
    public int GetX(){
        return this.x;
    }
    
    
    /**
     * Metodo per ottenere la posizione sull'asse y del proiettile
     * @return posizione sull'asse y
     */
    public int GetY(){
        return this.y;
    }
    
    
    /**
     * Metodo per ottenere il danno da infliggere in caso di collisione
     * @return danno da infliggere
     */
    public int GetDamage(){
        return this.damage;
    }
    
    
    /**
     * Metodo per ottenere la direzione del proiettile
     * @return direzione del proiettile: -1 per l'alto, 1 per il basso
     */
    public int GetDirection(){
        return this.direction;
    }
    
    
    /**
     * Metodo astratto per clonare un priettile
     * @return nuova istanza del proiettile clonata
     */
    public Projectile Clone(){
        return new Projectile(this.x, this.y, this.direction, this.damage);
    }
}
