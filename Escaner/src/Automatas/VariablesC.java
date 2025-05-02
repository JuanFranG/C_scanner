package Automatas;

public class VariablesC {

    public static boolean esIdentificadorValido(String identificador) {
        if (identificador == null || identificador.isEmpty()) {
            return false;
        }

        int estado = 0; // Estado inicial
        int[][] tablaTransiciones = {
                {1, 2, 1, 0},  // Estado 0
                {1, 1, 1, 1},  // Estado 1
                {2, 2, 2, 0}   // Estado 2
        };

        for (char c : identificador.toCharArray()) {
            int columna = -1; // Inicializar a un valor inválido

            if (Character.isLetter(c)) {
                columna = 0; // L
            } else if (Character.isDigit(c)) {
                columna = 1; // D
            } else if (c == '_') {
                columna = 2; // _
            } 

            if (columna == -1) {
                return false; // Carácter inválido
            }

            estado = tablaTransiciones[estado][columna];

            if (estado == 2) {
                return false; // Estado de error
            }
        }

        return estado == 1 && !identificador.contains(" "); // El identificador es válido si termina en el estado 1 y no contiene espacios
    }
}
