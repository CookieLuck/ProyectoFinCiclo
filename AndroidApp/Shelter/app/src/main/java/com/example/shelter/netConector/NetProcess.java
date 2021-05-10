package com.example.shelter.netConector;


import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.shelter.protocol.Protocol;

public class NetProcess extends Thread{
    Object context;
    NetConector nc;
    Protocol prol;
    String okay;
    String notOkay;
    String timeOut;
    Context contexto;
    public NetProcess(Object context, Context contexto, NetConector nc, Protocol prol, String okay, String notOkay, String timeOut) {
        this.context = context;
        this.contexto = contexto;
        this.nc = nc;
        this.prol = prol;
        this.okay = okay;
        this.notOkay = notOkay;
        this.timeOut = timeOut;
        this.start();
    }

    @Override
    public void run() {
        ContextCompat.getMainExecutor(contexto).execute(()  -> {
            nc.sendPackage(context,prol,okay,notOkay,timeOut);
                });

    }
}
