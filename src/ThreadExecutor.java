import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExecutor {
	private static ExecutorService service = null;
	private static volatile Future taskOneResults = null;
	private static volatile Future taskTwoResults = null;
	private LsofThread lsof=new LsofThread();
	
	public void executor() throws InterruptedException{
		service = Executors.newCachedThreadPool();
			try{
				checkTasks();
				Thread.sleep(1000);
			} catch (Exception e) {
				System.err.println("Caught exception: " + e.getMessage());
			}
		}	
	public  void checkTasks() throws Exception {
			if (taskTwoResults == null
					|| taskTwoResults.isDone()
					|| taskTwoResults.isCancelled()){
				taskTwoResults = service.submit(lsof);
				service.shutdown();
			}
			else{
				System.out.println("Please wait.....");
			}
			if (taskOneResults == null
					|| taskOneResults.isDone()
					|| taskOneResults.isCancelled()){   	
				//taskOneResults = service.submit(lt);	
			} 		
	}
	
}