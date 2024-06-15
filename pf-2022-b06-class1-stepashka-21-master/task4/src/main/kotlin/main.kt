/*
 * В решениях следующих заданий предполагается использование циклов и диапазонов:
 * - https://kotlinlang.org/docs/control-flow.html#for-loops
 * - https://kotlinlang.org/docs/control-flow.html#while-loops
 * - https://kotlinlang.org/docs/ranges.html
 */

/*
 * Задание 4.1. Пользуясь циклом for, посимвольно напечатайте рамку размера width x height,
 * состоящую из символов frameChar. Можно считать, что width>=2, height>=2.
 * Например, вызов printFrame(5,3,'+') должен напечатать следующее:

+++++
+   +
+++++
 */
fun printFrame(width: Int, height: Int, frameChar: Char) {
    for (q in 1..width) print("$frameChar")
    println()
    for (i in 2..(height-1)) {
        print("$frameChar")
        for (j in 2..(width-1)) print(" ")
        println(frameChar)
    }
    for (q in 1..width) print("$frameChar")    
}

/*
 * Задание 4.2. Выполните предыдущее задание, пользуясь циклом repeat.
 * Например, следующий код печатает числа от 1 до 10:

    repeat(10) {
        println(it + 1)
    }

 * Переменная `it` в этом коде последовательно принимает значения от 0 до 9, и для
 * каждого нового значения вызывается блок в фигурных скобках.
 *
 * На самом деле repeat — это не синтаксическая конструкция, а функция, принимающая блок
 * в качестве параметра. Если вам интересно, посмотреть на реализацию этой функции можно здесь:
 * https://github.com/JetBrains/kotlin/blob/34e57a45f2d4283be572137b4b497414b8833ee7/libraries/stdlib/src/kotlin/util/Standard.kt#L151
 */
fun printFrame2(width: Int, height: Int, frameChar: Char) {
    repeat(width) {
        print(frameChar)
   } 
   println()
   repeat(height-2) {
        print(frameChar)
        repeat(width-2) {
            print(" ")
        }
        println(frameChar)
   }
    repeat(width) {
        print(frameChar)
   }
}

/*
 * Задание 4.3. Даны целые положительные числа A и B. Найти их наибольший общий делитель (НОД),
 * используя алгоритм Евклида:
 * НОД(A, B) = НОД(B, A mod B),    если B ≠ 0;        НОД(A, 0) = A,
 * где «mod» обозначает операцию взятия остатка от деления.
 */
fun gcd(q: Long, w: Long): Long {
    var a: Long
    var b: Long
    a=q; b=w
    while (a>0 && b>0) {
        if (a>b) a=a%b else b=b%a
    }
    return a+b
}

/*
 * Задание 4.4. Дано вещественное число X и целое число N (> 0). Найти значение выражения
 * 1 + X + X^2/(2!) + … + X^N/(N!), где N! = 1·2·…·N.
 * Полученное число является приближенным значением функции exp в точке X.
 */
fun expTaylor(x: Double, n: Int): Double {
    var s: Double = 1.0
    var f: Double = 1.0
    for (i in 1..n) {
        f=f*x/i
        s=s+f
    }
    return s
}

fun main() {
    printFrame(5,3,'+')
    printFrame2(5,5,'@')
    println(gcd(3,9))
    println(expTaylor(5.0,7))
}
