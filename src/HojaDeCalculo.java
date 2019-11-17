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

        //...

        //Añado la hoja a la lista

        lista.add(hoja);

    }
}

class Hoja{

    /*Atributos de la hoja de cálculo*/

    private int[][] hoja;
    private int nFil;
    private int nCol;

    public Hoja(int nFil, int nCol){
        this.nFil = nFil;
        this.nCol = nCol;
        hoja = new int[nFil][nCol];
        inicializar();
    }

    /**
     * Cambia el valor a una casilla, el valor que llega siempre es correcto
     * @param valor Valor de la casilla
     * @param fila Fila de la casilla
     * @param col Columna de la casilla
     */
    public setCasilla(int valor, int fila, int col){

        this.hoja[fila][col] = valor;
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