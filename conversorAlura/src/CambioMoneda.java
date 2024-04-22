import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class CambioMoneda {

    public static void main(String[] args) {
        int i=0;
        ArrayList <LogErrores>logArray=new ArrayList();
        LogErrores log=new LogErrores();
        Double TasaCambio=0.00;
        Double Monto=0.00;
        Double resultadofin=0.00;
        String MonedaAConvertir="";
        String resultado="";
        String logcompleto="";
        boolean fin=true;
        Scanner teclado=new Scanner(System.in);
        LocalDateTime hoy=LocalDateTime.now();
        /**
         * While del programa.
         */
        while(fin){
        //Llamada al menu y lectura del teclado
                     Menu();
            i=teclado.nextInt();
          //Ifs de selección de operación
            if(i==1){
                log=new LogErrores();
                System.out.println("Escoge la Moneda a Convertir Tecleando las siglas Ejemplo: CANADA--> CAD ");
                Listado_DePaises(resultado);
                MonedaAConvertir=teclado.nextLine();
                MonedaAConvertir=teclado.nextLine();
                MonedaAConvertir=MonedaAConvertir.toUpperCase();
                log.setMoneda(MonedaAConvertir);
                TasaCambio=Tasa(MonedaAConvertir);
                System.out.println("Ingresa la cantidad a Convertir ");
                Monto=teclado.nextDouble();
                log.setMonto(Monto);
                resultadofin=Monto/TasaCambio;
                DecimalFormat df=new DecimalFormat("#.00");
                resultadofin= Double.valueOf(df.format(resultadofin));
                log.setSaldo(resultadofin);
                hoy=LocalDateTime.now();
                log.setHoy(hoy);
                System.out.println("El valor en Dolares es: "+resultadofin);
                log.setDescripcion("Se cambia de la Moneda seleccionada a Dolares");
                logArray.add(log);
                EscribirLog(logArray);

            } else if (i==2) {
                log=new LogErrores();
                System.out.println("Escoge la Moneda a Convertir Tecleando las siglas Ejemplo: CANADA--> CAD ");
                Listado_DePaises(resultado);
                MonedaAConvertir=teclado.nextLine();
                MonedaAConvertir=teclado.nextLine();
                MonedaAConvertir=MonedaAConvertir.toUpperCase();
                log.setMoneda(MonedaAConvertir);
                TasaCambio=Tasa(MonedaAConvertir);
                System.out.println("Ingresa la cantidad a Convertir ");
                Monto=teclado.nextDouble();
                log.setMonto(Monto);
                resultadofin=Monto*TasaCambio;
                DecimalFormat df=new DecimalFormat("#.00");
                resultadofin= Double.valueOf(df.format(resultadofin));
                log.setSaldo(resultadofin);
                hoy=LocalDateTime.now();
                log.setHoy(hoy);
                System.out.println("El valor en"+MonedaAConvertir+" es: "+resultadofin);
                log.setDescripcion("Se cambia de Dolares a Moneda seleccionada");
                logArray.add(log);
                EscribirLog(logArray);
            }else if(i==3){
                String userdir = System.getProperty("user.dir");
                userdir = userdir + "\\src\\logfile.txt";
                String linea="";

                try (FileReader fr = new FileReader(userdir);) {
                    BufferedReader br = new BufferedReader(fr);
                    // Lectura del fichero
                    while ((linea = br.readLine()) != null) {
                        linea = linea + "\n" + linea;
                        logcompleto=linea;
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("El Log de cambios es el siguiente: ");
                System.out.println(logcompleto);
                System.out.println("fin del Log");
            }else if (i==4) {
                System.out.println("Gracias Hasta Pronto");
                EscribirLog(logArray);
                fin=false;
            }
        }


        //Conexion(resultado);
    }

    /**
     * Metodo que permite la conexión
     * y devuelve un resultado
     * base  es para poder obtener el listado completo de países
     */
    static JsonObject Conexion(String resultado,int base){
        JsonObject objetojason = null;
        // Setting URL
        String url_str;
        if (base==1){
            url_str = "https://v6.exchangerate-api.com/v6/1bf93de29e1c680e7575755e/latest/USD";
        }else{
             url_str = "https://v6.exchangerate-api.com/v6/1bf93de29e1c680e7575755e/codes";
        }


    // Making Request
        try {
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            objetojason= root.getAsJsonObject();
            // Accessing object
            resultado = objetojason.get("result").getAsString();
            
            
        }catch (Exception e){
            System.out.println(e);
        }

        return objetojason;
    }

    /**
     * Menu textual
     */
    static void Menu(){
        System.out.println(""" 
                ************************************************************************************************************************************************************************************
                Bienvenido al Conversor de Monedas, por favor elige una de las siguientes opciones:
                1. Convertir de Moneda a Dolar.
                2. Convertir de Dolar a Moneda.
                3. Log de Conversiones;
                4. Salir del programa.
                **************************************************************************************************************************************************************************************""");
    }

    /**
     * Listado de paises
     * presentara las opciones de 10 paises por vez
     * teniendo la opcion de cancelar
     */
   static void Listado_DePaises(String resultado){
        JsonObject objetoj=Conexion(resultado,2);
        JsonArray array_de_codigos=objetoj.get("supported_codes").getAsJsonArray();
       for (int i = 0; i < array_de_codigos.size()-1; i++) {
           System.out.println(array_de_codigos.get(i));
       }
       JsonArray valor1=array_de_codigos.get(0).getAsJsonArray();

    }

    /**
     * Devuelve la tasa de cambio buscada
     * @param resultado
     */
    static Double Tasa(String resultado){
        Map<String, JsonElement> valores=null;
        Map<String,Double> datosfinales=null;
      String cambio=null;
        JsonObject objetoj=Conexion(resultado,1);
        valores=objetoj.asMap();
        JsonElement tasas=valores.get("conversion_rates");
        Gson parse=new Gson();
        datosfinales= parse.fromJson(parse.toJson(tasas),Map.class);

        return Double.parseDouble(String.valueOf(datosfinales.get(resultado)));
    }

    /**
     * Método que se utiliza para guardar la información en el Logfile.txt
     */
    static void EscribirLog(ArrayList logarray){
        String userdir = System.getProperty("user.dir");
        userdir = userdir + "\\src\\logfile.txt";
        try (FileWriter fichero = new FileWriter(userdir))
        {
            PrintWriter pw = new PrintWriter(fichero);

            for (int i = 0; i < logarray.size() ; i++){
                LogErrores lg= (LogErrores) logarray.get(i);
                pw.println("Logentry: "+lg.getHoy()+"--"+lg.getDescripcion()+" Con la Moneda escogida:  "+lg.getMoneda()+" con el monto inicial de: " +lg.getMonto()+" y resultado de: "+lg.getSaldo());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
