import java.lang.IllegalStateException

open class Box<T> (t: T) {
    var n = t
    fun view() {
        if (n == null) {
            throw IllegalStateException("Box is empty")
        } else {
            n
        }
    }
    fun put(k: Any) {
        if (n != null) {
            throw IllegalStateException("Box is not empty")
        }
    }
    fun get() {

    }
}

fun <T1, T2> Box<T1>.convert(function: (t: T1) -> T2): Box<T2> {

}

//class BoxList<T>

fun part1() {
    println("=== Часть 1: класс Box ===")
    val b = Box(6)
    println("Box b: ${b.view()}")
    try {
        b.put(6)
    } catch (e: IllegalStateException) {
        println("ERROR: ${e.message}")
    }
    b.get()
    try {
        println("Box b: ${b.view()}")
    } catch (e: IllegalStateException) {
        println("ERROR: ${e.message}")
    }
    try {
        b.get()
    } catch (e: IllegalStateException) {
        println("ERROR: ${e.message}")
    }
    b.put(0)
    println("Box b: ${b.view()}")

    val b2 = Box("hello")
    println("Box b2: ${b2.view()}")
    val s = b2.get()
    println("String's length is ${s.length}")
    b2.put("bye")
    val s2 = b2.get()
    println("String's length is ${s2.length}")
}

fun part2() {
    println("\n=== Часть 2: функция convert ===")
/* TODO: uncomment
    val intBox = Box(42)
    val stringBox = intBox.convert { "$it!" }
    println("Box stringBox: ${stringBox.view()}")
    try {
        println("Box intBox: ${intBox.view()}")
    } catch (e: IllegalStateException) {
        println("ERROR: ${e.message}")
    }
    val intBox2 = stringBox.convert { it.length }
    println("Box intBox2: ${intBox2.view()}")
    val intBox3 = intBox2.convert { it + 1 }
    println("Box intBox3: ${intBox3.view()}")
 */
}

fun part3() {
    println("\n=== Часть 3: класс BoxList ===")
/* TODO: uncomment
    val intBoxes = BoxList<Int>()
    (1..8).forEach { intBoxes.add(it) }
    println(intBoxes)
    
    val stringBoxes = BoxList<String>()
    ('a'..'h').map { it.toString() }
        .forEach { stringBoxes.add(it) }
    println(stringBoxes)
 */
}

fun main() {
    part1()
    part2()
    part3()
}