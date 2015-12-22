package myproject.util;

import static org.junit.Assert.*;
import org.junit.Test;

import myproject.model.Agent;


public class TimeServerTEST {
   TimeServerList q = new TimeServerList();
   // TimeServerLinked q = new TimeServerLinked();
 
   @Test
   public void testThatEmptySizeIsZero() {
     assertEquals(0, q.size());
   }
 
   @Test
   public void testThatDequeueOnEmptyThrowsIndexOutOfBoundsException() {
     boolean exceptionOccurred = false;
 
     try {
       q.dequeue();
     } catch (java.util.NoSuchElementException e) {
       exceptionOccurred = true;
     }
 
     assertTrue(exceptionOccurred);
   }
 
   @Test
   public void testThatEnqueueFollowedByDequeueReturnsSameReference() {
     class TestThatEnqueueFollowedByDequeueReturnsSameReference
     implements Agent
     {
       public void run(double time) {}
     }
 
     Agent x1 = new TestThatEnqueueFollowedByDequeueReturnsSameReference();
     q.enqueue(0, x1);
     assertSame(x1, q.dequeue());
     assertEquals(0, q.size());
   }
 
   @Test
   public void testThatElementsAreInsertedInOrder() {
     class TestThatElementsAreInsertedInOrder implements Agent {
       public void run(double time) {}
     }
 
     Agent x1 = new TestThatElementsAreInsertedInOrder();
     Agent x2 = new TestThatElementsAreInsertedInOrder();
     q.enqueue(0, x2);
     q.enqueue(1, x1);
     assertSame(x2, q.dequeue());
     assertSame(x1, q.dequeue());
     q.enqueue(1, x1);
     q.enqueue(0, x2);
     assertSame(x2, q.dequeue());
     assertSame(x1, q.dequeue());
     q.enqueue(0, x1);
     q.enqueue(0, x2);
     assertSame(x1, q.dequeue());
     assertSame(x2, q.dequeue());
     q.enqueue(0, x2);
     q.enqueue(0, x1);
     assertSame(x2, q.dequeue());
     assertSame(x1, q.dequeue());
   }
 
   @Test
   public void testToString() {
     class TestToString implements Agent {
       public void run(double time) {}
       public String toString() { return "x"; }
     }
 
     q.enqueue(0, new TestToString());
     q.enqueue(1, new TestToString());
     assertEquals("[(0.0,x);(1.0,x)]", q.toString());
   }
 
   @Test
   public void testCurrentTime() {
     class TestCurrentTime implements Agent {
       public void run(double time) {}
     }
 
     double expected = 1230;
     q.enqueue(expected, new TestCurrentTime());
 
     assertEquals(0.0, q.currentTime(), .1e-6);
     q.run(expected);
 
     assertEquals(expected, q.currentTime(), .1e-6);
   }
 
   private double scratch;
   @Test
   public void testDoActionsAtOrBefore() {
     class TestDoActionsAtOrBefore implements Agent {
       private double myScratch;
       TestDoActionsAtOrBefore(double myScratch) {
        this.myScratch = myScratch;
       }
       public void run(double time) {
         scratch = myScratch;
       }
     }
 
     double time1 = 12;
     double time2 = 23;
     double value1 = 42;
     double value2 = 27;
 
     q.enqueue(time1, new TestDoActionsAtOrBefore(value1));
 
     scratch = 0;
     q.run(time1 - 1);
     assertEquals(0.0, scratch, .1e-6);
 
     scratch = 0;
     q.run(1);
     assertEquals(value1, scratch, .1e-6);
 
     q.enqueue(time2, new TestDoActionsAtOrBefore(value2));
 
     scratch = 0;
     q.run(time2);
     assertEquals(value2, scratch, .1e-6);
   }
}

