package com.fencer.lock.format;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.utils.DrawUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class FixedWidthFormat<T> implements IDrawFormat<T> {


    private  final  int width ;

    private Map<String, SoftReference<String[]>> valueMap; //避免产生大量对象
    private StaticLayout staticLayout;

    public FixedWidthFormat(int width) {
        this.width =width ;
        valueMap = new HashMap<>();



    }

    @Override
    public int measureWidth(Column<T> column, int position, TableConfig config) {
        return width;
    }


    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);

        TextPaint textPaint =new TextPaint(paint);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            staticLayout =StaticLayout.Builder.obtain(column.format(position),0,column.format(position).length(),
                    textPaint,width)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(0f, 1f) // add, multiplier
                    .setIncludePad(false)
                    .build();
        }else{
            staticLayout = new StaticLayout(
                    column.format(position),
                    textPaint,
                    width,
                    Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false
            );
        }

        return  staticLayout.getHeight();
    }
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void draw(Canvas c, Rect rect, CellInfo<T> cellInfo, TableConfig config) {
        setTextPaint(config, cellInfo, textPaint);
        textPaint.setColor(Color.parseColor("#333333"));
        if(cellInfo.column.getTextAlign() !=null) {
            textPaint.setTextAlign(cellInfo.column.getTextAlign());
        }
        int hPadding = (int) (config.getHorizontalPadding()*config.getZoom());
        int realWidth =rect.width() - 2*hPadding;
        StaticLayout staticLayout = new StaticLayout(cellInfo.column.format(cellInfo.row), textPaint, realWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        c.save();
        c.translate(DrawUtils.getTextCenterX(rect.left+hPadding,rect.right-hPadding,textPaint), rect.top+(rect.height()-staticLayout.getHeight())/2);
        staticLayout.draw(c);
        c.restore();
    }

    protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
       // paint.breakText(getSplitString(value),true,width+0.0f,)
        DrawUtils.drawMultiText(c,paint,rect,getSplitString(value));
    }



    public void setTextPaint(TableConfig config,CellInfo<T> cellInfo, Paint paint) {
        config.getContentStyle().fillPaint(paint);
        ICellBackgroundFormat<CellInfo> backgroundFormat = config.getContentCellBackgroundFormat();
        if(backgroundFormat!=null && backgroundFormat.getTextColor(cellInfo) != TableConfig.INVALID_COLOR){
            paint.setColor(backgroundFormat.getTextColor(cellInfo));
        }
        paint.setTextSize(paint.getTextSize()*config.getZoom());

    }

    protected String[] getSplitString(String val){
        String[] values = null;
        if(valueMap.get(val)!=null){
            values= valueMap.get(val).get();
        }
        if(values == null){
            values = val.split("\n");
            valueMap.put(val, new SoftReference<>(values));
        }
        return values;
    }

}
