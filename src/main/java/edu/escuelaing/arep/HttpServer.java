/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep;

/**
 *
 * @author sergio.nunez
 */

import java.net.*;

import javax.imageio.ImageIO;

import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HttpServer {
	
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int puerto = getPort();;
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println(puerto);
        } catch (IOException e) {
            System.err.println("Could not listen on port:" + getPort());
            System.exit(1);
            System.out.println(puerto);
        }

        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        
        String inputLine, outputLine;
        
        while ((inputLine = in.readLine()) != null) {
        	System.out.println(in.readLine()+"Sergio");
            System.out.println("Recibí: " + inputLine);
            if (inputLine.contains("GET")) {
            	String[] palabra = inputLine.split("/");
                String [] palabra2 =  palabra[1].split(" ");
                System.out.println(palabra2[0].contains(".jpg"));
                if(inputLine.contains(palabra2[0])) {
                	if(palabra2[0].contains(".jpg")) {
                		imagen("/src/main/Resource/"+palabra2[0],clientSocket.getOutputStream(),out);
                	}
                	else if(palabra2[0].contains(".html")) {
                		leenos("/src/main/Resource/"+palabra2[0],out);
                	}
                	else {
                		
                	}
                }
            }
            
            if (!in.ready()) {break; }
            	
            
        }

        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>El cuadrado del numero ingresado es: </h1>\n"
                + "</body>\n" 
                + "</html>\n" + inputLine;
        //out.println(outputLine);
        // recordar que puede fallar si entra en catch y no cierra esta vaina

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }


    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    private static void imagen(String element, OutputStream clientOutput, PrintWriter out) throws IOException {
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + element));
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writeimg = new DataOutputStream(clientOutput);
            String imagen = "HTTP/1.1 404 Not Found \r\n"
                    + "Content-Type: text/html; charset=\"utf-8\" \r\n"
                    + "\r\n";
            //out.write(imagen.getBytes());
            ImageIO.write(image, "PNG", ArrBytes);
            writeimg.writeBytes("HTTP/1.1 200 OK \r\n");
            writeimg.writeBytes("Content-Type: image/png \r\n");
            writeimg.writeBytes("\r\n");
            writeimg.write(ArrBytes.toByteArray());
            System.out.println(System.getProperty("user.dir") + "/resources/imagenes/" + element);
        } catch (IOException e) {
        }

    }
    
    public static void leenos(String pag, PrintWriter out) {
        BufferedReader intermedio;
             try {//abrimos comunicación (buffer)
            	 intermedio= new BufferedReader (new FileReader(System.getProperty("user.dir")+pag));
                 String text_linea="";
                 out.println("HTTP/1.1 200 OK \r\n\\r\\n");
            	 //out.println("Content-Type: text/html\r\n");
            	 //out.println("\r\n");
                 while(text_linea!= null) {
                	 text_linea = intermedio.readLine();
                	 System.out.println(text_linea);
                	 out.println(text_linea);
                    }
             } catch (IOException e) {
            	 System.out.println("Mira si el archivo se encuentra en su sitio porque yo no lo veo");
            	 e.printStackTrace();
             }
       }
    
}