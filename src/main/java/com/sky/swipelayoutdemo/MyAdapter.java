package com.sky.swipelayoutdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sky.swipelayoutdemo.R.id.putTop;
import static com.sky.swipelayoutdemo.R.id.tv_delete;


/**
 * 作者：SKY
 * 创建时间：2016-9-12 8:54
 * 描述：lv 的适配器
 */

public class MyAdapter extends BaseAdapter {

    private List<Msg> messages = new ArrayList<>();

    private Context context;

    public MyAdapter (Context context, List<Msg> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount () {
        return messages.size();
    }

    @Override
    public Object getItem (int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lv_swipelayout_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initializeViews(position, holder);
        return convertView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            switch (v.getId()) {
                case tv_delete:
                    int position = (int) v.getTag(R.id.delete);
                    messages.remove(position);
                    notifyDataSetChanged();
                    SwipeLayoutManager.getInstance().getSwipeLayout().close(false);
                    break;
                case putTop:
                    position = (int) v.getTag(R.id.setTop);
                    messages.add(0, messages.get(position));
                    messages.remove(messages.get(position + 1));
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    private void initializeViews (int position, ViewHolder holder) {

        Msg msg = messages.get(position);

        Picasso.with(context)
                .load(Cheeses.ICONS[new Random().nextInt(Cheeses.ICONS.length)])
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .resize(120,120)
                .error(R.mipmap.ic_launcher)
                .into(holder.iv_icon);

        holder.tvName.setText(msg.getName());
        holder.tvContent.setText(msg.getContent());
        if (position == 0) {
            holder.tvUnReadMsgCount.setVisibility(View.INVISIBLE);
        } else {
            holder.tvUnReadMsgCount.setVisibility(View.VISIBLE);
            holder.tvUnReadMsgCount.setText(position + "");
        }

        holder.tvDelete.setTag(R.id.delete, position);
        holder.tvDelete.setOnClickListener(listener);
        holder.tvSetTop.setTag(R.id.setTop, position);
        holder.tvSetTop.setOnClickListener(listener);
    }

    protected class ViewHolder {

        private ImageView iv_icon;
        private TextView tvName;
        private TextView tvContent;
        private TextView tvUnReadMsgCount;
        private TextView tvDelete;
        private TextView tvSetTop;

        public ViewHolder (View view) {
            iv_icon = (ImageView) view.findViewById(R.id.iv_swipe_icon);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvUnReadMsgCount = (TextView) view.findViewById(R.id.tv_unread_msg_count);
            tvDelete = (TextView) view.findViewById(tv_delete);
            tvSetTop = (TextView) view.findViewById(R.id.putTop);
        }
    }
}

