package com.gmk.geisa.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.SurveyActivity;
import com.androidadvance.androidsurvey.models.Survey;
import com.gmk.geisa.R;
import com.gmk.geisa.controller.OtherController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mSession;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.ItemViewHolder> {

    private List<Survey> mSurvey;

    private final List<Survey> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    OtherController otherController;
    private ProgressDialog pDialog;

    public SurveyListAdapter(List<Survey> survey, Context context, mSession session) {
        mSurvey = survey;
        mContext = context;
        filteredUserList = new ArrayList<>();
        for (Survey cu : survey) {
            filteredUserList.add(cu);
        }
        sessionUser = session;
        userFilter = new UserFilter(this, survey);
        otherController = OtherController.getInstance(mContext);
        otherController.setCallBackSendDataSurvey(callbackSurvey);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_survey, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        final Survey selectedCustomer = filteredUserList.get(position);
        holder.promoname.setText(selectedCustomer.getSurveyName());
        holder.produk.setText(selectedCustomer.getSurveyDesc());
        holder.typepromo.setText(selectedCustomer.getSurveyType());
        holder.bonuspoduk.setText(String.valueOf(selectedCustomer.getSurveyTypeRef()).concat("-").concat(selectedCustomer.getSurveyTypeRefDesc()));
        holder.stardate.setText(selectedCustomer.getPeriodeStart());
        if(selectedCustomer.isUsePeriode()) {
            holder.enddate.setText(selectedCustomer.getPeriodeEnd());
        }else{
            holder.enddate.setText("~");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // v.setBackgroundColor(Color.GREEN);
                Intent inten = new Intent(mContext, SurveyActivity.class);
                inten.putExtra(SurveyActivity.SESSIONSURVEY,selectedCustomer);
                inten.putExtra(SurveyActivity.CREATORID,String.valueOf(sessionUser.getId()));
               // inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // mContext.startActivity(inten);
                Activity origin = (Activity)mContext;
                origin.startActivityForResult(inten,69);
            }
        });


    }

    OtherController.CallBackSendDataSurvey callbackSurvey=new OtherController.CallBackSendDataSurvey() {
        @Override
        public void resultReady(Answers.AllValue survey, boolean hasil) {
            String pesan;
            if(hasil){
                pesan="Sending Survey Success";
            }else{
                pesan="Sending Survey Failed, Will send soon";
            }
            Toast.makeText(mContext,pesan,Toast.LENGTH_SHORT).show();
            if(pDialog!=null)
                pDialog.dismiss();
        }
    };

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 69) {
            if (resultCode == RESULT_OK) {

                //String answers_json = data.getExtras().getString(SurveyActivity.ANSWERSSTRING);

                //Log.e("****", "****************** WE HAVE ANSWERS ******************");
                //Log.e("ANSWERS JSON", answers_json);
                //Log.e("****", "*****************************************************");
                if(data.getSerializableExtra(SurveyActivity.ANSWERSOBJECT)!=null){
                    if (pDialog == null)
                        pDialog = new ProgressDialog(mContext);
                    pDialog.setMessage("Send Survey To Server ...\n Please Wait...");
                    pDialog.show();
                    Answers.AllValue answer= (Answers.AllValue) data.getSerializableExtra(SurveyActivity.ANSWERSOBJECT);
                    //save to database local
                    otherController.sendSurveyToServer(answer);

                }
                //do whatever you want with them...
            }
        }
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
                //  Log.e("tgl",tgldpt.getTime()+",");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
                Log.e("tgl", e.toString() + "");
            }
        } else {
            tglkosong = true;
        }
        return tgldpt;
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public Survey getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<Survey> getAllItem() {
        return mSurvey;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView promoname, produk, bonuspoduk, bonusqty, bonusunit, stardate, enddate, typepromo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            promoname = (TextView) itemView.findViewById(R.id.promoname);
            produk = (TextView) itemView.findViewById(R.id.produk);
            bonuspoduk = (TextView) itemView.findViewById(R.id.bonuspoduk);
            bonusqty = (TextView) itemView.findViewById(R.id.bonusqty);
            bonusunit = (TextView) itemView.findViewById(R.id.bonusunit);
            stardate = (TextView) itemView.findViewById(R.id.stardate);
            enddate = (TextView) itemView.findViewById(R.id.enddate);
            typepromo = (TextView) itemView.findViewById(R.id.typepromo);



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

        private final SurveyListAdapter adapter;

        private final List<Survey> originalList;

        private final List<Survey> filteredList;

        private UserFilter(SurveyListAdapter adapter, List<Survey> originalList) {
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

                for (final Survey user : originalList) {
                    if (user.getSurveyName() != null) {
                        if (user.getSurveyDesc().toLowerCase().contains(filterPattern) || user.getSurveyName().toLowerCase().contains(filterPattern) || user.getSurveyTypeRefDesc().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<Survey>) results.values);
            adapter.notifyDataSetChanged();
        }
    }




}
