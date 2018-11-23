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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.controller.UnitController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mUnit;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

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
public class SampleLineListAdapter extends RecyclerView.Adapter<SampleLineListAdapter.ItemViewHolder> {

    private List<mSample.mProductSample> mProductPrice;
    //private List<mCustomer> mCustomersReal;
    private final List<mSample.mProductSample> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;

    private int DistBanchId, totalselected, totalBefore;
    private boolean isPP = false;
    private ProductController productController;
    private UnitController unitController;
    ArrayList<mListString> listStringUnit = new ArrayList<>();
    ArrayList<mListString> listStringBonus = new ArrayList<>();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);


    public SampleLineListAdapter(List<mSample.mProductSample> customers, Context context) {
        mProductPrice = customers;
        // mCustomersReal=customers;
        mContext = context;
        filteredUserList = new ArrayList<>();
        productController = ProductController.getInstance(context);
        unitController=UnitController.getInstance(context);
        for (mSample.mProductSample cu : customers) {
            filteredUserList.add(cu);
           // Log.e("filterer",filteredUserList.size()+","+cu.getProductId());
        }
       // Log.e("filterer ada ngak ",filteredUserList.size()+","+filteredUserList.get(0).getProductId());
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
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_sample_product_list, parent, false);
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
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        final mSample.mProductSample selectedCustomer = filteredUserList.get(position);
        final int posisi = position;
        Log.e("isi posis",""+posisi);
        //Log.e("isi filtered",selectedCustomer.getProductId()+"");
        holder.productId.setText(String.valueOf(selectedCustomer.getProductId()));
        holder.productName.setText(selectedCustomer.getProductName());
        holder.qty.setText(String.valueOf(selectedCustomer.getQty()));
        holder.satuan.setText(selectedCustomer.getKemasan());
        holder.note.setText(selectedCustomer.getNote());

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
                v.setBackgroundColor(Color.GREEN);
                updateItem(selectedCustomer, posisi, holder);

            }
        });


    }



    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mSample.mProductSample getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mSample.mProductSample> getAllItem() {
        return mProductPrice;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView productId, productName,  qty, satuan,infoSample,note;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productId = (TextView) itemView.findViewById(R.id.productId);
            productName = (TextView) itemView.findViewById(R.id.productName);
            satuan = (TextView) itemView.findViewById(R.id.satuan);
            qty = (TextView) itemView.findViewById(R.id.qty);
            infoSample=(TextView) itemView.findViewById(R.id.infoSample);
            note=(TextView) itemView.findViewById(R.id.note);
            handleView = (ImageView) itemView.findViewById(R.id.handle);


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

        private final SampleLineListAdapter adapter;

        private final List<mSample.mProductSample> originalList;

        private final List<mSample.mProductSample> filteredList;

        private UserFilter(SampleLineListAdapter adapter, List<mSample.mProductSample> originalList) {
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

                for (final mSample.mProductSample user : originalList) {
                    if (String.valueOf(user.getProductId()).toLowerCase().contains(filterPattern) || user.getProductName().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<mSample.mProductSample>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


    public void updateItem(final mSample.mProductSample poLine, final int posisi, final ItemViewHolder holder) {
        //imgEdit = true;

        final Context context = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.preview_change_product_sample, null);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final SearchableSpinner listUnit = (SearchableSpinner) dialogView.findViewById(R.id.listUnit);
        final TextInputEditText productName = (TextInputEditText) dialogView.findViewById(R.id.productName);
        final TextInputEditText qty = (TextInputEditText) dialogView.findViewById(R.id.qty);
        final TextInputEditText unitid = (TextInputEditText) dialogView.findViewById(R.id.unitid);
        final TextInputEditText note = (TextInputEditText) dialogView.findViewById(R.id.note);

        listStringUnit.clear();
        listStringUnit.add(new mListString(0, "-", "Select Unit"));

        ArrayList<mUnit> units=unitController.getUnitByStatus(1);
        int urutan=1;
        for (mUnit lv : units) {
            mListString isi = new mListString(urutan, lv.getUnitId(), lv.getUnitName());
            listStringUnit.add(isi);
            urutan++;
        }
        listUnit.setAdapter(listStringUnit, 2, 2);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();

        if (poLine != null) {

            if (poLine.getKemasan() != null) {
                int i = 0;
                for (mListString t : listStringUnit) {
                    // Log.e("isi list unit",t.getNilai1()+","+poLine.getUnitId());
                    if (t.getNilai1().equalsIgnoreCase(poLine.getKemasan())) {
                        listUnit.setSelection(i);
                        break;
                    }
                    i++;
                }
            }


            productName.setText(poLine.getProductName());
            qty.setText(String.valueOf(poLine.getQty()));
            unitid.setText(poLine.getKemasan());
            note.setText(poLine.getNote());
            if (poLine.isSelected()) {
                btnDel.setText("UnDelete");
            }
        }

        listUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                unitid.setText(String.valueOf(listStringUnit.get(position).getNilai1()));

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
                View focusView = null;
                unitid.setError(null);
                boolean cancel=false;
                //Log.e("sample disini","sa"+qty.getEditableText().toString());
                if(!TextUtils.isEmpty(qty.getEditableText().toString())){
                    double isi=Double.parseDouble(qty.getEditableText().toString());
                    if(isi>0 && unitid.getEditableText().toString().equals("-")){
                        unitid.setError(context.getString(R.string.error_field_required));
                        focusView = unitid;
                        cancel = true;
                    }
                    Log.e("masuk kesini"," ttl"+isi);
                }
                if(cancel){
                    focusView.requestFocus();
                }else {
                    if (!TextUtils.isEmpty(qty.getEditableText())) {
                        filteredUserList.get(posisi).setQty(Double.parseDouble(qty.getEditableText().toString()));
                        poLine.setQty(Double.parseDouble(qty.getEditableText().toString()));
                    }
                    if (!TextUtils.isEmpty(unitid.getEditableText())) {
                        filteredUserList.get(posisi).setKemasan(unitid.getEditableText().toString());
                        poLine.setKemasan(unitid.getEditableText().toString());
                    }
                    if (!TextUtils.isEmpty(note.getEditableText())) {
                        filteredUserList.get(posisi).setNote(note.getEditableText().toString());
                        poLine.setNote(note.getEditableText().toString());
                    }

                    notifyDataSetChanged();
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);

                    dialog.dismiss();
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }

}
