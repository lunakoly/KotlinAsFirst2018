@file:Suppress("UNUSED_PARAMETER")
package lesson2.task2

import lesson1.task1.sqr
import lesson1.task1.trackLength
import java.lang.Math.pow
import kotlin.math.abs

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Returns the sum of digits of the number
 * @param number target number
 */
fun sumDigits(number: Int) = number.toString().sumBy { char -> char.toInt() }

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int) = sumDigits(number) == 2 * sumDigits(number / 100)

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int) =
        x1 == x2 || y1 == y2 || abs(x1 - x2) == abs(y1 - y2)

/**
 * Returns true if the year is leap
 * @param year the year to test
 */
fun isLeadYear(year: Int) = when {
    year % 400 == 0 -> true
    year % 100 == 0 -> false
    year % 4 == 0 -> true
    else -> false
}

/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    val isLeap = isLeadYear(year)
    val februaryLength = if (isLeap) 29 else 28

    return when {
        month == 2 -> februaryLength
        (month + month / 8) % 2 == 0 -> 30
        else -> 31
    }
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean = trackLength(x1, y1, x2, y2) <= r2 - r1

/**
 * Returns true if the brick with that side being
 * moved forward can pass the hole
 */
fun brickSidePasses(a: Int, b: Int, r: Int, s: Int) = (a <= r && b <= s) || (a <= s && b <= r)

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean =
        brickSidePasses(a, b, r, s) || brickSidePasses(a, c, r, s) || brickSidePasses(b, c, r, s)
