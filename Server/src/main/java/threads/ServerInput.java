package threads;


import core.ServerCore;

import java.sql.SQLOutput;
import java.util.Scanner;

public class ServerInput<Start> extends Thread{

    ServerCore serverCore;

    public ServerInput(ServerCore serverCore){
        this.serverCore = serverCore;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Command processor started.");

        while(true){
            String command = sc.nextLine();

            if(command.equalsIgnoreCase("/getFreeMemory")){
                System.out.println(serverCore.getMemoryLeft());
            }

            if(command.equalsIgnoreCase("/help")){
                utils.PrintableFunctions.printCommandsHelp();
            }

            if(command.equalsIgnoreCase("/stop")){
                System.out.println("Closing server...");
                System.exit(-1);
            }

            if(command.equalsIgnoreCase("/pause")){
                if(!serverCore.paused){
                    serverCore.paused = true;
                    System.out.println("Server paused.");
                }else{
                    System.out.println("Server already paused.");
                }

            }

            if(command.equalsIgnoreCase("/resume")){
                if(serverCore.paused){
                    serverCore.paused = false;
                    System.out.println("Server resumed.");
                }else{
                    System.out.println("Server is already working.");
                }

            }

        }

    }
}
