package com.apps.navigationdrawer.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.apps.navigationdrawer.R;
import com.apps.navigationdrawer.adapter.NavigationDrawerAdapter;
import com.apps.navigationdrawer.model.NavigationDrawerItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();
    RecyclerView recyclerView;


    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationDrawerAdapter navigationDrawerAdapter;

    View containView;
    private static String[] titles = null;
    private FragmentDrawerListener fragmentDrawerListener;




    public FragmentDrawer() {
        // Required empty public constructor
    }

    public void  setDrawerListener(FragmentDrawerListener fragmentDrawerListener){
    this.fragmentDrawerListener = fragmentDrawerListener;
    }


    public static List<NavigationDrawerItem> getDataa(){
        List<NavigationDrawerItem> data = new ArrayList<>();

        for (int i=0;i<titles.length;i++){
            NavigationDrawerItem navigationDrawerItem = new NavigationDrawerItem();
            navigationDrawerItem.setNav_title(titles[i]);
            data.add(navigationDrawerItem);
        }

        Log.i(TAG,"data ? "+new Gson().toJson(data));

        return data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View laView = inflater.inflate(R.layout.fragment_drawer, container, false);
        recyclerView = (RecyclerView) laView.findViewById(R.id.drawerListRecyclerView);

        navigationDrawerAdapter = new NavigationDrawerAdapter(getActivity(),getDataa());
        recyclerView.setAdapter(navigationDrawerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                fragmentDrawerListener.onDrawerItemSelected(view,position);
                drawerLayout.closeDrawer(containView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return laView;
    }




    static  class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
              this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View chile = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (chile != null && clickListener != null){
                        clickListener.onLongClick(chile,recyclerView.getChildPosition(chile));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(),e.getY());
            if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(childView,rv.getChildPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public static interface  ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }


    public void  setUp(int freagmentId, DrawerLayout drawerLayouts, final Toolbar toolbar){
        containView = getActivity().findViewById(freagmentId);
        drawerLayout = drawerLayouts;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayouts,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1-slideOffset/2);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }


    public interface  FragmentDrawerListener{
        public void onDrawerItemSelected(View view,int position);
    }


}
