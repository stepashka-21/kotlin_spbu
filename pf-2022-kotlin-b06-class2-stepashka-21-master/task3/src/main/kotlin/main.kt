/*
 * Перед выполнением заданий рекомендуется просмотреть туториал по регулярным выражениям:
 * https://zetcode.com/kotlin/regularexpressions/
 */

/*
 * Задание 3.1. Проверить, содержит ли заданная строка только цифры?
 */
fun allDigits(s: String) = s.matches("^[0-9]+$".toRegex())

/*
 * Задание 3.2. Проверить, содержит ли заданная строка подстроку, состоящую
 * из букв abc в указанном порядке, но в произвольном регистре?
 */
fun containsABC(s: String) = "abc".toRegex().containsMatchIn(s.lowercase())

/*
 * Задание 3.3. Найти первое вхождение подстроки, состоящей только из цифр,
 * и вернуть её в качестве результата. Вернуть пустую строку, если вхождения нет.
 */
fun findDigitalSubstring(s: String) : String {
    val found = Regex("""\d+""").findAll(s) 
    val r = StringBuilder() 
    for(i in found) { 
        r.append(i.value) 
        break
    } 
    return r.toString()
}

/*
 * Задание 3.4. Заменить все вхождения подстрок строки S, состоящих только из цифр,
 * на заданную строку S1.
 */
fun hideDigits(s: String, s1: String) : String {
    val found = Regex("""\d+""").findAll(s) 
    var r: String = s
    
    for (i in found) {
        r=r.replaceFirst(i.value, s1)
    } 
    return r.toString()
}


fun main() {
    println(allDigits(""))
    println(containsABC("AbC"))
    println(findDigitalSubstring("d78gfgh5678"))
    println(hideDigits("d78gfgh53456786d3","*"))
}
