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
 * Game Class.<br>
 * The game class represents the controller of the program, it sets up the filed,
 * manages the game difficulty, it saves the current score and the hi-score,
 * it acquires user's commands, edits fleet's states and gives to the interface 
 * informations to be showed to the player
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
     * Creates a new Game
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
     * method to call in order to update the state of the game
     * @param delta time in milliseconds from the last call
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
     * Method to move the defence fleet
     * @param d -1 to move left the fleet, 1 to move right the fleet, 0 to hold
     * the position
     */
    public void SetDirection(int d){
        this.direction = d;
    }
    
    
    /**
     * Method to set the state of the game
     * @param s 0 for a new game, 1 for play, 2 for game-over, from 3 to 15 for
     * a temporary pause
     */
    public void SetState(int s){
        this.state = s;
    }
    
    
    /**
     * Method for shoot a projectile to the invaders
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
     * Method to get the game state
     * @return 0 for a new game, 1 for play, 2 for game-over, from 3 to 15 for a
     * temporary pause
     */
    public int GetState(){
        return this.state;
    }
    
    
    /**
     * Method to get the current score
     * @return current score
     */
    public int GetScore(){
        return this.score;
    }
    
    
    /**
     * metoh to get the highest score
     * @return higest score
     */
    public int GetHiScore(){
        return this.hiscore;
    }
    
    
    /**
     * Mthod to get the current level
     * @return current level
     */
    public int GetLevel(){
        return this.level;
    }
    
    
    /**
     * Methos to get the number of lives left to the player
     * @return number of lives left
     */
    public int GetLife(){
        return this.life;
    }
    
    
    /**
     * Methos to get a copy of the flet's ships
     * @return ArrayList of ship
     * @see Fleet
     */
    public ArrayList<Ship> GetShips(){
        ArrayList<Ship> r = this.InvadersFleet.GetShips();
        r.add(this.EarthFleet.GetShips().get(0));
        return r;
    }
    
    
    /**
     * Methos to get a copy of the projectiles in the game
     * @return ArayList of projectile
     * @see Projectile
     */
    public ArrayList<Projectile> GetProjectiles(){
        ArrayList<Projectile> r = new ArrayList<Projectile>();
        for(Projectile p : this.projectiles)
            r.add(p.Clone());
        return r;
    }
    
    
    /**
     * Class method to get the field width
     * @return field width in pixels
     */
    public static int FieldWidth(){
        return (int)(20*Ship.ShipWidth());
    }
    
    
    /**
     * Class method to get the field height
     * @return field height in pixels
     */
    public static int FieldHeight(){
        return (int)(18*Ship.ShipHeight())+10;
    }
    
    
    /**
     * Private method to reset the field
     */
    private void ResetField(){
        this.InvadersFleet = new Fleet(22,22,11,true);
        this.EarthFleet = new Fleet(0,0,0,false);
        this.projectiles.clear();
        this.direction=0;
    }
    
    
    /**
     * Private method to start a new game
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
     * Private method to get the damage to apply to enemy ships at the current level
     * @return damage ammount
     */
    private int GetLevelDamage(){
        if(this.level>6)
            return this.level_data[6][0];
        else
            return this.level_data[this.level][0];
    }
    
    
    /**
     * Private method to get the probability that the invader's ships have to fire
     * @return probability in percentage: o none fire, 100 all invader's ships fire
     */
    private int GetLevelFireProb(){
        if(this.level>6)
            return this.level_data[6][1];
        else
            return this.level_data[this.level][1];
    }
    
    
    /**
     * Private method to update the hi-score and in case save it on file
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
