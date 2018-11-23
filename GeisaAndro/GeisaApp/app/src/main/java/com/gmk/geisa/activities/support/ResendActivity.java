package com.gmk.geisa.activities.support;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.PoController;
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.controller.TrackingController;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCallPlanNote;
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mDemo;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTracking;
import com.gmk.geisa.model.mTrackingPicture;

import java.util.ArrayList;

public class ResendActivity extends AppCompatActivity {
    Button SyncUpdate, SyncClose;
    public final static String sessionUser = "sessionLogin";
    private mSession session;
    private String SalesId = "";
    private CustomerController customerController;
    private CallPlanController callPlanController;
    private PoController poController;
    private RofoController rofoController;
    private TrackingController trackingController;

    private ArrayList<mCallPlan> newcallplans = new ArrayList<>();
    private  ArrayList<mCallPlan> realisasiPlan=new ArrayList<>();
    private ArrayList<mCustomer> customers = new ArrayList<>();
    private ArrayList<mPO> pos = new ArrayList<>();
    private ArrayList<mRofo> rofos = new ArrayList<>();
    private ArrayList<mComplain> complains = new ArrayList<>();
    private ArrayList<mDemo> demos = new ArrayList<>();
    private ArrayList<mSample> samples = new ArrayList<>();
    private ArrayList<mCallPlanNote> bins = new ArrayList<>();
    private ArrayList<mTrackingPicture> trackingPictures = new ArrayList<>();
    private ArrayList<mTracking> trackings = new ArrayList<>();
    int ttlpicture=0;
    boolean hslpicture =true;


    ProgressDialog pdialog;
    private ProgressBar spinner;
    TextView pleasewait, pNOO, pCallPlan, pPO, pRofo, pComplain, pDemo,
            pSample, pBIN, pVisit, pPicture,pCustVisit;
    TextView pNOOQty, pCallPlanQty, pPOQty, pRofoQty, pComplainQty, pDemoQty, pSampleQty, pBINQty, pVisitQty, pPictureQty,pCustVisitQty;
    CheckBox cNOO, cCallPlan, cPO, cRofo, cComplain, cDemo, cSample, cBIN, cVisit, cPicture,cCustVisit;
    boolean bNOO = true, bCallPlan = true, bPO = true, bRofo = true, bComplain = true, bDemo = true,
            bSample = true, bBIN = true, bVisit = true, bPicture = true,bCustVisit=true;
    ImageView infodownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend);
        setTitle("");
        this.setFinishOnTouchOutside(false);

        SyncUpdate = (Button) findViewById(R.id.SyncUpdate);
        SyncClose = (Button) findViewById(R.id.SyncClose);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        infodownload = (ImageView) findViewById(R.id.infodownload);
        pleasewait = (TextView) findViewById(R.id.pleasewait);

        cNOO = (CheckBox) findViewById(R.id.cNOO);
        cCallPlan = (CheckBox) findViewById(R.id.cCallPlan);
        cPO = (CheckBox) findViewById(R.id.cPO);
        cRofo = (CheckBox) findViewById(R.id.cRofo);
        cComplain = (CheckBox) findViewById(R.id.cComplain);
        cDemo = (CheckBox) findViewById(R.id.cDemo);
        cSample = (CheckBox) findViewById(R.id.cSample);
        cBIN = (CheckBox) findViewById(R.id.cBIN);
        cVisit = (CheckBox) findViewById(R.id.cVisit);
        cPicture = (CheckBox) findViewById(R.id.cPicture);
        cCustVisit=(CheckBox) findViewById(R.id.cCustVisit);
        /*cTarget=(CheckBox) findViewById(R.id.cTarget);*/


        pNOO = (TextView) findViewById(R.id.pNOO);
        pCallPlan = (TextView) findViewById(R.id.pCallPlan);
        pPO = (TextView) findViewById(R.id.pPO);
        pRofo = (TextView) findViewById(R.id.pRofo);
        pComplain = (TextView) findViewById(R.id.pComplain);
        pDemo = (TextView) findViewById(R.id.pDemo);
        pSample = (TextView) findViewById(R.id.pSample);
        pBIN = (TextView) findViewById(R.id.pBIN);
        pVisit = (TextView) findViewById(R.id.pVisit);
        pPicture = (TextView) findViewById(R.id.pPicture);
        pCustVisit=(TextView) findViewById(R.id.pCustVisit);
        /*pTarget=(TextView) findViewById(R.id.pTarget);*/

        pNOOQty = (TextView) findViewById(R.id.pNOOQty);
        pCallPlanQty = (TextView) findViewById(R.id.pCallPlanQty);
        pPOQty = (TextView) findViewById(R.id.pPOQty);
        pRofoQty = (TextView) findViewById(R.id.pRofoQty);
        pComplainQty = (TextView) findViewById(R.id.pComplainQty);
        pDemoQty = (TextView) findViewById(R.id.pDemoQty);
        pSampleQty = (TextView) findViewById(R.id.pSampleQty);
        pBINQty = (TextView) findViewById(R.id.pBINQty);
        pVisitQty = (TextView) findViewById(R.id.pVisitQty);
        pPictureQty = (TextView) findViewById(R.id.pPictureQty);
        pCustVisitQty=(TextView) findViewById(R.id.pCustVisitQty);



        customerController = CustomerController.getInstance(this);
        customerController.setCallBackAddCustomerSync(callBackCustomer);
        callPlanController = CallPlanController.getInstance(this);
        callPlanController.setCallBackGetDataDraft(callBackCallPlanDraft);
        callPlanController.setCallBackGetData(callBackCallPlanRealisasi);
        poController = PoController.getInstance(this);
        poController.setCallBackGetDataSync(callBackPO);
        rofoController = RofoController.getInstance(this);
        rofoController.setCallBackGetDataSync(callbackRofo);
        callPlanController.setCallBackGetDataComplain(callbackComplain);
        callPlanController.setCallBackGetDataDemo(callbackDemo);
        callPlanController.setCallBackGetDataSample(callbackSample);
        callPlanController.setCallBackGetDataBIN(callbackBin);
        trackingController = TrackingController.getInstance(this);
        trackingController.setCallBackGetDataTracking(callBackGetDataTracking);
        trackingController.setCallBackGetDataTrackingPicture(callBackGetDataTrackingPicture);


        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = String.valueOf(session.getId());
        }
        checkPending();

        SyncUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDownload();
            }
        });
        SyncClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkPending() {
        customers.clear();
        customers = customerController.getAllCustomerPending(SalesId);
        newcallplans.clear();
        newcallplans = callPlanController.getAllCallPlanDraftPending(SalesId);
        realisasiPlan.clear();
        realisasiPlan=callPlanController.getCallPlanByStatus(1);
        pos.clear();
        pos = poController.getAllPOPending();
        rofos.clear();
        rofos = rofoController.getAllRofoPending(SalesId);
        complains.clear();
        complains = callPlanController.getAllCallPlanComplainPending();
        demos.clear();
        demos = callPlanController.getAllCallPlanDemoPending();
        samples.clear();
        samples = callPlanController.getAllCallPlanSamplePending();
        bins.clear();
        bins = callPlanController.getAllCallPlanNotePending();
        trackings.clear();
        trackings = trackingController.getAllTrackingPending();
        trackingPictures.clear();
        trackingPictures = trackingController.getAllTrackingPicturePending();

        pNOOQty.setText(String.valueOf(customers.size()));
        //Log.e("total pending","pending"+newcallplans.size());
        pCallPlanQty.setText(String.valueOf(newcallplans.size()));
        pPOQty.setText(String.valueOf(pos.size()));
        pRofoQty.setText(String.valueOf(rofos.size()));
        pComplainQty.setText(String.valueOf(complains.size()));
        pDemoQty.setText(String.valueOf(demos.size()));
        pSampleQty.setText(String.valueOf(samples.size()));
        pBINQty.setText(String.valueOf(bins.size()));
        pVisitQty.setText(String.valueOf(trackings.size()));
        pPictureQty.setText(String.valueOf(trackingPictures.size()));
        pCustVisitQty.setText(String.valueOf(realisasiPlan.size()));
    }

    private void StartDownload() {
        spinner.setVisibility(View.VISIBLE);
        infodownload.setVisibility(View.GONE);
        pleasewait.setText("Please Wait .....");
        cNOO.setChecked(false);
        cCallPlan.setChecked(false);
        cPO.setChecked(false);
        cRofo.setChecked(false);
        cComplain.setChecked(false);
        cDemo.setChecked(false);
        cSample.setChecked(false);
        cBIN.setChecked(false);
        cCustVisit.setChecked(false);
       /*  cPrice.setChecked(false);
        cCallPlan.setChecked(false);
        cBiType.setChecked(false);*/


        pNOO.setText("waiting");
        pCallPlan.setText("waiting");
        pPO.setText("waiting");
        pRofo.setText("waiting");
        pComplain.setText("waiting");
        pDemo.setText("waiting");
        pSample.setText("waiting");
        pBIN.setText("waiting");
        pCustVisit.setText("waiting");
        /*pPrice.setText("waiting");
        pCallPlan.setText("waiting");
        pBiType.setText("waiting");*/
        updatePicture();
        updateNOO();
        updateCallPlan();
        updateCallPlanRealisasi();
        updatePO();
        updateRofo();
        updateComplain();
        updateDemo();
        updateSample();
        updateBIN();
        updateTracking();

    }

    private void stopUpload() {

        if (bNOO && bCallPlan && bPO && bRofo && bComplain && bDemo &&
                bSample && bBIN && bVisit && bPicture&& bCustVisit) {
            spinner.setVisibility(View.GONE);
            infodownload.setVisibility(View.VISIBLE);
            pleasewait.setText("Finish Uploading");
        }
        checkPending();

    }

    private void updateNOO() {
        bNOO = false;
        pNOO.setText(getString(R.string.Starting));
        customerController.reSendCustomer(customers, SalesId);
    }

    CustomerController.CallBackAddCustomerSync callBackCustomer = new CustomerController.CallBackAddCustomerSync() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cNOO.setChecked(true);
                pNOO.setText(getString(R.string.Done));
            } else {
                cNOO.setChecked(true);
                pNOO.setText(getString(R.string.ProblemUploading));
            }
            bNOO = true;
            stopUpload();

        }


    };

    private void updateCallPlan() {
        bCallPlan = false;
        pCallPlan.setText(getString(R.string.Starting));
        callPlanController.updateCallPlanToServerWithDeleteDraft(SalesId, newcallplans, null);
    }
    CallPlanController.CallBackGetDataDraft callBackCallPlanDraft = new CallPlanController.CallBackGetDataDraft() {
        @Override
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil) {
            Log.e("hasil call","hsl"+ hasil);
            if (hasil) {
                if(callPlanController.deleteCallPlanDraft(newcallplans)) {
                    cCallPlan.setChecked(true);
                    pCallPlan.setText(getString(R.string.Done));
                }else{
                    cCallPlan.setChecked(true);
                    pCallPlan.setText(getString(R.string.ProblemUploading));
                }
            } else {
                cCallPlan.setChecked(true);
                pCallPlan.setText(getString(R.string.ProblemUploading));
            }
            bCallPlan = true;
            stopUpload();
        }
    };




    private void updateCallPlanRealisasi() {
        bCustVisit = false;
        pCustVisit.setText(getString(R.string.Starting));
        callPlanController.updateCallPlanToServerStatusWithCallBack(SalesId, realisasiPlan);
    }

    CallPlanController.CallBackGetData callBackCallPlanRealisasi = new CallPlanController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil) {
            if (hasil) {
                cCustVisit.setChecked(true);
                pCustVisit.setText(getString(R.string.Done));
            } else {
                cCustVisit.setChecked(true);
                pCustVisit.setText(getString(R.string.ProblemUploading));
            }
            bCustVisit = true;
            stopUpload();
        }
    };

    private void updatePO() {
        bPO = false;
        pPO.setText(getString(R.string.Starting));
        poController.updatePoToServer(pos);
    }

    PoController.CallBackGetDataSync callBackPO = new PoController.CallBackGetDataSync() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cPO.setChecked(true);
                pPO.setText(getString(R.string.Done));
            } else {
                cPO.setChecked(true);
                pPO.setText(getString(R.string.ProblemUploading));
            }
            bPO = true;
            stopUpload();
        }
    };

    private void updateRofo() {
        bRofo = false;
        pRofo.setText(getString(R.string.Starting));
        rofoController.reSendRofoToServer(rofos, SalesId);
    }

    RofoController.CallBackGetDataSync callbackRofo = new RofoController.CallBackGetDataSync() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cRofo.setChecked(true);
                pRofo.setText(getString(R.string.Done));
            } else {
                cRofo.setChecked(true);
                pRofo.setText(getString(R.string.ProblemUploading));
            }
            bRofo = true;
            stopUpload();
        }
    };

    private void updateComplain() {
        bComplain = false;
        pComplain.setText(getString(R.string.Starting));
        callPlanController.reSendCallPlanComplainToServer(SalesId, complains);
    }

    CallPlanController.CallBackGetDataComplain callbackComplain = new CallPlanController.CallBackGetDataComplain() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cComplain.setChecked(true);
                pComplain.setText(getString(R.string.Done));
            } else {
                cComplain.setChecked(true);
                pComplain.setText(getString(R.string.ProblemUploading));
            }
            bComplain = true;
            stopUpload();
        }
    };

    private void updateDemo() {
        bDemo = false;
        pDemo.setText(getString(R.string.Starting));
        callPlanController.reSendCallPlanDemoToServer(SalesId, demos);
    }

    CallPlanController.CallBackGetDataDemo callbackDemo = new CallPlanController.CallBackGetDataDemo() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cDemo.setChecked(true);
                pDemo.setText(getString(R.string.Done));
            } else {
                cDemo.setChecked(true);
                pDemo.setText(getString(R.string.ProblemUploading));
            }
            bDemo = true;
            stopUpload();
        }
    };

    private void updateSample() {
        bSample = false;
        pSample.setText(getString(R.string.Starting));
        callPlanController.reSendCallPlanSampleToServer(SalesId, samples);
    }

    CallPlanController.CallBackGetDataSample callbackSample = new CallPlanController.CallBackGetDataSample() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cSample.setChecked(true);
                pSample.setText(getString(R.string.Done));
            } else {
                cSample.setChecked(true);
                pSample.setText(getString(R.string.ProblemDownloading));
            }
            bSample = true;
            stopUpload();
        }
    };

    private void updateBIN() {
        bBIN = false;
        pBIN.setText(getString(R.string.Starting));
        callPlanController.reSendCallPlanNoteToServer(SalesId, bins);
    }

    CallPlanController.CallBackGetDataBIN callbackBin = new CallPlanController.CallBackGetDataBIN() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cBIN.setChecked(true);
                pBIN.setText(getString(R.string.Done));
            } else {
                cBIN.setChecked(true);
                pBIN.setText(getString(R.string.ProblemUploading));
            }
            bBIN = true;
            stopUpload();
        }
    };

    private void updateTracking() {
        bVisit = false;
        pVisit.setText(getString(R.string.Starting));
        trackingController.reSendTrackingServer(trackings);
    }

    TrackingController.CallBackGetDataTracking callBackGetDataTracking = new TrackingController.CallBackGetDataTracking() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                cVisit.setChecked(true);
                pVisit.setText(getString(R.string.Done));
            } else {
                cVisit.setChecked(true);
                pVisit.setText(getString(R.string.ProblemUploading));
            }
            bVisit = true;
            stopUpload();
        }
    };

    private void updatePicture() {
        bPicture = false;
        pPicture.setText(getString(R.string.Starting));
        ttlpicture=trackingPictures.size();
        trackingController.reSendTrackingPictureToServer(trackingPictures);
    }

    TrackingController.CallBackGetDataTrackingPicture callBackGetDataTrackingPicture = new TrackingController.CallBackGetDataTrackingPicture() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                ttlpicture-=1;
            }else {
                hslpicture = hasil;
            }
            if(ttlpicture<=0){
                if(hslpicture) {
                    cPicture.setChecked(true);
                    pPicture.setText(getString(R.string.Done));
                }else{
                    cPicture.setChecked(true);
                    pPicture.setText(getString(R.string.ProblemUploading));
                }
                bPicture = true;
                stopUpload();
            }
        }
    };


}
