package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val t1 = singletonSet(11)
    val t2 = singletonSet(12)
    val t3 = singletonSet(13)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains elements that are in both sets") {
    new TestSets {
      val s = intersect(s1,s2)
      assert(!contains(s,1), "Intersect 1")
      assert(!contains(s,2), "Intersect 2")
      assert(!contains(s,3), "Intersect 3")
      val t = union(s1,s2)
      val u = intersect(t,s1)
      assert(contains(u,1), "Intersect 4")
      assert(!contains(u,2), "Intersect 5")
      assert(!contains(u,3), "Intersect 6")

    }
  }


  test("difference contains elements that are in first set and not in second set") {
    new TestSets {
      val s = diff(s1,s2)
      assert(contains(s,1), "Diff 1")
      assert(!contains(s,2), "Diff 2")
      assert(!contains(s,3), "Diff 3")
      val v = diff(s2,s1)
      assert(!contains(v,1), "Diff 4")
      assert(contains(v,2), "Diff 5")
      assert(!contains(v,3), "Diff 6")
      val t = union(s1,s2)
      val u = diff(t,s1)
      assert(!contains(u,1), "Diff 7")
      assert(contains(u,2), "Diff 8")
      assert(!contains(u,3), "Diff 9")

    }
  }

  test("filter contains all elements of a set for which the predicate p is true") {
    new TestSets {
      def p(x: Int) = x==1
      val t = union(s1,s2)
      val s = filter(t,p)
      assert(contains(s,1), "Filter 1")
      assert(!contains(s,2), "Filter 2")
      assert(!contains(s,3), "Filter 3")

    }
  }


  test("forall checks if all elements of a set satisfy condition p") {
    new TestSets {
      def p(x: Int) = {x==1 || x==2}
      val t = union(s1,s2)
      val u = union(t,s3)

      assert(!forall(u,p),"Forall 1")


    }
  }

  test("exists checks if all elements of a set satisfy condition p") {
    new TestSets {
      def p(x: Int) = {x==1}
      val t = union(s1,s2)
      val u = union(t,s3)
      val v = union(s2,s3)

      assert(exists(u,p),"Exist 1")
      assert(!exists(v,p),"Exist 2")


    }
  }


  test("map transforms all elements of a set according to function f") {
    new TestSets {
      def f(x: Int): Int = 0
      def p(x: Int):Boolean= {x == 0}
      val t = union(union(s1,s2),s3)
      assert(forall(map(t,f), p))
    }
  }

}
