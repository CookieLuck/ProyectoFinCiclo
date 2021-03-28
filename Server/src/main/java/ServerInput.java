import java.util.Scanner;

public class ServerInput extends Thread{

    Main main;

    ServerInput(Main main){
        this.main = main;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Command processor started.");

        while(true){
            String command = sc.nextLine();

            if(command.equals("/getFreeMemory")){

            }

        }

    }
}
