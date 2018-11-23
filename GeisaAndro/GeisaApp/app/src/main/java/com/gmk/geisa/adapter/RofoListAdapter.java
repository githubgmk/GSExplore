package com.gmk.geisa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mRofo;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class RofoListAdapter extends RecyclerView.Adapter<RofoListAdapter.ItemViewHolder> {

    private List<mRofo> mProductPrice;
    //private List<mCustomer> mCustomersReal;
    private final List<mRofo> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;

    private TextView mTextView, mTextViewppn, mTextViewttl;
    ArrayList<mListString> listStringUnit = new ArrayList<>();
    private int totalselected, totalBefore;
    private boolean Editable = false;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    ProductController productController;


    public RofoListAdapter(List<mRofo> customers, Context context, boolean editable, TextView tv, TextView tvppn) {
        mProductPrice = customers;
        // mCustomersReal=customers;
        mContext = context;
        mTextView = tv;
        mTextViewppn = tvppn;
        Editable = editable;
        filteredUserList = new ArrayList<>();
        productController = ProductController.getInstance(context);
        for (mRofo cu : customers) {
            filteredUserList.add(cu);
        }
        userFilter = new UserFilter(this, customers);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_rofo_product_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        final mRofo selectedCustomer = filteredUserList.get(position);
        //Log.e("fler",filteredUserList.size()+","+position+","+selectedCustomer.getProductCode()+","+selectedCustomer.getQty()+","+selectedCustomer.getProductName());
        final int posisi = position;
        holder.productId.setText(String.valueOf(selectedCustomer.getProductId()).concat("/").concat(selectedCustomer.getProductCode()));
        holder.productName.setText(selectedCustomer.getProductName());

        holder.qty.setText(String.valueOf(selectedCustomer.getQty()));
        holder.unit.setText(selectedCustomer.getUnitId());


        String nilai = nf.format(Math.round(selectedCustomer.getValue()));

        holder.price.setText("Rp. " + nilai);
        holder.satuan.setText(String.valueOf("/" + selectedCustomer.getUnitId()));
        if (selectedCustomer.getCustName() != null)
            holder.custName.setText(selectedCustomer.getCustName());
        if (selectedCustomer.getStatusName() != null)
            holder.status.setText(selectedCustomer.getStatusName());

        if (selectedCustomer.isSelected()) {
            holder.handleView.setImageResource(android.R.drawable.ic_menu_delete);
            holder.itemView.setBackgroundColor(Color.RED);
            totalselected += 1;
            //if (mTextView != null)
            //    mTextView.setText(String.valueOf(totalselected));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.handleView.setImageDrawable(null);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Editable) {
                    v.setBackgroundColor(Color.GREEN);
                    updateItem(selectedCustomer, posisi, holder);
                }
            }
        });


    }

    public void hitungHarga(ArrayList<mRofo> po) {
        double jumlahtotal = 0, total = 0;
        for (mRofo cu : po) {
            if (!cu.isSelected()) {
                double harga = cu.getValue();
                jumlahtotal += cu.getQty() * harga;
                total += cu.getQty();
            }
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        String nilai = nf.format(Math.round(jumlahtotal));
        mTextView.setText(nilai);
        mTextViewppn.setText(String.valueOf(total));
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mRofo getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mRofo> getAllItem() {
        return mProductPrice;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView productId, productName, price, satuan, qty, unit, status, custName;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productId = (TextView) itemView.findViewById(R.id.productId);
            productName = (TextView) itemView.findViewById(R.id.productName);
            price = (TextView) itemView.findViewById(R.id.price);
            satuan = (TextView) itemView.findViewById(R.id.satuan);
            qty = (TextView) itemView.findViewById(R.id.qty);
            unit = (TextView) itemView.findViewById(R.id.unit);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
            custName = (TextView) itemView.findViewById(R.id.custName);
            status = (TextView) itemView.findViewById(R.id.status);


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

        private final RofoListAdapter adapter;

        private final List<mRofo> originalList;

        private final List<mRofo> filteredList;

        private UserFilter(RofoListAdapter adapter, List<mRofo> originalList) {
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

                for (final mRofo user : originalList) {
                    if (user.getProductName().toLowerCase().contains(filterPattern) || (user.getCustName() != null && user.getCustName().toLowerCase().contains(filterPattern))) {
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
            adapter.filteredUserList.addAll((ArrayList<mRofo>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


    public void updateItem(final mRofo poLine, final int posisi, final ItemViewHolder holder) {
        //imgEdit = true;

        Context context = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.preview_change_product_rofo, null);
        final TextInputEditText custId = (TextInputEditText) dialogView.findViewById(R.id.custid);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final SearchableSpinner listUnit = (SearchableSpinner) dialogView.findViewById(R.id.listUnit);
        final TextInputEditText name = (TextInputEditText) dialogView.findViewById(R.id.name);
        final TextInputEditText qty = (TextInputEditText) dialogView.findViewById(R.id.qty);
        final TextInputEditText unitid = (TextInputEditText) dialogView.findViewById(R.id.unitid);
        final TextInputEditText price = (TextInputEditText) dialogView.findViewById(R.id.price);

        final int[] priceId = new int[2];
        final double[] hargaNew = {0};
        final boolean[] updatenew = {false};


        final ArrayList<mListString> listringunit = new ArrayList<>();
        listringunit.add(new mListString(0, "Select Unit", "-"));
        listUnit.setAdapter(listringunit, 2, 2);


        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();

        if (poLine != null) {
            ArrayList<mProductPriceDiskon> sessionproduk = productController.getAllProductPriceDiskon(String.valueOf(poLine.getDistBranchId()), String.valueOf(poLine.getProductId()));
            //  Log.e(" cek sessi rofo", poLine.getDistBranchId() + "," + poLine.getProductId() + "," + sessionproduk.size());
            //listStringUnit.add(new mListString(0, "Select Level"));
            listStringUnit.clear();
            for (mProductPriceDiskon lv : sessionproduk) {
                mListString isi = new mListString(lv.getRecId(), String.valueOf(lv.getUnitId()), lv.getPriceDiscGroupId(), String.valueOf(lv.getPrice()), String.valueOf(lv.getRecId()));
                listStringUnit.add(isi);
            }
            listUnit.setAdapter(listStringUnit, 4, 4);
            name.setText(poLine.getProductName());
            qty.setText(String.valueOf(poLine.getQty()));
            unitid.setText(poLine.getUnitId());
            priceId[0] = poLine.getPriceId();
            String nilai = nf.format(Math.round(poLine.getValue()));
            price.setText("Rp." + nilai);
            if (poLine.isSelected()) {
                btnDel.setText("UnDelete");
            }
        }
        //qty.requestFocus();


        listUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                unitid.setText(String.valueOf(listStringUnit.get(position).getNilai1()));
                String nilai = nf.format(Math.round(Double.parseDouble(listStringUnit.get(position).getNilai3())));
                price.setText("Rp." + nilai);
                priceId[1] = listStringUnit.get(position).get_id();
                hargaNew[0] = Double.parseDouble(listStringUnit.get(position).getNilai3());
                if (priceId[0] != priceId[1]) {
                    updatenew[0] = true;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poLine.isSelected()) {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    filteredUserList.get(posisi).setSelected(false);
                    poLine.setSelected(false);
                    holder.handleView.setImageDrawable(null);
                    v.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    filteredUserList.get(posisi).setSelected(true);
                    poLine.setSelected(true);
                    holder.handleView.setImageResource(android.R.drawable.ic_menu_delete);
                    v.setBackgroundColor(Color.RED);
                }
                notifyDataSetChanged();
                hitungHarga((ArrayList<mRofo>) filteredUserList);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(qty.getEditableText())) {
                    filteredUserList.get(posisi).setQty(Integer.parseInt(qty.getEditableText().toString()));
                    poLine.setQty(Integer.parseInt(qty.getEditableText().toString()));
                }
                if (!TextUtils.isEmpty(unitid.getEditableText())) {
                    filteredUserList.get(posisi).setUnitId(unitid.getEditableText().toString());
                    poLine.setUnitId(unitid.getEditableText().toString());
                }

                if (updatenew[0]) {
                    filteredUserList.get(posisi).setPriceId(priceId[1]);
                    filteredUserList.get(posisi).setValue(hargaNew[0]);
                    poLine.setPriceId(priceId[1]);
                    poLine.setValue(hargaNew[0]);
                }


                notifyDataSetChanged();
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                hitungHarga((ArrayList<mRofo>) filteredUserList);

                dialog.dismiss();
            }
        });

        //
        //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }


}
