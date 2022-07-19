package com.fencer.lock.format;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;

public class RiverNameFormat <T> extends TextDrawFormat<T> {

    private int width;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 多行文字格式化构造方法
     *
     * @param width 指定宽度 px
     */
    public  RiverNameFormat(int width) {
        this.width = width;
    }

    /**
     * 多行文字格式化构造方法
     *
     * @param dpWidth 指定宽度 dp
     */
    public  RiverNameFormat(Context context, int dpWidth) {
        this.width = DensityUtils.dp2px(context,dpWidth);
    }

    @Override
    public int measureWidth(Column<T> column, int position, TableConfig config) {

        return width;
    }

    @Override
    public int measureHeight(Column<T> column, int position, TableConfig config) {
        config.getContentStyle().fillPaint(textPaint);
        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        return DrawUtils.getTextHeight(paint)+4;
    }

    @Override
    public void draw(Canvas c, Rect rect, CellInfo<T> cellInfo , TableConfig config) {
        setTextPaint(config, cellInfo, textPaint);
        textPaint.setColor(Color.parseColor("#3976dc"));
        if(cellInfo.column.getTextAlign() !=null) {
            textPaint.setTextAlign(cellInfo.column.getTextAlign());
        }
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int hPadding = (int) (config.getHorizontalPadding()*config.getZoom());
        int realWidth =rect.width() - 2*hPadding;
        StaticLayout staticLayout = new StaticLayout(cellInfo.column.format(cellInfo.row), textPaint, realWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        c.save();
        c.translate(DrawUtils.getTextCenterX(rect.left+hPadding,rect.right-hPadding,textPaint), rect.top+(rect.height()-staticLayout.getHeight())/2);
        staticLayout.draw(c);
        c.restore();
    }
}
