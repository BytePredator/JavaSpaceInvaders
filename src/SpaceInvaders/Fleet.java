/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
import java.util.ArrayList;

/**
 *
 * @author byte-predator
 */
public class Fleet {
    private ArrayList<Ship> ships;
    private int x;
    private int y;
    private int direction;
    private boolean invaders;
    private int frame;
    
    public Fleet(int LightShips, int MidShips, int HeavyShips, boolean invaders){
        this.ships = new ArrayList<Ship>();
        this.x = 0;
        this.y = 0;
        this.direction = 1;
        this.invaders = invaders;
        this.frame=0;
        if(invaders){
            int i;
            for(i=0; i<HeavyShips; i++){
                double line = (int)(i/(Game.FieldWidth()/(13+Ship.ShipWidth())-2))*(13+Ship.ShipHeight());
                double col  = (i%((int)(Game.FieldWidth()/(13+Ship.ShipWidth())-2)))*(13+Ship.ShipWidth());
                this.ships.add(new HeavyShip(
                    (int)col, 
                    (int)line
                ));
            }
            for(; i<HeavyShips+MidShips; i++){
                double line = (int)(i/(Game.FieldWidth()/(13+Ship.ShipWidth())-2))*(13+Ship.ShipHeight());
                double col  = (i%((int)(Game.FieldWidth()/(13+Ship.ShipWidth())-2)))*(13+Ship.ShipWidth());
                this.ships.add(new MidShip(
                    (int)col, 
                    (int)line
                ));
            }
            for(; i<HeavyShips+MidShips+LightShips; i++){
                double line = (int)(i/(Game.FieldWidth()/(13+Ship.ShipWidth())-2))*(13+Ship.ShipHeight());
                double col  = (i%((int)(Game.FieldWidth()/(13+Ship.ShipWidth())-2)))*(13+Ship.ShipWidth());
                this.ships.add(new LightShip(
                    (int)col, 
                    (int)line
                ));
            }
        }else{
            this.x=(Game.FieldWidth()-Ship.ShipWidth())/2;
            this.y=Game.FieldHeight()-Ship.ShipHeight();
            this.ships.add(new AntiAir(0,0));
        }
    }
    
    public int GetX(){
        return this.x;
    }
    
    public int GetY(){
        return this.y;
    }
    
    public void SetX(int x){
        int minX=Game.FieldWidth();
        int maxX=0;
        for(Ship s : this.ships){
            if(s.GetLife()>0){
                int t=s.GetX();
                if(t<minX)
                    minX=t;
                if(t>maxX)
                    maxX=t;
            }
        }
        if(x+minX<0){
            this.x=-minX;
        }
        else if(x+maxX+Ship.ShipWidth()>Game.FieldWidth())
            this.x=Game.FieldWidth()-maxX-Ship.ShipWidth();
        else
            this.x=x;
    }
    
    public void SetY(int y){
        this.y=y;
    }
    
    public int CollisionCheck(int x, int y, int damage){
        for(Ship s: this.ships){
            if(s.GetLife()>0||s.GetExplosionCounter()>0)
                if(s.GetX()+this.x <= x && s.GetX()+this.x+Ship.ShipWidth() >= x)
                    if(s.GetY()+this.y <= y && s.GetY()+this.y+Ship.ShipHeight() >= y){
                        s.Damage(damage);
                        if(s.GetLife()==0)
                            return s.GetScore();
                        else
                            return -1;
                    }
        }   
        return 0;
    }
    
    public int Altitude(){
        int y=0;
        for(Ship s : this.ships){
            if(s.GetLife()<=0||s.GetExplosionCounter()<=0)
                continue;
            int t=s.GetY();
            if(t>y)
                y=t;
        }
        return y+this.y;
    }
    
    public void Update(){
        if(this.invaders){
            ArrayList<Ship> r = this.GetShips();
            int delay=10;
            if(r.size()<=1)
                delay=1;
            else if(r.size()<=5)
                delay=2;
            else if(r.size()<=10)
                delay=5;
            this.frame++;
            if(this.frame%10==0){
                for(Ship s: this.ships)
                    s.Update();
            }
            if(this.frame%delay==0){
                int x = this.x;
                this.SetX(x+this.direction*Ship.ShipWidth());
                if(this.x==x){
                    this.SetY(this.y+Ship.ShipHeight());
                    this.direction*=-1;
                }
            }
        }
    }
    
    public ArrayList<Ship> GetShips(){
        ArrayList<Ship> r = new ArrayList<Ship>();
            for( Ship s: this.ships){
                if(s.GetLife()>0||s.GetExplosionCounter()>0){
                    Ship tmp = s.Clone();
                    tmp.SetX(tmp.GetX()+this.x);
                    tmp.SetY(tmp.GetY()+this.y);
                    r.add(tmp);
                }
            }
        return r;
    }
}
