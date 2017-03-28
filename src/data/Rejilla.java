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


/**
 * Esta clase representa una rejilla con una determinada Anchura
 * y Altura, en la que cada celda puede estar VACIA, contener
 * un BLOQUE (muro exterior) o PIEZA (trozo de una pieza)
 */
public class Rejilla{
    public static final int VACIO        = 0;
    public static final int BLOQUE       = 1;
    public static final int BloqueSalida = 2;
    public static final int PuntoPequeño = 3;
    public static final int PuntoGrande  = 4;

    public static boolean modoBonus=false;  /// En el modo bonus, comecocos come a fantasmas
 
    private boolean rejillaInicializada=false;
    
    private int anchura;
    private int altura;
    private int[][] celdas;
    private String Mapa[]={ "BBBBBBBBBBBBBBBBBBBBBBBBBBBB", 
                            "B............BB............B",
                            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
                            "BoBBBB.BBBBB.BB.BBBBB.BBBBoB", 
                            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
                            "B..........................B", 
                            "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
                            "B.BBBB.BB.BBBBBBBB.BB.BBBB.B",
                            "B......BB....BB....BB......B", 
                            "BBBBBB.BBBBB BB BBBBB.BBBBBB",
                            "     B.BBBBB BB BBBBB.B     ",
                            "     B.BB          BB.B     ", 
                            "     B.BB BB____BB BB.B     ",
                            "BBBBBB.BB B      B BB.BBBBBB",
                            "     B.   B      B   .B     ", 
                            "BBBBBB.BB B      B BB.BBBBBB",
                            "     B.BB BBBBBBBB BB.B     ",
                            "     B.BB          BB.B     ", 
                            "     B.BB BBBBBBBB BB.B     ",
                            "BBBBBB.BB BBBBBBBB BB.BBBBBB",
                            "B............BB............B", 
                            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
                            "B.BBBB.BBBBB.BB.BBBBB.BBBB.B",
                            "Bo..BB................BB..oB", 
                            "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
                            "BBB.BB.BB.BBBBBBBB.BB.BB.BBB",
                            "B......BB....BB....BB......B", 
                            "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
                            "B.BBBBBBBBBB.BB.BBBBBBBBBB.B",
                            "B..........................B", 
                            "BBBBBBBBBBBBBBBBBBBBBBBBBBBB",
                            
                            };
   

    /**
     * Crea espacio para una rejilla con anchura igual a w  y altura
     * igual a h.
     * @param w anchura de la nueva Rejilla
     * @param h altura de la nueva Rejilla
     */
    public Rejilla(int w,int h){   //28 31
        anchura=w;
        altura=h;
        //comer=true;
        if(rejillaInicializada==false){
        celdas= new int[altura][anchura];     //31 filas x28 columnas, matriz con la forma que vemos en el String
        initStringRejilla();
        }else{
            
        }
    }
    
    /**
     * Devuelve la anchura de la rejilla.
     * @return la anchura de la rejilla
     */
    public int getAnchura(){
        return anchura;
    }
    
    /**
     * Devuelve la altura de la rejilla.
     * @return la altura de la rejilla
     */
    public int getAltura(){
        return altura;
    }
    
    /**
     * Establece el tipo de celda en las coordenadas x e y de esta Rejilla
     *  @param x coordenada x (columna)
     * @param y coordenada y (fila)
     * @param valor el tipo de celda (VACIA, PIEZA o BLOQUE)
     */
    public void assignTipoCelda(int x,int y,int valor){
        celdas[x][y]= valor ;
    }
    
    /**
     * Obtiene el tipo de celda en las coordenadas x e y de esta Rejilla
     * @param x coordenada x (columna)
     * @param y coordenada y (fila)
     * @return el tipo de Celda en la coordenada x,y.
     */
    
    public int getTipoCelda(int x,int y){
        return celdas[x][y];
    }
    
    /**
     * Establece que forma va a tener cada elemento de la matriz rejilla,
     * si este va a ser un punto, un bloque, un punto grande,...
     */
    public void initStringRejilla(){
        int i,j;        //ancho es 28 y alto es 31
        for(i=0;i<altura;i++){       
            for(j=0;j<anchura;j++){
                if((Mapa[i].charAt(j))=='B'){
                celdas[i][j]=BLOQUE;
                }
                if((Mapa[i].charAt(j))=='.'){
                celdas[i][j]=PuntoPequeño;
                }
                if((Mapa[i].charAt(j))=='o'){
                celdas[i][j]=PuntoGrande;
                }
                if((Mapa[i].charAt(j))==' '){
                celdas[i][j]=VACIO;
                }
                if((Mapa[i].charAt(j))=='_'){
                celdas[i][j]=BloqueSalida;
                }
                
            }
            
       
              
        }
        rejillaInicializada=true;
    }

      /**
     * Indica si al mover la figura una celda según la direccion indicada, se chocará con
     * alguna otra pieza ya colocada, o con los bordes de la rejilla
     * @param com el elemento que queremos comprobar si se chocará
     * @param direccion de movimiento 
     * @return true si se choca, false en caso contrario
     */
     public boolean seChoca(Comecocos com, int direccion){

         
        for(int i=0;i<4;i++){ 
            if(direccion==com.ABAJO){
                if(celdas[com.getY()+1][com.getX()]==BLOQUE   ){
                    return true;
                }            
            }

            else if (direccion==com.IZQUIERDA){
                if(celdas[com.getY()][com.getX()-1]==BLOQUE ){
                    return true;
                }           
            }
            else if (direccion==com.DERECHA){
                if(celdas[com.getY()][com.getX()+1]==BLOQUE){
                    return true;
                }           
            }
            else if(direccion==com.ARRIBA){
                if(celdas[com.getY()-1][com.getX()]==BLOQUE){
                    return true;
                }            
            }
        }
        
        
        return false;
        
    }
    
    /**
     * Introduciendo los parámetros necesarios, nos indica si el comecocos ha
     * chocado con uno de los fantasmas,devolviendo "true" si esto es así
     * Si los fantasmas están en estado 
     * "Asustados" estos se suspenden por un tiempo.
     * 
     * @param fantasma
     * @param com
     * @param comDir
     * @param fDir
     * @return boolean muerto
     */ 
    public boolean chocaFantasma(Comecocos fantasma[], Comecocos com, int comDir,int fDir[]){   //Se ejecuta en la clase mueve
       
        boolean muerto=false;
       
        for(int i=0;i<4;i++){
            if(fantasma[i].getX()==com.getX() && fantasma[i].getY()==com.getY() && fantasma[i].vidaFantasma==true){
                muerto=true;
                if(fantasma[i].asustado==true){
                fantasma[i].suspendeFantasma();
                guiComecocos.RejillaPanel.frame.añadirPuntos(200);
                }
                
            }
            if((comDir == com.DERECHA && fDir[i]==Comecocos.IZQUIERDA)  && (com.getX() == fantasma[i].getX()+1) && fantasma[i].getY()==com.getY() && fantasma[i].vidaFantasma==true){
                muerto=true;   
                if(fantasma[i].asustado==true){
                fantasma[i].suspendeFantasma();
                guiComecocos.RejillaPanel.frame.añadirPuntos(200);
                }
            }
            if((comDir == com.IZQUIERDA && fDir[i]==Comecocos.DERECHA)  && (com.getX() == fantasma[i].getX()-1) && fantasma[i].getY()==com.getY() && fantasma[i].vidaFantasma==true){
                muerto=true;  
                if(fantasma[i].asustado==true){
                fantasma[i].suspendeFantasma();
                guiComecocos.RejillaPanel.frame.añadirPuntos(200);
                }
            }
            if((comDir == com.ARRIBA && fDir[i]==Comecocos.ABAJO)  && (com.getX() == fantasma[i].getX()) && fantasma[i].getY()==com.getY()+1 && fantasma[i].vidaFantasma==true){
                muerto=true;    
                if(fantasma[i].asustado==true){
                fantasma[i].suspendeFantasma();
                guiComecocos.RejillaPanel.frame.añadirPuntos(200);
                }
            }    
            if((comDir == com.ABAJO && fDir[i]==Comecocos.ARRIBA)  && (com.getX() == fantasma[i].getX()) && fantasma[i].getY()+1==com.getY() && fantasma[i].vidaFantasma==true){
                muerto=true;    
                if(fantasma[i].asustado==true){
                fantasma[i].suspendeFantasma();
                guiComecocos.RejillaPanel.frame.añadirPuntos(200);
                }
            }            
        }
        
        
        
        return muerto;
        
    }


    

    


}
