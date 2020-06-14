package com.wjx.android.weather.common.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.wjx.android.weather.R
import com.wjx.android.weather.common.util.DisplayUtil
import java.util.*

/**
 * Created by user on 2016/10/19.
 */
class HourlyView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mHeight = 0
    private var mWidth = 0
    private var tempBaseTop = 0
    private var tempBaseBottom  = 0
    private var bitmapPaint: Paint? = null
    private var windyBoxPaint: Paint? = null
    private var linePaint: Paint? = null
    private var pointPaint: Paint? = null
    private var dashLinePaint: Paint? = null
    private var textPaint: TextPaint? = null
    private var listItems: MutableList<HourlyItem>? = null
    private var maxScrollOffset = 0 //滚动条最长滚动距离
    private var scrollOffset = 0 //滚动条偏移量
    private var currentItemIndex = 0 //当前滚动的位置所对应的item下标
    private val currentWeatherRes = -1
    private val maxTemp = 26
    private val minTemp = 21
    private val maxWindy = 5
    private val minWindy = 2
    private fun init() {
        mWidth =
            MARGIN_LEFT_ITEM + MARGIN_RIGHT_ITEM + ITEM_SIZE * ITEM_WIDTH
        mHeight = 700 //暂时先写死
        tempBaseTop = (700 - bottomTextHeight) / 4
        tempBaseBottom = (700 - bottomTextHeight) * 2 / 3
        initHourItems()
        initPaint()
    }

    private fun initPaint() {
        pointPaint = Paint()
        pointPaint!!.color = Color.WHITE
        pointPaint!!.isAntiAlias = true
        pointPaint!!.textSize = 8f
        linePaint = Paint()
        linePaint!!.color = Color.WHITE
        linePaint!!.isAntiAlias = true
        linePaint!!.style = Paint.Style.STROKE
        linePaint!!.strokeWidth = 5f
        dashLinePaint = Paint()
        dashLinePaint!!.color = Color.WHITE
        val effect: PathEffect = DashPathEffect(floatArrayOf(5f, 5f, 5f, 5f), 1F)
        dashLinePaint!!.pathEffect = effect
        dashLinePaint!!.strokeWidth = 3f
        dashLinePaint!!.isAntiAlias = true
        dashLinePaint!!.style = Paint.Style.STROKE
        windyBoxPaint = Paint()
        windyBoxPaint!!.textSize = 1f
        windyBoxPaint!!.color = Color.WHITE
        windyBoxPaint!!.alpha = windyBoxAlpha
        windyBoxPaint!!.isAntiAlias = true
        textPaint = TextPaint()
        textPaint!!.textSize = DisplayUtil.sp2px(context, 12)
        textPaint!!.color = Color.WHITE
        textPaint!!.isAntiAlias = true
        bitmapPaint = Paint()
        bitmapPaint!!.isAntiAlias = true
    }

    //简单初始化下，后续改为由外部传入
    private fun initHourItems() {
        listItems = ArrayList()
        for (i in 0 until ITEM_SIZE) {
            var time: String
            time = if (i < 10) {
                "0$i:00"
            } else {
                "$i:00"
            }
            val left =
                MARGIN_LEFT_ITEM + i * ITEM_WIDTH
            val right = left + ITEM_WIDTH - 1
            val top = (mHeight - bottomTextHeight +
                    (maxWindy - WINDY[i]) * 1.0 / (maxWindy - minWindy) * windyBoxSubHight
                    - windyBoxMaxHeight).toInt()
            val bottom = mHeight - bottomTextHeight
            val rect = Rect(left, top, right, bottom)
            val point =
                calculateTempPoint(left, right, TEMP[i])
            val hourItem = HourlyItem()
            hourItem.windyBoxRect = rect
            hourItem.time = time
            hourItem.windy = WINDY[i]
            hourItem.temperature = TEMP[i]
            hourItem.tempPoint = point
            hourItem.res = WEATHER_RES[i]
            (listItems as ArrayList<HourlyItem>).add(hourItem)
        }
    }

    private fun calculateTempPoint(left: Int, right: Int, temp: Int): Point {
        val minHeight = tempBaseTop.toDouble()
        val maxHeight = tempBaseBottom.toDouble()
        val tempY =
            maxHeight - (temp - minTemp) * 1.0 / (maxTemp - minTemp) * (maxHeight - minHeight)
        return Point((left + right) / 2, tempY.toInt())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in listItems!!.indices) {
            val rect = listItems!![i].windyBoxRect
            val point = listItems!![i].tempPoint
            //画风力的box和提示文字
            onDrawBox(canvas, rect, i)
            //画温度的点
            onDrawTemp(canvas, i)
            //画表示天气图片
            if (listItems!![i].res != -1 && i != currentItemIndex) {
                val drawable: Drawable? =
                    ContextCompat.getDrawable(context, listItems!![i].res)
                drawable?.setBounds(
                    (point!!.x - DisplayUtil.dip2px(context, 10)).toInt(),
                    point!!.y - DisplayUtil.dip2px(context, 25).toInt(),
                    point!!.x + DisplayUtil.dip2px(context, 10).toInt(),
                    point!!.y - DisplayUtil.dip2px(context, 5).toInt()
                )
                drawable?.draw(canvas)
            }
            onDrawLine(canvas, i)
            onDrawText(canvas, i)
        }
        //底部水平的白线
        linePaint!!.color = Color.WHITE
        canvas.drawLine(
            0f,
            mHeight - bottomTextHeight.toFloat(),
            mWidth.toFloat(),
            mHeight - bottomTextHeight.toFloat(),
            linePaint!!
        )
        //中间温度的虚线
//        Path path1 = new Path();
//        path1.moveTo(MARGIN_LEFT_ITEM, tempBaseTop);
//        path1.quadTo(mWidth - MARGIN_RIGHT_ITEM, tempBaseTop, mWidth - MARGIN_RIGHT_ITEM, tempBaseTop);
//        canvas.drawPath(path1, dashLinePaint);
//        Path path2 = new Path();
//        path2.moveTo(MARGIN_LEFT_ITEM, tempBaseBottom);
//        path2.quadTo(mWidth - MARGIN_RIGHT_ITEM, tempBaseBottom, mWidth - MARGIN_RIGHT_ITEM, tempBaseBottom);
//        canvas.drawPath(path2, dashLinePaint);
    }

    private fun onDrawTemp(canvas: Canvas, i: Int) {
        val item = listItems!![i]
        val point = item.tempPoint
        canvas.drawCircle(point!!.x.toFloat(), point.y.toFloat(), 10f, pointPaint!!)
        if (currentItemIndex == i) {
            //计算提示文字的运动轨迹
            val Y = tempBarY
            //画出背景图片
            val drawable: Drawable? = ContextCompat.getDrawable(context, R.mipmap.hour_24_float)
            drawable?.setBounds(
                scrollBarX,
                Y - DisplayUtil.dip2px(context, 24).toInt(),
                scrollBarX + ITEM_WIDTH,
                Y - DisplayUtil.dip2px(context, 4).toInt()
            )
            drawable?.draw(canvas)
            //画天气
            val res = findCurrentRes(i)
            if (res != -1) {
                val drawTemp: Drawable? = ContextCompat.getDrawable(context, res)
                drawTemp?.setBounds(
                    (scrollBarX + ITEM_WIDTH / 2 + (ITEM_WIDTH / 2 - DisplayUtil.dip2px(
                        context,
                        18
                    )) / 2).toInt(),
                    (Y - DisplayUtil.dip2px(context, 23)).toInt(),
                    (scrollBarX + ITEM_WIDTH - (ITEM_WIDTH / 2 - DisplayUtil.dip2px(
                        context,
                        18
                    )) / 2).toInt(),
                    (Y - DisplayUtil.dip2px(context, 5)).toInt()
                )
                drawTemp?.draw(canvas)
            }
            //画出温度提示
            var offset = ITEM_WIDTH / 2
            if (res == -1) offset = ITEM_WIDTH
            val targetRect = Rect(
                scrollBarX, (Y - DisplayUtil.dip2px(context, 24)).toInt()
                , scrollBarX + offset, (Y - DisplayUtil.dip2px(context, 4)).toInt()
            )
            val fontMetrics = textPaint!!.fontMetricsInt
            val baseline =
                (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
            textPaint!!.textAlign = Paint.Align.CENTER
            canvas.drawText(
                item.temperature .toString() + "°",
                targetRect.centerX().toFloat(),
                baseline.toFloat(),
                textPaint!!
            )
        }
    }

    private fun findCurrentRes(i: Int): Int {
        if (listItems!![i].res != -1) return listItems!![i].res
        for (k in i downTo 0) {
            if (listItems!![k].res != -1) return listItems!![k].res
        }
        return -1
    }

    //画底部风力的BOX
    private fun onDrawBox(
        canvas: Canvas,
        rect: Rect?,
        i: Int
    ) {
        // 新建一个矩形
        val boxRect = RectF(rect)
        val item = listItems!![i]
        if (i == currentItemIndex) {
            windyBoxPaint!!.alpha = 255
            canvas.drawRoundRect(boxRect, 4f, 4f, windyBoxPaint!!)
            //画出box上面的风力提示文字
            val targetRect = Rect(
                scrollBarX,
                (rect!!.top - DisplayUtil.dip2px(context, 20)).toInt()
                ,
                scrollBarX + ITEM_WIDTH,
                (rect!!.top - DisplayUtil.dip2px(context, 0)).toInt()
            )
            val fontMetrics = textPaint!!.fontMetricsInt
            val baseline =
                (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
            textPaint!!.textAlign = Paint.Align.CENTER
            canvas.drawText(
                "风力" + item.windy + "级",
                targetRect.centerX().toFloat(),
                baseline.toFloat(),
                textPaint!!
            )
        } else {
            windyBoxPaint!!.alpha = windyBoxAlpha
            canvas.drawRoundRect(boxRect, 4f, 4f, windyBoxPaint!!)
        }
    }

    //温度的折线,为了折线比较平滑，做了贝塞尔曲线
    private fun onDrawLine(canvas: Canvas, i: Int) {
        linePaint!!.color = Color.YELLOW
        linePaint!!.strokeWidth = 3f
        val point = listItems!![i].tempPoint
        if (i != 0) {
            val pointPre = listItems!![i - 1].tempPoint
            val path = Path()
            path.moveTo(pointPre!!.x.toFloat(), pointPre.y.toFloat())
            if (i % 2 == 0) path.cubicTo(
                (pointPre.x + point!!.x) / 2.toFloat(),
                (pointPre.y + point.y) / 2 - 7.toFloat(),
                (pointPre.x + point.x) / 2.toFloat(),
                (pointPre.y + point.y) / 2 + 7.toFloat(),
                point.x.toFloat(),
                point.y.toFloat()
            ) else path.cubicTo(
                (pointPre.x + point!!.x) / 2.toFloat(),
                (pointPre.y + point.y) / 2 + 7.toFloat(),
                (pointPre.x + point.x) / 2.toFloat(),
                (pointPre.y + point.y) / 2 - 7.toFloat(),
                point.x.toFloat(),
                point.y.toFloat()
            )
            canvas.drawPath(path, linePaint!!)
        }
    }

    //绘制底部时间
    private fun onDrawText(canvas: Canvas, i: Int) {
        //此处的计算是为了文字能够居中
        val rect = listItems!![i].windyBoxRect
        val targetRect = Rect(
            rect!!.left,
            rect.bottom,
            rect.right,
            rect.bottom + bottomTextHeight
        )
        val fontMetrics = textPaint!!.fontMetricsInt
        val baseline =
            (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        textPaint!!.textAlign = Paint.Align.CENTER
        val text = listItems!![i].time
        canvas.drawText(text!!, targetRect.centerX().toFloat(), baseline.toFloat(), textPaint!!)
    }

    fun drawLeftTempText(canvas: Canvas, offset: Int) {
        //画最左侧的文字
        textPaint!!.textAlign = Paint.Align.LEFT
        canvas.drawText(
            "$maxTemp°",
            DisplayUtil.dip2px(context, 6) + offset,
            tempBaseTop.toFloat(),
            textPaint!!
        )
        canvas.drawText(
            "$minTemp°",
            DisplayUtil.dip2px(context, 6) + offset,
            tempBaseBottom.toFloat(),
            textPaint!!
        )
    }

    //设置scrollerView的滚动条的位置，通过位置计算当前的时段
    fun setScrollOffset(offset: Int, maxScrollOffset: Int) {
        this.maxScrollOffset = maxScrollOffset
        scrollOffset = offset
        val index = calculateItemIndex(offset)
        currentItemIndex = index
        invalidate()
    }

    //通过滚动条偏移量计算当前选择的时刻
    private fun calculateItemIndex(offset: Int): Int {
//        Log.d(TAG, "maxScrollOffset = " + maxScrollOffset + "  scrollOffset = " + scrollOffset);
        val x = scrollBarX
        var sum =
            MARGIN_LEFT_ITEM - ITEM_WIDTH / 2
        for (i in 0 until ITEM_SIZE) {
            sum += ITEM_WIDTH
            if (x < sum) return i
        }
        return ITEM_SIZE - 1
    }

    private val scrollBarX: Int
        private get() {
            var x =
                (ITEM_SIZE - 1) * ITEM_WIDTH * scrollOffset / maxScrollOffset
            x = x + MARGIN_LEFT_ITEM
            return x
        }

    //计算温度提示文字的运动轨迹
    private val tempBarY: Int
        private get() {
            val x = scrollBarX
            var sum = MARGIN_LEFT_ITEM
            var startPoint: Point? = null
            val endPoint: Point?
            var i: Int
            i = 0
            while (i < ITEM_SIZE) {
                sum += ITEM_WIDTH
                if (x < sum) {
                    startPoint = listItems!![i].tempPoint
                    break
                }
                i++
            }
            if (i + 1 >= ITEM_SIZE || startPoint == null) return listItems!![ITEM_SIZE - 1].tempPoint!!.y
            endPoint = listItems!![i + 1].tempPoint
            val rect = listItems!![i].windyBoxRect
            return (startPoint.y + (x - rect!!.left) * 1.0 / ITEM_WIDTH * (endPoint!!.y - startPoint.y)).toInt()
        }

    companion object {
        private const val ITEM_SIZE = 24 //24小时
        private const val ITEM_WIDTH = 140 //每个Item的宽度
        private const val MARGIN_LEFT_ITEM = 100 //左边预留宽度
        private const val MARGIN_RIGHT_ITEM = 100 //右边预留宽度
        private const val windyBoxAlpha = 80
        private const val windyBoxMaxHeight = 80
        private const val windyBoxMinHeight = 20
        private const val windyBoxSubHight =
            windyBoxMaxHeight - windyBoxMinHeight
        private const val bottomTextHeight = 60
        private val TEMP = intArrayOf(
            22, 23, 23, 23, 23,
            22, 23, 23, 23, 22,
            21, 21, 22, 22, 23,
            23, 24, 24, 25, 25,
            25, 26, 25, 24
        )
        private val WINDY = intArrayOf(
            2, 2, 3, 3, 3,
            4, 4, 4, 3, 3,
            3, 4, 4, 4, 4,
            2, 2, 2, 3, 3,
            3, 5, 5, 5
        )
        private val WEATHER_RES = intArrayOf(
            R.mipmap.w0, R.mipmap.w1, R.mipmap.w3, -1, -1
            , R.mipmap.w5, R.mipmap.w7, R.mipmap.w9, -1, -1
            , -1, R.mipmap.w10, R.mipmap.w15, -1, -1
            , -1, -1, -1, -1, -1
            , R.mipmap.w18, -1, -1, R.mipmap.w19
        )
    }

    init {
        init()
    }
}