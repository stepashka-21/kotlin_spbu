class Human {
    val greeting = "Привет, я человек!"
}

class Dog {
    val bark = "Гав!"
}

class Alien {
    val command = "Ты меня не видишь"
}

interface Creature {
    var message: String
}

class HumanAdapter (speech: Human) : Creature {
    override var message = speech.greeting
}
class DogAdapter(speech: Dog) : Creature {
    override var message = speech.bark
}
class AlienAdapter(speech: Alien) : Creature {
    override var message = speech.command
}
class RobotVacuumAdapter(speech: Talkable): Creature{
    override var message = speech.sound
}
class RobotVacuum : Talkable {
    override var sound = "ур"
    override fun talk() {
        println("$sound-".repeat(3) + sound)
    }
}

fun main() {
    val creatures = listOf(HumanAdapter(Human()), DogAdapter(Dog()), AlienAdapter(Alien()), RobotVacuumAdapter(RobotVacuum()))
    
    println("Все сообщения:")
    creatures.forEach { println(it.message) }
}