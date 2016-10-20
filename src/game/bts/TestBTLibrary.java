// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/20/2016 11:06:23
// ******************************************************* 
package game.bts;

/**
 * BT library that includes the trees read from the following files:
 * <ul>
 * <li>C:/Users/Andrew/OneDrive/Docs/misc/java_nonsense/RedGreenBlue/
 * behaviour_trees\ComputeAndMove.xbt</li>
 * </ul>
 */
public class TestBTLibrary implements jbt.execution.core.IBTLibrary {
	/**
	 * Tree generated from file
	 * C:/Users/Andrew/OneDrive/Docs/misc/java_nonsense/
	 * RedGreenBlue/behaviour_trees\ComputeAndMove.xbt.
	 */
	private static jbt.model.core.ModelTask test;

	/* Static initialization of all the trees. */
	static {
		test = new jbt.model.task.decorator.ModelRepeat(
				null,
				new jbt.model.task.composite.ModelSequence(
						null,
						new game.bts.model_actions.ComputeDestination(null),
						new game.bts.model_actions.Move(null, null, "unitDestination")));

	}

	/**
	 * Returns a behaviour tree by its name, or null in case it cannot be found.
	 * It must be noted that the trees that are retrieved belong to the class,
	 * not to the instance (that is, the trees are static members of the class),
	 * so they are shared among all the instances of this class.
	 */
	public jbt.model.core.ModelTask getBT(java.lang.String name) {
		if (name.equals("test")) {
			return test;
		}
		return null;
	}

	/**
	 * Returns an Iterator that is able to iterate through all the elements in
	 * the library. It must be noted that the iterator does not support the
	 * "remove()" operation. It must be noted that the trees that are retrieved
	 * belong to the class, not to the instance (that is, the trees are static
	 * members of the class), so they are shared among all the instances of this
	 * class.
	 */
	public java.util.Iterator<jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>> iterator() {
		return new BTLibraryIterator();
	}

	private class BTLibraryIterator
			implements
			java.util.Iterator<jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>> {
		static final long numTrees = 1;
		long currentTree = 0;

		public boolean hasNext() {
			return this.currentTree < numTrees;
		}

		public jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask> next() {
			this.currentTree++;

			if ((this.currentTree - 1) == 0) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"test", test);
			}

			throw new java.util.NoSuchElementException();
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
}
