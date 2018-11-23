package com.gmk.geisa.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPOCustInfo;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class POTabCustomerInfoViewFragment extends Fragment implements com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    mPOCustInfo plan = null;
    mPO PO = null;


    final ArrayList<mListString> PoVia = new ArrayList<>();
    String distpic = "", AlamatKirim = "";
    TextInputEditText shipmentdate, povia, endperiode, picdist, postatus, shipmentaddress, notes, mechanisme;
    SearchableSpinner listPoBy;


    TextInputLayout endperiodegroup, mechanismegroup;
    int typePlan, lokasi;
    boolean isPP = false, isCopyPP = false,isSellOut=false;


    public static POTabCustomerInfoViewFragment newInstant() {
        POTabCustomerInfoViewFragment poDetailFragment = new POTabCustomerInfoViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        poDetailFragment.setArguments(args);
        return poDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("sesiPlan"))
                plan = (mPOCustInfo) arguments.getSerializable("sesiPlan");
        }
        // Log.e("isi call prod", " isinya" + plan.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_po_new_detail_general, container, false);
        endperiodegroup = (TextInputLayout) view.findViewById(R.id.endperiodegroup);
        mechanismegroup = (TextInputLayout) view.findViewById(R.id.mechanismegroup);
        shipmentdate = (TextInputEditText) view.findViewById(R.id.shipmentdate);
        endperiode = (TextInputEditText) view.findViewById(R.id.endperiode);
        mechanisme = (TextInputEditText) view.findViewById(R.id.mechanisme);
        povia = (TextInputEditText) view.findViewById(R.id.povia);
        postatus = (TextInputEditText) view.findViewById(R.id.postatus);
        shipmentaddress = (TextInputEditText) view.findViewById(R.id.shipmentaddress);
        notes = (TextInputEditText) view.findViewById(R.id.notes);
        picdist = (TextInputEditText) view.findViewById(R.id.picdist);

        listPoBy = (SearchableSpinner) view.findViewById(R.id.listPoBy);

        setIsPP(isPP);
        setisCopyPP(isCopyPP);
        picdist.setText(distpic);
        postatus.setText("Draft");
        // Log.e("alamat kirim",AlamatKirim+","+distpic);
        shipmentaddress.setText(AlamatKirim);

        PoVia.clear();

        PoVia.add(new mListString(0, "Select Po Via", "-"));
        PoVia.add(new mListString(1, "ByEmail", "Email"));
        PoVia.add(new mListString(2, "ByFax", "Faximile"));
        PoVia.add(new mListString(3, "ByPhone", "PhoneCall"));
        PoVia.add(new mListString(4, "ByVisit", "Visit"));
        PoVia.add(new mListString(5, "SellOut", "SellOut"));
        listPoBy.setAdapter(PoVia, 2, 1);
        listPoBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                povia.setText(String.valueOf(PoVia.get(position).getNilai2()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(isSellOut){
            listPoBy.setSelection(5);
        }

        shipmentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                changeTanggal(shipmentdate.getText().toString(), 0);
            }
        });
        shipmentdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (hasFocus) {
                    changeTanggal(shipmentdate.getText().toString(), 0);
                }
            }
        });
        endperiode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTanggal(endperiode.getText().toString(), 1);
            }
        });
        endperiode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    changeTanggal(endperiode.getText().toString(), 1);
                }
            }
        });

        if (((PoNewActivity) getActivity()).getCallPlanId() != null && !((PoNewActivity) getActivity()).getCallPlanId().equalsIgnoreCase("0")) {

            int i = 0;
            for (mListString t : PoVia) {
                if (t.getNilai2().equalsIgnoreCase("Visit")) {
                    listPoBy.setSelection(i);
                    break;
                }
                i++;
            }
        }
        if (PO != null) {
            setViewItem(PO);
        }


        return view;
    }

    public void setisCopyPP(boolean isCopyPP) {
        this.isCopyPP = isCopyPP;

    }

    public void setIsPP(boolean isPP) {
        this.isPP = isPP;

        if (isPP) {
            typePlan = 1;
            if (endperiodegroup != null)
                endperiodegroup.setVisibility(View.VISIBLE);
            if (mechanismegroup != null)
                mechanismegroup.setVisibility(View.VISIBLE);

        } else {
            typePlan = 0;
            if (endperiodegroup != null)
                endperiodegroup.setVisibility(View.GONE);
            if (mechanismegroup != null)
                mechanismegroup.setVisibility(View.GONE);
        }
        // Log.e("is pp c",isPP+","+typePlan);
    }

    private void setViewItem(mPO po) {
        //Log.e("isi po by",po.getPoById()+","+po.getPoViaId());
        int i = 0;
        for (mListString t : PoVia) {
            if (t.getNilai2().equalsIgnoreCase(po.getPoViaId())) {
                listPoBy.setSelection(i);
                break;
            }
            i++;
        }
        povia.setText(po.getPoById());
        if (!isCopyPP) {
            postatus.setText(po.getPoStatusName());
            notes.setText(po.getNotes());
        }
        shipmentdate.setText(po.getShipDate());
        endperiode.setText(po.getEndPeriodeDate());
        picdist.setText(po.getPicDist());
        shipmentaddress.setText(po.getShipAddress());

        mechanisme.setText(po.getMechanisme());
        if (po.isPP()) {
            typePlan = 1;
        } else {
            typePlan = 0;
        }

        plan = new mPOCustInfo();
        plan.setPovia(povia.getEditableText().toString());
        plan.setPostatus(postatus.getEditableText().toString());
        plan.setShipmentdate(shipmentdate.getEditableText().toString());
        if (TextUtils.isEmpty(endperiode.getEditableText().toString())) {
            plan.setEndperiode(shipmentdate.getEditableText().toString());
        } else {
            plan.setEndperiode(endperiode.getEditableText().toString());
        }
        plan.setPicdist(picdist.getEditableText().toString());
        //plan.setShipmentdate(shipmentdate.getEditableText().toString());
        plan.setShipmentaddress(shipmentaddress.getEditableText().toString());
        plan.setNotes(notes.getEditableText().toString());
        plan.setMechanisme(mechanisme.getEditableText().toString());
        plan.setTypepo(typePlan);
    }

    public void setItem(mPO po) {
        PO = po;
    }

    public mPOCustInfo getItem() {
        View focusView = null;
        boolean cancel = false;
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        povia.setError(null);
        shipmentdate.setError(null);
        endperiode.setError(null);
        endperiode.setError(null);
        shipmentaddress.setError(null);

        //listnama.setError(null);
        //listnamaerror.setVisibility(View.INVISIBLE);
        if (TextUtils.isEmpty(povia.getEditableText()) || povia.getEditableText().toString().equalsIgnoreCase("-")) {
            Toast.makeText(getContext(), "sample Via at Customer Info Is Required", Toast.LENGTH_SHORT).show();
            povia.setError(getString(R.string.error_field_required));
            focusView = povia;
            cancel = true;
        } else if (TextUtils.isEmpty(shipmentdate.getEditableText())) {
            Toast.makeText(getContext(), "Shipment Date at Customer Info Is Required", Toast.LENGTH_SHORT).show();
            shipmentdate.setError(getString(R.string.error_field_required));
            focusView = shipmentdate;
            cancel = true;
        } else {
            if (endperiodegroup.getVisibility() == View.VISIBLE) {
                if (TextUtils.isEmpty(endperiode.getEditableText().toString())) {
                    Toast.makeText(getContext(), "End Periode at Customer Info Is Required", Toast.LENGTH_SHORT).show();
                    endperiode.setError(getString(R.string.error_field_required));
                    focusView = endperiode;
                    cancel = true;
                }
            } else if (TextUtils.isEmpty(shipmentaddress.getEditableText())) {
                Toast.makeText(getContext(), "Shipment Address at Customer Info Is Required", Toast.LENGTH_SHORT).show();
                shipmentaddress.setError(getString(R.string.error_field_required));
                focusView = shipmentaddress;
                cancel = true;
            }
        }
        if (cancel) {
            focusView.requestFocus();
            plan = new mPOCustInfo();
            plan.setError(true);
        } else {
            plan = new mPOCustInfo();
            plan.setPovia(povia.getEditableText().toString());
            plan.setPostatus(postatus.getEditableText().toString());
            plan.setShipmentdate(shipmentdate.getEditableText().toString());
            if (TextUtils.isEmpty(endperiode.getEditableText().toString())) {
                plan.setEndperiode(shipmentdate.getEditableText().toString());
            } else {
                plan.setEndperiode(endperiode.getEditableText().toString());
            }
            plan.setMechanisme(mechanisme.getEditableText().toString());
            plan.setPicdist(picdist.getEditableText().toString());
            //plan.setShipmentdate(shipmentdate.getEditableText().toString());
            plan.setShipmentaddress(shipmentaddress.getEditableText().toString());
            plan.setNotes(notes.getEditableText().toString());
            plan.setTypepo(typePlan);
        }
        return plan;
    }


    //change tanggal
    com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog dpd;
    boolean tglkosong;
    Calendar tgldpt = Calendar.getInstance();

    void changeTanggal(String tanggal, int lokasi) {
        String dtStart = tanggal;
        this.lokasi = lokasi;
        //Log.e("isi start", dtStart);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date date = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(date);
                if (this.lokasi == 0) {
                    shipmentdate.setText(format.format(tgldpt.getTime()));
                } else {
                    endperiode.setText(format.format(tgldpt.getTime()));
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
            }
        } else {
            tglkosong = true;
        }
        Calendar nows = Calendar.getInstance();
        Calendar now = tgldpt;
        if (tglkosong) {
            now = nows;
        }

        // Calendar.getInstance();
        dpd = com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                nows.get(Calendar.HOUR_OF_DAY),
                nows.get(Calendar.MINUTE),
                false
        );
        dpd.dateOnly(true);
        //dpd.setMaxDate(nows);
        if(!isSellOut) {
            Calendar mindate = Calendar.getInstance();
            mindate.add(Calendar.DATE, 1);
            dpd.setMinDate(mindate);
        }
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        setTanggal(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
    }

    void setTanggal(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        // String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year + " " + hourString + ":" + minuteString + ":" + secondString;
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // cal.setTime();
            // Log.e("isi tanggal","xx"+(-monthOfYear)+","+monthOfYear+","+(+monthOfYear));
            cal.set(year, monthOfYear - 1, dayOfMonth, hourOfDay, minute, second);
        } catch (Exception ex) {

        }
        tgldpt = cal;
        if (lokasi == 0) {
            shipmentdate.setText(format.format(tgldpt.getTime()));
        } else {
            endperiode.setText(format.format(tgldpt.getTime()));
        }
    }


    public void setDetailInfo(String pic, String alamatkirim) {
        //Log.e("lagi",pic);
        distpic = pic;
        AlamatKirim = alamatkirim;
        if (picdist != null)
            picdist.setText(pic);
        if (shipmentaddress != null)
            shipmentaddress.setText(alamatkirim);
    }

    public void setIsSellOut(boolean isSellOut) {
        this.isSellOut=isSellOut;
        if(listPoBy!=null)
            listPoBy.setSelection(5);

    }
}
