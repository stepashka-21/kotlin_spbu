import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Task2Test {
    @Test
    fun min3Test1() {
        assertEquals(0, min3(2, 0, 3))
    }

    @Test
    fun min3Test2() {
        assertEquals(-2, min3(1, 0, -2))
    }

    @Test
    fun min3Test3() {
        assertEquals(-3, min3(-2, -3, -1))
    }

    @Test
    fun max3Test1() {
        assertEquals(3, max3(2, 0, 3))
    }

    @Test
    fun max3Test2() {
        assertEquals(-1, max3(-1,-2,-3))
    }

    @Test
    fun max3Test3() {
        assertEquals(2, max3(-3, 2, 0))
    }

    @Test
    fun deg2RadTest1() {
        assertEquals(PI, deg2rad(180.0), 1e-5)
        assertEquals(5*PI, deg2rad(2*360 + 180.0), 1e-5)
    }

    @Test
    fun rad2DegTest1() {
        assertEquals(180.0, rad2deg(PI), 1e-5)
        assertEquals(2*360 + 180.0, rad2deg(5*PI), 1e-5)
    }

    @Test
    fun moreRadDegTests() {
        assertEquals(-PI, deg2rad(-180.0), 1e-5)
        assertEquals(PI/2, deg2rad(90.0), 1e-5)
        assertEquals(0.0, deg2rad(0.0), 1e-5)
        assertEquals(-3*PI, deg2rad(-540.0), 1e-5)
        assertEquals(2*PI, deg2rad(360.0), 1e-5)
        assertEquals(-180.0, rad2deg(-PI), 1e-5)
        assertEquals(90.0, rad2deg(PI/2), 1e-5)
        assertEquals(0.0, rad2deg(0.0), 1e-5)
        assertEquals(-270.0, rad2deg(-3*PI/2), 1e-5)
        assertEquals(180.0/PI, rad2deg(1.0), 1e-5)
    }

} 
