/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

/**
 * Projectile Class<br>
 * Each projectile has a position on the combat field, a direction and a damage
 * to apply to the target
 * @author byte-predator
 */
public class Projectile {
    private int x;
    private int y;
    private int direction;
    private int damage;
    
    
    /**
     * Creates a new projectile
     * @param x projectile's position on the X axe
     * @param y projectile's position on the Y axe
     * @param direction direction of the projectile: 1 to top, -1 to bottom
     * @param damage damage to apply to the target
     */
    public Projectile(int x, int y, int direction, int damage){
        this.x=x;
        this.y=y;
        this.direction=direction;
        this.damage=damage;
    }
    
    
    /**
     * Method to call each frame to update the projectile's state
     */
    public void Update(){
        this.y+=direction;
    }
    
    
    /**
     * Method to get the projectile's position on X axe
     * @return position on X axe
     */
    public int GetX(){
        return this.x;
    }
    
    
    /**
     * Method to get the projectile's position on Y axe
     * @return position on Y axe
     */
    public int GetY(){
        return this.y;
    }
    
    
    /**
     * Method to get the damage to apply in case of collision
     * @return damage to apply
     */
    public int GetDamage(){
        return this.damage;
    }
    
    
    /**
     * Methos to get the direction of the projectile
     * @return direction of the projectile: -1 for the top, 1 for the bottom
     */
    public int GetDirection(){
        return this.direction;
    }
    
    
    /**
     * Method to clone a projectile
     * @return a cloned instance of the projectile
     */
    public Projectile Clone(){
        return new Projectile(this.x, this.y, this.direction, this.damage);
    }
}
