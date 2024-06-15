import java.io.File
import kotlin.test.*

internal class Task1Test {
    @Test
    fun testApplyOperation() {
        assertEquals(10, applyOperation('*', 5, 2))
        assertEquals(2, applyOperation('/', 10, 5))
        assertFailsWith<DivisionByZero> { applyOperation('/', 1, 0) }
        assertFailsWith<UnsupportedOperation> { applyOperation('+', 0, 0) }
    }
    @Test
    fun testApplySchema() {
        val f = applySchema("/**/")
        val numbers1 = listOf(3,3,4,3,33,3)
        assertFailsWith<TooManyNumbers> { f(numbers1) }
        val numbers2 = listOf(1,2)
        assertFailsWith<NotEnoughNumbers> { f(numbers2) }
        val numbers3 = listOf(4,2,5,1,3)
        assertEquals(3,f(numbers3))
    }
    @Test
    fun testFormatLHS() {
        assertEquals("22/3*2", formatLHS("/*", arrayListOf("22", "3", "2")))
    }
    @Test
    fun testProcessString() {
        assertEquals("12/2*3/5=3", processString("/*/","12,2,3,5"))
        assertFailsWith<IncorrectNumbers> { processString("/*/*","1,2,3,x,4") }
    }
    @Test
    fun testProcessFiles() {
        processFiles("sc.txt","dat.txt","out.txt")
        assertEquals(File("outTest.txt").readLines(), File("out.txt").readLines())
    }
    @Test
    fun testMain() {
        assertFailsWith<IncorrectArguments> { main(arrayOf("xzv", "sfds")) }
    }
}