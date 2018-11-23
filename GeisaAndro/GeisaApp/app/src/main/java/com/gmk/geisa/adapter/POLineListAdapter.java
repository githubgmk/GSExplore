package com.gmk.geisa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.controller.PoController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.text.MessageFormat;
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
public class POLineListAdapter extends RecyclerView.Adapter<POLineListAdapter.ItemViewHolder> {

    private List<mPoLine> mProductPrice;
    private mPO POPP;
    private ArrayList<mPoLine> newPP = new ArrayList<>();
    private final List<mPoLine> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private TextView mTextView, mTextViewppn, mTextViewttl;
    private int DistBanchId, LevelCust;
    String PoIdReff;
    PoController poController;
    private boolean isPP = false, isCopyPP = false;
    private ProductController productController;
    ArrayList<mListString> listStringUnit = new ArrayList<>();
    ArrayList<mListString> listStringBonus = new ArrayList<>();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);


    public POLineListAdapter(int levelCust, List<mPoLine> customers, Context context, boolean pp, boolean copyPP, String idPPRef, int distBanchId, TextView tv, TextView tvppn, TextView tvttl) {
        mProductPrice = customers;
        LevelCust = levelCust;
        PoIdReff = idPPRef;
        //POPP=popp;
        //newPP.addAll(popp);
        poController = PoController.getInstance(context);
        mContext = context;
        mTextView = tv;
        mTextViewppn = tvppn;
        mTextViewttl = tvttl;
        isPP = pp;
        isCopyPP = copyPP;
        DistBanchId = distBanchId;
        filteredUserList = new ArrayList<>();
        productController = ProductController.getInstance(context);
        filteredUserList.addAll(customers);
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
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_po_product_list, parent, false);
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
        final mPoLine selectedCustomer = filteredUserList.get(position);
    //final List<mPoLine> ppnya=POPP.getPoLines()   ;
        final int posisi = position;
        holder.productId.setText(String.valueOf(selectedCustomer.getProductId()).concat("/").concat(selectedCustomer.getProductCode()));
        holder.productName.setText(selectedCustomer.getProductName());

        holder.disc1.setText(String.valueOf(selectedCustomer.getDisc1()));
        holder.disc2.setText(String.valueOf(selectedCustomer.getDisc2()));
        if (LevelCust == 1) {
            holder.disc3.setText(MessageFormat.format(" Disc3 {0}%", String.valueOf(selectedCustomer.getDisc3())));
        }
        String nilai0 = nf.format(Math.round(selectedCustomer.getDiscRp()));
        holder.discCash.setText(nilai0);
        holder.qty.setText(String.valueOf(selectedCustomer.getQty()));
        holder.unit.setText(selectedCustomer.getUnitId());


        String nilai = nf.format(Math.round(selectedCustomer.getUnitPrice()));

        holder.price.setText("Rp. " + nilai);
        holder.satuan.setText(String.valueOf("/" + selectedCustomer.getUnitId()));

        if (selectedCustomer.isSelected()) {
            holder.handleView.setImageResource(android.R.drawable.ic_menu_delete);
            holder.itemView.setBackgroundColor(Color.RED);
            //totalselected += 1;
            //if (mTextView != null)
            //    mTextView.setText(String.valueOf(totalselected));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.handleView.setImageDrawable(null);
        }

        if (selectedCustomer.getPoLineBonus() != null) {
            //Log.e("isi bonus",selectedCustomer.getProductName()+","+posisi+","+selectedCustomer.getPoLineBonus().getProductName());
            holder.bonuspoduk.setText(selectedCustomer.getPoLineBonus().getProductName());
            holder.bonusqty.setText(String.valueOf(selectedCustomer.getPoLineBonus().getQty()));
            holder.bonusunit.setText(selectedCustomer.getPoLineBonus().getUnitId());
        } else {
            holder.bonuspoduk.setText("");
            holder.bonusqty.setText("");
            holder.bonusunit.setText("");
        }

        //holder.productImage.setImageResource(imgResource);


        //Log.e("isi tgl",selectedCustomer.getProductName()+","+posisi+","+totalselected);
        //holder.tglcall.setText(getHari(selectedCustomer.getProductId()));
        //holder.blncall.setText(getBulan(selectedCustomer.getCallPlanDate()));
        //holder.tahuncall.setText(getTahun(selectedCustomer.getCallPlanDate()));
       /* holder.customerName.setText(selectedCustomer.getProductName());
        holder.customerAlias.setText(selectedCustomer.getProductId());
        holder.customerAddress.setText(selectedCustomer.getProductSimpleDescription());

         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.GREEN);
                updateItem(LevelCust, selectedCustomer, posisi, holder);


                /*if (selectedCustomer.isSelected()) {

                    filteredUserList.get(posisi).setSelected(false);
                    selectedCustomer.setSelected(false);
                    int idx = mProductPrice.indexOf(filteredUserList.get(posisi));
                    mProductPrice.get(idx).setSelected(false);

                    v.setBackgroundColor(Color.TRANSPARENT);
                    totalselected -= 1;
                    //
                } else {
                    filteredUserList.get(posisi).setSelected(true);
                    selectedCustomer.setSelected(true);
                    int idx = mProductPrice.indexOf(filteredUserList.get(posisi));
                    mProductPrice.get(idx).setSelected(true);

                    v.setBackgroundColor(Color.GREEN);

                    totalselected += 1;
                    //

                }
                if (mTextView != null)
                    mTextView.setText(String.valueOf(totalselected));*/

            }
        });


    }

    public void hitungHarga(ArrayList<mPoLine> po) {
        double jumlahtotal = 0, ppn = 0, total = 0;
        for (mPoLine cu : po) {
            if (!cu.isSelected()) {
                //nanti rubah untuk diskon pengurangan
                double harga = 0;
                if (cu.isIncludePPN()) {
                    harga = (cu.getUnitPrice() / 1.1) * 1;
                } else {
                    harga = cu.getUnitPrice();
                }
                double diskontotal = (1 - (1 - cu.getDisc1() / 100) * (1 - cu.getDisc2() / 100) * (1 - cu.getDisc3() / 100));
                jumlahtotal += cu.getQty() * (harga - (harga * diskontotal) - cu.getDiscRp());
            }
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        String nilai = nf.format(Math.round(jumlahtotal));
        ppn = jumlahtotal * 0.1;
        String ppnnilai = nf.format(Math.round(ppn));
        total = jumlahtotal + ppn;
        String ttlnilai = nf.format(Math.round(total));
        mTextView.setText(nilai);
        mTextViewppn.setText(ppnnilai);
        mTextViewttl.setText(ttlnilai);
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mPoLine getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mPoLine> getAllItem() {
        return mProductPrice;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView productId, productName, disc1, disc2, disc3, discCash, price, satuan, qty, unit, bonuspoduk, bonusqty, bonusunit;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productId = (TextView) itemView.findViewById(R.id.productId);
            productName = (TextView) itemView.findViewById(R.id.productName);
            disc1 = (TextView) itemView.findViewById(R.id.disc1);
            disc2 = (TextView) itemView.findViewById(R.id.disc2);
            disc3 = (TextView) itemView.findViewById(R.id.disc3);
            discCash = (TextView) itemView.findViewById(R.id.discCash);
            price = (TextView) itemView.findViewById(R.id.price);
            satuan = (TextView) itemView.findViewById(R.id.satuan);
            qty = (TextView) itemView.findViewById(R.id.qty);
            unit = (TextView) itemView.findViewById(R.id.unit);
            bonuspoduk = (TextView) itemView.findViewById(R.id.bonuspoduk);
            bonusqty = (TextView) itemView.findViewById(R.id.bonusqty);
            bonusunit = (TextView) itemView.findViewById(R.id.bonusunit);
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

        private final POLineListAdapter adapter;

        private final List<mPoLine> originalList;

        private final List<mPoLine> filteredList;

        private UserFilter(POLineListAdapter adapter, List<mPoLine> originalList) {
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

                for (final mPoLine user : originalList) {
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
            adapter.filteredUserList.addAll((ArrayList<mPoLine>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


    public void updateItem(final int levelCust, final mPoLine poLine, final int posisi, final ItemViewHolder holder) {
        //imgEdit = true;
        //Log.e("mulai update","mu;ai update");
        final double[] BonusPrice = new double[1];
        final double[] BonusDiscoundCash = new double[1];
        final int[] BonusPriceId = new int[2];
        final String[] BonusProductCode = new String[1];
        final String[] BonusProductName = new String[1];
        final String[] RefRecId = new String[1];
        final boolean[] updatenewbonus = {false};
        final ArrayList<mPoLine> poLinesPP;


        final Context context = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.preview_change_product_po, null);
        final TextInputEditText custId = (TextInputEditText) dialogView.findViewById(R.id.custid);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final SearchableSpinner listUnit = (SearchableSpinner) dialogView.findViewById(R.id.listUnit);
        final TextInputEditText name = (TextInputEditText) dialogView.findViewById(R.id.name);
        final TextInputEditText qty = (TextInputEditText) dialogView.findViewById(R.id.qty);
        final TextInputEditText unitid = (TextInputEditText) dialogView.findViewById(R.id.unitid);
        final TextInputEditText disc1 = (TextInputEditText) dialogView.findViewById(R.id.disc1);
        final TextInputEditText disc2 = (TextInputEditText) dialogView.findViewById(R.id.disc2);
        final TextInputLayout disc2view = (TextInputLayout) dialogView.findViewById(R.id.disc2view);
        final TextInputEditText disc3 = (TextInputEditText) dialogView.findViewById(R.id.disc3);
        final TextInputLayout disc3view = (TextInputLayout) dialogView.findViewById(R.id.disc3view);
        final TextInputEditText discCash = (TextInputEditText) dialogView.findViewById(R.id.discCash);
        final CheckBox includePPN = (CheckBox) dialogView.findViewById(R.id.includePPN);
        final TextInputEditText price = (TextInputEditText) dialogView.findViewById(R.id.price);
        final TextInputEditText pricelist = (TextInputEditText) dialogView.findViewById(R.id.pricelist);
        final TextInputEditText bonusid = (TextInputEditText) dialogView.findViewById(R.id.bonusid);
        final TextInputEditText bonusunit = (TextInputEditText) dialogView.findViewById(R.id.bonusunit);
        final TextInputEditText bonusqty = (TextInputEditText) dialogView.findViewById(R.id.bonusqty);
        final SearchableSpinner listbonus = (SearchableSpinner) dialogView.findViewById(R.id.listbonus);

        final ArrayList<mListString> listringunit = new ArrayList<>();
        listringunit.add(new mListString(0, "Select Unit", "-"));
        listUnit.setAdapter(listringunit, 2, 2);

        final int[] priceId = new int[4];
        final double[] priceListNew = new double[5];
        final double[] hargaNew = {0};
        final boolean[] updatenew = {false};
        final int priceIdDefault;
        //Log.e("isi poref","xx"+PoIdReff);
        poLinesPP = poController.GetAllPOLineByPoId(PoIdReff);

        if (isPP) {
            if (levelCust == 1) {
                disc2view.setHint("Disc 2-(HO)*");
                disc3.setEnabled(true);
                disc3view.setVisibility(View.VISIBLE);
            } else {
                disc3.setEnabled(false);
                disc3view.setVisibility(View.GONE);
            }
            disc1.setEnabled(true);
            disc2.setEnabled(true);
            discCash.setEnabled(true);
            bonusqty.setEnabled(true);
            listbonus.setEnabled(true);
        } else {
            disc1.setEnabled(false);
            disc2.setEnabled(false);
            disc3.setEnabled(false);
            disc3view.setVisibility(View.GONE);
            discCash.setEnabled(false);
            bonusqty.setEnabled(false);
            listbonus.setEnabled(false);
        }


        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();

        if (poLine != null) {
            priceIdDefault = poLine.getPriceId();
            //besok dirubah lagi untuk detail produk bonus
            ArrayList<mProductPriceDiskon> plan = productController.getAllProductPriceDiskon(String.valueOf(DistBanchId));
            listStringBonus.clear();
            mListString isis = new mListString(0, "Select Product Bonus", "Id", "code", "unit");
            isis.setNilai5("");
            isis.setNilai6("");
            listStringBonus.add(isis);
            for (mProductPriceDiskon lv : plan) {
                mListString isi = new mListString(lv.getRecId(), lv.getProductName(), String.valueOf(lv.getProductId()), String.valueOf(lv.getProductCode()), lv.getUnitId());
                isi.setNilai5(String.valueOf(lv.getRecId()));
                isi.setNilai6(String.valueOf(lv.getPrice()));
                isi.setNilai7(String.valueOf(lv.getPrice()));
                listStringBonus.add(isi);
            }
            listbonus.setAdapter(listStringBonus, 4, 4);
            if (poLine.getPoLineBonus() != null && poLine.getPoLineBonus().getRecIdTab() != null) {
                int i = 0;
                for (mListString t : listStringBonus) {
                    //  Log.e("isi list", t.getNilai5()+","+poLine.getPoLineBonus().getRecIdTab());
                    if (t.getNilai5().equalsIgnoreCase(String.valueOf(poLine.getPoLineBonus().getPriceId()))) {
                        listbonus.setSelection(i);
                        break;
                    }
                    i++;
                }
            } else {
                int i = 0;
                for (mListString t : listStringBonus) {
                    //  Log.e("isi list", t.getNilai5()+","+poLine.getPoLineBonus().getRecIdTab());
                    if (t.getNilai5().equalsIgnoreCase(String.valueOf(priceIdDefault))) {
                        listbonus.setSelection(i);
                        break;
                    }
                    i++;
                }
            }


            ArrayList<mProductPriceDiskon> sessionproduk = productController.getAllProductPriceDiskon(String.valueOf(DistBanchId), String.valueOf(poLine.getProductId()));
            // Log.e(" cek sessi po", DistBanchId + "," + poLine.getProductId() + "," + sessionproduk.size());
            listStringUnit.clear();
            for (mProductPriceDiskon lv : sessionproduk) {
                mListString isi = new mListString(lv.getRecId(), String.valueOf(lv.getUnitId()), lv.getPriceDiscGroupId(), String.valueOf(lv.getPrice()), String.valueOf(lv.getRecId()));
                isi.setNilai5(String.valueOf(lv.getDisc1()));
                isi.setNilai6(String.valueOf(lv.getDisc2()));
                isi.setNilai7(String.valueOf(lv.getDisc3()));
                listStringUnit.add(isi);
            }
            listUnit.setAdapter(listStringUnit, 4, 4);
            if (poLine.getUnitId() != null) {
                int i = 0;
                for (mListString t : listStringUnit) {
                    // Log.e("isi list unit",t.getNilai1()+","+poLine.getUnitId());
                    if (t.getNilai1().equalsIgnoreCase(poLine.getUnitId())) {
                        listUnit.setSelection(i);
                        break;
                    }
                    i++;
                }
            }

            String names = poLine.getProductId() + " - " + poLine.getProductName();
            name.setText(names);
            qty.setText(String.valueOf(poLine.getQty()));
            unitid.setText(poLine.getUnitId());
            price.setText(String.valueOf(poLine.getUnitPrice()));
            pricelist.setText(String.valueOf(poLine.getPriceList()));
            includePPN.setChecked(poLine.isIncludePPN());
            disc1.setText(String.valueOf(poLine.getDisc1()));
            disc2.setText(String.valueOf(poLine.getDisc2()));
            disc3.setText(String.valueOf(poLine.getDisc3()));
            discCash.setText(String.valueOf(poLine.getDiscRp()));
            priceId[0] = poLine.getPriceId();
            if (poLine.getPoLineBonus() != null) {
                // poLineBonus = poLine.getPoLineBonus();
                BonusPriceId[0] = poLine.getPoLineBonus().getPriceId();
                bonusqty.setText(String.valueOf(poLine.getPoLineBonus().getQty()));
            }
            if (poLine.isSelected()) {
                btnDel.setText("UnDelete");
            }
        }
        bonusqty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Log.e("lost focus","lost focus");
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            }
        });
        qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //cek jika dirubah qty kelipatan akan tambah bonus
                    if (isCopyPP) {
                        int hasil = 0;
                        double bonus = 0, item, itemstandar = 0;
                        item = Double.parseDouble(qty.getEditableText().toString());
                        for (mPoLine cu : poLinesPP) {
                            // Log.e("masuk sini",cu.getQty()+","+cu.getPoLineBonus().getQty());
                            if (poLine.getPriceId() == cu.getPriceId() && poLine.getProductId() == cu.getProductId()) {
                                itemstandar = cu.getQty();
                                double hitung = item / cu.getQty();
                                hasil = (int) hitung;
                                bonus = cu.getPoLineBonus().getQty();
                                //  Log.e("masuk sini bisa",cu.getQty()+","+cu.getPoLineBonus().getQty());
                                break;
                            }
                        }
                        double ttl = hasil * bonus;
                        if (ttl == 0 && item >= itemstandar) {
                            bonusqty.setText(String.valueOf(bonus));
                        } else {
                            bonusqty.setText(String.valueOf(ttl));
                        }
                    }
                }
            }
        });

        listUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                unitid.setText(String.valueOf(listStringUnit.get(position).getNilai1()));
                // String nilai = nf.format(Math.round(Double.parseDouble(listStringUnit.get(position).getNilai3())));
                price.setText(listStringUnit.get(position).getNilai3());
                priceId[1] = listStringUnit.get(position).get_id();
                priceListNew[0] = Double.parseDouble(listStringUnit.get(position).getNilai3());//pricelist
                priceListNew[1] = Double.parseDouble(listStringUnit.get(position).getNilai5());//diskon1
                priceListNew[2] = Double.parseDouble(listStringUnit.get(position).getNilai6());//diskon2
                priceListNew[3] = Double.parseDouble(listStringUnit.get(position).getNilai7());//diskon3
                hargaNew[0] = Double.parseDouble(listStringUnit.get(position).getNilai3());
                if (priceId[0] != priceId[1]) {
                    updatenew[0] = true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listbonus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                if (listStringBonus.get(position).get_id() != 0) {
                    bonusid.setText(String.valueOf(listStringBonus.get(position).getNilai2()));
                    bonusunit.setText(String.valueOf(listStringBonus.get(position).getNilai4()));
                    BonusProductName[0] = String.valueOf(listStringBonus.get(position).getNilai1());
                    BonusPrice[0] = Double.valueOf(listStringBonus.get(position).getNilai6());
                    RefRecId[0] = poLine.getRecIdTab();
                    BonusPriceId[1] = listStringBonus.get(position).get_id();
                    BonusProductCode[0] = listStringBonus.get(position).getNilai3();
                    BonusDiscoundCash[0] = Double.valueOf(listStringBonus.get(position).getNilai6());
                    if (BonusPriceId[0] != BonusPriceId[1]) {
                        updatenewbonus[0] = true;
                    }

                } else {
                    bonusid.setText(String.valueOf(listStringBonus.get(position).get_id()));
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
                    if (filteredUserList.get(posisi).getPoLineBonus() != null)
                        filteredUserList.get(posisi).getPoLineBonus().setSelected(true);
                    poLine.setSelected(false);
                    if (poLine.getPoLineBonus() != null)
                        poLine.getPoLineBonus().setSelected(false);
                    holder.handleView.setImageDrawable(null);
                    v.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    filteredUserList.get(posisi).setSelected(true);
                    if (filteredUserList.get(posisi).getPoLineBonus() != null)
                        filteredUserList.get(posisi).getPoLineBonus().setSelected(true);
                    poLine.setSelected(true);
                    if (poLine.getPoLineBonus() != null)
                        poLine.getPoLineBonus().setSelected(true);
                    holder.handleView.setImageResource(android.R.drawable.ic_menu_delete);
                    v.setBackgroundColor(Color.RED);
                }
                notifyDataSetChanged();
                hitungHarga((ArrayList<mPoLine>) filteredUserList);
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
                //final List<mPoLine> poLinestemp=popp;

                if (!TextUtils.isEmpty(qty.getEditableText())) {
                    filteredUserList.get(posisi).setQty(Double.parseDouble(qty.getEditableText().toString()));
                    poLine.setQty(Double.parseDouble(qty.getEditableText().toString()));
                    if (isCopyPP) {
                        int hasil = 0;
                        double bonus = 0, item, itemstandar = 0;
                        item = Double.parseDouble(qty.getEditableText().toString());
                        for (mPoLine cu : poLinesPP) {
                            // Log.e("masuk sini",cu.getQty()+","+cu.getPoLineBonus().getQty());
                            if (poLine.getPriceId() == cu.getPriceId() && poLine.getProductId() == cu.getProductId()) {
                                itemstandar = cu.getQty();
                                double hitung = item / cu.getQty();
                                hasil = (int) hitung;
                                bonus = cu.getPoLineBonus().getQty();
                                //  Log.e("masuk sini bisa",cu.getQty()+","+cu.getPoLineBonus().getQty());
                                break;
                            }
                        }
                        double ttl = hasil * bonus;
                        if (ttl == 0 && item >= itemstandar) {
                            bonusqty.setText(String.valueOf(bonus));
                        } else {
                            bonusqty.setText(String.valueOf(ttl));
                        }
                    }
                }

                if (!TextUtils.isEmpty(unitid.getEditableText())) {
                    filteredUserList.get(posisi).setUnitId(unitid.getEditableText().toString());
                    poLine.setUnitId(unitid.getEditableText().toString());
                }
                if (!TextUtils.isEmpty(price.getEditableText())) {
                    filteredUserList.get(posisi).setUnitPrice(Double.parseDouble(price.getEditableText().toString()));
                    poLine.setUnitPrice(Double.parseDouble(price.getEditableText().toString()));
                }
                if (!TextUtils.isEmpty(pricelist.getEditableText())) {
                    filteredUserList.get(posisi).setPriceList(Double.parseDouble(pricelist.getEditableText().toString()));
                    poLine.setPriceList(Double.parseDouble(pricelist.getEditableText().toString()));
                }
                if (!TextUtils.isEmpty(disc1.getEditableText())) {
                    filteredUserList.get(posisi).setDisc1(Double.parseDouble(disc1.getEditableText().toString()));
                    poLine.setDisc1(Double.parseDouble(disc1.getEditableText().toString()));
                }
                if (!TextUtils.isEmpty(disc2.getEditableText())) {
                    filteredUserList.get(posisi).setDisc2(Double.parseDouble(disc2.getEditableText().toString()));
                    poLine.setDisc2(Double.parseDouble(disc2.getEditableText().toString()));
                }
                if (!TextUtils.isEmpty(disc3.getEditableText())) {
                    filteredUserList.get(posisi).setDisc3(Double.parseDouble(disc3.getEditableText().toString()));
                    poLine.setDisc3(Double.parseDouble(disc3.getEditableText().toString()));
                }
                if (!TextUtils.isEmpty(discCash.getEditableText())) {
                    filteredUserList.get(posisi).setDiscRp(Double.parseDouble(discCash.getEditableText().toString()));
                    poLine.setDiscRp(Double.parseDouble(discCash.getEditableText().toString()));
                }
                filteredUserList.get(posisi).setIncludePPN(includePPN.isChecked());
                poLine.setIncludePPN(includePPN.isChecked());

                //tambahan juga untuk pass save
                if (updatenew[0]) {

                    filteredUserList.get(posisi).setPriceId(priceId[1]);
                    filteredUserList.get(posisi).setUnitPrice(hargaNew[0]);
                    filteredUserList.get(posisi).setPriceList(priceListNew[0]);
                    filteredUserList.get(posisi).setDisc1(priceListNew[1]);
                    filteredUserList.get(posisi).setDisc2(priceListNew[2]);
                    filteredUserList.get(posisi).setDisc3(priceListNew[3]);
                    poLine.setPriceId(priceId[1]);
                    poLine.setUnitPrice(hargaNew[0]);
                    poLine.setPriceList(priceListNew[0]);
                    poLine.setDisc1(priceListNew[1]);
                    poLine.setDisc2(priceListNew[2]);
                    poLine.setDisc3(priceListNew[3]);


                }

               /* if(finalPoLineBonus !=null){
                    filteredUserList.get(posisi).setPoLineBonus(poLineBonus);
                    poLine.setPoLineBonus(poLineBonus);
                }else {*/
                if (!bonusqty.getEditableText().toString().equalsIgnoreCase("")) {
                    if (bonusqty.getEditableText().toString().equalsIgnoreCase("0")) {
                        filteredUserList.get(posisi).setPoLineBonus(null);
                        poLine.setPoLineBonus(null);
                    } else {
                       /* for(mPoLine cu: POPP){
                            Log.e("terakhir sebelumnya",cu.getQty()+","+cu.getPoLineBonus().getQty());
                        }*/
                        Calendar calss = Calendar.getInstance();
                        SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat smp3 = new SimpleDateFormat("yyyyMMddhhmmss");
                        double qtybns = 0;
                        if (!bonusqty.getEditableText().toString().equalsIgnoreCase(""))
                            qtybns = Double.parseDouble(bonusqty.getEditableText().toString());
                        mPoLine poLineBonus = new mPoLine(poLine.getRecIdTab().concat("#1"),
                                poLine.getPoId(), Integer.parseInt(bonusid.getEditableText().toString()), BonusProductName[0],
                                BonusProductCode[0], qtybns, BonusPrice[0], bonusunit.getEditableText().toString(),
                                BonusPriceId[1], BonusPrice[0], 0, 0, 0, 0,
                                BonusDiscoundCash[0] * qtybns, 0, RefRecId[0], 0, false, smp2.format(calss.getTime()), smp2.format(calss.getTime()), 1);

                        filteredUserList.get(posisi).setPoLineBonus(poLineBonus);
                        poLine.setPoLineBonus(poLineBonus);
                      /*  for(mPoLine cu: POPP){
                            Log.e("terakhir sesudah lg ya",cu.getQty()+","+cu.getPoLineBonus().getQty());
                        }*/
                    }
                }
                // }

                /*POPP=poLinestemp;
                for(mPoLine cu: POPP){
                    Log.e("terakhir bgt",cu.getQty()+","+cu.getPoLineBonus().getQty());
                }*/

                notifyDataSetChanged();
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                //mTextView.setText("1000000");
                hitungHarga((ArrayList<mPoLine>) filteredUserList);


                dialog.dismiss();
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }

}
