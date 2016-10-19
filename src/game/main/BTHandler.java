package game.main;

import java.util.ArrayList;
import java.util.Iterator;

import jbt.execution.core.IBTExecutor;
import jbt.execution.core.ExecutionTask.Status;

public class BTHandler implements Runnable 
{
	private ArrayList<IBTExecutor> treeExecutors;
	private Iterator<IBTExecutor> treeExecutorsIterator;
	
	public BTHandler(ArrayList<IBTExecutor> treeExecutors) {
		this.treeExecutors = treeExecutors;
		treeExecutorsIterator = treeExecutors.iterator();
	}
	
	public synchronized void add(IBTExecutor e) {
		treeExecutors.add(e);
	}
	
	@Override
	public void run() {
		while(true) {
			for(IBTExecutor i : treeExecutors) {
				if(i.getStatus() == Status.RUNNING) {
					i.tick();
				}
			}
		}
		
		/*while(treeExecutorsIterator.hasNext()) {
			System.out.println("!");
			IBTExecutor i = treeExecutorsIterator.next();
			if(i.getStatus() == Status.RUNNING) {
				i.tick();
			} else {
				//treeExecutors.remove(i);
			}
		}*/
	}
}