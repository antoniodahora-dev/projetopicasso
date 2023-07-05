package co.a3tecnology.picasso

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.FloatRange
import com.squareup.picasso.Transformation

class AlfaTransformation(@FloatRange(from = 0.0 , to = 1.0) alpha: Float) : Transformation {

    private val alfa: Int = (alpha * 255).toInt()

    override fun transform(source: Bitmap): Bitmap {
        val height = source.height
        val width = source.width

        val alphaBitmap = Bitmap.createBitmap(width, height, source.config)

        val canvas = Canvas(alphaBitmap)
        canvas.drawARGB(0,0 ,0 ,0)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.alpha = alfa

        canvas.drawBitmap(source, 0f, 0f, paint)

        if (alphaBitmap != source) {
            source.recycle()
        }

        return alphaBitmap
    }

    override fun key(): String {
        return "alfaTransformation"
    }

}