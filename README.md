# afterexecute

Create a example class for this sample demo

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


The following is an example shows how to create a method in MyClass

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

To Create a new MyClass

        MyClass myClass = new MyClass(MainActivity.this);
        
Then choose Sync Callback or Async Callback as you need 


                //Sync callback
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


                //Async callback
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

# Examples of use

See examples that you can use to execute methods and handle the afterExecute callback

async:     

                myClass.myMethodToCall().myMethodToCall().afterExecute(afterExecuteAsync)

sync:     

                myClass.myMethodToCall().myMethodToCall().afterExecute(afterExecuteSync)


custom:

            myClass
                .myMethodToCall().myMethodToCall().afterExecute(afterExecuteAsync)
                .myMethodToCall().afterExecute(afterExecuteSync)
                .myMethodToCall().myMethodToCall().myMethodToCall().myMethodToCall().afterExecuteAsync(afterExecuteAsync)
                .myMethodToCall().myMethodToCall().afterExecuteSync(afterExecuteSync)
                  .myMethodToCall();
