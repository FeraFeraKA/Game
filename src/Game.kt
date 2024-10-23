import java.util.*
import kotlin.random.Random
import kotlin.system.exitProcess

fun fighterCheck(): Int {
    val input = Scanner(System.`in`)
    var character = 0
    do {
        character = input.nextInt()
        if (character !in 1..3) println("Неверный ввод. Попробуйте ещё раз")
    } while (character !in 1..3)
    return character
}

fun defenderCheck(fighter: Int): Int {
    val input = Scanner(System.`in`)
    var character = 0
    do {
        character = input.nextInt()
        if (character == fighter) println("Неверный ввод. Нельзя атаковать себя. Попробуйте ещё раз")
    } while (character == fighter)
    return character
}

fun main() {
    val input = Scanner(System.`in`)
    val players = mutableListOf<Human>()
    var countOfMove = 0
    println("Как выбираются характеристики? 1 - случайно, 2 - вводом игрока")
    var choice = input.nextInt()
    when (choice) {
        1 -> {
            players.add(Characters.createCharacter(Characters.Wizard))
            players.add(Characters.createCharacter(Characters.Robot))
            players.add(Characters.createCharacter(Characters.Knight))
        }
        2 -> {
            println("Введите HP волшебника, его силу и ману")
            players.add(Wizard(input.nextInt(), input.nextInt(), input.nextInt()))
            println("Введите HP робота, его силу и заряд батареи")
            players.add(Robot(input.nextInt(), input.nextInt(), input.nextInt()))
            println("Введите HP рыцаря, его силу и защиту")
            players.add(Knight(input.nextInt(), input.nextInt(), input.nextInt()))
        }
        else -> {
            println("Неверный ввод")
            exitProcess(0)
        }
    }

    println("Как будут выполняться действия? 1 - автоматически, 2 - выбором игрока")
    choice = input.nextInt()
    when (choice) {
        1 -> {
            println("------------")
            println("Игра началась")
            println("------------")
            println("Изначальные характеристики:\n" +
                    "Wizard - ${players[0].getStats()}\n" +
                    "Robot - ${players[1].getStats()}\n" +
                    "Knight - ${players[2].getStats()}")
            println("<!---------------!>")

            while (players.count {it.health > 0} > 2) {
                val character = Random.nextInt(players.size)
                val activeCharacter = players[character]

                countOfMove++
                println("Начало хода\nХод: $countOfMove")
                println("<!---------------!>")

                if (activeCharacter.health <= 0) continue

                val stringCharacter = when (activeCharacter) {
                    is Wizard -> "Wizard"
                    is Robot -> "Robot"
                    is Knight -> "Knight"
                    else -> "Unknown"
                }

                val opponents = players.filter {it != activeCharacter && it.health > 0}
                if (opponents.isNotEmpty()) {
                    val target = opponents[Random.nextInt(opponents.size)]
                    val action = Random.nextInt(3)

                    println("Атакующий игрок - $stringCharacter - ${activeCharacter.getStats()}")
                    println("Атакуемый игрок - ${target::class.simpleName} - ${target.getStats()}")
                    println("------------")

                    when (action) {
                        0 -> {
                            println("$stringCharacter атакует ${target::class.simpleName}")
                            activeCharacter.attack(target)
                        }
                        1 -> {
                            println("$stringCharacter исцеляется и получает усиление характеристик")
                            activeCharacter.heal()
                        }
                        2 -> {
                            when (stringCharacter) {
                                "Wizard" -> {
                                    println("$stringCharacter использует сильную атаку на ${target::class.simpleName}")
                                    activeCharacter.strongAttack(target)
                                }
                                "Robot" -> {
                                    println("$stringCharacter заряжается")
                                    activeCharacter.charge(target)
                                }
                                "Knight" -> {
                                    println("$stringCharacter использует тяжёлую атаку на ${target::class.simpleName}")
                                    activeCharacter.heavyAttack(target)
                                }
                            }
                        }
                    }

                    println("------------")
                    println("Характеристики атакующего игрока - ${activeCharacter.getStats()}")
                    if (target.health < 0) target.health = 0
                    println("Характеристики атакуемого игрока - ${target.getStats()}")
                    if (target.health == 0) println("------------") else println("<!---------------!>")

                    if (players.count {it.health <= 0} > 0) {
                        println("Игра закончена: ${target::class.simpleName} умер")
                        println("------------")
                        break
                    }
                }
            }
        }
        2 -> {
            println("------------")
            println("В игре действуют следующие правила:\n" +
                    "При выборе атакующего и атакуемого персонажа выбираем так: 1 - волшебник, 2 - робот, 3 - рыцарь\n" +
                    "При выборе вида атаки: 1 - атака, 2 - исцеление и усиление, 3 - особая атака каждого класса")
            println("------------")
            println("Игра началась")
            println("------------")
            println("Изначальные характеристики:\n" +
                    "Wizard - ${players[0].getStats()}\n" +
                    "Robot - ${players[1].getStats()}\n" +
                    "Knight - ${players[2].getStats()}")
            println("<!---------------!>")

            while (players.count {it.health > 0} > 2) {
                countOfMove++
                println("Начало хода\nХод: $countOfMove")
                println("<!---------------!>")

                println("Какой персонаж будет атаковать?")
                val character = fighterCheck() - 1
                val activeCharacter = players[character]

                if (activeCharacter.health <= 0) continue

                val stringCharacter = when (activeCharacter) {
                    is Wizard -> "Wizard"
                    is Robot -> "Robot"
                    is Knight -> "Knight"
                    else -> "Unknown"
                }

                println("Кого будет атаковать $stringCharacter?")
                val target = players[defenderCheck(character + 1) - 1]

                println("Какое действите он будет совершать по отношению к ${target::class.simpleName} или себе?")
                val action = fighterCheck() - 1
                println("------------")

                println("Атакующий игрок - $stringCharacter - ${activeCharacter.getStats()}")
                println("Атакуемый игрок - ${target::class.simpleName} - ${target.getStats()}")
                println("------------")

                when (action) {
                    0 -> {
                        println("$stringCharacter атакует ${target::class.simpleName}")
                        activeCharacter.attack(target)
                    }
                    1 -> {
                        println("$stringCharacter исцеляется и получает усиление характеристик")
                        activeCharacter.heal()
                    }
                    2 -> {
                        when (stringCharacter) {
                            "Wizard" -> {
                                println("$stringCharacter использует сильную атаку на ${target::class.simpleName}")
                                activeCharacter.strongAttack(target)
                            }
                            "Robot" -> {
                                println("$stringCharacter заряжается")
                                activeCharacter.charge(target)
                            }
                            "Knight" -> {
                                println("$stringCharacter использует тяжёлую атаку на ${target::class.simpleName}")
                                activeCharacter.heavyAttack(target)
                            }
                        }
                    }
                }

                println("------------")
                println("Характеристики атакующего игрока - ${activeCharacter.getStats()}")
                if (target.health < 0) target.health = 0
                println("Характеристики атакуемого игрока - ${target.getStats()}")
                if (target.health == 0) println("------------") else println("<!---------------!>")

                if (players.count {it.health <= 0} > 0) {
                    println("Игра закончена: ${target::class.simpleName} умер")
                    println("------------")
                    break
                }
            }
        }
        else -> {
            println("Неверный ввод")
            exitProcess(0)
        }
    }
    println("Результаты игры: ")
    val sortedPlayers = players.sortedByDescending { it.health }
    var count = 1
    for (player in sortedPlayers) {
        println("$count. ${player::class.simpleName} - ${player.getStats()}")
        count++
    }
}
