package com.feximin.box.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by Neo on 16/3/17.
 */
public class RoundedCornersStrokeTransformation_bak implements Transformation<Bitmap> {

    private int mRadius;
    private int mStrokeColor;
    private int mStrokeWidth;
    private BitmapPool mBitmapPool;

    public RoundedCornersStrokeTransformation_bak(Context context, int radius, int strokeWidth, int strokeColor){
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.mRadius = radius;
        this.mStrokeColor = strokeColor;
        this.mStrokeWidth = strokeWidth;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int radius = mRadius;
        int start = 0;
        if (mStrokeWidth > 0 && mStrokeWidth < Math.min(width, height) / 2f && mRadius > mStrokeWidth){
            paint.setColor(mStrokeColor);
            canvas.drawRoundRect(new RectF(0, 0, width, height), mRadius, mRadius, paint);
            start = mStrokeWidth;
            radius = mRadius - mStrokeWidth;
            width = width - mStrokeWidth;
            height = height - mStrokeWidth;
        }
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        canvas.drawRoundRect(new RectF(start, start, width, height), radius, radius, paint);

//        paint.setXfermode(null);
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rectF, paint);


        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override public String getId() {
        return "RoundedCornersStrokeTransformation(radius=" + mRadius + ", strokeColor="
                + mStrokeColor + ", strokeWidth=" + mStrokeWidth + ")";
    }
}
