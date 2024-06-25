package com.example.afterexecute;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

public class MainActivity extends AppCompatActivity {
    public static Button buttonNormalTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNormalTest = (Button) findViewById(R.id.ButtonNormalTest);

        MyClass myClass = new MyClass(MainActivity.this);

        findViewById(R.id.ButtonTimeoutTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClass.timeoutTest(5000).after(afterExecuteCallback)
                        .timeoutTest(300).after(afterExecuteCallback)
                        .timeoutTest(1000).after(afterExecuteCallback);
            }
        });

        findViewById(R.id.ButtonNormalTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClass.normalTest().after(afterExecuteCallback)
                        .normalTest().after(afterExecuteCallback)
                        .normalTest().after(afterExecuteCallback);
            }
        });

        findViewById(R.id.ButtonThreadTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClass.threadTest().after(afterExecuteCallback)
                        .threadTest().after(afterExecuteCallback)
                        .threadTest().after(afterExecuteCallback);
            }
        });
    }

    private LiveMethodObserver.OnAfterExecuteCallback afterExecuteCallback = new LiveMethodObserver.OnAfterExecuteCallback() {
        @Override
        public void onAfterExecute(Object object) {
            LiveDataObject liveDataObject = (LiveDataObject) object;
            System.out.println("cb id=" + liveDataObject.id + " data=" + liveDataObject.data );
        }
    };
}

class MyClass extends LiveMethodObserver {

    MyClass(Context context, MutableLiveData liveData) {
        super(context, liveData);
    }

    MyClass(Context context) {
        super(context, new MutableLiveData());
    }

    MyClass threadTest(){
        final PostExecute postExecute = new PostExecute(context1,liveData1);
        postExecute.thread(() -> {
            postExecute.object(new LiveDataObject(postExecute.id, "objectId(" + postExecute.id + ") thread test success " + System.nanoTime()));
        });
        return this;
    }

    MyClass normalTest(){
        final PostExecute postExecute = new PostExecute(context1,liveData1);
        /*
        postExecute.run(() -> {
            postExecute.object(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") normal test success " + System.nanoTime() ) );
        });
         */
        handler.post(() -> {
            postExecute.object(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") normal test success " + System.nanoTime() ) );
        });
        return this;
    }

    MyClass timeoutTest(int millis){
        final PostExecute postExecute = new PostExecute(context1,liveData1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleTimer simpleTimer = new SimpleTimer();
                simpleTimer.setTimeout(millis);
                simpleTimer.startTimer();

                while(simpleTimer.getTime() < simpleTimer.getTimeout()){
                   SystemClock.sleep(100);
                }

                postExecute.liveData.postValue(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") timeout interval of " + millis + " finished " + System.nanoTime() ) );
            }
        }).start();
        return this;
    }

    MyClass after(OnAfterExecuteCallback onAfterExecuteCallback){
        PostExecute postExecute = list1.get(list1.size()-1);
        postExecute.afterExecuteCallback = onAfterExecuteCallback;
        return this;
    }

}