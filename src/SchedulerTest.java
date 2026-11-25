import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

/**
 * Testing for the Scheduling algorithm
 */
public class SchedulerTest {

  private ArrayList<String[]> testTasks;
  private String[] task1 = {"11:00", "12:15"};
  private String[] task2 = {"13:00", "14:00"};
  private String[] task3 = {"08:00", "09:30"};
  private String[] task4 = {"10:00", "10:50"};
  private String[] task5 = {"14:00", "15:15"};
  private String[] task6 = {"20:00", "22:00"};
  private String[] task7 = {"15:00", "17:30"};
  private String[] task8 = {"09:00", "12:00"};

  Scheduler scheduler;

  @BeforeEach
  public void setup() {
    testTasks = new ArrayList<String[]>();
  }

  @Test
  public void testConstructorDeepcopy() {
    testTasks.add(task1);
    testTasks.add(task2);
    // testTasks is [[11:00, 12:15], [13:00, 14:00]]
    Scheduler scheduler = new Scheduler(testTasks);
    ArrayList<String[]> scheduleTasks = scheduler.getTasks();
    scheduleTasks.remove(0);
    scheduleTasks.get(0)[0] = "08:00";
    // scheduleTasks is [[08:00, 14:00]] (shouldn't change testTasks)
    ArrayList<String[]> expected = new ArrayList<String[]>();
    expected.add(task1);
    expected.add(task2);
    //expected is [[11:00, 12:15], [13:00, 14:00]] (same as testTasks)
    assertEquals(expected, testTasks);
    assertNotEquals(scheduleTasks, testTasks);
  }

  @Test
  public void testConvertToInt() {
    testTasks.add(task1);
    testTasks.add(task2);
    // testTasks is [[11:00, 12:15], [13:00, 14:00]]
    // the int version should be [[660, 735], [780, 840]]
    Scheduler scheduler = new Scheduler(testTasks);
    scheduler.convertToInt();
    ArrayList<int[]> scheduleIntTasks = scheduler.getIntTasks();
    assertEquals(660, scheduleIntTasks.get(0)[0]);
    assertEquals(735, scheduleIntTasks.get(0)[1]);
    assertEquals(780, scheduleIntTasks.get(1)[0]);
    assertEquals(840, scheduleIntTasks.get(1)[1]);
  }

  @Test
  public void testConvertToString() {
    testTasks.add(task2);
    testTasks.add(task3);
    // testTasks is [[13:00, 14:00], [08:00, 09:30]]
    // the int version is [[780, 840], [480, 570]]
    Scheduler scheduler = new Scheduler(testTasks);
    scheduler.convertToInt();
    // testTasks is [[13:00, 14:00], [08:00, 09:30]]
    // the int version is [[780, 840], [480, 570]]
    // the final version should give back the original String[] tasks
    // [[13:00, 14:00], [08:00, 09:30]]
    scheduler.convertToString();
    ArrayList<String[]> scheduleTasks = scheduler.getTasks();
    assertEquals("13:00", scheduleTasks.get(0)[0]);
    assertEquals("14:00", scheduleTasks.get(0)[1]);
    assertEquals("08:00", scheduleTasks.get(1)[0]);
    assertEquals("09:30", scheduleTasks.get(1)[1]);
  }

  @AfterEach
  public void teardown() {
    testTasks = null;
    scheduler = null;
  }

}