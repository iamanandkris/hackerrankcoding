def NCK(list:List[Int],k:Int):List[List[Int]]={
		def rest(accum: List[Int], lst:List[Int], numToChoose:Int):List[List[Int]]={
			lst match{
				case top::last => rest(accum:::List(top), last, numToChoose-1) ::: rest(accum, last, numToChoose)
				case Nil => 	if (accum.size == k)List(accum) else Nil
			}
		}
		rest(Nil, list,k).sortBy { x => x.size }
	}
	
	val listOfItem = List(1,2,3,4)

	def disjoint(list:List[Int], setLength:List[Int]):scala.collection.immutable.Set[scala.collection.immutable.Set[List[Int]]]={
		if (list.length != setLength.sum) Set(Set(Nil))
		else{
			val t = setLength.foldLeft(scala.collection.immutable.Set((scala.collection.immutable.Set[List[Int]](), list))){(a,b) => {
				a.flatMap(f => {
					NCK(f._2,b).map (y => (Set(y) ++ f._1, f._2 diff y))
				})
			 }
			}
			t.map(f => f._1)
		}
	}
	
	disjoint(listOfItem, List(2,1,1))
