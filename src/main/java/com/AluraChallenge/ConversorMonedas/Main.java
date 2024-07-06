package com.AluraChallenge.ConversorMonedas;



import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static servicioAPI servicio = new servicioAPI();
    public static String API_KEY = "";

    public static void main(String[] args) {


        System.out.println("Bienvenido/a a la aplicación de conversión de monedas.");

        String menu = """
                --------------------------------------------------------
                Selecciona una de las siguientes opciones:
                
                1. Convertir entre dos monedas
                2. Consultar los codigos de las monedas disponibles
                3. Salir
                
                --------------------------------------------------------
                """;
        int opcion = 0;
        Scanner input = new Scanner(System.in);

        while(opcion != 3){
            System.out.println(menu);
            try{
                opcion = input.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Escribe una opción valida.");
                input.nextLine();
            }

            switch (opcion){
                case 1:
                    convertirMonedas();
                    break;
                case 2:
                    consultarCodigosIdiomas();
                    break;
                case 3:
                    System.out.println("Gracias por usar la aplicación de conversión de monedas.");
                    System.out.println("Cerrando la aplicación.");
                    break;
            }
        }


    }

    public static void consultarCodigosIdiomas(){
        System.out.println("Los codigos de monedas disponibles en la aplicación son:");
        System.out.println("Codigo  |  Nombre de Moneda");
        CodigosMonedas.codigosMonedas
                .forEach((key, value) -> System.out.println(STR."\{key}     |  \{value}"));
    }

    public static void convertirMonedas(){
        RespuestaParser parser = new RespuestaParser();
        Scanner input = new Scanner(System.in);
        System.out.println("Ingresa el codigo de la moneda de la que quieres convertir.");
        String monedaBase = input.nextLine().trim().toUpperCase();
        if(!codigoMonedaValido(monedaBase)){
            System.out.println(STR."Lo sentimos, el codigo '\{monedaBase}' no está disponible en la aplicación");
            return;
        }
        System.out.println("Ahora, ingresa el codigo de la moneda a la que quieres convertir.");
        String monedaObjetivo = input.nextLine().trim().toUpperCase();
        if(!codigoMonedaValido(monedaObjetivo)){
            System.out.println(STR."Lo sentimos, el codigo '\{monedaObjetivo}' no está disponible en la aplicación");
            return;
        }

        System.out.println(STR."Ingresa la cantidad en \{monedaBase} que quieres convertir a \{monedaObjetivo}.");
        try{
            double cantidad = Double.parseDouble(input.nextLine());
            try{
                String jsonResponse = servicio.obtenerTasaCambio(monedaBase,monedaObjetivo,API_KEY);
                double tasaCambio = parser.obtenerTasaCambio(jsonResponse);
                System.out.println(
                        STR."\{formatearMoneda(cantidad, monedaBase)} (\{CodigosMonedas.codigosMonedas.get(monedaBase)}) equivale a \{formatearMoneda(cantidad*tasaCambio,monedaObjetivo)} (\{CodigosMonedas.codigosMonedas.get(monedaObjetivo)})"
                );
            }catch(Exception e){
                System.out.println("Ocurrió un error, por favor intentalo de nuevo.");
            }
        }catch(NumberFormatException e){
            System.out.println("Introduce un número en formato adecuado.");
        }


    }

    public static Boolean codigoMonedaValido(String codigoMoneda){
        return CodigosMonedas.codigosMonedas.containsKey(codigoMoneda);
    }
    public static String formatearMoneda(double cantidad, String monedaCodigo){

        return NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag(CodigosMonedas.codigosLocales.get(monedaCodigo))
        ).format(cantidad);
    }
}