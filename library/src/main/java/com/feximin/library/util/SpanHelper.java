package com.feximin.library.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.SparseArray;

import com.mianmian.guild.App;
import com.mianmian.guild.view.CenterVerticalImageSpan;

/**
 * Created by Neo on 16/4/21.
 */
public class SpanHelper {


    private static final SparseArray<ForegroundColorSpan> sForegroundColorSpanCache = new SparseArray<>(2);
    private static final SparseArray<ImageSpan> sImgSpanCache = new SparseArray<>(2);

    private SpannableStringBuilder ssb;

    private SpanHelper(){
        ssb = new SpannableStringBuilder();
    }

    public static SpanHelper obtain(){
        return new SpanHelper();
    }
    public SpannableStringBuilder build(){
        return ssb;
    }

    public SpanHelper append(int textResId, int color){
        return append(Tool.getString(textResId), color);
    }

    public SpanHelper append(String text, int color){
        ForegroundColorSpan span = sForegroundColorSpanCache.get(color);
        if (span == null) {
            synchronized (sForegroundColorSpanCache) {
                span = sForegroundColorSpanCache.get(color);
                if (span == null) {
                    span = new ForegroundColorSpan(color);
                    sForegroundColorSpanCache.put(color, span);
                }
            }
        }
        int start = ssb.length();
        ssb.append(text);
        ssb.setSpan(span,start, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpanHelper append(String text, int[] pos, int[] color){
        int length = pos.length;
        if (length != color.length){
            throw new IllegalArgumentException("length should be equal !!");
        }
        if (length == 0){
            append(text);
        }else{
            int last = 0;
            for (int i = 0; i<length; i++){
                append(text.substring(last, pos[i]), color[i]);
                last = pos[i];
            }
        }
        return this;
    }
    public SpanHelper append(int imgResId, int mL, int mR){
        ImageSpan span = sImgSpanCache.get(imgResId);
        if(span == null){
            synchronized (sImgSpanCache) {
                span = sImgSpanCache.get(imgResId);
                if (span == null) {
                    span = new CenterVerticalImageSpan(App.getApp(), imgResId, mL, mR);
                    sImgSpanCache.put(imgResId, span);
                }
            }
        }
        ssb.append("s");
        int length = ssb.length();
        ssb.setSpan(span, length -1, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }


    public SpanHelper append(String text){
        ssb.append(text);
        return this;
    }

    public SpanHelper append(int imgResId){
        int dp5 = ScreenUtil.dpToPx(5);
        return this.append(imgResId, dp5, dp5);
    }
}
