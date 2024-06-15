import java.io.File
import java.nio.charset.Charset
import java.nio.charset.Charset.forName

fun main(args: Array<String>) {
    val f = args[0]
    val k1 = args[1]
    val k2 = args[2]
    if (File(f).exists() && Charset.isSupported(k1) && Charset.isSupported(k2)) {
        var f1 = File(f).readText(forName(k1))
        File(f).writeText(f1,forName(k2))
    }
}