package com.feximin.library.adatper;

import android.view.View;
import android.widget.TextView;

import com.feximin.library.activity.BaseActivity;
import com.feximin.library.adatper.BaseListAdapter;
import com.mianmian.guild.R;
import com.mianmian.guild.entity.AlphaIndexAble;

/**
 * Created by Neo on 16/2/18.
 */
public abstract class BaseAlphaIndexAdapter<T extends AlphaIndexAble> extends BaseListAdapter<T> {

    private TextView mTopAlphaTextView;
    public BaseAlphaIndexAdapter(BaseActivity activity, TextView txt) {
        super(activity);
        this.mTopAlphaTextView = txt;
    }

    @Override
    protected void inflateView(View convertView, int position) {
        TextView txtIndex = (TextView) convertView.findViewById(R.id.txt_alpha);
        boolean showIndex;
        String alpha = getItem(position).getAlpha();

        if(position == 0){
            showIndex = true;
            mTopAlphaTextView.setText(alpha);
        }else{
            String last = getItem(position - 1).getAlpha();
            showIndex = !alpha.equals(last);
        }
        txtIndex.setText(alpha);
        txtIndex.setVisibility(showIndex?View.VISIBLE:View.GONE);
        fillUpContent(convertView, position);
    }


    public TextView getTopAlphaTextView(){
        return this.mTopAlphaTextView;
    }

    protected abstract void fillUpContent(View convertView, int position);

}
