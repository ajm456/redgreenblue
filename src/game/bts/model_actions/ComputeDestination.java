// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/07/2016 15:32:24
// ******************************************************* 
package game.bts.model_actions;

/** ModelAction class created from MMPM action ComputeDestination. */
public class ComputeDestination extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of ComputeDestination. */
	public ComputeDestination(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a game.bts.execution_actions.ComputeDestination task that is able
	 * to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new game.bts.execution_actions.ComputeDestination(this,
				executor, parent);
	}
}