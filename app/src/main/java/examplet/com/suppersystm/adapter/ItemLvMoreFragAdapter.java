package examplet.com.suppersystm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import examplet.com.suppersystm.R;


public class ItemLvMoreFragAdapter extends BaseAdapter {

    private String[]  mEntities;

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemLvMoreFragAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        mEntities =  context.getResources().getStringArray(R.array.more_items);
    }

    @Override
    public int getCount() {
        return  mEntities.length;
    }

    @Override
    public String getItem(int position) {
        return  mEntities[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_lv_more_frag, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String)getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(String entity, ViewHolder holder, int position) {
        holder.tvItemLvMoreFrag.setText(entity);
    }

    protected class ViewHolder {
        private TextView tvItemLvMoreFrag;

        public ViewHolder(View view) {
            tvItemLvMoreFrag = (TextView) view.findViewById(R.id.tv_item_lv_more_frag);
        }
    }
}
