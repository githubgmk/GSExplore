package com.gmk.geisa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTodoList;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemViewHolder> {

    private List<mTodoList> mPO=new ArrayList<>();

    private final List<mTodoList> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private CallPlanController callPlanController;

    public ToDoListAdapter(List<mTodoList> po, Context context, mSession session) {
        //mPO = po;
        mContext = context;
        callPlanController=CallPlanController.getInstance(context);
        filteredUserList = new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        for (mTodoList cu : po) {
            if(cu.getStatusId()!=1 && getTanggal(cu.getCreatedDate()).before(calendar.getTime())){
                //jika sudah dipilih dan tanggal calendar sudah kemarin tidak ditampilkan
            }else {
                mPO.add(cu);
                filteredUserList.add(cu);
            }
        }
        sessionUser = session;
        userFilter = new UserFilter(this, po);
    }

    private Calendar getTanggal(String dtStart) {
        Calendar tgldpt = Calendar.getInstance();
        boolean tglkosong = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date dates = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(dates);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
            }
        } else {
            tglkosong = true;
        }
        return tgldpt;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_todo, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final mTodoList selectedCustomer = filteredUserList.get(position);
        final int posisi =position;
        holder.category.setText(selectedCustomer.getCategory());
        holder.title.setText(selectedCustomer.getTitle());
        holder.detail.setText(selectedCustomer.getDetail());
        holder.docdate.setText(selectedCustomer.getDocDate());
        holder.response.setText(selectedCustomer.getStatusDetail());
        holder.lastupdate.setText(selectedCustomer.getCreatedDate());
        holder.status.setText(selectedCustomer.getStatus());

        Log.e("status read","re"+selectedCustomer.isStatusRead());
        if(selectedCustomer.isStatusRead()){
            holder.handle.setImageResource(R.drawable.btn_check_on);
            holder.itemView.setBackgroundColor(Color.GREEN);
        }else{
            holder.handle.setImageResource(0);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nanti ditambahan lagi untuk update database
                if(!selectedCustomer.isStatusRead()){
                    holder.handle.setImageResource(R.drawable.btn_check_on);
                    holder.itemView.setBackgroundColor(Color.GREEN);
                    selectedCustomer.setStatusRead(true);
                    int idx = mPO.indexOf(filteredUserList.get(posisi));
                    mPO.get(idx).setStatusRead(true);
                    updateTodo(selectedCustomer);

                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mTodoList getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mTodoList> getAllItem() {
        return mPO;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView status,lastupdate,response,docdate,detail,title,category;
        public final ImageView handle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category);
            title = (TextView) itemView.findViewById(R.id.title);
            detail = (TextView) itemView.findViewById(R.id.detail);
            docdate = (TextView) itemView.findViewById(R.id.docdate);
            response = (TextView) itemView.findViewById(R.id.response);
            lastupdate = (TextView) itemView.findViewById(R.id.lastupdate);
            status=(TextView) itemView.findViewById(R.id.status);
            handle=(ImageView) itemView.findViewById(R.id.handle);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }


    }


    public Filter setFilter() {
        return userFilter;
    }

    public static class UserFilter extends Filter {

        private final ToDoListAdapter adapter;

        private final List<mTodoList> originalList;

        private final List<mTodoList> filteredList;

        private UserFilter(ToDoListAdapter adapter, List<mTodoList> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            this.filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                this.filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final mTodoList user : originalList) {
                    if (user.getTitle() != null) {
                        if (user.getCategory().toLowerCase().contains(filterPattern) || user.getTitle().toLowerCase().contains(filterPattern) || user.getStatus().toLowerCase().contains(filterPattern)) {
                            this.filteredList.add(user);
                        }
                    }
                }
            }
            results.values = this.filteredList;
            results.count = this.filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            adapter.filteredUserList.clear();
            adapter.filteredUserList.addAll((ArrayList<mTodoList>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
    //update todo list;
    private void updateTodo(mTodoList todo){
        callPlanController.updateTodoListToServer(todo);
    }



}
