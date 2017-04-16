def isPrime(x : Int) : Boolean = (for(i <- 1 to x)yield x % 2) forall(_  != 0)

isPrime(10000009)
isPrime(5)

def streamRange(lo: Int, high: Int): Stream[Int] = {
  if(lo >= high) Stream.empty
  else Stream.cons(lo, streamRange(lo + 1, high))
}

streamRange(1, 10).take(3).apply(2)

(streamRange(100, 10000) filter isPrime) apply 100

def from(n: Int) : Stream[Int] = n #:: from(n + 1)
from(5)

def sieve(n : Stream[Int]): Stream[Int] = {
  n.head #:: sieve(n.tail filter (_ % n.head != 0))
}

val primes = sieve(from(2))
primes.take(100).toList

