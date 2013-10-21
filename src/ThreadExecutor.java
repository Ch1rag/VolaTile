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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class ThreadExecutor implements Callable<Object> {
	private static ExecutorService service = null;
	
    private ArrayList<Future<?>> futures=new ArrayList<Future<?>>();
	
	private Thread td=new Thread();
	
	private Connections Con;
	private Handles Hnd;
	private Sockets Soc;
	private Threads Thd;
	//private Callable<?> call=new Connections();
	

	/*ThreadExecutor(){

	}*/
	ThreadExecutor(Thread td){
		this.td=td;
	}

	ThreadExecutor(Connections Con,Handles Hnd,Sockets Soc,Threads Thd){
		this.Con=Con;
		this.Soc=Soc;
		this.Thd=Thd;
		this.Hnd=Hnd;
	}

	

	public ArrayList<Future<?>> executor() throws InterruptedException,IOException{
		service = Executors.newCachedThreadPool();
		try{
			call();
		} catch (Exception e) {
			System.err.println("Caught exception: " + e.getMessage());
		}
		return futures;
	}	
	public ArrayList<Future<?>> call() throws InterruptedException, ExecutionException { // run the services
		{
		
			/*threadResult=service.submit(tdThd);
	    	hndResults=service.submit(tdHnd);
	    	socResults=service.submit(tdSoc);
	    	conResults=service.submit(tdCon);
	    	*/
	    	//futures.add(service.submit(tdThd));
			futures.add(service.submit(Con));
			futures.add(service.submit(Soc));
			futures.add(service.submit(Thd));
	    	futures.add(service.submit(Hnd));
	    	futures.add(service.submit(td));
	    	
	    	service.shutdown();
			service.shutdownNow();
			service.isTerminated();
			
			
			
				/*if((futures.get(3).get()==null && futures.get(3).isDone()==true)){

					futures.add(service.submit(td));
					service.shutdown();
					service.shutdownNow();
					service.isTerminated();
				 }*/

			
			
			/* try {
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
			}*/
	       }

			return futures;
		}
	

	/*@Override
	public void run() {
		// TODO Auto-generated method stub

	}*/

}





