package com.feximin.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.feximin.box.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {

	public TagLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private MarginLayoutParams mDefaultChildLp;
	{
		mDefaultChildLp = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
		int padding = ScreenUtil.dpToPx(5);
		mDefaultChildLp.leftMargin = padding;
		mDefaultChildLp.rightMargin = padding;
		mDefaultChildLp.topMargin = padding;
		mDefaultChildLp.bottomMargin = padding;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		height = Math.max(height, getSuggestedMinimumHeight());
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
			
		int maxWidth = getPaddingLeft() + getPaddingRight();
		int maxHeight = getPaddingTop() + getPaddingBottom();
		
		int lineWidth = getPaddingLeft() + getPaddingRight();
		int lineHeight = 0;
		

		int childCount = getChildCount();
		for(int i = 0; i<childCount; i++){
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			int childWidth = child.getMeasuredWidth(); 
			int childHeight = child.getMeasuredHeight();
			LayoutParams lp = child.getLayoutParams();
			if(lp != null && lp instanceof MarginLayoutParams){
				MarginLayoutParams marginLp = (MarginLayoutParams) lp;
				childWidth += marginLp.leftMargin;
				childWidth += marginLp.rightMargin;
				
				childHeight += marginLp.topMargin;
				childHeight += marginLp.bottomMargin;
			}
			lineWidth += childWidth;
			if(lineWidth > width){
				i--;
				lineWidth -= childWidth;
				maxHeight += lineHeight;
				maxWidth = Math.max(lineWidth, maxWidth);
				lineWidth = getPaddingLeft() + getPaddingRight();
				lineHeight = 0;
			}else{
				lineHeight = Math.max(lineHeight, childHeight);
				if(i == childCount - 1){
					maxHeight += lineHeight;
				}
				maxWidth = Math.max(lineWidth, maxWidth);
			}
			
		}
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY?width:maxWidth,  heightMode == MeasureSpec.EXACTLY?height:maxHeight);
	}

	@Override
	public void addView(View child) {
		if(child.getLayoutParams() == null) child.setLayoutParams(mDefaultChildLp);
		super.addView(child);
	}

	private List<View> mLineViews = new ArrayList<View>();
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mLineViews.clear();
		int pL = getPaddingLeft();
		int pT = getPaddingTop();
		int pR = getPaddingRight();
		int totalHeight = pT;
		int totalWidth = pL;
		int width = getMeasuredWidth();
		
		int lineWidth = pL + pR;
		int lineHeight = 0;
		int childCount = getChildCount();
		boolean needLayout = false;
		for(int i = 0; i<childCount; i++){
			

			View child = getChildAt(i);
			int childWidth = child.getMeasuredWidth(); 
			int childHeight = child.getMeasuredHeight();
			LayoutParams lp = child.getLayoutParams();
			if(lp != null && lp instanceof MarginLayoutParams){
				MarginLayoutParams marginLp = (MarginLayoutParams) lp;
				childWidth += marginLp.leftMargin;
				childWidth += marginLp.rightMargin;
				
				childHeight += marginLp.topMargin;
				childHeight += marginLp.bottomMargin;
			}
			lineWidth += childWidth;
			
			if(lineWidth > width){
				i--;
				lineWidth -= childWidth;
				needLayout = true;
			}else{
				mLineViews.add(child);
				lineHeight = Math.max(lineHeight, childHeight);
				if(i == childCount - 1){
					needLayout = true;
				}
			}
			if(needLayout){
				int lineCount = mLineViews.size();
				for(int j = 0; j<lineCount; j++){
					View lineChild = mLineViews.get(j);
					int left = totalWidth + (width - lineWidth)/2;
					int top = totalHeight + (lineHeight - lineChild.getMeasuredHeight())/2;
					
					LayoutParams lpp = child.getLayoutParams();
					if(lpp != null && lpp instanceof MarginLayoutParams){
						MarginLayoutParams marginLp = (MarginLayoutParams) lpp;
						left += marginLp.leftMargin;
						totalWidth += marginLp.leftMargin;
						totalWidth += marginLp.rightMargin;
					}
					int right = left + lineChild.getMeasuredWidth();
					int bottom = top + lineChild.getMeasuredHeight();
					lineChild.layout(left, top, right, bottom);
//					L.e(String.format("i-->%s, left-->%s, top-->%s, right-->%s, bottom-->%s ",  i, left, top, right, bottom));
					totalWidth += lineChild.getMeasuredWidth();
				}
				totalHeight += lineHeight;
				lineWidth = pL + pR;
				totalWidth = pL;
				lineHeight = 0;
				mLineViews.clear();
				needLayout = false;
			}
			
		}
	}
}
