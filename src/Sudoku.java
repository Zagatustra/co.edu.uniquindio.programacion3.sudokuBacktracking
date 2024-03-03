/*
Programador: Gonzaga Ospina Patiño
Programa: Solucionador de Sudokus 9x9 usando la tecnica de Backtracking
Fecha incio:28 Feb/2024
Version 1.0
 */
/*
¿Que es el backtracking?
-Es una tecnica o metodo la cual tiene como proposito solucionar problemas revisando todos los posibles escenarios
o resultados que satisfacen las restricciones. Este método usa la recursividad para crear hilos de posibilidades
los cuales muestran los posibles escenarios y encunetra (si lo hay una respuesta).
 */
public class Sudoku {
    public static void main(String[] args) {
        int[][] sudoku = {
                {5, 3, 4, 6, 7, 8, 9, 0, 0},
                {6, 7, 0, 1, 9, 5, 0, 4, 8},
                {0, 9, 8, 0, 4, 0, 5, 6, 7},
                {8, 5, 9, 7, 6, 0, 4, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        int[][] sudokuSinResolver = {
                {0, 2, 0, 0, 0, 8, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 5, 0},
                {0, 9, 0, 7, 0, 0, 2, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 3, 0, 0, 4, 0, 7, 0},
                {0, 1, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 8, 0}
        };

        int[][] sudokuSinResolver1 = {
                {0, 0, 0, 0, 9, 0, 0, 2, 0},
                {4, 0, 2, 5, 0, 0, 0, 6, 0},
                {0, 5, 3, 0, 7, 0, 0, 4, 0},
                {0, 7, 8, 0, 0, 1, 0, 0, 0},
                {9, 0, 0, 0, 5, 0, 0, 0, 0},
                {0, 4, 0, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 7, 0, 0, 2},
                {5, 0, 0, 0, 4, 0, 7, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 6}
        };
        printSudoku(sudokuSinResolver1,0,0);
        System.out.println("------------------------------");
        resolverSudoku(sudokuSinResolver1,0,0);
        printSudoku(sudokuSinResolver1,0,0);
    }

    public static boolean resolverSudoku(int[][] sudoku, int fila, int columna) {
        int[] posicionActual = encontrarPosicionVacia(sudoku, fila, columna);

        if (posicionActual == null) {
            System.out.println("Done!");
            return true;  // Sudoku resuelto o sin pocisiones vacias
        }

        fila = posicionActual[0];
        columna = posicionActual[1];

        int numeroAPoner = 1;

        return intentarPonerNumero(sudoku, fila, columna, numeroAPoner);
    }


    /**
     * Esta función intenta poner un numero entre el uno y el nueve dentro de la celda dada
     * @param sudoku matrix 9x9
     * @param fila indicie de la fila
     * @param columna indice de la columna
     * @param numeroAPoner numero a poner en la celda del sudoku
     * @return
     */
    public static boolean intentarPonerNumero(int[][] sudoku, int fila, int columna, int numeroAPoner) {
        if (numeroAPoner > 9) {
            return false;  // No hay números válidos para poner, retroceder
        }

        if (esNumValido(sudoku, fila, columna, numeroAPoner)) {
            sudoku[fila][columna] = numeroAPoner;

            if (resolverSudoku(sudoku, fila, columna + 1)) {
                return true;  // Sudoku resuelto
            }
            //Esta linea es importatnte puesto que es la que  permite volver a colocar
            //un cero en una celda que se le asigno un numero pero que dicho camino escogido
            //no llevo a la solución del problema. Si no existe esta linea el programa se detiene
            //al rellenar la primera fila con los campos posibles
            sudoku[fila][columna] = 0;  // Deshacer el cambio si no conduce a una solución
        }

        return intentarPonerNumero(sudoku, fila, columna, numeroAPoner + 1);
    }


    /** Funcion que mediante las funciones enFila, enColumna y enBloque revisa
     *  si es posible poner un numero en una celda vacia dada del sudoku(matriz)
     * @param sudoku, matrix de 9x9
     * @param fila, indice de la fila
     * @param columna, indice de la columna
     * @param num, el numero que se va a poner en la celda escogida siempre y cuando sea valido
     * @return true si las tres funciones usadas (enFilas, enColumna y enBloque) son false, osea, que el numero
     * no fue encontrado.
     */
    public static boolean esNumValido(int[][] sudoku, int fila,int columna, int num){
        return !enFila(sudoku,fila,0,num) &&
                !enColumna(sudoku,0,columna,num)&&
                !enBloque(sudoku,fila,columna,num,0,0);
    }

    /**
     * Esta funcion revisa si el numero a poner en la celda vacia del sudoku se encunetra en la
     * correspondiente columna
     * @param sudoku, matrix de 9x9
     * @param fila, indice de la fila
     * @param columna, indice de la columna
     * @param num, el numero a poner que se va a buscar en la columna escogida
     * @return true si encuentra el numero dado dentro de la columna
     *         false si no encuentra el numero dado dentro de la columna
     */
    public static boolean enColumna(int[][] sudoku, int fila, int columna,int num){
        if(fila==sudoku.length){
            return false;
        }
        if (sudoku[fila][columna]==num){
            return true;
        }
        return enColumna(sudoku,fila+1,columna,num);
    }

    /**
     * Esta funcion revisa si el numero a poner en la celda vacia del sudoku se encuentra en la
     * correspondiente fila
     * @param sudoku, matrix de 9x9
     * @param fila, indice de la fila
     * @param columna, indice de la columna
     * @param num, el numero a poner que se va a buscar en la fila escogida
     * @return true si encuentra el numero dado dentro de la fila
     *         false si no encuentra el numero dado dentro de la fila
     */
    public static boolean enFila(int[][] sudoku,int fila,  int columna,int num){
        if(columna==sudoku.length){
            return false;
        }
        if (sudoku[fila][columna]==num){
            return true;
        }
        return enFila(sudoku,fila,columna+1,num);
    }

    /**
     * Esta funcion revisa si el numero a poner en la celda vacia del sudoku se encuentra en el
     * correspondiente bloque, esto mediante el uso del modulo y dos variables "i" y "j" que permiten
     * contar las secciones de 3x3 que corresponden a un bloque
     * @param sudoku matrix 9x9
     * @param fila indice de la fila
     * @param columna incidice de la columna
     * @param num numero a poner que se va a buscar en el correspondiente bloque
     * @param i indice que se mueve entre 0 y 3 que permite delimitar la busqueda en bloque para las filas
     * @param j indice que se mueve entre 0 y 2 que permite delimitar la busqueda en bloque para las columnas
     * @return true si encuentra el numero dado dentro del bloque
     *      *         false si no encuentra el numero dado dentro del bloque
     */
    public static boolean enBloque(int[][] sudoku,int fila, int columna, int num, int i, int j){
        //llego a la ultima fila y no encontro el numero en el bloque
        if(i==3){
            return false;
        }
        if(sudoku[fila-fila%3+i][columna-columna%3+j]==num){
            return true;
        }

        if(j==2){
            return enBloque(sudoku,fila,columna,num,i+1,0);
        }
        return enBloque(sudoku,fila,columna,num,i,j+1);
    }


    /**
     * Esta funcion permite encontrar una celda vacia en el sudoku, la cual es representada por el
     * numero cero.
     * @param sudoku matrix 9x9
     * @param indFil indice de la fila
     * @param indCol indice de la columna
     * @return null si no encuentra celda vacia en el todo el sudoku
     *         returna un arreglo de dos componentes que seran las posiciones de filas y columnas respectivamente
     *         de la celda vacia(que contiene un cero)
     */
    public static int[] encontrarPosicionVacia(int[][] sudoku, int indFil, int indCol) {
        int[] posicion = new int[2];

        if (indFil == sudoku.length) {
            return null;  // Ya hemos llegado al final del sudoku
        }

        if (indCol == sudoku[0].length) {
            // Reinicia la columna y avanza a la siguiente fila
            indCol = 0;
            indFil++;
        }

        if (indFil < sudoku.length) {
            if (sudoku[indFil][indCol] == 0) {
                // Encontramos una posición vacía
                posicion[0] = indFil;
                posicion[1] = indCol;
                return posicion;
            } else {
                // No es una posición vacía, intentemos la siguiente columna
                return encontrarPosicionVacia(sudoku, indFil, indCol + 1);
            }
        }

        // Índices fuera de límites, no hemos llegado al final
        return encontrarPosicionVacia(sudoku, indFil, indCol + 1);
    }

    public static void printSudoku(int[][] sudoku, int indFil, int indCol) {
        if (indFil >= sudoku.length) {
            return;  // Ya hemos llegado al final del sudoku
        }

        System.out.print(" "+sudoku[indFil][indCol]+" ");

        if (indCol == sudoku[0].length - 1) {
            // Reinicia la columna y avanza a la siguiente fila
            System.out.println();
            indCol = 0;
            indFil++;
        } else {
            indCol++;
        }

        printSudoku(sudoku, indFil, indCol);
    }
}