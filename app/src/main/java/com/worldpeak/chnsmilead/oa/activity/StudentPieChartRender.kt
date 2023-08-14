package com.worldpeak.chnsmilead.oa.activity

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet.ValuePosition
import com.github.mikephil.charting.renderer.PieChartRenderer
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

class StudentPieChartRender(
    private val uiParams: StudentChartUiParams,
    view: PieChart,
    anim: ChartAnimator,
    handler: ViewPortHandler
) :
    PieChartRenderer(view, anim, handler) {
    private val valueBackgroundPaint = Paint()

    init {
        valueBackgroundPaint.color = uiParams.valueBackgroundColor
        valueBackgroundPaint.setShadowLayer(8F, 0F, 3F, uiParams.valueBackgroundShadowColor)
    }

    override fun drawValues(c: Canvas) {
        val center = mChart.centerCircleBox

        // get whole the radius
        val radius = mChart.radius
        var rotationAngle = mChart.rotationAngle
        val drawAngles = mChart.drawAngles
        val absoluteAngles = mChart.absoluteAngles
        val phaseX = mAnimator.phaseX
        val phaseY = mAnimator.phaseY
        val roundedRadius = (radius - radius * mChart.holeRadius / 100f) / 2f
        val holeRadiusPercent = mChart.holeRadius / 100f
        var labelRadiusOffset = radius / 10f * 3.6f
        if (mChart.isDrawHoleEnabled) {
            labelRadiusOffset = (radius - radius * holeRadiusPercent) / 2f
            if (!mChart.isDrawSlicesUnderHoleEnabled && mChart.isDrawRoundedSlicesEnabled) {
                // Add curved circle slice and spacing to rotation angle, so that it sits nicely inside
                rotationAngle += (roundedRadius * 360 / (Math.PI * 2 * radius)).toFloat()
            }
        }
        val labelRadius = radius
        val data = mChart.data
        val dataSets = data.dataSets
        val yValueSum = data.yValueSum
        val drawEntryLabels = mChart.isDrawEntryLabelsEnabled
        var angle: Float
        var xIndex = 0
        c.save()
        val offset = Utils.convertDpToPixel(5f)
        for (i in dataSets.indices) {
            val dataSet = dataSets[i]
            val drawValues = dataSet.isDrawValuesEnabled
            if (!drawValues && !drawEntryLabels) continue
            val xValuePosition = dataSet.xValuePosition
            val yValuePosition = dataSet.yValuePosition

            // apply the text-styling defined by the DataSet
            applyValueTextStyle(dataSet)
            val lineHeight = (Utils.calcTextHeight(mValuePaint, "Q")
                    + Utils.convertDpToPixel(4f))
            val formatter = dataSet.valueFormatter
            val entryCount = dataSet.entryCount
            mValueLinePaint.color = dataSet.valueLineColor
            mValueLinePaint.strokeWidth = Utils.convertDpToPixel(dataSet.valueLineWidth)
            val sliceSpace = getSliceSpace(dataSet)
            val iconsOffset = MPPointF.getInstance(dataSet.iconsOffset)
            iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x)
            iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y)
            for (j in 0 until entryCount) {
                val entry = dataSet.getEntryForIndex(j)
                angle = if (xIndex == 0) 0f else absoluteAngles[xIndex - 1] * phaseX
                val sliceAngle = drawAngles[xIndex]
                val sliceSpaceMiddleAngle = sliceSpace / (Utils.FDEG2RAD * labelRadius)

                // offset needed to center the drawn text in the slice
                val angleOffset = (sliceAngle - sliceSpaceMiddleAngle / 2f) / 2f
                angle += angleOffset
                val transformedAngle = rotationAngle + angle * phaseY
                val value: Float =
                    if (mChart.isUsePercentValuesEnabled) entry.y / yValueSum * 100f else entry.y
                val formattedValue = formatter.getPieLabel(value, entry)
                val entryLabel = entry.label
                val sliceXBase = Math.cos((transformedAngle * Utils.FDEG2RAD).toDouble()).toFloat()
                val sliceYBase = Math.sin((transformedAngle * Utils.FDEG2RAD).toDouble()).toFloat()
                val drawXOutside = drawEntryLabels &&
                        xValuePosition == ValuePosition.OUTSIDE_SLICE
                val drawYOutside = drawValues &&
                        yValuePosition == ValuePosition.OUTSIDE_SLICE
                val drawXInside = drawEntryLabels &&
                        xValuePosition == ValuePosition.INSIDE_SLICE
                val drawYInside = drawValues &&
                        yValuePosition == ValuePosition.INSIDE_SLICE
                if (drawXOutside || drawYOutside) {
                    val valueLineLength1 = dataSet.valueLinePart1Length
                    val valueLineLength2 = dataSet.valueLinePart2Length
                    val valueLinePart1OffsetPercentage =
                        dataSet.valueLinePart1OffsetPercentage / 100f
                    var pt2x: Float
                    var pt2y: Float
                    var labelPtx: Float
                    var labelPty: Float
                    var line1Radius: Float
                    line1Radius =
                        if (mChart.isDrawHoleEnabled) ((radius - radius * holeRadiusPercent)
                                * valueLinePart1OffsetPercentage
                                + radius * holeRadiusPercent) else radius * valueLinePart1OffsetPercentage
                    val polyline2Width =
                        if (dataSet.isValueLineVariableLength) labelRadius * valueLineLength2 * Math.abs(
                            Math.sin(
                                (
                                        transformedAngle * Utils.FDEG2RAD).toDouble()
                            )
                        ).toFloat() else labelRadius * valueLineLength2
                    val pt0x = line1Radius * sliceXBase + center.x
                    val pt0y = line1Radius * sliceYBase + center.y
                    val pt1x = labelRadius * (1 + valueLineLength1) * sliceXBase + center.x
                    val pt1y = labelRadius * (1 + valueLineLength1) * sliceYBase + center.y
                    if (transformedAngle % 360.0 >= 90.0 && transformedAngle % 360.0 <= 270.0) {
                        pt2x = pt1x - polyline2Width
                        pt2y = pt1y
                        mValuePaint.textAlign = Paint.Align.RIGHT
                        if (drawXOutside) paintEntryLabels.textAlign = Paint.Align.RIGHT
                        labelPtx = pt2x - offset
                        labelPty = pt2y
                    } else {
                        pt2x = pt1x + polyline2Width
                        pt2y = pt1y
                        mValuePaint.textAlign = Paint.Align.LEFT
                        if (drawXOutside) paintEntryLabels.textAlign = Paint.Align.LEFT
                        labelPtx = pt2x + offset
                        labelPty = pt2y
                    }
                    if (dataSet.valueLineColor != ColorTemplate.COLOR_NONE) {
                        if (dataSet.isUsingSliceColorAsValueLineColor) {
                            mValueLinePaint.color = dataSet.getColor(j)
                        }
                        c.drawLine(pt0x, pt0y, pt1x, pt1y, mValueLinePaint)
                        c.drawLine(pt1x, pt1y, pt2x, pt2y, mValueLinePaint)
                    }

                    // draw everything, depending on settings
                    if (drawXOutside && drawYOutside) {
                        drawValue(
                            c,
                            formattedValue,
                            labelPtx,
                            labelPty,
                            dataSet.getValueTextColor(j)
                        )
                        if (j < data.entryCount && entryLabel != null) {
                            drawEntryLabel(c, entryLabel, labelPtx, labelPty + lineHeight)
                        }
                    } else if (drawXOutside) {
                        if (j < data.entryCount && entryLabel != null) {
                            drawEntryLabel(c, entryLabel, labelPtx, labelPty + lineHeight / 2f)
                        }
                    } else if (drawYOutside) {
                        drawValue(
                            c,
                            formattedValue,
                            labelPtx,
                            labelPty + lineHeight / 2f,
                            dataSet.getValueTextColor(j)
                        )
                    }
                }
                if (drawXInside || drawYInside) {
                    // calculate the text position
                    val x = labelRadius * sliceXBase + center.x
                    val y = labelRadius * sliceYBase + center.y
                    mValuePaint.textAlign = Paint.Align.CENTER

                    // draw everything, depending on settings
                    if (drawXInside && drawYInside) {
                        drawValue(c, formattedValue, x, y, dataSet.getValueTextColor(j))
                        if (j < data.entryCount && entryLabel != null) {
                            drawEntryLabel(c, entryLabel, x, y + lineHeight)
                        }
                    } else if (drawXInside) {
                        if (j < data.entryCount && entryLabel != null) {
                            drawEntryLabel(c, entryLabel, x, y + lineHeight / 2f)
                        }
                    } else if (drawYInside) {
                        drawValue(
                            c,
                            formattedValue,
                            x,
                            y + lineHeight / 2f,
                            dataSet.getValueTextColor(j)
                        )
                    }
                }
                if (entry.icon != null && dataSet.isDrawIconsEnabled) {
                    val icon = entry.icon
                    val x = (labelRadius + iconsOffset.y) * sliceXBase + center.x
                    var y = (labelRadius + iconsOffset.y) * sliceYBase + center.y
                    y += iconsOffset.x
                    Utils.drawImage(
                        c,
                        icon, x.toInt(), y.toInt(),
                        icon.intrinsicWidth,
                        icon.intrinsicHeight
                    )
                }
                xIndex++
            }
            MPPointF.recycleInstance(iconsOffset)
        }
        MPPointF.recycleInstance(center)
        c.restore()
    }

    override fun drawValue(c: Canvas, valueText: String, x: Float, y: Float, color: Int) {
        c.drawCircle(x, y, uiParams.valueBackgroundRadius, valueBackgroundPaint)
        mValuePaint.color = color
        mValuePaint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        drawTextWithCenterPoint(c, x + 5, y, valueText, mValuePaint)
    }

    private fun drawTextWithCenterPoint(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        text: String,
        paint: Paint
    ) {
        //获取文本的宽度，但是是一个比较粗略的结果
        val textWidth = paint.measureText(text)
        //文字度量
        val fontMetrics = paint.fontMetrics
        //得到基线的位置
        val baselineY = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        //绘制
        canvas.drawText(text, centerX, baselineY, paint)
    }
}