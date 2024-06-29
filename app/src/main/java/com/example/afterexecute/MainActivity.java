package com.example.afterexecute;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyClass myClass = new MyClass(MainActivity.this);

        // Button Thread Test
        findViewById(R.id.ButtonNormalTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LiveMethodObserver.OnAfterExecuteCallback afterExecuteCallback = new LiveMethodObserver.OnAfterExecuteCallback() {
                    @Override
                    public void onAfterExecute(Object object) {
                        LiveDataObject liveDataObject = (LiveDataObject) object;
                        System.out.println("cb id=" + liveDataObject.id + " data=" + liveDataObject.data );
                    }
                };
                myClass.normalTest().after(afterExecuteCallback)
                        .normalTest().after(afterExecuteCallback)
                        .normalTest().after(afterExecuteCallback);


            }
        });

        // Button Normal Test
        findViewById(R.id.ButtonThreadTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LiveMethodObserver.OnAfterExecuteCallback afterExecuteCallback = new LiveMethodObserver.OnAfterExecuteCallback() {
                    @Override
                    public void onAfterExecute(Object object) {
                        LiveDataObject liveDataObject = (LiveDataObject) object;
                        System.out.println("cb id=" + liveDataObject.id + " data=" + liveDataObject.data );
                    }
                };
                myClass.threadTest().after(afterExecuteCallback)
                        .threadTest().after(afterExecuteCallback)
                        .threadTest().after(afterExecuteCallback);


            }
        });

        //Button Timeout Async test
        findViewById(R.id.ButtonTimeoutTestSync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LiveMethodObserver.AfterExecuteSync afterExecuteSync = new LiveMethodObserver.AfterExecuteSync(){
                    @Override
                    public void onExecuteTask(LiveMethodObserver.PostExecute postExecute, int pos) {
                        System.out.println("task id="+postExecute.object.id+" pos="+pos+" msg_data=" +postExecute.object.data+" execute success");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete Sync");
                    }
                };

                myClass.timeoutTest(1000).timeoutTest(1000).afterExecute(afterExecuteSync)
                        .timeoutTest(3000).afterExecuteSync(afterExecuteSync);


            }
        });

        //Button Timeout Sync test
        /**
         *      take care if multiples clicks at same time will cause app to show alerts ui are not responding
         *      but you call run multiples times in your block code
         */
        findViewById(R.id.ButtonTimeoutTestAsync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LiveMethodObserver.AfterExecuteAsync afterExecuteAsync = new LiveMethodObserver.AfterExecuteAsync(){
                    @Override
                    public void onExecuteTask(LiveMethodObserver.PostExecute postExecute, int pos) {
                        System.out.println("task id="+postExecute.object.id+" pos="+pos+" msg_data=" +postExecute.object.data+" execute success");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete Async");
                    }
                };

                myClass.timeoutTest(1000).timeoutTest(1000).afterExecute(afterExecuteAsync)
                        .timeoutTest(3000).afterExecuteAsync(afterExecuteAsync);


            }
        });

    }//[end] onCreate
}

class MyClass extends LiveMethodObserver {

    MyClass(Context context, MutableLiveData liveData) {
        super(context, liveData);
    }

    MyClass(Context context) {
        super(context, new MutableLiveData());
    }

    MyClass threadTest(){
        final PostExecute postExecute = new PostExecute(context1,liveData1,list1);
        postExecute.handle(new Thread(() -> {
            //[START thread test code]
            // todo...
            ThreadSleep(1111);
                               {
                                   System.out.println("start exec");

                                   new Thread2(() -> {

                                       postExecute.run(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") timeout run test of  finished " + System.nanoTime()));
                                       postExecute.runUI(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") timeout runUI interval of  finished " + System.nanoTime()));
                                   }).start();


                                   System.out.println("end exec");
                               }
            //...
            //[END thread test code]
            //you only runUI once a postexecute object at time, this line will ignored
            postExecute.runUI(new LiveDataObject(postExecute.id, "objectId(" + postExecute.id + ") thread test success " + System.nanoTime()));
        }));
        return this;
    }

    MyClass normalTest(){
        final PostExecute postExecute = new PostExecute(context1,liveData1,list1);
        postExecute.handle(() -> {
            //[START normal test code]
             // todo...
            //[END normal test code]
            postExecute.runUI(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") normal test success " + System.nanoTime()));
        });
        return this;
    }

    MyClass timeoutTest(int millis){
        {

            final PostExecute postExecute = new PostExecute(context1,liveData1,getTasks());
            postExecute.addTask(new Thread2(() -> {
                try {

                    new FutureTimer(){{
                        System.out.println("start timeout interval");

                        start(3333, new FutureTimerInterface() {
                            @Override
                            public void onComplete() {

                                System.out.println("end timeout interval");
                            }
                        });
                    }};

                }catch (Exception e){
                    //todo catch error
                }
            }));
            postExecute.setObject(new LiveDataObject(postExecute.id,"objectId(" + postExecute.id + ") timeout interval of " + millis + " finished " + System.nanoTime()));


        }
        return this;
    }


    /**
     * AfterExecute Methods
     */

    MyClass afterExecuteAsync(AfterExecuteAsync afterExecuteAsync){
        afterExecute(afterExecuteAsync);
        return this;
    }

    MyClass afterExecuteSync(AfterExecuteSync afterExecuteSync){
        afterExecute(afterExecuteSync);
        return this;
    }

    MyClass afterExecute(AfterExecuteSync afterExecuteSync){
        runTasks(true, afterExecuteSync);
        return this;
    }

    MyClass afterExecute(AfterExecuteAsync afterExecuteAsync){
        runTasks(false, afterExecuteAsync);
        return this;
    }

    MyClass after(OnAfterExecuteCallback onAfterExecuteCallback){
        PostExecute postExecute = list1.get(list1.size()-1);
        postExecute.afterExecuteCallback = onAfterExecuteCallback;
        return this;
    }

}