import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**    
 * The Thread Executor class is used to execute the processes consecutively
 * 
 * @author	Chirag Barot
 * @version	1.0
 */
public class ThreadExecutor implements Callable<Object> {
	private static ExecutorService service = null;
	private ArrayList<Future<?>> futures = new ArrayList<Future<?>>();
	private Connections Con;
	private Handles Hnd;
	private Sockets Soc;
	private Threads Thd;
	private ProcessBuilderClass pb;

	/**
     * The overloaded constructor for Thread Executor Class.
	 *
	 * @param	Con			Connection Process
	 * @param	Soc			Sockets Process
	 * @param	Thd			Thread Process
	 * @param	Hnd			Handles Process
	 * @param 	pb			Process Builder (pslist)
     */
	ThreadExecutor(Connections Con, Handles Hnd, Sockets Soc, Threads Thd,
			ProcessBuilderClass pb) {
		this.Con = Con;
		this.Soc = Soc;
		this.Thd = Thd;
		this.Hnd = Hnd;
		this.pb = pb;
	}

	public ArrayList<Future<?>> executor() throws InterruptedException,
			IOException {
		service = Executors.newCachedThreadPool();
		try {
			call();
		} catch (Exception e) {
			System.err.println("Caught exception: " + e.getMessage());
		}
		return futures;
	}

	public ArrayList<Future<?>> call() throws InterruptedException,
			ExecutionException { // run the services
		{
			futures.add(service.submit(Thd));//0
			futures.add(service.submit(Hnd));//1
			futures.add(service.submit(Soc));//2
			futures.add(service.submit(Con));//3
			futures.add(service.submit(pb)); //4
			
			service.shutdown();
			service.shutdownNow();
			service.isTerminated();
		}
		return futures;
	}

}
