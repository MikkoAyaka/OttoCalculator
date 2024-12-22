package priv.mikkoayaka.ottocalculator

import kotlin.random.Random

fun main() {
    println("请输入总人数 n：")
    val n = readlnOrNull()?.toIntOrNull() ?: run {
        println("请输入有效的总人数！")
        return
    }

    println("请输入第 x 个人的位置 (1 到 $n)：")
    val x = readlnOrNull()?.toIntOrNull() ?: run {
        println("请输入有效的位序！")
        return
    }

    if (x < 1 || x > n) {
        println("位序必须在 1 到 $n 之间！")
        return
    }

    println("请输入第 $x 个人的点数 v (1 到 99)：")
    val v = readlnOrNull()?.toIntOrNull() ?: run {
        println("请输入有效的点数！")
        return
    }

    if (v < 1 || v > 99) {
        println("点数必须在 1 到 99 之间！")
        return
    }

    val simulations = 100000

    val probability = calculateProbability(n, x, v, simulations)
    println("第 $x 个人的点数为 $v 时，他抽中的概率为：${"%.6f".format(probability * 100)}%")
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
