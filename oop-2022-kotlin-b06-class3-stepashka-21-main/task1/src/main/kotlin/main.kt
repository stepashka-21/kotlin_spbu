interface Computer {
    fun calculateAnswer(): Int
}
class Desktop(m: Int) : Computer {
    var n = m
    override fun calculateAnswer(): Int = n;

}
class SummingCloud(m: Int) : Computer {
    private var s = mutableListOf<Computer>()
    init {
        for (i in 1..m)
            s.add(Desktop(i))
    }
    override fun calculateAnswer(): Int = s.sumOf { it.calculateAnswer() }

}

fun main() {
    val answer = SummingCloud(10).calculateAnswer()
    println("Sum = $answer") // should print 55
}