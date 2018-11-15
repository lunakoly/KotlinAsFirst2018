@file:Suppress("UNUSED_PARAMETER")
package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle) = max(center.distance(other.center) - radius - other.radius, 0.0)

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point) = center.distance(p) <= radius

    // I'd suggest using 'sqr(center.x - p.x) + sqr(center.y - p.y) <= sqr(radius)'
    // but it causes some errors with the last test, so i just added a little delta
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    val length: Double get() = begin.distance(end)
    val center: Point get() = Point((begin.x + end.x) / 2, (begin.y + end.y) / 2)

    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2)
        throw IllegalArgumentException("Not enough points")

    var longestSegment = Segment(points[0], points[1])

    for (i in 0 until points.size - 1)
        for (j in i + 1 until points.size) {
            val segment = Segment(points[i], points[j])

            if (segment.length > longestSegment.length)
                longestSegment = segment
        }

    return longestSegment
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val center = diameter.center
    val radius = max(center.distance(diameter.begin), center.distance(diameter.end))
    return Circle(center, radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x = (cos(other.angle) * b - cos(angle) * other.b) / sin(other.angle - angle)

        // when angle -> PI/2 the calculations tend to
        // be inaccurate. So let's take the line with the
        // biggest |cos(angle)|

        return if (abs(cos(angle)) > abs(cos(other.angle)))
            Point(x, (x * sin(angle) + b) / cos(angle))
        else
            Point(x, (x * sin(other.angle) + other.b) / cos(other.angle))
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment) = lineByPoints(s.begin, s.end)

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val angle = atan2(b.y - a.y, b.x - a.x)
    return Line(a, transformAngle(angle))
}

/**
 * An attempt to fix the bug with tiny negative
 * doubles summed with PI
 * @param angle the angle to be transformed to fit the [0; PI)
 */
fun transformAngle(angle: Double): Double {
    return when {
        angle < 0 && angle > -PI.ulp -> 0.0
        angle >= PI -> angle - PI
        angle >= 0 -> angle
        else -> angle + PI
    }
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val center = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    val angle = atan2(b.y - a.y, b.x - a.x) + PI/2
    return Line(center, transformAngle(angle))
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2)
        throw IllegalArgumentException("Not enough circles")

    var furthestPair = circles[0] to circles[1]

    for (i in 0 until circles.size - 1)
        for (j in i + 1 until circles.size) {
            val newPair = circles[i] to circles[j]

            if (furthestPair.first.distance(furthestPair.second) > newPair.first.distance(newPair.second))
                furthestPair = newPair
        }

    return furthestPair
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val bisector1 = bisectorByPoints(a, b)
    val bisector2 = bisectorByPoints(b, c)
    val center = bisector1.crossPoint(bisector2)
    val radius = max(max(center.distance(a), center.distance(b)), center.distance(c))
    return Circle(center, radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty())
        throw IllegalArgumentException("No points found")

    if (points.size == 1)
        return Circle(points[0], 0.0)

    if (points.size == 2)
        return circleByDiameter(Segment(points[0], points[1]))

    var minimumRadiusCircle = circleByThreePoints(points[0], points[1], points[2])
    var isFirst = true

    for (i in 0 until points.size - 2)
        for (j in i + 1 until points.size - 1)
            for (k in j + 1 until points.size) {
                val newCircle = circleByThreePoints(points[i], points[j], points[k])
                val containsAll = points.all { newCircle.contains(it) }

                if (containsAll) {
                    if (isFirst || newCircle.radius < minimumRadiusCircle.radius) {
                        minimumRadiusCircle = newCircle
                        isFirst = false
                    }
                }
            }

    for (i in 0 until points.size - 1)
        for (j in i + 1 until points.size) {
            val newCircle = circleByDiameter(Segment(points[i], points[j]))
            val containsAll = points.all { newCircle.contains(it) }

            if (containsAll)
                if (newCircle.radius < minimumRadiusCircle.radius)
                    minimumRadiusCircle = newCircle
        }

    return minimumRadiusCircle
}

