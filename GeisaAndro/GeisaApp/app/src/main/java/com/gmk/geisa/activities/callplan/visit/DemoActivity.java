package com.gmk.geisa.activities.callplan.visit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mDemo;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTrackingPicture;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DemoActivity extends AppCompatActivity {
    public static String sessionUser = "sessionUser";
    public static String callPlanId = "callPlanId";
    public static String customerId = "customerId";
    public static String sessionCust = "sessionCust";
    private mSession session;
    private mCustomer customer;
    private String idCallPlan,idCustomer;
    private int SalesId;
    private ProgressDialog pDialog;
    CallPlanController callPlanController;
    ArrayList<mListString> listStringsType = new ArrayList<>();
    //private String inBedMenuTitle = "Set to 'In bed'";
    Button CstAddCustomerMap;
    // SearchableSpinner listType;
    TextInputEditText demoId, demoVisitId, demoStatus, demoDate, demoPeserta, demotitle, demodeskripsi, demoresponse;
    Button buttonimage;
    ImageView imagefl1, imagefl2, imagefl3, imagefl4, imagefl5, imagefl6;
    private String idDemo;
    boolean imgEdit = false, imgChange = false;
    String imgSrc = "";
    private Uri file;
    private int recCode = 100;
    private ArrayList<mTrackingPicture> mGambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        buttonimage = (Button) findViewById(R.id.buttonimage);
        imagefl1 = (ImageView) findViewById(R.id.imagefl1);
        imagefl2 = (ImageView) findViewById(R.id.imagefl2);
        imagefl3 = (ImageView) findViewById(R.id.imagefl3);
        imagefl4 = (ImageView) findViewById(R.id.imagefl4);
        imagefl5 = (ImageView) findViewById(R.id.imagefl5);
        imagefl6 = (ImageView) findViewById(R.id.imagefl6);
        demoId = (TextInputEditText) findViewById(R.id.demoId);
        demoVisitId = (TextInputEditText) findViewById(R.id.demoVisitId);
        demoStatus = (TextInputEditText) findViewById(R.id.demoStatus);
        // listType = (SearchableSpinner) findViewById(R.id.listType);
        demoDate = (TextInputEditText) findViewById(R.id.demoDate);
        demoPeserta = (TextInputEditText) findViewById(R.id.demoPeserta);
        demotitle = (TextInputEditText) findViewById(R.id.demotitle);
        demodeskripsi = (TextInputEditText) findViewById(R.id.demodeskripsi);
        demoresponse = (TextInputEditText) findViewById(R.id.demoresponse);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //add controller
        callPlanController = CallPlanController.getInstance(this);
        setTitle("Add New Demo");
        mGambar = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
        SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            idDemo = String.valueOf(SalesId).concat(String.valueOf(smp2.format(calss.getTime())));
            demoId.setText(idDemo);
        }
        if (getIntent().getSerializableExtra(callPlanId) != null) {
            idCallPlan = (String) getIntent().getSerializableExtra(callPlanId);
            demoVisitId.setText(idCallPlan);
        }
        if (getIntent().getSerializableExtra(customerId) != null) {
            idCustomer = (String) getIntent().getSerializableExtra(customerId);
        }
        demoDate.setText(smp3.format(calss.getTime()));
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
        //demoId.setText(null);
        //demoVisitId.setText(null);
        //demoStatus.setText(null);
        //demoDate.setText(null);
        mGambar = new ArrayList<>();
        demoPeserta.setText(null);
        demotitle.setText(null);
        demodeskripsi.setText(null);
        demoresponse.setText(null);

    }

    private void savedToDatabase() {
        boolean cancel = false;
        View focusView = null;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        demoId.setError(null);
        demoVisitId.setError(null);
        demoDate.setError(null);
        demoPeserta.setError(null);
        demotitle.setError(null);
        demodeskripsi.setError(null);

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(demoId.getEditableText())) {
            demoId.setError(getString(R.string.error_field_required));
            focusView = demoId;
            cancel = true;
        } else if (TextUtils.isEmpty(demoVisitId.getEditableText())) {
            demoVisitId.setError(getString(R.string.error_field_required));
            focusView = demoVisitId;
            cancel = true;
        } else if (TextUtils.isEmpty(demoDate.getEditableText())) {
            demoDate.setError(getString(R.string.error_field_required));
            focusView = demoDate;
            cancel = true;
        } else if (TextUtils.isEmpty(demoPeserta.getEditableText())) {
            demoPeserta.setError(getString(R.string.error_field_required));
            focusView = demoPeserta;
            cancel = true;
        } else if (TextUtils.isEmpty(demotitle.getEditableText())) {
            demotitle.setError(getString(R.string.error_field_required));
            focusView = demotitle;
            cancel = true;
        } else if (TextUtils.isEmpty(demodeskripsi.getEditableText())) {
            demodeskripsi.setError(getString(R.string.error_field_required));
            focusView = demodeskripsi;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Calendar calss = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
            mDemo callPlanDemo = new mDemo(demoId.getEditableText().toString(), demoVisitId.getEditableText().toString(),idCustomer,
                    demotitle.getEditableText().toString(), demodeskripsi.getEditableText().toString(), Integer.valueOf(demoPeserta.getEditableText().toString()),
                    3,"Finish", demoDate.getEditableText().toString(), "-", smp2.format(calss.getTime()), String.valueOf(SalesId), "1900-01-01", "-", 1);
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Send Demo To Server ...\n Please Wait...");
            pDialog.show();
            callPlanController.updateCallPlanDemoToServer(String.valueOf(SalesId), callPlanDemo,mGambar);
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Save To Database Success" + callPlanDemo.getDemoId(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            savedToDatabase();
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            cancelAdd();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //proses picture

    public void show_dialogConfirm(final int index) {
        imgEdit = true;
        Context context = DemoActivity.this;

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
        file = Uri.fromFile(getOutputMediaFile(SalesId + "_" + idDemo));
        //Log.e("isi fileasli",file.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, recCode);
    }

    private static File getOutputMediaFile(String uniqname) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CameraDemo", "failed to create directory");
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
        if (requestCode >= 100) {
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
                    mGambar.add(new mTrackingPicture(TrackingPictureId, idDemo, file.getPath(),
                            String.valueOf(levelBattrey), "Demo", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
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
                    tempmgambar.add(new mTrackingPicture(TrackingPictureId, idDemo, path,
                            gbr.getStatusBattery(), "Demo", gbr.getCreatedDate(), String.valueOf(SalesId), 1));
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
                    tempmgambar.add(new mTrackingPicture(TrackingPictureId, idDemo, file.getPath(),
                            String.valueOf(levelBattrey), "Demo", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
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
            tempmgambar.add(new mTrackingPicture(TrackingPictureId, idDemo, file.getPath(),
                    String.valueOf(levelBattrey), "Demo", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
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
