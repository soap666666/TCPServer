package com.example.chat;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static volatile ArrayList<String> clients=new ArrayList<>();
    public static void main(String[] args){
        ServerSocket serverSocket;
        ExecutorService threadPool=Executors.newCachedThreadPool();  //可将线程池替换为newSingleThreadExecutor以测试单线程情况下的性能

        System.out.println("Server start");
        /*
         * 1.多对多聊天室（需要广播？）
         * 2.一对一聊天（两个进程之间通信？）
         */

        try{
            serverSocket=new ServerSocket(10101);
            while(true){
                Socket incoming=serverSocket.accept();
                threadPool.submit(()->{
                    try(
                        InputStream inputStream=incoming.getInputStream();
                        OutputStream outputStream=incoming.getOutputStream();
                        Scanner in=new Scanner(inputStream,StandardCharsets.UTF_8);
                        PrintWriter out=new PrintWriter(new OutputStreamWriter(outputStream,StandardCharsets.UTF_8),true);
                    ) {
                        System.out.println("233");
                        out.println("You have connected to the server! You can enter END to exit."); 

                        boolean open=true;
                        while(open&&in.hasNextLine()){
                            String line=in.nextLine();
                            System.out.println("server received: "+line);
                            if(line.trim().equals("END")){
                                out.println("END");
                                open=false;
                            }
                        }
                        System.out.println("end");
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        }catch(IOException ioException){
            ioException.printStackTrace();
        }finally{
            System.out.println("Server end");
        }
    }
}
