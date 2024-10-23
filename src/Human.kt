abstract class Human(var health: Int, var power: Int) {
    abstract fun getStats(): String

    open fun attack(target: Human) {

    }

    open fun heal() {

    }

    open fun strongAttack(target: Human) {

    }

    open fun charge(target: Human) {

    }

    open fun heavyAttack(target: Human) {

    }
}