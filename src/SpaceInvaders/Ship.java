/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
/**
 * Ship Class.<br>
 * Each ship has a position on the combat field, a life and a score.<br>
 * Each new frame the image of the ship changes in order to create an animation
 * effect.<br>When a ship ends his life, the explosion animation starts.
 * @author byte-predator
 */
public abstract class Ship {
    private int x;
    private int y;
    private String explosion;
    protected int explosion_counter;
    protected String[] image;
    protected int frame;
    protected int score;
    protected int life;
    
    
    /**
     * Creates a new Ship
     * @param x ship's position on the X axe
     * @param y ship's position on the Y axe
     * @param life ship's life
     * @param score score gained from ship's destruction
     */
    public Ship(int x, int y,int life, int score){
        this.frame=0;
        this.x=x;
        this.y=y;
        this.life=life;
        this.image=new String[2];
        this.explosion="img/explosion.png";
        this.explosion_counter=1;
        this.score=score;
    }
    
    
    /**
     * Method that reduces ship's life based on received damage
     * @param d received damage's positive integer value
     */
    public void Damage(int d){
        this.life-=d;
        if(this.life<0)
            this.life=0;
    }
    
    
    /**
     * Method to call each frame to update the ship's state
     */
    public void Update(){
        this.frame++;
        if(frame>=2)
            frame=0;
        if(this.life==0){
            this.explosion_counter-=1;
            if(this.explosion_counter<0)
                this.explosion_counter=0;
        }
    }
    
    
    /**
     * Sets the ship's position on the X axe
     * @param x position on X axe
     */
    public void SetX(int x){
        this.x=x;
    }
    
    
    /**
     * Sets the ship's position on the Y axe
     * @param y position on Y axe
     */
    public void SetY(int y){
        this.y=y;
    }
    
    
    /**
     * Method to get the remaining ship's life
     * @return remaining ship's life
     */
    public int GetLife(){
        return this.life;
    }
    
    
    /**
     * Method to get the image's path used to render the ship
     * @return image's path
     */
    public String GetImage(){
        if(this.life>0){
            return this.image[this.frame];
        }else if(this.explosion_counter>0)
            return this.explosion;
        return "";
    }
    
    
    /**
     * Method to get the ship's position on the X axe
     * @return position on X axe
     */
    public int GetX(){
        return this.x;
    }
    
    
    /**
     * Method to get the ship's position on the Y axe
     * @return position on Y axe
     */
    public int GetY(){
        return this.y;
    }
    
    
    /**
     * Method to get the ship's score
     * @return score
     */
    public int GetScore(){
        return this.score;
    }
    
    
    /**
     * Method to get the explosion counter's value
     * @return number of explosion animation's remaining frames 
     */
    public int GetExplosionCounter(){
        return this.explosion_counter;
    }
    
    
    /**
     * Class method to get the ship's width
     * @return ship's width in pixels
     */
    public static int ShipWidth(){
        return 26;
    }
    
    
    /**
     * Class method to get the ship's height
     * @return ship's height in pixels
     */
    public static int ShipHeight(){
        return 18;
    }
    
    
    /**
     * Abstract method to cone a ship
     * @return new cloned ship's instance
     */
    public abstract Ship Clone();
}
