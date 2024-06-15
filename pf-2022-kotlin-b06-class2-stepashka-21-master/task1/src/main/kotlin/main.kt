/*
 * Задание 1.1. Дана строка. Верните строку, содержащую текст "Длина: NN",
 * где NN — длина заданной строки. Например, если задана строка "hello",
 * то результатом должна быть строка "Длина: 5".
 */
fun stringLength(s: String) = ("Длина: ${s.length}")

/*
 * Задание 1.2. Дана непустая строка. Вернуть коды ее первого и последнего символов.
 * Рекомендуется найти специальные функции для вычисления соответствующих символов и их кодов.
 */
fun firstLastCodes(s: String): Pair<Int, Int> {
    return Pair((s[0].toChar()).code, (s.last().toChar()).code)
}

/*
 * Задание 1.3. Дана строка. Подсчитать количество содержащихся в ней цифр.
 * В решении необходимо воспользоваться циклом for.
 */
fun countDigits(s: String): Int {
var k: Int=0
    for (i in 0..s.length-1)
    if (s[i] in "0987654321") k=k+1 else k=k
    return k
}

/*
 * Задание 1.4. Дана строка. Подсчитать количество содержащихся в ней цифр.
 * В решении необходимо воспользоваться методом count:
 * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/count.html
 * и предикатом it.isDigit().
 */
fun countDigits2(s: String): Int = s.filter{ it.isDigit() }.count()

/*
 * Задание 1.5. Дана строка, изображающая арифметическое выражение вида «<цифра>±<цифра>±…±<цифра>»,
 * где на месте знака операции «±» находится символ «+» или «−» (например, «4+7−2−8»). Вывести значение
 * данного выражения (целое число).
 */
fun calcDigits(expr: String): Int {
    var r: Int = 0
    var k: Int = 1
    for (i in 0..expr.length-1)
    if (expr[i].toString()=="+") {
        k=1
    } else if (expr[i].toString()=="-") {
        k=-1
    } else {
        r+=k*expr[i].toString().toInt()
        
    }
    return r
}

/*
 * Задание 1.6. Даны строки S, S1 и S2. Заменить в строке S первое вхождение строки S1 на строку S2.
 */
fun replaceWithString(s: String, s1: String, s2: String): String = s.replaceFirst(s1, s2)

fun main() {
    println(stringLength("dfgh"))
    println(firstLastCodes(" "))
    println(countDigits("dj3dh"))
    println(countDigits2("bjk34jrn34mk34k2"))
    println(calcDigits("1+2+8-9"))
    println(replaceWithString("2qwe3qwe4qwe","qw","ty"))
}
