class Robot(health: Int, power: Int, var battery: Int): Human(health, power) {
    override fun attack(target: Human) {
        target.health -= (power + battery - 3)
        battery -= 10
    }

    override fun heal() {
        health += (power + battery)
        power += 2
        battery += power + 55
    }

    override fun charge(target: Human) {
        battery += power + 100
    }

    override fun getStats(): String {
        return "здоровье: $health, сила: $power, заряд батареи: $battery"
    }
}