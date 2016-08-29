package com.feximin.box.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by Neo on 16/3/17.
 */
public class CropCircleStrokeTransformation implements Transformation<Bitmap> {

    private int mStrokeColor;
    private int mStrokeWidth;
    private BitmapPool mBitmapPool;

    public CropCircleStrokeTransformation(Context context, int strokeWidth, int strokeColor){
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.mStrokeWidth = strokeWidth;
        this.mStrokeColor = strokeColor;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int size = Math.min(source.getWidth(), source.getHeight());

        int width = (source.getWidth() - size) / 2;
        int height = (source.getHeight() - size) / 2;

        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float r = size / 2f;

        if (mStrokeWidth > 0 && mStrokeWidth < r){     //透明也是可以的
            paint.setColor(mStrokeColor);
            canvas.drawCircle(r, r, r, paint);
        }
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            // source isn't square, move viewport to center
            Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);

        canvas.drawCircle(r, r, r-mStrokeWidth, paint);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override public String getId() {
        return "CropCircleStrokeTransformation(strokeColor=" + mStrokeColor + ", strokeWidth=" + mStrokeWidth  + ")";
    }
}
