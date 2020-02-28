package com.zhutian.baselibrary.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatImageView

import com.zhutian.baselibrary.R

class RoundAngleImageView : AppCompatImageView {
    private var paint: Paint? = null

    private var radioLeftTop = 0
    private var radioLeftBottom = 0
    private var radioRightTop = 0
    private var radioRightBottom = 0
    private var radio = 0


    private var paint2: Paint? = null

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView)
            radioLeftTop =
                a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radioLeftTop, radioLeftTop)
            radioLeftBottom = a.getDimensionPixelSize(
                R.styleable.RoundAngleImageView_radioLeftBottom,
                radioLeftBottom
            )
            radioRightTop = a.getDimensionPixelSize(
                R.styleable.RoundAngleImageView_radioRightTop,
                radioRightTop
            )
            radioRightBottom = a.getDimensionPixelSize(
                R.styleable.RoundAngleImageView_radioRightBottom,
                radioRightBottom
            )
            radio = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_radio, radio)
        } else {
            val density = context.resources.displayMetrics.density
            radioLeftTop = (radioLeftTop * density).toInt()
            radioLeftBottom = (radioLeftBottom * density).toInt()
            radioRightTop = (radioRightTop * density).toInt()
            radioRightBottom = (radioRightBottom * density).toInt()
            radio = (radio * density).toInt()
        }
        paint = Paint()
        paint!!.color = Color.WHITE
        paint!!.isAntiAlias = true
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        paint2 = Paint()
        paint2!!.xfermode = null
    }

    override fun draw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(bitmap)
        super.draw(canvas2)

        //如果设置了radio
        if (radio != 0) {
            radioLeftTop = radio
            radioLeftBottom = radio
            radioRightTop = radio
            radioRightBottom = radio
            drawLiftUp(canvas2)
            drawRightUp(canvas2)
            drawLiftDown(canvas2)
            drawRightDown(canvas2)
        }
        if (radioLeftTop != 0) {
            drawLiftUp(canvas2)
        }
        if (radioLeftBottom != 0) {
            drawLiftDown(canvas2)
        }
        if (radioRightTop != 0) {
            drawRightUp(canvas2)
        }
        if (radioRightBottom != 0) {
            drawRightDown(canvas2)
        }
        canvas.drawBitmap(bitmap, 0f, 0f, paint2)
        bitmap.recycle()
    }

    private fun drawLiftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, radioLeftTop.toFloat())
        path.lineTo(0f, 0f)
        path.lineTo(radioLeftTop.toFloat(), 0f)
        path.arcTo(
            RectF(
                0f,
                0f,
                (radioLeftTop * 2).toFloat(),
                (radioLeftTop * 2).toFloat()
            ),
            -90f,
            -90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawLiftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, (height - radioLeftBottom).toFloat())
        path.lineTo(0f, height.toFloat())
        path.lineTo(radioLeftBottom.toFloat(), height.toFloat())
        path.arcTo(
            RectF(
                0f,
                (height - radioLeftBottom * 2).toFloat(),
                (0 + radioLeftBottom * 2).toFloat(),
                height.toFloat()
            ),
            90f,
            90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightDown(canvas: Canvas) {
        val path = Path()
        path.moveTo((width - radioRightBottom).toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), (height - radioRightBottom).toFloat())
        path.arcTo(
            RectF(
                (width - radioRightBottom * 2).toFloat(),
                (height - radioRightBottom * 2).toFloat(),
                width.toFloat(),
                height.toFloat()
            ), 0f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), radioRightTop.toFloat())
        path.lineTo(width.toFloat(), 0f)
        path.lineTo((width - radioRightTop).toFloat(), 0f)
        path.arcTo(
            RectF(
                (width - radioRightTop * 2).toFloat(),
                0f,
                width.toFloat(),
                (0 + radioRightTop * 2).toFloat()
            ),
            -90f,
            90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }
}
