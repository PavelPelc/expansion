package bi.ui

import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import java.io.File
import kotlin.random.Random


class UiUtils {

    companion object {

        val rnd = Random(System.currentTimeMillis()/100)

        fun niceDouble(d:Double) = (d*100).toInt().toDouble()/100.0

        // point q lies on line segment 'pr'
        fun onSegment(p: Point, q: Point, r: Point): Boolean {
            return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(
                    p.y,
                    r.y
                ) && q.y >= Math.min(p.y, r.y)
        }

        // To find orientation of ordered triplet (p, q, r).
        // The function returns following values
        // 0 --> p, q and r are collinear
        // 1 --> Clockwise
        // 2 --> Counterclockwise
        fun orientation(p: Point, q: Point, r: Point): Int {
            // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
            // for details of below formula.
            val `val` = (q.y - p.y) * (r.x - q.x) -
                    (q.x - p.x) * (r.y - q.y)
            if (`val` == 0.0) return 0 // collinear
            return if (`val` > 0) 1 else 2 // clock or counterclock wise
        }

        // The main function that returns true if line segment 'p1q1'
        // and 'p2q2' INNER intersect. Border points are omitted.
        fun doIntersect(abX: Double, abY:Double, aeX: Double, aeY:Double, bbX: Double, bbY:Double, beX: Double, beY:Double): Boolean {

            val p1 = Point(abX, abY)
            val q1 =  Point(aeX, aeY)
            val p2 =  Point(bbX, bbY)
            val q2 =  Point(beX, beY)
            // Find the four orientations needed for general and
            // special cases
            val o1 = orientation(p1, q1, p2)
            val o2 = orientation(p1, q1, q2)
            val o3 = orientation(p2, q2, p1)
            val o4 = orientation(p2, q2, q1)

            // General case
            return o1 != o2 && o1 != 0 && o2 != 0 && o3 != o4 && o3 != 0 && o4 != 0
        }

        fun grayAndWhite(src: Image): Image {
            val result = WritableImage(src.width.toInt(), src.height.toInt())
            val reader = src.pixelReader
            val writer = result.pixelWriter
            for (x in 0 until src.width.toInt()) for (y in 0 until src.height.toInt() ) {
                val pixel = reader.getColor(x,y)
                var grey = (( pixel.red + pixel.green + pixel.blue )*16 / 3 + 255-32).toInt()
                writer.setColor(x,y, Color.rgb(grey,grey,grey))
            }
            return result
        }


    }


}

data class Point(val x:Double, val y: Double)