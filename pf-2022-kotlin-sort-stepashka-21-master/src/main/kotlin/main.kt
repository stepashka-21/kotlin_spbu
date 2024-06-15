import java.io.File
import kotlin.system.exitProcess
// функция вывода
fun outPut(lines: Array<String>, outfile: String, flags: Array<String>, output: String) {
    if (output == "no") { // стандартный вывод
        println(lines.joinToString("\n"))
    } else if ("-e" in flags) { // вывод, сохраняющий исходный текст в файле
        val text = File(outfile).readText()
        File(outfile).writeText((text+lines.joinToString("\n")))
    } else {
        File(outfile).writeText(lines.joinToString("\n")) // вывод в файл
    }
}
// создание списка флагов и файлов
fun filesFlags(args: Array<String>): Pair<Array<String>, Array<String>> {
    var flags = emptyArray<String>() // все возможные флаги
    var files = emptyArray<String>() // все возможные пути к файлам
    for (elements in args) {
        if (elements[0]=='-' && elements.length==2) {
            flags+=elements
        } else {
            files+=elements
        }
    }
    return Pair(files, flags)
}
// проверка на корректность ввода файлов и флагов
fun checkFilesFlags(files: Array<String>, flags: Array<String>, args: Array<String>): Boolean {
    for (elements in files) {
        if (!File(elements).exists()) {
            println("неверные файлы!")
            println(files.joinToString(" "))
            return false
        }
    }
    // если флаги "-n", "-v" вместе или "-с" вместе с "-n" или "-v, то выполнить задание невозможно
    if (("-n" in flags && "-v" in flags) || ("-c" in flags && ("-n" in flags || "-v" in flags))) {
        println("неверные флаги!")
        return false
    }
    // проверка корреткности файла после флага "-о"
    if ("-o" in args) {
        val indexO: Int = args.indexOf("-o")
        if (!File(args[indexO + 1]).exists()) {
            println("неверный файл вывода!")
            return false
        }
    }
    // если флаг "-е" без флага "-о", то выполнить задание невозможно
    if ("-e" in flags && "-o" !in flags) {
        println("неверные флаги!")
        return false
    }
    return true
}
fun main(args: Array<String>) {
    // ввод файлов/флагов -- через пробел, если есть "-о", то файл вывода строго за ним
    val flags = filesFlags(args).second
    val files = filesFlags(args).first
    /*
    -f — игнорировать регистр букв
    -r — изменить результаты сравнения на противоположные
    -u — выводить только первое среди нескольких равных
    -n — сравнивать по числовым значениям строк (простая версия, считающая, что вся строка является числом, иные строки попадают в конец вывода в том порядке, в котором они шли во входных данных (устойчивая сортировка))
    -v — сортировать по номерам (версии) в текстовом представлении (простая версия, считающая, что вся строка является версией типа "Последовательность чисел" , иные строки попадают в конец вывода в том порядке, в котором они шли во входных данных (устойчивая сортировка))
    -o — выводить в ФАЙЛ, а не на стандартный вывод
    -c — проверять, сортированы ли входные файлы; не сортировать (только "-f" учитывается)
    -е — выводить в ФАЙЛ, а не на стандартный вывод, с сохранением текста этого ФАЙЛа
    */
    var lines = emptyArray<String>() // строки, считываемые из файлов
    var output = "no" // вывод в файл?
    var outfile = "" // файл для вывода
    // проверка корректности введенных данных
    if (!checkFilesFlags(files, flags,args)) {
        exitProcess(-1)
    }
    // проверяем, есть ли флаг "-о", если есть -- проверяем, корректен ли файл после него,
    // если да -- выводим результат в файл !! после "-о" должен быть путь к файлу
    if ("-o" in args) {
        val indexO: Int = args.indexOf("-o")
        output = "yes" // да, вывод результата в файл
        outfile = args[indexO + 1]
        files.filter { it != (args[indexO + 1]) }  // удаляем из массива файл вывода
    }
    // читаем все файлы
    for (elements in files) {
        lines += File(elements).readLines()
    }
    // реализация флага "-u": если есть флаг "-f" -- реализуем его
    if ("-u" in flags && "-c" !in flags) {
        lines = if ("-f" in flags) {
            lines.distinctBy { it.lowercase() }.toTypedArray()
        } else {
            lines.distinctBy {it}.toTypedArray()
        }
    }
    // реализация флага "-n"
    if ("-n" in flags) {
        var numb = emptyArray<Int>() // пустой массив для чисел
        var other = emptyArray<String>() // для остального
        for (line in lines)
            if (line.toIntOrNull() != null) { // проверка того, что элемент строки состоит из цифр
                numb += line.toInt()
            } else other += line
        if ("-r" in flags) { // проверка на наличие флага "-r"
            numb.sortDescending()
        } else {
            numb.sort()
        }
        lines = numb.map { it.toString()}.toTypedArray() + other
    }
    // реализация флага "-v"
    if ("-v" in flags) {
        var numb = emptyArray<String>() // пустой массив для последовательностей чисел
        var other = emptyArray<String>() // для остального
        for (line in lines) {
            if (line.matches("([0-9]+\\.)+([0-9]+)?".toRegex())) { // проверка на принадлежность к последовательности
                numb += (line)
            } else {
                other += (line)
            }
        }
        // реализация флага "-v" (компаратор)
        val compare = { str1: String, str2: String -> // сравнение двух строк
            val list1: List<String> = listOf(*str1.split(".").toTypedArray()) // первая строка
            val list2: List<String> = listOf(*str2.split(".").toTypedArray()) // вторая строка
            var x = 0
            val length: Int = if (list1.size < list2.size) {
                list1.size
            } else list2.size
            var t = 0
            for (i in 0..length) {
                if (list1[i].toInt() < list2[i].toInt()) {
                    x = -1
                    t = 1
                    break
                } else if (list1[i].toInt() > list2[i].toInt()) {
                    x = 1
                    t = 1
                    break
                }
            }
            if (t == 0) { // случай равенства первых частей последовательностей
                x = if (list1.size == length) {
                    -1
                } else {
                    1
                }
            }
            x
        }

        numb = numb.sortedWith(comparator = compare).toTypedArray()
        if ("-r" in flags) { // проверка на наличие флага "-r"
            numb.sortDescending()
        }
        lines = numb + other
    }
    // если нет никакой особенной сортировки
    if ("-n" !in flags && "-v" !in flags && "-c" !in flags) {
        if ("-f" in flags && "-u" !in flags) { //проверка на наличие флага "-f"
            lines.sortedWith(comparator = {s: String, t: String ->
                if (s.lowercase() < t.lowercase()) 1 else -1
            })
        } else {
            lines.sort()
        }
        if ("-r" in flags) { //проверка на наличие флага "-r"
            lines.reverse()
        }
    }
    // реализация флага "-с"
    if ("-c" in flags) {
        lines = emptyArray()
        var lineFile: List<String>
        for (file in files) {
            lineFile = listOf(File(file).readText())
            val line = lineFile
            val lineSort = lineFile.sorted()
            if ("-f" in flags) { //проверка на наличие флага "-f"
                lineSort.sortedWith(comparator = {s: String, t: String ->
                    if (s.lowercase() < t.lowercase()) 1 else -1
                })
            }
            lines += if (line == lineSort) {
                "$file отсортирован"
            } else {
                "$file не отсортирован"
            }
        }
    }
    outPut(lines, outfile, flags, output)
}