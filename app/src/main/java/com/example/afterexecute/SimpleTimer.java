package com.example.afterexecute;
// Copyright (c) Thiago Schnell | https://github.com/thiagoschnell/afterexecute/blob/main/LICENSE
// Licensed under the MIT License.
public class SimpleTimer {
    private long startTime = 0;
    private long timeout = 0;

    private long getTickCount() {
        return System.currentTimeMillis();
    }

    protected long getTime(){
        return getTickCount() - startTime;
    }

    protected void startTimer(){
        startTime = getTickCount();
    }

    protected long getTimeout() {
        return timeout;
    }

    protected void setTimeout(long timeout){
        if(timeout > 0){
            this.timeout = timeout;
            //startTimer();
        }else if(timeout==0){
            this.timeout = 0;
        }else{
            throw new Error("INVALID TIMEOUT VALUE (" + timeout + ")");
        }
    }

    protected long getRemaingTime() {
        if(timeout>0) {
            return getTime();
        }else {
            return 0;
        }
    }
}
