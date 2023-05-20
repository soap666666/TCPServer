package com.example;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class TCPServer {
    private static final Logger LOGGER=LogManager.getLogger();

    public static void main(String[] args){
        ServerSocket serverSocket;
        ExecutorService threadPool=Executors.newCachedThreadPool();  //可将线程池替换为newSingleThreadExecutor以测试单线程情况下的性能
        LOGGER.info("Server start");
        /*
         * 数据通信服务器
         * 客户端发送命令和数据，服务端执行相应的操作，包括文件读写、数据库读写等
         */
        try{
            serverSocket=new ServerSocket(41265);
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
                        out.println("TCP connection build! Enter END to close the connection."); 

                        boolean open=true;
                        while(open&&in.hasNextLine()){
                            String line=in.nextLine();
                            LOGGER.info("server received: "+line);
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
            LOGGER.info("Server end");
        }
    }
}
