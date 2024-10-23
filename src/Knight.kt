class Knight(health: Int, power: Int, var defense: Int): Human(health, power) {
    override fun attack(target: Human) {
        target.health -= (power + 12 + defense + 4)
        defense += 12
    }

    override fun heal() {
        defense += 20
        power += 3
        health += (defense * 2)
    }

    override fun heavyAttack(target: Human) {
        target.health -= defense * 2
        if (defense > 15) {
            defense -= 15
        }
    }

    override fun getStats(): String {
        return "здоровье: $health, сила: $power, защита: $defense"
    }
}