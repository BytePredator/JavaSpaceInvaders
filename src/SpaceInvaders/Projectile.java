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
public class Projectile {
    private int x;
    private int y;
    private int direction;
    private int damage;
    
    public Projectile(int x, int y, int direction, int damage){
        this.x=x;
        this.y=y;
        this.direction=direction;
        this.damage=damage;
    }
    
    public void Update(){
        this.y+=direction;
    }
    
    public int GetX(){
        return this.x;
    }
    
    public int GetY(){
        return this.y;
    }
    
    public int GetDamage(){
        return this.damage;
    }
    
    public int GetDirection(){
        return this.direction;
    }
    
    public Projectile Clone(){
        return new Projectile(this.x, this.y, this.direction, this.damage);
    }
}
