import java.io.RandomAccessFile
import kotlin.system.exitProcess

// ключи и их значения храняться в одном текстовом файле "dbname.txt"
// ключи на нечетных строках, их значения -- на четных
// реализация put
fun put(keys: Array<String>, values: Array<String>, filename: String) {
    var str = "" // найденные значения по данным ключам
    val file = RandomAccessFile(filename, "rw")
    for (element in keys.indices) {
        file.seek(0)
        var line = file.readLine()
        var k = 0 // счетчик: "1" - строка ключей, "0" - строка значений
        while (line != null) {
            k = if (k == 0) 1 else 0
            if (line == keys[element] + "?" && k == 1) {
                file.seek(file.filePointer - line.length - 1)
                file.write((Array(keys[element].length) { " " }.joinToString("") + "?").toByteArray()) // удаление ключа (остается только спец.символ "?")
                file.readLine()
                str += file.readLine() + " "
                break
            }
            line = file.readLine()
        }
        file.readLine()
        file.readLine()
        file.write((keys[element] + "?" + "\n").toByteArray())
        file.write((values[element] + "\n").toByteArray())
    }
    println(str)
}
// реализация get
fun get(keys: Array<String>, filename: String) {
    var str = ""
    val file = RandomAccessFile(filename, "rw")
    for (element in keys.indices) {
        file.seek(0)
        var line = file.readLine()
        var k = 0 // счетчик: "нечетные" - строка ключей, "четные" - строка значений
        while (line != null) {
            k += 1;
            if (line == keys[element] + "?" && k % 2 != 0) {
                str += file.readLine() + " "
                break
            }
            line = file.readLine()
        }
    }
    println(str)
}
// реализация remove
fun remove(keys: Array<String>, filename: String) {
    var str = ""
    val file = RandomAccessFile(filename, "rw")
    for (element in keys.indices) {
        file.seek(0)
        var line = file.readLine()
        var k = 0 // счетчик: "нечетные" - строка ключей, "четные" - строка значений
        while (line != null) {
            k += 1;
            if (line == keys[element] + "?" && k % 2 != 0) {
                file.seek(file.filePointer - line.length - 1)
                file.write((Array(keys[element].length){" "}.joinToString("") + "?").toByteArray()) // удаление ключа (остается только спец.символ "?")
                file.readLine()
                str += file.readLine() + " "
                break
            }
            line = file.readLine()
        }
    }
    println(str)
}
// реализация проверки корректности введенных данных
fun check(args: Array<String>): Boolean {
    if (args[0] != "put" && args[0] != "get" && args[0] != "remove") { // указана ли команда?
        println("дайте команду")
        return false
    }
    if (args.size == 1) { // указан ли файл?
        println("неверные ключи и/или значения")
        return false
    }
    return true
}
fun main(args: Array<String>) {
    if (!check(args)) { // проверка корректности введенных данных
        exitProcess(-1)
    }
    val command = args[0] // введенная команда
    val filename = args[1] // введенная БД
    when (command) {
        "get" -> {
            get(args.copyOfRange(2, args.size), "$filename.txt") // удаляем название комманды и файла из аргументов, оставляя только ключи
        }
        "remove" -> {
            remove(args.copyOfRange(2, args.size), "$filename.txt") // удаляем название комманды и файла из аргументов, оставляя только ключи
        }
        else -> {
            var keys = emptyArray<String>() // создание списка всех введенных ключей
            var values = emptyArray<String>() // создание списка всех введенных значений
            for (i in 2 until args.size) {
                if (i % 2 == 0) {
                    keys += args[i]
                } else {
                    values += args[i]
                }
            }
            put(keys, values, "$filename.txt")
        }
    }
}

