/**    
 * A concise description of the class (including invariants if any)
 *    
 * @
 * PUBLIC FEATURES:
 * // Constructors
 *    each constructor should be listed here
 * // Methods
 *    The signature and a brief comment (if needed)
 *    In alphabetic order
 *
 * COLLABORATORS:
 *    Names of classes (other than System and java.lang)
 *
 * MODIFIED:
 * @version number, date last changed and author’s initials &/or what changed (very brief)
 * @author    (can have multiple authors)
 * http://swinbrain.ict.swin.edu.au/wiki/Swinburne_Java_Coding_Standard
 */
 
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExecutor implements Runnable {
	private static ExecutorService service = null;
	private static volatile Future threadResult;
	private static volatile Future processResult;
	private Future future;
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
	    	future=service.submit(new Handles());
	    	service.submit(new Sockets());
	    	service.submit(new Connections());
	    	
	    	
	         try {
				if((threadResult.get()==null && future.get()==null)|| (threadResult.isDone() && future.isDone())
						|| (threadResult.isCancelled()&&future.isCancelled())){
					 
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
	   
	
	 
	
