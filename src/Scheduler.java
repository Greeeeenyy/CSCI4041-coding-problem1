import java.util.ArrayList;
import java.util.Comparator;

/**
 * This is a greedy algorithm which solves the scheduling problem for classes at UMN.
 * Each class has the structure of an ArrayList<String[]> and each String[] is structured
 * as [start, end] where both are a string of time in terms of military time
 * (e.g. 8:37am is 08:37, but 4:15pm is 16:15) and start is a time before end. This
 * algorithm will make a new list of classes that don't conflict (a conflict is
 * described as the start time of a class being less than 15 minutes after the
 * end time of another class) with each other and contains as many classes as it
 * possible can with the given classes. As a note it is assumed that 8:00am (08:00) is the
 * earliest time a class can start and 10:00pm (22:00) is the latest.
 */
public class Scheduler {

  private ArrayList<String[]> tasks =  new ArrayList<String[]>(); // requiring an ArrayList since some tasks may not be included in the schedule
  private ArrayList<int[]> intTasks = new ArrayList<int[]>(); // save an int version of the times

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

  /**
   * Converts the tasks from Strings to their integer versions
   * (e.g. 08:30 becomes 510 because 8 hours and 30 minutes is
   * 510 minutes), this makes it easier to sort and check for
   * collisions. Uses its helper method to add new int[]s to
   * intTasks.
   */
  public void convertToInt() {
    intTasks = new ArrayList<int[]>();
    for (String[] inner : tasks) {
      intTasks.add(convertToIntHelper(inner));
    }
  }

  /**
   * Helper method for convertToInt that creates the intTask
   * for the new ArrayList<int[]> from the original String[]
   * task.
   *
   * @param task original String[] task with times as Strings
   */
  private int[] convertToIntHelper(String[] task) {
    int[] intTask = new int[2]; // only 2 times (start and end) so it'll need a length of 2
    String startTime = task[0];
    String endTime = task[1];
    String[] startNums = startTime.split(":");
    String[] endNums = endTime.split(":");
    // ^ these contain the hour in the 0th index and the minutes in the 1st index
    int startTotal = Integer.parseInt(startNums[0]) * 60 + Integer.parseInt(startNums[1]);
    int endTotal = Integer.parseInt(endNums[0]) * 60 + Integer.parseInt(endNums[1]);
    // ^ calculating time by parsing the Strings into ints then multiplying the hour by 60 and adding the minutes
    intTask[0] = startTotal;
    intTask[1] = endTotal;
    return intTask;
  }

  /**
   * Method to convert from int tasks back into String tasks,
   * this method will be used after the algorithm finds the
   * tasks to use in the scheduling problem to convert them
   * back into a more readable form. Uses its helper method.
   */
  public void convertToString() {
    if (intTasks.size() == 0) { // if there's no int representation of the data it'll wipe the schedule out
      return;
    }
    tasks = new ArrayList<String[]>();
    for (int[] inner : intTasks) {
      tasks.add(convertToStringHelper(inner));
    }
  }

  /**
   * Helper method for String conversion, it works the exact
   * same way as its int counterpart, just the reverse way.
   *
   * @param intTask int[] task with times as ints
   */
  private String[] convertToStringHelper(int[] intTask) {
    String[] stringTask = new String[2];
    int startTime = intTask[0];
    int endTime = intTask[1];
    int startHourInt = startTime / 60;
    int startMinInt = startTime % 60;
    int endHourInt = endTime / 60;
    int endMinInt = endTime % 60;
    String startHourString;
    String startMinString;
    String endHourString;
    String endMinString;
    if (startHourInt < 10) {
      startHourString = "0" + startHourInt;
    }
    else {
      startHourString = "" + startHourInt;
    }
    if (startMinInt < 10) {
      startMinString = "0" + startMinInt;
    }
    else {
      startMinString = "" + startMinInt;
    }
    if (endHourInt < 10) {
      endHourString = "0" + endHourInt;
    }
    else {
      endHourString = "" + endHourInt;
    }
    if (endMinInt < 10) {
      endMinString = "0" + endMinInt;
    }
    else {
      endMinString = "" + endMinInt;
    }
    String stringStart = startHourString + ":" + startMinString;
    String stringEnd = endHourString + ":" + endMinString;
    stringTask[0] = stringStart;
    stringTask[1] = stringEnd;
    return stringTask;
  }

  /**
   * Sorts the tasks in order by converting tasks to their int
   * version and sorts by end time in nondecreasing order.
   */
  public void sortTasks() {
    convertToInt(); // get the int version
    intTasks.sort(Comparator.comparingInt(a -> a[1])); // sorts by the second element (the end time) of the inner arrays
  }

  /**
   * The method that actually puts together the schedule with
   * the most tasks and changes both intTasks and tasks to be
   * scheduled (so this action cannot be reversed, but they
   * are both entirely different objects so the original list
   * of tasks still exists). It starts by sorting then it
   * immediately chooses the first task in the list then
   * chooses the next viable task in the list (viable meaning
   * no collisions) until there are no more tasks to check.
   */
  public void schedule() {
    sortTasks(); // this method sorts the int version, so we can immediately start the algorithm now
    ArrayList<int[]> scheduleList = new ArrayList<int[]>(); // new scheduled list variable
    int endToCompare = intTasks.get(0)[1]; // save the end time of the first element to compare to the start of the next task until a task works
    scheduleList.add(intTasks.get(0)); // add the first task
    for (int i = 1; i < intTasks.size(); i++) {
      int startToCompare = intTasks.get(i)[0];
      if (startToCompare >= (endToCompare + 15)) { // check if the start of the current task is at least 15 minutes after the end of the previous task chosen
        scheduleList.add(intTasks.get(i));
        endToCompare = intTasks.get(i)[1]; // new end time to compare with
      }
    }
    intTasks = scheduleList;
    convertToString(); // make the String version for applicability
  }

  /**
   * Getter that returns the tasks ArrayList.
   */
  public ArrayList<String[]> getTasks() {
    return tasks;
  }

  /**
   * Getter for testing that returns the intTasks ArrayList.
   */
  public ArrayList<int[]> getIntTasks() {
    return intTasks;
  }
}