package core;

import core.model.ProtectoraEntity;
import core.model.UsuarioEntity;

import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    Socket s;
    boolean IsProtectora;
    ProtectoraEntity protectora;
    UsuarioEntity user;

    public void write(String s){
        try {
            PrintWriter output = new PrintWriter(this.s.getOutputStream(), true);
            output.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String recieve(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    Client(Socket s){
        this.s = s;
    }



    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public boolean isProtectora() {
        return IsProtectora;
    }

    public UsuarioEntity getUser() {
        return user;
    }

    public void setUser(UsuarioEntity user) {
        this.user = user;
    }

    public void setProtectora(boolean protectora) {
        IsProtectora = protectora;
    }

    public ProtectoraEntity getProtectora() {
        return protectora;
    }

    public void setProtectora(ProtectoraEntity protectora) {
        this.protectora = protectora;
    }
}
