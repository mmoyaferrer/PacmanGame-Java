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
 * Representa una de las celdas de las que se compone una pieza, posteriormente
 * se heredará esta clase en Comecocos para tener los métodos de elemento en 
 * Comecocos como plantilla.
 */
public class Elemento {
    private int fila;
    private int columna;
    
    /** 
     * Crea una nueva instancia de elemento
     * @param f fila
     * @param c columna
     */
    public Elemento(int f,int c) {
        fila=f;
        columna=c;
    }
    
    /**
     * Obtiene la fila de este Elemento
     * @return la fila de este Elemento
     */
    public int getFila(){
        return fila;
    }
    
     /**
     * Obtiene la columna de este Elemento
     * @return la columna de este Elemento
     */
    public int getColumna(){
        return columna;
    }
    
    /**
     * Define la fila de este Elemento
     * @param valor el nuevo valor para la fila de este Elemento
     */
    public void setFila(int valor){
        fila=valor;
    }
    
    /**
     * Define la columna de este Elemento
     * @param valor el nuevo valor para la columna de este Elemento
     */    
    public void setColumna(int valor){
        columna=valor;
    }
    
}
 