package Escaner;
import Automatas.NotacionCientifica;
import Automatas.VariablesC;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EscanerC {

    // Tokens
    private static final String INT = "INT";
    private static final String MAIN = "MAIN";
    private static final String VOID = "VOID";
    private static final String BREAK = "BREAK";
    private static final String DO = "DO";
    private static final String ELSE = "ELSE";
    private static final String IF = "IF";
    private static final String WHILE = "WHILE";
    private static final String RETURN = "RETURN";
    private static final String READ = "READ";
    private static final String WRITE = "WRITE";

    private static final String LBRACE = "LBRACE";
    private static final String RBRACE = "RBRACE";
    private static final String LSQUARE = "LSQUARE";
    private static final String RSQUARE = "RSQUARE";
    private static final String LPAR = "LPAR";
    private static final String RPAR = "RPAR";
    private static final String SEMI = "SEMI";
    private static final String PLUS = "PLUS";
    private static final String MINUS = "MINUS";
    private static final String MUL_OP = "MUL_OP";
    private static final String DIV_OP = "DIV_OP";
    private static final String AND_OP = "AND_OP";
    private static final String OR_OP = "OR_OP";
    private static final String NOT_OP = "NOT_OP";
    private static final String ASSIGN = "ASSIGN";
    private static final String LT = "LT";
    private static final String GT = "GT";
    private static final String SHL_OP = "SHL_OP";
    private static final String SHR_OP = "SHR_OP";
    private static final String EQ = "EQ";
    private static final String NOTEQ = "NOTEQ";
    private static final String LTEQ = "LTEQ";
    private static final String GTEQ = "GTEQ";
    private static final String ANDAND = "ANDAND";
    private static final String OROR = "OROR";
    private static final String COMMA = "COMMA";

    private static final String NUMBER = "NUMBER"; // Cambiado de INT_NUM a NUMBER
    private static final String ID = "ID";
    private static final String DOT = "DOT";

    // Función para verificar palabras reservadas
    private static String getPalabraReservada(String palabra) {
        switch (palabra) {
            case "int":
                return INT;
            case "main":
                return MAIN;
            case "void":
                return VOID;
            case "break":
                return BREAK;
            case "do":
                return DO;
            case "else":
                return ELSE;
            case "if":
                return IF;
            case "while":
                return WHILE;
            case "return":
                return RETURN;
            case "scanf":
                return READ;
            case "printf":
                return WRITE;
            default:
                return null;
        }
    }

    // Función para verificar símbolos especiales
    private static String getSimboloEspecial(String simbolo) {
        switch (simbolo) {
            case "{":
                return LBRACE;
            case "}":
                return RBRACE;
            case "[":
                return LSQUARE;
            case "]":
                return RSQUARE;
            case "(":
                return LPAR;
            case ")":
                return RPAR;
            case ";":
                return SEMI;
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "*":
                return MUL_OP;
            case "/":
                return DIV_OP;
            case "&":
                return AND_OP;
            case "|":
                return OR_OP;
            case "!":
                return NOT_OP;
            case "=":
                return ASSIGN;
            case "<":
                return LT;
            case ">":
                return GT;
            case "<<":
                return SHL_OP;
            case ">>":
                return SHR_OP;
            case "==":
                return EQ;
            case "!=":
                return NOTEQ;
            case "<=":
                return LTEQ;
            case ">=":
                return GTEQ;
            case "&&":
                return ANDAND;
            case "||":
                return OROR;
            case ",":
                return COMMA;
            case ".":
                return DOT;
            default:
                return null;
        }
    }

    // Función para escanear
    public static void escanearArchivo(String archivo) {
        try {
            BufferedReader leer = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = leer.readLine()) != null) {
                int posicion = 0;

                while (posicion < linea.length()) {

                    // Siempre actualiza el carácter actual
                    char caracter = linea.charAt(posicion);

                    // Espacios
                    if (Character.isWhitespace(caracter)) {
                        posicion++;
                        continue;
                    }

                    // Cadenas (strings)
                    if (caracter == '"') {
                        String cadena = "";
                        posicion++;
                        while (posicion < linea.length() && linea.charAt(posicion) != '"') {
                            cadena += linea.charAt(posicion);
                            posicion++;
                        }
                        if (posicion < linea.length()) {
                            posicion++; // Saltar la comilla de cierre
                            System.out.println("Token: STRING \"" + cadena + "\"");
                        }
                        continue;
                    }

                    // Palabras reservadas o identificadores
                    if (Character.isLetter(caracter) || caracter == '_') {
                        String token = "" + caracter;
                        posicion++;
                        while (posicion < linea.length()) {
                            char siguiente = linea.charAt(posicion);
                            if (Character.isLetterOrDigit(siguiente) || siguiente == '_') {
                                token += siguiente;
                                posicion++;
                            } else {
                                break;
                            }
                        }
                        String tipo = getPalabraReservada(token);
                        if (tipo != null) {
                            System.out.println("Token: " + tipo + " \"" + token + "\"");
                        } else if (VariablesC.esIdentificadorValido(token)) {
                            System.out.println("Token: " + ID + " \"" + token + "\"");
                        }
                        continue;
                    }

                    // Números (enteros, punto flotante, notación científica)
                    if (Character.isDigit(caracter)) {
                        StringBuilder numero = new StringBuilder("" + caracter);
                        posicion++;
                        boolean puntoEncontrado = false;
                        boolean exponenteEncontrado = false;

                        // Recolectar parte entera, decimal, exponente
                        while (posicion < linea.length()) {
                            char siguiente = linea.charAt(posicion);
                            if (Character.isDigit(siguiente)) {
                                numero.append(siguiente);
                                posicion++;
                            } else if (siguiente == '.' && !puntoEncontrado && !exponenteEncontrado) {
                                numero.append(siguiente);
                                puntoEncontrado = true;
                                posicion++;
                            } else if ((siguiente == 'e' || siguiente == 'E') && !exponenteEncontrado) {
                                numero.append(siguiente);
                                exponenteEncontrado = true;
                                posicion++;
                                // Permitir signo después de e/E
                                if (posicion < linea.length() && (linea.charAt(posicion) == '+' || linea.charAt(posicion) == '-')) {
                                    numero.append(linea.charAt(posicion));
                                    posicion++;
                                }
                            } else {
                                break;
                            }
                        }

                        if (NotacionCientifica.validarNotacionCientifica(numero.toString())) {
                            System.out.println("Token: " + NUMBER + " \"" + numero + "\"");
                        }
                        continue;
                    }

                    // Operadores dobles (como ==, <=, &&, etc.)
                    if (posicion + 1 < linea.length()) {
                        String doble = "" + caracter + linea.charAt(posicion + 1);
                        String tipo = getSimboloEspecial(doble);
                        if (tipo != null) {
                            System.out.println("Token: " + tipo + " \"" + doble + "\"");
                            posicion += 2;
                            continue;
                        }
                    }

                    // Operadores simples
                    String tipo = getSimboloEspecial("" + caracter);
                    if (tipo != null) {
                        System.out.println("Token: " + tipo + " \"" + caracter + "\"");
                        posicion++;
                        continue;
                    }

                    // Caracter desconocido
                    posicion++;
                }
            }

            leer.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}