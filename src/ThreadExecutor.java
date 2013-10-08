import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExecutor implements Runnable {
	private static ExecutorService service = null;
	private static volatile Future threadResult;
	private static volatile Future processResult;
/*
	private Connections conn=new Connections();
	private Sockets socket=new Sockets();
	private Threads thread=new Threads();*/
	private Thread td=new Thread();
	
	ThreadExecutor(){
		
	}
	ThreadExecutor(Thread td){
		this.td=td;
	}
	public void executor() throws InterruptedException,IOException{
		service = Executors.newCachedThreadPool();
			try{
				run();
				
				//Thread.sleep(1000);
			} catch (Exception e) {
				System.err.println("Caught exception: " + e.getMessage());
			}
		}	
	public void run() { // run the services
	    {
	    	threadResult=service.submit(new Threads());
	    	service.submit(new Sockets());
	    	service.submit(new Connections());
	    	
	         try {
				if(threadResult.get()==null || threadResult.isDone()
						|| threadResult.isCancelled()){
					//td.start(); 
					processResult=service.submit(td);
					service.shutdown();
					service.shutdownNow();
				 }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       }
	    
	    }
	
	}
	   
	
	 
	/*public  void checkTasks() throws Exception {
			if (taskTwoResults == null
					|| taskTwoResults.isDone()
					|| taskTwoResults.isCancelled()){
				taskTwoResults = service.submit(thread);
			}
			if (taskOneResults == null
					|| taskOneResults.isDone()
					|| taskOneResults.isCancelled()){  
				
				taskTwoResults = service.submit(conn);
				taskTwoResults = service.submit(socket);
					
			} 	
			
	}*/
	
