import kotlin.random.Random

enum class Characters {
    Wizard, Robot, Knight;

    companion object {
        fun createCharacter (type:Characters): Human {
            return when (type) {
                Wizard -> Wizard(
                    Random.nextInt(100, 400),
                    Random.nextInt(2, 100),
                    Random.nextInt(30, 100)
                )
                Robot -> Robot(
                    Random.nextInt(100, 400),
                    Random.nextInt(2, 100),
                    Random.nextInt(35, 100)
                )
                Knight -> Knight(
                    Random.nextInt(100, 400),
                    Random.nextInt(2, 100),
                    Random.nextInt(15, 100)
                )
            }
        }
    }
}