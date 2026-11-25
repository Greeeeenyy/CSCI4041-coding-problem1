import java.util.ArrayList;

/**
 * This is a greedy algorithm which solves the scheduling problem for classes at UMN.
 * Each class has the structure of an ArrayList<String[]> and each String[] is structured
 * as [start, end] where both are a string of time in terms of military time
 * (e.g. 8:37am is 8:37, but 4:15pm is 16:15) and start is a time before end. This
 * algorithm will make a new list of classes that don't conflict with each other
 * and contains as many classes as it possible can with the given classes. As a note
 * it is generally assumed that 8:00am is the earliest time a class can start and
 * 10:00pm is the latest (though the end time won't affect the algorithm but the start
 * time assumption will).
 */
public class Scheduler {

  private ArrayList<String[]> tasks =  new ArrayList<String[]>(); // requiring an ArrayList since some tasks may not be included in the schedule

  /**
   * Constructor for the Scheduler which makes a deepcopy so the original
   * task list won't be changed.
   *
   * @param tasks list of tasks to schedule
   */
  public Scheduler(ArrayList<String[]> tasks) {
    for (String[] inner : tasks) {
      this.tasks.add(inner.clone()); // looping to deepcopy
    }
  }

  public void schedule() {

  }

  /**
   * Getter for testing that returns the tasks list.
   */
  public ArrayList<String[]> getTasks() {
    return tasks;
  }
}