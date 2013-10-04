package kunday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class TestThread {
	private List<String> list = new ArrayList<String>();
	public TestThread() {
		list.add("python");
		list.add("vol.py");
		list.add("--profile=Mac10_8_4_64bitx64");
		list.add("-f");
		list.add("/Users/chiragbarot/Mem/ram_dump.mach-o");
	}
	
	
	private List getProcessBuilderList(String type) {
		if ("LSOfBuilder" == type) {
			return Arrays.asList(list).add("-c something")
		} else if ("NetStatBuilder" == type) {
			return Arrays.asList(list).add("-c something")
		}
		return null;
	}
	
	public void run() throws InterruptedException{
		ThreadedLsofProcessBuilder lsBuilder = new ThreadedLsofProcessBuilder(getProcessBuilderList("LSOfBuilder));
		ThreadedNetstatProcessBuilder nsBuilder = new ThreadedNetstatProcessBuilder();
		List<ThreadedProcessBuilder> parallelTasks = Arrays.asList(lsBuilder, nsBuilder);

		ExecutorService service = Executors.newCachedThreadPool();
		List<Future<Object>> threadedJobs = service.invokeAll(parallelTasks);
	}
	
}
