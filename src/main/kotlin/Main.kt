package priv.mikkoayaka.ottocalculator

import kotlin.random.Random

fun main() {
    println("主人~今晚总共有多少位客人呢~")
    val n = readlnOrNull()?.toIntOrNull() ?: run {
        println("是没听过的数字呢")
        return
    }

    if (n < 1 ||  n > 99) {
        println("我最多只能服侍99个人哦")
        return
    }

    println("呜呜~请告诉我主人您是第几位呢？")
    val x = readlnOrNull()?.toIntOrNull() ?: run {
        println("……？")
        return
    }

    if (x < 1 || x > n) {
        println("您只能排在 1 到 $n 之间哦")
        return
    }

    println("最、最后，请主人告诉我您的大小是……")
    val v = readlnOrNull()?.toIntOrNull() ?: run {
        println("不要说那种奇怪的话啦~")
        return
    }

    if (v < 1 || v > 99) {
        println("嗯~只能在 1 到 99 之间哦")
        return
    }

    val simulations = 100000

    val probability = calculateProbability(n, x, v, simulations)
    println("主、主人的大小为 $v 的时候成功的概率为：${"%.6f".format(probability * 100)}%……害羞到抬不起头了喵~")
}

fun calculateProbability(totalPlayers: Int, playerPosition: Int, playerScore: Int, trials: Int): Double {
    var winCount = 0
    repeat(trials) {
        val scorePool = mutableSetOf<Int>()
        for (i in 1..99) scorePool.add(i)
        scorePool.remove(playerScore)
        // 模拟每个人的掷骰子点数
        val scores = mutableSetOf<Int>()
        while (scores.size < totalPlayers - 1) {
            val score = scorePool.random()
            scores.add(score) // 生成 1 到 99 的随机点数
            scorePool.remove(score)
        }

        // 将第 x 个人的点数 v 插入到分数集合
        val finalScores = scores.toMutableList()
        finalScores.add(playerPosition - 1,playerScore)

        // 找到最大点数的索引
        val maxScore = finalScores.maxOrNull()
        val winnerIndex = finalScores.indexOf(maxScore)

        // 检查第 x 个人是否是最大点数的拥有者
        if (winnerIndex == playerPosition - 1) {
            winCount++
        }
    }

    return winCount.toDouble() / trials
}
