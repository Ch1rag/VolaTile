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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExecutor implements Callable<Object> {
	private static ExecutorService service = null;
	private ArrayList<Future<?>> futures = new ArrayList<Future<?>>();
	private Connections Con;
	private Handles Hnd;
	private Sockets Soc;
	private Threads Thd;
	private ProcessBuilderClass pb;

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
			futures.add(service.submit(Con));
			futures.add(service.submit(Soc));
			futures.add(service.submit(Thd));
			futures.add(service.submit(Hnd));
			futures.add(service.submit(pb));

			service.shutdown();
			service.shutdownNow();
			service.isTerminated();
		}
		return futures;
	}

}
