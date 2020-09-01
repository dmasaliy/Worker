package com.example.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MyTextWorker extends Worker {

    public static final String UUID_KEY = "Key";

    public MyTextWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
        String str = UUID.randomUUID().toString();

        Data data = new Data.Builder().putString(UUID_KEY, str).build();
        return Result.success(data);
    }
}
