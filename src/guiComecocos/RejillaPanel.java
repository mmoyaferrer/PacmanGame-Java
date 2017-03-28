/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiComecocos;

import data.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Ventana en la que se ve gráficamente el juego.
 * 
 */
public class RejillaPanel extends javax.swing.JPanel {

    public static ComecocosFrame frame;
    private int anchoCelda=-1;
    public static Comecocos comecocos;
    public static Comecocos[] fantasma = new Comecocos[4]; 
 
    static Rejilla rejilla;
    
    public  static int contador; // contador de tiempo para que los fantasmas dejen de estar asustados
 
    
    public static int Puntos;
    private int angulo=45;
    
   
    
    /**
     * Inicia Rejilla panel, con las posiciones de los fantasmas
     */
    public RejillaPanel() {
        
        comecocos=new Comecocos(0, 0,2,1);
        fantasma[0]=new Comecocos(0, 0, 12, 15);
        fantasma[1]= new Comecocos(0, 0, 13, 15);
        fantasma[2]= new Comecocos(0, 0, 14, 15);
        fantasma[3]= new Comecocos(0, 0, 15, 15);
        initComponents();
       
    }
    
    /**
     * Recibe el objeto fr de comecocos, para asignárselo a frame, objeto que
     * hemos creado en ésta clase, y posteriormente poder usar los métodos de 
     * Comecocos Frame.
     * @param fr 
     */
    public RejillaPanel(ComecocosFrame fr){
        this();
        frame=fr;
    }
    
    /**
     * Método para reiniciar la puntuación actual
     */
    public void reiniciarPuntos(){
        Puntos=0;
    }
    
    /**
     * Metodo para establecer el tiempo durante el que los fantasmas están 
     * asustados.
     * 
     * @param con 
     */
    public void setContador(int con){
        contador=con;
    }
    

    /**
     * Metodo para dibujar el Mapa (rejilla) al inicio,o reinicio del juego.
     * @param g 
     */
    public void dibujaRejilla(java.awt.Graphics g){
        int y,x;  //Eje x y eje Y
        rejilla=frame.getRejilla();
        int xoffset=(getWidth()-rejilla.getAnchura()*anchoCelda)/2;
        
      for(x=0;x<rejilla.getAnchura();x++){
         for(y=0;y<rejilla.getAltura();y++){
            
                
                    if(rejilla.getTipoCelda(y,x) == Rejilla.BLOQUE){
                        g.setColor(Color.BLUE);
                        g.fillRect(xoffset+x*anchoCelda,y*anchoCelda,anchoCelda,anchoCelda);
                    }
                    
                    if(rejilla.getTipoCelda(y,x) == Rejilla.PuntoPequeño){
                        g.setColor(Color.BLACK);
                        g.fillRect(xoffset+x*anchoCelda,y*anchoCelda,anchoCelda,anchoCelda);
                        g.setColor(Color.YELLOW);
                        g.fillOval(xoffset+x*anchoCelda+anchoCelda/2,y*anchoCelda+anchoCelda/2,anchoCelda/5,anchoCelda/5);

                    }
                    
                    if(rejilla.getTipoCelda(y,x) == Rejilla.PuntoGrande){
                        g.setColor(Color.BLACK);
                        g.fillRect(xoffset+x*anchoCelda,y*anchoCelda,anchoCelda,anchoCelda);
                        g.setColor(Color.YELLOW);
                        g.fillOval(xoffset+x*anchoCelda+anchoCelda/2-anchoCelda/4,y*anchoCelda+anchoCelda/2-anchoCelda/4,anchoCelda/2,anchoCelda/2);
                    }

                    
                    if(rejilla.getTipoCelda(y,x) == Rejilla.BloqueSalida){
                        g.setColor(Color.BLACK);
                        g.fillRect(xoffset+x*anchoCelda,y*anchoCelda,anchoCelda,anchoCelda);
                        g.setColor(Color.YELLOW);
                        g.fillRect(xoffset+x*anchoCelda,y*anchoCelda+3*anchoCelda/4,anchoCelda,anchoCelda/4);


                    }
                    if(rejilla.getTipoCelda(y, x)==rejilla.VACIO){
                        g.setColor(Color.BLACK);
                        g.fillRect(xoffset+x*anchoCelda,y*anchoCelda,anchoCelda,anchoCelda);
                    }
                }
            
        }
    
    }
    
    /**
     * Método para saber cual es el siguiente objeto al comecocos en función de 
     * la dirección que este lleve, así mismo, si es un punto, este desaparecerá,
     * si es un punto grande, los fantasmas estarán en estado asustado, donde 
     * podrán ser comidos.
     * 
     * @param direccion 
     */
    public static void siguienteObjeto(int direccion){
        rejilla=frame.getRejilla();
       //lleva el tiempo que los fantasmas están asustados 
        if(contador!=0 ){
            contador--;
        }
        if(contador==0){
            for(int i=0;i<4;i++){
                fantasma[i].setEstadoAsustado(false);
            }
        }
        
       
              
        // aqui se hace que se coman los puntos como antes        
        if(rejilla.getTipoCelda(comecocos.getY(),comecocos.getX())==Rejilla.PuntoPequeño){
                Puntos=Puntos+10;
                rejilla.assignTipoCelda(comecocos.getY(), comecocos.getX(), Rejilla.VACIO);  
        }
        if(rejilla.getTipoCelda(comecocos.getY(),comecocos.getX())==Rejilla.PuntoGrande){
                Puntos=Puntos+50;
                for(int i=0;i<4;i++){
                    fantasma[i].setEstadoAsustado(true);
                    contador=35;
                    
                }           
                                    
                rejilla.assignTipoCelda(comecocos.getY(), comecocos.getX(), Rejilla.VACIO);  
                                
        }
        frame.añadirPuntos(Puntos);
           
    }
    

    /**
     * Dibuja el comecocos, así mismo hace que la boca se abra y se cierre, 
     * y que el dibujo se rote en función de la dirección de éste.
     * @param g 
     */
    void dibujaComecocos(java.awt.Graphics g){      
      
        Rejilla rejilla=frame.getRejilla();
        
        int xoffset=(getWidth()-rejilla.getAnchura()*anchoCelda)/2+(comecocos.getX())*anchoCelda;
        int yoffset=comecocos.getY()*anchoCelda;
             
         if(frame.mueve.direccion==comecocos.DERECHA){ 
            
            g.setColor(Color.ORANGE);   //COMECOCOS
            g.fillArc(xoffset+comecocos.getColumna()*anchoCelda, yoffset+comecocos.getFila()*anchoCelda, anchoCelda, anchoCelda, angulo, angulo+250);

            g.setColor(Color.BLACK);       //OJO
            g.fillOval(xoffset+comecocos.getColumna()*anchoCelda+anchoCelda/2, yoffset+comecocos.getFila()*anchoCelda+anchoCelda/4, 5, 5);
        
            if(angulo==45){
                angulo=360;
            }else{
                angulo=45;
            }
        }
        
        if(frame.mueve.direccion==comecocos.IZQUIERDA){ 
            
            g.setColor(Color.ORANGE);   //COMECOCOS
            g.fillArc(xoffset+comecocos.getColumna()*anchoCelda, yoffset+comecocos.getFila()*anchoCelda, anchoCelda, anchoCelda, angulo-180, angulo+250);

            g.setColor(Color.BLACK);       //OJO
            g.fillOval(xoffset+comecocos.getColumna()*anchoCelda+anchoCelda/2, yoffset+comecocos.getFila()*anchoCelda+anchoCelda/4, 5, 5);
        
            if(angulo==45){
                angulo=360;
            }else{
                angulo=45;
            }
        }
        if(frame.mueve.direccion==comecocos.ARRIBA){ 
            
            g.setColor(Color.ORANGE);   //COMECOCOS
            g.fillArc(xoffset+comecocos.getColumna()*anchoCelda, yoffset+comecocos.getFila()*anchoCelda, anchoCelda, anchoCelda, angulo-280, angulo+250);

            g.setColor(Color.BLACK);       //OJO
            g.fillOval(xoffset+comecocos.getColumna()*anchoCelda+anchoCelda/2, yoffset+comecocos.getFila()*anchoCelda+anchoCelda/2, 5, 5);

            if(angulo==45){
                angulo=360;
            }else{
                angulo=45;
            }
        }
        if(frame.mueve.direccion==comecocos.ABAJO){ 
            
            g.setColor(Color.ORANGE);   //COMECOCOS
            g.fillArc(xoffset+comecocos.getColumna()*anchoCelda, yoffset+comecocos.getFila()*anchoCelda, anchoCelda, anchoCelda, angulo+255, angulo+250);

            g.setColor(Color.BLACK);       //OJO
            g.fillOval(xoffset+comecocos.getColumna()*anchoCelda+anchoCelda/2, yoffset+comecocos.getFila()*anchoCelda+anchoCelda/4, 5, 5);

            if(angulo==45){
                angulo=360;
            }else{
                angulo=45;
            }
        }
        
    }
    
    
    /**
     * Dibujar un fantasma, y lo colorea del color que se le indique en los
     * parametros de entrada al método.
     * 
     * @param fant
     * @param g
     * @param color 
     */
    void dibujaFantasma(Comecocos fant, java.awt.Graphics g, java.awt.Color color){      

    Rejilla rejilla=frame.getRejilla();
      int xoffset=(getWidth()-rejilla.getAnchura()*anchoCelda)/2+(fant.getX())*anchoCelda;
      int yoffset=fant.getY()*anchoCelda;
      int ox =xoffset+(int)(fant.getColumna()*anchoCelda),
        oy = yoffset+(int)(fant.getFila()*anchoCelda),
            c = anchoCelda,
            c12 = c/2,
            c15 = c/5,
              
            c35 = 3*c/5,
            c45 = 4*c/5,
            c16 = c/6,
            c17=c/7;
      // parte de arriba
      if(fant.getEstado()){
        g.setColor(color.GRAY);
      }
      else{
          g.setColor(color);
      }
        g.fillArc(ox, oy, anchoCelda, anchoCelda, 0, 180);
        
      // parte de abajo
        
       int[] xs = {ox, ox+c, ox+c, ox+5*c16, ox+4*c16, ox+3*c16, ox+2*c16, ox+c16, ox};
       int[] ys = {oy+c12, oy+c12, oy+c, oy+c45, oy+c, oy+c45, oy+c, oy+c45, oy+c};
            g.fillPolygon(xs, ys, 9);
      // Eyes
        g.setColor(color.WHITE);
        g.fillOval(ox+c15, oy+c15, c15, c15);
        g.fillOval(ox+c35, oy+c15, c15, c15);
        
        g.setColor(Color.BLACK);
        g.fillOval(ox+c15, oy+c15, c17, c17);
        g.fillOval(ox+c35, oy+c15, c17, c17); 
            

}

    
    /**
     * Método sobreescrito, que dibuja los componentes que se le indican(fantasmas
     * comecocos,mapa,...).
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if(anchoCelda==-1){
             anchoCelda=Math.min(getWidth()/frame.getRejilla().getAnchura(),(getHeight()-10)/frame.getRejilla().getAltura());
        }
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        dibujaRejilla(g);
        dibujaComecocos(g);
        
        if(fantasma[0].vidaFantasma==true){
        dibujaFantasma(fantasma[0],g,Color.CYAN);
        }
        if(fantasma[1].vidaFantasma==true){
        dibujaFantasma(fantasma[1], g, Color.PINK);
        }
        if(fantasma[2].vidaFantasma==true){
        dibujaFantasma(fantasma[2], g, Color.GREEN);
        }
        if(fantasma[3].vidaFantasma==true){
        dibujaFantasma(fantasma[3], g, Color.magenta);
        }
    }
    
           

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(1000, 1000));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RejillaPanel.this.mouseEntered(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RejillaPanel.this.keyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Capturador de teclas que se pulsan.
     * @param evt 
     */
    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode() == KeyEvent.VK_LEFT){
           
            ComecocosFrame.mueve.direccion=comecocos.IZQUIERDA;
            
            
            if(frame.getPanel()!=null){
            frame.getPanel().repaint();
            }
        }  
        else if (evt.getKeyCode() == KeyEvent.VK_RIGHT){
            ComecocosFrame.mueve.direccion=comecocos.DERECHA;
            if(frame.getPanel()!=null){
            frame.getPanel().repaint();
            }
        }
        else if (evt.getKeyCode() == KeyEvent.VK_UP){
           ComecocosFrame.mueve.direccion=comecocos.ARRIBA;
            if(frame.getPanel()!=null){
            frame.getPanel().repaint();
            }
        }
        else if (evt.getKeyCode() == KeyEvent.VK_DOWN){
           ComecocosFrame.mueve.direccion=comecocos.ABAJO;
            if(frame.getPanel()!=null){
            frame.getPanel().repaint();
            }
        }
        
    }//GEN-LAST:event_keyPressed

     /**
     * Capturador del foco del raton (último sitio en hacer click)
     * @param evt 
     */    
    private void mouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseEntered
        requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_mouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
