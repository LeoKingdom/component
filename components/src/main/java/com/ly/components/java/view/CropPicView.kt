package com.ly.components.java.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager


class CropPicView : View {
    private val paint = Paint()
    //画裁剪区域边框的画笔
    private val borderPaint = Paint()
    //裁剪框水平方向间距
    private var mHorizontalPadding: Float = 0.toFloat()
    //裁剪框边框宽度
    private var clipBorderWidth: Int = 0
    //裁剪圆框的半径
    private var clipRadiusWidth: Int = 0
    //裁剪框矩形宽度
    private var clipWidth: Int = 0
    //裁剪框类别，（圆形、矩形），默认为圆形
    private var clipType = ClipType.CIRCLE
    private var xfermode: Xfermode?=null

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle) {

        //去锯齿
        paint.isAntiAlias = true
        borderPaint.style = Paint.Style.STROKE
        borderPaint.color = Color.WHITE
        borderPaint.strokeWidth = clipBorderWidth.toFloat()
        borderPaint.isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    @SuppressLint("WrongConstant")
    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val LAYER_FLAGS = (0x01 or 0x02 or 0x04 or 0x08 or 0x10)
        //通过Xfermode的DST_OUT来产生中间的透明裁剪区域，一定要另起一个Layer（层）
        canvas.saveLayer(0f, 0f, this.getWidth().toFloat(), this.getHeight().toFloat(), null, LAYER_FLAGS)
        //设置背景
        canvas.drawColor(Color.parseColor("#a8000000"))
        paint.xfermode = xfermode
        //绘制圆形裁剪框
        if (clipType == ClipType.CIRCLE) {
            //中间的透明的圆
            canvas.drawCircle((this.getWidth() / 2).toFloat(), (this.getHeight() / 2).toFloat(), clipRadiusWidth.toFloat(), paint)
            //白色的圆边框
            canvas.drawCircle((this.getWidth() / 2).toFloat(), (this.getHeight() / 2).toFloat(), clipRadiusWidth.toFloat(), borderPaint)
        } else if (clipType == ClipType.RECTANGLE) { //绘制矩形裁剪框
            //绘制中间的矩形
            canvas.drawRect(mHorizontalPadding, (this.getHeight() / 2 - clipWidth / 2).toFloat(),
                    this.getWidth() - mHorizontalPadding, (this.getHeight() / 2 + clipWidth / 2).toFloat(), paint)
            //绘制白色的矩形边框
            canvas.drawRect(mHorizontalPadding, (this.getHeight() / 2 - clipWidth / 2).toFloat(),
                    this.getWidth() - mHorizontalPadding, (this.getHeight() / 2 + clipWidth / 2).toFloat(), borderPaint)
        }
        //出栈，恢复到之前的图层，意味着新建的图层会被删除，新建图层上的内容会被绘制到canvas (or the previous layer)
        canvas.restore()
    }

    /**
     * 获取裁剪区域的Rect
     *
     * @return
     */
    fun getClipRect(): Rect {
        val rect = Rect()
        //宽度的一半 - 圆的半径
        rect.left = this.getWidth() / 2 - clipRadiusWidth
        //宽度的一半 + 圆的半径
        rect.right = this.getWidth() / 2 + clipRadiusWidth
        //高度的一半 - 圆的半径
        rect.top = this.getHeight() / 2 - clipRadiusWidth
        //高度的一半 + 圆的半径
        rect.bottom = this.getHeight() / 2 + clipRadiusWidth
        return rect
    }

    /**
     * 设置裁剪框边框宽度
     *
     * @param clipBorderWidth
     */
    fun setClipBorderWidth(clipBorderWidth: Int) {
        this.clipBorderWidth = clipBorderWidth
        borderPaint.strokeWidth = clipBorderWidth.toFloat()
        invalidate()
    }

    /**
     * 设置裁剪框水平间距
     *
     * @param mHorizontalPadding
     */
    fun setmHorizontalPadding(mHorizontalPadding: Float) {
        this.mHorizontalPadding = mHorizontalPadding
        this.clipRadiusWidth = (getScreenWidth(getContext()) - 2 * mHorizontalPadding).toInt() / 2
        this.clipWidth = clipRadiusWidth * 2
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }


    /**
     * 设置裁剪框类别
     *
     * @param clipType
     */
    fun setClipType(clipType: ClipType) {
        this.clipType = clipType
    }

    /**
     * 裁剪框类别，圆形、矩形
     */
    enum class ClipType {
        CIRCLE, RECTANGLE
    }
}
