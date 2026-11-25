import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

/**
 * Testing for the Scheduling algorithm
 */
public class SchedulerTest {

  private ArrayList<String[]> testTasks;

  @BeforeEach
  public void setup() {
    testTasks = new ArrayList<String[]>();
  }

  @Test
  public void testConstructorDeepcopy() {
    String[] str1 = {"11:00", "12:15"};
    String[] str2 = {"13:00", "14:00"};
    testTasks.add(str1);
    testTasks.add(str2);
    // testTasks is [[11:00, 12:15], [13:00, 14:00]]
    Scheduler scheduler = new Scheduler(testTasks);
    ArrayList<String[]> scheduleTasks = scheduler.getTasks();
    scheduleTasks.remove(0);
    scheduleTasks.get(0)[0] = "8:00";
    // scheduleTasks is [[8:00, 14:00]] (shouldn't change testTasks)
    ArrayList<String[]> expected = new ArrayList<String[]>();
    expected.add(str1);
    expected.add(str2);
    //expected is [[11:00, 12:15], [13:00, 14:00]] (same as testTasks)
    assertEquals(expected, testTasks);
  }

  @AfterEach
  public void teardown() {
    testTasks = null;
  }

}