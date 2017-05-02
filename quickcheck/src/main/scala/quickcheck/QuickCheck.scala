package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] =  oneOf(
    const(empty),
    for{
      a <- arbitrary[A]
      h <- oneOf(const(empty), genHeap)
    } yield insert(a, h)
  )

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("gen2") = forAll{ (a1: A, a2: A) =>
    val h = empty
    val min = if(a1 < a2) a1 else a2
    val h1 = insert(a1, h)
    val h2 = insert(a2, h1)
    findMin(h2) == min
  }

  property("gen3") = forAll { (a: A) =>
    val h = empty
    val h1 = insert(a, h)
    deleteMin(h1) == empty
  }

  property("gen4") = forAll{ (h : H) =>
    val acc = List.empty
    val res = findMinRec(h, acc)
    res == res.sortWith(_ > _)
  }

  property("gen5") = forAll{ (h1 : H, h2: H ) =>

    if(isEmpty(h1) || isEmpty(h2)) true
    else {
      val min1 = findMin(h1)
      val min2 = findMin(h2)
      val min = if (min1 > min2) min2 else min1
      val h = meld(h1, h2)
      findMin(h) == min
    }
  }


  property("gen6") = forAll{ (h: H) =>
    if(isEmpty(h)) true
    else {
      val m1 = findMin(h)
      val h1 = deleteMin(h)
      if(isEmpty(h1)) true
      else {
        val m2 = findMin(h1)
        m2 != m1
      }
    }
  }

  def findMinRec(h : H, acc: List[A]) : List[A] = {
    if(h == empty) acc
    else {
      findMin(h) :: acc
      val h1 = deleteMin(h)
      findMinRec(h1, acc)
    }
  }

}
