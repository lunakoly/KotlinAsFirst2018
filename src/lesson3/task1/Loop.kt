@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import java.lang.Math.pow
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var number = n
    var count = 0

    do {
        count++
        number /= 10
    } while (number > 0)

    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var n1 = 0
    var n2 = 1

    // I virtually define fib(-1) = 1
    // and fib(0) = 0

    for (i in 1 until n) {
        val t = n1
        n1 += n2
        n2 = t
    }

    return n1 + n2
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    val smaller = min(m, n)
    val bigger = max(m, n)
    var lcm = bigger

    while (lcm % smaller != 0)
        lcm += bigger

    return lcm
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (i in 2..sqrt(n.toDouble()).toInt())
        if (n % i == 0)
            return i
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int) = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val smaller = min(m, n)
    val bigger = max(m, n)

    for (i in 2..sqrt(smaller.toDouble()).toInt())
        if (m % i == 0 && n % i == 0)
            return false

    return bigger % smaller != 0
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int) =
        ceil(sqrt(m.toDouble())).toInt() <= floor(sqrt(n.toDouble())).toInt()

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var steps = 0
    var theX = x

    while (theX != 1) {
        steps++

        if (theX % 2 == 0)
            theX /= 2
        else
            theX = 3 * theX + 1
    }

    return steps
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    // we must reduce x to smaller values
    // otherwise calculation can be
    // very painful due to the growing
    // speed of x > 100, etc.
    val theX = x % (2 * PI)
    var result = theX
    var member = theX
    var index = 1.0

    while (true) {
        member = -member * theX * theX / (index + 1) / (index + 2)

        if (abs(member) < eps)
            break

        result += member
        index += 2
    }

    return result
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    // we must reduce x to smaller values
    // otherwise calculation can be
    // very painful due to the growing
    // speed of x > 100, etc.
    val theX = x % (2 * PI)
    var result = 1.0
    var member = 1.0
    var index = 0.0

    while (true) {
        member = -member * theX * theX / (index + 1) / (index + 2)

        if (abs(member) < eps)
            break

        result += member
        index += 2
    }

    return result
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var number = n
    var result = 0

    while (number > 0) {
        result = result * 10 + number % 10
        number /= 10
    }

    return result
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int) = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val first = n % 10
    var number = n / 10

    while (number > 0) {
        if (number % 10 != first)
            return true
        number /= 10
    }

    return false
}

/**
 * Returns the digit located on the index-th
 * position from the right starting from 0
 * @param n target number
 * @param index index of range [0, len-1]
 */
fun digitAt(n: Int, index: Int): Int =  n / pow(10.0, index.toDouble()).toInt() % 10

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var reached = 0
    var number = 1
    var sqr = 0

    while (reached < n) {
        sqr = number * number
        reached += digitNumber(sqr)
        number++
    }

    return digitAt(sqr, reached - n)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var reached = 0
    var number = 0

    var n1 = 0
    var n2 = 1

    while (reached < n) {
        number = n1 + n2
        reached += digitNumber(number)

        val t = n1
        n1 += n2
        n2 = t
    }

    return digitAt(number, reached - n)
}
