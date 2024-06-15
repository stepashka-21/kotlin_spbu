/*
 * Перед выполнением заданий прочитайте короткий туториал о способах формирования строк:
 * https://www.baeldung.com/kotlin/concatenate-strings. В этих заданиях
 * рекомендуется всюду использовать класс StringBuilder.
 *
 * Задание 2.1. Дана непустая строка S и целое число N (> 0). Вернуть строку, содержащую символы
 * строки S, между которыми вставлено по N символов «*» (звездочка).
 */
fun fillWithAsterisks(s: String, n: Int): String {
    var sq=StringBuilder()
    for (i in 0..s.length-2) {
        sq.append(s[i]).toString()
        for (j in 1..n) {
            sq.append("*").toString() 
        }
    }
    sq.append(s[s.length-1]).toString()
    return sq.toString()
}

/*
 * Задание 2.2. Сформировать таблицу квадратов чисел от 1 до заданного числа N.
 * Например, для N=4 должна получиться следующая строка:

1  1
2  4
3  9
4 16

 * Обратите внимание на выравнивание: числа в первом столбце выравниваются по левому краю,
 * а числа во втором -- по правому, причём между числами должен оставаться как минимум один
 * пробел. В решении можно использовать функции pad*.
 */
fun tabulateSquares(n: Int): String {
    var s=StringBuilder()
    var k: Int = n*n
    var d: Int = n.toString().length + 1 + k.toString().length

    for (i in 1..n-1) {
        s.append("$i ")
        s.append("${i*i}\n".padStart(d-(i.toString().length)).toString())
    }
    s.append("$n ${n*n}")
    return s.toString()
}

fun main() {
    println(fillWithAsterisks("abc", 2))
    println(tabulateSquares(4))
}
