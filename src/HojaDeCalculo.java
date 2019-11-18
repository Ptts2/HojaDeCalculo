import java.util.*;

public class HojaDeCalculo{

    public static void main(final String[] args) {

        int nHojas = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList<Hoja> listaDeHojas = new ArrayList<Hoja>();

        //Leer número de hojas de cálculo
        try{
            nHojas = Integer.parseInt(sc.nextLine());
            
            //El numero de hojas debe ser al menos 1
            if(nHojas<1){
                System.out.println("El numero de hojas debe ser positivo mayor que 0.");
                System.exit(-1);
            }
        }catch(Exception e){
            System.out.println("El numero de hojas debe ser un entero.");
            System.exit(-1);
        }

        //Leeo las hojas
        for(int i = 0; i<nHojas; i++){
            leerHojas(sc, listaDeHojas);
        }

        //Imprimo las hojas
       for(int i = 0; i<nHojas; i++){
           System.out.println(listaDeHojas.get(i).toString());
       }
    }

    public static void leerHojas(Scanner sc, ArrayList<Hoja> lista){
        int nFil=0, nCol=0;
        Hoja hoja;
        String[] filYCol = (sc.nextLine().split(" "));

        //Leo el numero de filas y de columnas
        try{
            nFil = Integer.parseInt(filYCol[0]);
            nCol = Integer.parseInt(filYCol[1]);

            //compruebo que el numero de filas y columnas sea positivo
            if(nFil<1 || nCol<1){
                System.out.println("El numero de filas y columnas debe ser positivo mayor que 0.");
                System.exit(-1);
            }
        }catch(Exception e){
            System.out.println("El numero de filas y columnas debe ser un entero.");
            System.exit(-1);
        }

        //Creo la hoja

        hoja = new Hoja(nFil, nCol);

        //Leo las filas

        for(int i = 0; i<hoja.getNFil(); i++){
            String linea = sc.nextLine();
            String [] columnas = linea.split(" ");

            if(columnas.length != hoja.getNCol()){
                System.out.println("Error al introducir fila en la hoja de calculo");
                System.exit(-1);
            }

            hoja.setFila(i, columnas);
        }
        hoja.calcular();
        //Añado la hoja a la lista

        lista.add(hoja);

    }
}

class Hoja{

    //Array que contiene el abecedario
    final char[] abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /*Atributos de la hoja de cálculo*/
    private int[][] hoja;
    private String[][] hojaRef;
    private int nFil;
    private int nCol;

    /**
     * Constructor de la clase
     * @param nFil Numero de filas
     * @param nCol Numero de columnas
     */
    public Hoja(int nFil, int nCol){
        this.nFil = nFil;
        this.nCol = nCol;
        this.hoja = new int[nFil][nCol];
        this.hojaRef = new String[nFil][nCol];
        inicializar();
    }

    /**
     * Getter del numero de filas
     * @return numero de filas
     */
    public int getNFil(){
        return this.nFil;
    }

    /**
     * Getter del numero de columnas
     * @return numero de columnas
     */
    public int getNCol(){
        return this.nCol;
    }

    /**
     * Cambia el valor a una casilla, el valor que llega siempre es correcto
     * @param valor Valor de la casilla
     * @param fila Fila de la casilla
     * @param col Columna de la casilla
     */
    public void setCasilla(int valor, int fila, int col){

        this.hoja[fila][col] = valor;
    }

    /**
     * Llega cualquier string que empiece por "=", si la fórmula esta mal sale del programa
     * @param formula formula a leer
     * @return solucion de la formula, si la tiene
     */
    public int resolverFormula(String formula){
        //...

        return 0;
    }

    /**
     * Añade las filas a la hoja de calculo de referencias
     * Se supone que el numero de fila es correcto 
     * y que el string de la fila tiene el mismo numero de elementos que
     * numero de columnas
     * @param fil numero de fila
     * @param fila contenido de la fila
     */
    public void setFila(int fil, String[] fila){
        
        for(int i = 0; i<this.nCol; i++){
            this.hojaRef[fil][i] = fila[i];
        }
    }

    /**
     * Inicializa la hoja a cero
     */
    private void inicializar(){

        for(int i = 0; i<this.nFil; i++){
            for(int j = 0; j<this.nCol; j++){
                this.hoja[i][j] = 0;
            }
        }
    }

    /**
     * Hace los calculos correspondientes a la hoja de calculo cuando
     * la matriz de referencias esta completa
     */
    public void calcular(){

        for(int i = 0; i<this.nFil; i++){
            for(int j = 0; j<this.nCol; j++){

                //Si es formula
                if(this.hojaRef[i][j].charAt(0) == '=') {

                    this.hoja[i][j] = this.resolverFormula(this.hojaRef[i][j]);

                }else{
                    
                    try{
                        this.hoja[i][j] = Integer.parseInt(this.hojaRef[i][j]);

                    }catch(Exception e){
                        System.out.println("Entrada de hoja de calculo incorrecta");
                        System.exit(-1);
                    }
                }
                
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder salida = new StringBuilder();

        for(int i = 0; i<this.nFil; i++){
            for(int j = 0; j<this.nCol; j++){
                salida.append(this.hoja[i][j]);

                if(j != this.nCol-1){
                    salida.append(" ");
                }
            }
            if(i != nFil-1){
                salida.append(System.lineSeparator());
            }
        }
        
        return salida.toString();
    }
}