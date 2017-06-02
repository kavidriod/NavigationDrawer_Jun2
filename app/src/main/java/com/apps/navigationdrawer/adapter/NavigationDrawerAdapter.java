package com.apps.navigationdrawer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.navigationdrawer.R;
import com.apps.navigationdrawer.model.NavigationDrawerItem;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kavitha on 6/2/2017.
 */

public class NavigationDrawerAdapter  extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>{

  private  String TAG = getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    List<NavigationDrawerItem> navigationDrawerItemdatas = Collections.emptyList();


    public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> navigationDrawerItemdatas){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.navigationDrawerItemdatas = navigationDrawerItemdatas;
    }


    public void deleteNavigationDrawerItem(int position){
     navigationDrawerItemdatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.navigation_drawer_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i(TAG,"onBindViewHolder  position ? "+position);

            NavigationDrawerItem currentNavigationDrawerItem = navigationDrawerItemdatas.get(position);
        Log.i(TAG,"onBindViewHolder  nav title ? "+currentNavigationDrawerItem.getNav_title());
           holder.title.setText(currentNavigationDrawerItem.getNav_title());
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,"getItemCount  ? "+navigationDrawerItemdatas.size());
        return navigationDrawerItemdatas.size();
    }


    class  ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}
