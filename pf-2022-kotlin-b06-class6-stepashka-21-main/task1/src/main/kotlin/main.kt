import mu.KotlinLogging
import java.io.File
import java.io.RandomAccessFile

fun applyOperation(op: Char, arg1: Int, arg2: Int): Int {
    return when (op) {
        '*' -> arg1 * arg2
        '/' -> if (arg2 != 0) {arg1 / arg2} else {throw DivisionByZero()}
        else -> throw UnsupportedOperation(op)
    }
}

fun applySchema(schema: String) : (List<Int>) -> Int {
    val ops : List<Char> = schema.toList()
    return { args ->
        if (args.size > ops.size + 1) {
            throw TooManyNumbers(args.size, args.toString())
        } else if (args.size < ops.size + 1) {
            throw NotEnoughNumbers(args.size, args.toString())
        } else {
            var res = args[0]
            for (i in 1 until args.size) {
                res = applyOperation(ops[i - 1], res, args[i])
            }
            res
        }
    }
}

fun formatLHS(schema: String, numbers: List<String>): String {
    var str = ""
    for (i in schema.indices) {
        str += numbers[i] + schema[i]
    }
    str += numbers.last()
    return str
}

fun processString(schema: String, input: String): String {
    val transformation = applySchema(schema)
    val numbers = input.split(",")
    for (i in numbers) {
        if (i.toIntOrNull() == null) {
            throw IncorrectNumbers(numbers.toString())
        }
    }
    val result = transformation(numbers.map { it.toInt() })
    return formatLHS(schema, numbers) + "=$result"
}

fun processFiles(schemasFile: String, dataFile: String, outputFile: String) {
    if (!File(schemasFile).exists()) {
        throw UnrealFile(schemasFile)
    } else if (!File(dataFile).exists()) {
        throw UnrealFile(dataFile)
    } else {
        val schemaFile = RandomAccessFile(schemasFile, "rw")
        val datFile = RandomAccessFile(dataFile,"rw")
        val output = File(outputFile)
        var schema = schemaFile.readLine()
        var data = datFile.readLine()
        while (schema != null && data != null) {
            output.appendText(processString(schema,data) + "\n")
            schema = schemaFile.readLine()
            data = datFile.readLine()
        }
    }
}

val logger = KotlinLogging.logger {  }

fun main(args: Array<String>) {
    println(File("outTest.txt").readLines().joinToString())
    println(File("out.txt").readLines().joinToString())
    logger.info {"program started"}
    if (args.size == 3) {
        processFiles(args[0], args[1], args[2])
    } else {
        throw IncorrectArguments()
    }
    logger.info {"program completed"}
}