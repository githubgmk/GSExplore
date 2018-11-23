package com.gmk.geisa.activities.support;

import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.gmk.geisa.controller.AreaController;
import com.gmk.geisa.controller.BiCsController;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.ChannelController;
import com.gmk.geisa.controller.CustStatusController;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.DistBranchController;
import com.gmk.geisa.controller.DistController;
import com.gmk.geisa.controller.LevelController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.controller.UnitController;
import com.gmk.geisa.controller.ZoneController;
import com.gmk.geisa.model.mArea;
import com.gmk.geisa.model.mBiCsType;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mChannel;
import com.gmk.geisa.model.mCustStatus;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mDistributor;
import com.gmk.geisa.model.mDistributorBranch;
import com.gmk.geisa.model.mLevel;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mRofoTarget;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mUnit;
import com.gmk.geisa.model.mZone;

import java.util.ArrayList;
import java.util.Calendar;

public class SyncActivity extends AppCompatActivity {
    Button SyncUpdate, SyncClose;
    public final static String sessionUser = "sessionLogin";
    private mSession session;
    private String SalesId = "";
    private CustomerController customerController;
    private AreaController areaController;
    private LevelController levelController;
    private ChannelController channelController;
    private ZoneController zoneController;
    private CustStatusController custStatusController;
    private DistController distController;
    private DistBranchController distBranchController;
    private ProductController productController;
    private CallPlanController callPlanController;
    private BiCsController biCsController;
    private RofoController rofoController;
    private UnitController unitController;


    ProgressDialog pdialog;
    private ProgressBar spinner;
    TextView pleasewait, pCustomer, pArea, pLevel, pChannel, pZone, pCustStatus,
            pDistributor, pDistributorBranch, pProduct, pPrice, pCallPlan,pBiType,
            pTarget,pPromo,pUnit;
    CheckBox cCustomer, cArea, cLevel, cChannel, cZone,
            cCustStatus, cDistributor, cDistributorBranch, cCallPlan,cBiType,
            cProduct, cPrice,cTarget,cPromo,cUnit;
    boolean bCustomer = true, bArea = true, bLevel = true, bChannel = true, bZone = true, bCustStatus = true,
            bDistributor = true, bDistributorBranch = true, bProduct = true, bPrice = true, bCallPlan = true,
            bBiType=true,bTarget=true,bPromo=true,bUnit=true;
    ImageView infodownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        setTitle("");
        this.setFinishOnTouchOutside(false);

        SyncUpdate = (Button) findViewById(R.id.SyncUpdate);
        SyncClose = (Button) findViewById(R.id.SyncClose);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        infodownload = (ImageView) findViewById(R.id.infodownload);
        pleasewait = (TextView) findViewById(R.id.pleasewait);

        cCustomer = (CheckBox) findViewById(R.id.cCustomer);
        cArea = (CheckBox) findViewById(R.id.cArea);
        cLevel = (CheckBox) findViewById(R.id.cLevel);
        cChannel = (CheckBox) findViewById(R.id.cChannel);
        cZone = (CheckBox) findViewById(R.id.cZone);
        cCustStatus = (CheckBox) findViewById(R.id.cCustStatus);
        cDistributor = (CheckBox) findViewById(R.id.cDistributor);
        cDistributorBranch = (CheckBox) findViewById(R.id.cDistributorBranch);
        cProduct = (CheckBox) findViewById(R.id.cProduct);
        cPrice = (CheckBox) findViewById(R.id.cPrice);
        cCallPlan = (CheckBox) findViewById(R.id.cCallPlan);
        cBiType=(CheckBox) findViewById(R.id.cBiType);
        cTarget=(CheckBox) findViewById(R.id.cTarget);
        cPromo=(CheckBox) findViewById(R.id.cPromo);
        cUnit=(CheckBox) findViewById(R.id.cUnit);


        pArea = (TextView) findViewById(R.id.pArea);
        pCustomer = (TextView) findViewById(R.id.pCustomer);
        pLevel = (TextView) findViewById(R.id.pLevel);
        pChannel = (TextView) findViewById(R.id.pChannel);
        pZone = (TextView) findViewById(R.id.pZone);
        pCustStatus = (TextView) findViewById(R.id.pCustStatus);
        pDistributor = (TextView) findViewById(R.id.pDistributor);
        pDistributorBranch = (TextView) findViewById(R.id.pDistributorBranch);
        pProduct = (TextView) findViewById(R.id.pProduct);
        pPrice = (TextView) findViewById(R.id.pPrice);
        pCallPlan = (TextView) findViewById(R.id.pCallPlan);
        pBiType=(TextView) findViewById(R.id.pBiType);
        pTarget=(TextView) findViewById(R.id.pTarget);
        pPromo=(TextView) findViewById(R.id.pPromo);
        pUnit=(TextView) findViewById(R.id.pUnit);



        customerController = CustomerController.getInstance(this);
        customerController.setCallBackGetCustomer(callBackCustomer);
        areaController = AreaController.getInstance(this);
        areaController.setCallBackGetData(callBackArea);
        levelController = LevelController.getInstance(this);
        levelController.setCallBackGetData(callBackLevel);
        channelController = ChannelController.getInstance(this);
        channelController.setCallBackGetData(callbackChannel);
        zoneController = ZoneController.getInstance(this);
        zoneController.setCallBackGetData(callBackZone);
        custStatusController = CustStatusController.getInstance(this);
        custStatusController.setCallBackGetData(callBackCustStatus);
        distController = DistController.getInstance(this);
        distController.setCallBackGetData(callBackDistributor);
        distBranchController = DistBranchController.getInstance(this);
        distBranchController.setCallBackGetData(callBackDistBrach);
        productController = ProductController.getInstance(this);
        productController.setCallBackGetData(callBackProduct);
        productController.setCallBackGetDataPrice(callBackProductPrice);
        productController.setCallBackGetDataPromo(callBackPromo);
        callPlanController = CallPlanController.getInstance(this);
        callPlanController.setCallBackGetData(callBackCallPlan);
        biCsController=BiCsController.getInstance(this);
        biCsController.setCallBackGetData(callBackBiType);
        rofoController=RofoController.getInstance(this);
        rofoController.setCallBackGetDataRofo(callBackRofo);
        rofoController.setCallBackGetDataRofoTarget(callBackTarget);
        unitController=UnitController.getInstance(this);
        unitController.setCallBackGetData(callBackUnit);

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = String.valueOf(session.getId());
        }

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


    private void StartDownload() {
        SyncUpdate.setEnabled(false);
        SyncUpdate.setBackgroundColor(Color.TRANSPARENT);
        spinner.setVisibility(View.VISIBLE);
        infodownload.setVisibility(View.GONE);
        pleasewait.setText("Please Wait .....");
        cCustomer.setChecked(false);
        cArea.setChecked(false);
        cLevel.setChecked(false);
        cChannel.setChecked(false);
        cZone.setChecked(false);
        cCustStatus.setChecked(false);
        cDistributor.setChecked(false);
        cDistributorBranch.setChecked(false);
        cProduct.setChecked(false);
        cPrice.setChecked(false);
        cCallPlan.setChecked(false);
        cBiType.setChecked(false);
        cPromo.setChecked(false);


        pCustomer.setText("waiting");
        pArea.setText("waiting");
        pLevel.setText("waiting");
        pChannel.setText("waiting");
        pZone.setText("waiting");
        pCustStatus.setText("waiting");
        pDistributor.setText("waiting");
        pDistributorBranch.setText("waiting");
        pProduct.setText("waiting");
        pPrice.setText("waiting");
        pCallPlan.setText("waiting");
        pBiType.setText("waiting");
        pPromo.setText("waiting");

        updateCustomer();
        updateArea();
        updateLevel();
        updateChannel();
        updateZone();
        updateCustStatus();
        updateDistributor();
        updateDistributorBranch();
        updateProduct();
        updatePrice();
        updateCallPlan();
        updateBiType();
        updateRofo();
        updatePromo();
        updateUnit();
    }

    private void StopDownload() {

        if (bCustomer && bArea && bLevel && bChannel && bZone && bCustStatus &&
                bDistributor && bDistributorBranch && bProduct && bPrice && bCallPlan
                & bBiType && bTarget && bPromo && bUnit) {
            spinner.setVisibility(View.GONE);
            infodownload.setVisibility(View.VISIBLE);
            pleasewait.setText("Finish Downloading");
            SyncUpdate.setBackgroundColor(getResources().getColor(R.color.mdtp_button_color));
            SyncUpdate.setEnabled(true);
        }

    }

    private void updateCustomer() {
        bCustomer = false;
        pCustomer.setText(getString(R.string.Starting));
        customerController.GetAllCustomerFromServer(SalesId);
    }

    CustomerController.CallBackGetCustomer callBackCustomer = new CustomerController.CallBackGetCustomer() {
        @Override
        public void resultReady(ArrayList<mCustomer> customers, boolean hasil) {
            if (null != customers && customers.size() > 0) {
                cCustomer.setChecked(true);
                pCustomer.setText(getString(R.string.Done));
            } else {
                cCustomer.setChecked(true);
                pCustomer.setText(getString(R.string.ProblemDownloading));
            }
            bCustomer = true;
            StopDownload();

        }


    };

    private void updateArea() {
        bArea = false;
        pArea.setText(getString(R.string.Starting));
        areaController.getAllAreaFromServer(SalesId);
    }

    AreaController.CallBackGetData callBackArea = new AreaController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mArea> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cArea.setChecked(true);
                pArea.setText(getString(R.string.Done));
            } else {
                cArea.setChecked(true);
                pArea.setText(getString(R.string.ProblemDownloading));
            }
            bArea = true;
            StopDownload();
        }
    };

    private void updateLevel() {
        bLevel = false;
        pLevel.setText(getString(R.string.Starting));
        levelController.getAllLevelFromServer(SalesId);
    }

    LevelController.CallBackGetData callBackLevel = new LevelController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mLevel> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cLevel.setChecked(true);
                pLevel.setText(getString(R.string.Done));
            } else {
                cLevel.setChecked(true);
                pLevel.setText(getString(R.string.ProblemDownloading));
            }
            bLevel = true;
            StopDownload();
        }
    };

    private void updateChannel() {
        bChannel = false;
        pChannel.setText(getString(R.string.Starting));
        channelController.getAllChannelFromServer(SalesId);
    }

    ChannelController.CallBackGetData callbackChannel = new ChannelController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mChannel> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cChannel.setChecked(true);
                pChannel.setText(getString(R.string.Done));
            } else {
                cChannel.setChecked(true);
                pChannel.setText(getString(R.string.ProblemDownloading));
            }
            bChannel = true;
            StopDownload();
        }
    };

    private void updateZone() {
        bZone = false;
        pZone.setText(getString(R.string.Starting));
        zoneController.getAllZoneFromServer(SalesId);
    }

    ZoneController.CallBackGetData callBackZone = new ZoneController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mZone> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cZone.setChecked(true);
                pZone.setText(getString(R.string.Done));
            } else {
                cZone.setChecked(true);
                pZone.setText(getString(R.string.ProblemDownloading));
            }
            bZone = true;
            StopDownload();
        }
    };

    private void updateCustStatus() {
        bCustStatus = false;
        pCustStatus.setText(getString(R.string.Starting));
        custStatusController.getAllCustStatusFromServer(SalesId);
    }

    CustStatusController.CallBackGetData callBackCustStatus = new CustStatusController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mCustStatus> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cCustStatus.setChecked(true);
                pCustStatus.setText(getString(R.string.Done));
            } else {
                cCustStatus.setChecked(true);
                pCustStatus.setText(getString(R.string.ProblemDownloading));
            }
            bCustStatus = true;
            StopDownload();
        }
    };

    private void updateDistributor() {
        bDistributor = false;
        pDistributor.setText(getString(R.string.Starting));
        distController.getAllDistFromServer(SalesId);
    }

    DistController.CallBackGetData callBackDistributor = new DistController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mDistributor> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cDistributor.setChecked(true);
                pDistributor.setText(getString(R.string.Done));
            } else {
                cDistributor.setChecked(true);
                pDistributor.setText(getString(R.string.ProblemDownloading));
            }
            bDistributor = true;
            StopDownload();
        }
    };

    private void updateDistributorBranch() {
        bDistributorBranch = false;
        pDistributorBranch.setText(getString(R.string.Starting));
        distBranchController.getAllDistBranchFromServer(SalesId);
    }

    DistBranchController.CallBackGetData callBackDistBrach = new DistBranchController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mDistributorBranch> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0)|| hasil) {
                cDistributorBranch.setChecked(true);
                pDistributorBranch.setText(getString(R.string.Done));
            } else {
                cDistributorBranch.setChecked(true);
                pDistributorBranch.setText(getString(R.string.ProblemDownloading));
            }
            bDistributorBranch = true;
            StopDownload();
        }
    };


    private void updateProduct() {
        bProduct = false;
        pProduct.setText(getString(R.string.Starting));
        productController.getAllProductFromServer(SalesId);
    }
    ProductController.CallBackGetData callBackProduct = new ProductController.CallBackGetData(){
        @Override
        public void resultReady(ArrayList<mProduct> product, boolean hasil) {
            if(hasil) {
                cProduct.setChecked(true);
                pProduct.setText(getString(R.string.Done));
            }else{
                cProduct.setChecked(true);
                pProduct.setText(getString(R.string.ProblemDownloading));
            }
            bProduct = true;
            StopDownload();
        }
    };

    private void updatePrice() {
        bPrice = false;
        pPrice.setText(getString(R.string.Starting));
        productController.getAllProductPriceDiskonFromServer(SalesId);
    }
    ProductController.CallBackGetDataPrice callBackProductPrice = new ProductController.CallBackGetDataPrice(){
        @Override
        public void resultReady(ArrayList<mProductPriceDiskon> product, boolean hasil) {
            if(hasil) {
                cPrice.setChecked(true);
                pPrice.setText(getString(R.string.Done));
            }else{
                cPrice.setChecked(true);
                pPrice.setText(getString(R.string.ProblemDownloading));
            }
            bPrice = true;
            StopDownload();
        }
    };

    CallPlanController.CallBackGetData callBackCallPlan = new CallPlanController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil) {
            if(hasil) {
                cCallPlan.setChecked(true);
                pCallPlan.setText(getString(R.string.Done));
            }else{
                cCallPlan.setChecked(true);
                pCallPlan.setText(getString(R.string.ProblemDownloading));
            }
            bCallPlan = true;
            StopDownload();
        }
    };

    private void updateCallPlan() {
        bCallPlan = false;
        pCallPlan.setText(getString(R.string.Starting));
        callPlanController.checkUpdateAllCallPlanFromServer(String.valueOf(session.getId())); //download all callplan
    }

    BiCsController.CallBackGetData callBackBiType = new BiCsController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mBiCsType> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0 )|| hasil) {
                cBiType.setChecked(true);
                pBiType.setText(getString(R.string.Done));
            } else {
                cBiType.setChecked(true);
                pBiType.setText(getString(R.string.ProblemDownloading));
            }
            bBiType = true;
            StopDownload();
        }
    } ;

    private void updateBiType() {
        bBiType = false;
        pBiType.setText(getString(R.string.Starting));
        biCsController.getAllBiCsFromServer(String.valueOf(session.getId())); //download all callplan
    }

    RofoController.CallBackGetDataRofo callBackRofo = new RofoController.CallBackGetDataRofo() {
        @Override
        public void resultReady(ArrayList<mRofo> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0) || hasil) {
                cTarget.setChecked(true);
                pTarget.setText(getString(R.string.Done));
            } else {
                Log.e("isi target","er");
                cTarget.setChecked(true);
                pTarget.setText(getString(R.string.ProblemDownloading));
            }
            bTarget = true;
            StopDownload();
        }
    };

    RofoController.CallBackGetDataRofoTarget callBackTarget =new RofoController.CallBackGetDataRofoTarget() {
        @Override
        public void resultReady(ArrayList<mRofoTarget> customers, boolean hasil) {
            Calendar calendar=Calendar.getInstance();
            rofoController.getAllRofoFromServer(SalesId,String.valueOf(calendar.get(calendar.YEAR)),String.valueOf(calendar.get(calendar.MONTH)));
        }
    };


    private void updateRofo(){
        bTarget=false;
        pTarget.setText(getString(R.string.Starting));
        Calendar calendar=Calendar.getInstance();
        rofoController.getAllTargetFromServer(SalesId,String.valueOf(calendar.get(calendar.YEAR)));
    }


    ProductController.CallBackGetDataPromo callBackPromo = new ProductController.CallBackGetDataPromo() {
        @Override
        public void resultReady(ArrayList<mPromo> customers, boolean hasil) {
            if ((null != customers && customers.size() > 0 )|| hasil) {
                cPromo.setChecked(true);
                pPromo.setText(getString(R.string.Done));
            } else {
                cPromo.setChecked(true);
                pPromo.setText(getString(R.string.ProblemDownloading));
            }
            bPromo = true;
            StopDownload();
        }
    } ;

    private void updatePromo() {
        bPromo = false;
        pPromo.setText(getString(R.string.Starting));
        productController.getAllPromoFromServer(String.valueOf(session.getId())); //download all callplan
    }

    UnitController.CallBackGetData callBackUnit =new UnitController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mUnit> customers, boolean hasil) {
            if(hasil){
                cUnit.setChecked(true);
                pUnit.setText(getString(R.string.Done));
            }else{
                cUnit.setChecked(true);
                pUnit.setText(getString(R.string.ProblemDownloading));
            }
            bUnit = true;
            StopDownload();
        }
    } ;

    private void updateUnit() {
        bUnit = false;
        pUnit.setText(getString(R.string.Starting));
        unitController.getAllUnitFromServer(String.valueOf(session.getId())); //download all callplan
    }
}
