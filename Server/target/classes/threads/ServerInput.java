package threads;


import core.ServerCore;

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

        }

    }
}
