package com.example.worker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity{

    Button cancelWork;
    OneTimeWorkRequest randomTextRequest;
    OneTimeWorkRequest reverseTextRequest;
    TextView textView;

    public static  final String WORKER_TAG = "myWorkerTag";
    public static  final String WORKER_TAG_1 = "myWorkerTag1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        cancelWork = findViewById(R.id.cancelWork);
        cancelWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WorkManager.getInstance(MainActivity.this).cancelWorkById(request.getId());
                //WorkManager.getInstance(MainActivity.this).cancelWorkById(WORKER_TAG);
                WorkManager.getInstance(MainActivity.this).cancelAllWorkByTag(WORKER_TAG_1);

            }
        });
        doWork();
    }

    public void doWork(){
//        Constraints constaraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.UNMETERED)
//                //.setRequiresCharging(true)
//                .build();

        randomTextRequest = new OneTimeWorkRequest.Builder(MyTextWorker.class).build();
        reverseTextRequest = new OneTimeWorkRequest.Builder(MyUpdateWorker.class).build();

               //.addTag(WORKER_TAG_1)
             //  .setConstraints(constaraints)
//               .addTag(WORKER_TAG)
//               .setInitialDelay(5, TimeUnit.SECONDS)
//               .build();

       // request = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES).addTag(WORKER_TAG_1).build();
        WorkManager.getInstance(this)
                .beginWith(randomTextRequest)
                .then(reverseTextRequest)
                .enqueue();
                //.enqueue(request);
        LiveData<WorkInfo> info = WorkManager.getInstance(this).getWorkInfoByIdLiveData(reverseTextRequest.getId());
        info.observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                //Log.d(MyWorker.TAG, "onChange: " + Thread.currentThread().getName() + " " + workInfo.getState());
                String str = workInfo.getOutputData().getString(MyUpdateWorker.RESUL_KEY);
                textView.setText(str);
            }
        });
    }
}