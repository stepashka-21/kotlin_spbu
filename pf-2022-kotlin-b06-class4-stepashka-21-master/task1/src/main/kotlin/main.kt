/*
 * Объекты этого класса создаются из строки, но хранят внутри помимо строки
 * ещё и целочисленное значение соответствующего адреса. Например, для адреса
 * 127.0.0.1 должно храниться число 1 + 0 * 2^8 + 0 * 2^16 + 127 * 2^24 = 2130706433.
 */
import java.io.File
import kotlin.math.pow


data class IPv4Addr (val str: String) : Comparable<IPv4Addr> {
    internal val intValue = ipstr2int()

    private fun ipstr2int(): UInt {
        var arstr = str.split(".").toTypedArray()
        arstr.reverse()
        var r: UInt = (arstr[0].toUInt()+(arstr[1].toUInt()* 2.0.pow(8.0).toUInt() +arstr[2].toUInt()* 2.0.pow(16.0).toUInt()+arstr[3].toUInt()* 2.0.pow(
            24.0).toUInt()))
        return r
    }

    // Благодаря этому методу мы можем сравнивать два значения IPv4Addr
    override fun compareTo(other: IPv4Addr): Int {
        return intValue.compareTo(other.intValue)
    }

    override fun toString(): String {
        return str
    }
}

data class IPRange(val ipFrom: IPv4Addr, val ipTo: IPv4Addr) {
    override fun toString(): String {
        return "$ipFrom,$ipTo"
    }
}

data class IPLookupArgs(val ipsFile: String, val iprsFiles: List<String>)

// Необходимо заменить на более подходящий тип (коллекцию), позволяющий
// эффективно искать диапазон по заданному IP-адресу
typealias IPRangesDatabase = List<IPRange>

fun parseArgs(args: Array<String>): IPLookupArgs? {
    var iprs: List<String> = emptyList()
    if (args.size<=1) {
        return null
    }
    var ips = args[0]
    for (i in 1 until args.size) {
        iprs+=args[i]
    }
    return IPLookupArgs(ips,iprs)
}

fun loadQuery(filename: String): List<IPv4Addr> {
    return File(filename).readLines().map { IPv4Addr(it) }
}

fun loadRanges(filenames: List<String>): IPRangesDatabase {
    var w = mutableListOf<IPRange>()
    for (i in filenames.indices) {
        val fil = File(filenames[i]).readLines()
        for (j in fil) {
            var lis = j.split(',')
            var a1 = IPv4Addr(lis[0])
            var a2 = IPv4Addr(lis[1])
            w+=IPRange(a1,a2)
        }
    }
    return w
}

fun findRange(ranges: IPRangesDatabase, query: IPv4Addr): IPRange? {
    for (element in ranges) {
        ///if (query.intValue.compareTo(element.ipFrom.intValue) >= 0 && query.intValue.compareTo(element.ipTo.intValue) <= 0) {
            if (element.ipFrom <= query && query <= element.ipTo) {
                return element
            }
        ///}
    }
    return null
}


fun main(args: Array<String>) {
    val ipLookupArgs = parseArgs(args) ?: return
    val queries = loadQuery(ipLookupArgs.ipsFile)
    val ranges = loadRanges(ipLookupArgs.iprsFiles)
    queries.forEach { ip ->
        print("$ip: ")
        if (ranges?.let { findRange(it, ip) } == null) {
            println("NO")
        } else {
            println("YES")
        }
    }
}


