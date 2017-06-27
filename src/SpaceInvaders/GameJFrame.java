/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author byte-predator
 */
public class GameJFrame extends javax.swing.JFrame {

    /**
     * Creates new form GameJFrame
     */
    public GameJFrame() {
        initComponents();
        this.game = new Game();
        this.lastTime = System.currentTimeMillis();
        this.JObjects = new ArrayList<javax.swing.JLabel>();
        java.awt.Container p=this.getContentPane();
        p.setBackground(Color.black);
        this.jPanel1.setBackground(Color.black);
        this.setBounds(0, 0, Game.FieldWidth(), Game.FieldHeight()+80);
        this.jPanel1.setSize(Game.FieldWidth(), Game.FieldHeight());
        this.jPanel1.setPreferredSize(new java.awt.Dimension(Game.FieldWidth(), Game.FieldHeight()));
        javax.swing.JFrame self = this; 
        this.timer = new javax.swing.Timer(10, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(game.GetState()==2){
                    timer.stop();
                    String score = "";
                    if(game.GetScore()==game.GetHiScore())
                        score = "New Hi-Score!!!\n";
                    int n = JOptionPane.showOptionDialog(self,
                        score+"Do you want to play again?",
                        "",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //do not use a custom Icon
                        new Object[]{"Ok","Quit"},  //the titles of buttons
                        "Ok"); //default button title
                    if(n==0){
                        game.SetState(0);
                        timer.start();  
                    }else
                        self.dispose();
                }
                long time = System.currentTimeMillis();
                game.Update(time-lastTime);
                lastTime=time;
                
                ArrayList<Ship> temp = game.GetShips();
                ArrayList<Projectile> temp2 = game.GetProjectiles();
                int i=0;
                while(i<temp.size()&&i<JObjects.size()){
                    javax.swing.JLabel l = JObjects.get(i);
                    if(l.getIcon()==null||!l.getIcon().toString().equals(temp.get(i).GetImage())){
                        javax.swing.ImageIcon icon = new javax.swing.ImageIcon("src/SpaceInvaders/"+temp.get(i).GetImage());
                        l.setIcon(icon);
                        l.setOpaque(false);
                    }
                    l.setBounds(temp.get(i).GetX(), temp.get(i).GetY(), Ship.ShipWidth(), Ship.ShipHeight());
                    i++;
                }
                while(i<temp.size()){
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon("src/SpaceInvaders/"+temp.get(i).GetImage());
                    javax.swing.JLabel s = new javax.swing.JLabel(icon);
                    s.setBounds(temp.get(i).GetX(), temp.get(i).GetY(), Ship.ShipWidth(), Ship.ShipHeight());
                    JObjects.add(s);
                    jPanel1.add(s);
                    jPanel1.revalidate();
                    jPanel1.repaint();
                    i++;
                }
                while(i<temp.size()+temp2.size()&&i<JObjects.size()){
                    javax.swing.JLabel l = JObjects.get(i);
                    Projectile e = temp2.get(i-temp.size());
                    l.setIcon(null);
                    l.setBackground(Color.white);
                    l.setOpaque(true);
                    l.setBounds(e.GetX(), e.GetY(), 2,4);
                    i++;
                }
                while(i<temp.size()+temp2.size()){
                    Projectile e = temp2.get(i-temp.size());
                    javax.swing.JLabel l = new javax.swing.JLabel();
                    l.setIcon(null);
                    l.setBackground(Color.white);
                    l.setOpaque(true);
                    l.setBounds(e.GetX(), e.GetY(),2,4);
                    JObjects.add(l);
                    jPanel1.add(l);
                    jPanel1.revalidate();
                    jPanel1.repaint();
                    i++;
                }
                while(i<JObjects.size()){
                    jPanel1.remove(JObjects.get(i));
                    JObjects.remove(i);
                    jPanel1.revalidate();
                    jPanel1.repaint();
                }
                if(game.GetLife()==2){
                    jLabel7.setVisible(false);
                }else if(game.GetLife()==1){
                    jLabel6.setVisible(false);
                }else if(game.GetLife()==0){
                    jLabel5.setVisible(false);
                }else{
                    jLabel5.setVisible(true);
                    jLabel6.setVisible(true);
                    jLabel7.setVisible(true);
                }
                jLabel2.setText(String.valueOf(game.GetScore()));
                jLabel4.setText(String.valueOf(game.GetHiScore()));
                jLabel9.setText(String.valueOf(game.GetLevel()+1));
            }
        });
        this.timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(400, 300));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("FreeSans", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SCORE");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("FreeSans", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("0");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("FreeSans", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("HI-SCORE");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("FreeSans", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("0");

        jPanel1.setLayout(null);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SpaceInvaders/img/aa.png"))); // NOI18N

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SpaceInvaders/img/aa.png"))); // NOI18N

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SpaceInvaders/img/aa.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("FreeSans", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LEVEL");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("FreeSans", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 283, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(8, 8, 8))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_LEFT:
                this.game.SetDirection(-1);
                break;
            case java.awt.event.KeyEvent.VK_RIGHT:
                this.game.SetDirection(1);
                break;
            case java.awt.event.KeyEvent.VK_SPACE:
                this.game.Fire();
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_LEFT || evt.getKeyCode()==java.awt.event.KeyEvent.VK_RIGHT)
            this.game.SetDirection(0);
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameJFrame().setVisible(true);
            }
        });
    }
    
    private Game game;
    private Timer timer;
    private long lastTime;
    private ArrayList<javax.swing.JLabel> JObjects;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
