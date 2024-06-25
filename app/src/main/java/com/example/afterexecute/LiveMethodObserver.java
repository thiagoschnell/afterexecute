package com.example.afterexecute;
// Copyright (c) Thiago Schnell | https://github.com/thiagoschnell/afterexecute/blob/main/LICENSE
// Licensed under the MIT License.
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class LiveMethodObserver {
    List<PostExecute> list1 = new ArrayList<>();
    Context context1;
    MutableLiveData liveData1 = null;
    final Handler handler = new Handler(Looper.getMainLooper());
    LiveMethodObserver(Context context, MutableLiveData liveData ){
        this.context1 = context;
        this.liveData1 = liveData;
    }

    interface OnAfterExecuteCallback{
        void onAfterExecute(Object object);
    }

    class PostExecute extends AbstractLiveDataObserver{
        Integer id = null;
        PostExecute(Context context){
            this(context, new MutableLiveData());
        }

        PostExecute(Context context, MutableLiveData liveData){
            this.liveData = liveData;
            setLiveDataObserver((LifecycleOwner) context);
            list1.add(this);
            this.id = new Integer(list1.lastIndexOf(this));
        }

        void object2(LiveDataObject object){
            if(Looper.myLooper()!=null){
                liveData.setValue(object);
            }else{
                liveData.postValue(object);
            }
        }

        void object(LiveDataObject object){
            liveData.setValue(object);
        }

        void run(Runnable runnable){
            handler.post(runnable);
        }

        void thread(Runnable runnable){
            handler.post(() -> {
                new Thread(runnable).start();
            });
        }

        @Override
        protected void setLiveDataObserver(LifecycleOwner lifecycleOwner) {
            //[start] observer
            this.observer = new Observer() {
                @Override
                public void onChanged(Object o) {
                    if(afterExecuteCallback!=null){

                        final LiveDataObject liveDataObject = (LiveDataObject)o;
                        if(liveDataObject.id == id) {
                            afterExecuteCallback.onAfterExecute(o);
                            liveData.removeObserver(observer);
                        }

                    }
                }
            };
            liveData.observe(lifecycleOwner, this.observer);
            //[end] observer
        }
    }

}

abstract class AbstractLiveDataObserver{
    protected Observer observer;
    protected MutableLiveData<LiveDataObject> liveData = null;// new MutableLiveData<LiveDataObject>();
    abstract protected void setLiveDataObserver(LifecycleOwner lifecycleOwner);
    protected LiveMethodObserver.OnAfterExecuteCallback afterExecuteCallback;
}

class LiveDataObject{
    int id;
    Object data;
    LiveDataObject(int id, Object data){
        this.id = id;
        this.data = data;
    }
}