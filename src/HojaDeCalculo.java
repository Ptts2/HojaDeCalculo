import java.util.*;

public class HojaDeCalculo {

    public static void main(final String[] args) {

        int nHojas = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList<Hoja> listaDeHojas = new ArrayList<Hoja>();

        // Leer número de hojas de cálculo
        try {
            nHojas = Integer.parseInt(sc.nextLine());

            // El numero de hojas debe ser al menos 1
            if (nHojas < 1) {
                System.out.println("El numero de hojas debe ser positivo mayor que 0.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("El numero de hojas debe ser un entero.");
            System.exit(-1);
        }

        // Leeo las hojas
        for (int i = 0; i < nHojas; i++) {
            leerHojas(sc, listaDeHojas);
        }

        // Imprimo las hojas
        for (int i = 0; i < nHojas; i++) {
            System.out.println(listaDeHojas.get(i).toString());
        }
    }

    /**
     * Metodo que lee una hoja de calculo y la resuelve
     * @param sc Scannel
     * @param lista Lista donde se almacenan las distintas hojas de calculo
     */
    public static void leerHojas(Scanner sc, ArrayList<Hoja> lista) {
        int nFil = 0, nCol = 0;
        Hoja hoja;
        String[] filYCol = (sc.nextLine().split(" "));

        // Leo el numero de columnas y de filas
        try {
            nCol = Integer.parseInt(filYCol[0]);
            nFil = Integer.parseInt(filYCol[1]);

            // compruebo que el numero de filas y columnas sea positivo
            if (nFil < 1 || nCol < 1) {
                System.out.println("El numero de filas y columnas debe ser positivo mayor que 0.");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("El numero de filas y columnas debe ser un entero.");
            System.exit(-1);
        }

        // Creo la hoja

        hoja = new Hoja(nFil, nCol);

        // Leo las filas

        for (int i = 0; i < hoja.getNFil(); i++) {
            String linea = sc.nextLine();
            String[] columnas = linea.split(" ");

            if (columnas.length != hoja.getNCol()) {
                System.out.println("Error al introducir fila en la hoja de calculo.");
                System.exit(-1);
            }

            hoja.setFila(i, columnas);
        }
        hoja.calcular();

        // Añado la hoja a la lista
        lista.add(hoja);

    }
}

class Hoja {

    // Array que contiene el abecedario
    final String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /* Atributos de la hoja de cálculo */
    private int[][] hoja;
    private String[][] hojaRef;
    private int nFil;
    private int nCol;

    /**
     * Constructor de la clase
     * 
     * @param nFil Numero de filas
     * @param nCol Numero de columnas
     */
    public Hoja(int nFil, int nCol) {
        this.nFil = nFil;
        this.nCol = nCol;
        this.hoja = new int[nFil][nCol];
        this.hojaRef = new String[nFil][nCol];
        inicializar();
    }

    /**
     * Getter del numero de filas
     * 
     * @return numero de filas
     */
    public int getNFil() {
        return this.nFil;
    }

    /**
     * Getter del numero de columnas
     * 
     * @return numero de columnas
     */
    public int getNCol() {
        return this.nCol;
    }

    /**
     * Cambia el valor a una casilla, el valor que llega siempre es correcto
     * 
     * @param valor Valor de la casilla
     * @param fila  Fila de la casilla
     * @param col   Columna de la casilla
     */
    public void setCasilla(int valor, int fila, int col) {

        this.hoja[fila][col] = valor;
    }

    /**
     * Llega cualquier string que empiece por "=", si la fórmula esta mal sale del
     * programa
     * 
     * @param formula formula a leer
     * @return solucion de la formula, si la tiene
     */
    public int resolverFormula(String formula) {
        int valor = 0;
        ArrayList<int[]> casillas = new ArrayList<int[]>();
        // Suma

        String[] factores = formula.substring(1).split("\\+");

        for (int i = 0; i < factores.length; i++) {
            casillas.add(descifrarCasilla(factores[i]));
        }

        try {
            for (int i = 0; i < factores.length; i++) {
                valor = valor + this.hoja[casillas.get(i)[0]][casillas.get(i)[1]];

                if(this.hoja[casillas.get(i)[0]][casillas.get(i)[1]] == Integer.MIN_VALUE){
                    return Integer.MIN_VALUE;
                }
            }

        } catch (Exception e) {
            System.out.println("Error de formula, fuera de los limites de la hoja.");
            System.exit(-1);
        }
        return valor;
    }

    /**
     * Metodo para calcular las coordenadas de una casilla en formato de celda de
     * hoja de calculo
     * 
     * @param casilla Formato de celda de la hoja
     * @return coordenadas numericas en formato [Fila][Columna]
     */
    public int[] descifrarCasilla(String casilla) {

        int[] casillas = new int[2];
        int iFil = 0, iCol = 0, i = 0;
        boolean buscando = true;
        String columnas;

        do {

            if (!(abecedario.contains(String.valueOf(casilla.charAt(i))))) {

                if (i == 0) {
                    System.out.println("Error de formula, la formula debe estar compuesta unicamente por celdas.");
                    System.exit(-1);
                }
                iFil = i;
                buscando = false;
            }
            i++;
        } while (buscando && i < casilla.length());

        try {
            casillas[0] = Integer.parseInt(casilla.substring(iFil)) - 1;
        } catch (Exception e) {
            System.out.println("Error de formula, los valores han de ser numéricos y enteros");
            System.exit(-1);
        }

        // Calcular las columnas
        columnas = casilla.substring(0, iFil);

        try {
            for (i = 0; i < columnas.length(); i++) {
                int indice = abecedario.indexOf(columnas.charAt(i));

                if (indice < 0) {
                    System.out.println("Error de formula, caracter no reconocido.");
                    System.exit(-1);
                }
                // para calcular el valor en base 26 ValorDec * sistemadenum ^ indice

                iCol = iCol + (indice * ((int) (Math.pow(26.0, (double) ((columnas.length() - 1) - i)))));
            }
        } catch (Exception e) {
            System.out.println("Error de formula.");
            System.exit(-1);
        }

        casillas[1] = iCol;
        return casillas;
    }

    /**
     * Añade las filas a la hoja de calculo de referencias Se supone que el numero
     * de fila es correcto y que el string de la fila tiene el mismo numero de
     * elementos que numero de columnas
     * 
     * @param fil  numero de fila
     * @param fila contenido de la fila
     */
    public void setFila(int fil, String[] fila) {

        for (int i = 0; i < this.nCol; i++) {
            this.hojaRef[fil][i] = fila[i];
        }
    }

    /**
     * Inicializa la hoja a cero
     */
    private void inicializar() {

        for (int i = 0; i < this.nFil; i++) {
            for (int j = 0; j < this.nCol; j++) {
                this.hoja[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    /**
     * Hace los calculos correspondientes a la hoja de calculo cuando la matriz de
     * referencias esta completa
     */
    public void calcular() {

        ArrayList <Formula> formulas = new ArrayList<Formula>();
        for (int i = 0; i < this.nFil; i++) {
            for (int j = 0; j < this.nCol; j++) {

                // Si es formula la añado a una lista para hacerlo cuando todos los valores de
                // las demas casillas esten puestos
                if (this.hojaRef[i][j].charAt(0) == '=') {

                    formulas.add(new Formula(this.hojaRef[i][j], i, j));

                } else {

                    try {
                        this.hoja[i][j] = Integer.parseInt(this.hojaRef[i][j]);

                    } catch (Exception e) {
                        System.out.println("Entrada de hoja de calculo incorrecta.");
                        System.exit(-1);
                    }
                }

            }
        }

        //Ahora resuelvo las formulas, solo las resuelvo cuando todas las casillas que afectan a la formula tienen un valor asignado
        boolean resolviendo = true;
        int cont = 0, errores = 0;
        
        while(resolviendo){
            int valor = resolverFormula(formulas.get(cont).getFormula());;
             
            if(valor == Integer.MIN_VALUE){
                cont++;
                errores++;
                if( (errores >= 2 && formulas.size() == 2) || cont>=formulas.size()){
                    System.out.println("Error de formula, dependencias entre formulas");
                    System.exit(-1);
                }
            }else{
                this.hoja[formulas.get(cont).getFil()][formulas.get(cont).getCol()] = valor;
                formulas.remove(cont);
                cont = 0;
                errores = 0;
                if(formulas.isEmpty())
                    resolviendo = false;
            }

        }

    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder();

        for (int i = 0; i < this.nFil; i++) {
            for (int j = 0; j < this.nCol; j++) {
                salida.append(this.hoja[i][j]);

                if (j != this.nCol - 1) {
                    salida.append(" ");
                }
            }
            if (i != nFil - 1) {
                salida.append(System.lineSeparator());
            }
        }

        return salida.toString();
    }
}

class Formula{

    /* Atributos de la hoja de cálculo */
    private String formula;
    private int filFormula;
    private int colFormula;

    /**
     * Constructor de la clase formula
     * @param formula String que contiene las casillas de la formula y los operadores
     * @param fil Fila a la que pertenece la formula
     * @param col Columna a la que pertenece la formula
     */
    public Formula(String formula, int fil, int col){
        this.formula = formula;
        this.filFormula = fil;
        this.colFormula = col;
    }

    /**
     * Devuelve la formula como string
     * @return formula como string
     */
    public String getFormula(){
        return this.formula;
    }

    /**
     * Devuelve la fila a la que pertenece la formula
     * @return fila de la formula
     */
    public int getFil(){
        return this.filFormula;
    }

     /**
     * Devuelve la columna a la que pertenece la formula
     * @return columna de la formula
     */
    public int getCol(){
        return this.colFormula;
    }


}