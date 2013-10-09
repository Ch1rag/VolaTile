import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExecutor implements Runnable {
	private static ExecutorService service = null;
	private static volatile Future threadResult;
	private static volatile Future processResult;
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
			} catch (Exception e) {
				System.err.println("Caught exception: " + e.getMessage());
			}
		}	
	public void run() { // run the services
	    {
	    	threadResult=service.submit(new Threads());
	    	service.submit(new Sockets());
	    	service.submit(new Connections());
	    	service.submit(new Handles());
	    	
	         try {
				if(threadResult.get()==null || threadResult.isDone()
						|| threadResult.isCancelled()){
					 
					processResult=service.submit(td);
					service.shutdown();
					service.shutdownNow();
					service.isTerminated();
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
	   
	
	 
	
