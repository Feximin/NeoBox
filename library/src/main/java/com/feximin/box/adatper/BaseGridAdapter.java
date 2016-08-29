package com.feximin.box.adatper;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.feximin.box.activity.BaseActivity;

/**
 * Created by Neo on 16/1/26.
 * GridView也使用ListView的形式
 * 这种方式不能使用getItemViewType这样的
 */
public abstract class BaseGridAdapter<T> extends BaseListAdapter<T> {
    private int mColumn;
    protected int mItemWidth;

    public BaseGridAdapter(BaseActivity activity, int column){
        super(activity);
        this.mColumn = column;
    }

    public int getColumn(){
        return mColumn;
    }

    @Override
    public int getCount() {
        int size = mData.size();
        int count = size / mColumn;
        int mod = size % mColumn;
        if(mod > 0) count ++;
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int row = position / mColumn;
        if(convertView == null) convertView = getRowView(row);
        LinearLayout ll = (LinearLayout) convertView;
        for(int i = 0; i<mColumn; i++){
            View item = ll.getChildAt(i);
            if (refreshLayoutParams((LayoutParams) item.getLayoutParams())) item.requestLayout();
            int index = mColumn * position + i;
            if(index < mData.size()){
                item.setVisibility(View.VISIBLE);
                fillUp(item, index);
            }else{
                item.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    protected boolean refreshLayoutParams(LayoutParams lp){
        return false;
    }

    private View getRowView(int row){
        LinearLayout ll = new LinearLayout(mActivity);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER_VERTICAL);
        //不需要setPadding，这里只负责等分，padding事宜由具体的item去负责
//        ll.setPadding(halfPadding, halfPadding, halfPadding, halfPadding);             //我了个擦，为什么把setPadding放到setGravity之前就无效， http://blog.csdn.net/sam_zhang1984/article/details/8442342
        for(int i = 0; i<mColumn; i++){
            View item = getItemView(row, i);
            item.setVisibility(View.INVISIBLE);
            LayoutParams lp = new LayoutParams(0, -2, 1);
            lp.gravity = Gravity.CENTER;
            ll.addView(item, lp);
        }
        return ll;
    }

    protected View getItemView(int rowIndex, int columnIndex){
        return mInflater.inflate(getItemLayoutId(rowIndex, columnIndex), null);
    }

    public int getPosition(int rowIndex, int columnIndex){
        return rowIndex * mColumn + columnIndex;
    }

    @Override
    protected final int getItemLayoutId(int position) {
        return 0;
    }

    protected int getItemLayoutId(int rowIndex, int columnIndex){
        return 0;
    }
}
