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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class ThreadExecutor implements Runnable {
	private static ExecutorService service = null;
	private Future<?> threadResult;
	private Future<?> processResult;
	private Future<?> hndResults;
	private Future<?> socResults;
	private Future<?> conResults;
	private Thread td=new Thread();
	private Thread tdCon=new Thread();
	private Thread tdSoc=new Thread();
	private Thread tdThd=new Thread();
	private Thread tdHnd=new Thread();
	
	/*ThreadExecutor(){
		
	}*/
	ThreadExecutor(Thread td){
		this.td=td;
	}
	
	ThreadExecutor(Thread tdCon,Thread tdHnd,Thread tdSoc,Thread tdThd){
		this.tdCon=tdCon;
		this.tdSoc=tdSoc;
		this.tdThd=tdThd;
		this.tdHnd=tdHnd;
	}
	
	public Future<?> executor() throws InterruptedException,IOException{
		service = Executors.newCachedThreadPool();
			try{
				call();
			} catch (Exception e) {
				System.err.println("Caught exception: " + e.getMessage());
			}
			return processResult;
		}	
	public Future<?> call() { // run the services
	    {
	    	threadResult=service.submit(tdThd);
	    	hndResults=service.submit(tdHnd);
	    	socResults=service.submit(tdSoc);
	    	conResults=service.submit(tdCon);
	    	    	
	         try {
				if((threadResult.get()==null && hndResults.get()==null)|| (threadResult.isDone() && hndResults.isDone())
						|| (threadResult.isCancelled()&&hndResults.isCancelled())){
					 
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
	   
	    return processResult;
	    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	}

	   
	
	 
	
