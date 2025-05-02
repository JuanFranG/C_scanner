package Automatas;

public class NotacionCientifica {

    public static boolean validarNotacionCientifica(String entrada) {
        int estado = 0; // Estado inicial
        int[][] tablaTransiciones = {
            {2, 8, 9, 1, 0},   // Estado 0
            {2, 8, 9, 9, 0},   // Estado 1
            {2, 3, 5, 9, 1},   // Estado 2
            {4, 9, 5, 9, 1},   // Estado 3
            {4, 9, 5, 9, 1},   // Estado 4
            {6, 9, 9, 7, 0},   // Estado 5
            {6, 9, 9, 9, 1},   // Estado 6
            {6, 9, 9, 9, 0},   // Estado 7
            {4, 9, 9, 9, 0},   // Estado 8
            {9, 9, 9, 9, 0}    // Estado 9 (error)
        };

        boolean puntoDecimalEncontrado = false; // Variable para rastrear el punto decimal

        for (char c : entrada.toCharArray()) {
            int columna = obtenerColumna(c);

            if (columna == 1) { // Punto decimal
                if (puntoDecimalEncontrado) {
                    return false; // Múltiples puntos decimales, error
                }
                puntoDecimalEncontrado = true;
            }

            estado = tablaTransiciones[estado][columna];
            if (estado == 9) { // Estado de error
                return false;
            }
        }

        // Estados de aceptación: 2 (entero), 3 (punto flotante sin exponente), 4 (punto flotante con exponente), 6 (notación científica completa)
        return estado == 2 || estado == 3 || estado == 4 || estado == 6;
    }

    private static int obtenerColumna(char c) {
        if (Character.isDigit(c)) {
            return 0; // Dígito (DI)
        } else if (c == '.') {
            return 1; // Punto (.)
        } else if (c == 'E' || c == 'e') {
            return 2; // Exponente (E)
        } else if (c == '+' || c == '-') {
            return 3; // Signo (S)
        } else {
            return 4; // Otro carácter (error)
        }
    }
}