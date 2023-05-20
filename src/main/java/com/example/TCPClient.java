package com.example;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args){
        try (
            Socket socket = new Socket("localhost",41265);
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();
            Scanner in=new Scanner(inputStream,StandardCharsets.UTF_8);
            PrintWriter out=new PrintWriter(new OutputStreamWriter(outputStream,StandardCharsets.UTF_8),true);
        ) {
            out.println("Client connected.");
            out.println("Second Line.");
            
            boolean open=true;
            while(open&&in.hasNextLine()){
                String line=in.nextLine();
                System.out.println("client received: "+line);
                out.println("END");
                if(line.trim().equals("END")) open=false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(OutputStream outputStream,String command, Object data){
        
    }

    private void sendFile(OutputStream outputStream, File file){}
}
