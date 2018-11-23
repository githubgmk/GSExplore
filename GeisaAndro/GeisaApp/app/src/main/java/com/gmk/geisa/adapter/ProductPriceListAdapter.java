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
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.squareup.picasso.Picasso;

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
public class ProductPriceListAdapter extends RecyclerView.Adapter<ProductPriceListAdapter.ItemViewHolder> {

    private List<mProductPriceDiskon> mProductPrice;
    //private List<mCustomer> mCustomersReal;
    private final List<mProductPriceDiskon> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;

    private TextView mTextView;
    private int totalselected=0, totalBefore;

    public ProductPriceListAdapter(List<mProductPriceDiskon> customers, Context context, TextView tv) {
        mProductPrice = customers;
        // mCustomersReal=customers;
        mContext = context;
        mTextView = tv;
        totalselected=0;
        //totalBefore = Integer.parseInt(String.valueOf(TextUtils.isDigitsOnly(tv.getText().toString()) ? tv.getText() : 0));
        //if(null!=tv.getText())
        // totalselected = Integer.parseInt(String.valueOf(TextUtils.isDigitsOnly(tv.getText().toString()) ? tv.getText() : 0));//  totalBefore;
        //  Log.e("total before", tv.getText() + "total selected" + totalselected);
        filteredUserList = new ArrayList<>();
        for (mProductPriceDiskon cu : customers) {
            filteredUserList.add(cu);
        }
        userFilter = new UserFilter(this, customers);
    }

    public static int getIcon(Context c, String resName) {
        try {
            String newstr = resName;
            int endIndex = resName.lastIndexOf(".");
            if (endIndex != -1) {
                newstr = resName.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
            return c.getResources().getIdentifier(newstr, "drawable", c.getPackageName());
        } catch (Exception e) {
            Log.e("err", e.toString());
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_product_price_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }

    private String getBulan(String tgl) {
        Calendar tgldtp = getTanggal(tgl);
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL", Locale.getDefault());
        dateFormat.format(tgldtp.getTime());
        return String.valueOf(dateFormat.format(tgldtp.getTime()));
    }

    private String getTahun(String tgl) {
        Calendar tgldtp = getTanggal(tgl);
        return String.valueOf(tgldtp.get(Calendar.YEAR));
    }

    private String getHari(String tgl) {
        Calendar tgldtp = getTanggal(tgl);
        return String.valueOf(tgldtp.get(Calendar.DAY_OF_MONTH));
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
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final mProductPriceDiskon selectedCustomer = filteredUserList.get(position);
        final int posisi = position;
        holder.productId.setText(String.valueOf(selectedCustomer.getProductId()).concat("/").concat(selectedCustomer.getProductCode()));
        holder.productName.setText(selectedCustomer.getProductName());
        holder.productNameDist.setText(selectedCustomer.getProductNameDist());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy");
        Date date;
        String dateFrom = "", dateTo = "";
        // use SimpleDateFormat to define how to PARSE the INPUT
        if (!TextUtils.isEmpty(selectedCustomer.getStartDate())) {
            try {
                date = sdf.parse(selectedCustomer.getStartDate());
                dateFrom = sdfnew.format(date);
            } catch (ParseException e) {
                Log.e("errs", e.toString());
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(selectedCustomer.getEndDate())) {
            try {
                date = sdf.parse(selectedCustomer.getEndDate());
                dateTo = sdfnew.format(date);
            } catch (ParseException e) {
                Log.e("err", e.toString());
                e.printStackTrace();
            }
        }
        holder.productDesc.setText(dateFrom.concat(" s/d ").concat(dateTo));

        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        String nilai = nf.format(Math.round(selectedCustomer.getPrice()));

        holder.price.setText("Rp. " + nilai);
        holder.satuan.setText(String.valueOf("/" + selectedCustomer.getUnitId()));
        if (!TextUtils.isEmpty(selectedCustomer.getFoto())) {
            Picasso.with(mContext)
                    .load(selectedCustomer.getFoto())
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.noimage)
                    .into(holder.productImage);
        }
        if (selectedCustomer.isSelected()) {
            // holder.handleView.setImageResource(android.R.drawable.checkbox_on_background);
            //Log.e("sellected",selectedCustomer.isDraft()+"-xxx");
            holder.itemView.setBackgroundColor(Color.GREEN);
            if(selectedCustomer.isDraft()) {
                holder.itemView.setEnabled(false);
                holder.itemView.setSelected(false);
            }
            //totalselected += 1;
            //if (mTextView != null)
            //    mTextView.setText(String.valueOf(totalselected));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //holder.handleView.setImageResource(R.drawable.ic_reorder_grey);
        }
        hitungSelect();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCustomer.isSelected()) {

                    filteredUserList.get(posisi).setSelected(false);
                    selectedCustomer.setSelected(false);
                    int idx = mProductPrice.indexOf(filteredUserList.get(posisi));
                    mProductPrice.get(idx).setSelected(false);

                    v.setBackgroundColor(Color.TRANSPARENT);
                    //totalselected -= 1;
                    //
                } else {
                    filteredUserList.get(posisi).setSelected(true);
                    selectedCustomer.setSelected(true);
                    int idx = mProductPrice.indexOf(filteredUserList.get(posisi));
                    mProductPrice.get(idx).setSelected(true);

                    v.setBackgroundColor(Color.GREEN);

                    //totalselected += 1;
                    //

                }
                hitungSelect();

            }
        });


    }

    private  void hitungSelect(){
        int select=0;
        for(mProductPriceDiskon cu:mProductPrice){
            if(cu.isSelected())
                select+=1;

        }
        totalselected=select;
        if (mTextView != null)
            mTextView.setText(String.valueOf(totalselected));

    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mProductPriceDiskon getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mProductPriceDiskon> getAllItem() {
        return mProductPrice;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView productId, productName,productNameDist, productDesc, price, satuan;
        public final ImageView productImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            productId = (TextView) itemView.findViewById(R.id.productId);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productNameDist=(TextView) itemView.findViewById(R.id.productNameDist);
            productDesc = (TextView) itemView.findViewById(R.id.productDesc);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            price = (TextView) itemView.findViewById(R.id.price);
            satuan = (TextView) itemView.findViewById(R.id.satuan);


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

        private final ProductPriceListAdapter adapter;

        private final List<mProductPriceDiskon> originalList;

        private final List<mProductPriceDiskon> filteredList;

        private UserFilter(ProductPriceListAdapter adapter, List<mProductPriceDiskon> originalList) {
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

                for (final mProductPriceDiskon user : originalList) {
                    if (String.valueOf(user.getProductId()).toLowerCase().contains(filterPattern) ||
                            user.getProductName().toLowerCase().contains(filterPattern)||
                            user.getUnitId().toLowerCase().contains(filterPattern) ||
                            user.getProductCode().toLowerCase().contains(filterPattern) ||
                            user.getProductNameDist().toLowerCase().contains(filterPattern)
                            ) {
                        this.filteredList.add(user);
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
            adapter.filteredUserList.addAll((ArrayList<mProductPriceDiskon>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
