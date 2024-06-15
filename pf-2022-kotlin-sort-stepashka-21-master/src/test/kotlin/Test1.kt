import java.io.File
import kotlin.test.*

internal class Test1 {
    @Test
    fun testargsFilesFlags() {
        val a1 = filesFlags(arrayOf("-f", "ip.txt")).first
        val a2 = filesFlags(arrayOf("-f", "ip.txt")).second
        assertEquals(a1.toList(), arrayOf("-f").toList())
        assertEquals(a2.toList(), arrayOf("ip.txt").toList())
    }

    @Test
    fun testacheckFilesFlags() {
        val files1 = arrayOf("in.txt", "ip.txt")
        val flags1 = arrayOf("-n", "-v", "-f")
        val args1 = arrayOf("-n", "-v", "-f", "in.txt", "ip.txt")
        val files2 = arrayOf("in.txt", "ip.txt")
        val flags2 = arrayOf("-o", "-v", "-f")
        val args2 = arrayOf("-o", "-v", "-f", "in.txt", "ip.txt")
        assertTrue(!checkFilesFlags(files1, flags1, args1))
        assertTrue(!checkFilesFlags(files2, flags2, args2))
    }

    @Test
    fun testmain1() {
        val args = arrayOf("-n", "-u", "-o", "FIL/fileNew.txt", "FIL/fileOld.txt")
        main(args)
        assertEquals(File("FIL/fileNew.txt").readText(), File("FIL/fileNew1.txt").readText())
    }
    @Test
    fun testmain2() {
        val args = arrayOf("-v", "-e", "-o", "FIL/fileNeww.txt", "FIL/fileOld.txt")
        main(args)
        assertEquals(File("FIL/fileNeww.txt").readText(), File("FIL/fileNew2.txt").readText())
    }
    @Test
    fun testmain3() {
        val args = arrayOf("r", "-o", "FIL/fileNew.txt", "FIL/fileOld.txt")
        main(args)
        assertEquals(File("FIL/fileNew.txt").readText(), File("FIL/fileNew3.txt").readText())
    }
}
