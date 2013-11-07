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
<<<<<<< HEAD
<<<<<<< HEAD
 * @author Chirag Barot
 * @version 1.0
=======
=======
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
 * <p>
 * @
 * PUBLIC FEATURES:
 * // Constructors
 * ThreadExecutor(Connections Con, Handles Hnd, Sockets Soc, Threads Thd, ProcessBuilderClass pb)
 *  <p>
 * // Methods
 * ArrayList<Future<?>> call()
 * ArrayList<Future<?>> executor()
 * <p>
 * @author	Chirag Barot
 * @version	1.0
 *
 * 20131107 Updated headers and comments - AN
<<<<<<< HEAD
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
=======
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
 */
public class ThreadExecutor implements Callable<Object> {
	private static ExecutorService service = null;
	private ArrayList<Future<?>> futures = new ArrayList<Future<?>>();	// collection of futures
	private Connections Con;	// our connections 
	private Handles Hnd;		// our handles
	private Sockets Soc;		// our sockets
	private Threads Thd;		// threads
	private ProcessBuilderClass pb;	

	/**
	 * The overloaded constructor for Thread Executor Class.
	 * 
	 * @param Con
	 *            Connection Process
	 * @param Soc
	 *            Sockets Process
	 * @param Thd
	 *            Thread Process
	 * @param Hnd
	 *            Handles Process
	 * @param pb
	 *            Process Builder (pslist)
	 */
	ThreadExecutor(Connections Con, Handles Hnd, Sockets Soc, Threads Thd,
			ProcessBuilderClass pb) {
		this.Con = Con;
		this.Soc = Soc;
		this.Thd = Thd;
		this.Hnd = Hnd;
		this.pb = pb;
	}
	/**
	 * executor
	 * <p>
	 * 
	 * 
	 * @return list of ArrayList<Future<?>> 
	 * @throws InterruptedException 
	 * @throws IOException if problem reading files
	 */
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

	/**
	 * call
	 * <p>
	 *
	 *
	 * @return list of futures in an arrayList.
	 * @throws InterruptedException
	 * @throws ExecutionException
	
	 */
	public ArrayList<Future<?>> call() throws InterruptedException,
			ExecutionException { // run the services
		{
			futures.add(service.submit(Thd));// 0
			futures.add(service.submit(Hnd));// 1
			futures.add(service.submit(Soc));// 2
			futures.add(service.submit(Con));// 3
			futures.add(service.submit(pb)); // 4
            //Shutdown all services..
			service.shutdown();
			service.shutdownNow();
			service.isTerminated();
		}
		return futures;		// return collection of futures.
	}

}
