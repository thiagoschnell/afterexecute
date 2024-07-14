# afterexecute

Create a example class for this example

    class MyClass extends LiveMethodObserver {

        MyClass(Context context) {
            super(context, new MutableLiveData());
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


add your method, in this example our method example is myMethodToCall

     MyClass myMethodToCall(){        

            final PostExecute postExecute = new PostExecute(context1,liveData1,getTasks());
            postExecute.addTask(new Thread2(() -> {

               //todo...
               
               //add you method code here 
               
            }));

            //optional, set data to callback after execute the method, in this example the data go as message text
            postExecute.setObject(new LiveDataObject(postExecute.id,"method execute success");
        
        return this;
    }

Create the class, in this example our class is MyClass


        MyClass myClass = new MyClass(MainActivity.this);
        
Choose Sync Callback or Async Callback as you need 


                //Async callback
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


                //Sync callback
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

Call method async     

                myClass.myMethodToCall().myMethodToCall().afterExecute(afterExecuteAsync)

Call method sync     

                myClass.myMethodToCall().myMethodToCall().afterExecute(afterExecuteSync)


Call method custom

            myClass
                .myMethodToCall().myMethodToCall().afterExecute(afterExecuteAsync)
                .myMethodToCall().afterExecute(afterExecuteSync)
                .myMethodToCall().myMethodToCall().myMethodToCall().myMethodToCall().afterExecuteAsync(afterExecuteAsync)
                .myMethodToCall().myMethodToCall().afterExecuteSync(afterExecuteSync)
                  .myMethodToCall();
