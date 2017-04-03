package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
	}


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("should convert to pair") {
    val list = times(List('a', 'b', 'a'))
    assert(list.size == 2)
    println(list.foreach(println))
  }

  test("create code tree should work") {
    val chars = List('a', 'e', 't', 'w', 'e', 'q', 'b', 'a', 'y', 'j', 'a')
    createCodeTree(chars)
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }


  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("combine of list of single or nil element") {
    val leaf = List(Leaf('a', 2))
    assert(combine(leaf) == leaf)
    assert(Nil == combine(Nil))
  }

  test("encode something and see") {
    new TestTrees {
      val chars = List('a', 'e', 't', 'w', 'e', 'q', 'b', 'a', 'y', 'j', 'a')
      val codeTree = createCodeTree(chars)
     // val encoded = List(1, 1, 1, 0, 1, 0, 1, 0)
            val encoded: List[Bit]= encode(codeTree)(List('e', 'a', 't', 'w'))
           println("encoded e = " +  encode(codeTree)(List('e')))
            println("encoded a = " +  encode(codeTree)(List('a')))
            println("encoded t = " +  encode(codeTree)(List('t')))
            println("encoded w = " +  encode(codeTree)(List('w')))
            println("encoded = " + encoded)
      val decoded = decode(codeTree, encoded)
      println("decoded = " + decoded)

    }
  }


  test("decode and encode a very short text should be identity") {
    new TestTrees {
      println(encode(t1)("ab".toList));
      println(decode(t1, List(0, 1)))
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("Covert code tree to code table") {
    new TestTrees {
      val codeTable = convert(t1)
      println(codeTable)
    }
  }

  test("Test quick encode should still work") {
    new TestTrees {
      println(quickEncode(t1)("ab".toList))
      println(decodedSecret)
    }
  }

}
