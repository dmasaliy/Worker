package com.example.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MyUpdateWorker extends Worker {
    public static final String RESUL_KEY  = "result";

    public MyUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
      String data = getInputData().getString(MyTextWorker.UUID_KEY);

     String reversedStr = new StringBuilder(data).reverse().toString();
      Data reversed = new Data.Builder().putString(RESUL_KEY, reversedStr).build();
      return Result.success(reversed);
    }
}
