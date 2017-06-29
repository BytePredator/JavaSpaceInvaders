/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe del gioco.<br>
 * la classe gioco rappresenta il controller del programa, prepara il campo di battaglia,
 * gestisce la difficoltà della partita, salva il punteggio corrente e il punteggio massimo,
 * acquisisce i comandi del giocatore, modifica lo stato delle flotte e restituisce
 * all'interfaccia le informazioni da mostrare al giocatore
 * @author byte-predator
 */
public class Game {
    private Fleet InvadersFleet;
    private Fleet EarthFleet;
    private ArrayList<Projectile> projectiles;
    private int direction;
    private long time;
    private int state;
    private int score;
    private int hiscore;
    private int life;
    private int level;
    private Random rand;
    private int[][] level_data;
    
    
    /**
     * Costruttore della classe gioco
     */
    public Game(){
        this.InvadersFleet = new Fleet(2,0,0,true);
        this.EarthFleet = new Fleet(0,0,0,false);
        this.projectiles = new ArrayList<Projectile>();
        this.direction = 0;
        this.time = 0;
        this.state = 0;             //0 new-game; 1 run; 2 game-over; 3-15 paused;
        this.rand = new Random();
        this.life = 3;
        this.level = 0;
        this.level_data = new int[][] {{3, 5}, {3, 8}, {2, 10}, {2, 15}, {2, 20}, {1, 25}, {1, 30}};
        File file = new File("gamedata.txt");
        try {
            Scanner scanner = new Scanner(file);
            this.hiscore = scanner.nextInt();
        } catch (FileNotFoundException ex) {
            this.hiscore = 0;
        }
    }
    
    
    /**
     * Metodo da richiamare per aggiornare lo stato della partita
     * @param delta tempo in millisecondi trascorso dalla chiamata precedente
     */
    public void Update(float delta){
        int frame = (int)(delta+this.time)/100;
        if(this.state==1){
            ArrayList<Ship> tmp = this.InvadersFleet.GetShips();
            if(tmp.isEmpty()){
                this.state=3;
                this.level++;
                this.ResetField();
                return;
            }
            switch(this.direction){
                case -1:
                    this.EarthFleet.SetX((int)(this.EarthFleet.GetX()-delta/10));
                    break;
                case 1:
                    this.EarthFleet.SetX((int)(this.EarthFleet.GetX()+delta/10));
                    break;
            }
            for(int i=0; i<frame ;i++){
                this.InvadersFleet.Update();
                
                this.EarthFleet.Update();
                if(this.InvadersFleet.Distance()+Ship.ShipHeight()>=this.EarthFleet.GetY()){
                    this.UpdateHiScore();
                    this.state = 2;
                }
                int r = this.rand.nextInt((int)(tmp.size()*100/this.GetLevelFireProb()));
                if(r<tmp.size())
                    this.projectiles.add(new Projectile(
                        tmp.get(r).GetX()+Ship.ShipWidth()/2-1,
                        tmp.get(r).GetY(),
                        1,
                        1
                    ));
            }
            for(int i=0; i<(int)(delta+this.time)/25 ;i++){
                int f;
                ArrayList<Projectile> DeleteQueue = new ArrayList<Projectile>();
                for(Projectile p: this.projectiles){
                    p.Update();
                    if(p.GetDirection()<0){
                        f=this.InvadersFleet.CollisionCheck(p.GetX(),p.GetY(),p.GetDamage());
                        if(f>0)
                            this.score+=f*(this.level+1);
                    }else{
                        f=this.EarthFleet.CollisionCheck(p.GetX(),p.GetY(),p.GetDamage());
                        if(f>0){
                            this.EarthFleet = new Fleet(0,0,0,false);
                            this.score-=100;
                            this.life--;
                            if(this.life>=0)
                                this.state=3;
                            else{
                                this.UpdateHiScore();
                                this.state=2;
                                return;
                            }
                        }
                    }
                    if(f!=0||p.GetY()<0||p.GetY()>Game.FieldHeight())
                        DeleteQueue.add(p);
                }
                for(Projectile p: DeleteQueue)
                    this.projectiles.remove(p);
            }
        }else if(this.state>=15&&frame>0){
            this.state=1;
        }else if(this.state>=3){
            this.state+=frame;
        }else if(this.state==0){
            this.NewGame();
        }
        this.time = (long) (delta+this.time-frame*100);
    }
    
    
    /**
     * Metodo per spostare la flotta di difesa
     * @param d -1 per spostare la flotta di difesa a sinistra, 1 per spostare
     * la flotta a destra, 0 per farla rimanere ferma
     */
    public void SetDirection(int d){
        this.direction = d;
    }
    
    /**
     * Metodo per impostare lo stato della partita
     * @param s 0 per una nuova partita, 1 per giocare, 2 per il "game over", 
     * dal 3 al 15 per la pausa temporanea
     */
    public void SetState(int s){
        if(s<0||s>15)
            s=0;
        this.state = s;
    }
    
    
    /**
     * Metodo per sparare un proiettile contro gli invasori
     */
    public void Fire(){
        AntiAir aa = (AntiAir) this.EarthFleet.GetShips().get(0);
        for(Projectile p: this.projectiles)
        if(p.GetDirection()<0)
            return;
        Projectile p = new Projectile(
                aa.GetX()+Ship.ShipWidth()/2-1,
                aa.GetY(),
                -1,
                this.GetLevelDamage()
        );
        this.projectiles.add(p);
    }
    
    /**
     * Metodo per ottenere lo stato del gioco
     * @return 0 per una nuova partita, 1 per giocare, 2 per il "game over", 
     * dal 3 al 15 per la pausa temporanea
     */
    public int GetState(){
        return this.state;
    }
    
    /**
     * Metodo per ottenere il punteggio della partita attuale
     * @return punteggio della partita
     */
    public int GetScore(){
        return this.score;
    }
    
    
    /**
     * Metodo per ottenere il punteggio più alto raggiunto in tutte le patite
     * @return punteggio massimo ottenuto
     */
    public int GetHiScore(){
        return this.hiscore;
    }
    
    
    /**
     * Metodo per ottenere il livello attuale
     * @return livello attuale
     */
    public int GetLevel(){
        return this.level;
    }
    
    
    /**
     * Metodo per ottenere il numero di vite rimanenti al giocatore
     * @return numero di vite rimanenti
     */
    public int GetLife(){
        return this.life;
    }
    
    
    /**
     * Metodo per ottenere una copia delle navi dalle flotte
     * @return ArrayList di navi
     * @see Fleet
     */
    public ArrayList<Ship> GetShips(){
        ArrayList<Ship> r = this.InvadersFleet.GetShips();
        r.add(this.EarthFleet.GetShips().get(0));
        return r;
    }
    
    
    /**
     * Metodo per ottenere una copia dei proiettili della partita
     * @return ArrayList di proiettili
     * @see Projectile
     */
    public ArrayList<Projectile> GetProjectiles(){
        ArrayList<Projectile> r = new ArrayList<Projectile>();
        for(Projectile p : this.projectiles)
            r.add(p.Clone());
        return r;
    }
    
    
    /**
     * Metodo di classe per ottenere la larghezza del campo di battaglia
     * @return larghezza del campo in pixel
     */
    public static int FieldWidth(){
        return (int)(20*Ship.ShipWidth());
    }
    
    
    /**
     * Metodo di classe per ottenere l' altezza del campo di battaglia
     * @return altezza del campo in pixel
     */
    public static int FieldHeight(){
        return (int)(18*Ship.ShipHeight())+10;
    }
    
    
    /**
     * Metodo privato per resettare il campo di battaglia
     */
    private void ResetField(){
        this.InvadersFleet = new Fleet(22,22,11,true);
        this.EarthFleet = new Fleet(0,0,0,false);
        this.projectiles.clear();
        this.direction=0;
    }
    
    
    /**
     * Metodo privato per iniziare una nuova partita
     */
    private void NewGame(){
        this.life=3;
        this.time=0;
        this.level=0;
        this.state=3;
        this.score=0;
        this.ResetField();
    }
    
    
    /**
     * Metodo privato per ottenere da infliggere alle navi nemiche al livello corrente
     * @return valore del danno
     */
    private int GetLevelDamage(){
        if(this.level>6)
            return this.level_data[6][0];
        else
            return this.level_data[this.level][0];
    }
    
    
    /**
     * Metodo privato per ottenere la probabilità che le navi degli invasori 
     * sparino un proiettile
     * @return probabilità in percentuale: 0 nessun priettile sparato, 100 tutte
     * le navi degli invasori sparano
     */
    private int GetLevelFireProb(){
        if(this.level>6)
            return this.level_data[6][1];
        else
            return this.level_data[this.level][1];
    }
    
    
    /**
     * Metodo privato per aggiornare il punteggio più alto e in caso salvarlo su
     * file
     */
    private void UpdateHiScore(){
        if(this.score>this.hiscore){
            this.hiscore=this.score;
            PrintWriter file;
            try {
                file = new PrintWriter("gamedata.txt");
                file.println(this.score);
                file.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
