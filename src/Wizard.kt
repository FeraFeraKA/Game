class Wizard(health: Int, power: Int, var mana: Int): Human(health, power) {
    override fun attack(target: Human) {
        target.health -= power
        mana += power * 2
    }

    override fun heal() {
        health += mana * 3
        power += 2
        mana += 30
    }

    override fun strongAttack(target: Human) {
        if (mana > 100) {
            target.health -= (power + mana + 10)
            mana += 20
        }
        else {
            target.health -= power
        }
    }

    override fun getStats(): String {
        return "здоровье: $health, сила: $power, мана: $mana"
    }
}