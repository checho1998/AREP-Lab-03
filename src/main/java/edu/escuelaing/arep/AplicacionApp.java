/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

/**
 *
 * @author sergio.nunez
 */
public class AplicacionApp {
    
    public static void main(String[] args) throws Exception {
        Socket miSoket = null;

        PrintWriter out = null;
        

        BufferedReader in = null;

        try  {
            
            miSoket =  new  Socket("127.0.0.1", 35000);
           
            out =  new  PrintWriter(miSoket.getOutputStream(),  true); in =  new  BufferedReader(new  InputStreamReader(echoSocket.getInputStream()));
           
            }  catch  (Exception e)  {
           
            System.err.println("No sabe el puerto o el host");

            }
        
    }
}
