package com.lucky.cat.helloworld;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class CircleFramedDrawable extends Drawable {

    private final Bitmap mBitmap;
    private final int mSize;
    private final Paint mPaint;

    private float mScale;
    private Rect mSrcRect;
    private RectF mDstRect;

    public static CircleFramedDrawable getInstance(Context context, Bitmap icon,float size) {
        Resources res = context.getResources();
        float iconSize = px2dip(context,size);
        CircleFramedDrawable instance = new CircleFramedDrawable(icon, (int) iconSize);
        return instance;
    }

    public CircleFramedDrawable(Bitmap icon, int size) {
        super();
        mSize = size;

        mBitmap = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mBitmap);

        final int width = icon.getWidth();
        final int height = icon.getHeight();
        final int square = Math.min(width, height);

        final Rect cropRect = new Rect((width - square) / 2, (height - square) / 2, square, square);
        final RectF circleRect = new RectF(0f, 0f, mSize, mSize);

        final Path fillPath = new Path();
        //path.addArc方法用于绘制圆弧，这个圆弧取自RectF矩形的内接椭圆上的一部分，圆弧长度由后两个角度参数决定
        fillPath.addArc(circleRect, 0f, 360f);
        //PorterDuff.Mode.CLEAR:清除画布（所绘制不会提交到画布上）
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // opaque circle matte
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(fillPath, mPaint);

        // mask in the icon where the bitmap is opaque(不透明)
        //PorterDuff.Mode.SRC_IN:两者相交的地方绘制源图
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(icon, cropRect, circleRect, mPaint);

        // prepare paint for frame drawing
        mPaint.setXfermode(null);

        mScale = 1f;

        mSrcRect = new Rect(0, 0, mSize, mSize);
        mDstRect = new RectF(0, 0, mSize, mSize);
    }

    @Override
    public void draw(Canvas canvas) {
        //缩放处理
        final float inside = mScale * mSize;
        final float pad = (mSize - inside) / 2f;
        //将矩形的坐标设置为缩放后指定的值
        mDstRect.set(pad, pad, mSize - pad, mSize - pad);
        //第一个mSrcRect代表要绘制的bitmap区域，第二个mDstRect代表的是要将bitmap绘制在屏幕的什么地方
        canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public float getScale() {
        return mScale;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getIntrinsicWidth() {
        return mSize;
    }

    @Override
    public int getIntrinsicHeight() {
        return mSize;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}