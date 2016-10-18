// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/07/2016 15:32:24
// ******************************************************* 
package game.bts.model_conditions;

/** ModelCondition class created from MMPM condition PlayerInLineOfSight. */
public class PlayerInLineOfSight extends
		jbt.model.task.leaf.condition.ModelCondition {

	/** Constructor. Constructs an instance of PlayerInLineOfSight. */
	public PlayerInLineOfSight(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a game.bts.execution_conditions.PlayerInLineOfSight task that is
	 * able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new game.bts.execution_conditions.PlayerInLineOfSight(this,
				executor, parent);
	}
}