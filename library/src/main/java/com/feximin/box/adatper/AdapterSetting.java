package com.feximin.box.adatper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.entity.SettingBean;
import com.feximin.box.util.ViewHolder;
import com.feximin.box.util.drawable.DrawableHelper;
import com.feximin.box.view.Dot;
import com.feximin.library.R;

/**
 * Created by Neo on 15/11/30.
 */
public class AdapterSetting extends BaseListAdapter<SettingBean> {

    public AdapterSetting(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void fillUp(View convertView, int position) {
        SettingBean bean = getItem(position);
        Dot dot = ViewHolder.getViewById(convertView, R.id.dot);
        ImageView imgToggle = ViewHolder.getViewById(convertView, R.id.img_toggle);
        TextView txtRight = ViewHolder.getViewById(convertView, R.id.txt_right);
        TextView txtTitle = ViewHolder.getTextViewById(convertView, R.id.txt_title, bean.title);
        if (bean.imgLeftResId > 0) {
            DrawableHelper.obtain().setDrawableLeft(txtTitle, bean.imgLeftResId);
        }else{
            txtTitle.setCompoundDrawables(null, null, null, null);
        }
        dot.setVisibility(View.GONE);
        imgToggle.setVisibility(View.GONE);
        txtRight.setVisibility(View.GONE);
        @SettingBean.Type
        int type = bean.type;
        if(type == SettingBean.TEXT){
            txtRight.setVisibility(View.VISIBLE);
            txtRight.setText(bean.desc);
        }else if(type == SettingBean.DOT){
            dot.setVisibility(bean.selected?View.VISIBLE:View.GONE);
        }else{
            imgToggle.setVisibility(View.VISIBLE);
            imgToggle.setSelected(bean.selected);
        }
    }

    @Override
    protected int getItemLayoutId(int position) {
        return R.layout.item_setting_1;
    }

}
