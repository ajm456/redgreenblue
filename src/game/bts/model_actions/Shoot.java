// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/07/2016 15:32:23
// ******************************************************* 
package game.bts.model_actions;

/** ModelAction class created from MMPM action Shoot. */
public class Shoot extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of Shoot. */
	public Shoot(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a game.bts.execution_actions.Shoot task that is able to run this
	 * task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new game.bts.execution_actions.Shoot(this, executor, parent);
	}
}