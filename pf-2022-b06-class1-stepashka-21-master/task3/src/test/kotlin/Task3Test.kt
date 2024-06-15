import kotlin.test.Test
import kotlin.test.assertEquals

class Task3Test {
    @Test
    fun fTest() {
        assertEquals(1.0, f(0.0), 1e-5)
        assertEquals(-1.0, f(1.9), 1e-5)
        assertEquals(0.0, f(-1.0), 1e-5)
        assertEquals(1.0, f(2.0), 1e-5)
        assertEquals(1.0, f(2.5), 1e-5)
    }

    @Test
    fun numberOfDaysTest() {
        assertEquals(365, numberOfDays(2021))
        assertEquals(365, numberOfDays(900))
        assertEquals(366, numberOfDays(80))
        assertEquals(366, numberOfDays(2000))
    }

    @Test
    fun rotate2Test() {
        assertEquals('С', rotate2('С', 1, -1))
        assertEquals('С', rotate2('С', -1, 1))
        assertEquals('В', rotate2('С', 1, 2))
        assertEquals('Ю', rotate2('С', -1, -1))
        assertEquals('С', rotate2('С', 2, 2))
    }

    @Test
    fun ageDescriptionTest() {
        assertEquals("сорок два года", ageDescription(42))
        assertEquals("тридцать лет", ageDescription(30))
        assertEquals("двадцать один год", ageDescription(21))
        assertEquals("шестьдесят четыре года", ageDescription(64))
        assertEquals("пятьдесят пять лет", ageDescription(55))
    }

}
