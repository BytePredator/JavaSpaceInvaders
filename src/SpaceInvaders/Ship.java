/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
/**
 * Ship Class.<br>
 * 
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
    
    public void Damage(int d){
        this.life-=d;
        if(this.life<0)
            this.life=0;
    }
    
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
    
    public void SetX(int x){
        this.x=x;
    }
    
    public void SetY(int y){
        this.y=y;
    }
    
    public int GetLife(){
        return this.life;
    }
    
    public String GetImage(){
        if(this.life>0){
            return this.image[this.frame];
        }else if(this.explosion_counter>0)
            return this.explosion;
        return "";
    }
    
    public int GetX(){
        return this.x;
    }
    
    public int GetY(){
        return this.y;
    }
    
    public int GetScore(){
        return this.score;
    }
    
    public int GetExplosionCounter(){
        return this.explosion_counter;
    }
    
    public static int ShipWidth(){
        return 26;
    }
    
    public static int ShipHeight(){
        return 18;
    }
    
    public abstract Ship Clone();
}
