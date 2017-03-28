////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
/////////////                                                    ///////////////
/////////////                                                    ///////////////
/////////////  Manuel Moya Ferrer --- Jose Manuel García Giménez ///////////////
/////////////                                                    ///////////////
/////////////          Práctica 8 -- COMECOCOS                   ///////////////   
/////////////                                                    ///////////////
/////////////          3ºTelecomunicaciones UGR                  ///////////////
/////////////         Especialidad en Telemática                 ///////////////
/////////////                                                    ///////////////
/////////////                                                    ///////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


package data;

import javax.swing.JPanel;
import guiComecocos.ComecocosFrame;
import data.*;
import static data.Comecocos.ABAJO;
import static data.Comecocos.ARRIBA;
import static data.Comecocos.DERECHA;
import static data.Comecocos.IZQUIERDA;
import javax.swing.JOptionPane;

/**
 * Esta clase implementa una hebra que hace que se mueva continuamente la Figura actual
 * . 
 */
public class Mueve implements Runnable{
    private int delay;
    private boolean continuar=true;
    private boolean suspendFlag=true;
    private ComecocosFrame frame;
    private Comecocos comecocos=new Comecocos(0, 0,1,1);
    private Rejilla rejilla;
    
    //Lo utilizamos para determinar la direccion en la que se mueve el comecocos
  
    static public int direccion;
    static public int[] fDir=new int[4];

       
    /**
     * Constructor de la clase, establece el retardo en milisegundos
     * entre movimiento y movimiento de la Figura actual, y comienza a ejecutar
     * la hebra. 
     */
    public Mueve(ComecocosFrame fr,int nivel){
        frame=fr;
        delay= actualizaRetardo(nivel);
        direccion=DERECHA;
        for(int i=0;i<4;i++){
            fDir[i]=ARRIBA;
        }
        
        Thread t=new Thread(this);
        t.start();
    }
    
    
    @Override
    public void run(){
        try{
            while(continuar){
                synchronized(this){
                    while(suspendFlag){
                       wait();
                   }
                    }
                    Thread.sleep(delay);
                    
                    guiComecocos.RejillaPanel.comecocos.mueve(direccion,fDir);
                    
                    for(int i=0;i<4;i++){
                           if(guiComecocos.RejillaPanel.fantasma[i].getEstado()==false){
                                fDir[i]=guiComecocos.RejillaPanel.fantasma[i].mueveFantasma(fDir[i]); 
                           }
                           if(guiComecocos.RejillaPanel.fantasma[i].getEstado()==true && guiComecocos.RejillaPanel.contador%3==0){    
                                fDir[i]=guiComecocos.RejillaPanel.fantasma[i].mueveFantasma(fDir[i]);     
                           }                     
                    }

                if(frame.getPanel()!=null)
                    frame.getPanel().repaint();

            }
        } catch(InterruptedException e){
            System.out.println("Hilo interrumpido");
        }
    }
    
    /**
     * Detiene momentaneamente la ejecución de la hebra, haciendo que la Figura actual
     * quede parada.
     */
    synchronized public void suspender(){
        frame.getPanel().repaint();
        suspendFlag=true;
    }
    
    /**
     * Reanuda el movimiento de la hebra, la Figura actual vuelve  a moverse.
     */
    public synchronized void reanudar(){
        if(frame.getPanel()!=null)
            frame.getPanel().repaint();
        suspendFlag = false;
        notify();
    }
    
    /**
     * Termina la ejecución de la hebra.
     */
    public void parar(){
        continuar=false;
    }
    
    
    /**
     * Nos dice si la hebra está o no parada.
     * @return true si la hebra de movimiento está parada, false en otro caso
     */
    synchronized public boolean getParado(){
        return suspendFlag;
    }
    
    /**
     * La siguiente función actualiza el retardo que espera la hebra
     * para mover la Figura actual. El nivel más lento será
     * el 0 (retardo 700) y el más rápido el 10 (retardo 50)
     */
    private int actualizaRetardo(int nivel) {
        if (nivel>10) nivel=10;
        else if (nivel<0) nivel=0;
        return ( 400-(nivel*35) );
    }
}
 