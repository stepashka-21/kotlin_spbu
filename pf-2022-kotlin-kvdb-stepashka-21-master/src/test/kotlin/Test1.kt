import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.*

internal class Test1 {
    private val standardOut = System.out
    private val stream = ByteArrayOutputStream()

    @BeforeTest
    fun setUp() {
        System.setOut(PrintStream(stream))
    }

    @AfterTest
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun check1() {
        assertTrue(!check(arrayOf("qwr", "erg", "khre")))
        assertTrue(check(arrayOf("put", "jhc", "jefb")))
        assertTrue(!check(arrayOf("put")))
    }
    @Test
    fun put1() {
        val keys = arrayOf("qwe", "etrf", "tgu")
        val values = arrayOf("qwe1", "etrf1", "tgu1")
        put(keys, values, "f3f3.txt")
        assertEquals(File("f3f3Test.txt").readText(), File("f3f3.txt").readText())
    }
    @Test
    fun get1() {
        get(arrayOf("etrf", "qwe"), "f3f3Test.txt")
        assertEquals("etrf1 qwe1", stream.toString().trim().lines().joinToString("\n"))
        get(arrayOf("fh", "uyi"), "etr.txt")
        assertEquals("", stream.toString().trim().lines().joinToString("\n"))
    }
    @Test
    fun remove1() {
        val keys = arrayOf("qwe", "etrf", "tgu")
        val values = arrayOf("qwe1", "etrf1", "tgu1")
        put(keys, values, "f3f31.txt")
        remove(arrayOf("tgu"), "f3f31.txt")
        assertEquals("tgu1", stream.toString().trim().lines().joinToString("\n"))
        assertEquals(File("f3f3Test1.txt").readText(), File("f3f31.txt").readText())
    }
    @Test
    fun mainputTest() {
        val args = arrayOf("put", "dfg", "q", "qq", "rt", "ytyt", "ytt", "urru", "mjn", "yui")
        main(args)
        assertEquals(File("dfggg.txt").readText(), File("dfg.txt").readText())
    }
    @Test
    fun maingetTest() {
        val args = arrayOf("get", "dfggg", "qq", "rt")
        main(args)
        assertEquals("ytyt", stream.toString().trim().lines().joinToString("\n"))
    }
    @Test
    fun mainremove() {
        val args = arrayOf("remove", "dfggg", "qq")
        main(args)
        assertEquals("", stream.toString().trim().lines().joinToString("\n"))
    }
}
