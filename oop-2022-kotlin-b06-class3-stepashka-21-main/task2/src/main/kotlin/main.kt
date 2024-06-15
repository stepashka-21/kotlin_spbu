abstract class AbstractAnimal: Talkable {
    override fun talk() = println("$sound-".repeat(2) + sound)

}
interface Talkable {
    var sound: String
    fun talk()
}
class Cat : AbstractAnimal() {
    override var sound = "мяу"
}

class Dog : AbstractAnimal() {
    override var sound = "гав"
}

class Goose : AbstractAnimal() {
    override var sound = "га"
}
class RobotVacuum : Talkable {
    override var sound = "ур"
    override fun talk() {
        println("$sound-".repeat(3) + sound)
    }
}

fun main() {
    val animals = listOf(Cat(), Dog(), Goose()) // что произойдёт, если добавить в список RobotVacuum?
    animals.forEach { it.talk() }
}