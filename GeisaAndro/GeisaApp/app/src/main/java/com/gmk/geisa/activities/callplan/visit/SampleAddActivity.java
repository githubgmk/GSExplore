package com.gmk.geisa.activities.callplan.visit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTrackingPicture;
import com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SampleAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static String sessionUser = "sessionUser";
    public static String callPlanId = "callPlanId";
    public static String customerId = "customerId";
    public static String sessionSample = "sessionSample";
    private mSession session;
    private mSample sample;
    private String idCallPlan, idCustomer;
    private int SalesId;
    private ProgressDialog pDialog;
    CallPlanController callPlanController;
    ArrayList<mListString> listStringsType = new ArrayList<>();
    //SearchableSpinner listItem;
    LinearLayout realisasisgroup, responsegroup;
    TextInputEditText sampleId, sampleVisitId, sampleStatus, sampleRequestDate, samplefor, sampleReceivedDate, samplenote,
            samplecustname, samplecustjab, samplecusthp, responsenote;
    Button buttonimage, btnListItemRequest, btnListItemRealisasi;
    ImageView imagefl1, imagefl2, imagefl3, imagefl4, imagefl5, imagefl6;
    private String idSample;
    boolean imgEdit = false, imgChange = false;
    String imgSrc = "";
    private Uri file;
    private int recCode = 100;
    private ArrayList<mTrackingPicture> mGambar;
    int kodeTgl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        buttonimage = (Button) findViewById(R.id.buttonimage);
        imagefl1 = (ImageView) findViewById(R.id.imagefl1);
        imagefl2 = (ImageView) findViewById(R.id.imagefl2);
        imagefl3 = (ImageView) findViewById(R.id.imagefl3);
        imagefl4 = (ImageView) findViewById(R.id.imagefl4);
        imagefl5 = (ImageView) findViewById(R.id.imagefl5);
        imagefl6 = (ImageView) findViewById(R.id.imagefl6);

        realisasisgroup = (LinearLayout) findViewById(R.id.realisasisgroup);
        responsegroup = (LinearLayout) findViewById(R.id.responsegroup);
        sampleId = (TextInputEditText) findViewById(R.id.sampleId);
        sampleVisitId = (TextInputEditText) findViewById(R.id.sampleVisitId);
        sampleStatus = (TextInputEditText) findViewById(R.id.sampleStatus);
        sampleRequestDate = (TextInputEditText) findViewById(R.id.sampleRequestDate);
        samplefor = (TextInputEditText) findViewById(R.id.samplefor);
        sampleReceivedDate = (TextInputEditText) findViewById(R.id.sampleReceivedDate);
        //listItem = (SearchableSpinner) findViewById(R.id.listItem);
        samplenote = (TextInputEditText) findViewById(R.id.samplenote);
        samplecustname = (TextInputEditText) findViewById(R.id.samplecustname);
        samplecustjab = (TextInputEditText) findViewById(R.id.samplecustjab);
        responsenote = (TextInputEditText) findViewById(R.id.responsenote);
        samplecusthp = (TextInputEditText) findViewById(R.id.samplecusthp);
        btnListItemRequest = (Button) findViewById(R.id.btnListItemRequest);
        btnListItemRealisasi = (Button) findViewById(R.id.btnListItemRealisasi);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //add controller
        callPlanController = CallPlanController.getInstance(this);
        setTitle("Add New Sample");
        mGambar = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            idSample = String.valueOf(SalesId).concat(String.valueOf(smp2.format(calss.getTime())));
            sampleId.setText(idSample);
        }
        if (getIntent().getSerializableExtra(callPlanId) != null) {
            idCallPlan = (String) getIntent().getSerializableExtra(callPlanId);
            sampleVisitId.setText(idCallPlan);
        }
        if (getIntent().getSerializableExtra(customerId) != null) {
            idCustomer = (String) getIntent().getSerializableExtra(customerId);
        }
        if (getIntent().getSerializableExtra(sessionSample) != null) {
            sample = (mSample) getIntent().getSerializableExtra(sessionSample);
            idSample = sample.getSampleId();

            sampleId.setText(idSample);
            if (sample.getSampleStatusId() < 2) {
                setRealisasi();
            } else {
                //Log.e("masuk keini","masuk kesini");
                setResponse();
            }
        } else {
            setRequest();
        }

        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEdit = false;
                takePicture(v);
            }
        });


        imagefl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(0);
            }
        });
        imagefl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(1);
            }
        });
        imagefl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(2);
            }
        });
        imagefl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(3);
            }
        });
        imagefl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(4);
            }
        });
        imagefl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(5);
            }
        });


        //tgl
        /*sampleRequestDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    kodeTgl = 0;
                    changeTanggal(sampleRequestDate.getText().toString());
                }
            }
        });*/
        /*sampleReceivedDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    kodeTgl = 1;
                    changeTanggal(sampleRequestDate.getText().toString());
                }
            }
        });*/

        btnListItemRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleAddActivity.this, SampleItemActivity.class);
                intent.putExtra(SampleItemActivity.sessionType, 0);
                // intent.putExtra(SampleItemActivity.sessionSampleId,sampleId.getEditableText().toString());
                intent.putExtra(SampleItemActivity.sessionUser, session);
                //intent.putExtra(SampleItemActivity.sessionRequestDate,sampleRequestDate.getEditableText().toString());
                //intent.putExtra(SampleItemActivity.sessionPurpose,samplefor.getEditableText().toString());
                if (sample == null) {
                    sample = new mSample(sampleId.getEditableText().toString(), sampleVisitId.getEditableText().toString(), Integer.parseInt(idCustomer),
                            samplefor.getEditableText().toString(), sampleRequestDate.getEditableText().toString(), 0, sampleStatus.getEditableText().toString(),
                            sampleRequestDate.getEditableText().toString(), String.valueOf(SalesId));
                }
                intent.putExtra(SampleItemActivity.sessionSample, sample);
                startActivityForResult(intent, 999);
            }
        });

        btnListItemRealisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleAddActivity.this, SampleItemActivity.class);
                intent.putExtra(SampleItemActivity.sessionType, 1);
                intent.putExtra(SampleItemActivity.sessionUser, session);
                //intent.putExtra(SampleItemActivity.sessionRequestDate,sampleRequestDate.getEditableText().toString());
                //intent.putExtra(SampleItemActivity.sessionPurpose,samplefor.getEditableText().toString());
                if (sample == null) {
                    sample = new mSample(sampleId.getEditableText().toString(), sampleVisitId.getEditableText().toString(), Integer.parseInt(idCustomer),
                            samplefor.getEditableText().toString(), sampleRequestDate.getEditableText().toString(), 0, sampleStatus.getEditableText().toString(),
                            sampleRequestDate.getEditableText().toString(), String.valueOf(SalesId));
                }
                intent.putExtra(SampleItemActivity.sessionSample, sample);
                startActivityForResult(intent, 888);
            }
        });

    }

    private void setRequest() {
        setTitle("Add Request Sample");
        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
        sampleRequestDate.setText(smp3.format(calss.getTime()));
        sampleStatus.setText("Draft");
        realisasisgroup.setVisibility(View.GONE);
        responsegroup.setVisibility(View.GONE);
    }

    private void setRealisasi() {
        setTitle("Add Realisasi Sample");
        samplefor.setEnabled(false);
        btnListItemRequest.setEnabled(false);
        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
        sampleReceivedDate.setText(smp3.format(calss.getTime()));
        sampleId.setText(sample.getSampleId());
        sampleVisitId.setText(sample.getCallPlanId());
        sampleStatus.setText("Request");
        sampleRequestDate.setText(sample.getSampleDate());
        samplefor.setText(sample.getSampleFor());
        realisasisgroup.setVisibility(View.VISIBLE);
        responsegroup.setVisibility(View.GONE);
    }

    private void setResponse() {
        setTitle("Add Response Sample");
        sampleReceivedDate.setText(sample.getSampleReceivedDate());
        sampleId.setText(sample.getSampleId());
        sampleVisitId.setText(sample.getCallPlanId());
        sampleStatus.setText("Realisasi");
        sampleRequestDate.setText(sample.getSampleDate());
        samplefor.setText(sample.getSampleFor());
        samplefor.setEnabled(false);
        realisasisgroup.setVisibility(View.GONE);
        responsegroup.setVisibility(View.VISIBLE);
    }

    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);

        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Save");
        item.setIcon(android.R.drawable.ic_menu_save);
        MenuItem item1 = menu.findItem(R.id.menuRefresh);
        item1.setTitle("Cancel");
        item1.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        return true;
    }


    private void cancelAdd() {
        setRequest();
        btnListItemRequest.setEnabled(true);
        samplefor.setEnabled(true);
        sampleId.setError(null);
        sampleVisitId.setError(null);
        sampleRequestDate.setError(null);
        samplefor.setError(null);
        sampleReceivedDate.setError(null);
        btnListItemRequest.setError(null);
        samplenote.setError(null);
        samplecustname.setError(null);
        samplecustjab.setError(null);
        samplecusthp.setError(null);
        btnListItemRequest.setError(null);
        btnListItemRealisasi.setError(null);
        buttonimage.setError(null);
        btnListItemRequest.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        btnListItemRealisasi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        //sampleId.setText(null);
        //sampleVisitId.setText(null);
        //sampleStatus.setText(null);
        //sampleRequestDate.setText(null);
        mGambar = new ArrayList<>();
        samplefor.setText(null);
        sampleReceivedDate.setText(null);
        samplenote.setText(null);
        samplecustname.setText(null);
        samplecustjab.setText(null);
        samplecusthp.setText(null);

    }

    private void checkdata() {
        boolean cancel = false;
        View focusView = null;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sampleId.setError(null);
        sampleVisitId.setError(null);
        sampleRequestDate.setError(null);
        samplefor.setError(null);
        sampleReceivedDate.setError(null);
        btnListItemRequest.setError(null);
       /* String vsampleId,vsampleVisitId,vsampleRequestDate,vsampleStatus,vsamplefor,
               vsamplenote,vsamplecustname,vsamplecustjab,vsamplecusthp,vsamplereceivedate;
        vsampleId=sampleId.getEditableText().toString();
        vsampleVisitId=sampleVisitId.getEditableText().toString();
        vsampleRequestDate=sampleRequestDate.getEditableText().toString();
        vsampleStatus=sampleStatus.getEditableText().toString();
        vsamplefor=samplefor.getEditableText().toString();*/

        if (sample != null) {
            samplenote.setError(null);
            samplecustname.setError(null);
            samplecustjab.setError(null);
            samplecusthp.setError(null);
            btnListItemRequest.setError(null);
            btnListItemRealisasi.setError(null);
            buttonimage.setError(null);
            if (sample.getSampleStatusId() < 1) {
                //request sample
                if (TextUtils.isEmpty(sampleId.getEditableText())) {
                    sampleId.setError(getString(R.string.error_field_required));
                    focusView = sampleId;
                    cancel = true;
                } else if (TextUtils.isEmpty(sampleVisitId.getEditableText())) {
                    sampleVisitId.setError(getString(R.string.error_field_required));
                    focusView = sampleVisitId;
                    cancel = true;
                } else if (TextUtils.isEmpty(sampleRequestDate.getEditableText())) {
                    sampleRequestDate.setError(getString(R.string.error_field_required));
                    focusView = sampleRequestDate;
                    cancel = true;
                } else if (TextUtils.isEmpty(samplefor.getEditableText())) {
                    samplefor.setError(getString(R.string.error_field_required));
                    focusView = samplefor;
                    cancel = true;
                } else if (sample.getProductOfRequest() == null || sample.getProductOfRequest().size() <= 0) {
                    btnListItemRequest.setError(getString(R.string.error_field_required));
                    Toast.makeText(this, "Item Product Wajib Diisi", Toast.LENGTH_SHORT).show();
                    focusView = btnListItemRequest;
                    cancel = true;
                } else
                    cancel = false;
            } else if (sample.getSampleStatusId() < 2) {
                //realisasi sample
                if (TextUtils.isEmpty(sampleReceivedDate.getEditableText())) {
                    sampleReceivedDate.setError(getString(R.string.error_field_required));
                    focusView = sampleReceivedDate;
                    cancel = true;
                } else if (TextUtils.isEmpty(samplenote.getEditableText())) {
                    samplenote.setError(getString(R.string.error_field_required));
                    focusView = samplenote;
                    cancel = true;
                } else if (TextUtils.isEmpty(samplecustname.getEditableText())) {
                    samplecustname.setError(getString(R.string.error_field_required));
                    focusView = samplecustname;
                    cancel = true;
                } else if (TextUtils.isEmpty(samplecustjab.getEditableText())) {
                    samplecustjab.setError(getString(R.string.error_field_required));
                    focusView = samplecustjab;
                    cancel = true;
                } else if (TextUtils.isEmpty(samplecusthp.getEditableText())) {
                    samplecusthp.setError(getString(R.string.error_field_required));
                    focusView = samplecusthp;
                    cancel = true;
                } else if (sample.getProductOfRealisasi() == null || sample.getProductOfRealisasi().size() <= 0) {
                    btnListItemRealisasi.setError(getString(R.string.error_field_required));
                    Toast.makeText(this, "Item Product Wajib Diisi", Toast.LENGTH_SHORT).show();
                    focusView = btnListItemRealisasi;
                    cancel = true;
                } else if (mGambar == null || mGambar.size() <= 0) {
                    buttonimage.setError(getString(R.string.error_field_required));
                    Toast.makeText(this, "Bukti Foto Wajib Diisi", Toast.LENGTH_SHORT).show();
                    focusView = buttonimage;
                    cancel = true;
                } else
                    cancel = false;
            } else if (sample.getSampleStatusId() < 3) {
                //response sample
                if (TextUtils.isEmpty(responsenote.getEditableText())) {
                    responsenote.setError(getString(R.string.error_field_required));
                    focusView = responsenote;
                    cancel = true;
                } else
                    cancel = false;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                //ArrayList<mSample.mProductSample> productSample = new ArrayList<>();//add for sample item detail;
                //ditambah dari realisasi
                Log.e("mauk sini","s"+sample.getSampleStatusId());
                int statusId;
                String sampleStatus;
                Calendar calss = Calendar.getInstance();
                SimpleDateFormat smp2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                if (sample.getSampleStatusId() < 1) {
                    statusId = 1;
                    sampleStatus = "Requested";
                } else if (sample.getSampleStatusId() < 2) {
                    statusId = 2;
                    sampleStatus = "Realisasi";
                    sample.setSampleReceivedDate(smp2.format(calss.getTime()));
                    sample.setCustPic(samplecustname.getEditableText().toString());
                    sample.setCustPicJabatan(samplecustjab.getEditableText().toString());
                    sample.setCustPicHp(samplecusthp.getEditableText().toString());
                } else {
                    statusId = 3;
                    sampleStatus = "Response";
                    sample.setSampleResponseNote(responsenote.getEditableText().toString());
                    sample.setSampleResponseDate(smp2.format(calss.getTime()));
                }
                sample.setSampleFor(samplefor.getEditableText().toString());
                sample.setNote(samplenote.getEditableText().toString());
                sample.setSampleStatusId(statusId);
                sample.setSampleStatus(sampleStatus);
                sample.setModifiedDate(smp2.format(calss.getTime()));
                sample.setModifiedBy(String.valueOf(SalesId));
                sample.setStatusSend(1);
                //sample.setProductOfRequest(productSample);
                //Log.e("totaol realisasi","ttl"+sample.getProductOfRealisasi().size());
                for (mSample.mProductSample cu:sample.getProductOfRequest()) {
                    Log.e("totaol request",sample.getSampleId()+":"+ cu.getSampleProdukId()+","+ cu.getSampleId()+","+cu.getProductName()+","+cu.getTypeRequest());
                }
                for (mSample.mProductSample cu:sample.getProductOfRealisasi()) {
                    Log.e("totaol realisasi",sample.getSampleId()+":"+ cu.getSampleProdukId()+","+ cu.getSampleId()+","+cu.getProductName()+","+cu.getTypeRequest());
                }
                saveDataToDatabase(sample);
            }
        } else {
            if (TextUtils.isEmpty(sampleId.getEditableText())) {
                sampleId.setError(getString(R.string.error_field_required));
                focusView = sampleId;
                cancel = true;
            } else if (TextUtils.isEmpty(sampleVisitId.getEditableText())) {
                sampleVisitId.setError(getString(R.string.error_field_required));
                focusView = sampleVisitId;
                cancel = true;
            } else if (TextUtils.isEmpty(sampleRequestDate.getEditableText())) {
                sampleRequestDate.setError(getString(R.string.error_field_required));
                focusView = sampleRequestDate;
                cancel = true;
            } else if (TextUtils.isEmpty(samplefor.getEditableText())) {
                samplefor.setError(getString(R.string.error_field_required));
                focusView = samplefor;
                cancel = true;
            }
            //add new sample
            if (cancel) {
                focusView.requestFocus();
            } else {
                //persetujuanSample();
                Calendar calss = Calendar.getInstance();
                SimpleDateFormat smp2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                //SimpleDateFormat smp3 = new SimpleDateFormat("yyy-MM-dd");
                // Log.e("cust id","cust"+idCustomer);
                int custid = Integer.parseInt(idCustomer);
                sample = new mSample(sampleId.getEditableText().toString(), sampleVisitId.getEditableText().toString(), custid,
                        samplefor.getEditableText().toString(), sampleRequestDate.getEditableText().toString(), 0, sampleStatus.getEditableText().toString(),
                        smp2.format(calss.getTime()), String.valueOf(SalesId));
                //saveDataToDatabase(callPlanSample);
                Toast.makeText(this, "Item Product Wajib Diisi", Toast.LENGTH_SHORT).show();
                btnListItemRequest.setError(getString(R.string.error_field_required));
                focusView = btnListItemRequest;
                focusView.requestFocus();

            }
        }


    }

    private void saveDataToDatabase(final mSample callPlanSample) {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Send Sample To Server ...\n Please Wait...");
        pDialog.show();
        callPlanController.updateCallPlanSampleToServer(String.valueOf(SalesId), callPlanSample, mGambar);
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Save To Database Success " + callPlanSample.getSampleId(), Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            checkdata();
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            cancelAdd();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void persetujuanSample() {
        AlertDialog alertDialog = new AlertDialog.Builder(SampleAddActivity.this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Yakin Ingin Mengajukan Sample?\nApakah semua data dicek dan valid!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    //tanggal
    //change tanggal
    DatePickerDialog dpd;
    boolean tglkosong;
    Calendar tgldpt = Calendar.getInstance();

    void changeTanggal(String tanggal) {
        String dtStart = tanggal;
        //Log.e("isi start", dtStart);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date date = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(date);
                if (kodeTgl == 0) {
                    sampleRequestDate.setText(format.format(tgldpt.getTime()));
                } else if (kodeTgl == 1) {
                    sampleReceivedDate.setText(format.format(tgldpt.getTime()));
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
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                nows.get(Calendar.HOUR_OF_DAY),
                nows.get(Calendar.MINUTE),
                false
        );
        dpd.dateOnly(true);
        Calendar maxdate = Calendar.getInstance();
        maxdate.add(Calendar.DATE, 30);
        dpd.setMaxDate(maxdate);
        Calendar mindate = Calendar.getInstance();
        mindate.add(Calendar.DATE, -30);
        dpd.setMinDate(mindate);
        dpd.show(getFragmentManager(), "Datepickerdialog");
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
        if (kodeTgl == 0) {
            sampleRequestDate.setText(format.format(tgldpt.getTime()));
        } else if (kodeTgl == 1) {
            sampleReceivedDate.setText(format.format(tgldpt.getTime()));
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        setTanggal(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
    }

    //proses picture

    public void show_dialogConfirm(final int index) {
        imgEdit = true;
        Context context = SampleAddActivity.this;

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_image, null);
        final ImageView preview = (ImageView) dialogView.findViewById(R.id.imagePreview);
        //final ImageView cancel = (ImageView) dialogView.findViewById(R.id.imageCancel);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();
        if (mGambar != null && mGambar.size() > index) {
            String pathGambar = mGambar.get(index).getPicture();
            File imgFile = new File(pathGambar);
            Bitmap bt = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            preview.setImageBitmap(bt);
            imgSrc = pathGambar;
        } else {
            imgSrc = "DefaultGambar";
        }
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEdit = false;
                imgChange = false;
                dialog.dismiss();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mGambar.get(index);
                imgEdit = true;
                imgChange = false;
              /*  if(mGambar!=null && mGambar.size()>index) {
                    int sblm=mGambar.size();
                    mGambar.remove(index);
                    int ssdh=mGambar.size();
                    Log.e("delete gambar","didelete "+index+" dari "+ sblm+" sesudah"+ssdh);
                }*/
                dialog.dismiss();
                buttonimage.setEnabled(true);
                buttonimage.setBackgroundResource(R.drawable.ic_camera_ambil);
                reindexPicture();
                Toast.makeText(getApplicationContext(), "Delete Picture", Toast.LENGTH_SHORT).show();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (mGambar != null) {
                imgEdit = true;
                imgChange = true;
                takePicture(v);
                //}
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Change Picture", Toast.LENGTH_SHORT).show();
            }
        });

        //
        dialog.show();
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile(SalesId + "_" + idSample));
        //Log.e("isi fileasli",file.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, recCode);
    }

    private static File getOutputMediaFile(String uniqname) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CameraSample", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "DM_" + uniqname + "_" + timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("hasilnya", requestCode + "," + resultCode + "," + RESULT_OK);
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessionSample)) {
                    sample = (mSample) data.getSerializableExtra(sessionSample);
                    if (sample.getProductOfRequest() != null) {
                        btnListItemRequest.setError(null);
                        int imgResource = R.drawable.btn_check_on;
                        btnListItemRequest.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                    }
                }
                // Log.e("isi sample",sample.getProductOfRequest().size()+"");
            }
        } else if (requestCode == 888) {
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessionSample)) {
                    sample = (mSample) data.getSerializableExtra(sessionSample);
                    if (sample.getProductOfRealisasi() != null) {
                        btnListItemRealisasi.setError(null);
                        int imgResource = R.drawable.btn_check_on;
                        btnListItemRealisasi.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
                    }
                }
                // Log.e("isi sample",sample.getProductOfRequest().size()+"");
            }
        } else if (requestCode >= 100) {
            if (resultCode == RESULT_OK) {
                if (imgEdit) {
                    /*File imgFile = new File(file.getPath());
                    File new1 = saveBitmapToFile(imgFile);
                    Bitmap bt = BitmapFactory.decodeFile(new1.getAbsolutePath());*/
                    reindexPicture();
                } else {
                    // Log.e("isi file", file.getPath());
                    File imgFile = new File(file.getPath());
                    File new1 = saveBitmapToFile(imgFile);
                    Bitmap bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                    switch (requestCode) {
                        case 100:
                            imagefl1.setImageBitmap(bt);
                            break;
                        case 101:
                            imagefl2.setImageBitmap(bt);
                            break;
                        case 102:
                            imagefl3.setImageBitmap(bt);
                            break;
                        case 103:
                            imagefl4.setImageBitmap(bt);
                            break;
                        case 104:
                            imagefl5.setImageBitmap(bt);
                            break;
                        case 105:
                            imagefl6.setImageBitmap(bt);
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;
                        default:
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;

                    }
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                    SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Intent batteryIntent = getApplicationContext().registerReceiver(
                            null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    int levelBattrey = batteryIntent.getIntExtra("level", -1);
                    String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(recCode));
                    mGambar.add(new mTrackingPicture(TrackingPictureId, idSample, file.getPath(),
                            String.valueOf(levelBattrey), "Sample", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
                    recCode += 1;
                }

            }
        }
    }

    private void reindexPicture() {
        //int requestCode=100;
        int update = 100;
        boolean deleted;
        ArrayList<mTrackingPicture> tempmgambar = new ArrayList<mTrackingPicture>();
        imagefl1.setImageResource(R.drawable.ic_camera);
        imagefl2.setImageResource(R.drawable.ic_camera);
        imagefl3.setImageResource(R.drawable.ic_camera);
        imagefl4.setImageResource(R.drawable.ic_camera);
        imagefl5.setImageResource(R.drawable.ic_camera);
        imagefl6.setImageResource(R.drawable.ic_camera);
        if (mGambar != null && mGambar.size() > 0) {
            for (mTrackingPicture gbr : mGambar) {
                File imgFile = null;
                File new1;
                Bitmap bt = null;
                String nama = "";
                String path = "";
                deleted = false;
                Log.e("reindex gambar", imgSrc + " masuk re index" + gbr.getPicture());
                if (gbr.getPicture().equalsIgnoreCase(imgSrc)) {
                    Log.e("reindex gambar", "masuk re update delete" + imgSrc);
                    if (imgChange) {
                        imgFile = new File(file.getPath());
                        new1 = saveBitmapToFile(imgFile);
                        bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                        nama = imgFile.getName();
                        path = file.getPath();
                        Log.e("reindex gambar", "masuk re update" + path);
                    } else {
                        deleted = true;
                        Log.e("reindex gambar", "masuk re delete");
                    }
                } else {
                    Log.e("reindex gambar", "hanya update");
                    imgFile = new File(gbr.getPicture());
                    new1 = saveBitmapToFile(imgFile);
                    bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                    nama = imgFile.getName();
                    path = gbr.getPicture();
                }
                Log.e("reindex gambar", "isi reg" + update);
                if (!deleted) {
                    switch (update) {
                        case 100:
                            imagefl1.setImageBitmap(bt);
                            break;
                        case 101:
                            imagefl2.setImageBitmap(bt);
                            break;
                        case 102:
                            imagefl3.setImageBitmap(bt);
                            break;
                        case 103:
                            imagefl4.setImageBitmap(bt);
                            break;
                        case 104:
                            imagefl5.setImageBitmap(bt);
                            break;
                        case 105:
                            imagefl6.setImageBitmap(bt);
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;
                        default:
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;

                    }

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                    // SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    //Intent batteryIntent = getApplicationContext().registerReceiver(
                    //        null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    //int levelBattrey = batteryIntent.getIntExtra("level", -1);
                    String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(update));
                    tempmgambar.add(new mTrackingPicture(TrackingPictureId, idSample, path,
                            gbr.getStatusBattery(), "Sample", gbr.getCreatedDate(), String.valueOf(SalesId), 1));
                    /*tempmgambar.add(new mTrackingPicture(String.valueOf(update) + "-" + userlogin + "_" + smp2.format(cal.getTime()),
                            path, nama, kodeLapangan, smp.format(cal.getTime()), 1));*/
                    update += 1;
                }

            }
            Log.e("reindex gambar", update + "sampe lah" + imgChange);
            if (imgSrc.equalsIgnoreCase("DefaultGambar") && imgChange) {
                File imgFile = null;
                File new1;
                Bitmap bt = null;
                String nama = "";
                String path = "";
                deleted = false;

                imgFile = new File(file.getPath());
                new1 = saveBitmapToFile(imgFile);
                bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                nama = imgFile.getName();
                path = file.getPath();
                Log.e("reindex gambar", update + "masuk lah update" + path);

                if (!deleted) {
                    switch (update) {
                        case 100:
                            imagefl1.setImageBitmap(bt);
                            break;
                        case 101:
                            imagefl2.setImageBitmap(bt);
                            break;
                        case 102:
                            imagefl3.setImageBitmap(bt);
                            break;
                        case 103:
                            imagefl4.setImageBitmap(bt);
                            break;
                        case 104:
                            imagefl5.setImageBitmap(bt);
                            break;
                        case 105:
                            imagefl6.setImageBitmap(bt);
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;
                        default:
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;

                    }

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                    SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Intent batteryIntent = getApplicationContext().registerReceiver(
                            null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    int levelBattrey = batteryIntent.getIntExtra("level", -1);
                    String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(update));
                    tempmgambar.add(new mTrackingPicture(TrackingPictureId, idSample, file.getPath(),
                            String.valueOf(levelBattrey), "Sample", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
                    /*tempmgambar.add(new mTrackingPicture(String.valueOf(update) + "-" + userlogin + "_" + smp2.format(cal.getTime()),
                            path, nama, kodeLapangan, smp.format(cal.getTime()), 1));*/
                    update += 1;
                }
            }
        } else {
            File imgFile = new File(file.getPath());
            File new1 = saveBitmapToFile(imgFile);
            Bitmap bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
            switch (update) {
                case 100:
                    imagefl1.setImageBitmap(bt);
                    break;
                case 101:
                    imagefl2.setImageBitmap(bt);
                    break;
                case 102:
                    imagefl3.setImageBitmap(bt);
                    break;
                case 103:
                    imagefl4.setImageBitmap(bt);
                    break;
                case 104:
                    imagefl5.setImageBitmap(bt);
                    break;
                case 105:
                    imagefl6.setImageBitmap(bt);
                    buttonimage.setBackgroundResource(R.drawable.ic_camera);
                    buttonimage.setEnabled(false);
                    break;
                default:
                    buttonimage.setEnabled(false);
                    buttonimage.setBackgroundResource(R.drawable.ic_camera);
                    break;

            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
            SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Intent batteryIntent = getApplicationContext().registerReceiver(
                    null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int levelBattrey = batteryIntent.getIntExtra("level", -1);
            String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(update));
            tempmgambar.add(new mTrackingPicture(TrackingPictureId, idSample, file.getPath(),
                    String.valueOf(levelBattrey), "Sample", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
            /*tempmgambar.add(new mTrackingPicture(String.valueOf(update) + "-" + userlogin + "_" + smp2.format(cal.getTime()),
                    file.getPath(), imgFile.getName(), kodeLapangan, smp.format(cal.getTime()), 1));*/
            update += 1;
        }

        recCode = update;
        mGambar = tempmgambar;
        imgEdit = false;
        imgChange = false;
        imgSrc = "";
    }

    public File saveBitmapToFile(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            final int REQUIRED_SIZE = 75;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
            //overwrie file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

}
