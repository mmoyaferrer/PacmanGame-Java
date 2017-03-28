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

import java.awt.Color;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Esta clase nos creará un objeto comecocos ó fantasma, definiendo sus vidas en
 * caso de que sea un comecocos, sus diferentes parámetros, etc.
 * @author manuel
 */
public class Comecocos extends Elemento {  //Extiende de elemento por que he pensado que era solo de una casilla
   
    Rejilla rejilla=new Rejilla(28, 31);
    public static final int IZQUIERDA         = 0;
    public static final int DERECHA           = 1;
    public static final int ABAJO             = 2;
    public static final int ARRIBA            = 3;
    
    private double instanteInicioSuspension;
    public boolean asustado=false;
    double distancias[]=new double[4];
    int vidas; 
    private int x;
    private int y;
    public boolean vidaFantasma=true;
    java.util.Date inicioSuspensionFantasma;
    
    
    /**
     * Inicializa nuestro objeto comecocos, que aparecerá en la posicion x, y
     * que introducimos, en el mapa de Rejilla.
     * @param f
     * @param c
     * @param x
     * @param y 
     */
    public Comecocos(int f, int c,int x, int y) {
        super(0, 0);
        this.x=x;
        this.y=y;
        vidas=3;
        asustado=false;
    }
    
    /**
     * Reinicia la posicion del objeto.
     * @param x
     * @param y 
     */
    public void reiniciar(int x,int y){
        this.x=x;
        this.y=y;
    }
    
   /**
     * Obtiene la posición x respecto al origen de coordenadas de la Rejilla de 
     * la Figura actual.
     * @return in Posición x.
     */
    public int getX(){
        return x;
    }
    
    /**
     * Pone al objeto en estado asustado, se usa para los fantasmas, cuando 
     * el comecocos se come un punto grande, estos entran en estado asustado.
     * 
     * @param bol 
     */
    public void setEstadoAsustado(boolean bol){
        asustado=bol;
    }
    
    /**
     * Obtiene el estado del objeto, se usa para saber el estado del fantasma,
     * saber si esta asustado y por tanto puede ser comido por el comecocos, o
     * a la inversa.
     * @return boolean asustado 
     */
    public boolean getEstado(){
        return asustado;
    }
    
    
    /**
     * Se usa para los fantasmas, cuando estos están en estado "asustado" y son
     * comidos, su vida pasa a false, hasta que salgan del estado de suspensión
     * en el que entran.
     */
    public void MuereFantasma(){
        vidaFantasma=false;
    }
   
    
    /**
     * Obtiene la posición y respecto al origen de coordenadas de la Rejilla de 
     * la Figura actual
     * @return la posición y respecto al origen de coordenadas de la Rejilla de 
     * la Figura actual
     */
    public int getY(){
        return y;
    }
  

    /**
     * Mueve la Figura actual una casilla en la dirección indicado por dirección 
     * (ABAJO,IZQUIERDA o ARRIBA)
     * @param direccion la dirección de movimiento (ABAJO,IZQUIERDA o ARRIBA)
     */
    public void mueve(int direccion,int fDir[]){
        
        guiComecocos.RejillaPanel.frame.EstablecerVidasEnTextArea(vidas);
        
        guiComecocos.RejillaPanel.siguienteObjeto(direccion);
        
       
        
        if(rejilla.chocaFantasma(guiComecocos.RejillaPanel.fantasma, this,direccion,fDir)==true){
            
           for(int i=0;i<guiComecocos.RejillaPanel.fantasma.length;i++){
               
               if(guiComecocos.RejillaPanel.fantasma[i].asustado==false){
            
            

                vidas--;

                if(vidas>0){
                    JOptionPane.showMessageDialog(null, "Te quedan "+vidas+" vidas");
                 try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Comecocos.class.getName()).log(Level.SEVERE, null, ex);
                }
                    guiComecocos.RejillaPanel.comecocos.reiniciar(1, 1);

                    guiComecocos.RejillaPanel.fantasma[0].reiniciar(11, 15);
                    guiComecocos.RejillaPanel.fantasma[1].reiniciar(13, 15);
                    guiComecocos.RejillaPanel.fantasma[2].reiniciar(15, 15);
                    guiComecocos.RejillaPanel.fantasma[3].reiniciar(17, 15);

                }

                if(vidas==0){
                JOptionPane.showMessageDialog(null, "Has perdido");

                    
                    
                    int volverJugar=JOptionPane.showConfirmDialog(new JFrame(),"Jugar de nuevo?", "GAME OVER", JOptionPane.YES_NO_OPTION);

                    if(volverJugar==0)
                        try {        
                            guiComecocos.RejillaPanel.frame.inicializaJuego();
                } catch (InterruptedException ex) {
                            System.out.println("No se pudo reiniciar el juego");
                }        
                    else
                        System.exit(0);
                    
                }
                
                i=guiComecocos.RejillaPanel.fantasma.length; //Si el comecocos muere, solo se ejecuta una vez, ponemos el for puesto que si ponemos uno fijo, si posteriormente se elimina, no podemos comprobar su estado
               }
           }
        }
       
        if(direccion==ABAJO && rejilla.seChoca(this, direccion)==false){
            y++;            
        }else if(direccion==IZQUIERDA && rejilla.seChoca(this, direccion)==false){
            x--;
        }else if(direccion==DERECHA && rejilla.seChoca(this, direccion)==false){
            x++;
        }
        else if(direccion==ARRIBA && rejilla.seChoca(this, direccion)==false){
            y--;
        }
        
        
    }
    
    /**
     * Suspende un fantasma durante un tiempo, esto ocurre cuando están en 
     * estado "asustado" y posteriormente son comidos por el comecocos.
     */
    public void suspendeFantasma(){
       inicioSuspensionFantasma=new java.util.Date();
       instanteInicioSuspension=inicioSuspensionFantasma.getTime();
       this.vidaFantasma=false;   
    }
    
    /**
     * Devuelve a un fantasma al estado vivo, posteriormente a que este esté
     * en estado muerto (vidaFantasma=false).
     */
    public void reviveFantasma(){
        int aux=0;
        reiniciar(12+aux,15);
        this.vidaFantasma=true;
        aux++;
        if(aux==5)
            aux=0;
        
    }
    
    /**
     * Mueve al objeto fanstasma en una dirección semi-aleatoria en dirección
     * hacia el comecocos, si el fantasma está asustado, esto ocurre a la 
     * inversa.
     * @param direccionActual
     * @return int posible direccion
     */
    public int mueveFantasma(int direccionActual){
        
       
       if(inicioSuspensionFantasma!=null && ((new java.util.Date().getTime()-instanteInicioSuspension)>5000 && vidaFantasma==false)){  //Cuenta un poco de tiempo hasta que aparezcan de nuevo
          this.reviveFantasma();
       }
        
        
        int posibleDireccion=direccionActual;
        int direcciones[]={IZQUIERDA,DERECHA,ABAJO,ARRIBA};
        int n=1;
            
            
            distancias[ABAJO]=Math.sqrt((x-guiComecocos.RejillaPanel.comecocos.x)*(x-guiComecocos.RejillaPanel.comecocos.x)+(y+1-guiComecocos.RejillaPanel.comecocos.y)*(y+1-guiComecocos.RejillaPanel.comecocos.y));                          
       
            distancias[IZQUIERDA]=Math.sqrt((x-1-guiComecocos.RejillaPanel.comecocos.x)*(x-1-guiComecocos.RejillaPanel.comecocos.x)+(y-guiComecocos.RejillaPanel.comecocos.y)*(y-guiComecocos.RejillaPanel.comecocos.y)); 
            
            distancias[DERECHA]=Math.sqrt((x+1-guiComecocos.RejillaPanel.comecocos.x)*(x+1-guiComecocos.RejillaPanel.comecocos.x)+(y-guiComecocos.RejillaPanel.comecocos.y)*(y-guiComecocos.RejillaPanel.comecocos.y)); 
       
            distancias[ARRIBA]=Math.sqrt((x-guiComecocos.RejillaPanel.comecocos.x)*(x-guiComecocos.RejillaPanel.comecocos.x)+(y-1-guiComecocos.RejillaPanel.comecocos.y)*(y-1-guiComecocos.RejillaPanel.comecocos.y)); 
            
            int []tempdirecciones=direcciones.clone();
            
            ordena(distancias,tempdirecciones);
                
            Random aleatorio = new Random();
            
            if(vidaFantasma==true){
                
            if(!asustado){
            
                posibleDireccion=tempdirecciones[aleatorio.nextInt(2)];

                while (posibleDireccion == Contrario(direccionActual) || rejilla.seChoca(this, posibleDireccion)){

                    posibleDireccion=tempdirecciones[aleatorio.nextInt(n)];
                    if(n<=3){
                        n++;
                    }
                    else {
                        n=3;
                    }
                }

            }
            else if(asustado){
                int l=3;
                posibleDireccion=tempdirecciones[l];
                while (rejilla.seChoca(this, posibleDireccion)){
                    posibleDireccion=tempdirecciones[--l];
                    
                }
            }
            
            
            if(posibleDireccion==ABAJO){
                   y++;                        
               }
               else if (posibleDireccion==IZQUIERDA){
                   x--;
               }
               else if (posibleDireccion==DERECHA){
                   x++;
               }
               else if (direccionActual==ARRIBA){
                   y--;
               }
            }
               return posibleDireccion;
        
    }   
    
    /**
     * A partir de un vector de 4 posiciones y un vector con 4 direcciones, 
     * ordena el vector de las direcciones en función de las distancias. 
     * Usado en la clase mueve fantasma.
     * @param array1
     * @param array2 
     */
    static void ordena(double [] array1,int[] array2){
        for(int i=0 ; i<array1.length-1;i++){
            for(int j=0;j<array1.length-i-1;j++){
                if(array1[j]>(array1[j+1])){
                   double tempdouble= array1[j+1];
                   int tempint=array2[j+1];
                   array1[j+1]=array1[j];
                   array2[j+1]=array2[j];
                   array1[j]=tempdouble;
                   array2[j]=tempint;
                }                    
            }            
        }        
    }
    
    /**
     * Devuelve la dirección contraria a una dada.
     * @param direc
     * @return int DireccionContraria
     */
    private int Contrario(int direc){
        if(direc==ARRIBA){
             return ABAJO;
        }
        else if(direc==ABAJO){
             return ARRIBA;
        }
        else if(direc==IZQUIERDA){
             return DERECHA;
        }
        else {
             return IZQUIERDA;
        }
                        
        
    }
    
    /**
     * Devuelve la figura Comecocos.
     * @return Objeto Comecocos
     */
    public  Comecocos figuraComecocos(){
        return this;
    } 
    
    
    
}