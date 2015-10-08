object Solution {
import scala.concurrent._
import scala.concurrent.{ future, promise }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success}
def main(args: Array[String]) {
    val iteratorVal = io.Source.stdin.getLines()
    val inputIter = iteratorVal.take(1)
    //val start = System.nanoTime
    inputIter.foreach { k =>
      val intNum = k.toInt
      val inputIter1 = iteratorVal.take(intNum * 2).toList
      val op = inputIter1.grouped(2).map {
        case List(odd, even) => {
          val start = System.nanoTime
          if (odd.length() == even.length()) {
            if (odd == even) {
              Future{1}
            } else {
              Future{-1}
            }
          } else {
            //val op = kmpAlg(odd, even)
            Future {kmpAlg(odd, even)}
          }
        }
      }
      op.foreach { x => {
            	x.onComplete({
          			case Success(intVal) => {
            			println(intVal)
          			}
          			case Failure(exception) => {
            			println("Error")
          			}
        			})
      }}
    }

    //val end = System.nanoTime
    //println(s"Time taken: ${(end - start) / 1.0e9} sec")
    Thread.sleep(6000)
  } 

  def getPropTable(m: String): List[Int] = {
    def getLongestMatching(strLen: Int, len: Int): Int = {
      if (len < 1) 0
      else {
        if (m.regionMatches(0, m, strLen - len, len)) len
        else getLongestMatching(strLen, len - 1)
      }
    }

    val len = m.length()
    if (len == 1) List(-1)
    else {
      val ourterList: List[Int] = (1 to len - 1).toList
      val lst = ourterList.par.map { subStrLen => getLongestMatching(subStrLen, subStrLen - 1) }
      -1 :: lst.toList
    }
  } 

  def kmpAlg(source: String, toSearch: String): Int = {

    lazy val lengthOfSource = source.length()
    lazy val lengthOfSought = toSearch.length()
    lazy val T = getPropTable(toSearch)
    
    @scala.annotation.tailrec	
    def intermediate(m: Int, i: Int): Int = {
      val sum = m + i
      if (sum < lengthOfSource) {
        if (toSearch(i) == source(sum)) {
          if (i == (lengthOfSought - 1)) m
          else {
            intermediate(m, i + 1)
          }
        } else {
          if (T(i) > -1) {
            intermediate(sum - T(i), T(i))
          } else {
            intermediate(m + 1, 0)
          }
        }
      } else {
        -1
      }
    }

    intermediate(0, 0)
  }
}
