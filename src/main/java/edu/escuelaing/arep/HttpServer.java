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
import java.io.*;

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
        
        double cuadrado = 0;
        
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if(inputLine.contains("?")) {
                String[] valorCompleto = inputLine.split("=");
                String[] numero = valorCompleto[1].split(" ");
                System.out.println(numero[0]);
                cuadrado = Math.pow(Double.parseDouble(numero[0]),2);

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
                + cuadrado
                + "</body>\n"
                + "</html>\n" + inputLine;
        out.println(outputLine);
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
}