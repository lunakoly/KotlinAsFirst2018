@file:Suppress("UNUSED_PARAMETER")
package lesson8.task2

import kotlin.math.PI
import kotlin.math.atan2

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String = if (!inside()) "" else ('a' + column - 1) + row.toString()
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (!Regex("""[a-h][1-8]""").matches(notation))
        throw IllegalArgumentException()

    val column = notation[0] - 'a' + 1
    val row = notation[1] - '0'
    return Square(column, row)
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()

    var steps = 0

    if (start.column != end.column)
        steps++

    if (start.row != end.row)
        steps++

    return steps

    // I wish I could write
    // return (start.column != end.column) + (start.row != end.row)
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val trajectory = mutableListOf(start)

    if (trajectory.last().column != end.column)
        trajectory.add(Square(end.column, trajectory.last().row))

    if (trajectory.last().row != end.row)
        trajectory.add(Square(trajectory.last().column, end.row))

    return trajectory
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()

    if ((start.column + start.row) % 2 != (end.row + end.column) % 2)
        return -1

    var steps = 0

    if (start.column + start.row != end.row + end.column)
        steps++

    if (start.row - start.column != end.row - end.column)
        steps++

    return steps
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    if ((start.column + start.row) % 2 != (end.row + end.column) % 2)
        return emptyList()

    val trajectory = mutableListOf(start)
    val mainDifference = (end.row + end.column - trajectory.last().row - trajectory.last().column) / 2
    val secondaryDifference = (end.row - end.column - trajectory.last().row + trajectory.last().column) / 2

    if (mainDifference != 0)
        trajectory.add(Square(
                trajectory.last().column + mainDifference,
                trajectory.last().row + mainDifference))

    if (secondaryDifference != 0)
        trajectory.add(Square(
                trajectory.last().column - secondaryDifference,
                trajectory.last().row + secondaryDifference))

    // we might have got an out of bounds square
    // let's check if that's true and in that case
    // reapply transitions in another order

    if (trajectory.size == 3 && !trajectory[1].inside())
        trajectory[1] = Square(
                start.column - secondaryDifference,
                start.row + secondaryDifference)

    return trajectory
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()
    return kingTrajectory(start, end).size - 1
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val trajectory = mutableListOf(start)

    while (trajectory.last() != end) {
        val dy = end.row - trajectory.last().row
        val dx = end.column - trajectory.last().column

        // we have 8 directions -> PI/4 ranges
        // let's get rid of [-PI/8; +PI/8)
        val direction = atan2(dy.toDouble(), dx.toDouble()) - PI/8
        val next = when {
            direction <= -PI/4 * 3 -> Square(trajectory.last().column - 1, trajectory.last().row - 1)
            direction <= -PI/2 -> Square(trajectory.last().column, trajectory.last().row - 1)
            direction <= -PI/4 -> Square(trajectory.last().column + 1, trajectory.last().row - 1)
            direction <= 0 -> Square(trajectory.last().column + 1, trajectory.last().row)
            direction <= PI/4 -> Square(trajectory.last().column + 1, trajectory.last().row + 1)
            direction <= PI/2 -> Square(trajectory.last().column, trajectory.last().row + 1)
            direction <= PI/4 * 3  -> Square(trajectory.last().column - 1, trajectory.last().row + 1)
            else -> Square(trajectory.last().column - 1, trajectory.last().row)
        }

        trajectory.add(next)
    }

    return trajectory
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()
    return knightTrajectory(start, end).size - 1
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> {
    // traceMap[cell] -> shortest path previous cell
    // hopsMap[cell] -> min count of hops we need to perform
    // to get to that cell

    val traceMap = mutableMapOf<Square, Square?>()
    val hopsMap = mutableMapOf<Square, Int>()

    fun <T> MutableList<T>.pop() = this.removeAt(lastIndex)

    // depth-first search parameters for i-th square

    val parentsStack = mutableListOf<Square?>(null)
    val traceStack = mutableListOf(start)
    val hopsStack = mutableListOf(0)

    while (traceStack.isNotEmpty()) {
        val parent = parentsStack.pop()
        val square = traceStack.pop()
        val hop = hopsStack.pop()

        if (!square.inside())
            continue

        // if this cell can potentially give us a shorter way

        if (hopsMap.getOrDefault(square, Int.MAX_VALUE) > hop) {
            traceMap[square] = parent
            hopsMap[square] = hop

            // add all the near cells to visiting stack

            for (i in 0..7) {
                val sign1 = 2 * (i % 2) - 1
                val sign2 = 2 * ((i / 2) % 2) - 1
                val step = if ((i / 4) % 2 == 0) 2 else 1

                traceStack.add(Square(square.column + sign1 * step, square.row + sign2 * (3 - step)))
                parentsStack.add(square)
                hopsStack.add(hop + 1)
            }
        }
    }

    // start walking back to front and collect the whole path

    val trajectory = mutableListOf<Square>()
    var current: Square? = end

    while (current != null) {
        trajectory.add(current)
        current = traceMap[current]
    }

    return trajectory.reversed()
}
