/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
import java.util.ArrayList;

/**
 * Classe della flotta.<br>
 * Una flotta è composta da navi e possiede una posizione sul campo di battaglia.<br>
 * Una flotta di invasori è composta da vari tipi di navi (leggere, intermedie e pesanti),
 * il suo scopo è quello di atterrare sul pianeta da invadere.<br>
 * Una flotta di difesa invece è composta da un' unica unità anti aerea.
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
     * Costrutore della flotta.<br>
     * In caso di una flotta di invasione genera il numero di navi specificato 
     * dai primi tre parametri, in caso di una flotta di difesa i primi tre 
     * parametri vengono scartati e viene generata una sola unità anti aerea.
     * @param LightShips numero intero positivo di navi leggere
     * @param MidShips numero intero positivo di navi intermedie
     * @param HeavyShips numero intero positivo di navi pesanti
     * @param invaders true se è una flotta di invasione. false se è una flotta di difesa
     * @see LightShip
     * @see MidShip
     * @see HeavyShip
     * @see AntiAir
     */
    public Fleet(int LightShips, int MidShips, int HeavyShips, boolean invaders){
        this.ships = new ArrayList<Ship>();
        this.x = 0;
        this.y = 0;
        this.direction = 1;
        this.invaders = invaders;
        this.frame=0;
        if(invaders){
            if(LightShips<0)
                LightShips=0;
            if(MidShips<0)
                MidShips=0;
            if(HeavyShips<0)
                HeavyShips=0;
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
     * Metodo per ottenere la posizione sull'asse x della flotta
     * @return posizione sull'asse x
     */
    public int GetX(){
        return this.x;
    }
    
    
    /**
     * Metodo per ottenere la posizione sull'asse x della flotta
     * @return posizione sull'asse x
     */
    public int GetY(){
        return this.y;
    }
    
    
    /**
     * Setta la posizione della flotta sull'asse x
     * @param x nuova posizione sull'asse x
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
     * Setta la posizione della flotta sull'asse y
     * @param y nuova posizione sull'asse y
     */
    public void SetY(int y){
        this.y=y;
    }
    
    
    /**
     * Metodo che controlla se in una data posizione è presente una nave, in caso
     * avvenga la collisione, applica alla nave il danno specificato.<br>
     * Se la nave viene distrutta ne ritorna il punteggio.
     * @param x posizione sull'asse x della collisione
     * @param y posizione sull'asse y della collisione
     * @param damage danno da applicare
     * @return 0 se non è avvenuta nessuna collisione, -1 se è avvenuta una 
     * collisione ma la nave non è stata distrutta, il numero intero positivo del
     * punteggio della nave in caso questa venga distrutta dalla collisione
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
     * Metodo che restituisce il numero di pixel, a partire dal lato superiore 
     * del campo di battaglia, della nave a quota più bassa.
     * @return distanza in pixel del lato superiore del campo e la nave a quota inferiore
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
     * Metodo da richiamare ogni frame per aggiornare lo stato della flotta
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
     * Metodo che restituisce una copia delle navi della flotta
     * @return ArrayList di navi
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
