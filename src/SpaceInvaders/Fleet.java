/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
import java.util.ArrayList;

/**
 * Fleet Class.<br>
 * A fleet is made up of ships and have a position on the combat field.<br>
 * An invader's fleet is made up of different typs of ships (light, mid and heavy),
 * his goal is to land on the planet to invade.<br>
 * A defence fleet, instead, is made up of only one AntiAir unit
 * @author byte-predator
 */
public class Fleet {
    private ArrayList<Ship> ships;
    private int x;
    private int y;
    private int direction;
    private boolean invaders;
    private int frame;
    /**
     * Creates a new Fleet.<br>
     * If it's an invasion fleet, it generates the requested numbers of ships
     * based on the first three parameters, else if it's a defence fleet, it
     * discards the first three parameters and generates only one AntiAit unit.  
     * @param LightShips positive integer number of light ships
     * @param MidShips positive integer number of mid ships
     * @param HeavyShips positive integer number of heavy ships
     * @param invaders true if it's an invasion fleet, false if it's a defence one
     */
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
    
    
    /**
     * Method to get X axe's position of the fleet
     * @return position on X axe
     */
    public int GetX(){
        return this.x;
    }
    
    
    /**
     * Method to get Y axe's position of the fleet
     * @return position on Y axe
     */
    public int GetY(){
        return this.y;
    }
    
    
    /**
     * Sets the fleet's position on the X axe
     * @param x position on X axe
     */
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
    
    
    /**
     * Sets the fleet's position on the Y axe
     * @param y position on Y axe
     */
    public void SetY(int y){
        this.y=y;
    }
    
    
    /**
     * Method that checks if in a certain position there's a ship, if there's a
     * collision it applies to the ship the specified damage.<br>
     * If the ship gets destroyed returns his score
     * @param x collision's position on the X axe
     * @param y collision's position on the Y axe
     * @param damage damage to apply
     * @return 0 if there wasn't a collision, -1 if a collision occurred but
     * the ship didn't got destroyed, the positive integer ship's score if a
     * collision occurred and the ship got destroyed
     */
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
    
    
    /**
     * Method to get the number of pixels, from the top side of the combat field
     * to the ship at the lower quota
     * @return distance in pixels from the top side of the field to the ship at
     * the lower quota
     */
    public int Distance(){
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
    
    
    /**
     * Method to call each frame to update the fleet's state
     */
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
    
    
    /**
     * Methos to get a copy of the ships in the fleet
     * @return ArayList of ship
     * @see Ship
     */
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
