package com.lmhscodingclub.typeracer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) throws Exception {
    try (ServerSocket serverSocket = new ServerSocket(Integer.valueOf(System.getenv().getOrDefault("PORT", "8080")))) {
      System.out.println("Server started.\nListening for messages.");

      while (true) {
        try (Socket client = serverSocket.accept()) {
          System.out.println("Debug: got new message " + client.toString());

          InputStreamReader isr = new InputStreamReader(client.getInputStream());
          BufferedReader br = new BufferedReader(isr);

          // Read the first request from the client
          StringBuilder request = new StringBuilder();
          String line = br.readLine();

          System.out.println(line);

          while (!line.isBlank()) {
            request.append(line + "\r\n");
            line = br.readLine();
          }

          String firstLine = request.toString().split("\n")[0];
          String resource = firstLine.split(" ")[1];
          System.out.println(resource);

          // Respond
          OutputStream clientOutput = client.getOutputStream();
          clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());

          if (resource.equals("/varun")) {
            clientOutput.write("\r\n".getBytes());
            clientOutput.write("404 Not Found".getBytes());
          } else if (resource.equals("/hello")) {
            clientOutput.write("Content-Type: application/json, charset=utf-8\r\n".getBytes());
            clientOutput.write(("\r\n".getBytes()));
            clientOutput.write("{\"response\": \"Hello world\"}".getBytes());
          } else {
            clientOutput.write("\r\n".getBytes());
            clientOutput.write("What ya lookin' for?".getBytes());
          }

          System.out.println("---REQUEST---");
          System.out.println(request);

          clientOutput.flush();

          client.close();
        }
      }
    }
  }
}