package com.shuvojitkar.recylerview_implementaion.View;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuvojitkar.recylerview_implementaion.Data.DataItem;
import com.shuvojitkar.recylerview_implementaion.Logic.FakeDataSource;
import com.shuvojitkar.recylerview_implementaion.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class MainActivity extends AppCompatActivity implements FakeDataSource.FakeInterface{
    RecyclerView rec;
    LinearLayoutManager lin;
    GridLayoutManager grid;
    Context cn;
    RecAdapter adapter;
    AlertDialog dialog;
    List<DataItem>DataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rec = (RecyclerView) findViewById(R.id.rec);
        lin = new LinearLayoutManager(this);
        cn =this;
        grid = new GridLayoutManager(this,3);
        rec.setLayoutManager(lin);
        rec.setItemAnimator(new DefaultItemAnimator());
        Dialog();
        FakeDataSource fake = new FakeDataSource(this,dialog,this);
      setRecyclerViewItemTouchListener(); //For OnSwipe Data Remove
      // RecyclerAnimation();

    }


    void Dialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void setupAdapterView(final List<DataItem>result) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DataList =result;
                //Toast.makeText(cn, DataList.size(), Toast.LENGTH_SHORT).show();
                adapter =new RecAdapter(DataList);
                rec.setAdapter(adapter);
            }
        });

    }

    //Set On Swipe Data Remove
    private void setRecyclerViewItemTouchListener() {
        //1
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback( ItemTouchHelper.UP | ItemTouchHelper.DOWN ,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int oldPos = viewHolder.getAdapterPosition();
                int newPos = target.getAdapterPosition();
                DataItem data = DataList.get(oldPos);
                DataList.remove(oldPos);
                DataList.add(newPos, data);
                rec.getAdapter().notifyItemMoved(oldPos, newPos);
                return true;
                 }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //3
                int position = viewHolder.getAdapterPosition();
                DataList.remove(position);
                rec.getAdapter().notifyItemRemoved(position); }
        };
        //4
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rec);
    }

/*
    Another Way of Drag and Drop RecylerView
    void RecyclerAnimation(){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(rec);
    }

        void moveItem(int oldPos, int newPos){
        DataItem  dataItem = DataList.get(oldPos);
        DataList.remove(oldPos);
        DataList.add(newPos, dataItem);
        rec.getAdapter().notifyItemMoved(oldPos, newPos);
    }

    void deleteItem(final int position){
        DataList.remove(position);
        rec.getAdapter().notifyItemRemoved(position);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_recycler_manager) {
            changeLayoutManager();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Change the Recycler Layout
    private void changeLayoutManager() {
        if (rec.getLayoutManager().equals(lin)) {
            rec.setLayoutManager(grid);
        } else {
            rec.setLayoutManager(lin);
        }
    }



    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecHolder>{
            List<DataItem> listofData;

        public RecAdapter(List<DataItem> listofData) {
            this.listofData = listofData;
        }

        @Override
        public RecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rec_layout,parent,false);
            return new RecHolder(v);
        }

        @Override
        public void onBindViewHolder(RecHolder holder, int position) {
        DataItem data = listofData.get(position);
            holder.bindData(data);
        }


        @Override
        public int getItemCount() {
            return listofData.size();
        }

        public class RecHolder extends RecyclerView.ViewHolder {
            TextView name,designation,phone;
            ImageView img;
            public RecHolder(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img);
                name = (TextView) itemView.findViewById(R.id.name);
                designation = (TextView) itemView.findViewById(R.id.designation);
                phone = (TextView) itemView.findViewById(R.id.phn);
            }

            void bindData(DataItem data){
                name.setText(data.getName());
                designation.setText(data.getDesignation());
                phone.setText(data.getPhn());
                Log.i("Data",data.getName());
                Picasso.with(img.getContext()).load(data.getImgUrl()).into(img);

            }
        }

    }
}


