/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
/**
 * Classe astratta di una generica nave.<br>
 * Ogni nave possiede una posizione nel campo di battaglia, una vita e un punteggio.<br>
 * Ogni nuovo frame l'immagine della nave viene modificata in modo da creare un'animazione.<br>
 * Quando una nave termina la vita inizia l'animazione dell'esposione.<br>
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
     * Costruttore della classe nave
     * @param x posizione sull'asse x della nave
     * @param y posizione sull'asse y della nave
     * @param life vita della nave
     * @param score punteggio ottenuto dalla distruzione della nave
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
     * Metodo che riduce la vita della nave in base al danno subito
     * @param d valore intero e positivo del danno subito
     */
    public void Damage(int d){
        if(d<0)
            d=0;
        this.life-=d;
        if(this.life<0)
            this.life=0;
    }
    
    
    /**
     * Metodo da richiamare ogni frame per aggiornare lo stato della nave
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
     * Setta la posizione della nave sull'asse x
     * @param x nuova posizione sull'asse x
     */
    public void SetX(int x){
        this.x=x;
    }
    
    
    /**
     * Setta la posizione della nave sull'asse y
     * @param y nuova posizione sull'asse y
     */
    public void SetY(int y){
        this.y=y;
    }
    
    
    /**
     * Metodo per la restituzione della quantità di vita rimanente alla nave
     * @return vita rimanente alla nave
     */
    public int GetLife(){
        return this.life;
    }
    
    
    /**
     * Metodo per ottenere il percorso dell'imagine da usare per graficare la nave
     * @return percorso dell'immagine
     */
    public String GetImage(){
        if(this.life>0){
            return this.image[this.frame];
        }else if(this.explosion_counter>0)
            return this.explosion;
        return "";
    }
    
    
    /**
     * Metodo per ottenere la posizione sull'asse x della nave
     * @return posizione sull'asse x
     */
    public int GetX(){
        return this.x;
    }
    
    
    /**
     * Metodo per ottenere la posizione sull'asse y della nave
     * @return posizione sull'asse y
     */
    public int GetY(){
        return this.y;
    }
    
    
    /**
     * Metodo per ottenere il punteggio della nave
     * @return punteggio
     */
    public int GetScore(){
        return this.score;
    }
    
    
    /**
     * Metodo per ottenere il valore del contatore dell'esplosione
     * @return numero di frame rimanenti per l'animazione dell'esplosione
     */
    public int GetExplosionCounter(){
        return this.explosion_counter;
    }
    
    
    /**
     * Metodo di classe per ottenere la larghezza di una generica nave
     * @return numero di pixel della larghezza della nave
     */
    public static int ShipWidth(){
        return 26;
    }
    
    
    /**
     * Metodo di classe per ottenere l'altezza di una generica nave
     * @return numero di pixel dell' altezza della nave
     */
    public static int ShipHeight(){
        return 18;
    }
    
    
    /**
     * Metodo astratto per clonare una nave
     * @return nuova istanza della nave clonata
     */
    public abstract Ship Clone();
}
