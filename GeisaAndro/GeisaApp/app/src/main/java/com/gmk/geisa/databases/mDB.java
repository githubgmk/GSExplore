package com.gmk.geisa.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.model.mArea;
import com.gmk.geisa.model.mBiCsType;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCallPlanNote;
import com.gmk.geisa.model.mChannel;
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mCustStatus;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mDemo;
import com.gmk.geisa.model.mDistributor;
import com.gmk.geisa.model.mDistributorBranch;
import com.gmk.geisa.model.mLevel;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mPoLineOther;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mRofoAktualisasi;
import com.gmk.geisa.model.mRofoTarget;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTodoList;
import com.gmk.geisa.model.mTracking;
import com.gmk.geisa.model.mTrackingPicture;
import com.gmk.geisa.model.mUnit;
import com.gmk.geisa.model.mZone;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by kenjin on 12/11/2015.
 */
public class mDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "geisaforce.db";
    private static final int DB_VERSION = 71;

    private static mDB dbInstance;
    private static SQLiteDatabase db;
    //master
    private static final String TB_Customer = "mCustomer";
    private static final String TB_CustomerAndDistBranch = "mCustAndDistBranch";
    private static final String TB_User = "mUser";//sales&spv
    private static final String TB_Area = "mArea";
    private static final String TB_Zone = "mZone";
    private static final String TB_Channel = "mChannel";
    private static final String TB_Level = "mLevel";
    private static final String TB_CustStatus = "mCustStatus";
    private static final String TB_DistBranch = "mDistBranch";
    private static final String TB_Dist = "mDist";
    private static final String TB_BiCsType = "mBiCsType";
    private static final String TB_Produk = "mProduk";
    private static final String TB_ProdukPriceDisc = "mProdukPrice";
    private static final String TB_Promo = "mPromo";

    //transaksi
    private static final String TB_Pesan = "tPesan";
    private static final String TB_Session = "tSession";
    private static final String TB_Tracking = "tTracking";
    private static final String TB_CallPlan = "tCallplan";
    private static final String TB_CallPlanDraft = "tCallPlanDraft";
    private static final String TB_TrackingPicture = "tTrackingPicture";
    private static final String TB_TransCallPlanNote = "tCallPlanNote";
    private static final String TB_TransCallPlanDemo = "tCallPlanDemo";
    private static final String TB_TransCallPlanComplain = "tCallPlanComplain";
    // private static final String TB_TransCallPlanCompalinDetailItem = "tCallPlanComplainDetailItem";
    private static final String TB_TransCallPlanSample = "tTB_TransCallPlanSample";
    private static final String TB_TransCallPlanSampleProduk = "tTB_TransCallPlanSampleProduk";
    private static final String TB_PO = "tPO";
    private static final String TB_PoLine = "tPOLine";
    private static final String TB_PoLineOther = "tPOLineOther";
    private static final String TB_Rofo = "tRofo";
    private static final String TB_RofoTarget = "tTarget";
    private static final String TB_RofoAktualisasi = "tRofoAktualisasi";
    private static final String TB_Unit = "mUnit";
    private static final String TB_TodoList = "tTodoList";

    private static final String TB_SimpleNote = "mSimpleNote";
    private static final String TB_TransSimpleNote = "tSimpleNote";
    private static final String TB_TransCompetitor = "tCompetitor";
    private static final String TB_Visit = "tVisit";
    private static final String TB_VisitNote = "tVisitNote";
    //private static final String TB_RofoTarget = "tTarget";
    private static final String TB_DetailTarget = "dTarget";




    interface iSimpan {
        String id = "_id";
        String nama = "nama";
        String nilai1 = "nilai1";
        String nilai2 = "nilai2";
        String nilai3 = "nilai3";
        String nilai4 = "nilai4";
        String nilai5 = "nilai5";

    }

    interface iTodoList {
        String RecId = "ttdRecId";
        String Reference = "ttdReference";
        String CustId = "ttdCustId";
        String CustName = "ttdCustName";
        String Category = "ttdCategory";
        String Title = "ttdTitle";
        String DocDate = "ttdDocDate";
        String Detail = "ttdDetail";
        String StatusId = "ttdStatusId";
        String Status = "ttdStatus";
        String StatusDetail = "ttdStatusDetail";
        String CreatedDate = "ttdCreatedDate";
        String StatusRead = "ttdStatusRead";
    }

    private static final String SQL_CREATE_TODO_LIST =
            "CREATE TABLE "
                    + TB_TodoList
                    + "( "
                    + iTodoList.RecId
                    + " integer primary key unique not null, "
                    + iTodoList.Reference
                    + " String unique,"
                    + iTodoList.CustId
                    + " integer,"
                    + iTodoList.CustName
                    + " String,"
                    + iTodoList.Category
                    + " String,"
                    + iTodoList.Title
                    + " String,"
                    + iTodoList.DocDate
                    + " String,"
                    + iTodoList.Detail
                    + " String,"
                    + iTodoList.StatusId
                    + " integer,"
                    + iTodoList.Status
                    + " String,"
                    + iTodoList.StatusDetail
                    + " String,"
                    + iTodoList.CreatedDate
                    + " String,"
                    + iTodoList.StatusRead
                    + " integer"
                    + ")";


    interface iUnit {
        String UnitId = "iuUnitId";
        String UnitName = "iuUnitName";
        String Status = "iuStatus";
    }

    private static final String SQL_CREATE_UNIT =
            "CREATE TABLE "
                    + TB_Unit
                    + "( "
                    + iUnit.UnitId
                    + " text primary key unique not null, "
                    + iUnit.UnitName
                    + " text,"
                    + iUnit.Status
                    + " integer"
                    + ")";

    interface iPromo {
        String PromoId = "tpPromoId";
        String PromoName = "tpPromoName";
        String StartDate = "tpStartDate";
        String EndDate = "tpEndDate";
        String DistId = "tpDistId";
        String DistName = "tpDistName";
        String CustRelation = "tpCustRelation";
        String Cust = "tpCust";
        String CustName = "tpCustName";
        String ProductId = "tpProductId";
        String ProductName = "tpProductName";
        String UnitId = "tpUnitId";
        String MinQty = "tpMinQty";
        String MinValue = "tpMinValue";
        String MultiplyQty = "tpMultiplyQty";
        String ProductIdBonus = "tpProductIdBonus";
        String ProductBonusName = "tpProductBonusName";
        String UnitIdBonus = "tpUnitIdBonus";
        String QtyBonus = "tpQtyBonus";
        String Notes = "tpNotes";
        String CreatedDate = "tpCreatedDate";
        String CreatedBy = "tpCreatedBy";
        String ModifiedDate = "tpModifiedDate";
        String ModifiedBy = "tpModifiedBy";
    }

    private static final String SQL_CREATE_PROMO =
            "CREATE TABLE "
                    + TB_Promo
                    + "( "
                    + iPromo.PromoId
                    + " integer primary key not null, "
                    + iPromo.PromoName
                    + " text, "
                    + iPromo.StartDate
                    + " text, "
                    + iPromo.EndDate
                    + " text, "
                    + iPromo.DistId
                    + " integer, "
                    + iPromo.DistName
                    + " text, "
                    + iPromo.CustRelation
                    + " integer,"
                    + iPromo.Cust
                    + " text, "
                    + iPromo.CustName
                    + " text, "
                    + iPromo.ProductId
                    + " integer, "
                    + iPromo.ProductName
                    + " text, "
                    + iPromo.UnitId
                    + " text,"
                    + iPromo.MinQty
                    + " integer,"
                    + iPromo.MinValue
                    + " integer,"
                    + iPromo.MultiplyQty
                    + " integer, "
                    + iPromo.ProductIdBonus
                    + " integer, "
                    + iPromo.ProductBonusName
                    + " text,"
                    + iPromo.UnitIdBonus
                    + " text,"
                    + iPromo.QtyBonus
                    + " integer,"
                    + iPromo.Notes
                    + " text, "
                    + iPromo.CreatedDate
                    + " text, "
                    + iPromo.CreatedBy
                    + " text,"
                    + iPromo.ModifiedDate
                    + " text,"
                    + iPromo.ModifiedBy
                    + " text"
                    + ")";

    interface iRofoAktualisasi {
        String RofoAktualisasiId = "traRofoAktualisasiId";
        String Year = "traYear";
        String Month = "traMonth";
        String SalesmanId = "traSalesmanId";
        String ValueRofo = "traValueRofo";
        String ValueRofoDraft = "ValueRofoDraft";
        String ValueSales = "traValueSales";
        String ValueTarget = "traValueTarget";
        String UpdatedDate = "traUpdatedDate";

    }

    private static final String SQL_CREATE_ROFO_AKTUALISASI =
            "CREATE TABLE "
                    + TB_RofoAktualisasi
                    + "( "
                    + iRofoAktualisasi.RofoAktualisasiId
                    + " text primary key not null, "
                    + iRofoAktualisasi.Year
                    + " int, "
                    + iRofoAktualisasi.Month
                    + " int, "
                    + iRofoAktualisasi.SalesmanId
                    + " int, "
                    + iRofoAktualisasi.ValueRofo
                    + " double, "
                    + iRofoAktualisasi.ValueRofoDraft
                    + " double, "
                    + iRofoAktualisasi.ValueSales
                    + " double,"
                    + iRofoAktualisasi.ValueTarget
                    + " double, "
                    + iRofoAktualisasi.UpdatedDate
                    + " text "
                    + ")";

    interface iRofoTarget {
        String SalesTargetId = "ttSalesTargetId";
        String Year = "ttYear";
        String Month = "ttMonth";
        String SalesmanId = "ttSalesmanId";
        String Value = "ttValue";
        String CreatedDate = "ttCreatedDate";
        String CreatedBy = "ttCreatedBy";
        String ModifiedDate = "ttModifiedDate";
        String ModifiedBy = "ttModifiedBy";
    }

    private static final String SQL_CREATE_TARGET_ROFO =
            "CREATE TABLE "
                    + TB_RofoTarget
                    + "( "
                    + iRofoTarget.SalesTargetId
                    + " integer primary key autoincrement not null, "
                    + iRofoTarget.Year
                    + " integer, "
                    + iRofoTarget.Month
                    + " integer, "
                    + iRofoTarget.SalesmanId
                    + " integer, "
                    + iRofoTarget.Value
                    + " double, "
                    + iRofoTarget.CreatedDate
                    + " text,"
                    + iRofoTarget.CreatedBy
                    + " text, "
                    + iRofoTarget.ModifiedDate
                    + " text, "
                    + iRofoTarget.ModifiedBy
                    + " text"
                    + ")";

    interface iRofo {
        String RofoId = "trRofoId";
        String Year = "trYear";
        String Month = "trMonth";
        String SalesmanId = "trSalesmanId";
        String CustId = "trCustId";
        String DistBranchId = "trDistBranchId";
        String ProductId = "trProductId";
        String ProductCode = "trProductCode";
        String Qty = "trQty";
        String Value = "trValue";
        String UnitId = "trUnitId";
        String PriceId = "trPriceId";
        String StatusId = "trStatusId";
        String StatusName = "trStatusName";
        String CreatedDate = "trCreatedDate";
        String CreatedBy = "trCreatedBy";
        String ModifiedDate = "trModifiedDate";
        String ModifiedBy = "trModifiedBy";
        String StatusSend = "trStatusSend";
    }


    private static final String SQL_CREATE_ROFO =
            "CREATE TABLE "
                    + TB_Rofo
                    + "( "
                    + iRofo.RofoId
                    + " text primary key not null, "
                    + iRofo.Year
                    + " integer, "
                    + iRofo.Month
                    + " integer, "
                    + iRofo.SalesmanId
                    + " integer, "
                    + iRofo.CustId
                    + " integer, "
                    + iRofo.DistBranchId
                    + " integer, "
                    + iRofo.ProductId
                    + " integer,"
                    + iRofo.Qty
                    + " integer, "
                    + iRofo.ProductCode
                    + " text, "
                    + iRofo.UnitId
                    + " text, "
                    + iRofo.Value
                    + " double, "
                    + iRofo.StatusId
                    + " integer,"
                    + iRofo.StatusName
                    + " text,"
                    + iRofo.CreatedDate
                    + " text,"
                    + iRofo.CreatedBy
                    + " text, "
                    + iRofo.ModifiedDate
                    + " text, "
                    + iRofo.ModifiedBy
                    + " text,"
                    + iRofo.StatusSend
                    + " integer,"
                    + iRofo.PriceId
                    + " integer"
                    + ")";

    interface iPo {
        String PoId = "tpPoId";
        String PoCustNumberRef = "tpPoCustNumberRef";
        String PoDate = "tpPoDate";
        String DistBranchId = "tpDistBranchId";
        String CustId = "tpCustId";
        String SalesmanId = "tpSalesmanId";
        String CallPlanId = "tpCallPlanId";
        String PoById = "tpPoById";
        String PoViaId = "tpPoViaId";
        String ShipDate = "tpShipDate";
        String EndPeriodeDate = "tpEndPeriodeDate";
        String Mechanisme = "tpMechanisme";
        String ShipAddress = "tpShipAddress";
        String Disc1 = "tpDisc1";
        String Disc2 = "tpDisc2";
        String CashDisc = "tpCashDisc";
        String isPP = "tpisPP";
        String PicDist = "tpPicDist";
        String PicCust = "tpPicCust";
        String Notes = "tpNotes";
        String Signature = "tpSignature";
        String PoStatusId = "tpPoStatusId";
        String PoStatusName = "tpPoStatusName";
        String SoNo = "tpSoNo";
        String SoDate = "tpSoDate";
        String DoNo = "tpDoNo";
        String DoDate = "tpDoDate";
        String CreatedDate = "tpCreatedDate";
        String CreatedBy = "tpCreatedBy";
        String ConfirmDate = "tpConfirmDate";
        String ModifiedDate = "tpModifiedDate";
        String ModifiedBy = "tpModifiedBy";
        String StatusSend = "tpStatusSend";
        String KeteranganDetail = "tpKeteranganDetail";
        String isSellOut = "tpisSellOut";
    }

    private static final String SQL_CREATE_PO =
            "CREATE TABLE "
                    + TB_PO
                    + "( "
                    + iPo.PoId
                    + " text primary key not null, "
                    + iPo.PoCustNumberRef
                    + " text, "
                    + iPo.PoDate
                    + " text, "
                    + iPo.CustId
                    + " integer, "
                    + iPo.SalesmanId
                    + " integer, "
                    + iPo.CallPlanId
                    + " text, "
                    + iPo.PoById
                    + " text,"
                    + iPo.PoViaId
                    + " text,"
                    + iPo.ShipDate
                    + " text,"
                    + iPo.EndPeriodeDate
                    + " text,"
                    + iPo.Mechanisme
                    + " text,"
                    + iPo.ShipAddress
                    + " text,"
                    + iPo.Disc1
                    + " double,"
                    + iPo.Disc2
                    + " double, "
                    + iPo.CashDisc
                    + " double, "
                    + iPo.isPP
                    + " integer, "
                    + iPo.PicDist
                    + " text, "
                    + iPo.PicCust
                    + " text, "
                    + iPo.Notes
                    + " text,"
                    + iPo.Signature
                    + " text,"
                    + iPo.PoStatusId
                    + " integer,"
                    + iPo.PoStatusName
                    + " text,"
                    + iPo.SoNo
                    + " text,"
                    + iPo.SoDate
                    + " datetime,"
                    + iPo.DoNo
                    + " text, "
                    + iPo.DoDate
                    + " datetime, "
                    + iPo.CreatedDate
                    + " datetime,"
                    + iPo.CreatedBy
                    + " text,"
                    + iPo.ConfirmDate
                    + " datetime,"
                    + iPo.ModifiedDate
                    + " datetime,"
                    + iPo.ModifiedBy
                    + " text,"
                    + iPo.StatusSend
                    + " integer,"
                    + iPo.DistBranchId
                    + " integer,"
                    + iPo.KeteranganDetail
                    + " text,"
                    +iPo.isSellOut
                    + " integer"
                    + ")";

    interface iPoLine {
        String RecIdTab = "iplRecId";
        String PoId = "iplPoId";
        String ProductId = "iplProductId";
        String ProductName = "iplProductName";
        String ProductCode = "iplProductCode";
        String Qty = "iplQty";
        String UnitPrice = "iplUnitPrice";
        String UnitId = "iplUnitId";
        String PriceId = "iplPriceId";
        String PriceList = "iplPriceList";
        String PromoId = "iplPromoId";
        String RefRecIdTab = "iplRefRecId";
        String DiscId = "iplDiscId";
        String Disc1 = "iplDisc1";
        String Disc2 = "iplDisc2";
        String Disc3 = "iplDisc3";
        String DiscRp = "iplDiscRp";
        String Point = "iplPoint";
        String IncludePPN = "iplIncludePPN";
        String CreatedDate = "iplCreatedDate";
        String ConfirmDate = "iplConfirmDate";
        String StatusSend = "iplStatusSend";
        String Selected = "tpSelected";
    }

    private static final String SQL_CREATE_PO_PRODUCT =
            "CREATE TABLE "
                    + TB_PoLine
                    + "( "
                    + iPoLine.RecIdTab
                    + " text primary key not null, "
                    + iPoLine.PoId
                    + " text, "
                    + iPoLine.ProductId
                    + " integer, "
                    + iPoLine.ProductName
                    + " text,"
                    + iPoLine.ProductCode
                    + " text,"
                    + iPoLine.Qty
                    + " double, "
                    + iPoLine.UnitPrice
                    + " double, "
                    + iPoLine.UnitId
                    + " text, "
                    + iPoLine.PriceId
                    + " integer,"
                    + iPoLine.PriceList
                    + " double,"
                    + iPoLine.DiscId
                    + " integer, "
                    + iPoLine.Disc1
                    + " double, "
                    + iPoLine.Disc2
                    + " double, "
                    + iPoLine.Disc3
                    + " double, "
                    + iPoLine.DiscRp
                    + " double, "
                    + iPoLine.Point
                    + " doube,"
                    + iPoLine.IncludePPN
                    + " integer,"
                    + iPoLine.CreatedDate
                    + " text,"
                    + iPoLine.ConfirmDate
                    + " text,"
                    + iPoLine.StatusSend
                    + " integer,"
                    + iPoLine.PromoId
                    + " integer,"
                    + iPoLine.RefRecIdTab
                    + " text,"
                    + iPoLine.Selected
                    + " integer default 0"
                    + ")";

    interface iPoLineOther {
        String RecIdTab = "iploRecId";
        String PoId = "iploPoId";
        String ProductCode = "iploProductCode";
        String ProductName = "iploProductName";
        String Qty = "iploQty";
        String Unit = "iploUnit";
        String StatusSend = "iploStatusSend";
    }

    private static final String SQL_CREATE_PO_PRODUCT_OTHER =
            "CREATE TABLE "
                    + TB_PoLineOther
                    + "( "
                    + iPoLineOther.RecIdTab
                    + " text primary key not null, "
                    + iPoLineOther.PoId
                    + " text, "
                    + iPoLineOther.ProductCode
                    + " text, "
                    + iPoLineOther.ProductName
                    + " text, "
                    + iPoLineOther.Qty
                    + " double, "
                    + iPoLineOther.Unit
                    + " text,"
                    + iPoLineOther.StatusSend
                    + " integer"
                    + ")";


    interface iProductPriceDisc {
        String RecId = "ippRecId";
        String Relation = "ippRelation";
        String DistId = "ippDistId";
        String PriceType = "ippPriceType";
        String PriceDiscGroupId = "ippPriceDiscGroupId";
        String ProductCode = "ippProductCode";
        String ProductNameDist = "ippProductNameDist";
        String UnitId = "ippUnitId";
        String Price = "ippPrice";
        String Disc1 = "ippDisc1";
        String Disc2 = "ippDisc2";
        String Disc3 = "ippDisc3";
        String StartDate = "ippStartDate";
        String EndDate = "ippEndDate";
    }

    private static final String SQL_CREATE_PRODUCT_PRICE_DISC =
            "CREATE TABLE "
                    + TB_ProdukPriceDisc
                    + "( "
                    + iProductPriceDisc.RecId
                    + " integer primary key not null, "
                    + iProductPriceDisc.Relation
                    + " integer, "
                    + iProductPriceDisc.DistId
                    + " integer, "
                    + iProductPriceDisc.PriceType
                    + " integer, "
                    + iProductPriceDisc.PriceDiscGroupId
                    + " text, "
                    + iProductPriceDisc.ProductCode
                    + " text, "
                    + iProductPriceDisc.ProductNameDist
                    + " text,"
                    + iProductPriceDisc.UnitId
                    + " text,"
                    + iProductPriceDisc.Price
                    + " double,"
                    + iProductPriceDisc.Disc1
                    + " double,"
                    + iProductPriceDisc.Disc2
                    + " double,"
                    + iProductPriceDisc.Disc3
                    + " double,"
                    + iProductPriceDisc.StartDate
                    + " date,"
                    + iProductPriceDisc.EndDate
                    + " date"
                    + ")";


    interface iProduct {
        String ProductId = "mProductId";
        String ProductName = "mProductName";
        String Foto = "mFoto";
        String ProductSimpleDescription = "mProductSimpleDescription";
        String RecIdProductMap = "mRecIdProductMap";
        String ProductCode = "mProductCode";
        String DistId = "mDistId";
        String ProductNameDist = "mProductNameDist";
        String StatusId = "mStatusId";
    }

    private static final String SQL_CREATE_PRODUCT =
            "CREATE TABLE "
                    + TB_Produk
                    + "( "
                    + iProduct.ProductId
                    + " integer not null, "
                    + iProduct.ProductName
                    + " text, "
                    + iProduct.Foto
                    + " text, "
                    + iProduct.ProductSimpleDescription
                    + " text, "
                    + iProduct.RecIdProductMap
                    + " integer, "
                    + iProduct.ProductCode
                    + " text, "
                    + iProduct.DistId
                    + " integer  not null,"
                    + iProduct.ProductNameDist
                    + " text,"
                    + iProduct.StatusId
                    + " integer"
                    + ")";

    interface iTransCallPlanSampleProduk {
        String SampleProdukId = "itcsdSampleProdukId";
        String SampleId = "itcsdSampleId";
        String ProductId = "itcsItemId";
        String ProductName = "itcsProductName";
        String ProductCode = "itcsProductCode";
        String Kemasan = "itcsKemasan";
        String Qty = "itcsQty";
        String Note = "itcsNote";
        String TypeRequest = "itcsTypeRequest";
        String CreatedDate = "itcsCreatedDate";
    }

    private static final String SQL_CREATE_TRANS_CALL_PLAN_SAMPLE_PRODUCT =
            "CREATE TABLE "
                    + TB_TransCallPlanSampleProduk
                    + "( "
                    + iTransCallPlanSampleProduk.SampleProdukId
                    + " text primary key not null, "
                    + iTransCallPlanSampleProduk.SampleId
                    + " text, "
                    + iTransCallPlanSampleProduk.ProductId
                    + " integer, "
                    + iTransCallPlanSampleProduk.ProductName
                    + " text, "
                    + iTransCallPlanSampleProduk.ProductCode
                    + " text, "
                    + iTransCallPlanSampleProduk.Kemasan
                    + " text, "
                    + iTransCallPlanSampleProduk.Qty
                    + " double, "
                    + iTransCallPlanSampleProduk.Note
                    + " text,"
                    + iTransCallPlanSampleProduk.TypeRequest
                    + " text,"
                    + iTransCallPlanSampleProduk.CreatedDate
                    + " text "
                    + ")";

    interface iTransCallPlanSample {
        String SampleId = "tcsSampleId";
        String CallPlanId = "tcsCallPlanId";
        String CustId = "tcsCustomerId";
        String SampleFor = "tcsSampleFor";
        String SampleDate = "tcsSampleDate";
        String SampleStatusId = "tcsSampleStatusId";
        String SampleStatus = "tcsSampleStatus";
        String SampleReceivedDate = "tcsSampleReceivedDate";
        String CustPic = "tcsSampleCustomerName";///status draft 0 ,inreview 1, open 2,close 3
        String CustPicJabatan = "tcsSampleCustomerNameJabatan";
        String CustPicHp = "tcsSampleCustomerNameHP";
        String Note = "tcsNote";
        String SampleResponseDate = "tcsSampleResponseDate";
        String SampleResponseNote = "tcsSampleResponseNote";
        String CreatedDate = "tcsCreatedDate";
        String CreatedBy = "tcsCreatedBy";
        String ModifiedDate = "tcsModifiedDate";
        String ModifiedBy = "tcsModifiedBy";
        String StatusSend = "tcsStatusSend";
    }

    private static final String SQL_CREATE_TRANS_CALL_PLAN_SAMPLE =
            "CREATE TABLE "
                    + TB_TransCallPlanSample
                    + "( "
                    + iTransCallPlanSample.SampleId
                    + " text primary key not null, "
                    + iTransCallPlanSample.CallPlanId
                    + " text, "
                    + iTransCallPlanSample.CustId
                    + " text, "
                    + iTransCallPlanSample.SampleFor
                    + " text, "
                    + iTransCallPlanSample.SampleDate
                    + " text, "
                    + iTransCallPlanSample.SampleStatusId
                    + " integer, "
                    + iTransCallPlanSample.SampleStatus
                    + " integer,"
                    + iTransCallPlanSample.SampleReceivedDate
                    + " integer,"
                    + iTransCallPlanSample.CustPic
                    + " text,"
                    + iTransCallPlanSample.CustPicJabatan
                    + " text,"
                    + iTransCallPlanSample.CustPicHp
                    + " text,"
                    + iTransCallPlanSample.Note
                    + " text,"
                    + iTransCallPlanSample.SampleResponseDate
                    + " text,"
                    + iTransCallPlanSample.SampleResponseNote
                    + " text,"
                    + iTransCallPlanSample.CreatedDate
                    + " text,"
                    + iTransCallPlanSample.CreatedBy
                    + " text,"
                    + iTransCallPlanSample.ModifiedDate
                    + " text,"
                    + iTransCallPlanSample.ModifiedBy
                    + " text,"
                    + iTransCallPlanSample.StatusSend
                    + " integer"
                    + ")";

    interface iTransCallPlanComplainDetail {
        String ProductComplainId = "tccdProductComplainId";
        String ComplainId = "tccdComplainId";
        String ItemId = "tccdItemId";
        String BatchNumber = "tccdBatchNumber";
        String QtyComplain = "tccdQtyComplain";
        String QtyComplainSatuan = "tccdQtyComplainSatuan";
        String QtyPembelian = "tccdQtyPembelian";
        String QtyPembelianSatuan = "tccdQtyPembelianSatuan";
        String CategoryComplain = "tccdCategoryComplain";
        String CategoryComplainId = "tccdCategoryComplainId";
        String StatusSend = "tccdStatusSend";
    }

    interface iTransCallPlanComplain {
        String ComplainId = "tccCompainId";
        String SafetyFood = "tccSafetyFood";
        String QualityFood = "tccQualityFood";
        String QualityApplication = "tccQualityApplication";
        String QuantityAll = "tccQuantityAll";
        String PackagingAll = "tccPackagingAll";
        String CallPlanId = "tccCallPlanId";
        String CustId = "tccCustomerId";
        String ProductId = "tccItemId";
        String ProductName = "tccItemIdDetail";
        String ComplainStatusId = "tccComplainStatusId";///status draft 0 ,inreview 1, open 2,close 3
        String ComplainStatusName = "tccComplainStatusName";
        String SampleSendDate = "tccComplainSampleSendDate";
        String CustPic = "tccComplainCustomerName";
        String CustPicJabatan = "tccComplainCustomerNameJabatan";
        String CustPicHp = "tccComplainCustomerNameHP";
        String ComplainNote = "tccComplainNote";
        String ComplainPriority = "tccComplainPriority";
        String ComplainResponse = "tccComplainResponse";
        String ComplainResponseDate = "tccComplainResponseDate";
        String ComplainResponseBy = "tccComplainResponseBy";
        String CreatedDate = "tccCreatedDate";
        String CreatedBy = "tccCreatedBy";
        String ModifiedDate = "tccModifiedDate";
        String ModifiedBy = "tccModifiedBy";
        String StatusSend = "tccStatusSend";
        // private ArrayList<mProdukComplain> ProducOfComplain;
    }

    private static final String SQL_CREATE_TRANS_CALL_PLAN_COMPLAIN =
            "CREATE TABLE "
                    + TB_TransCallPlanComplain
                    + "( "
                    + iTransCallPlanComplain.ComplainId
                    + " text primary key not null, "
                    + iTransCallPlanComplain.SafetyFood
                    + " integer, "
                    + iTransCallPlanComplain.QualityFood
                    + " integer, "
                    + iTransCallPlanComplain.QualityApplication
                    + " integer, "
                    + iTransCallPlanComplain.QuantityAll
                    + " integer, "
                    + iTransCallPlanComplain.PackagingAll
                    + " integer, "
                    + iTransCallPlanComplain.CallPlanId
                    + " text, "
                    + iTransCallPlanComplain.CustId
                    + " text, "
                    + iTransCallPlanComplain.ProductId
                    + " text, "
                    + iTransCallPlanComplain.ProductName
                    + " text, "
                    + iTransCallPlanComplain.ComplainStatusId
                    + " integer, "
                    + iTransCallPlanComplain.ComplainStatusName
                    + " integer,"
                    + iTransCallPlanComplain.SampleSendDate
                    + " integer,"
                    + iTransCallPlanComplain.CustPic
                    + " text,"
                    + iTransCallPlanComplain.CustPicJabatan
                    + " text,"
                    + iTransCallPlanComplain.CustPicHp
                    + " text,"
                    + iTransCallPlanComplain.ComplainNote
                    + " text,"
                    + iTransCallPlanComplain.ComplainPriority
                    + " text,"
                    + iTransCallPlanComplain.ComplainResponse
                    + " text,"
                    + iTransCallPlanComplain.ComplainResponseDate
                    + " text,"
                    + iTransCallPlanComplain.ComplainResponseBy
                    + " text,"
                    + iTransCallPlanComplain.CreatedDate
                    + " text,"
                    + iTransCallPlanComplain.CreatedBy
                    + " text,"
                    + iTransCallPlanComplain.ModifiedDate
                    + " text,"
                    + iTransCallPlanComplain.ModifiedBy
                    + " text,"
                    + iTransCallPlanComplain.StatusSend
                    + " integer"
                    + ")";

    interface iTransCallPlanDemo {
        String DemoId = "tcdDemoId";
        String CallPlanId = "tcdCallPlanId";
        String CustId = "tcdCustomerId";
        String DemoTitle = "tcdDemoTitle";
        String DemoDescription = "tcdDemoDescription";
        String DemoPeserta = "tcdDemoPeserta";
        String DemoStatusId = "tcdDemoStatusId";//status draft 0 ,inreview 1, open 2,close 3
        String DemoStatusName = "tcdDemoStatusName";
        String DemoDate = "tcdDemoDate";
        String DemoResponse = "tcdDemoResponse";
        String CreatedDate = "tcdCreatedDate";
        String CreatedBy = "tcdCreatedBy";
        String ResponseDate = "tcdResponseDate";
        String ResponseBy = "tcdResponseBy";
        String ModifiedDate = "tcdModifiedDate";
        String ModifiedBy = "tcdModiefiedBy";
        String StatusSend = "tcdStatusSend";
    }

    private static final String SQL_CREATE_TRANS_CALL_PLAN_DEMO =
            "CREATE TABLE "
                    + TB_TransCallPlanDemo
                    + "( "
                    + iTransCallPlanDemo.DemoId
                    + " text primary key not null, "
                    + iTransCallPlanDemo.CallPlanId
                    + " text, "
                    + iTransCallPlanDemo.CustId
                    + " text, "
                    + iTransCallPlanDemo.DemoTitle
                    + " text, "
                    + iTransCallPlanDemo.DemoDescription
                    + " text, "
                    + iTransCallPlanDemo.DemoPeserta
                    + " integer,"
                    + iTransCallPlanDemo.DemoStatusId
                    + " integer,"
                    + iTransCallPlanDemo.DemoStatusName
                    + " text,"
                    + iTransCallPlanDemo.DemoDate
                    + " text,"
                    + iTransCallPlanDemo.DemoResponse
                    + " text,"
                    + iTransCallPlanDemo.CreatedDate
                    + " text,"
                    + iTransCallPlanDemo.CreatedBy
                    + " text,"
                    + iTransCallPlanDemo.ResponseDate
                    + " text,"
                    + iTransCallPlanDemo.ResponseBy
                    + " text,"
                    + iTransCallPlanDemo.ModifiedDate
                    + " text,"
                    + iTransCallPlanDemo.ModifiedBy
                    + " text,"
                    + iTransCallPlanDemo.StatusSend
                    + " integer"
                    + ")";

    interface iTransCallPlanNote {
        String TransCallPlanNoteId = "tcnTransCallPlanNoteId";
        String BiCsTypeId = "tcnBiCsTypeId";
        String BiCsTypeName = "tcnBiCsTypeName";
        String CallPlanId = "tcnCallPlanId";
        String CustId = "tcnCustId";
        String Notes1 = "tcnNotes1";
        String Notes2 = "tcnNotes2";
        String Notes3 = "tcnNotes3";
        String CreatedDate = "tcnCreatedDate";
        String CreatedBy = "tcnCreatedBy";
        String ModifiedDate = "tcnModifiedDate";
        String ModifiedBy = "tcnModifiedBy";
        String StatusSend = "tcnStatusSend";
    }


    private static final String SQL_CREATE_TRANS_CALL_PLAN_NOTE =
            "CREATE TABLE "
                    + TB_TransCallPlanNote
                    + "( "
                    + iTransCallPlanNote.TransCallPlanNoteId
                    + " text primary key not null, "
                    + iTransCallPlanNote.BiCsTypeId
                    + " integer, "
                    + iTransCallPlanNote.BiCsTypeName
                    + " text, "
                    + iTransCallPlanNote.CallPlanId
                    + " text, "
                    + iTransCallPlanNote.CustId
                    + " integer, "
                    + iTransCallPlanNote.Notes1
                    + " text, "
                    + iTransCallPlanNote.Notes2
                    + " text,"
                    + iTransCallPlanNote.Notes3
                    + " text,"
                    + iTransCallPlanNote.CreatedDate
                    + " text,"
                    + iTransCallPlanNote.CreatedBy
                    + " text,"
                    + iTransCallPlanNote.ModifiedDate
                    + " text,"
                    + iTransCallPlanNote.ModifiedBy
                    + " text,"
                    + iTransCallPlanNote.StatusSend
                    + " integer"
                    + ")";

    interface iCallPlan {
        String CallPlanId = "tCallPlanId";
        String CallPlanDate = "tCallPlanDate";
        String CallPlanTypeId = "tCallPlanTypeId";
        String CallPlanTypeName = "tCallPlanTypeName";
        String SalesmanId = "tSalesmanId";
        String CustId = "tCustId";
        String CallPlanStatusId = "tCallPlanStatusId";
        String CallPlanStatusName = "tCallPlanStatusName";
        String CreatedDate = "tCreatedDate";
        String CreatedBy = "tCreatedBy";
        String StatusSend = "tCStatusSend";
        String Notes = "tcNotes";
    }

    private static final String SQL_CREATE_CALL_PLAN =
            "CREATE TABLE "
                    + TB_CallPlan
                    + "( "
                    + iCallPlan.CallPlanId
                    + " text primary key not null, "
                    + iCallPlan.CallPlanDate
                    + " text, "
                    + iCallPlan.CallPlanTypeId
                    + " integer, "
                    + iCallPlan.CallPlanTypeName
                    + " text, "
                    + iCallPlan.SalesmanId
                    + " integer,"
                    + iCallPlan.CustId
                    + " integer,"
                    + iCallPlan.CallPlanStatusId
                    + " text,"
                    + iCallPlan.CallPlanStatusName
                    + " text,"
                    + iCallPlan.CreatedDate
                    + " text,"
                    + iCallPlan.CreatedBy
                    + " text,"
                    + iCallPlan.StatusSend
                    + " integer,"
                    + iCallPlan.Notes
                    + " text"
                    + ")";

    interface iCallPlanDraft {
        String CallPlanId = "dCallPlanId";
        String CallPlanDate = "dCallPlanDate";
        String CallPlanTypeId = "dCallPlanTypeId";
        String SalesmanId = "dSalesmanId";
        String CustId = "dCustId";
        String CallPlanStatusId = "dCallPlanStatusId";
        String CreatedDate = "dCreatedDate";
        String CreatedBy = "dCreatedBy";
        String StatusSend = "dStatusSend";
    }

    private static final String SQL_CREATE_CALL_PLAN_DRAFT =
            "CREATE TABLE "
                    + TB_CallPlanDraft
                    + "( "
                    + iCallPlanDraft.CallPlanId
                    + " text primary key not null, "
                    + iCallPlanDraft.CallPlanDate
                    + " text, "
                    + iCallPlanDraft.CallPlanTypeId
                    + " integer, "
                    + iCallPlanDraft.SalesmanId
                    + " integer,"
                    + iCallPlanDraft.CustId
                    + " integer,"
                    + iCallPlanDraft.CallPlanStatusId
                    + " text,"
                    + iCallPlanDraft.CreatedDate
                    + " text,"
                    + iCallPlanDraft.CreatedBy
                    + " text,"
                    + iCallPlanDraft.StatusSend
                    + " integer"
                    + ")";

    interface iCustomer {
        String id = "cust_id";
        String CustId = "CustId";
        String CustGroupId = "CustGroupId";
        String CustName = "CustName";
        String AliasName = "AliasName";
        String Address = "Address";
        String Lat = "Lat";
        String Lng = "Lng";
        String Radius = "Radius";
        String Pic = "CustPic";
        String PicJabatan = "PicJabatan";
        String Telp = "Telp";
        String Email = "Email";
        String Hp = "Hp";
        String Website = "Website";
        String Npwp = "Npwp";
        String CreditLimit = "CreditLimit";
        String Top = "Top";
        String JoinDate = "JoinDate";
        String EcTolerance = "EcTolerance";
        String SalesmanId = "SalesmanId";
        String CustomerDistBranchId = "CustomerDistBranchId";
        String AreaId = "CustAreaId";
        String StsPkpId = "CustStsPkpId";
        String StsPkpName = "StsPkpName";
        String CustById = "CustCustById";
        String CustByName = "CustByName";
        String FreqTypeId = "CustFreqTypeId";
        String FreqTypeName = "FreqTypeName";
        String ChannelId = "CustChannelId";
        String CustLevelId = "CustLevelId";
        String CustZoneId = "CustZoneId";
        String CustStatusId = "CustStatusId";
        String StatusSend = "CustStatusSend";
        String CustIdAndro = "CustIdAndro";
        String NewOutlet = "newOutlet";
    }

    private static final String SQL_CREATE_CUSTOMER = "CREATE TABLE "
            + TB_Customer
            + "( "
            + iCustomer.id
            + " integer primary key autoincrement not null, "
            + iCustomer.CustId
            + " integer unique, "
            + iCustomer.CustGroupId
            + " text, "
            + iCustomer.CustName
            + " text,"
            + iCustomer.AliasName
            + " text,"
            + iCustomer.Address
            + " text,"
            + iCustomer.Lat
            + " double default 0,"
            + iCustomer.Lng
            + " double default 0,"
            + iCustomer.Radius
            + " double,"
            + iCustomer.Pic
            + " text, "
            + iCustomer.PicJabatan
            + " text, "
            + iCustomer.Telp
            + " text,"
            + iCustomer.Email
            + " text,"
            + iCustomer.Hp
            + " text,"
            + iCustomer.Website
            + " text,"
            + iCustomer.Npwp
            + " text,"
            + iCustomer.CreditLimit
            + " integer,"
            + iCustomer.Top
            + " text, "
            + iCustomer.JoinDate
            + " text, "
            + iCustomer.EcTolerance
            + " integer,"
            + iCustomer.SalesmanId
            + " integer,"
            + iCustomer.CustomerDistBranchId
            + " integer,"
            + iCustomer.AreaId
            + " integer,"
            + iCustomer.StsPkpId
            + " text,"
            + iCustomer.StsPkpName
            + " text,"
            + iCustomer.CustById
            + " text,"
            + iCustomer.CustByName
            + " text,"
            + iCustomer.FreqTypeId
            + " integer,"
            + iCustomer.FreqTypeName
            + " text,"
            + iCustomer.ChannelId
            + " integer,"
            + iCustomer.CustLevelId
            + " integer,"
            + iCustomer.CustZoneId
            + " integer,"
            + iCustomer.CustStatusId
            + " integer,"
            + iCustomer.StatusSend
            + " integer,"
            + iCustomer.CustIdAndro
            + " text,"
            + iCustomer.NewOutlet
            + " integer"
            + ")";


    interface iCustomerAndDistBranch {
        String id = "cdb_id";
        String CustomerDistBranchId = "cdbCustomerDistBranchId";
        String DistBranchId = "cdbDistBranchId";
        String CustId = "cdbCustId";
        String CustCode = "CustCode";
        String PriceGroupId = "cdbPriceGroupId";
        String PriceGroupName = "PriceGroupName";
        String DiscGroupId = "cdbDiscGroupId";
        String DiscGroupName = "DiscGroupName";
        String CustIdAndro = "cdbCustIdAndro";
        String StatusSend = "cdbStatusSend";

    }

    private static final String SQL_CREATE_CUSTOMER_AND_DIST_BRANCH =
            "CREATE TABLE "
                    + TB_CustomerAndDistBranch
                    + "( "
                    + iCustomerAndDistBranch.id
                    + " integer primary key autoincrement not null, "
                    + iCustomerAndDistBranch.CustomerDistBranchId
                    + " integer unique, "
                    + iCustomerAndDistBranch.DistBranchId
                    + " integer, "
                    + iCustomerAndDistBranch.CustId
                    + " integer,"
                    + iCustomerAndDistBranch.CustCode
                    + " text,"
                    + iCustomerAndDistBranch.PriceGroupId
                    + " text,"
                    + iCustomerAndDistBranch.PriceGroupName
                    + " text,"
                    + iCustomerAndDistBranch.DiscGroupId
                    + " text,"
                    + iCustomerAndDistBranch.DiscGroupName
                    + " text,"
                    + iCustomerAndDistBranch.CustIdAndro
                    + " text,"
                    + iCustomerAndDistBranch.StatusSend
                    + " integer"
                    + ")";

    interface iDistBranch {
        String DistBranchId = "DistBranchId";
        String DistBranchName = "DistBranchName";
        String AreaCode = "DistBranchAreaCode";
        String AreaName = "DistBranchAreaName";
        String Address = "DistBranchAddress";
        String Pic = "DistBranchPic";
        String Telp = "DistBranchTelp";
        String Email = "DistBranchEmail";
        String DistId = "DistBranchDistId";
        String AreaId = "DistBranchAreaId";
        String StatusId = "DistBranchStatusId";
    }

    private static final String SQL_CREATE_DIST_BRANCH =
            "CREATE TABLE "
                    + TB_DistBranch
                    + "( "
                    + iDistBranch.DistBranchId
                    + " integer primary key autoincrement not null, "
                    + iDistBranch.DistBranchName
                    + " text, "
                    + iDistBranch.AreaCode
                    + " text,"
                    + iDistBranch.AreaName
                    + " text,"
                    + iDistBranch.Address
                    + " text,"
                    + iDistBranch.Pic
                    + " text,"
                    + iDistBranch.Telp
                    + " text,"
                    + iDistBranch.Email
                    + " text,"
                    + iDistBranch.DistId
                    + " integer,"
                    + iDistBranch.AreaId
                    + " integer,"
                    + iDistBranch.StatusId
                    + " integer"
                    + ")";

    interface iDist {
        String DistId = "DistId";
        String DistName = "DistName";
        String Address = "DistAddress";
        String Pic = "DistPic";
        String Telp = "DistTelp";
        String Email = "DistEmail";
        String StatusId = "DistStatus";
    }

    private static final String SQL_CREATE_DIST =
            "CREATE TABLE "
                    + TB_Dist
                    + "( "
                    + iDist.DistId
                    + " integer primary key autoincrement not null, "
                    + iDist.DistName
                    + " text, "
                    + iDist.Address
                    + " text, "
                    + iDist.Pic
                    + " text,"
                    + iDist.Telp
                    + " text,"
                    + iDist.Email
                    + " text,"
                    + iDist.StatusId
                    + " integer"
                    + ")";


    //hierarcy by governoor
    interface cProvince {
        String id = "_id";
        String provName = "province_name";

    }

    interface cCity {
        String id = "_id";
        String cityName = "city_name";
        String provId = "prov_id";
    }

    //hierarcy by organization
    interface iRegion {
        String id = "_id";
        String globalName = "global_name";
        String globalAddress = "global_address";
        String provId = "prov_id";
        String areaId = "area_id";
    }

    interface iChannel {
        String ChannelId = "ChannelId";
        String ChannelName = "ChannelName";
        String Email = "ChannelEmail";
        String Signature = "ChannelSignature";
        String Pic = "ChannelPic";
        String StatusId = "ChannelStatusId";
    }

    private static final String SQL_CREATE_CHANNEL =
            "CREATE TABLE "
                    + TB_Channel
                    + "( "
                    + iChannel.ChannelId
                    + " integer primary key autoincrement not null, "
                    + iChannel.ChannelName
                    + " text, "
                    + iChannel.Email
                    + " text,"
                    + iChannel.Signature
                    + " text,"
                    + iChannel.Pic
                    + " text,"
                    + iChannel.StatusId
                    + " integer"
                    + ")";

    interface iArea {
        String id = "areaId";
        String AreaName = "AreaName";
        String AreaCode = "AreaCode";
        String RegionId = "AreaRegionId";
    }

    private static final String SQL_CREATE_AREA =
            "CREATE TABLE "
                    + TB_Area
                    + "( "
                    + iArea.id
                    + " integer primary key autoincrement not null, "
                    + iArea.AreaName
                    + " text, "
                    + iArea.AreaCode
                    + " text,"
                    + iArea.RegionId
                    + " integer"
                    + ")";

    interface iZone {
        String id = "Zoneid";
        String ZoneName = "ZoneName";

    }

    private static final String SQL_CREATE_ZONE =
            "CREATE TABLE "
                    + TB_Zone
                    + "( "
                    + iZone.id
                    + " integer primary key autoincrement not null, "
                    + iZone.ZoneName
                    + " text"
                    + ")";


    interface iLevel {
        String id = "Levelid";
        String LevelName = "LevelName";

    }

    private static final String SQL_CREATE_LEVEL =
            "CREATE TABLE "
                    + TB_Level
                    + "( "
                    + iLevel.id
                    + " integer primary key autoincrement not null, "
                    + iLevel.LevelName
                    + " text"
                    + ")";

    interface iCustomerStatus {
        String id = "CStatusId";
        String CustStatusName = "CustStatusName";

    }

    private static final String SQL_CREATE_CUST_STATUS =
            "CREATE TABLE "
                    + TB_CustStatus
                    + "( "
                    + iCustomerStatus.id
                    + " integer primary key autoincrement not null, "
                    + iCustomerStatus.CustStatusName
                    + " text"
                    + ")";

    interface iBiCsType {
        String BiCsTypeId = "mBiCsTypeId";
        String BiCsTypeName = "mBiCsTypeName";
        String BiCsTypeEmail = "mBiCsTypeEmail";
        String BiCsTypeJenis = "mBiCsTypeJenis";
        String BICsTypeStatus = "mBiCsTypeStatus";
    }

    private static final String SQL_CREATE_BI_CS_TYPE =
            "CREATE TABLE "
                    + TB_BiCsType
                    + "("
                    + iBiCsType.BiCsTypeId
                    + " integer primary key autoincrement not null,"
                    + iBiCsType.BiCsTypeName
                    + " text,"
                    + iBiCsType.BiCsTypeEmail
                    + " text,"
                    + iBiCsType.BiCsTypeJenis
                    + " text,"
                    + iBiCsType.BICsTypeStatus
                    + " integer default '0'"
                    + ")";


    interface cMasTarget {
        String id = "_id";
        String targetSalesYear = "target_sales_year";
        String targetSalesMonth = "target_sales_month";
        String targetSalesPrice = "target_sales_price";
        String targetSalesStatus = "target_sales_status";
    }

    interface cDetTarget {
        String id = "_id";
        String targetSalesid = "target_sales_id";
        String productId = "product_id";
        String custId = "customer_id";
        String detTargetSalesQty = "det_target_sales_qty";
        String detTargetSalesStatus = "det_target_sales_status";
        String detTargetSalesPoin = "det_target_sales_point";

    }


    //TB_User
    interface iUser {
        String id = "_id";
        String userName = "user_name";
        String userPass = "user_pass";
        String userStatus = "user_status";
        String userSPV = "spv_sales_id";
        String userLevel = "level";
        String areaId = "area_id";
        String userEmail = "user_email";
        String userMacAddress = "mac_address_device";
        String userAlias = "user_alias";
        String statusChange = "statusChange";
        String picture = "picture";
        String fcmid = "fcmid";
        String imei = "imei";
        String recId = "recId";
    }

    private static final String SQL_CREATE_USER =
            "CREATE TABLE IF NOT EXISTS  "
                    + TB_User
                    + "( "
                    + iUser.id
                    + " integer primary key autoincrement not null, "
                    + iUser.userName
                    + " text, "
                    + iUser.userPass
                    + " text, "
                    + iUser.userAlias
                    + " text, "
                    + iUser.userStatus
                    + " text,"
                    + iUser.userSPV
                    + " text,"
                    + iUser.userLevel
                    + " text,"
                    + iUser.areaId
                    + " text,"
                    + iUser.userEmail
                    + " text,"
                    + iUser.userMacAddress
                    + " text,"
                    + iUser.picture
                    + " BLOB,"
                    + iUser.fcmid
                    + " longtext,"
                    + iUser.imei
                    + " longtext,"
                    + iUser.recId
                    + " text,"
                    + iUser.statusChange
                    + " integer default '0'"
                    + ")";

    //TB_Session
    interface iSession {
        String id = "_id";
        String nama = "nama";
        String status = "status";
        String nilai1 = "nilai1";
        String nilai2 = "nilai2";
        String nilai3 = "nilai3";
        String nilai4 = "nilai4";
        String nilai5 = "nilai5";
        String nilai6 = "nilai6";
        String nilai7 = "nilai7";
        String nilai8 = "nilai8";
        String nilai9 = "nilai9";
    }

    private static final String SQL_CREATE_SESSION =
            "CREATE TABLE IF NOT EXISTS  "
                    + TB_Session
                    + "( "
                    + iSession.id
                    + " integer primary key autoincrement not null, "
                    + iSession.nama
                    + " text, "
                    + iSession.status
                    + " integer default '0', "
                    + iSession.nilai1
                    + " text, "
                    + iSession.nilai2
                    + " text, "
                    + iSession.nilai3
                    + " text,"
                    + iSession.nilai4
                    + " text,"
                    + iSession.nilai5
                    + " text,"
                    + iSession.nilai6
                    + " text,"
                    + iSession.nilai7
                    + " text,"
                    + iSession.nilai8
                    + " text,"
                    + iSession.nilai9
                    + " text"
                    + ")";

    //TB_Tracking
    interface iTracking {
        String id = "_id";
        String trackingId = "trackingId";
        String SalesmanId = "trackSalesmanId";
        String trackingType = "trackingType";
        String trackingDate = "trackingDate";
        String trackingTime = "trackingTime";
        String trackingLat = "trackingLat";
        String trackingLot = "trackingLot";
        String trackingRef = "trackingRef";
        String trackingStatus = "trackingStatus";
        String statusSend = "statusSend";
        String createDate = "createDate";
        String infoDevice = "infoDevice";
    }

    private static final String SQL_CREATE_TRACKING =
            "CREATE TABLE IF NOT EXISTS  "
                    + TB_Tracking
                    + "( "
                    + iTracking.id
                    + " integer primary key autoincrement not null, "
                    + iTracking.trackingId
                    + " text, "
                    + iTracking.SalesmanId
                    + " text, "
                    + iTracking.trackingType
                    + " text,"
                    + iTracking.trackingDate
                    + " date,"
                    + iTracking.trackingTime
                    + " text,"
                    + iTracking.trackingLat
                    + " text,"
                    + iTracking.trackingLot
                    + " text,"
                    + iTracking.trackingRef
                    + " text,"
                    + iTracking.trackingStatus
                    + " text,"
                    + iTracking.statusSend
                    + " integer default 1,"
                    + iTracking.createDate
                    + " datetime,"
                    + iTracking.infoDevice
                    + " text default '-'"
                    + ")";

    //TB_TrackingPicture
    interface iTrackingPicture {
        String TrackingPictureId = "tpTrackingPictureId";
        String PictureRef = "tpPictureRef";
        String Picture = "tpPicture";
        String StatusBattery = "tpStatusBattery";
        String Note = "tpNote";
        String CreatedDate = "tpCreatedDate";
        String CreatedBy = "tpCreatedBy";
        String StatusSend = "tpStatusSend";
    }

    private static final String SQL_CREATE_TRACKING_PICTURE =
            "CREATE TABLE IF NOT EXISTS  "
                    + TB_TrackingPicture
                    + "( "
                    + iTrackingPicture.TrackingPictureId
                    + " text primary key not null, "
                    + iTrackingPicture.PictureRef
                    + " text, "
                    + iTrackingPicture.Picture
                    + " text, "
                    + iTrackingPicture.StatusBattery
                    + " text,"
                    + iTrackingPicture.Note
                    + " text,"
                    + iTrackingPicture.CreatedDate
                    + " datetime,"
                    + iTrackingPicture.CreatedBy
                    + " text,"
                    + iTrackingPicture.StatusSend
                    + " integer default 1"
                    + ")";

    //TB_Pesan
    interface iPesan {
        String id = "_id";
        String idPengirim = "idPengirim";
        String pengirim = "namaPengirim";
        String idPenerima = "idPenerima";
        String penerima = "namaPenerima";
        String judul = "title";
        String fcmid = "fcmid";
        String isipesan = "isiPesan";
        String typePesan = "typePesan";
        String dateSend = "dateSend";
        String dateRead = "dateRead";
        String statusPesan = "statusPesan";
        String refId = "refid";
        String statusSend = "statusSend";
    }

    private static final String SQL_CREATE_PESAN =
            "CREATE TABLE IF NOT EXISTS  "
                    + TB_Pesan
                    + "( "
                    + iPesan.id
                    + " integer primary key not null, "
                    + iPesan.idPengirim
                    + " integer, "
                    + iPesan.pengirim
                    + " text, "
                    + iPesan.idPenerima
                    + " integer,"
                    + iPesan.penerima
                    + " text,"
                    + iPesan.judul
                    + " text,"
                    + iPesan.fcmid
                    + " text,"
                    + iPesan.isipesan
                    + " text,"
                    + iPesan.typePesan
                    + " text,"
                    + iPesan.dateSend
                    + " datetime,"
                    + iPesan.dateRead
                    + " text,"
                    + iPesan.statusPesan
                    + " text,"
                    + iPesan.refId
                    + " text,"
                    + iPesan.statusSend
                    + " integer DEFAULT 0"
                    + ")";

    private mDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static mDB getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new mDB(context);
            db = dbInstance.getWritableDatabase();

        }
        return dbInstance;
    }

    @Override
    public synchronized void close() {
        super.close();
        if (dbInstance != null)
            dbInstance.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //Utils.TRACE("ContactDB", "" + SQL_CREATE_CONTACT);
        db.execSQL(SQL_CREATE_CUSTOMER);
        db.execSQL(SQL_CREATE_CUSTOMER_AND_DIST_BRANCH);
        db.execSQL(SQL_CREATE_DIST_BRANCH);
        db.execSQL(SQL_CREATE_DIST);
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_SESSION);
        db.execSQL(SQL_CREATE_TRACKING);
        db.execSQL(SQL_CREATE_PESAN);
        db.execSQL(SQL_CREATE_ZONE);
        db.execSQL(SQL_CREATE_AREA);
        db.execSQL(SQL_CREATE_CHANNEL);
        db.execSQL(SQL_CREATE_CUST_STATUS);
        db.execSQL(SQL_CREATE_LEVEL);
        db.execSQL(SQL_CREATE_CALL_PLAN);
        db.execSQL(SQL_CREATE_CALL_PLAN_DRAFT);
        db.execSQL(SQL_CREATE_TRACKING_PICTURE);
        db.execSQL(SQL_CREATE_BI_CS_TYPE);
        db.execSQL(SQL_CREATE_TRANS_CALL_PLAN_NOTE);
        db.execSQL(SQL_CREATE_TRANS_CALL_PLAN_DEMO);
        db.execSQL(SQL_CREATE_TRANS_CALL_PLAN_COMPLAIN);
        db.execSQL(SQL_CREATE_TRANS_CALL_PLAN_SAMPLE);
        db.execSQL(SQL_CREATE_TRANS_CALL_PLAN_SAMPLE_PRODUCT);
        db.execSQL(SQL_CREATE_PRODUCT);
        db.execSQL(SQL_CREATE_PRODUCT_PRICE_DISC);
        db.execSQL(SQL_CREATE_PO);
        db.execSQL(SQL_CREATE_PO_PRODUCT);
        db.execSQL(SQL_CREATE_PO_PRODUCT_OTHER);
        db.execSQL(SQL_CREATE_TARGET_ROFO);
        db.execSQL(SQL_CREATE_ROFO);
        db.execSQL(SQL_CREATE_ROFO_AKTUALISASI);
        db.execSQL(SQL_CREATE_PROMO);
        db.execSQL(SQL_CREATE_UNIT);
        db.execSQL(SQL_CREATE_TODO_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_Customer);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CustomerAndDistBranch);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CustStatus);
        db.execSQL("DROP TABLE IF EXISTS " + TB_DistBranch);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Dist);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Zone);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Area);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Channel);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Level);
        //db.execSQL("DROP TABLE IF EXISTS " + TB_User);
        //db.execSQL("DROP TABLE IF EXISTS " + TB_Session);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Tracking);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TrackingPicture);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CallPlan);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CallPlanDraft);
        db.execSQL("DROP TABLE IF EXISTS " + TB_BiCsType);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TransCallPlanNote);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TransCallPlanDemo);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TransCallPlanComplain);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TransCallPlanSample);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TransCallPlanSampleProduk);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Produk);
        db.execSQL("DROP TABLE IF EXISTS " + TB_ProdukPriceDisc);
        db.execSQL("DROP TABLE IF EXISTS " + TB_PO);
        db.execSQL("DROP TABLE IF EXISTS " + TB_PoLine);
        db.execSQL("DROP TABLE IF EXISTS " + TB_PoLineOther);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Rofo);
        db.execSQL("DROP TABLE IF EXISTS " + TB_RofoTarget);
        db.execSQL("DROP TABLE IF EXISTS " + TB_RofoAktualisasi);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Promo);
        db.execSQL("DROP TABLE IF EXISTS " + TB_Unit);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TodoList);
        onCreate(db);
    }


    /*LOGIN */
    public void delLoginExcept(String imei) {
        String selection = iUser.imei + " != ?";
        String[] selectionArgs = {imei};
        db.delete(TB_User, selection, selectionArgs);
    }

    public mSession getLoginUpdate(String userName, int status) {
        mSession login = null;
        String[] projection = {iUser.id, iUser.userName, iUser.userPass, iUser.recId,
                iUser.userStatus, iUser.userSPV, iUser.userLevel, iUser.areaId, iUser.fcmid, iUser.imei,
                iUser.userEmail, iUser.userMacAddress, iUser.userAlias, iUser.picture, iUser.statusChange};//
        String selection = iUser.userName + " = ?  and " + iUser.statusChange + " = ? ";
        String[] selectionArgs = {userName, String.valueOf(status)};
        Cursor cursor = db.query(TB_User, projection, selection, selectionArgs, null, null, null);
        try {
            // Log.e("cek kursor", userName + "," + userPass);
            if (cursor != null && cursor.moveToFirst()) {
                // Log.e("masuk", "ada data " + userName + "," + userPass);
                //      Log.e("masuk", "ada data to first "+userName +","+userPass);
                do {
                    // Log.e("masuk", "ada data" + cursor.getString(1));
                    byte[] picturetostring = cursor.getBlob(cursor
                            .getColumnIndexOrThrow(iUser.picture));
                    String isipicture = null;
                    if (picturetostring != null) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            isipicture = new String(picturetostring, StandardCharsets.UTF_8);
                        }else{
                            isipicture = new String(picturetostring, Charset.forName("UTF-8"));
                        }
                    }
                    login = new mSession(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iUser.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userPass)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userSPV)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            picturetostring,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.imei)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.recId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iUser.statusChange)),
                            "login",
                            1,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            isipicture
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return login;
    }

    public mSession getLogin(int idlogin) {
        mSession login = null;
        String[] projection = {iUser.id, iUser.userName, iUser.userPass, iUser.recId,
                iUser.userStatus, iUser.userSPV, iUser.userLevel, iUser.areaId, iUser.fcmid, iUser.imei,
                iUser.userEmail, iUser.userMacAddress, iUser.userAlias, iUser.picture, iUser.statusChange};//
        String selection = iUser.id + " = ? ";
        String[] selectionArgs = {String.valueOf(idlogin)};
        Cursor cursor = db.query(TB_User, projection, selection, selectionArgs, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                // Log.e("masuk", "ada data " + userName + "," + userPass);
                //      Log.e("masuk", "ada data to first "+userName +","+userPass);
                do {
                    // Log.e("masuk", "ada data" + cursor.getString(1));
                    byte[] picturetostring = cursor.getBlob(cursor
                            .getColumnIndexOrThrow(iUser.picture));
                    String isipicture = null;
                    if (picturetostring != null) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            isipicture = new String(picturetostring, StandardCharsets.UTF_8);
                        }else{
                            isipicture = new String(picturetostring, Charset.forName("UTF-8"));
                        }
                    }
                    login = new mSession(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iUser.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userPass)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userSPV)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            picturetostring,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.imei)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.recId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iUser.statusChange)),
                            "login",
                            1,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            isipicture
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        // Log.e("cek kursor", userName + "," + userPass);

        return login;
    }

    public mSession getLogin(String userName, String userPass) {
        mSession login = null;
        String[] projection = {iUser.id, iUser.userName, iUser.userPass, iUser.recId,
                iUser.userStatus, iUser.userSPV, iUser.userLevel, iUser.areaId, iUser.fcmid, iUser.imei,
                iUser.userEmail, iUser.userMacAddress, iUser.userAlias, iUser.picture, iUser.statusChange};//
        String selection = iUser.userName + " = ?  and " + iUser.userPass + " = ? ";
        String[] selectionArgs = {userName, userPass};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_User, projection, selection, selectionArgs, null, null, null);
        try {
// Log.e("cek kursor", userName + "," + userPass);
            if (cursor != null && cursor.moveToFirst()) {
                Log.e("masuk", "ada data " + userName + "," + userPass);
                //      Log.e("masuk", "ada data to first "+userName +","+userPass);
                do {
                    // Log.e("masuk", "ada data" + cursor.getString(1));
                    byte[] picturetostring = cursor.getBlob(cursor
                            .getColumnIndexOrThrow(iUser.picture));
                    String isipicture = null;
                    if (picturetostring != null) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            isipicture = new String(picturetostring, StandardCharsets.UTF_8);
                        }else{
                            isipicture = new String(picturetostring, Charset.forName("UTF-8"));
                        }
                    }
                    login = new mSession(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iUser.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userPass)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userSPV)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            picturetostring,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.imei)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.recId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iUser.statusChange)),
                            "login",
                            1,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            isipicture
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return login;
    }

    public mSession getLogins(String userName) {
        mSession login = null;
        String[] projection = {iUser.id, iUser.userName, iUser.userPass, iUser.recId,
                iUser.userStatus, iUser.userSPV, iUser.userLevel, iUser.areaId, iUser.fcmid, iUser.imei,
                iUser.userEmail, iUser.userMacAddress, iUser.userAlias, iUser.picture, iUser.statusChange};//
        String selection = iUser.userName + " = ? ";
        String[] selectionArgs = {userName};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_User, projection, selection, selectionArgs, null, null, null);
        try {
// Log.e("cek kursor", userName + "," + userPass);
            if (cursor != null && cursor.moveToFirst()) {
                //  Log.e("masuk", "ada data " + userName);
                //      Log.e("masuk", "ada data to first "+userName +","+userPass);
                do {
                    // Log.e("masuk", "ada data" + cursor.getString(1));
                    byte[] picturetostring = cursor.getBlob(cursor
                            .getColumnIndexOrThrow(iUser.picture));
                    String isipicture = null;
                    if (picturetostring != null) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            isipicture = new String(picturetostring, StandardCharsets.UTF_8);
                        }else{
                            isipicture = new String(picturetostring, Charset.forName("UTF-8"));
                        }
                    }
                    login = new mSession(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iUser.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userPass)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userSPV)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            picturetostring,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.imei)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.recId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iUser.statusChange)),
                            "login",
                            1,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userStatus)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userLevel)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userMacAddress)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.areaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.fcmid)),
                            isipicture
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return login;
    }

    public boolean addLogin(mSession login) {
        ContentValues values = new ContentValues();

        values.put(iUser.id, login.getId());
        values.put(iUser.userName, login.getUserName());
        values.put(iUser.userPass, login.getUserPass());
        values.put(iUser.userStatus, login.getUserStatus());
        values.put(iUser.userSPV, login.getUserSPV());
        values.put(iUser.userLevel, login.getUserLevel());
        values.put(iUser.areaId, login.getAreaId());
        values.put(iUser.userEmail, login.getUserEmail());
        values.put(iUser.userMacAddress, login.getUserMacAddress());
        values.put(iUser.userAlias, login.getUserAlias());
        values.put(iUser.picture, login.getPicture());
        values.put(iUser.fcmid, login.getFcmid());
        values.put(iUser.imei, login.getImei());
        values.put(iUser.recId, login.getRecId());
        values.put(iUser.statusChange, login.getStatusChange());

        return ((db.insert(TB_User, null, values)) != -1);
    }

    public boolean updateLogin(mSession login) {
        ContentValues values = new ContentValues();
        String selection = iUser.id + " = ? ";
        String[] selectionArgs = {String.valueOf(login.getId())};

        values.put(iUser.userName, login.getUserName());
        values.put(iUser.userPass, login.getUserPass());
        values.put(iUser.userStatus, login.getUserStatus());
        values.put(iUser.userSPV, login.getUserSPV());
        values.put(iUser.userLevel, login.getUserLevel());
        values.put(iUser.areaId, login.getAreaId());
        values.put(iUser.userEmail, login.getUserEmail());
        values.put(iUser.userMacAddress, login.getUserMacAddress());
        values.put(iUser.userAlias, login.getUserAlias());
        values.put(iUser.picture, login.getPicture());
        values.put(iUser.fcmid, login.getFcmid());
        values.put(iUser.imei, login.getImei());
        values.put(iUser.recId, login.getRecId());
        values.put(iUser.statusChange, login.getStatusChange());

        return ((db.update(TB_User, values, selection, selectionArgs)) == 1);

    }

    public boolean insertUpdateLogin(mSession login) {
        String[] projection = {iUser.id};
        String selection = iUser.id + " = ? ";
        String[] selectionArgs = {String.valueOf(login.getId())};
        Cursor cursor = db.query(TB_User, projection, selection, selectionArgs, null, null, null);
        boolean hasil = false;
        try {
            hasil = (cursor.getCount() > 0) ? true : false;
            if (hasil) {
                Log.e("sukses update", "sukase");
                hasil = updateLogin(login);
            } else {
                Log.e("sukses insert", "sukase");
                hasil = addLogin(login);
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hasil;
    }

    /*cust status*/
    public boolean InsertUpateAllCustStatus(ArrayList<mCustStatus> cus) {
        boolean hasil = false;
        try {
            if (cus.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CustStatus + " " +
                        "(" + iCustomerStatus.id
                        + "," + iCustomerStatus.CustStatusName
                        + ") " +
                        "values" +
                        "(?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mCustStatus cu : cus) {
                    insert.bindString(1, String.valueOf(cu.getCustStatusId()));
                    insert.bindString(2, cu.getCustStatusName());
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi cust status ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (cus.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mCustStatus> getCustStatusAll() {
        ArrayList<mCustStatus> list = new ArrayList<>();
        String[] projection = {iCustomerStatus.id, iCustomerStatus.CustStatusName};
        String orderBy = iCustomerStatus.CustStatusName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_CustStatus, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mCustStatus(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomerStatus.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public mCustStatus getCustStatusById(String Id) {
        mCustStatus list = null;
        String[] projection = {iCustomerStatus.id, iCustomerStatus.CustStatusName};
        String selection = iCustomerStatus.id + " = ? ";
        String[] selectionArgs = {Id};
        String orderBy = iCustomerStatus.CustStatusName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_CustStatus, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mCustStatus(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomerStatus.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName))

                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    /*zone*/
    public boolean InsertUpateAllZone(ArrayList<mZone> zone) {
        boolean hasil = false;
        try {
            if (zone.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Zone + " " +
                        "(" + iZone.id
                        + "," + iZone.ZoneName
                        + ") " +
                        "values" +
                        "(?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mZone cu : zone) {
                    insert.bindString(1, String.valueOf(cu.getCustZoneId()));
                    insert.bindString(2, cu.getCustZoneName());
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi zone ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (zone.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mZone> getZoneAll() {
        ArrayList<mZone> list = new ArrayList<>();
        String[] projection = {iZone.id, iZone.ZoneName};
        String orderBy = iZone.ZoneName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Zone, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mZone(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iZone.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public mZone getZoneByZoneId(String zoneId) {
        mZone list = null;
        String[] projection = {iZone.id, iZone.ZoneName};
        String selection = iZone.id + " = ? ";
        String[] selectionArgs = {zoneId};
        String orderBy = iZone.ZoneName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Zone, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mZone(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iZone.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName))

                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    /*area*/
    public boolean InsertUpateAllArea(ArrayList<mArea> area) {
        boolean hasil = false;
        try {
            if (area.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Area + " " +
                        "(" + iArea.id
                        + "," + iArea.AreaName
                        + "," + iArea.AreaCode
                        + "," + iArea.RegionId
                        + ") " +
                        "values" +
                        "(?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mArea cu : area) {
                    insert.bindString(1, String.valueOf(cu.getAreaId()));
                    insert.bindString(2, cu.getAreaName());
                    insert.bindString(3, cu.getAreaCode());
                    insert.bindString(4, String.valueOf(cu.getRegionId()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                // Log.e("isi branch ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (area.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mArea> getAreaAll() {
        ArrayList<mArea> list = new ArrayList<>();
        String[] projection = {iArea.id,
                iArea.AreaCode,
                iArea.AreaName,
                iArea.RegionId};
        String orderBy = iArea.AreaName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Area, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mArea(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iArea.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iArea.RegionId))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mArea> getAreaByRegion(String regionId) {
        ArrayList<mArea> list = new ArrayList<>();
        String[] projection = {iArea.id,
                iArea.AreaCode,
                iArea.AreaName,
                iArea.RegionId};//
        String selection = iArea.RegionId + " = ? ";
        String[] selectionArgs = {regionId};
        String orderBy = iArea.AreaName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Area, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mArea(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iArea.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iArea.RegionId))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public mArea getAreaByAreaId(String areaId) {
        mArea list = null;
        String[] projection = {iArea.id,
                iArea.AreaCode,
                iArea.AreaName,
                iArea.RegionId};//
        String selection = iArea.id + " = ? ";
        String[] selectionArgs = {areaId};
        String orderBy = iArea.AreaName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Area, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mArea(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iArea.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iArea.RegionId))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    /*channel*/
    public boolean InsertUpateAllChannel(ArrayList<mChannel> cnl) {
        boolean hasil = false;
        try {
            if (cnl.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Channel + " " +
                        "(" + iChannel.ChannelId
                        + "," + iChannel.ChannelName
                        + "," + iChannel.Email
                        + "," + iChannel.Signature
                        + "," + iChannel.Pic
                        + "," + iChannel.StatusId
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mChannel cu : cnl) {
                    insert.bindString(1, String.valueOf(cu.getChannelId()));
                    insert.bindString(2, cu.getChannelName());
                    insert.bindString(3, cu.getEmail());
                    insert.bindString(4, cu.getSignature());
                    insert.bindString(5, cu.getPic());
                    insert.bindString(6, String.valueOf(cu.getStatusId()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                // Log.e("isi branch ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (cnl.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mChannel> getChannelAll() {
        ArrayList<mChannel> list = new ArrayList<>();
        String[] projection = {iChannel.ChannelId, iChannel.ChannelName, iChannel.Email, iChannel.Pic, iChannel.StatusId, iChannel.Signature};
        String orderBy = iChannel.ChannelName + " ASC";
        String selection = iChannel.StatusId + " = ? ";
        String[] selectionArgs = {"1"};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Channel, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mChannel(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iChannel.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Signature)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Pic)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iChannel.StatusId))


                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public mChannel getChannelByChannelId(String channelId) {
        mChannel list = null;
        String[] projection = {iChannel.ChannelId, iChannel.ChannelName, iChannel.Email, iChannel.Pic, iChannel.StatusId};
        String selection = iChannel.ChannelId + " = ? ";
        String[] selectionArgs = {channelId};
        String orderBy = iChannel.ChannelName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Channel, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mChannel(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iChannel.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Signature)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Pic)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iChannel.StatusId))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mChannel> getChannelByStatusId(String statusId) {
        ArrayList<mChannel> list = new ArrayList<>();
        String[] projection = {iChannel.ChannelId, iChannel.ChannelName, iChannel.Email, iChannel.Pic, iChannel.StatusId};
        String selection = iChannel.StatusId + " = ? ";
        String[] selectionArgs = {statusId};
        String orderBy = iChannel.ChannelName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Channel, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mChannel(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iChannel.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Signature)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.Pic)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iChannel.StatusId))


                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    /*level*/
    public boolean InsertUpateAllLevel(ArrayList<mLevel> lvl) {
        boolean hasil = false;
        try {
            if (lvl.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Level + " " +
                        "(" + iLevel.id
                        + "," + iLevel.LevelName
                        + ") " +
                        "values" +
                        "(?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mLevel cu : lvl) {
                    insert.bindString(1, String.valueOf(cu.getCustLevelId()));
                    insert.bindString(2, cu.getCustLevelName());
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                // Log.e("isi branch ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (lvl.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mLevel> getLevelAll() {
        ArrayList<mLevel> list = new ArrayList<>();
        String[] projection = {iLevel.id, iLevel.LevelName};
        String orderBy = iLevel.LevelName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Level, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mLevel(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iLevel.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public mLevel getLevelById(String Id) {
        mLevel list = null;
        String[] projection = {iLevel.id, iLevel.LevelName};
        String selection = iLevel.id + " = ? ";
        String[] selectionArgs = {Id};
        String orderBy = iLevel.LevelName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Level, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mLevel(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iLevel.id)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName))

                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    /*product otherQty diskon*/
    public boolean InsertUpateAllProductPriceDiskon(ArrayList<mProductPriceDiskon> produk) {
        boolean hasil = false;
        try {
            if (produk.size() > 0) {
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_ProdukPriceDisc + " " +
                        "(" + iProductPriceDisc.RecId
                        + "," + iProductPriceDisc.Relation
                        + "," + iProductPriceDisc.DistId
                        + "," + iProductPriceDisc.PriceType
                        + "," + iProductPriceDisc.PriceDiscGroupId
                        + "," + iProductPriceDisc.ProductCode
                        + "," + iProductPriceDisc.UnitId
                        + "," + iProductPriceDisc.Price
                        + "," + iProductPriceDisc.Disc1
                        + "," + iProductPriceDisc.Disc2
                        + "," + iProductPriceDisc.Disc3
                        + "," + iProductPriceDisc.StartDate
                        + "," + iProductPriceDisc.EndDate
                        + "," + iProductPriceDisc.ProductNameDist
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mProductPriceDiskon cu : produk) {
                       /* Log.e("masuk isert demo", cu.getComplainId() + "," + cu.isSafetyFood() + "," +
                                String.valueOf(cu.isQualityFood()) + "," + cu.isQualityApplication() + "," +
                                String.valueOf(cu.isQuantityAll()) + "," + cu.getCreatedDate());*/
                    insert.bindString(1, String.valueOf(cu.getRecId()));
                    insert.bindString(2, String.valueOf(cu.getRelation()));
                    insert.bindString(3, String.valueOf(cu.getDistId()));
                    insert.bindString(4, String.valueOf(cu.getPriceType()));
                    insert.bindString(5, cu.getPriceDiscGroupId());
                    insert.bindString(6, cu.getProductCode());
                    insert.bindString(7, cu.getUnitId());
                    insert.bindString(8, String.valueOf(cu.getPrice()));
                    insert.bindString(9, String.valueOf(cu.getDisc1()));
                    insert.bindString(10, String.valueOf(cu.getDisc2()));
                    insert.bindString(11, String.valueOf(cu.getDisc3()));
                    insert.bindString(12, cu.getStartDate());
                    insert.bindString(13, cu.getEndDate());
                    insert.bindString(14, cu.getProductNameDist());
                    insert.execute();
                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi potherQty dskn", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (produk.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mProductPriceDiskon> getProdukPriceDiskonById(String distGroupId) {

        ArrayList<mProductPriceDiskon> cList = new ArrayList<>();
        try {
            String[] selectionArgs = {distGroupId, "1"};
            @SuppressLint("Recycle")
          /*  Cursor cursor = db.rawQuery("Select " + TB_Produk + ".*," + TB_ProdukPriceDisc + ".* " +
                    " FROM " + TB_Produk +
                    " INNER JOIN " + TB_ProdukPriceDisc + " ON " + iProduct.DistId + "=" + iProductPriceDisc.DistId +
                    " AND " + iProduct.ProductCode + "=" + iProductPriceDisc.ProductCode +
                    " LEFT JOIN " +
                    " ( Select * from " + TB_CustomerAndDistBranch + " INNER JOIN " + TB_DistBranch +
                    "   On " + iCustomerAndDistBranch.DistBranchId + "=" + iDistBranch.DistBranchId +
                    "   WHERE " + iCustomerAndDistBranch.CustomerDistBranchId + "= ? " +
                    "  ) TC ON " + iDistBranch.DistId + "=" + iProduct.DistId
                   , selectionArgs
            );*/
                    Cursor cursor = db.rawQuery(" Select " + TB_Produk + ".*," + TB_ProdukPriceDisc + ".* "
                    +" FROM " + TB_Produk
                    +" INNER JOIN " + TB_ProdukPriceDisc + " ON " + iProduct.DistId + "=" + iProductPriceDisc.DistId
                    +" AND " + iProduct.ProductCode + "=" + iProductPriceDisc.ProductCode
                    +" INNER JOIN "
                    +" ( Select * from " + TB_CustomerAndDistBranch + " INNER JOIN " + TB_DistBranch
                    +"   On " + iCustomerAndDistBranch.DistBranchId + "=" + iDistBranch.DistBranchId
                    +"   WHERE " + iCustomerAndDistBranch.CustomerDistBranchId + "=? "
                    +"  ) TC ON " + iDistBranch.DistId + "=" + iProduct.DistId
                    + " AND (" + iProductPriceDisc.PriceDiscGroupId + "=" + iCustomerAndDistBranch.PriceGroupId
                    +"      OR " + iProductPriceDisc.PriceDiscGroupId + "=" + iCustomerAndDistBranch.CustCode
                    +"      OR " + iProductPriceDisc.PriceType + "=2 )"
                    +" WHERE " + iProduct.StatusId + " = ? ", selectionArgs
            );
            //Log.e("cek data","data"+distGroupId);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        /*Log.e("ada data", distGroupId + " cus " + cursor.getInt(cursor
                                .getColumnIndexOrThrow(iProductPriceDisc.DistId)) + "," + cursor.getString(cursor
                                .getColumnIndexOrThrow(iProductPriceDisc.ProductNameDist)));*/
                        cList.add(new mProductPriceDiskon(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iProductPriceDisc.RecId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Relation)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.DistId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.PriceType)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.PriceDiscGroupId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.ProductCode)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.UnitId)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Price)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc1)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc2)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc3)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.StartDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.EndDate)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.Foto)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductSimpleDescription)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.RecIdProductMap)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.ProductNameDist)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.StatusId))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample produk", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mProductPriceDiskon> getProdukPriceDiskonById(String distGroupId, String productId) {

        ArrayList<mProductPriceDiskon> cList = new ArrayList<>();
        try {
            String[] selectionArgs = {distGroupId, productId, "1"};
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery("Select " + TB_Produk + ".*," + TB_ProdukPriceDisc + ".* " +
                    " from " + TB_Produk +
                    " INNER JOIN " + TB_ProdukPriceDisc + " ON " + iProduct.DistId + "=" + iProductPriceDisc.DistId +
                    " AND " + iProduct.ProductCode + "=" + iProductPriceDisc.ProductCode +
                    " INNER JOIN " +
                    " ( Select * from " + TB_CustomerAndDistBranch + " INNER JOIN " + TB_DistBranch +
                    "   On " + iCustomerAndDistBranch.DistBranchId + "=" + iDistBranch.DistBranchId +
                    "   WHERE " + iCustomerAndDistBranch.CustomerDistBranchId + "= ?" +
                    "  ) TC ON " + iDistBranch.DistId + "=" + iProduct.DistId +
                    " AND (" + iProductPriceDisc.PriceDiscGroupId + "=" + iCustomerAndDistBranch.PriceGroupId +
                    "      OR " + iProductPriceDisc.PriceDiscGroupId + "=" + iCustomerAndDistBranch.CustCode +
                    "      OR " + iProductPriceDisc.PriceType + "=2 )" +
                    " WHERE " + iProduct.ProductId + "= ? AND " + iProduct.StatusId + " = ?", selectionArgs
            );
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        cList.add(new mProductPriceDiskon(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iProductPriceDisc.RecId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Relation)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.DistId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.PriceType)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.PriceDiscGroupId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.ProductCode)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.UnitId)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Price)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc1)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc2)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc3)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.StartDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.EndDate)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.Foto)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductSimpleDescription)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.RecIdProductMap)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.ProductNameDist)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.StatusId))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample produk", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mProductPriceDiskon> getProdukPriceDiskonByPriceGroup(String statusId) {

        ArrayList<mProductPriceDiskon> cList = new ArrayList<>();
        try {
            String[] projection = {
                    iProductPriceDisc.RecId, iProductPriceDisc.Relation, iProductPriceDisc.DistId, iProductPriceDisc.PriceDiscGroupId,
                    iProductPriceDisc.ProductCode, iProductPriceDisc.UnitId, iProductPriceDisc.Price, iProductPriceDisc.PriceType,
                    iProductPriceDisc.Disc1, iProductPriceDisc.Disc2,iProductPriceDisc.Disc3, iProductPriceDisc.StartDate, iProductPriceDisc.EndDate
            };
            String selection = iProduct.StatusId + " = ? ";
            String[] selectionArgs = {statusId};
            String orderby = iProduct.ProductCode;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_ProdukPriceDisc, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        cList.add(new mProductPriceDiskon(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iProductPriceDisc.RecId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Relation)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.DistId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.PriceType)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.PriceDiscGroupId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.ProductCode)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.ProductNameDist)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.UnitId)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Price)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc1)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc2)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.Disc3)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.StartDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProductPriceDisc.EndDate))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample produk", e.getMessage());
        }
        return cList;
    }

    /*product*/
    public boolean InsertUpateAllProduct(ArrayList<mProduct> produk) {
        boolean hasil = false;
        if (produk.size() > 0) {
            for (mProduct cu : produk) {

                String[] projection = {iProduct.ProductId};
                String selection = iProduct.ProductId + " = ? AND " + iProduct.DistId + " = ?";
                String[] selectionArgs = {String.valueOf(cu.getProductId()), String.valueOf(cu.getDistId())};
                Cursor cursor = db.query(TB_Produk, projection, selection, selectionArgs, null, null, null);
                try {
                    hasil = (cursor.getCount() > 0) ? true : false;
                    if (hasil) {
                        //Log.e("sukses update", "sukase");
                        hasil = updateProduct(cu);
                    } else {
                       // Log.e("sukses insert", "sukase");
                        hasil = addProduct(cu);
                    }
                } catch (Exception ex) {
                    Log.e("err GL", "G" + ex.toString());
                    hasil = false;
                } finally {
                    if (cursor != null)
                        cursor.close();
                }

            }
        }

        return hasil;
    }

    public boolean addProduct(mProduct cu) {
       // Log.e("insert product", cu.getProductId() + "," + cu.getDistId());
        ContentValues values = new ContentValues();

        values.put(iProduct.ProductId, String.valueOf(cu.getProductId()));
        values.put(iProduct.ProductName, cu.getProductName());
        values.put(iProduct.Foto, cu.getFoto());
        values.put(iProduct.ProductSimpleDescription, cu.getProductSimpleDescription());
        values.put(iProduct.RecIdProductMap, String.valueOf(cu.getRecIdProductMap()));
        values.put(iProduct.ProductCode, cu.getProductCode());
        values.put(iProduct.DistId, String.valueOf(cu.getDistId()));
        values.put(iProduct.ProductNameDist, cu.getProductNameDist());
        values.put(iProduct.StatusId, String.valueOf(cu.getStatusId()));


        return ((db.insert(TB_Produk, null, values)) != -1);
    }

    public boolean updateProduct(mProduct cu) {
       // Log.e("update product", cu.getProductId() + "," + cu.getDistId());
        ContentValues values = new ContentValues();
        String selection = iProduct.ProductId + " = ? AND " + iProduct.DistId + " = ?";
        String[] selectionArgs = {String.valueOf(cu.getProductId()), String.valueOf(cu.getDistId())};

        values.put(iProduct.ProductId, String.valueOf(cu.getProductId()));
        values.put(iProduct.ProductName, cu.getProductName());
        values.put(iProduct.Foto, cu.getFoto());
        values.put(iProduct.ProductSimpleDescription, cu.getProductSimpleDescription());
        values.put(iProduct.RecIdProductMap, String.valueOf(cu.getRecIdProductMap()));
        values.put(iProduct.ProductCode, cu.getProductCode());
        values.put(iProduct.DistId, String.valueOf(cu.getDistId()));
        values.put(iProduct.ProductNameDist, cu.getProductNameDist());
        values.put(iProduct.StatusId, String.valueOf(cu.getStatusId()));

        return ((db.update(TB_Produk, values, selection, selectionArgs)) == 1);

    }

    public boolean InsertUpateAllProductOld(ArrayList<mProduct> produk) {
        boolean hasil = false;
        try {
            if (produk.size() > 0) {
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Produk + " " +
                        "(" + iProduct.ProductId
                        + "," + iProduct.ProductName
                        + "," + iProduct.Foto
                        + "," + iProduct.ProductSimpleDescription
                        + "," + iProduct.RecIdProductMap
                        + "," + iProduct.ProductCode
                        + "," + iProduct.DistId
                        + "," + iProduct.ProductNameDist
                        + "," + iProduct.StatusId
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mProduct cu : produk) {
                       /* Log.e("masuk isert demo", cu.getComplainId() + "," + cu.isSafetyFood() + "," +
                                String.valueOf(cu.isQualityFood()) + "," + cu.isQualityApplication() + "," +
                                String.valueOf(cu.isQuantityAll()) + "," + cu.getCreatedDate());*/
                    insert.bindString(1, String.valueOf(cu.getProductId()));
                    insert.bindString(2, cu.getProductName());
                    insert.bindString(3, cu.getFoto());
                    insert.bindString(4, cu.getProductSimpleDescription());
                    insert.bindString(5, String.valueOf(cu.getRecIdProductMap()));
                    insert.bindString(6, cu.getProductCode());
                    insert.bindString(7, String.valueOf(cu.getDistId()));
                    insert.bindString(8, cu.getProductNameDist());
                    insert.bindString(9, String.valueOf(cu.getStatusId()));
                    insert.execute();
                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                //Log.e("isi produk ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
           // Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
           // Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (produk.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mProduct> getAllProduct(String statusId) {

        ArrayList<mProduct> cList = new ArrayList<>();
        try {
            String[] projection = {
                    iProduct.ProductId, iProduct.ProductName, iProduct.Foto, iProduct.StatusId, iProduct.ProductSimpleDescription, iProduct.ProductCode
            };
            String selection = iProduct.StatusId + " = ? ";
            String[] selectionArgs = {statusId};
            String orderby = iProduct.ProductName;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(true, TB_Produk, projection, selection, selectionArgs, null, null, orderby, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        cList.add(new mProduct(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iProduct.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductCode)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.Foto)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductSimpleDescription)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iProduct.StatusId))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
               // Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
           // Log.e("err sample produk", e.getMessage());
        }
        return cList;
    }

    /*distributor & branch*/
    /*distributor*/
    public boolean InsertUpateAllDistributor(ArrayList<mDistributor> dist) {
        boolean hasil = false;
        try {
            if (dist.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Dist + " " +
                        "(" + iDist.DistId
                        + "," + iDist.DistName
                        + "," + iDist.Pic
                        + "," + iDist.Address
                        + "," + iDist.Email
                        + "," + iDist.Telp
                        + "," + iDist.StatusId
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mDistributor cu : dist) {
                    insert.bindString(1, String.valueOf(cu.getDistId()));
                    insert.bindString(2, cu.getDistName());
                    insert.bindString(3, cu.getPic());
                    insert.bindString(4, cu.getAddress());
                    insert.bindString(5, cu.getEmail());
                    insert.bindString(6, cu.getTelp());
                    insert.bindString(7, String.valueOf(cu.getStatusId()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                //Log.e("isi branch ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
           // Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
           // Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (dist.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mDistributor> getDistributorAll() {
        ArrayList<mDistributor> list = new ArrayList<>();
        String[] projection = {iDist.DistId, iDist.DistName, iDist.Email, iDist.Pic, iDist.Telp, iDist.Telp, iDist.StatusId};
        String orderBy = iDist.DistName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Dist, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mDistributor(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDist.DistId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDist.StatusId))


                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public mDistributor getDistributorByDistId(String distId) {
        mDistributor list = null;
        String[] projection = {iDist.DistId, iDist.DistName, iDist.Email, iDist.Pic, iDist.Telp, iDist.Telp, iDist.StatusId};
        String selection = iDist.DistId + " = ? ";
        String[] selectionArgs = {distId};
        String orderBy = iDist.DistName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Dist, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mDistributor(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDist.DistId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDist.StatusId))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mDistributor> getDistributorByStatusId(String statusId) {
        ArrayList<mDistributor> list = new ArrayList<>();
        String[] projection = {iDist.DistId, iDist.DistName, iDist.Email, iDist.Pic, iDist.Telp, iDist.Telp, iDist.StatusId};
        String selection = iDist.StatusId + " = ? ";
        String[] selectionArgs = {statusId};
        String orderBy = iDist.DistName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Dist, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mDistributor(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDist.DistId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDist.StatusId))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    /*branch*/
    public boolean InsertUpateAllDistBranch(ArrayList<mDistributorBranch> cust) {
        boolean hasil = false;
        try {
            if (cust.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_DistBranch + " " +
                        "(" + iDistBranch.DistBranchId
                        + "," + iDistBranch.DistBranchName
                        + "," + iDistBranch.AreaCode
                        + "," + iDistBranch.AreaName
                        + "," + iDistBranch.Address
                        + "," + iDistBranch.Pic
                        + "," + iDistBranch.Telp
                        + "," + iDistBranch.Email
                        + "," + iDistBranch.DistId
                        + "," + iDistBranch.AreaId
                        + "," + iDistBranch.StatusId
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);

                for (mDistributorBranch cu : cust) {

                    insert.bindString(1, String.valueOf(cu.getDistBranchId()));
                    insert.bindString(2, String.valueOf(cu.getDistBranchName()));
                    insert.bindString(3, String.valueOf(cu.getAreaCode()));
                    insert.bindString(4, String.valueOf(cu.getAreaName()));
                    insert.bindString(5, cu.getAddress());
                    insert.bindString(6, cu.getPic());
                    insert.bindString(7, cu.getTelp());
                    insert.bindString(8, cu.getEmail());
                    insert.bindString(9, String.valueOf(cu.getDistId()));
                    insert.bindString(10, String.valueOf(cu.getAreaId()));
                    insert.bindString(11, String.valueOf(cu.getStatusId()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
               // Log.e("isi dist branch ", "tidak ada");
                hasil=false;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            //Log.e("XML sql:", "err sql:" + ex.toString());
            hasil=false;
        } catch (Exception e) {
           // Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (cust.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mDistributorBranch> getDistBranchAll() {
        ArrayList<mDistributorBranch> list = new ArrayList<>();
        String[] projection = {iDistBranch.DistBranchId, iDistBranch.DistBranchName, iDistBranch.AreaCode, iDistBranch.AreaName,
                iDistBranch.Address, iDistBranch.Pic, iDistBranch.Telp, iDistBranch.Email, iDistBranch.DistId, iDistBranch.AreaId,
                iDistBranch.StatusId};
        String orderBy = iDistBranch.DistBranchName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_DistBranch, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mDistributorBranch(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDistBranch.DistBranchId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
           // Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return list;
    }

    public mDistributorBranch getDistBranchByDistBranchId(int distBranchId) {
        mDistributorBranch list = null;
        String[] projection = {iDistBranch.DistBranchId, iDistBranch.DistBranchName, iDistBranch.AreaCode, iDistBranch.AreaName,
                iDistBranch.Address, iDistBranch.Pic, iDistBranch.Telp, iDistBranch.Email, iDistBranch.DistId, iDistBranch.AreaId,
                iDistBranch.StatusId};
        String selection = iDistBranch.DistBranchId + " = ? ";
        String[] selectionArgs = {String.valueOf(distBranchId)};
        String orderBy = iDistBranch.DistBranchName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_DistBranch, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mDistributorBranch(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDistBranch.DistBranchId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
           // Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return list;
    }

    public ArrayList<mDistributorBranch> getDistBranchByDistId(String distId) {
        ArrayList<mDistributorBranch> list = new ArrayList<>();
        String[] projection = {iDistBranch.DistBranchId, iDistBranch.DistBranchName, iDistBranch.AreaCode, iDistBranch.AreaName,
                iDistBranch.Address, iDistBranch.Pic, iDistBranch.Telp, iDistBranch.Email, iDistBranch.DistId, iDistBranch.AreaId,
                iDistBranch.StatusId};
        String selection = iDistBranch.DistId + " = ? ";
        String[] selectionArgs = {distId};
        String orderBy = iDistBranch.DistBranchName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_DistBranch, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mDistributorBranch(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDistBranch.DistBranchId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
           // Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public ArrayList<mDistributorBranch> getDistBranchByStatusId(String statusId) {
        ArrayList<mDistributorBranch> list = new ArrayList<>();
        String[] projection = {iDistBranch.DistBranchId, iDistBranch.DistBranchName, iDistBranch.AreaCode, iDistBranch.AreaName,
                iDistBranch.Address, iDistBranch.Pic, iDistBranch.Telp, iDistBranch.Email, iDistBranch.DistId, iDistBranch.AreaId,
                iDistBranch.StatusId};
        String selection = iDistBranch.StatusId + " = ? ";
        String[] selectionArgs = {statusId};
        String orderBy = iDistBranch.DistBranchName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_DistBranch, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mDistributorBranch(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iDistBranch.DistBranchId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    /*customer and branch*/
    public boolean InsertUpateAllCustomerBranch(ArrayList<mCustomerAndDistBranch> cust) {
        boolean hasil = false;
        try {
            if (cust.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CustomerAndDistBranch + " " +
                        "(" + iCustomerAndDistBranch.id
                        + "," + iCustomerAndDistBranch.CustomerDistBranchId
                        + "," + iCustomerAndDistBranch.CustId
                        + "," + iCustomerAndDistBranch.DistBranchId
                        + "," + iCustomerAndDistBranch.CustCode
                        + "," + iCustomerAndDistBranch.PriceGroupId
                        + "," + iCustomerAndDistBranch.PriceGroupName
                        + "," + iCustomerAndDistBranch.DiscGroupId
                        + "," + iCustomerAndDistBranch.DiscGroupName
                        + "," + iCustomerAndDistBranch.CustIdAndro
                        + "," + iCustomerAndDistBranch.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?)";
                //"+ (select " + iCustomerAndDistBranch.StatusSend + " from " + TB_CustomerAndDistBranch + " where " + iCustomerAndDistBranch.id + " = ?))";
                SQLiteStatement insert = db.compileStatement(sql);

                for (mCustomerAndDistBranch cu : cust) {
                    // Log.e("isi branch", "isi brach " + cu.getCustId() + "," + cu.getCustomerDistBranchId());
                    insert.bindString(1, String.valueOf(cu.getCustomerDistBranchId()));
                    insert.bindString(2, String.valueOf(cu.getCustomerDistBranchId()));
                    insert.bindString(3, String.valueOf(cu.getCustId()));
                    insert.bindString(4, String.valueOf(cu.getDistBranchId()));
                    insert.bindString(5, cu.getCustCode());
                    insert.bindString(6, cu.getPriceGroupId());
                    insert.bindString(7, cu.getPriceGroupName());
                    insert.bindString(8, cu.getDiscGroupId());
                    insert.bindString(9, cu.getDiscGroupName());
                    insert.bindString(10, cu.getCustIdAndro());
                    insert.bindString(11, String.valueOf(cu.getStatusSend()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                // Log.e("isi branch ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            //Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            //Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (cust.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean InsertUpateCustomerBranch(mCustomerAndDistBranch cu) {
        boolean hasil = false;
        try {
            db.beginTransaction();
            String sql = "Insert or Replace into " + TB_CustomerAndDistBranch + " " +
                    "(" + iCustomerAndDistBranch.id
                    + "," + iCustomerAndDistBranch.CustomerDistBranchId
                    + "," + iCustomerAndDistBranch.CustId
                    + "," + iCustomerAndDistBranch.DistBranchId
                    + "," + iCustomerAndDistBranch.CustCode
                    + "," + iCustomerAndDistBranch.PriceGroupId
                    + "," + iCustomerAndDistBranch.PriceGroupName
                    + "," + iCustomerAndDistBranch.DiscGroupId
                    + "," + iCustomerAndDistBranch.DiscGroupName
                    + "," + iCustomerAndDistBranch.CustIdAndro
                    + "," + iCustomerAndDistBranch.StatusSend
                    + ") " +
                    "values" +
                    "(?,?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement insert = db.compileStatement(sql);

            insert.bindString(1, String.valueOf(cu.getCustomerDistBranchId()));
            insert.bindString(2, String.valueOf(cu.getCustomerDistBranchId()));
            insert.bindString(3, String.valueOf(cu.getCustId()));
            insert.bindString(4, String.valueOf(cu.getDistBranchId()));
            insert.bindString(5, cu.getCustCode());
            insert.bindString(6, cu.getPriceGroupId());
            insert.bindString(7, cu.getPriceGroupName());
            insert.bindString(8, cu.getDiscGroupId());
            insert.bindString(9, cu.getDiscGroupName());
            insert.bindString(10, cu.getCustIdAndro());
            insert.bindString(11, String.valueOf(cu.getCustomerDistBranchId()));
            insert.execute();

            db.setTransactionSuccessful();
            hasil = true;
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
           // Log.e("XML sql:", "err sql:" + ex.toString());
            hasil = false;
        } catch (Exception e) {
           // Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            db.endTransaction();
        }

        return hasil;
    }

    public boolean addCustomerAndBranch(mCustomerAndDistBranch distBranch) {
        String[] projection = {iCustomerAndDistBranch.CustomerDistBranchId};
        String selection = iCustomerAndDistBranch.CustId + " = ?  AND " + iCustomerAndDistBranch.DistBranchId + " = ? ";
        String[] selectionArgs = {String.valueOf(distBranch.getCustId()), String.valueOf(distBranch.getDistBranchId())};
        Cursor cursor = db.query(TB_CustomerAndDistBranch, projection, selection, selectionArgs, null, null, null);
        boolean hasil = false;
        try {
            hasil = (cursor.getCount() > 0) ? true : false;
            if (!hasil) {
                hasil = InsertUpateCustomerBranch(distBranch);
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return hasil;

    }

    public int delCustBranch(String id) {
        String selection = iCustomerAndDistBranch.id + " = ?";
        String[] selectionArgs = {id};
        return db.delete(TB_CustomerAndDistBranch, selection, selectionArgs);
    }

    public int delCustBranchByCustId(String id) {
        String selection = iCustomerAndDistBranch.CustId + " = ? And " + iCustomerAndDistBranch.StatusSend + "!=?";
        String[] selectionArgs = {id, "1"};
        return db.delete(TB_CustomerAndDistBranch, selection, selectionArgs);
    }

    public boolean InsertUpateAllNewCustomerBranch(ArrayList<mCustomerAndDistBranch> cust, ArrayList<mCustomerAndDistBranch> oldcust) {
        boolean hasil = false;
        try {
            if (oldcust.size() > 0) {
                for (mCustomerAndDistBranch cu : oldcust) {
                    delCustBranch(String.valueOf(cu.getCustomerDistBranchId()));
                }
            }
            if (cust.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CustomerAndDistBranch + " " +
                        "(" + iCustomerAndDistBranch.id
                        + "," + iCustomerAndDistBranch.CustomerDistBranchId
                        + "," + iCustomerAndDistBranch.CustId
                        + "," + iCustomerAndDistBranch.DistBranchId
                        + "," + iCustomerAndDistBranch.CustCode
                        + "," + iCustomerAndDistBranch.PriceGroupId
                        + "," + iCustomerAndDistBranch.PriceGroupName
                        + "," + iCustomerAndDistBranch.DiscGroupId
                        + "," + iCustomerAndDistBranch.DiscGroupName
                        + "," + iCustomerAndDistBranch.CustIdAndro
                        + "," + iCustomerAndDistBranch.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?)";

                SQLiteStatement insert = db.compileStatement(sql);

                for (mCustomerAndDistBranch cu : cust) {
                    //Log.e("isi branch", "isi brach" + cu.getDistBranchName() + "," + cu.getDistBranchId());
                    insert.bindString(1, String.valueOf(cu.getCustomerDistBranchId()));
                    insert.bindString(2, String.valueOf(cu.getCustomerDistBranchId()));
                    insert.bindString(3, String.valueOf(cu.getCustId()));
                    insert.bindString(4, String.valueOf(cu.getDistBranchId()));
                    insert.bindString(5, cu.getCustCode());
                    insert.bindString(6, cu.getPriceGroupId());
                    insert.bindString(7, cu.getPriceGroupName());
                    insert.bindString(8, cu.getDiscGroupId());
                    insert.bindString(9, cu.getDiscGroupName());
                    insert.bindString(10, cu.getCustIdAndro());
                    insert.bindString(11, String.valueOf(cu.getStatusSend()));
                    insert.execute();


                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                // Log.e("isi branch ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            //Log.e("XML sql:", "err sql:" + ex.toString());
            hasil=false;
        } catch (Exception e) {
           // Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (cust.size() > 0)
                db.endTransaction();

        }

        return hasil;
    }

    private ArrayList<mCustomerAndDistBranch> getAllCustAndDistBranch() {
        ArrayList<mCustomerAndDistBranch> cu = new ArrayList<>();
        String[] projection = {iCustomerAndDistBranch.CustomerDistBranchId, iCustomerAndDistBranch.DistBranchId,
                iCustomerAndDistBranch.DiscGroupId, iCustomerAndDistBranch.PriceGroupId, iCustomerAndDistBranch.CustCode, iCustomerAndDistBranch.PriceGroupName,
                iCustomerAndDistBranch.CustId, iCustomerAndDistBranch.CustIdAndro, iCustomerAndDistBranch.id, iCustomerAndDistBranch.DiscGroupName};
        String orderBy = iCustomerAndDistBranch.CustomerDistBranchId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_CustomerAndDistBranch, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    /*Log.e("ada cust and branch", cursor.getString(cursor
                            .getColumnIndexOrThrow(iCustomerAndDistBranch.CustId)) + "," + cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomerAndDistBranch.CustomerDistBranchId)) + "," + cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)));*/
                    cu.add(new mCustomerAndDistBranch(
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustomerDistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustIdAndro))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return cu;
    }

    public ArrayList<mCustomerAndDistBranch> getCustAndDistBranchDistinctBySalesId(String salesId) {
        ArrayList<mCustomerAndDistBranch> cu = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CustomerAndDistBranch +
                " INNER JOIN " + TB_Customer + " ON " +
                iCustomerAndDistBranch.CustId + " = " + iCustomer.CustId +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + " = " + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Dist + " ON " +
                iDistBranch.DistId + " = " + iDist.DistId
        );

        String[] projection = {iCustomerAndDistBranch.DistBranchId,
                //iCustomerAndDistBranch.CustomerDistBranchId,iCustomerAndDistBranch.CustId,
                //iCustomerAndDistBranch.PriceGroupId, iCustomerAndDistBranch.CustCode, iCustomerAndDistBranch.PriceGroupName,iCustomerAndDistBranch.DiscGroupName,iCustomerAndDistBranch.CustIdAndro,iCustomerAndDistBranch.id,iCustomerAndDistBranch.DiscGroupId,
                iDistBranch.Address, iDistBranch.DistBranchName, iDistBranch.AreaCode, iDistBranch.AreaName,
                //iDistBranch.AreaId, iDistBranch.Email, iDistBranch.Pic,
                //iDistBranch.Telp, iDistBranch.StatusId, iDistBranch.DistId,
                iDist.DistName
                //iDist.Address,  iDist.Email, iDist.Pic, iDist.Telp
        };
        String selection = iCustomer.SalesmanId + " = ? ";
        String[] selectionArgs = {salesId};
        String orderBy = iDistBranch.DistBranchName + " ASC";
        _QB.setDistinct(true);
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                   // Log.e("ada customer and branch", cursor.getInt(cursor
                    //        .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)) + " areanya ");
                    cu.add(new mCustomerAndDistBranch(
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)),
                            0,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            "",
                            "",
                            "",
                            0,
                            0,
                            0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            "",
                            "",
                            "",
                            ""
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return cu;

    }

    private ArrayList<mCustomerAndDistBranch> getCustAndDistBranchByCustId(int custId) {
        // Log.e("cari cust distbranch ","id uct"+custId);
        ArrayList<mCustomerAndDistBranch> cu = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CustomerAndDistBranch +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + " = " + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Dist + " ON " +
                iDistBranch.DistId + " = " + iDist.DistId
        );
        String[] projection = {iCustomerAndDistBranch.CustomerDistBranchId, iCustomerAndDistBranch.DistBranchId,
                iCustomerAndDistBranch.DiscGroupId, iCustomerAndDistBranch.PriceGroupId, iCustomerAndDistBranch.CustCode, iCustomerAndDistBranch.PriceGroupName,
                iCustomerAndDistBranch.CustId, iCustomerAndDistBranch.CustIdAndro, iCustomerAndDistBranch.id, iCustomerAndDistBranch.DiscGroupName,
                iDistBranch.Address, iDistBranch.AreaCode, iDistBranch.AreaId, iDistBranch.AreaName, iDistBranch.Email, iDistBranch.Pic, iDistBranch.DistBranchName,
                iDistBranch.Telp, iDistBranch.StatusId, iDistBranch.DistId,
                iDist.Address, iDist.DistName, iDist.Email, iDist.Pic, iDist.Telp
        };
        String selection = iCustomerAndDistBranch.CustId + " = ? ";
        String[] selectionArgs = {String.valueOf(custId)};
        String orderBy = iCustomerAndDistBranch.CustCode + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Log.e("ada customer and branch", cursor.getString(cursor
                    //         .getColumnIndexOrThrow(iCustomerAndDistBranch.CustId)) + " areanya ");
                    cu.add(new mCustomerAndDistBranch(
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustomerDistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustIdAndro)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Email))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return cu;

    }

    private mCustomerAndDistBranch getCustAndDistBranchByCustDistBranchId(int custdistbranchId) {
        mCustomerAndDistBranch cu = null;
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CustomerAndDistBranch +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + " = " + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Dist + " ON " +
                iDistBranch.DistId + " = " + iDist.DistId
        );
        String[] projection = {iCustomerAndDistBranch.CustomerDistBranchId, iCustomerAndDistBranch.DistBranchId,
                iCustomerAndDistBranch.DiscGroupId, iCustomerAndDistBranch.PriceGroupId, iCustomerAndDistBranch.CustCode, iCustomerAndDistBranch.PriceGroupName,
                iCustomerAndDistBranch.CustId, iCustomerAndDistBranch.CustIdAndro, iCustomerAndDistBranch.id, iCustomerAndDistBranch.DiscGroupName,
                iDistBranch.Address, iDistBranch.AreaCode, iDistBranch.AreaId, iDistBranch.AreaName, iDistBranch.Email, iDistBranch.Pic, iDistBranch.DistBranchName,
                iDistBranch.Telp, iDistBranch.StatusId, iDistBranch.DistId,
                iDist.Address, iDist.DistName, iDist.Email, iDist.Pic, iDist.Telp
        };
        String selection = iCustomerAndDistBranch.CustomerDistBranchId + " = ? ";
        String[] selectionArgs = {String.valueOf(custdistbranchId)};
        String orderBy = iCustomerAndDistBranch.CustCode + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Log.e("ada customer and otherQty", cursor.getString(cursor
                    //        .getColumnIndexOrThrow(iDistBranch.Pic)) + " areanya ");
                    cu = new mCustomerAndDistBranch(
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustomerDistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustIdAndro)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Email))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return cu;

    }

    private mCustomerAndDistBranch getCustAndDistBranchByCustDistBranchId(int custId, int branchId) {
        mCustomerAndDistBranch cu = null;
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CustomerAndDistBranch +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + " = " + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Dist + " ON " +
                iDistBranch.DistId + " = " + iDist.DistId
        );
        String[] projection = {iCustomerAndDistBranch.CustomerDistBranchId, iCustomerAndDistBranch.DistBranchId,
                iCustomerAndDistBranch.DiscGroupId, iCustomerAndDistBranch.PriceGroupId, iCustomerAndDistBranch.CustCode, iCustomerAndDistBranch.PriceGroupName,
                iCustomerAndDistBranch.CustId, iCustomerAndDistBranch.CustIdAndro, iCustomerAndDistBranch.id, iCustomerAndDistBranch.DiscGroupName,
                iDistBranch.Address, iDistBranch.AreaCode, iDistBranch.AreaId, iDistBranch.AreaName, iDistBranch.Email, iDistBranch.Pic, iDistBranch.DistBranchName,
                iDistBranch.Telp, iDistBranch.StatusId, iDistBranch.DistId,
                iDist.Address, iDist.DistName, iDist.Email, iDist.Pic, iDist.Telp
        };
        String selection = iCustomerAndDistBranch.CustId + " = ?  AND " + iCustomerAndDistBranch.DistBranchId + "= ? ";
        String[] selectionArgs = {String.valueOf(custId), String.valueOf(branchId)};
        String orderBy = iCustomerAndDistBranch.CustCode + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Log.e("ada customer and otherQty", cursor.getString(cursor
                    //        .getColumnIndexOrThrow(iDistBranch.Pic)) + " areanya ");
                    cu = new mCustomerAndDistBranch(
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustomerDistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.PriceGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.DiscGroupName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerAndDistBranch.CustIdAndro)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistBranchName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDistBranch.Email)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.DistId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.AreaId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iDistBranch.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.DistName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iDist.Email))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return cu;

    }
    /*customer*/

    public boolean InsertUpateCustomer(ArrayList<mCustomer> cust) {
        boolean hasil = false;
        if (cust.size() > 0) {
            for (mCustomer cu : cust) {
                ContentValues values = new ContentValues();

                values.put(iCustomer.id, cu.getCustId());
                values.put(iCustomer.CustId, cu.getCustId());
                values.put(iCustomer.CustGroupId, cu.getCustGroupId());
                values.put(iCustomer.CustName, cu.getCustName());
                values.put(iCustomer.AliasName, cu.getAliasName());
                values.put(iCustomer.Address, cu.getAddress());
                values.put(iCustomer.Lat, cu.getLat());
                values.put(iCustomer.Lng, cu.getLng());
                values.put(iCustomer.Radius, cu.getRadius());
                values.put(iCustomer.Pic, cu.getPic());
                values.put(iCustomer.PicJabatan, cu.getPicJabatan());
                values.put(iCustomer.Telp, cu.getTelp());
                values.put(iCustomer.Email, cu.getEmail());
                values.put(iCustomer.Hp, cu.getHp());
                values.put(iCustomer.Website, cu.getWebsite());
                values.put(iCustomer.Npwp, cu.getNpwp());
                values.put(iCustomer.CreditLimit, cu.getCreditLimit());
                values.put(iCustomer.Top, cu.getTop());
                values.put(iCustomer.JoinDate, cu.getJoinDate().toString());
                values.put(iCustomer.EcTolerance, cu.getEcTolerance());
                values.put(iCustomer.SalesmanId, cu.getSalesmanId());
                values.put(iCustomer.CustomerDistBranchId, cu.getCustomerDistBranchId());
                values.put(iCustomer.AreaId, cu.getAreaId());
                values.put(iCustomer.StsPkpId, cu.getStsPkpId());
                values.put(iCustomer.CustById, cu.getCustById());
                values.put(iCustomer.FreqTypeId, cu.getFreqTypeId());
                values.put(iCustomer.ChannelId, cu.getChannelId());
                values.put(iCustomer.CustStatusId, cu.getCustStatusId());
                //values.put(iCustomer.StatusSend, cu.getStatusSend());
                values.put(iCustomer.CustIdAndro, cu.getCustIdAndro());

                return ((db.replace(TB_Customer, null, values)) != -1);
            }
        }
        return hasil;
    }

    public boolean InsertUpateAllCustomer(ArrayList<mCustomer> cust) {
        boolean hasil = false;
        try {
            if (cust.size() > 0) {
                for (mCustomer cu : cust) {
                    if (null != cu) {
                        //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                        db.beginTransaction();
                        try {
                            String sql = "Insert or Replace into " + TB_Customer + " " +
                                    "(" + iCustomer.id
                                    + "," + iCustomer.CustId
                                    + "," + iCustomer.CustGroupId
                                    + "," + iCustomer.CustName
                                    + "," + iCustomer.AliasName
                                    + "," + iCustomer.Address
                                    + "," + iCustomer.Lat
                                    + "," + iCustomer.Lng
                                    + "," + iCustomer.Radius
                                    + "," + iCustomer.Pic
                                    + "," + iCustomer.PicJabatan
                                    + "," + iCustomer.Telp
                                    + "," + iCustomer.Email
                                    + "," + iCustomer.Hp
                                    + "," + iCustomer.Website
                                    + "," + iCustomer.Npwp
                                    + "," + iCustomer.CreditLimit
                                    + "," + iCustomer.Top
                                    + "," + iCustomer.JoinDate
                                    + "," + iCustomer.EcTolerance
                                    + "," + iCustomer.SalesmanId
                                    + "," + iCustomer.CustomerDistBranchId
                                    + "," + iCustomer.AreaId
                                    + "," + iCustomer.StsPkpId
                                    + "," + iCustomer.StsPkpName
                                    + "," + iCustomer.CustById
                                    + "," + iCustomer.CustByName
                                    + "," + iCustomer.FreqTypeId
                                    + "," + iCustomer.FreqTypeName
                                    + "," + iCustomer.ChannelId
                                    + "," + iCustomer.CustLevelId
                                    + "," + iCustomer.CustZoneId
                                    + "," + iCustomer.CustStatusId
                                    + "," + iCustomer.CustIdAndro
                                    + "," + iCustomer.StatusSend
                                    + "," + iCustomer.NewOutlet
                                    + ") " +
                                    "values" +
                                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                    "(select " + iCustomer.StatusSend + " from " + TB_Customer + " where " + iCustomer.CustId + " = ?),?)";
                            SQLiteStatement insert = db.compileStatement(sql);
                            insert.bindString(1, String.valueOf(cu.getCustId()));
                            insert.bindString(2, String.valueOf(cu.getCustId()));
                            insert.bindString(3, cu.getCustGroupId());
                            insert.bindString(4, cu.getCustName());
                            insert.bindString(5, cu.getAliasName());
                            insert.bindString(6, cu.getAddress());
                            insert.bindString(7, String.valueOf(cu.getLat()));
                            insert.bindString(8, String.valueOf(cu.getLng()));
                            insert.bindString(9, String.valueOf(cu.getRadius()));
                            insert.bindString(10, cu.getPic());
                            insert.bindString(11, cu.getPicJabatan());
                            insert.bindString(12, cu.getTelp());
                            insert.bindString(13, cu.getEmail());
                            insert.bindString(14, cu.getHp());
                            insert.bindString(15, cu.getWebsite());
                            insert.bindString(16, cu.getNpwp());
                            insert.bindString(17, cu.getCreditLimit());
                            insert.bindString(18, cu.getTop());
                            insert.bindString(19, cu.getJoinDate());
                            insert.bindString(20, String.valueOf(cu.getEcTolerance()));
                            insert.bindString(21, String.valueOf(cu.getSalesmanId()));
                            insert.bindString(22, String.valueOf(cu.getCustomerDistBranchId()));
                            insert.bindString(23, String.valueOf(cu.getAreaId()));
                            insert.bindString(24, String.valueOf(cu.getStsPkpId()));
                            insert.bindString(25, cu.getStsPkpName());
                            insert.bindString(26, String.valueOf(cu.getCustById()));
                            insert.bindString(27, cu.getCustByName());
                            insert.bindString(28, String.valueOf(cu.getFreqTypeId()));
                            insert.bindString(29, cu.getFreqTypeName());
                            insert.bindString(30, String.valueOf(cu.getChannelId()));
                            insert.bindString(31, String.valueOf(cu.getCustLevelId()));
                            insert.bindString(32, String.valueOf(cu.getCustZoneId()));
                            insert.bindString(33, String.valueOf(cu.getCustStatusId()));
                            insert.bindString(34, cu.getCustIdAndro());
                            insert.bindString(35, String.valueOf(cu.getCustId()));
                            insert.bindString(36, String.valueOf(cu.getNewOutlet()));
                            insert.execute();
                            db.setTransactionSuccessful();
                            hasil = true;
                        } catch (android.database.sqlite.SQLiteConstraintException ex) {
                            //Log.e("error db", "db:" + ex.toString());
                            ex.printStackTrace();
                            hasil=false;
                        } catch (Exception ex) {
                            //Log.e("error ex", "ex:" + ex.toString());
                            ex.printStackTrace();
                            hasil=false;
                        } finally {
                            db.endTransaction();
                            //Log.e("isi detail", "isi cutomer");
                            if (delCustBranchByCustId(String.valueOf(cu.getCustId())) > -5) {
                                if (null != cu.getCustomerAndBranch()) {
                                    //Log.e("isi detail branch", cu.getCustName() + " isi cutomer branch " + cu.getCustomerAndBranch().size());
                                    hasil = InsertUpateAllCustomerBranch(cu.getCustomerAndBranch());
                                }
                            }
                        }


                    }
                }


            } else {
                return true;
            }
        } catch (Exception e) {
           // Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public boolean InsertUpateCustomer(mCustomer cu) {
        boolean hasil = false;
        try {
            if (null != cu) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_Customer + " " +
                            "(" + iCustomer.id
                            + "," + iCustomer.CustId
                            + "," + iCustomer.CustGroupId
                            + "," + iCustomer.CustName
                            + "," + iCustomer.AliasName
                            + "," + iCustomer.Address
                            + "," + iCustomer.Lat
                            + "," + iCustomer.Lng
                            + "," + iCustomer.Radius
                            + "," + iCustomer.Pic
                            + "," + iCustomer.PicJabatan
                            + "," + iCustomer.Telp
                            + "," + iCustomer.Email
                            + "," + iCustomer.Hp
                            + "," + iCustomer.Website
                            + "," + iCustomer.Npwp
                            + "," + iCustomer.CreditLimit
                            + "," + iCustomer.Top
                            + "," + iCustomer.JoinDate
                            + "," + iCustomer.EcTolerance
                            + "," + iCustomer.SalesmanId
                            + "," + iCustomer.CustomerDistBranchId
                            + "," + iCustomer.AreaId
                            + "," + iCustomer.StsPkpId
                            + "," + iCustomer.StsPkpName
                            + "," + iCustomer.CustById
                            + "," + iCustomer.CustByName
                            + "," + iCustomer.FreqTypeId
                            + "," + iCustomer.FreqTypeName
                            + "," + iCustomer.ChannelId
                            + "," + iCustomer.CustLevelId
                            + "," + iCustomer.CustZoneId
                            + "," + iCustomer.CustStatusId
                            + "," + iCustomer.CustIdAndro
                            + "," + iCustomer.StatusSend
                            + "," + iCustomer.NewOutlet
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    insert.bindString(1, String.valueOf(cu.get_Id()));
                    insert.bindString(2, String.valueOf(cu.getCustId()));
                    insert.bindString(3, cu.getCustGroupId());
                    insert.bindString(4, cu.getCustName());
                    insert.bindString(5, cu.getAliasName());
                    insert.bindString(6, cu.getAddress());
                    insert.bindString(7, String.valueOf(cu.getLat()));
                    insert.bindString(8, String.valueOf(cu.getLng()));
                    insert.bindString(9, String.valueOf(cu.getRadius()));
                    insert.bindString(10, cu.getPic());
                    insert.bindString(11, cu.getPicJabatan());
                    insert.bindString(12, cu.getTelp());
                    insert.bindString(13, cu.getEmail());
                    insert.bindString(14, cu.getHp());
                    insert.bindString(15, cu.getWebsite());
                    insert.bindString(16, cu.getNpwp());
                    insert.bindString(17, cu.getCreditLimit());
                    insert.bindString(18, cu.getTop());
                    insert.bindString(19, cu.getJoinDate());
                    insert.bindString(20, String.valueOf(cu.getEcTolerance()));
                    insert.bindString(21, String.valueOf(cu.getSalesmanId()));
                    insert.bindString(22, String.valueOf(cu.getCustomerDistBranchId()));
                    insert.bindString(23, String.valueOf(cu.getAreaId()));
                    insert.bindString(24, String.valueOf(cu.getStsPkpId()));
                    insert.bindString(25, cu.getStsPkpName());
                    insert.bindString(26, String.valueOf(cu.getCustById()));
                    insert.bindString(27, cu.getCustByName());
                    insert.bindString(28, String.valueOf(cu.getFreqTypeId()));
                    insert.bindString(29, cu.getFreqTypeName());
                    insert.bindString(30, String.valueOf(cu.getChannelId()));
                    insert.bindString(31, String.valueOf(cu.getCustLevelId()));
                    insert.bindString(32, String.valueOf(cu.getCustZoneId()));
                    insert.bindString(33, String.valueOf(cu.getCustStatusId()));
                    insert.bindString(34, cu.getCustIdAndro());
                    insert.bindString(35, String.valueOf(cu.getStatusSend()));
                    insert.bindString(36, String.valueOf(cu.getNewOutlet()));
                    insert.execute();
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    //Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                    hasil=false;
                } catch (Exception ex) {
                    //Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                    hasil=false;
                } finally {
                    db.endTransaction();

                    //update customer set id=customer id where customer andro is ....

                    //Log.e("isi detail", "isi cutomer");

                    if (cu.getStatusSend() == 0) {
                        updateCustIdByCustIdAndro(cu.getCustId(), cu.get_Id(), cu.getCustIdAndro());
                        if (null != cu.getCustomerAndBranch()) {
                            // Log.e("isi detail branch", "isi cutomer branch");
                            hasil = InsertUpateAllCustomerBranch(cu.getCustomerAndBranch());
                        }
                    } else {
                        if (null != cu.getCustomerAndBranch()) {
                            // Log.e("isi detail branch", "isi cutomer branch");
                            hasil = InsertUpateAllCustomerBranch(cu.getCustomerAndBranch());
                        }
                    }
                }


            }
        } catch (Exception e) {
            //Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public int delCust(String id) {
        String selection = iCustomer.id + " = ?";
        String[] selectionArgs = {id};
        return db.delete(TB_Customer, selection, selectionArgs);
    }

    public int delCustBySales(String salesId) {
        String selection = iCustomer.SalesmanId + " = ?";
        String[] selectionArgs = {salesId};
        return db.delete(TB_Customer, selection, selectionArgs);
    }

    public boolean InsertUpateNewCustomer(mCustomer cu, mCustomer oldcu) {

        boolean hasil = false;
        try {
            if (null != oldcu && null != cu) {
                if (cu.getCustId() != oldcu.getCustId())
                    delCust(String.valueOf(oldcu.get_Id()));
            }
            if (null != cu) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_Customer + " " +
                            "(" + iCustomer.id
                            + "," + iCustomer.CustId
                            + "," + iCustomer.CustGroupId
                            + "," + iCustomer.CustName
                            + "," + iCustomer.AliasName
                            + "," + iCustomer.Address
                            + "," + iCustomer.Lat
                            + "," + iCustomer.Lng
                            + "," + iCustomer.Radius
                            + "," + iCustomer.Pic
                            + "," + iCustomer.PicJabatan
                            + "," + iCustomer.Telp
                            + "," + iCustomer.Email
                            + "," + iCustomer.Hp
                            + "," + iCustomer.Website
                            + "," + iCustomer.Npwp
                            + "," + iCustomer.CreditLimit
                            + "," + iCustomer.Top
                            + "," + iCustomer.JoinDate
                            + "," + iCustomer.EcTolerance
                            + "," + iCustomer.SalesmanId
                            + "," + iCustomer.CustomerDistBranchId
                            + "," + iCustomer.AreaId
                            + "," + iCustomer.StsPkpId
                            + "," + iCustomer.StsPkpName
                            + "," + iCustomer.CustById
                            + "," + iCustomer.CustByName
                            + "," + iCustomer.FreqTypeId
                            + "," + iCustomer.FreqTypeName
                            + "," + iCustomer.ChannelId
                            + "," + iCustomer.CustLevelId
                            + "," + iCustomer.CustZoneId
                            + "," + iCustomer.CustStatusId
                            + "," + iCustomer.CustIdAndro
                            + "," + iCustomer.StatusSend
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    insert.bindString(1, String.valueOf(cu.get_Id()));
                    insert.bindString(2, String.valueOf(cu.getCustId()));
                    insert.bindString(3, cu.getCustGroupId());
                    insert.bindString(4, cu.getCustName());
                    insert.bindString(5, cu.getAliasName());
                    insert.bindString(6, cu.getAddress());
                    insert.bindString(7, String.valueOf(cu.getLat()));
                    insert.bindString(8, String.valueOf(cu.getLng()));
                    insert.bindString(9, String.valueOf(cu.getRadius()));
                    insert.bindString(10, cu.getPic());
                    insert.bindString(11, cu.getPicJabatan());
                    insert.bindString(12, cu.getTelp());
                    insert.bindString(13, cu.getEmail());
                    insert.bindString(14, cu.getHp());
                    insert.bindString(15, cu.getWebsite());
                    insert.bindString(16, cu.getNpwp());
                    insert.bindString(17, cu.getCreditLimit());
                    insert.bindString(18, cu.getTop());
                    insert.bindString(19, cu.getJoinDate());
                    insert.bindString(20, String.valueOf(cu.getEcTolerance()));
                    insert.bindString(21, String.valueOf(cu.getSalesmanId()));
                    insert.bindString(22, String.valueOf(cu.getCustomerDistBranchId()));
                    insert.bindString(23, String.valueOf(cu.getAreaId()));
                    insert.bindString(24, String.valueOf(cu.getStsPkpId()));
                    insert.bindString(25, cu.getStsPkpName());
                    insert.bindString(26, String.valueOf(cu.getCustById()));
                    insert.bindString(27, cu.getCustByName());
                    insert.bindString(28, String.valueOf(cu.getFreqTypeId()));
                    insert.bindString(29, cu.getFreqTypeName());
                    insert.bindString(30, String.valueOf(cu.getChannelId()));
                    insert.bindString(31, String.valueOf(cu.getCustLevelId()));
                    insert.bindString(32, String.valueOf(cu.getCustZoneId()));
                    insert.bindString(33, String.valueOf(cu.getCustStatusId()));
                    insert.bindString(34, cu.getCustIdAndro());
                    insert.bindString(35, String.valueOf(cu.getStatusSend()));
                    insert.execute();
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    //Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                    hasil=false;
                } catch (Exception ex) {
                    //Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                    hasil=false;
                } finally {
                    db.endTransaction();

                    //update customer set id=customer id where customer andro is ....

                    // Log.e("isi detail", "isi cutomer");
                    hasil = InsertUpateAllNewCustomerBranch(cu.getCustomerAndBranch(), oldcu.getCustomerAndBranch());
                    /*if(cu.getStatusSend()==0){
                        updateCustIdByCustIdAndro(cu.getCustId(),cu.get_Id(),cu.getCustIdAndro());
                        if (null != cu.getCustomerAndBranch()) {
                            Log.e("isi detail branch", "isi cutomer branch");
                            hasil = InsertUpateAllCustomerBranch(cu.getCustomerAndBranch());
                        }
                    }else{
                        if (null != cu.getCustomerAndBranch()) {
                            Log.e("isi detail branch", "isi cutomer branch");

                        }
                    }*/
                }


            }
        } catch (Exception e) {
            //Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public boolean updateCustIdByCustIdAndro(int CustId, int id, String idAndro) {
        ContentValues values = new ContentValues();
        String selection = iCustomer.CustIdAndro + " = ? AND " + iCustomer.id + " = ?";
        String[] selectionArgs = {idAndro, String.valueOf(id)};

        values.put(iCustomer.id, CustId);
        //values.put(iCustomer.id, CustId);
        return ((db.update(TB_Customer, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean updateCustLatLng(mCustomer cust) {
        //Log.e("isi", cust.getCustIdAndro() + "," + cust.getCustId());
        ContentValues values = new ContentValues();
        String selection = iCustomer.CustIdAndro + " = ? AND " + iCustomer.CustId + " = ?";
        String[] selectionArgs = {String.valueOf(cust.getCustIdAndro()), String.valueOf(cust.getCustId())};

        values.put(iCustomer.Lat, cust.getLat());
        values.put(iCustomer.Lng, cust.getLng());
        values.put(iCustomer.StatusSend, 1);
        return ((db.update(TB_Customer, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public mCustomer getCustomerByCustomerId(int custId) {
        mCustomer listContacts = null;
        String[] projection = {iCustomer.CustId,
                iCustomer.CustGroupId,
                iCustomer.CustName,
                iCustomer.AliasName,
                iCustomer.Address,
                iCustomer.Lat,
                iCustomer.Lng,
                iCustomer.Radius,
                iCustomer.Pic,
                iCustomer.PicJabatan,
                iCustomer.Telp,
                iCustomer.Email,
                iCustomer.Hp,
                iCustomer.Website,
                iCustomer.Npwp,
                iCustomer.CreditLimit,
                iCustomer.Top,
                iCustomer.JoinDate,
                iCustomer.EcTolerance,
                iCustomer.SalesmanId,
                iCustomer.CustomerDistBranchId,
                iCustomer.AreaId,
                iCustomer.StsPkpId,
                iCustomer.CustById,
                iCustomer.FreqTypeId,
                iCustomer.ChannelId,
                iCustomer.CustLevelId,
                iCustomer.CustZoneId,
                iCustomer.CustStatusId,
                iCustomer.CustIdAndro,
                iCustomer.StatusSend};
        String selection = iCustomer.CustId + " = ? ";
        String[] selectionArgs = {String.valueOf(custId)};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Customer, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts = new mCustomer(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustomerDistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend))

                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerByCustomerGroup(String custId) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        String[] projection = {iCustomer.CustId,
                iCustomer.CustGroupId,
                iCustomer.CustName,
                iCustomer.AliasName,
                iCustomer.Address,
                iCustomer.Lat,
                iCustomer.Lng,
                iCustomer.Radius,
                iCustomer.Pic,
                iCustomer.PicJabatan,
                iCustomer.Telp,
                iCustomer.Email,
                iCustomer.Hp,
                iCustomer.Website,
                iCustomer.Npwp,
                iCustomer.CreditLimit,
                iCustomer.Top,
                iCustomer.JoinDate,
                iCustomer.EcTolerance,
                iCustomer.SalesmanId,
                iCustomer.CustomerDistBranchId,
                iCustomer.AreaId,
                iCustomer.StsPkpId,
                iCustomer.CustById,
                iCustomer.FreqTypeId,
                iCustomer.ChannelId,
                iCustomer.CustLevelId,
                iCustomer.CustZoneId,
                iCustomer.CustStatusId,
                iCustomer.CustIdAndro,
                iCustomer.StatusSend};//
        String selection = iCustomer.CustGroupId + " = ? ";
        String[] selectionArgs = {custId};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Customer, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts
                            .add(new mCustomer(cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustName)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.AliasName)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Address)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Lat)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Lng)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Radius)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Pic)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Telp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Email)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Hp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Website)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Npwp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Top)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.JoinDate)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustomerDistBranchId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.AreaId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustById)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.ChannelId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.StatusSend))

                            ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return listContacts;
    }

    //dimatikan nanti dibuka lagi kalo dipake customer dan detail
    public ArrayList<mCustomer> getCustomerBySales(String salesId, String status) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        String[] projection = {iCustomer.CustId,
                iCustomer.CustGroupId,
                iCustomer.CustName,
                iCustomer.AliasName,
                iCustomer.Address,
                iCustomer.Lat,
                iCustomer.Lng,
                iCustomer.Radius,
                iCustomer.Pic,
                iCustomer.PicJabatan,
                iCustomer.Telp,
                iCustomer.Email,
                iCustomer.Hp,
                iCustomer.Website,
                iCustomer.Npwp,
                iCustomer.CreditLimit,
                iCustomer.Top,
                iCustomer.JoinDate,
                iCustomer.EcTolerance,
                iCustomer.SalesmanId,
                iCustomer.CustomerDistBranchId,
                iCustomer.AreaId,
                iCustomer.StsPkpId,
                iCustomer.CustById,
                iCustomer.FreqTypeId,
                iCustomer.ChannelId,
                iCustomer.CustLevelId,
                iCustomer.CustZoneId,
                iCustomer.CustStatusId,
                iCustomer.CustIdAndro,
                iCustomer.StatusSend};//
        String selection = iCustomer.SalesmanId + " = ? AND " + iCustomer.CustStatusId + " = ?"
                + " AND " + iCustomer.CustId + "=" + iCustomer.CustGroupId;
        String[] selectionArgs = {salesId, status};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Customer, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts
                            .add(new mCustomer(cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustName)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.AliasName)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Address)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Lat)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Lng)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Radius)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Pic)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Telp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Email)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Hp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Website)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Npwp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Top)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.JoinDate)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustomerDistBranchId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.AreaId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustById)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.ChannelId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.StatusSend))

                            ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerAllBySales(String salesId, String status) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        String[] projection = {iCustomer.CustId,
                iCustomer.CustGroupId,
                iCustomer.CustName,
                iCustomer.AliasName,
                iCustomer.Address,
                iCustomer.Lat,
                iCustomer.Lng,
                iCustomer.Radius,
                iCustomer.Pic,
                iCustomer.PicJabatan,
                iCustomer.Telp,
                iCustomer.Email,
                iCustomer.Hp,
                iCustomer.Website,
                iCustomer.Npwp,
                iCustomer.CreditLimit,
                iCustomer.Top,
                iCustomer.JoinDate,
                iCustomer.EcTolerance,
                iCustomer.SalesmanId,
                iCustomer.CustomerDistBranchId,
                iCustomer.AreaId,
                iCustomer.StsPkpId,
                iCustomer.CustById,
                iCustomer.FreqTypeId,
                iCustomer.ChannelId,
                iCustomer.CustLevelId,
                iCustomer.CustZoneId,
                iCustomer.CustStatusId,
                iCustomer.CustIdAndro,
                iCustomer.StatusSend};
        String selection = iCustomer.SalesmanId + " = ? AND " + iCustomer.CustStatusId + " = ?";
        String[] selectionArgs = {salesId, status};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Customer, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts
                            .add(new mCustomer(cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustName)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.AliasName)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Address)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Lat)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Lng)),
                                    cursor.getDouble(cursor
                                            .getColumnIndexOrThrow(iCustomer.Radius)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Pic)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Telp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Email)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Hp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Website)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Npwp)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.Top)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.JoinDate)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustomerDistBranchId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.AreaId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustById)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.ChannelId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iCustomer.StatusSend))

                            ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerAllJoinBySales(String salesId, String status) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Customer +
                " LEFT JOIN " + TB_User + " ON " +
                iCustomer.SalesmanId + " = " + iUser.id +
                " LEFT JOIN " + TB_Level + " ON " +
                iCustomer.CustLevelId + " = " + iLevel.id +
                " LEFT JOIN " + TB_Channel + " ON " +
                iCustomer.ChannelId + "=" + iChannel.ChannelId +
                " LEFT JOIN " + TB_CustStatus + " ON " +
                iCustomer.CustStatusId + "=" + iCustomerStatus.id +
                " LEFT JOIN " + TB_Zone + " ON " +
                iCustomer.CustZoneId + "=" + iZone.id +
                " LEFT JOIN " + TB_Area + " ON " +
                iCustomer.AreaId + "=" + iArea.id);
        String[] projection = {iCustomer.CustId, iCustomer.id, iCustomer.StsPkpName, iCustomer.FreqTypeName, iCustomer.CustByName,
                iCustomer.CustGroupId, iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCustomer.Lat,
                iCustomer.Lng, iCustomer.Radius, iCustomer.Pic, iCustomer.PicJabatan, iCustomer.Telp, iCustomer.Email,
                iCustomer.Hp, iCustomer.Website, iCustomer.Npwp, iCustomer.CreditLimit, iCustomer.Top, iCustomer.JoinDate,
                iCustomer.EcTolerance, iCustomer.SalesmanId, iCustomer.CustomerDistBranchId, iCustomer.AreaId, iCustomer.StsPkpId,
                iCustomer.CustById, iCustomer.FreqTypeId, iCustomer.ChannelId, iCustomer.CustLevelId, iCustomer.CustZoneId,
                iCustomer.CustStatusId, iCustomer.CustIdAndro, iCustomer.StatusSend, iCustomer.NewOutlet,
                iLevel.LevelName, iChannel.ChannelName, iChannel.Pic, iChannel.Email, iCustomerStatus.CustStatusName, iZone.ZoneName,
                iArea.AreaCode, iArea.AreaName, iUser.userAlias
        };
        String selection = iCustomer.SalesmanId + " = ? AND " + iCustomer.CustStatusId + " = ?";
        String[] selectionArgs = {salesId, status};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int custId = cursor.getInt(cursor.getColumnIndexOrThrow(iCustomer.CustId));
                    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = getCustAndDistBranchByCustId(custId);
                    listContacts.add(new mCustomer(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.id)),
                            custId,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustByName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.NewOutlet)),
                            customerAndDistBranches
                    ));
                } while (cursor.moveToNext());
                // getAllCustAndDistBranch();
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerAllJoinBySales(String salesId, String status, String orsatus) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Customer +
                " LEFT JOIN " + TB_User + " ON " +
                iCustomer.SalesmanId + " = " + iUser.id +
                " LEFT JOIN " + TB_Level + " ON " +
                iCustomer.CustLevelId + " = " + iLevel.id +
                " LEFT JOIN " + TB_Channel + " ON " +
                iCustomer.ChannelId + "=" + iChannel.ChannelId +
                " LEFT JOIN " + TB_CustStatus + " ON " +
                iCustomer.CustStatusId + "=" + iCustomerStatus.id +
                " LEFT JOIN " + TB_Zone + " ON " +
                iCustomer.CustZoneId + "=" + iZone.id +
                " LEFT JOIN " + TB_Area + " ON " +
                iCustomer.AreaId + "=" + iArea.id);
        String[] projection = {iCustomer.CustId, iCustomer.id, iCustomer.StsPkpName, iCustomer.FreqTypeName, iCustomer.CustByName,
                iCustomer.CustGroupId, iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCustomer.Lat,
                iCustomer.Lng, iCustomer.Radius, iCustomer.Pic, iCustomer.PicJabatan, iCustomer.Telp, iCustomer.Email,
                iCustomer.Hp, iCustomer.Website, iCustomer.Npwp, iCustomer.CreditLimit, iCustomer.Top, iCustomer.JoinDate,
                iCustomer.EcTolerance, iCustomer.SalesmanId, iCustomer.CustomerDistBranchId, iCustomer.AreaId, iCustomer.StsPkpId,
                iCustomer.CustById, iCustomer.FreqTypeId, iCustomer.ChannelId, iCustomer.CustLevelId, iCustomer.CustZoneId,
                iCustomer.CustStatusId, iCustomer.CustIdAndro, iCustomer.StatusSend, iCustomer.NewOutlet,
                iLevel.LevelName, iChannel.ChannelName, iChannel.Pic, iChannel.Email, iCustomerStatus.CustStatusName, iZone.ZoneName,
                iArea.AreaCode, iArea.AreaName, iUser.userAlias
        };
        String selection = iCustomer.SalesmanId + " = ? AND (" + iCustomer.CustStatusId + " = ? OR " + iCustomer.CustStatusId + " = ? )";
        String[] selectionArgs = {salesId, status, orsatus};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int custId = cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.CustId));
                    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = getCustAndDistBranchByCustId(custId);
                    listContacts.add(new mCustomer(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.id)),
                            custId,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustByName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.NewOutlet)),
                            customerAndDistBranches
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
           // Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public mCustomer getCustomerJoinByCustIdAndSalesId(String idCustomer, String salesId) {
        mCustomer listContacts = new mCustomer();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Customer +
                " LEFT JOIN " + TB_User + " ON " +
                iCustomer.SalesmanId + " = " + iUser.id +
                " LEFT JOIN " + TB_Level + " ON " +
                iCustomer.CustLevelId + " = " + iLevel.id +
                " LEFT JOIN " + TB_Channel + " ON " +
                iCustomer.ChannelId + "=" + iChannel.ChannelId +
                " LEFT JOIN " + TB_CustStatus + " ON " +
                iCustomer.CustStatusId + "=" + iCustomerStatus.id +
                " LEFT JOIN " + TB_Zone + " ON " +
                iCustomer.CustZoneId + "=" + iZone.id +
                " LEFT JOIN " + TB_Area + " ON " +
                iCustomer.AreaId + "=" + iArea.id);
        String[] projection = {iCustomer.CustId, iCustomer.id, iCustomer.StsPkpName, iCustomer.FreqTypeName, iCustomer.CustByName,
                iCustomer.CustGroupId, iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCustomer.Lat,
                iCustomer.Lng, iCustomer.Radius, iCustomer.Pic, iCustomer.PicJabatan, iCustomer.Telp, iCustomer.Email,
                iCustomer.Hp, iCustomer.Website, iCustomer.Npwp, iCustomer.CreditLimit, iCustomer.Top, iCustomer.JoinDate,
                iCustomer.EcTolerance, iCustomer.SalesmanId, iCustomer.CustomerDistBranchId, iCustomer.AreaId, iCustomer.StsPkpId,
                iCustomer.CustById, iCustomer.FreqTypeId, iCustomer.ChannelId, iCustomer.CustLevelId, iCustomer.CustZoneId,
                iCustomer.CustStatusId, iCustomer.CustIdAndro, iCustomer.StatusSend, iCustomer.NewOutlet,
                iLevel.LevelName, iChannel.ChannelName, iChannel.Pic, iChannel.Email, iCustomerStatus.CustStatusName, iZone.ZoneName,
                iArea.AreaCode, iArea.AreaName, iUser.userAlias
        };
        String selection = iCustomer.SalesmanId + " = ? AND " + iCustomer.CustId + " = ?";
        String[] selectionArgs = {salesId, idCustomer};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int custId = cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.CustId));
                    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = getCustAndDistBranchByCustId(custId);
                    listContacts = new mCustomer(cursor.getInt(cursor.getColumnIndexOrThrow(iCustomer.id)),
                            custId,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustByName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.NewOutlet)),
                            customerAndDistBranches
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerAllJoin(String salesId) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Customer +
                " LEFT JOIN " + TB_User + " ON " +
                iCustomer.SalesmanId + " = " + iUser.id +
                " LEFT JOIN " + TB_Level + " ON " +
                iCustomer.CustLevelId + " = " + iLevel.id +
                " LEFT JOIN " + TB_Channel + " ON " +
                iCustomer.ChannelId + "=" + iChannel.ChannelId +
                " LEFT JOIN " + TB_CustStatus + " ON " +
                iCustomer.CustStatusId + "=" + iCustomerStatus.id +
                " LEFT JOIN " + TB_Zone + " ON " +
                iCustomer.CustZoneId + "=" + iZone.id +
                " LEFT JOIN " + TB_Area + " ON " +
                iCustomer.AreaId + "=" + iArea.id);
        String[] projection = {iCustomer.CustId, iCustomer.id, iCustomer.StsPkpName, iCustomer.FreqTypeName, iCustomer.CustByName,
                iCustomer.CustGroupId, iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCustomer.Lat,
                iCustomer.Lng, iCustomer.Radius, iCustomer.Pic, iCustomer.PicJabatan, iCustomer.Telp, iCustomer.Email,
                iCustomer.Hp, iCustomer.Website, iCustomer.Npwp, iCustomer.CreditLimit, iCustomer.Top, iCustomer.JoinDate,
                iCustomer.EcTolerance, iCustomer.SalesmanId, iCustomer.CustomerDistBranchId, iCustomer.AreaId, iCustomer.StsPkpId,
                iCustomer.CustById, iCustomer.FreqTypeId, iCustomer.ChannelId, iCustomer.CustLevelId, iCustomer.CustZoneId,
                iCustomer.CustStatusId, iCustomer.CustIdAndro, iCustomer.StatusSend, iCustomer.NewOutlet,
                iLevel.LevelName, iChannel.ChannelName, iChannel.Pic, iChannel.Email, iCustomerStatus.CustStatusName, iZone.ZoneName,
                iArea.AreaCode, iArea.AreaName, iUser.userAlias
        };
        String selection = iCustomer.SalesmanId + " = ? AND (" + iCustomer.CustStatusId + " = 3  OR " + iCustomer.CustStatusId + " = 5 OR " + iCustomer.CustStatusId + " = 1 )";
        String[] selectionArgs = {salesId};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int custId = cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.CustId));
                    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = getCustAndDistBranchByCustId(custId);
                    listContacts.add(new mCustomer(cursor.getInt(cursor.getColumnIndexOrThrow(iCustomer.id)),
                            custId,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustByName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.NewOutlet)),
                            customerAndDistBranches
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerAllJoinBySalesExcept(String salesId, String status) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Customer +
                " LEFT JOIN " + TB_User + " ON " +
                iCustomer.SalesmanId + " = " + iUser.id +
                " LEFT JOIN " + TB_Level + " ON " +
                iCustomer.CustLevelId + " = " + iLevel.id +
                " LEFT JOIN " + TB_Channel + " ON " +
                iCustomer.ChannelId + "=" + iChannel.ChannelId +
                " LEFT JOIN " + TB_CustStatus + " ON " +
                iCustomer.CustStatusId + "=" + iCustomerStatus.id +
                " LEFT JOIN " + TB_Zone + " ON " +
                iCustomer.CustZoneId + "=" + iZone.id +
                " LEFT JOIN " + TB_Area + " ON " +
                iCustomer.AreaId + "=" + iArea.id);
        String[] projection = {iCustomer.CustId, iCustomer.id, iCustomer.StsPkpName, iCustomer.FreqTypeName, iCustomer.CustByName,
                iCustomer.CustGroupId, iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCustomer.Lat,
                iCustomer.Lng, iCustomer.Radius, iCustomer.Pic, iCustomer.PicJabatan, iCustomer.Telp, iCustomer.Email,
                iCustomer.Hp, iCustomer.Website, iCustomer.Npwp, iCustomer.CreditLimit, iCustomer.Top, iCustomer.JoinDate,
                iCustomer.EcTolerance, iCustomer.SalesmanId, iCustomer.CustomerDistBranchId, iCustomer.AreaId, iCustomer.StsPkpId,
                iCustomer.CustById, iCustomer.FreqTypeId, iCustomer.ChannelId, iCustomer.CustLevelId, iCustomer.CustZoneId,
                iCustomer.CustStatusId, iCustomer.CustIdAndro, iCustomer.StatusSend, iCustomer.NewOutlet,
                iLevel.LevelName, iChannel.ChannelName, iChannel.Pic, iChannel.Email, iCustomerStatus.CustStatusName, iZone.ZoneName,
                iArea.AreaCode, iArea.AreaName, iUser.userAlias
        };
        String selection = iCustomer.SalesmanId + " = ? AND (" + iCustomer.CustStatusId + " = 3  OR " + iCustomer.CustStatusId + " = 5 ) AND " + iCustomer.CustStatusId + " != ? ";
        String[] selectionArgs = {salesId, status};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int custId = cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.CustId));
                    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = getCustAndDistBranchByCustId(custId);
                    listContacts.add(new mCustomer(cursor.getInt(cursor.getColumnIndexOrThrow(iCustomer.id)),
                            custId,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustByName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.NewOutlet)),
                            customerAndDistBranches
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCustomer> getCustomerAllJoinBySalesStatusSend(String salesId, String status) {
        ArrayList<mCustomer> listContacts = new ArrayList<mCustomer>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Customer +
                " LEFT JOIN " + TB_User + " ON " +
                iCustomer.SalesmanId + " = " + iUser.id +
                " LEFT JOIN " + TB_Level + " ON " +
                iCustomer.CustLevelId + " = " + iLevel.id +
                " LEFT JOIN " + TB_Channel + " ON " +
                iCustomer.ChannelId + "=" + iChannel.ChannelId +
                " LEFT JOIN " + TB_CustStatus + " ON " +
                iCustomer.CustStatusId + "=" + iCustomerStatus.id +
                " LEFT JOIN " + TB_Zone + " ON " +
                iCustomer.CustZoneId + "=" + iZone.id +
                " LEFT JOIN " + TB_Area + " ON " +
                iCustomer.AreaId + "=" + iArea.id);
        String[] projection = {iCustomer.CustId, iCustomer.id, iCustomer.StsPkpName, iCustomer.FreqTypeName, iCustomer.CustByName,
                iCustomer.CustGroupId, iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCustomer.Lat,
                iCustomer.Lng, iCustomer.Radius, iCustomer.Pic, iCustomer.PicJabatan, iCustomer.Telp, iCustomer.Email,
                iCustomer.Hp, iCustomer.Website, iCustomer.Npwp, iCustomer.CreditLimit, iCustomer.Top, iCustomer.JoinDate,
                iCustomer.EcTolerance, iCustomer.SalesmanId, iCustomer.CustomerDistBranchId, iCustomer.AreaId, iCustomer.StsPkpId,
                iCustomer.CustById, iCustomer.FreqTypeId, iCustomer.ChannelId, iCustomer.CustLevelId, iCustomer.CustZoneId,
                iCustomer.CustStatusId, iCustomer.CustIdAndro, iCustomer.StatusSend, iCustomer.NewOutlet,
                iLevel.LevelName, iChannel.ChannelName, iChannel.Pic, iChannel.Email, iCustomerStatus.CustStatusName, iZone.ZoneName,
                iArea.AreaCode, iArea.AreaName, iUser.userAlias
        };
        String selection = iCustomer.SalesmanId + " = ? AND " + iCustomer.StatusSend + " = ?";
        String[] selectionArgs = {salesId, status};
        String orderBy = iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int custId = cursor.getInt(cursor
                            .getColumnIndexOrThrow(iCustomer.CustId));
                    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = getCustAndDistBranchByCustId(custId);
                    listContacts.add(new mCustomer(cursor.getInt(cursor.getColumnIndexOrThrow(iCustomer.id)),
                            custId,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustGroupId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lat)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Lng)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iCustomer.Radius)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Pic)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.PicJabatan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Telp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Email)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Hp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Website)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Npwp)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CreditLimit)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Top)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.JoinDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.EcTolerance)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iUser.userAlias)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.AreaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iArea.AreaName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.StsPkpName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustByName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.FreqTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.ChannelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iChannel.ChannelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustLevelId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iLevel.LevelName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustZoneId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iZone.ZoneName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomerStatus.CustStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustIdAndro)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.StatusSend)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCustomer.NewOutlet)),
                            customerAndDistBranches
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    /*Promo*/
    public boolean InsertUpateAllPromo(mPromo cu) {

        boolean hasil = false;
        try {
            if (null != cu) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                try {
                    db.beginTransaction();
                    String sql = "Insert or Replace into " + TB_Promo + " " +
                            "(" + iPromo.PromoId
                            + "," + iPromo.PromoName
                            + "," + iPromo.StartDate
                            + "," + iPromo.EndDate
                            + "," + iPromo.DistId
                            + "," + iPromo.DistName
                            + "," + iPromo.CustRelation
                            + "," + iPromo.Cust
                            + "," + iPromo.CustName
                            + "," + iPromo.ProductId
                            + "," + iPromo.ProductName
                            + "," + iPromo.UnitId
                            + "," + iPromo.MinQty
                            + "," + iPromo.MinValue
                            + "," + iPromo.MultiplyQty
                            + "," + iPromo.ProductIdBonus
                            + "," + iPromo.ProductBonusName
                            + "," + iPromo.UnitIdBonus
                            + "," + iPromo.QtyBonus
                            + "," + iPromo.Notes
                            + "," + iPromo.CreatedDate
                            + "," + iPromo.CreatedBy
                            + "," + iPromo.ModifiedDate
                            + "," + iPromo.ModifiedBy
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);

                    insert.bindString(1, String.valueOf(cu.getPromoId()));
                    insert.bindString(2, cu.getPromoName());
                    insert.bindString(3, cu.getStartDate());
                    insert.bindString(4, cu.getEndDate());
                    insert.bindString(5, String.valueOf(cu.getDistId()));
                    insert.bindString(6, cu.getDistName());
                    insert.bindString(7, String.valueOf(cu.getCustRelation()));
                    insert.bindString(8, cu.getCust());
                    insert.bindString(9, cu.getCustName());
                    insert.bindString(10, String.valueOf(cu.getProductId()));
                    insert.bindString(11, cu.getProductName());
                    insert.bindString(12, cu.getUnitId());
                    insert.bindString(13, String.valueOf(cu.getMinQty()));
                    insert.bindString(14, String.valueOf(cu.getMinValue()));
                    insert.bindString(15, String.valueOf(cu.getMultiplyQty()));
                    insert.bindString(16, String.valueOf(cu.getProductIdBonus()));
                    insert.bindString(17, cu.getProductBonusName());
                    insert.bindString(18, cu.getUnitIdBonus());
                    insert.bindString(19, String.valueOf(cu.getQtyBonus()));
                    insert.bindString(20, cu.getNotes());
                    insert.bindString(21, cu.getCreatedDate());
                    insert.bindString(22, cu.getCreatedBy());
                    insert.bindString(23, cu.getModifiedDate());
                    insert.bindString(24, cu.getModifiedBy());
                    insert.execute();
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    //Log.e("error db", "db:" + ex.toString());
                    db.endTransaction();
                    ex.printStackTrace();
                } catch (Exception ex) {
                    db.endTransaction();
                    //Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            //Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public boolean InsertUpateAllPromo(ArrayList<mPromo> promo) {

        boolean hasil = false;
        try {
            if (promo != null && promo.size() > 0) {
                for (mPromo cu : promo) {
                    if (null != cu) {
                        //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                        try {
                            db.beginTransaction();
                            String sql = "Insert or Replace into " + TB_Promo + " " +
                                    "(" + iPromo.PromoId
                                    + "," + iPromo.PromoName
                                    + "," + iPromo.StartDate
                                    + "," + iPromo.EndDate
                                    + "," + iPromo.DistId
                                    + "," + iPromo.DistName
                                    + "," + iPromo.CustRelation
                                    + "," + iPromo.Cust
                                    + "," + iPromo.CustName
                                    + "," + iPromo.ProductId
                                    + "," + iPromo.ProductName
                                    + "," + iPromo.UnitId
                                    + "," + iPromo.MinQty
                                    + "," + iPromo.MinValue
                                    + "," + iPromo.MultiplyQty
                                    + "," + iPromo.ProductIdBonus
                                    + "," + iPromo.ProductBonusName
                                    + "," + iPromo.UnitIdBonus
                                    + "," + iPromo.QtyBonus
                                    + "," + iPromo.Notes
                                    + "," + iPromo.CreatedDate
                                    + "," + iPromo.CreatedBy
                                    + "," + iPromo.ModifiedDate
                                    + "," + iPromo.ModifiedBy
                                    + ") " +
                                    "values" +
                                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            SQLiteStatement insert = db.compileStatement(sql);

                            insert.bindString(1, String.valueOf(cu.getPromoId()));
                            insert.bindString(2, cu.getPromoName());
                            insert.bindString(3, cu.getStartDate());
                            insert.bindString(4, cu.getEndDate());
                            insert.bindString(5, String.valueOf(cu.getDistId()));
                            insert.bindString(6, cu.getDistName());
                            insert.bindString(7, String.valueOf(cu.getCustRelation()));
                            insert.bindString(8, cu.getCust());
                            insert.bindString(9, cu.getCustName());
                            insert.bindString(10, String.valueOf(cu.getProductId()));
                            insert.bindString(11, cu.getProductName());
                            insert.bindString(12, cu.getUnitId());
                            insert.bindString(13, String.valueOf(cu.getMinQty()));
                            insert.bindString(14, String.valueOf(cu.getMinValue()));
                            insert.bindString(15, String.valueOf(cu.getMultiplyQty()));
                            insert.bindString(16, String.valueOf(cu.getProductIdBonus()));
                            insert.bindString(17, cu.getProductBonusName());
                            insert.bindString(18, cu.getUnitIdBonus());
                            insert.bindString(19, String.valueOf(cu.getQtyBonus()));
                            insert.bindString(20, cu.getNotes());
                            insert.bindString(21, cu.getCreatedDate());
                            insert.bindString(22, cu.getCreatedBy());
                            insert.bindString(23, cu.getModifiedDate());
                            insert.bindString(24, cu.getModifiedBy());
                            insert.execute();
                            db.setTransactionSuccessful();
                            db.endTransaction();
                            hasil = true;
                        } catch (android.database.sqlite.SQLiteConstraintException ex) {
                            //Log.e("error db", "db:" + ex.toString());
                            db.endTransaction();
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            db.endTransaction();
                            //Log.e("error ex", "ex:" + ex.toString());
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                hasil = true;
            }

        } catch (Exception e) {
            //Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public ArrayList<mPromo> getAllPromo() {

        ArrayList<mPromo> cList = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String[] projection = {
                    iPromo.PromoId, iPromo.PromoName, iPromo.StartDate, iPromo.EndDate, iPromo.DistId, iPromo.DistName, iPromo.CustRelation,
                    iPromo.Cust, iPromo.CustName, iPromo.ProductId, iPromo.ProductName, iPromo.UnitId, iPromo.MinQty, iPromo.MinValue,
                    iPromo.MultiplyQty, iPromo.ProductIdBonus, iPromo.ProductBonusName, iPromo.UnitIdBonus, iPromo.QtyBonus, iPromo.Notes,
                    iPromo.CreatedDate, iPromo.CreatedBy, iPromo.ModifiedDate, iPromo.ModifiedBy
            };
            String selection = iPromo.StartDate + " <= ? AND  (" + iPromo.EndDate + "='1900-01-01' OR " + iPromo.EndDate + ">= ? )";
            String[] selectionArgs = {format.format(calendar.getTime()), format.format(calendar.getTime())};
            // Log.e(" isi tgl",format.format(calendar.getTime()));
            String orderby = iPromo.PromoName;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(true, TB_Promo, projection, selection, selectionArgs, null, null, orderby, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        // Log.e("ada promo",cursor.getString(cursor.getColumnIndexOrThrow(iPromo.PromoName)));
                        cList.add(new mPromo(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iPromo.PromoId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.PromoName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.StartDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.EndDate)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.DistId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.DistName)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.CustRelation)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.Cust)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.CustName)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.ProductName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.UnitId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.MinQty)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.MinValue)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.MultiplyQty)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.ProductIdBonus)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.ProductBonusName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.UnitIdBonus)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iPromo.QtyBonus)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.Notes)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iPromo.ModifiedBy))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                //Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            //Log.e("err sample produk", e.getMessage());
        }
        return cList;
    }

    /*target & rofo*/
    public boolean InsertUpateAllTargetRofo(ArrayList<mRofoTarget> po) {

        boolean hasil = false;
        try {
            if (null != po) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_RofoTarget + " " +
                            "(" + iRofoTarget.SalesTargetId
                            + "," + iRofoTarget.Year
                            + "," + iRofoTarget.Month
                            + "," + iRofoTarget.SalesmanId
                            + "," + iRofoTarget.Value
                            + "," + iRofoTarget.CreatedDate
                            + "," + iRofoTarget.CreatedBy
                            + "," + iRofoTarget.ModifiedDate
                            + "," + iRofoTarget.ModifiedBy
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    for (mRofoTarget cu : po) {
                        insert.bindString(1, String.valueOf(cu.getSalesTargetId()));
                        insert.bindString(2, String.valueOf(cu.getYear()));
                        insert.bindString(3, String.valueOf(cu.getMonth()));
                        insert.bindString(4, String.valueOf(cu.getSalesmanId()));
                        insert.bindString(5, String.valueOf(cu.getValue()));
                        insert.bindString(6, cu.getCreatedDate());
                        insert.bindString(7, cu.getCreatedBy());
                        insert.bindString(8, cu.getModifiedDate());
                        insert.bindString(9, cu.getModifiedBy());
                        insert.execute();
                    }
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    //Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                    hasil=false;
                } catch (Exception ex) {
                    //Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                    hasil=false;
                } finally {
                    db.endTransaction();
                }
            }
        } catch (Exception e) {
            //Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public ArrayList<mRofoTarget> getTargetRofoBySalesId(String salesId) {
        ArrayList<mRofoTarget> target = new ArrayList<>();
        String[] projection = {
                iRofoTarget.SalesTargetId, iRofoTarget.Year, iRofoTarget.Month, iRofoTarget.SalesmanId,
                iRofoTarget.Value, iRofoTarget.CreatedDate, iRofoTarget.CreatedBy, iRofoTarget.ModifiedDate, iRofoTarget.ModifiedBy
        };
        String selection = iRofoTarget.SalesmanId + " = ? ";
        String[] selectionArgs = {salesId};
        String orderBy = iRofoTarget.Year + " ASC," + iRofoTarget.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_RofoTarget, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofoTarget(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iRofoTarget.SalesTargetId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.ModifiedBy))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
           // Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public ArrayList<mRofoTarget> getTargetRofoBySalesIdYearMonth(String salesId, String year, String month) {
        ArrayList<mRofoTarget> target = new ArrayList<>();
        String[] projection = {
                iRofoTarget.SalesTargetId, iRofoTarget.Year, iRofoTarget.Month, iRofoTarget.SalesmanId,
                iRofoTarget.Value, iRofoTarget.CreatedDate, iRofoTarget.CreatedBy, iRofoTarget.ModifiedDate, iRofoTarget.ModifiedBy
        };
        String selection = iRofoTarget.SalesmanId + " = ? AND " + iRofoTarget.Year + "= ? AND " + iRofoTarget.Month + " = ?";
        String[] selectionArgs = {salesId, year, month};
        String orderBy = iRofoTarget.Year + " ASC," + iRofoTarget.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_RofoTarget, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofoTarget(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iRofoTarget.SalesTargetId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofoTarget.ModifiedBy))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            //Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public boolean deleteRofoCustMonth(int salesId, int year, int month, int custId, int distbranch) {
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=? AND " + iRofo.CustId + "=? AND " + iRofo.DistBranchId + "=? AND " + iRofo.StatusSend + "=?";
        String[] selectionArgs = {String.valueOf(salesId), String.valueOf(year), String.valueOf(month), String.valueOf(custId), String.valueOf(distbranch), "0"};
        return ((db.delete(TB_Rofo, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean updateRofoStatus(String rofoId, String salesId, String year, String month, int status) {
        ContentValues values = new ContentValues();
        String selection = iRofo.RofoId + " = ? AND " + iRofo.SalesmanId + "=? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=?";
        String[] selectionArgs = {rofoId, salesId, year, month};

        values.put(iRofo.StatusSend, status);

        return ((db.update(TB_Rofo, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean InsertUpateAllRofo(ArrayList<mRofo> po) {

        boolean hasil = false;
        try {
            if (null != po) {
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_Rofo + " " +
                            "(" + iRofo.RofoId
                            + "," + iRofo.Year
                            + "," + iRofo.Month
                            + "," + iRofo.SalesmanId
                            + "," + iRofo.CustId
                            + "," + iRofo.DistBranchId
                            + "," + iRofo.ProductId
                            + "," + iRofo.ProductCode
                            + "," + iRofo.Qty
                            + "," + iRofo.UnitId
                            + "," + iRofo.Value
                            + "," + iRofo.StatusId
                            + "," + iRofo.StatusName
                            + "," + iRofo.CreatedDate
                            + "," + iRofo.CreatedBy
                            + "," + iRofo.ModifiedDate
                            + "," + iRofo.ModifiedBy
                            + "," + iRofo.StatusSend
                            + "," + iRofo.PriceId
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    for (mRofo cu : po) {
                        if (!cekRofoOnPending(cu.getRofoId(), String.valueOf(cu.getSalesmanId()))) {
                            //Log.e("add rofo",cu.getRofoId()+","+cu.getStatusSend());
                            insert.bindString(1, cu.getRofoId());
                            insert.bindString(2, String.valueOf(cu.getYear()));
                            insert.bindString(3, String.valueOf(cu.getMonth()));
                            insert.bindString(4, String.valueOf(cu.getSalesmanId()));
                            insert.bindString(5, String.valueOf(cu.getCustId()));
                            insert.bindString(6, String.valueOf(cu.getDistBranchId()));
                            insert.bindString(7, String.valueOf(cu.getProductId()));
                            insert.bindString(8, cu.getProductCode());
                            insert.bindString(9, String.valueOf(cu.getQty()));
                            insert.bindString(10, cu.getUnitId());
                            insert.bindString(11, String.valueOf(cu.getValue()));
                            insert.bindString(12, String.valueOf(cu.getStatusId()));
                            insert.bindString(13, cu.getStatusName());
                            insert.bindString(14, cu.getCreatedDate());
                            insert.bindString(15, cu.getCreatedBy());
                            insert.bindString(16, cu.getModifiedDate());
                            insert.bindString(17, cu.getModifiedBy());
                            insert.bindString(18, String.valueOf(cu.getStatusSend()));
                            insert.bindString(19, String.valueOf(cu.getPriceId()));
                            insert.execute();
                        }
                    }
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }


            }
        } catch (Exception e) {
            Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public boolean cekRofoOnPending(String rofoId, String salesId) {
        String[] projection = {iRofo.RofoId};
        String selection = iRofo.RofoId + " = ? AND " + iRofo.SalesmanId + "= ?  AND " + iRofo.StatusSend + "=?";
        String[] selectionArgs = {rofoId, salesId, "1"};
        Cursor cursor = db.query(TB_Rofo, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public ArrayList<mRofo> getRofoBySalesId(String salesId) {
        ArrayList<mRofo> target = new ArrayList<>();
        String[] projection = {
                iRofo.RofoId, iRofo.Year, iRofo.Month, iRofo.SalesmanId, iRofo.CustId, iRofo.DistBranchId, iRofo.ProductId,
                iRofo.ProductCode, iRofo.Qty, iRofo.UnitId, iRofo.StatusId, iRofo.StatusSend, iRofo.StatusName, iRofo.PriceId,
                iRofo.Value, iRofo.CreatedDate, iRofo.CreatedBy, iRofo.ModifiedDate, iRofo.ModifiedBy
        };
        String selection = iRofo.SalesmanId + " = ? ";
        String[] selectionArgs = {salesId};
        String orderBy = iRofo.Year + " ASC," + iRofo.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Rofo, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofo(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofo.RofoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductId)),
                            "",
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductCode)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iRofo.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.PriceId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public ArrayList<mRofo> getRofoBySalesIdStatus(String salesId, String status) {
        ArrayList<mRofo> target = new ArrayList<>();
        String[] projection = {
                iRofo.RofoId, iRofo.Year, iRofo.Month, iRofo.SalesmanId, iRofo.CustId, iRofo.DistBranchId, iRofo.ProductId,
                iRofo.ProductCode, iRofo.Qty, iRofo.UnitId, iRofo.StatusId, iRofo.StatusSend, iRofo.StatusName, iRofo.PriceId,
                iRofo.Value, iRofo.CreatedDate, iRofo.CreatedBy, iRofo.ModifiedDate, iRofo.ModifiedBy
        };
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.StatusSend + "=?";
        String[] selectionArgs = {salesId, status};
        String orderBy = iRofo.Year + " ASC," + iRofo.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Rofo, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofo(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofo.RofoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductId)),
                            "",
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductCode)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iRofo.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.PriceId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public ArrayList<mCustomer> getCustomerBySalesIdYearMonth(String salesId, String year, String month) {
        ArrayList<mCustomer> target = new ArrayList<>();
        String[] projection = {
                iRofo.CustId
        };
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=? ";
        String[] selectionArgs = {salesId, year, month};
        String orderBy = iRofo.CustId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(true, TB_Rofo, projection, selection, selectionArgs, null, null, orderBy, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mCustomer cust = getCustomerByCustomerId(cursor.getInt(cursor.getColumnIndexOrThrow(iRofo.CustId)));
                    target.add(cust);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public ArrayList<mRofo> getRofoBySalesIdYearMonth(String salesId, String year, String month) {
        ArrayList<mRofo> target = new ArrayList<>();
        String[] projection = {
                iRofo.RofoId, iRofo.Year, iRofo.Month, iRofo.SalesmanId, iRofo.CustId, iRofo.DistBranchId, iRofo.ProductId,
                iRofo.ProductCode, iRofo.Qty, iRofo.UnitId, iRofo.StatusId, iRofo.StatusSend, iRofo.StatusName, iRofo.PriceId,
                iRofo.Value, iRofo.CreatedDate, iRofo.CreatedBy, iRofo.ModifiedDate, iRofo.ModifiedBy
        };
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=? ";
        String[] selectionArgs = {salesId, year, month};
        String orderBy = iRofo.Year + " ASC," + iRofo.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Rofo, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofo(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofo.RofoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductId)),
                            "",
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductCode)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iRofo.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.PriceId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public ArrayList<mRofo> getRofoBySalesIdYearMonthApprove(String salesId, String year, String month) {
        ArrayList<mRofo> target = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Rofo +
                " LEFT JOIN " + TB_Customer + " ON " +
                iRofo.CustId + " = " + iCustomer.CustId +
                " LEFT JOIN " + TB_CustomerAndDistBranch + " ON " +
                iCustomer.CustId + "=" + iCustomerAndDistBranch.CustId +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + "=" + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Produk + " ON " +
                iRofo.ProductId + "=" + iProduct.ProductId + " AND " + iProduct.DistId + "=" + iDistBranch.DistId
        );
        String[] projection = {
                iRofo.RofoId, iRofo.Year, iRofo.Month, iRofo.SalesmanId, iRofo.CustId, iRofo.DistBranchId, iRofo.ProductId,
                iRofo.ProductCode, iRofo.Qty, iRofo.UnitId, iRofo.StatusId, iRofo.StatusSend, iRofo.StatusName, iRofo.PriceId,
                iRofo.Value, iRofo.CreatedDate, iRofo.CreatedBy, iRofo.ModifiedDate, iRofo.ModifiedBy, iProduct.ProductName, iCustomer.CustName
        };
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=?  AND " + iRofo.StatusId + " =2";
        String[] selectionArgs = {salesId, year, month};
        String orderBy = iRofo.Year + " ASC," + iRofo.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofo(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofo.RofoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iProduct.ProductName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductCode)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iRofo.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.PriceId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public ArrayList<mRofo> getRofoBySalesIdYearMonthNotApprove(String salesId, String year, String month) {
        ArrayList<mRofo> target = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Rofo +
                " LEFT JOIN " + TB_Customer + " ON " +
                iRofo.CustId + " = " + iCustomer.CustId +
                " LEFT JOIN " + TB_CustomerAndDistBranch + " ON " +
                iCustomer.CustId + "=" + iCustomerAndDistBranch.CustId +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + "=" + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Produk + " ON " +
                iRofo.ProductId + "=" + iProduct.ProductId + " AND " + iProduct.DistId + "=" + iDistBranch.DistId
        );
        String[] projection = {
                iRofo.RofoId, iRofo.Year, iRofo.Month, iRofo.SalesmanId, iRofo.CustId, iRofo.DistBranchId, iRofo.ProductId,
                iRofo.ProductCode, iRofo.Qty, iRofo.UnitId, iRofo.StatusId, iRofo.StatusSend, iRofo.StatusName, iRofo.PriceId,
                iRofo.Value, iRofo.CreatedDate, iRofo.CreatedBy, iRofo.ModifiedDate, iRofo.ModifiedBy, iProduct.ProductName, iCustomer.CustName
        };
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=?  AND " + iRofo.StatusId + " !=2";
        String[] selectionArgs = {salesId, year, month};
        String orderBy = iRofo.Year + " ASC," + iRofo.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofo(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofo.RofoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iProduct.ProductName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductCode)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iRofo.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.PriceId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    //join dengan product
    public ArrayList<mRofo> getRofoBySalesIdYearMonthCust(String salesId, String year, String month, String custid, String custbranch) {
        ArrayList<mRofo> target = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_Rofo +
                " LEFT JOIN " + TB_Customer + " ON " +
                iRofo.CustId + " = " + iCustomer.CustId +
                " LEFT JOIN " + TB_CustomerAndDistBranch + " ON " +
                iCustomer.CustId + "=" + iCustomerAndDistBranch.CustId +
                " LEFT JOIN " + TB_DistBranch + " ON " +
                iCustomerAndDistBranch.DistBranchId + "=" + iDistBranch.DistBranchId +
                " LEFT JOIN " + TB_Produk + " ON " +
                iRofo.ProductId + "=" + iProduct.ProductId + " AND " + iProduct.DistId + "=" + iDistBranch.DistId
        );
        String[] projection = {
                iRofo.RofoId, iRofo.Year, iRofo.Month, iRofo.SalesmanId, iRofo.CustId, iRofo.DistBranchId, iRofo.ProductId,
                iRofo.ProductCode, iRofo.Qty, iRofo.UnitId, iRofo.StatusId, iRofo.StatusSend, iRofo.StatusName, iRofo.PriceId,
                iRofo.Value, iRofo.CreatedDate, iRofo.CreatedBy, iRofo.ModifiedDate, iRofo.ModifiedBy, iProduct.ProductName, iCustomer.CustName
        };
        String selection = iRofo.SalesmanId + " = ? AND " + iRofo.Year + "=? AND " + iRofo.Month + "=? AND " + iRofo.CustId + "=? AND " + iRofo.DistBranchId + "=?";
        String[] selectionArgs = {salesId, year, month, custid, custbranch};
        String orderBy = iRofo.Year + " ASC," + iRofo.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    target.add(new mRofo(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofo.RofoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Year)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Month)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iProduct.ProductName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ProductCode)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iRofo.Value)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.PriceId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iRofo.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iRofo.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    public boolean InsertUpateAllRofoAktualisasi(ArrayList<mRofoAktualisasi> po) {

        boolean hasil = false;
        try {
            if (null != po) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_RofoAktualisasi + " " +
                            "(" + iRofoAktualisasi.RofoAktualisasiId
                            + "," + iRofoAktualisasi.Year
                            + "," + iRofoAktualisasi.Month
                            + "," + iRofoAktualisasi.SalesmanId
                            + "," + iRofoAktualisasi.ValueRofo
                            + "," + iRofoAktualisasi.ValueSales
                            + "," + iRofoAktualisasi.ValueTarget
                            + "," + iRofoAktualisasi.UpdatedDate
                            + "," + iRofoAktualisasi.ValueRofoDraft
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    for (mRofoAktualisasi cu : po) {
                        // Log.e("isi aktualisasi",cu.getMonth()+","+cu.getValueSales()+","+cu.getValueRofo());
                        insert.bindString(1, String.valueOf(cu.getRofoAktualisasiId()));
                        insert.bindString(2, String.valueOf(cu.getYear()));
                        insert.bindString(3, String.valueOf(cu.getMonth()));
                        insert.bindString(4, String.valueOf(cu.getSalesmanId()));
                        insert.bindString(5, String.valueOf(cu.getValueRofo()));
                        insert.bindString(6, String.valueOf(cu.getValueSales()));
                        insert.bindString(7, String.valueOf(cu.getValueTarget()));
                        insert.bindString(8, cu.getUpdatedDate());
                        insert.bindString(9, String.valueOf(cu.getValueRofoDraft()));
                        insert.execute();
                    }
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        } catch (Exception e) {
            Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public ArrayList<mRofoAktualisasi> getRofoAktualBySalesIdYear(String salesId, int year) {
        ArrayList<mRofoAktualisasi> target = new ArrayList<>();
        String[] projection = {
                iRofoAktualisasi.RofoAktualisasiId, iRofoAktualisasi.Year, iRofoAktualisasi.Month, iRofoAktualisasi.SalesmanId, iRofoAktualisasi.ValueRofoDraft,
                iRofoAktualisasi.ValueRofo, iRofoAktualisasi.ValueSales, iRofoAktualisasi.ValueTarget, iRofoAktualisasi.UpdatedDate
        };
        String selection = iRofoAktualisasi.SalesmanId + " = ? AND " + iRofoAktualisasi.Year + "=?";
        String[] selectionArgs = {salesId, String.valueOf(year)};
        String orderBy = iRofoAktualisasi.Year + " ASC," + iRofoAktualisasi.Month + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_RofoAktualisasi, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String bulanName = Constants.getBulanString(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iRofoAktualisasi.Month)));
                    // Log.e("err","err"+cursor.getDouble(cursor.getColumnIndexOrThrow(iRofoAktualisasi.ValueSales)));
                    target.add(new mRofoAktualisasi(cursor.getString(cursor
                            .getColumnIndexOrThrow(iRofoAktualisasi.RofoAktualisasiId)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iRofoAktualisasi.Year)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iRofoAktualisasi.Month)),
                            bulanName,
                            cursor.getInt(cursor.getColumnIndexOrThrow(iRofoAktualisasi.SalesmanId)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(iRofoAktualisasi.ValueRofo)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(iRofoAktualisasi.ValueRofoDraft)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(iRofoAktualisasi.ValueSales)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(iRofoAktualisasi.ValueTarget)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iRofoAktualisasi.UpdatedDate))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return target;
    }

    /*po*/
    public boolean InsertUpateAllPO(mPO cu) {

        boolean hasil = false;
        try {
            if (null != cu) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());

                try {
                    db.beginTransaction();
                    String sql = "Insert or Replace into " + TB_PO + " " +
                            "(" + iPo.PoId
                            + "," + iPo.PoCustNumberRef
                            + "," + iPo.PoDate
                            + "," + iPo.CustId
                            + "," + iPo.SalesmanId
                            + "," + iPo.CallPlanId
                            + "," + iPo.PoById
                            + "," + iPo.PoViaId
                            + "," + iPo.ShipDate
                            + "," + iPo.EndPeriodeDate
                            + "," + iPo.ShipAddress
                            + "," + iPo.Disc1
                            + "," + iPo.Disc2
                            + "," + iPo.isPP
                            + "," + iPo.PicDist
                            + "," + iPo.PicCust
                            + "," + iPo.Notes
                            + "," + iPo.Signature
                            + "," + iPo.PoStatusId
                            + "," + iPo.PoStatusName
                            + "," + iPo.SoNo
                            + "," + iPo.SoDate
                            + "," + iPo.DoNo
                            + "," + iPo.DoDate
                            + "," + iPo.CreatedDate
                            + "," + iPo.CreatedBy
                            + "," + iPo.ConfirmDate
                            + "," + iPo.ModifiedDate
                            + "," + iPo.ModifiedBy
                            + "," + iPo.StatusSend
                            + "," + iPo.DistBranchId
                            + "," + iPo.Mechanisme
                            + "," + iPo.CashDisc
                            + "," + iPo.KeteranganDetail
                            + "," + iPo.isSellOut
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);

                    insert.bindString(1, cu.getPoId());
                    insert.bindString(2, cu.getPoCustNumberRef());
                    insert.bindString(3, cu.getPoDate());
                    insert.bindString(4, String.valueOf(cu.getCustId()));
                    insert.bindString(5, String.valueOf(cu.getSalesmanId()));
                    insert.bindString(6, cu.getCallPlanId());
                    insert.bindString(7, cu.getPoById());
                    insert.bindString(8, cu.getPoViaId());
                    insert.bindString(9, cu.getShipDate());
                    insert.bindString(10, cu.getEndPeriodeDate());
                    insert.bindString(11, cu.getShipAddress());
                    insert.bindString(12, String.valueOf(cu.getDisc1()));
                    insert.bindString(13, String.valueOf(cu.getDisc2()));
                    insert.bindString(14, String.valueOf(cu.isPP() ? 1 : 0));
                    insert.bindString(15, cu.getPicDist());
                    insert.bindString(16, cu.getPicCust());
                    insert.bindString(17, cu.getNotes());
                    insert.bindString(18, cu.getSignature());
                    insert.bindString(19, String.valueOf(cu.getPoStatusId()));
                    insert.bindString(20, cu.getPoStatusName());
                    insert.bindString(21, cu.getSoNo());
                    insert.bindString(22, cu.getSoDate());
                    insert.bindString(23, cu.getDoNo());
                    insert.bindString(24, cu.getDoDate());
                    insert.bindString(25, cu.getCreatedDate());
                    insert.bindString(26, cu.getCreatedBy());
                    insert.bindString(27, cu.getConfirmDate());
                    insert.bindString(28, cu.getModifiedDate());
                    insert.bindString(29, cu.getModifiedBy());
                    insert.bindString(30, String.valueOf(cu.getStatusSend()));
                    insert.bindString(31, String.valueOf(cu.getDistBranchId()));
                    insert.bindString(32, cu.getMechanisme());
                    insert.bindString(33, String.valueOf(cu.getCashDisc()));
                    insert.bindString(34, cu.getKeteranganDetail());
                    insert.bindString(35, String.valueOf(cu.isSellOut() ? 1 : 0));
                    insert.execute();
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    //insert po detail
                    if (cu.getPoLines() != null && cu.getPoLines().size() > 0)
                        hasil = InsertUpateAllPOLine(cu.getPoLines());
                    //insert po detail other
                    if (cu.getPoLineOthers() != null && cu.getPoLineOthers().size() > 0)
                        hasil = InsertUpateAllPOLineOther(cu.getPoLineOthers());
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    Log.e("error db", "db:" + ex.toString());
                    db.endTransaction();
                    ex.printStackTrace();
                } catch (Exception ex) {
                    db.endTransaction();
                    Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    //poline
    public boolean InsertUpateAllPOLine(ArrayList<mPoLine> po) {

        boolean hasil = false;
        try {
            if (null != po) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_PoLine + " " +
                            "(" + iPoLine.RecIdTab
                            + "," + iPoLine.PoId
                            + "," + iPoLine.ProductId
                            + "," + iPoLine.ProductName
                            + "," + iPoLine.ProductCode
                            + "," + iPoLine.Qty
                            + "," + iPoLine.UnitPrice
                            + "," + iPoLine.UnitId
                            + "," + iPoLine.PriceId
                            + "," + iPoLine.PriceList
                            + "," + iPoLine.DiscId
                            + "," + iPoLine.Disc1
                            + "," + iPoLine.Disc2
                            + "," + iPoLine.Disc3
                            + "," + iPoLine.DiscRp
                            + "," + iPoLine.Point
                            + "," + iPoLine.IncludePPN
                            + "," + iPoLine.CreatedDate
                            + "," + iPoLine.ConfirmDate
                            + "," + iPoLine.StatusSend
                            + "," + iPoLine.PromoId
                            + "," + iPoLine.RefRecIdTab
                            + "," + iPoLine.Selected
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    for (mPoLine cu : po) {
                        if (cu.isSelected()) {
                            DeletePoLineByRecId(cu.getRecIdTab());
                        } else {
                            // Log.e("insert poline",cu.getProductName());
                            insert.bindString(1, cu.getRecIdTab());
                            insert.bindString(2, cu.getPoId());
                            insert.bindString(3, String.valueOf(cu.getProductId()));
                            insert.bindString(4, cu.getProductName());
                            insert.bindString(5, cu.getProductCode());
                            insert.bindString(6, String.valueOf(cu.getQty()));
                            insert.bindString(7, String.valueOf(cu.getUnitPrice()));
                            insert.bindString(8, cu.getUnitId());
                            insert.bindString(9, String.valueOf(cu.getPriceId()));
                            insert.bindString(10, String.valueOf(cu.getPriceList()));
                            insert.bindString(11, String.valueOf(cu.getDiscId()));
                            insert.bindString(12, String.valueOf(cu.getDisc1()));
                            insert.bindString(13, String.valueOf(cu.getDisc2()));
                            insert.bindString(14, String.valueOf(cu.getDisc3()));
                            insert.bindString(15, String.valueOf(cu.getDiscRp()));
                            insert.bindString(16, String.valueOf(cu.getPoint()));
                            insert.bindString(17, String.valueOf(cu.isIncludePPN() ? 1 : 0));
                            insert.bindString(18, cu.getCreatedDate());
                            insert.bindString(19, cu.getConfirmDate());
                            insert.bindString(20, String.valueOf(cu.getStatusSend()));
                            insert.bindString(21, String.valueOf(cu.getPromoId()));
                            insert.bindString(22, String.valueOf(cu.getRefRecIdTab()));
                            insert.bindString(23, String.valueOf(cu.isSelected() ? 1 : 0));
                            insert.execute();
                        }
                        if (cu.getPoLineBonus() != null && cu.getQty() > 0 && cu.getProductId() != 0)
                            InsertUpateAllPOLineBonus(cu.getPoLineBonus());
                    }
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }


            }
        } catch (Exception e) {
            Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public boolean DeletePoLineByRecId(String recId) {
        String selection = iPoLine.RecIdTab + " = ?";
        String[] selectionArgs = {recId};
        return ((db.delete(TB_PoLine, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean InsertUpateAllPOLineBonus(mPoLine cu) {

        boolean hasil = false;
        try {
            if (null != cu) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_PoLine + " " +
                            "(" + iPoLine.RecIdTab
                            + "," + iPoLine.PoId
                            + "," + iPoLine.ProductId
                            + "," + iPoLine.ProductName
                            + "," + iPoLine.ProductCode
                            + "," + iPoLine.Qty
                            + "," + iPoLine.UnitPrice
                            + "," + iPoLine.UnitId
                            + "," + iPoLine.PriceId
                            + "," + iPoLine.PriceList
                            + "," + iPoLine.DiscId
                            + "," + iPoLine.Disc1
                            + "," + iPoLine.Disc2
                            + "," + iPoLine.Disc3
                            + "," + iPoLine.DiscRp
                            + "," + iPoLine.Point
                            + "," + iPoLine.IncludePPN
                            + "," + iPoLine.CreatedDate
                            + "," + iPoLine.ConfirmDate
                            + "," + iPoLine.StatusSend
                            + "," + iPoLine.PromoId
                            + "," + iPoLine.RefRecIdTab
                            + "," + iPoLine.Selected
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    //Log.e("insert poline",cu.getProductName());
                    if (cu.isSelected()) {
                        DeletePoLineByRecId(cu.getRecIdTab());
                    } else {
                        insert.bindString(1, cu.getRecIdTab());
                        insert.bindString(2, cu.getPoId());
                        insert.bindString(3, String.valueOf(cu.getProductId()));
                        insert.bindString(4, cu.getProductName());
                        insert.bindString(5, cu.getProductCode());
                        insert.bindString(6, String.valueOf(cu.getQty()));
                        insert.bindString(7, String.valueOf(cu.getUnitPrice()));
                        insert.bindString(8, cu.getUnitId());
                        insert.bindString(9, String.valueOf(cu.getPriceId()));
                        insert.bindString(10, String.valueOf(cu.getPriceList()));
                        insert.bindString(11, String.valueOf(cu.getDiscId()));
                        insert.bindString(12, String.valueOf(cu.getDisc1()));
                        insert.bindString(13, String.valueOf(cu.getDisc2()));
                        insert.bindString(14, String.valueOf(cu.getDisc3()));
                        insert.bindString(15, String.valueOf(cu.getDiscRp()));
                        insert.bindString(16, String.valueOf(cu.getPoint()));
                        insert.bindString(17, String.valueOf(cu.isIncludePPN() ? 1 : 0));
                        insert.bindString(18, cu.getCreatedDate());
                        insert.bindString(19, cu.getConfirmDate());
                        insert.bindString(20, String.valueOf(cu.getStatusSend()));
                        insert.bindString(21, String.valueOf(cu.getPromoId()));
                        insert.bindString(22, String.valueOf(cu.getRefRecIdTab()));
                        insert.bindString(23, String.valueOf(cu.isSelected() ? 1 : 0));
                        insert.execute();
                    }

                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }


            }
        } catch (Exception e) {
            Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    //polineother
    public boolean InsertUpateAllPOLineOther(ArrayList<mPoLineOther> po) {

        boolean hasil = false;
        try {
            if (null != po) {
                //Log.e("isi custoemr","isi cutomer"+ cu.getCustName());
                db.beginTransaction();
                try {
                    String sql = "Insert or Replace into " + TB_PoLineOther + " " +
                            "(" + iPoLineOther.RecIdTab
                            + "," + iPoLineOther.PoId
                            + "," + iPoLineOther.ProductCode
                            + "," + iPoLineOther.ProductName
                            + "," + iPoLineOther.Qty
                            + "," + iPoLineOther.Unit
                            + "," + iPoLineOther.StatusSend
                            + ") " +
                            "values" +
                            "(?,?,?,?,?,?,?)";
                    SQLiteStatement insert = db.compileStatement(sql);
                    for (mPoLineOther cu : po) {
                        insert.bindString(1, cu.getRecIdTab());
                        insert.bindString(2, cu.getPoId());
                        insert.bindString(3, cu.getProductCode());
                        insert.bindString(4, cu.getProductName());
                        insert.bindString(5, String.valueOf(cu.getQty()));
                        insert.bindString(6, cu.getUnit());
                        insert.bindString(7, String.valueOf(cu.getStatusSend()));
                        insert.execute();
                    }
                    db.setTransactionSuccessful();
                    hasil = true;
                } catch (android.database.sqlite.SQLiteConstraintException ex) {
                    Log.e("error db", "db:" + ex.toString());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    Log.e("error ex", "ex:" + ex.toString());
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        } catch (Exception e) {
            Log.e("XML:", "err :" + e.toString());
            hasil = false;
        }

        return hasil;
    }

    public ArrayList<mPO> getAllPOPending() {
        ArrayList<mPO> po = new ArrayList<>();
        String[] projection = {
                iPo.PoId, iPo.PoCustNumberRef, iPo.PoDate, iPo.CustId, iPo.SalesmanId, iPo.CallPlanId, iPo.PoById, iPo.DistBranchId, iPo.CashDisc,
                iPo.PoViaId, iPo.ShipDate, iPo.EndPeriodeDate, iPo.Mechanisme, iPo.ShipAddress, iPo.Disc1, iPo.Disc2, iPo.isPP, iPo.PicDist,
                iPo.PicCust, iPo.Notes, iPo.Signature, iPo.PoStatusId, iPo.PoStatusName, iPo.SoNo, iPo.SoDate, iPo.CreatedBy,
                iPo.DoNo, iPo.DoDate, iPo.CreatedDate, iPo.ConfirmDate, iPo.ModifiedDate, iPo.ModifiedBy, iPo.StatusSend, iPo.isSellOut
        };
        String selection = iPo.StatusSend + "= ? ";
        String[] selectionArgs = {"1"};
        String orderBy = iPo.PoId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PO, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArrayList<mPoLine> poLines = getPOLineByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)), false);
                    ArrayList<mPoLineOther> poLineOthers = getPOLineOtherByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)));
                    //  Log.e("ttl po",poLines.size()+","+poLineOthers.size());
                    mCustomer cust = getCustomerByCustomerId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)));
                    mCustomerAndDistBranch dist = getCustAndDistBranchByCustDistBranchId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)), cursor.getInt(cursor.getColumnIndexOrThrow(iPo.DistBranchId)));
                    po.add(new mPO(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPo.PoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoCustNumberRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoViaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.EndPeriodeDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Mechanisme)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipAddress)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.CashDisc)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isPP)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicDist)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicCust)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Signature)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ConfirmDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedBy)),
                            poLines, poLineOthers, cust, dist,
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isSellOut))>0
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    public ArrayList<mPO> getAllPOBetween(String from, String To, int custId) {
        ArrayList<mPO> po = new ArrayList<>();
        String[] projection = {
                iPo.PoId, iPo.PoCustNumberRef, iPo.PoDate, iPo.CustId, iPo.SalesmanId, iPo.CallPlanId, iPo.PoById, iPo.DistBranchId, iPo.CashDisc,
                iPo.PoViaId, iPo.ShipDate, iPo.EndPeriodeDate, iPo.Mechanisme, iPo.ShipAddress, iPo.Disc1, iPo.Disc2, iPo.isPP, iPo.PicDist,
                iPo.PicCust, iPo.Notes, iPo.Signature, iPo.PoStatusId, iPo.PoStatusName, iPo.SoNo, iPo.SoDate, iPo.CreatedBy,
                iPo.DoNo, iPo.DoDate, iPo.CreatedDate, iPo.ConfirmDate, iPo.ModifiedDate, iPo.ModifiedBy, iPo.StatusSend,iPo.isSellOut
        };
        String selection;
        String[] selectionArgs = null;
        if (custId == 0) {
            selection = iPo.PoDate + ">= ?  And " + iPo.PoDate + "<= ?";
            String[] selectionA = {from, To};
            selectionArgs = selectionA;
        } else {
            selection = iPo.CustId + "=?  AND " + iPo.PoDate + ">= ?  And " + iPo.PoDate + "<= ?";
            String[] selectionA = {String.valueOf(custId), from, To};
            selectionArgs = selectionA;
        }
        String orderBy = iPo.PoDate + " Desc";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PO, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArrayList<mPoLine> poLines = getPOLineByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)), false);
                    ArrayList<mPoLineOther> poLineOthers = getPOLineOtherByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)));
                    //  Log.e("ttl po",poLines.size()+","+poLineOthers.size());
                    mCustomer cust = getCustomerByCustomerId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)));
                    mCustomerAndDistBranch dist = getCustAndDistBranchByCustDistBranchId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)), cursor.getInt(cursor.getColumnIndexOrThrow(iPo.DistBranchId)));
                    po.add(new mPO(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPo.PoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoCustNumberRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoViaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.EndPeriodeDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Mechanisme)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipAddress)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.CashDisc)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isPP)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicDist)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicCust)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Signature)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ConfirmDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedBy)),
                            poLines, poLineOthers, cust, dist,
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isSellOut)) > 0
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    public int getDraftPO() {
        int hasil=0;
        String[] projection = {iPo.PoId};
        String selection = iPo.PoStatusId + " < ? ";
        String[] selectionArgs = {"1"};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PO, projection, selection, selectionArgs, null, null, null);
        if (cursor != null) hasil = cursor.getCount();
        return  hasil;
    }
    public ArrayList<mPO> getAllPODraft() {
        ArrayList<mPO> po = new ArrayList<>();
        String[] projection = {
                iPo.PoId, iPo.PoCustNumberRef, iPo.PoDate, iPo.CustId, iPo.SalesmanId, iPo.CallPlanId, iPo.PoById, iPo.DistBranchId, iPo.CashDisc,
                iPo.PoViaId, iPo.ShipDate, iPo.EndPeriodeDate, iPo.Mechanisme, iPo.ShipAddress, iPo.Disc1, iPo.Disc2, iPo.isPP, iPo.PicDist,
                iPo.PicCust, iPo.Notes, iPo.Signature, iPo.PoStatusId, iPo.PoStatusName, iPo.SoNo, iPo.SoDate, iPo.CreatedBy,
                iPo.DoNo, iPo.DoDate, iPo.CreatedDate, iPo.ConfirmDate, iPo.ModifiedDate, iPo.ModifiedBy, iPo.StatusSend,iPo.isSellOut
        };
        String selection = iPo.PoStatusId + " < ? ";
        String[] selectionArgs = {"1"};
        String orderBy = iPo.PoId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PO, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArrayList<mPoLine> poLines = getPOLineByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)), true);
                    ArrayList<mPoLineOther> poLineOthers = getPOLineOtherByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)));
                    // Log.e("ttl po",poLines.size()+","+poLineOthers.size());
                    mCustomer cust = getCustomerByCustomerId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)));
                    mCustomerAndDistBranch dist = getCustAndDistBranchByCustDistBranchId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)), cursor.getInt(cursor.getColumnIndexOrThrow(iPo.DistBranchId)));
                    po.add(new mPO(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPo.PoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoCustNumberRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoViaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.EndPeriodeDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Mechanisme)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipAddress)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.CashDisc)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isPP)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicDist)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicCust)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Signature)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ConfirmDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedBy)),
                            poLines, poLineOthers, cust, dist,
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isSellOut)) > 0
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        // Log.e("total po yg ada","ttl po "+po.size());
        return po;
    }

    public ArrayList<mPO> getAllPOPPActive(String salesId, String tgl) {
        ArrayList<mPO> po = new ArrayList<>();
        String[] projection = {
                iPo.PoId, iPo.PoCustNumberRef, iPo.PoDate, iPo.CustId, iPo.SalesmanId, iPo.CallPlanId, iPo.PoById, iPo.DistBranchId, iPo.CashDisc,
                iPo.PoViaId, iPo.ShipDate, iPo.EndPeriodeDate, iPo.Mechanisme, iPo.ShipAddress, iPo.Disc1, iPo.Disc2, iPo.isPP, iPo.PicDist,
                iPo.PicCust, iPo.Notes, iPo.Signature, iPo.PoStatusId, iPo.PoStatusName, iPo.SoNo, iPo.SoDate, iPo.CreatedBy,
                iPo.DoNo, iPo.DoDate, iPo.CreatedDate, iPo.ConfirmDate, iPo.ModifiedDate, iPo.ModifiedBy, iPo.StatusSend,iPo.isSellOut
        };
        String selection = iPo.SalesmanId + " = ? AND " + iPo.EndPeriodeDate + ">=? AND " + iPo.PoStatusId + "!=7 AND " + iPo.isPP + "=1 AND " + iPo.PoStatusId + ">=3 ";
        String[] selectionArgs = {salesId, tgl};
        String orderBy = iPo.PoId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PO, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArrayList<mPoLine> poLines = getPOLineByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)), true);
                    ArrayList<mPoLineOther> poLineOthers = getPOLineOtherByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)));
                    //Log.e("ttl po",poLines.size()+","+poLineOthers.size());
                    mCustomer cust = getCustomerByCustomerId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)));
                    mCustomerAndDistBranch dist = getCustAndDistBranchByCustDistBranchId(cursor.getInt(cursor.getColumnIndexOrThrow(iPo.CustId)), cursor.getInt(cursor.getColumnIndexOrThrow(iPo.DistBranchId)));
                    po.add(new mPO(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPo.PoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoCustNumberRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoViaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.EndPeriodeDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Mechanisme)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipAddress)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.CashDisc)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isPP)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicDist)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicCust)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Signature)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ConfirmDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedBy)),
                            poLines, poLineOthers, cust, dist,
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isSellOut)) > 0
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    public mPO getPOByPoId(String poId) {
        mPO po = null;
        String[] projection = {
                iPo.PoId, iPo.PoCustNumberRef, iPo.PoDate, iPo.CustId, iPo.SalesmanId, iPo.CallPlanId, iPo.PoById, iPo.DistBranchId, iPo.CashDisc,
                iPo.PoViaId, iPo.ShipDate, iPo.EndPeriodeDate, iPo.Mechanisme, iPo.ShipAddress, iPo.Disc1, iPo.Disc2, iPo.isPP, iPo.PicDist,
                iPo.PicCust, iPo.Notes, iPo.Signature, iPo.PoStatusId, iPo.PoStatusName, iPo.SoNo, iPo.SoDate, iPo.CreatedBy,
                iPo.DoNo, iPo.DoDate, iPo.CreatedDate, iPo.ConfirmDate, iPo.ModifiedDate, iPo.ModifiedBy, iPo.StatusSend,iPo.isSellOut
        };
        String selection = iPo.PoId + " = ? ";
        String[] selectionArgs = {poId};
        String orderBy = iPo.PoId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PO, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ArrayList<mPoLine> poLines = getPOLineByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)), false);
                    ArrayList<mPoLineOther> poLineOthers = getPOLineOtherByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPo.PoId)));
                    po = new mPO(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPo.PoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoCustNumberRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.DistBranchId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoById)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoViaId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.EndPeriodeDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Mechanisme)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ShipAddress)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPo.CashDisc)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isPP)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicDist)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PicCust)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.Signature)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.PoStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.SoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoNo)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.DoDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ConfirmDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPo.ModifiedBy)),
                            poLines, poLineOthers,
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPo.isSellOut)) > 0
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    public ArrayList<mPoLine> getPOLineByPoId(String poId, boolean isDraft) {
        ArrayList<mPoLine> po = new ArrayList<>();
        String[] projection = {
                iPoLine.RecIdTab, iPoLine.PoId, iPoLine.ProductId, iPoLine.ProductName, iPoLine.ProductCode, iPoLine.RefRecIdTab, iPoLine.PromoId,
                iPoLine.Qty, iPoLine.UnitPrice, iPoLine.UnitId, iPoLine.PriceId, iPoLine.PriceList, iPoLine.DiscId, iPoLine.Disc1, iPoLine.Disc2,iPoLine.Disc3,
                iPoLine.DiscRp, iPoLine.Point, iPoLine.IncludePPN, iPoLine.CreatedDate, iPoLine.ConfirmDate, iPoLine.StatusSend,
        };
        String selection = iPoLine.PoId + " = ? AND (" + iPoLine.UnitPrice + "*" + iPoLine.Qty + ")!=" + iPoLine.DiscRp;
        String[] selectionArgs = {poId};
        String orderBy = iPoLine.PoId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PoLine, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPoLine poBonus = getPOLineBonusByPoId(cursor.getString(cursor.getColumnIndexOrThrow(iPoLine.PoId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iPoLine.RecIdTab)));
                    po.add(new mPoLine(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPoLine.RecIdTab)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.PoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.ProductId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.ProductName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.ProductCode)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.UnitPrice)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.PriceId)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.PriceList)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.DiscId)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Disc3)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.DiscRp)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.PromoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.RefRecIdTab)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Point)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.IncludePPN)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.ConfirmDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.StatusSend)),
                            poBonus, isDraft
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    public mPoLine getPOLineBonusByPoId(String poId, String refPoLine) {
        mPoLine po = null;
        String[] projection = {
                iPoLine.RecIdTab, iPoLine.PoId, iPoLine.ProductId, iPoLine.ProductName, iPoLine.ProductCode, iPoLine.RefRecIdTab, iPoLine.PromoId,
                iPoLine.Qty, iPoLine.UnitPrice, iPoLine.UnitId, iPoLine.PriceId, iPoLine.PriceList, iPoLine.DiscId, iPoLine.Disc1, iPoLine.Disc2,iPoLine.Disc3,
                iPoLine.DiscRp, iPoLine.Point, iPoLine.IncludePPN, iPoLine.CreatedDate, iPoLine.ConfirmDate, iPoLine.StatusSend,
        };
        String selection = iPoLine.PoId + " = ? AND " + iPoLine.RefRecIdTab + "= ? AND (" + iPoLine.Qty + "*" + iPoLine.UnitPrice + ")=" + iPoLine.DiscRp;
        String[] selectionArgs = {poId, refPoLine};
        String orderBy = iPoLine.PoId + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PoLine, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    po = new mPoLine(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPoLine.RecIdTab)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.PoId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.ProductId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.ProductName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.ProductCode)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Qty)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.UnitPrice)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.UnitId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.PriceId)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.PriceList)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.DiscId)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Disc1)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Disc2)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Disc3)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.DiscRp)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.PromoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.RefRecIdTab)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLine.Point)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.IncludePPN)) > 0,
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLine.ConfirmDate)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLine.StatusSend))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    public ArrayList<mPoLineOther> getPOLineOtherByPoId(String poId) {
        ArrayList<mPoLineOther> po = new ArrayList<>();
        String[] projection = {
                iPoLineOther.RecIdTab, iPoLineOther.PoId, iPoLineOther.ProductCode, iPoLineOther.ProductName,
                iPoLineOther.Qty, iPoLineOther.Unit, iPoLineOther.StatusSend
        };
        String selection = iPoLineOther.PoId + " = ? ";
        String[] selectionArgs = {poId};
        String orderBy = iPoLineOther.RecIdTab + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_PoLineOther, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    po.add(new mPoLineOther(cursor.getString(cursor
                            .getColumnIndexOrThrow(iPoLineOther.RecIdTab)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLineOther.PoId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLineOther.ProductCode)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLineOther.ProductName)),
                            cursor.getDouble(cursor
                                    .getColumnIndexOrThrow(iPoLineOther.Qty)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPoLineOther.Unit)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPoLineOther.StatusSend))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return po;
    }

    /*callplan & callplandraft*/
    public boolean InsertUpateAllCallPlan(mCallPlan cu) {
        boolean hasil = false;
        try {
            if (cu != null) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CallPlan + " " +
                        "(" + iCallPlan.CallPlanId
                        + "," + iCallPlan.CallPlanDate
                        + "," + iCallPlan.CallPlanTypeId
                        + "," + iCallPlan.CallPlanTypeName
                        + "," + iCallPlan.SalesmanId
                        + "," + iCallPlan.CustId
                        + "," + iCallPlan.CallPlanStatusId
                        + "," + iCallPlan.CallPlanStatusName
                        + "," + iCallPlan.CreatedDate
                        + "," + iCallPlan.CreatedBy
                        + "," + iCallPlan.StatusSend
                        + "," + iCallPlan.Notes
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                //Log.e("isi call plan", "isi callplan" + cu.getCallPlanId() + "," + cu.getCallPlanDate());
                insert.bindString(1, cu.getCallPlanId());
                insert.bindString(2, cu.getCallPlanDate());
                insert.bindString(3, String.valueOf(cu.getCallPlanTypeId()));
                insert.bindString(4, cu.getCallPlanTypeName());
                insert.bindString(5, String.valueOf(cu.getSalesmanId()));
                insert.bindString(6, String.valueOf(cu.getCustId()));
                insert.bindString(7, String.valueOf(cu.getCallPlanStatusId()));
                insert.bindString(8, cu.getCallPlanStatusName());
                insert.bindString(9, cu.getCreatedDate());
                insert.bindString(10, cu.getCreatedBy());
                insert.bindString(11, String.valueOf(cu.getStatusSend()));
                insert.bindString(12, cu.getNotes());
                insert.execute();

                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi call draft ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (db.inTransaction())
                db.endTransaction();
        }

        return hasil;
    }


    public boolean InsertUpateAllCallPlan(ArrayList<mCallPlan> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CallPlan + " " +
                        "(" + iCallPlan.CallPlanId
                        + "," + iCallPlan.CallPlanDate
                        + "," + iCallPlan.CallPlanTypeId
                        + "," + iCallPlan.CallPlanTypeName
                        + "," + iCallPlan.SalesmanId
                        + "," + iCallPlan.CustId
                        + "," + iCallPlan.CallPlanStatusId
                        + "," + iCallPlan.CallPlanStatusName
                        + "," + iCallPlan.CreatedDate
                        + "," + iCallPlan.CreatedBy
                        + "," + iCallPlan.StatusSend
                        + "," + iCallPlan.Notes
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);

                for (mCallPlan cu : callPlan) {
                    //Log.e("isi call plan", "isi callplan" + cu.getCallPlanId() + "," + cu.getCallPlanDate());
                    insert.bindString(1, cu.getCallPlanId());
                    insert.bindString(2, cu.getCallPlanDate());
                    insert.bindString(3, String.valueOf(cu.getCallPlanTypeId()));
                    insert.bindString(4, cu.getCallPlanTypeName());
                    insert.bindString(5, String.valueOf(cu.getSalesmanId()));
                    insert.bindString(6, String.valueOf(cu.getCustId()));
                    insert.bindString(7, String.valueOf(cu.getCallPlanStatusId()));
                    insert.bindString(8, cu.getCallPlanStatusName());
                    insert.bindString(9, cu.getCreatedDate());
                    insert.bindString(10, cu.getCreatedBy());
                    insert.bindString(11, String.valueOf(cu.getStatusSend()));
                    insert.bindString(12, cu.getNotes());
                    insert.execute();
                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi call draft ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean InsertUpateAllCallPlanWhere(ArrayList<mCallPlan> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CallPlan + " " +
                        "(" + iCallPlan.CallPlanId
                        + "," + iCallPlan.CallPlanDate
                        + "," + iCallPlan.CallPlanTypeId
                        + "," + iCallPlan.CallPlanTypeName
                        + "," + iCallPlan.SalesmanId
                        + "," + iCallPlan.CustId
                        + "," + iCallPlan.CallPlanStatusId
                        + "," + iCallPlan.CallPlanStatusName
                        + "," + iCallPlan.CreatedDate
                        + "," + iCallPlan.CreatedBy
                        + "," + iCallPlan.StatusSend
                        + "," + iCallPlan.Notes
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mCallPlan cu : callPlan) {
                    if (!cekCallPlanOnPending(cu.getCallPlanId(), String.valueOf(cu.getSalesmanId()))) {
                       /* Log.e("masuk isert callPlan", cu.getCallPlanId() + "," + cu.getCallPlanDate() + "," +
                                String.valueOf(cu.getCallPlanTypeId()) + "," + cu.getCallPlanTypeName() + "," +
                                String.valueOf(cu.getSalesmanId()) + "," + cu.getCallPlanStatusName());*/
                        insert.bindString(1, cu.getCallPlanId());
                        insert.bindString(2, cu.getCallPlanDate());
                        insert.bindString(3, String.valueOf(cu.getCallPlanTypeId()));
                        insert.bindString(4, cu.getCallPlanTypeName());
                        insert.bindString(5, String.valueOf(cu.getSalesmanId()));
                        insert.bindString(6, String.valueOf(cu.getCustId()));
                        insert.bindString(7, String.valueOf(cu.getCallPlanStatusId()));
                        insert.bindString(8, cu.getCallPlanStatusName());
                        insert.bindString(9, cu.getCreatedDate());
                        insert.bindString(10, cu.getCreatedBy());
                        insert.bindString(11, String.valueOf(cu.getStatusSend()));
                        insert.bindString(12, cu.getNotes());
                        insert.execute();
                        updateDraftCallPlan(cu);
                    }

                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call draft ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public mCallPlan getCallPlanAllJoinByCallPlanId(String callPlanId) {
        mCallPlan listContacts = null;
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CallPlan +
                " LEFT JOIN " + TB_Customer + " ON " +
                iCustomer.CustId + " = " + iCallPlan.CustId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCallPlan.CallPlanId,
                iCallPlan.CustId, iCallPlan.CallPlanDate, iCallPlan.CallPlanStatusId, iCallPlan.CallPlanTypeId,
                iCallPlan.CreatedBy, iCallPlan.CreatedDate, iCallPlan.SalesmanId, iCallPlan.StatusSend, iCallPlan.Notes,
                iCallPlan.CallPlanStatusName, iCallPlan.CallPlanTypeName
        };
        String selection = iCallPlan.CallPlanId + " = ? ";
        String[] selectionArgs = {callPlanId};
        String orderBy = iCallPlan.CallPlanDate + " ASC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts = new mCallPlan(cursor.getString(cursor
                            .getColumnIndexOrThrow(iCallPlan.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCallPlan> getCallPlanAllJoinByStatusSend(int statusSend) {
        ArrayList<mCallPlan> listContacts = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CallPlan +
                " LEFT JOIN " + TB_Customer + " ON " +
                iCustomer.CustId + " = " + iCallPlan.CustId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCallPlan.CallPlanId,
                iCallPlan.CustId, iCallPlan.CallPlanDate, iCallPlan.CallPlanStatusId, iCallPlan.CallPlanTypeId,
                iCallPlan.CreatedBy, iCallPlan.CreatedDate, iCallPlan.SalesmanId, iCallPlan.StatusSend, iCallPlan.Notes,
                iCallPlan.CallPlanStatusName, iCallPlan.CallPlanTypeName
        };
        String selection = iCallPlan.StatusSend + " = ? ";
        String[] selectionArgs = {String.valueOf(statusSend)};
        String orderBy = iCallPlan.CallPlanDate + " ASC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts.add(new mCallPlan(cursor.getString(cursor
                            .getColumnIndexOrThrow(iCallPlan.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }


    public ArrayList<mCallPlan> getCallPlanAllJoinByDateBetween(String salesId, String dateFrom, String dateTo, int custId) {
        ArrayList<mCallPlan> listContacts = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CallPlan +
                " LEFT JOIN " + TB_Customer + " ON " +
                iCustomer.CustId + " = " + iCallPlan.CustId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCallPlan.CallPlanId,
                iCallPlan.CustId, iCallPlan.CallPlanDate, iCallPlan.CallPlanStatusId, iCallPlan.CallPlanTypeId,
                iCallPlan.CreatedBy, iCallPlan.CreatedDate, iCallPlan.SalesmanId, iCallPlan.StatusSend, iCallPlan.Notes,
                iCallPlan.CallPlanStatusName, iCallPlan.CallPlanTypeName
        };
        String selection;
        String[] selectionArgs = null;
        if (custId == 0) {
            selection = iCallPlan.SalesmanId + " = ? AND " + iCallPlan.CallPlanDate + " >= ?  And " + iCallPlan.CallPlanDate + "<=?";
            String[] selectionA = {salesId, dateFrom, dateTo};
            selectionArgs = selectionA;
        } else {
            selection = iCustomer.CustId + "=? AND " + iCallPlan.SalesmanId + " = ? AND " + iCallPlan.CallPlanDate + " >= ?  And " + iCallPlan.CallPlanDate + "<=?";
            String[] selectionA = {String.valueOf(custId), salesId, dateFrom, dateTo};
            selectionArgs = selectionA;
        }
        String orderBy = iCallPlan.CallPlanDate + " DESC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts.add(new mCallPlan(cursor.getString(cursor
                            .getColumnIndexOrThrow(iCallPlan.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return listContacts;
    }


    public ArrayList<mCallPlan> getCallPlanAllJoinByDateBySales(String salesId, String byDate) {
        ArrayList<mCallPlan> listContacts = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CallPlan +
                " LEFT JOIN " + TB_Customer + " ON " +
                iCustomer.CustId + " = " + iCallPlan.CustId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCallPlan.CallPlanId,
                iCallPlan.CustId, iCallPlan.CallPlanDate, iCallPlan.CallPlanStatusId, iCallPlan.CallPlanTypeId,
                iCallPlan.CreatedBy, iCallPlan.CreatedDate, iCallPlan.SalesmanId, iCallPlan.StatusSend, iCallPlan.Notes,
                iCallPlan.CallPlanStatusName, iCallPlan.CallPlanTypeName
        };
        String selection = iCallPlan.SalesmanId + " = ? AND " + iCallPlan.CallPlanDate + " = ? ";
        String[] selectionArgs = {salesId, byDate};
        String orderBy = iCallPlan.CallPlanDate + " ASC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts.add(new mCallPlan(cursor.getString(cursor
                            .getColumnIndexOrThrow(iCallPlan.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanTypeName)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CallPlanStatusName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.Notes)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlan.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlan.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return listContacts;
    }

    public boolean cekCallPlanOnPending(String callPlanId, String salesId) {
        String[] projection = {iCallPlan.CallPlanId};
        String selection = iCallPlan.CallPlanId + " = ? AND " + iCallPlan.SalesmanId + "= ?  AND " + iCallPlan.StatusSend + "=?";
        String[] selectionArgs = {callPlanId, salesId, "1"};
        Cursor cursor = db.query(TB_CallPlan, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }


    public boolean updateDraftCallPlan(mCallPlan callPlan) {
        ContentValues values = new ContentValues();
        String selection = iCallPlanDraft.CallPlanId + " = ?";
        String[] selectionArgs = {callPlan.getCallPlanId()};

        values.put(iCallPlanDraft.StatusSend, callPlan.getStatusSend());

        return ((db.update(TB_CallPlanDraft, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean selectCallPlanDraftByCallPlanId(String callPlanId) {
        String[] projection = {iCallPlanDraft.CallPlanId};
        String selection = iCallPlanDraft.CallPlanId + " = ? ";
        String[] selectionArgs = {callPlanId};
        Cursor cursor = db.query(TB_CallPlanDraft, projection, selection, selectionArgs, null, null, null);
        boolean hasil;
        hasil = cursor.getCount() > 0;
        cursor.close();
        return hasil;
    }
    public boolean DeleteCallPlanDraft(String callPlanId) {
        //Log.e("isi callplandraf","cal"+callPlanId);
        String selection = iCallPlanDraft.CallPlanId + " = ?";
        String[] selectionArgs = {callPlanId};
        if(selectCallPlanDraftByCallPlanId(callPlanId)){
           // Log.e("isi callplan","clid"+ callPlanId);
            return (db.delete(TB_CallPlanDraft, selection, selectionArgs)) == 1;
        }else{
            return  true;
        }

    }

    public boolean InsertUpateAllCallPlanDraft(ArrayList<mCallPlan> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_CallPlanDraft + " " +
                        "(" + iCallPlanDraft.CallPlanId
                        + "," + iCallPlanDraft.CallPlanDate
                        + "," + iCallPlanDraft.CallPlanTypeId
                        + "," + iCallPlanDraft.SalesmanId
                        + "," + iCallPlanDraft.CustId
                        + "," + iCallPlanDraft.CallPlanStatusId
                        + "," + iCallPlanDraft.CreatedDate
                        + "," + iCallPlanDraft.CreatedBy
                        + "," + iCallPlanDraft.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);

                for (mCallPlan cu : callPlan) {
                    //  Log.e("isi call plan for", "isi callplan" + cu.getCallPlanId() + "," + cu.getCallPlanDate() + "," + cu.getCallPlanStatusId());
                    insert.bindString(1, cu.getCallPlanId());
                    insert.bindString(2, cu.getCallPlanDate());
                    insert.bindString(3, String.valueOf(cu.getCallPlanTypeId()));
                    insert.bindString(4, String.valueOf(cu.getSalesmanId()));
                    insert.bindString(5, String.valueOf(cu.getCustId()));
                    insert.bindString(6, String.valueOf(cu.getCallPlanStatusId()));
                    insert.bindString(7, cu.getCreatedDate());
                    insert.bindString(8, cu.getCreatedBy());
                    insert.bindString(9, String.valueOf(cu.getStatusSend()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi call draft ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mCallPlan> getCallPlanDraftAllJoinByDateBySales(String salesId, String byDate, int status) {
        ArrayList<mCallPlan> listContacts = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CallPlanDraft +
                " LEFT JOIN " + TB_Customer + " ON " +
                iCustomer.CustId + " = " + iCallPlanDraft.CustId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCallPlanDraft.CallPlanId,
                iCallPlanDraft.CustId, iCallPlanDraft.CallPlanDate, iCallPlanDraft.CallPlanStatusId, iCallPlanDraft.CallPlanTypeId,
                iCallPlanDraft.CreatedBy, iCallPlanDraft.CreatedDate, iCallPlanDraft.SalesmanId, iCallPlanDraft.StatusSend
        };
        String selection = iCallPlanDraft.SalesmanId + " = ? AND " + iCallPlanDraft.CallPlanDate + " = ? AND " + iCallPlanDraft.StatusSend + "=?";
        String[] selectionArgs = {salesId, byDate, String.valueOf(status)};
        String orderBy = iCallPlanDraft.CallPlanDate + " ASC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts.add(new mCallPlan(cursor.getString(cursor
                            .getColumnIndexOrThrow(iCallPlanDraft.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CallPlanDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CallPlanTypeId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CallPlanStatusId)),
                            "",
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mCallPlan> getCallPlanDraftAllJoinBySales(String salesId, String typeDraft, int status) {
        ArrayList<mCallPlan> listContacts = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_CallPlanDraft +
                " LEFT JOIN " + TB_Customer + " ON " +
                iCustomer.CustId + " = " + iCallPlanDraft.CustId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iCallPlanDraft.CallPlanId,
                iCallPlanDraft.CustId, iCallPlanDraft.CallPlanDate, iCallPlanDraft.CallPlanStatusId, iCallPlanDraft.CallPlanTypeId,
                iCallPlanDraft.CreatedBy, iCallPlanDraft.CreatedDate, iCallPlanDraft.SalesmanId, iCallPlanDraft.StatusSend
        };
        String selection = iCallPlanDraft.SalesmanId + " = ? AND " + iCallPlanDraft.CallPlanStatusId + " = ? AND " + iCallPlanDraft.StatusSend + "=?";
        String[] selectionArgs = {salesId, typeDraft, String.valueOf(status)};
        String orderBy = iCallPlanDraft.CallPlanDate + " ASC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts.add(new mCallPlan(cursor.getString(cursor
                            .getColumnIndexOrThrow(iCallPlanDraft.CallPlanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CallPlanDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CallPlanTypeId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.SalesmanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CustId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CallPlanStatusId)),
                            "",
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iCallPlanDraft.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return listContacts;
    }

    /*CallPlan Note*/
    public boolean InsertUpateAllTransCallPlanNoteWhere(ArrayList<mCallPlanNote> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");

                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TransCallPlanNote + " " +
                        "(" + iTransCallPlanNote.TransCallPlanNoteId
                        + "," + iTransCallPlanNote.BiCsTypeId
                        + "," + iTransCallPlanNote.CallPlanId
                        + "," + iTransCallPlanNote.Notes1
                        + "," + iTransCallPlanNote.Notes2
                        + "," + iTransCallPlanNote.Notes3
                        + "," + iTransCallPlanNote.CreatedDate
                        + "," + iTransCallPlanNote.CreatedBy
                        + "," + iTransCallPlanNote.ModifiedDate
                        + "," + iTransCallPlanNote.ModifiedBy
                        + "," + iTransCallPlanNote.StatusSend
                        + "," + iTransCallPlanNote.CustId
                        + "," + iTransCallPlanNote.BiCsTypeName
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mCallPlanNote cu : callPlan) {
                    if (!cekTransCallPlanNoteOnPending(cu.getCallPlanId(), cu.getCallPlanNoteId())) {
                        Log.e("msk callPlannote", cu.getCallPlanNoteId() + "," + cu.getBiCsTypeId() + "," +
                                String.valueOf(cu.getNotes1()) + "," + cu.getNotes2() + "," +
                                String.valueOf(cu.getNotes3()) + "," + cu.getCreatedDate());
                        insert.bindString(1, cu.getCallPlanNoteId());
                        insert.bindString(2, String.valueOf(cu.getBiCsTypeId()));
                        insert.bindString(3, cu.getCallPlanId());
                        insert.bindString(4, cu.getNotes1());
                        insert.bindString(5, cu.getNotes2());
                        insert.bindString(6, cu.getNotes3());
                        insert.bindString(7, cu.getCreatedDate());
                        insert.bindString(8, cu.getCreatedBy());
                        insert.bindString(9, cu.getModifiedDate());
                        insert.bindString(10, cu.getModifiedBy());
                        insert.bindString(11, String.valueOf(cu.getStatusSend()));
                        insert.bindString(12, String.valueOf(cu.getCustId()));
                        insert.bindString(13, cu.getBiCsTypeName());
                        insert.execute();
                    }

                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call note ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean cekTransCallPlanNoteOnPending(String callPlanId, String callPlanNoteId) {
        String[] projection = {iTransCallPlanNote.CallPlanId};
        String selection = iTransCallPlanNote.CallPlanId + " = ? AND " + iTransCallPlanNote.TransCallPlanNoteId + "= ?  AND " + iTransCallPlanNote.StatusSend + "=?";
        String[] selectionArgs = {callPlanId, callPlanNoteId, "1"};
        Cursor cursor = db.query(TB_TransCallPlanNote, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public boolean updateTransCallNoteStatus(String transNoteId, String callplanId, int status) {
        ContentValues values = new ContentValues();
        String selection = iTransCallPlanNote.TransCallPlanNoteId + " = ? AND " + iTransCallPlanNote.CallPlanId + "=?";
        String[] selectionArgs = {transNoteId, callplanId};

        //values.put(iSession.nama, session.getNama());
        values.put(iTransCallPlanNote.StatusSend, status);

        return ((db.update(TB_TransCallPlanNote, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public ArrayList<mCallPlanNote> getTransCallPlanNoteByStatus(String status) {

        ArrayList<mCallPlanNote> cList = new ArrayList<>();
        try {
            String[] projection = {iTransCallPlanNote.TransCallPlanNoteId, iTransCallPlanNote.BiCsTypeId, iTransCallPlanNote.CallPlanId, iTransCallPlanNote.CustId,
                    iTransCallPlanNote.Notes1, iTransCallPlanNote.Notes2, iTransCallPlanNote.Notes3, iTransCallPlanNote.CreatedDate, iTransCallPlanNote.BiCsTypeName,
                    iTransCallPlanNote.CreatedBy, iTransCallPlanNote.ModifiedDate, iTransCallPlanNote.ModifiedBy, iTransCallPlanNote.StatusSend
            };
            String selection = iTransCallPlanNote.StatusSend + " = ? ";
            String[] selectionArgs = {status};
            String orderby = iTransCallPlanNote.TransCallPlanNoteId;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_TransCallPlanNote, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        cList.add(new mCallPlanNote(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanNote.TransCallPlanNoteId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.BiCsTypeId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.BiCsTypeName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.CallPlanId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.Notes1)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.Notes2)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.Notes3)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.ModifiedBy)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanNote.StatusSend))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }


        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mCallPlanNote> getTransCallPlanNoteByDateBetween(String salesId, String dateFrom, String dateTo, int custId) {
        ArrayList<mCallPlanNote> cList = new ArrayList<>();
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
        _QB.setTables(TB_TransCallPlanNote +
                " LEFT JOIN " + TB_Customer + " ON " +
                iTransCallPlanNote.CustId + " = " + iCustomer.CustId +
                " LEFT JOIN " + TB_BiCsType + " ON " +
                iTransCallPlanNote.BiCsTypeId + " = " + iBiCsType.BiCsTypeId);
        String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iBiCsType.BiCsTypeName,
                iTransCallPlanNote.TransCallPlanNoteId, iTransCallPlanNote.BiCsTypeId, iTransCallPlanNote.CallPlanId, iTransCallPlanNote.CustId,
                iTransCallPlanNote.Notes1, iTransCallPlanNote.Notes2, iTransCallPlanNote.Notes3, iTransCallPlanNote.CreatedDate, iTransCallPlanNote.BiCsTypeName,
                iTransCallPlanNote.CreatedBy, iTransCallPlanNote.ModifiedDate, iTransCallPlanNote.ModifiedBy, iTransCallPlanNote.StatusSend
        };

        String selection;
        String[] selectionArgs = null;
        if (custId == 0) {
            selection = iCustomer.SalesmanId + " = ? AND  substr(trim(" + iTransCallPlanNote.CreatedDate + "),1,10)  Between  ?  And ? ";
            String[] selectionA = {salesId, dateFrom, dateTo};
            selectionArgs = selectionA;
        } else {
            selection = iCustomer.CustId + "=?  AND " + iCustomer.SalesmanId + " = ? AND  substr(trim(" + iTransCallPlanNote.CreatedDate + "),1,10)  Between  ?  And ? ";
            String[] selectionA = {String.valueOf(custId), salesId, dateFrom, dateTo};
            selectionArgs = selectionA;
        }

        String orderBy = iTransCallPlanNote.CreatedDate + " DESC," + iCustomer.CustName + " ASC," + iCustomer.AliasName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    cList.add(new mCallPlanNote(cursor.getString(cursor
                            .getColumnIndexOrThrow(iTransCallPlanNote.TransCallPlanNoteId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.BiCsTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.BiCsTypeName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.CallPlanId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.CustId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.Notes1)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.Notes2)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.Notes3)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.CreatedBy)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.ModifiedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.ModifiedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTransCallPlanNote.StatusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.CustName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.AliasName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iCustomer.Address)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iBiCsType.BiCsTypeName))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return cList;
    }

    /*transcall callplan demo */
    public boolean InsertUpateAllTransCallPlanDemoWhere(ArrayList<mDemo> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TransCallPlanDemo + " " +
                        "(" + iTransCallPlanDemo.DemoId
                        + "," + iTransCallPlanDemo.CallPlanId
                        + "," + iTransCallPlanDemo.CustId
                        + "," + iTransCallPlanDemo.DemoTitle
                        + "," + iTransCallPlanDemo.DemoDescription
                        + "," + iTransCallPlanDemo.DemoPeserta
                        + "," + iTransCallPlanDemo.DemoStatusId
                        + "," + iTransCallPlanDemo.DemoDate
                        + "," + iTransCallPlanDemo.DemoResponse
                        + "," + iTransCallPlanDemo.CreatedDate
                        + "," + iTransCallPlanDemo.CreatedBy
                        + "," + iTransCallPlanDemo.ResponseDate
                        + "," + iTransCallPlanDemo.ResponseBy
                        + "," + iTransCallPlanDemo.ModifiedDate
                        + "," + iTransCallPlanDemo.ModifiedBy
                        + "," + iTransCallPlanDemo.StatusSend
                        + "," + iTransCallPlanDemo.DemoStatusName
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mDemo cu : callPlan) {
                    if (!cekTransCallPlanDemoOnPending(cu.getCallPlanId(), cu.getDemoId())) {
                        Log.e("masuk isert demo", cu.getDemoId() + "," + cu.getCallPlanId() + "," +
                                String.valueOf(cu.getDemoTitle()) + "," + cu.getDemoDescription() + "," +
                                String.valueOf(cu.getDemoPeserta()) + "," + cu.getCreatedDate());
                        insert.bindString(1, cu.getDemoId());
                        insert.bindString(2, cu.getCallPlanId());
                        insert.bindString(3, cu.getCustId());
                        insert.bindString(4, cu.getDemoTitle());
                        insert.bindString(5, cu.getDemoDescription());
                        insert.bindString(6, String.valueOf(cu.getDemoPeserta()));
                        insert.bindString(7, String.valueOf(cu.getDemoStatusId()));
                        insert.bindString(8, cu.getDemoDate());
                        insert.bindString(9, cu.getDemoResponse());
                        insert.bindString(10, cu.getCreatedDate());
                        insert.bindString(11, cu.getCreatedBy());
                        insert.bindString(12, cu.getResponseDate());
                        insert.bindString(13, cu.getResponseBy());
                        insert.bindString(14, cu.getModifiedDate());
                        insert.bindString(15, cu.getModifiedBy());
                        insert.bindString(16, String.valueOf(cu.getStatusSend()));
                        insert.bindString(17, cu.getDemoStatusName());
                        insert.execute();
                    }

                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call demo ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean cekTransCallPlanDemoOnPending(String callPlanId, String demoId) {
        String[] projection = {iTransCallPlanDemo.CallPlanId};
        String selection = iTransCallPlanDemo.CallPlanId + " = ? AND " + iTransCallPlanDemo.DemoId + "= ?  AND " + iTransCallPlanDemo.StatusSend + "=?";
        String[] selectionArgs = {callPlanId, demoId, "1"};
        Cursor cursor = db.query(TB_TransCallPlanDemo, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public boolean updateTransCallDemoStatus(String demoId, String callplanId, int status) {
        ContentValues values = new ContentValues();
        String selection = iTransCallPlanDemo.DemoId + " = ? AND " + iTransCallPlanDemo.CallPlanId + "=?";
        String[] selectionArgs = {demoId, callplanId};

        //values.put(iSession.nama, session.getNama());
        values.put(iTransCallPlanDemo.StatusSend, status);

        return ((db.update(TB_TransCallPlanDemo, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public ArrayList<mDemo> getTransCallPlanDemoByStatus(String status) {

        ArrayList<mDemo> cList = new ArrayList<>();
        try {
            String[] projection = {iTransCallPlanDemo.DemoId, iTransCallPlanDemo.DemoTitle, iTransCallPlanDemo.CallPlanId, iTransCallPlanDemo.CustId, iTransCallPlanDemo.DemoStatusName,
                    iTransCallPlanDemo.DemoDescription, iTransCallPlanDemo.DemoPeserta, iTransCallPlanDemo.DemoStatusId, iTransCallPlanDemo.DemoDate,
                    iTransCallPlanDemo.CreatedDate, iTransCallPlanDemo.CreatedBy, iTransCallPlanDemo.ResponseDate, iTransCallPlanDemo.ResponseBy,
                    iTransCallPlanDemo.DemoResponse, iTransCallPlanDemo.ModifiedDate, iTransCallPlanDemo.ModifiedBy, iTransCallPlanDemo.StatusSend
            };
            String selection = iTransCallPlanDemo.StatusSend + " = ? ";
            String[] selectionArgs = {status};
            String orderby = iTransCallPlanDemo.DemoId;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_TransCallPlanDemo, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        cList.add(new mDemo(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanDemo.DemoId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CallPlanId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoTitle)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoDescription)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoPeserta)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoStatusName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoResponse)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ResponseBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ModifiedBy)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.StatusSend))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mDemo> getTransCallPlanDemoBetween(String salesId, String dateFrom, String dateTo, int custId) {

        ArrayList<mDemo> cList = new ArrayList<>();
        try {
            SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
            _QB.setTables(TB_TransCallPlanDemo +
                    " INNER JOIN " + TB_Customer + " ON " +
                    iCustomer.CustId + " = " + iTransCallPlanDemo.CustId
            );
            String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address,
                    iTransCallPlanDemo.DemoId, iTransCallPlanDemo.DemoTitle, iTransCallPlanDemo.CallPlanId, iTransCallPlanDemo.CustId, iTransCallPlanDemo.DemoStatusName,
                    iTransCallPlanDemo.DemoDescription, iTransCallPlanDemo.DemoPeserta, iTransCallPlanDemo.DemoStatusId, iTransCallPlanDemo.DemoDate,
                    iTransCallPlanDemo.CreatedDate, iTransCallPlanDemo.CreatedBy, iTransCallPlanDemo.ResponseDate, iTransCallPlanDemo.ResponseBy,
                    iTransCallPlanDemo.DemoResponse, iTransCallPlanDemo.ModifiedDate, iTransCallPlanDemo.ModifiedBy, iTransCallPlanDemo.StatusSend
            };
            String selection;
            String[] selectionArgs = null;
            if (custId == 0) {
                selection = iCustomer.SalesmanId + " = ? AND substr(trim(" + iTransCallPlanDemo.DemoDate + "),1,10)  Between ? AND ? ";
                String[] selectionA = {salesId, dateFrom, dateTo};
                selectionArgs = selectionA;
            } else {
                selection = iCustomer.CustId + "=?  AND " + iCustomer.SalesmanId + " = ? AND substr(trim(" + iTransCallPlanDemo.DemoDate + "),1,10)  Between ? AND ? ";
                String[] selectionA = {String.valueOf(custId), salesId, dateFrom, dateTo};
                selectionArgs = selectionA;
            }
            String orderBy = iTransCallPlanDemo.DemoDate + " Desc";
            @SuppressLint("Recycle")
            Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        //boolean is = cursor.getInt(cursor
                        //        .getColumnIndexOrThrow(iTransCallPlanComplain.SafetyFood)) > 0;
                        //Log.e("isi safety", "sa" + cursor.getString(cursor.getColumnIndexOrThrow(iTransCallPlanComplain.CreatedDate)) + ",");
                        cList.add(new mDemo(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanDemo.DemoId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CallPlanId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoTitle)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoDescription)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoPeserta)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoStatusName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.DemoResponse)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ResponseBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.ModifiedBy)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanDemo.StatusSend)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iCustomer.CustName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iCustomer.AliasName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iCustomer.Address))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err complain", e.getMessage());
        }
        return cList;
    }

    /*transcallplan complain*/
    public boolean InsertUpateAllTransCallPlanComplainWhere(ArrayList<mComplain> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TransCallPlanComplain + " " +
                        "(" + iTransCallPlanComplain.ComplainId
                        + "," + iTransCallPlanComplain.SafetyFood
                        + "," + iTransCallPlanComplain.QualityFood
                        + "," + iTransCallPlanComplain.QualityApplication
                        + "," + iTransCallPlanComplain.QuantityAll
                        + "," + iTransCallPlanComplain.PackagingAll
                        + "," + iTransCallPlanComplain.CallPlanId
                        + "," + iTransCallPlanComplain.CustId
                        + "," + iTransCallPlanComplain.ProductId
                        + "," + iTransCallPlanComplain.ProductName
                        + "," + iTransCallPlanComplain.ComplainStatusId
                        + "," + iTransCallPlanComplain.ComplainStatusName
                        + "," + iTransCallPlanComplain.SampleSendDate
                        + "," + iTransCallPlanComplain.CustPic
                        + "," + iTransCallPlanComplain.CustPicJabatan
                        + "," + iTransCallPlanComplain.CustPicHp
                        + "," + iTransCallPlanComplain.ComplainNote
                        + "," + iTransCallPlanComplain.ComplainPriority
                        + "," + iTransCallPlanComplain.ComplainResponse
                        + "," + iTransCallPlanComplain.ComplainResponseDate
                        + "," + iTransCallPlanComplain.ComplainResponseBy
                        + "," + iTransCallPlanComplain.CreatedDate
                        + "," + iTransCallPlanComplain.CreatedBy
                        + "," + iTransCallPlanComplain.ModifiedDate
                        + "," + iTransCallPlanComplain.ModifiedBy
                        + "," + iTransCallPlanComplain.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mComplain cu : callPlan) {
                    if (!cekTransCallPlanComplainOnPending(cu.getCallPlanId(), cu.getComplainId())) {
                        Log.e("masuk isert complain", cu.getComplainId() + "," + cu.isSafetyFood() + "," +
                                String.valueOf(cu.isQualityFood()) + "," + cu.isQualityApplication() + "," +
                                String.valueOf(cu.isQuantityAll()) + "," + cu.getCreatedDate());
                        insert.bindString(1, cu.getComplainId());
                        insert.bindString(2, String.valueOf(cu.isSafetyFood() ? 1 : 0));
                        insert.bindString(3, String.valueOf(cu.isQualityFood() ? 1 : 0));
                        insert.bindString(4, String.valueOf(cu.isQualityApplication() ? 1 : 0));
                        insert.bindString(5, String.valueOf(cu.isQuantityAll() ? 1 : 0));
                        insert.bindString(6, String.valueOf(cu.isPackagingAll() ? 1 : 0));
                        insert.bindString(7, cu.getCallPlanId());
                        insert.bindString(8, cu.getCustId());
                        insert.bindString(9, cu.getProductId());
                        insert.bindString(10, cu.getProductName());
                        insert.bindString(11, String.valueOf(cu.getComplainStatusId()));
                        insert.bindString(12, cu.getComplainStatusName());
                        insert.bindString(13, cu.getSampleSendDate());
                        insert.bindString(14, cu.getCustPic());
                        insert.bindString(15, cu.getCustPicJabatan());
                        insert.bindString(16, cu.getCustPicHp());
                        insert.bindString(17, cu.getComplainNote());
                        insert.bindString(18, cu.getComplainPriority());
                        insert.bindString(19, cu.getComplainResponse());
                        insert.bindString(20, cu.getComplainResponseDate());
                        insert.bindString(21, cu.getComplainResponseBy());
                        insert.bindString(22, cu.getCreatedDate());
                        insert.bindString(23, cu.getCreatedBy());
                        insert.bindString(24, cu.getModifiedDate());
                        insert.bindString(25, cu.getModifiedBy());
                        insert.bindString(26, String.valueOf(cu.getStatusSend()));
                        insert.execute();
                    }

                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call complain ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean cekTransCallPlanComplainOnPending(String callPlanId, String complainId) {
        String[] projection = {iTransCallPlanComplain.CallPlanId};
        String selection = iTransCallPlanComplain.CallPlanId + " = ? AND " + iTransCallPlanComplain.ComplainId + "= ?  AND " + iTransCallPlanComplain.StatusSend + "=?";
        String[] selectionArgs = {callPlanId, complainId, "1"};
        Cursor cursor = db.query(TB_TransCallPlanComplain, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public boolean updateTransCallComplainStatus(String complainId, String callplanId, int status) {
        ContentValues values = new ContentValues();
        String selection = iTransCallPlanComplain.ComplainId + " = ? AND " + iTransCallPlanComplain.CallPlanId + "=?";
        String[] selectionArgs = {complainId, callplanId};

        //values.put(iSession.nama, session.getNama());
        values.put(iTransCallPlanComplain.StatusSend, status);

        return ((db.update(TB_TransCallPlanComplain, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public ArrayList<mComplain> getTransCallPlanComplainBetween(String salesId, String dateFrom, String dateTo, int custId) {

        ArrayList<mComplain> cList = new ArrayList<>();
        try {
            SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
            _QB.setTables(TB_TransCallPlanComplain +
                    " INNER JOIN " + TB_Customer + " ON " +
                    iCustomer.CustId + " = " + iTransCallPlanComplain.CustId +
                    " LEFT JOIN " + TB_Produk + " ON " +
                    iTransCallPlanComplain.ProductId + " = " + iProduct.ProductId
            );
            String[] projection = {iCustomer.CustName, iCustomer.AliasName, iCustomer.Address, iProduct.ProductName,
                    iTransCallPlanComplain.ComplainId, iTransCallPlanComplain.SafetyFood, iTransCallPlanComplain.QualityFood, iTransCallPlanComplain.QualityApplication,
                    iTransCallPlanComplain.QuantityAll, iTransCallPlanComplain.PackagingAll, iTransCallPlanComplain.CallPlanId, iTransCallPlanComplain.CustId, iTransCallPlanComplain.ProductId,
                    iTransCallPlanComplain.ProductName, iTransCallPlanComplain.ComplainStatusId, iTransCallPlanComplain.ComplainStatusName,
                    iTransCallPlanComplain.SampleSendDate, iTransCallPlanComplain.ComplainNote, iTransCallPlanComplain.ComplainPriority,
                    iTransCallPlanComplain.ComplainResponse, iTransCallPlanComplain.ComplainResponseDate, iTransCallPlanComplain.CustPic,
                    iTransCallPlanComplain.CustPicJabatan, iTransCallPlanComplain.CustPicHp, iTransCallPlanComplain.ComplainResponseBy,
                    iTransCallPlanComplain.CreatedDate, iTransCallPlanComplain.CreatedBy, iTransCallPlanComplain.ModifiedDate, iTransCallPlanComplain.ModifiedBy, iTransCallPlanComplain.StatusSend
            };
            String selection;
            String[] selectionArgs = null;
            if (custId == 0) {
                selection = iCustomer.SalesmanId + " = ? AND substr(trim(" + iTransCallPlanComplain.CreatedDate + "),1,10)  Between ? AND ? ";
                String[] selectionA = {salesId, dateFrom, dateTo};
                selectionArgs = selectionA;
            } else {
                selection = iCustomer.CustId + "=?  AND " + iCustomer.SalesmanId + " = ? AND substr(trim(" + iTransCallPlanComplain.CreatedDate + "),1,10)  Between ? AND ? ";
                String[] selectionA = {String.valueOf(custId), salesId, dateFrom, dateTo};
                selectionArgs = selectionA;
            }
            String orderBy = iTransCallPlanComplain.ComplainId + " Desc";
            @SuppressLint("Recycle")
            Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderBy);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        //boolean is = cursor.getInt(cursor
                        //        .getColumnIndexOrThrow(iTransCallPlanComplain.SafetyFood)) > 0;
                        Log.e("isi safety", "sa" + cursor.getString(cursor.getColumnIndexOrThrow(iTransCallPlanComplain.CreatedDate)) + ",");
                        cList.add(new mComplain(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.SafetyFood)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.QualityFood)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.QualityApplication)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.QuantityAll)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.PackagingAll)) > 0,
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CallPlanId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iProduct.ProductName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ProductName)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainStatusName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.SampleSendDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustPic)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustPicJabatan)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustPicHp)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainNote)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainPriority)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainResponse)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainResponseBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ModifiedBy)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.StatusSend)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iCustomer.CustName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iCustomer.AliasName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iCustomer.Address))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err complain", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mComplain> getTransCallPlanComplainByStatus(String status) {

        ArrayList<mComplain> cList = new ArrayList<>();
        try {
            String[] projection = {iTransCallPlanComplain.ComplainId, iTransCallPlanComplain.SafetyFood, iTransCallPlanComplain.QualityFood, iTransCallPlanComplain.QualityApplication,
                    iTransCallPlanComplain.QuantityAll, iTransCallPlanComplain.PackagingAll, iTransCallPlanComplain.CallPlanId, iTransCallPlanComplain.CustId, iTransCallPlanComplain.ProductId,
                    iTransCallPlanComplain.ProductName, iTransCallPlanComplain.ComplainStatusId, iTransCallPlanComplain.ComplainStatusName,
                    iTransCallPlanComplain.SampleSendDate, iTransCallPlanComplain.ComplainNote, iTransCallPlanComplain.ComplainPriority,
                    iTransCallPlanComplain.ComplainResponse, iTransCallPlanComplain.ComplainResponseDate, iTransCallPlanComplain.CustPic,
                    iTransCallPlanComplain.CustPicJabatan, iTransCallPlanComplain.CustPicHp, iTransCallPlanComplain.ComplainResponseBy,
                    iTransCallPlanComplain.CreatedDate, iTransCallPlanComplain.CreatedBy, iTransCallPlanComplain.ModifiedDate, iTransCallPlanComplain.ModifiedBy, iTransCallPlanComplain.StatusSend
            };
            String selection = iTransCallPlanComplain.StatusSend + " = ? ";
            String[] selectionArgs = {status};
            String orderby = iTransCallPlanComplain.ComplainId;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_TransCallPlanComplain, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        boolean is = cursor.getInt(cursor
                                .getColumnIndexOrThrow(iTransCallPlanComplain.SafetyFood)) > 0;
                        // Log.e("isi safety", "sa" + cursor.getInt(cursor.getColumnIndexOrThrow(iTransCallPlanComplain.SafetyFood)) + "," + is);
                        cList.add(new mComplain(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.SafetyFood)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.QualityFood)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.QualityApplication)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.QuantityAll)) > 0,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.PackagingAll)) > 0,
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CallPlanId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ProductName)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainStatusName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.SampleSendDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustPic)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustPicJabatan)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CustPicHp)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainNote)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainPriority)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainResponse)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ComplainResponseBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.ModifiedBy)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanComplain.StatusSend))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err complain", e.getMessage());
        }
        return cList;
    }

    /*transcallplan sample*/
    public boolean InsertUpateAllTransCallPlanSampleWhere(ArrayList<mSample> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TransCallPlanSample + " " +
                        "(" + iTransCallPlanSample.SampleId
                        + "," + iTransCallPlanSample.CallPlanId
                        + "," + iTransCallPlanSample.CustId
                        + "," + iTransCallPlanSample.SampleFor
                        + "," + iTransCallPlanSample.SampleDate
                        + "," + iTransCallPlanSample.SampleStatusId
                        + "," + iTransCallPlanSample.SampleStatus
                        + "," + iTransCallPlanSample.SampleReceivedDate
                        + "," + iTransCallPlanSample.CustPic
                        + "," + iTransCallPlanSample.CustPicJabatan
                        + "," + iTransCallPlanSample.CustPicHp
                        + "," + iTransCallPlanSample.Note
                        + "," + iTransCallPlanSample.SampleResponseDate
                        + "," + iTransCallPlanSample.SampleResponseNote
                        + "," + iTransCallPlanSample.CreatedDate
                        + "," + iTransCallPlanSample.CreatedBy
                        + "," + iTransCallPlanSample.ModifiedDate
                        + "," + iTransCallPlanSample.ModifiedBy
                        + "," + iTransCallPlanSample.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mSample cu : callPlan) {
                    // Log.e("isi sample jumlah ", cu.getSampleStatusId() + "," + cu.getProductOfRequest().size() + "," + cu.getProductOfRealisasi().size());
                    if (cu.getSampleStatusId() < 2) {
                        if (cu.getProductOfRequest() != null && cu.getProductOfRequest().size() > 0) {
                            InsertUpateAllTransCallPlanSampleProduct(cu.getProductOfRequest());
                        }
                    } else if (cu.getSampleStatusId() < 3) {
                        if (cu.getProductOfRealisasi() != null && cu.getProductOfRealisasi().size() > 0) {
                            InsertUpateAllTransCallPlanSampleProduct(cu.getProductOfRealisasi());
                        }
                    }

                    //if (!cekTransCallPlanSampleOnPending(cu.getCallPlanId(), cu.getSampleId())) {
                       /* Log.e("masuk isert demo", cu.getComplainId() + "," + cu.isSafetyFood() + "," +
                                String.valueOf(cu.isQualityFood()) + "," + cu.isQualityApplication() + "," +
                                String.valueOf(cu.isQuantityAll()) + "," + cu.getCreatedDate());*/
                    insert.bindString(1, cu.getSampleId());
                    insert.bindString(2, cu.getCallPlanId());
                    insert.bindString(3, String.valueOf(cu.getCustId()));
                    insert.bindString(4, cu.getSampleFor());
                    insert.bindString(5, cu.getSampleDate());
                    insert.bindString(6, String.valueOf(cu.getSampleStatusId()));
                    insert.bindString(7, cu.getSampleStatus());
                    insert.bindString(8, cu.getSampleReceivedDate());
                    insert.bindString(9, cu.getCustPic());
                    insert.bindString(10, cu.getCustPicJabatan());
                    insert.bindString(11, cu.getCustPicHp());
                    insert.bindString(12, cu.getNote());
                    insert.bindString(13, cu.getSampleResponseDate());
                    insert.bindString(14, cu.getSampleResponseNote());
                    insert.bindString(15, cu.getCreatedDate());
                    insert.bindString(16, cu.getCreatedBy());
                    insert.bindString(17, cu.getModifiedDate());
                    insert.bindString(18, cu.getModifiedBy());
                    insert.bindString(19, String.valueOf(cu.getStatusSend()));
                    insert.execute();
                    //}

                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call sample ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean InsertUpateAllTransCallPlanSampleFromServer(ArrayList<mSample> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TransCallPlanSample + " " +
                        "(" + iTransCallPlanSample.SampleId
                        + "," + iTransCallPlanSample.CallPlanId
                        + "," + iTransCallPlanSample.CustId
                        + "," + iTransCallPlanSample.SampleFor
                        + "," + iTransCallPlanSample.SampleDate
                        + "," + iTransCallPlanSample.SampleStatusId
                        + "," + iTransCallPlanSample.SampleStatus
                        + "," + iTransCallPlanSample.SampleReceivedDate
                        + "," + iTransCallPlanSample.CustPic
                        + "," + iTransCallPlanSample.CustPicJabatan
                        + "," + iTransCallPlanSample.CustPicHp
                        + "," + iTransCallPlanSample.Note
                        + "," + iTransCallPlanSample.SampleResponseDate
                        + "," + iTransCallPlanSample.SampleResponseNote
                        + "," + iTransCallPlanSample.CreatedDate
                        + "," + iTransCallPlanSample.CreatedBy
                        + "," + iTransCallPlanSample.ModifiedDate
                        + "," + iTransCallPlanSample.ModifiedBy
                        + "," + iTransCallPlanSample.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mSample cu : callPlan) {
                    // Log.e("isi sample jumlah ", cu.getSampleStatusId() + "," + cu.getProductOfRequest().size() + "," + cu.getProductOfRealisasi().size());

                    if (cu.getProductOfRequest() != null && cu.getProductOfRequest().size() > 0) {
                        InsertUpateAllTransCallPlanSampleProduct(cu.getProductOfRequest());
                    }

                    if (cu.getProductOfRealisasi() != null && cu.getProductOfRealisasi().size() > 0) {
                        InsertUpateAllTransCallPlanSampleProduct(cu.getProductOfRealisasi());
                    }


                    //if (!cekTransCallPlanSampleOnPending(cu.getCallPlanId(), cu.getSampleId())) {
                       /* Log.e("masuk isert demo", cu.getComplainId() + "," + cu.isSafetyFood() + "," +
                                String.valueOf(cu.isQualityFood()) + "," + cu.isQualityApplication() + "," +
                                String.valueOf(cu.isQuantityAll()) + "," + cu.getCreatedDate());*/
                    insert.bindString(1, cu.getSampleId());
                    insert.bindString(2, cu.getCallPlanId());
                    insert.bindString(3, String.valueOf(cu.getCustId()));
                    insert.bindString(4, cu.getSampleFor());
                    insert.bindString(5, cu.getSampleDate());
                    insert.bindString(6, String.valueOf(cu.getSampleStatusId()));
                    insert.bindString(7, cu.getSampleStatus());
                    insert.bindString(8, cu.getSampleReceivedDate());
                    insert.bindString(9, cu.getCustPic());
                    insert.bindString(10, cu.getCustPicJabatan());
                    insert.bindString(11, cu.getCustPicHp());
                    insert.bindString(12, cu.getNote());
                    insert.bindString(13, cu.getSampleResponseDate());
                    insert.bindString(14, cu.getSampleResponseNote());
                    insert.bindString(15, cu.getCreatedDate());
                    insert.bindString(16, cu.getCreatedBy());
                    insert.bindString(17, cu.getModifiedDate());
                    insert.bindString(18, cu.getModifiedBy());
                    insert.bindString(19, String.valueOf(cu.getStatusSend()));
                    insert.execute();
                    //}

                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call sample ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean cekTransCallPlanSampleOnPending(String callPlanId, String sampleId) {
        String[] projection = {iTransCallPlanSample.CallPlanId};
        String selection = iTransCallPlanSample.CallPlanId + " = ? AND " + iTransCallPlanSample.SampleId + "= ?  AND " + iTransCallPlanSample.StatusSend + "=?";
        String[] selectionArgs = {callPlanId, sampleId, "1"};
        Cursor cursor = db.query(TB_TransCallPlanSample, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public boolean updateTransCallSampleStatus(String sampleId, String callplanId, int status) {
        ContentValues values = new ContentValues();
        String selection = iTransCallPlanSample.SampleId + " = ? AND " + iTransCallPlanSample.CallPlanId + "=?";
        String[] selectionArgs = {sampleId, callplanId};

        //values.put(iSession.nama, session.getNama());
        values.put(iTransCallPlanSample.StatusSend, status);

        return ((db.update(TB_TransCallPlanSample, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public ArrayList<mSample> getTransCallPlanSampleNotFinish(String status, String custId) {

        ArrayList<mSample> cList = new ArrayList<>();
        try {
            String[] projection = {
                    iTransCallPlanSample.SampleId, iTransCallPlanSample.CallPlanId, iTransCallPlanSample.CustId
                    , iTransCallPlanSample.SampleFor, iTransCallPlanSample.SampleDate, iTransCallPlanSample.SampleStatusId
                    , iTransCallPlanSample.SampleStatus, iTransCallPlanSample.SampleReceivedDate, iTransCallPlanSample.CustPic
                    , iTransCallPlanSample.CustPicJabatan, iTransCallPlanSample.CustPicHp, iTransCallPlanSample.Note
                    , iTransCallPlanSample.SampleResponseDate, iTransCallPlanSample.SampleResponseNote, iTransCallPlanSample.CreatedDate
                    , iTransCallPlanSample.CreatedBy, iTransCallPlanSample.ModifiedDate, iTransCallPlanSample.ModifiedBy, iTransCallPlanSample.StatusSend
            };
            String selection = iTransCallPlanSample.SampleStatusId + " < ?  AND " + iTransCallPlanSample.CustId + "=?";
            String[] selectionArgs = {status, custId};
            String orderby = iTransCallPlanSample.SampleId;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_TransCallPlanSample, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ArrayList<mSample.mProductSample> productSamplesRequest = getTransCallPlanSampleProdukBySample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)), "request");
                        ArrayList<mSample.mProductSample> productSamplesRealisasi = getTransCallPlanSampleProdukBySample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)), "realisasi");
                        mCustomer customer = getCustomerByCustomerId(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.CustId)));
                        cList.add(new mSample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CallPlanId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleFor)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleDate)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleStatus)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleReceivedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPic)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPicJabatan)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPicHp)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.Note)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleResponseNote)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.ModifiedBy)),
                                productSamplesRequest, productSamplesRealisasi, customer,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.StatusSend))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mSample> getTransCallPlanSampleByStatus(String status) {

        ArrayList<mSample> cList = new ArrayList<>();
        try {
            String[] projection = {
                    iTransCallPlanSample.SampleId, iTransCallPlanSample.CallPlanId, iTransCallPlanSample.CustId
                    , iTransCallPlanSample.SampleFor, iTransCallPlanSample.SampleDate, iTransCallPlanSample.SampleStatusId
                    , iTransCallPlanSample.SampleStatus, iTransCallPlanSample.SampleReceivedDate, iTransCallPlanSample.CustPic
                    , iTransCallPlanSample.CustPicJabatan, iTransCallPlanSample.CustPicHp, iTransCallPlanSample.Note
                    , iTransCallPlanSample.SampleResponseDate, iTransCallPlanSample.SampleResponseNote, iTransCallPlanSample.CreatedDate
                    , iTransCallPlanSample.CreatedBy, iTransCallPlanSample.ModifiedDate, iTransCallPlanSample.ModifiedBy, iTransCallPlanSample.StatusSend
            };
            String selection = iTransCallPlanSample.StatusSend + " = ? ";
            String[] selectionArgs = {status};
            String orderby = iTransCallPlanSample.SampleId;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_TransCallPlanSample, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ArrayList<mSample.mProductSample> productSamplesRequest = getTransCallPlanSampleProdukBySample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)), "request");
                        ArrayList<mSample.mProductSample> productSamplesRealisasi = getTransCallPlanSampleProdukBySample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)), "realisasi");
                        cList.add(new mSample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CallPlanId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleFor)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleDate)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleStatus)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleReceivedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPic)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPicJabatan)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPicHp)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.Note)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleResponseNote)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.ModifiedBy)),
                                productSamplesRequest, productSamplesRealisasi,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.StatusSend))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample", e.getMessage());
        }
        return cList;
    }

    public ArrayList<mSample> getAllCallPlanSampleBetween(String salesId, String dateFrom, String dateTo, int custId) {

        ArrayList<mSample> cList = new ArrayList<>();
        try {
            SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();
            _QB.setTables(TB_TransCallPlanSample +
                    " INNER JOIN " + TB_Customer + " ON " +
                    iCustomer.CustId + " = " + iTransCallPlanSample.CustId
            );
            String[] projection = {
                    iTransCallPlanSample.SampleId, iTransCallPlanSample.CallPlanId, iTransCallPlanSample.CustId
                    , iTransCallPlanSample.SampleFor, iTransCallPlanSample.SampleDate, iTransCallPlanSample.SampleStatusId
                    , iTransCallPlanSample.SampleStatus, iTransCallPlanSample.SampleReceivedDate, iTransCallPlanSample.CustPic
                    , iTransCallPlanSample.CustPicJabatan, iTransCallPlanSample.CustPicHp, iTransCallPlanSample.Note
                    , iTransCallPlanSample.SampleResponseDate, iTransCallPlanSample.SampleResponseNote, iTransCallPlanSample.CreatedDate
                    , iTransCallPlanSample.CreatedBy, iTransCallPlanSample.ModifiedDate, iTransCallPlanSample.ModifiedBy, iTransCallPlanSample.StatusSend
            };
            String selection;
            String[] selectionArgs = null;
            if (custId == 0) {
                selection = iCustomer.SalesmanId + "= ? AND " + iTransCallPlanSample.SampleDate + " Between ? and ? ";
                String[] selectionA = {salesId, dateFrom, dateTo};
                selectionArgs = selectionA;
            } else {
                selection = iCustomer.CustId + "=?  AND " + iCustomer.SalesmanId + "= ? AND " + iTransCallPlanSample.SampleDate + " Between ? and ? ";
                String[] selectionA = {String.valueOf(custId), salesId, dateFrom, dateTo};
                selectionArgs = selectionA;
            }
            String orderby = iTransCallPlanSample.SampleDate + " Desc";
            @SuppressLint("Recycle")
            Cursor cursor = _QB.query(db, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ArrayList<mSample.mProductSample> productSamplesRequest = getTransCallPlanSampleProdukBySample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)), "request");
                        ArrayList<mSample.mProductSample> productSamplesRealisasi = getTransCallPlanSampleProdukBySample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)), "realisasi");
                        mCustomer customer = getCustomerByCustomerId(cursor.getInt(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.CustId)));
                        cList.add(new mSample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSample.SampleId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CallPlanId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleFor)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleDate)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleStatusId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleStatus)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleReceivedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPic)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPicJabatan)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CustPicHp)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.Note)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleResponseDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.SampleResponseNote)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CreatedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.CreatedBy)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.ModifiedDate)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.ModifiedBy)),
                                productSamplesRequest, productSamplesRealisasi, customer,
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSample.StatusSend))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample", e.getMessage());
        }
        return cList;
    }

    //transcallplan sampleproduk
    public boolean InsertUpateAllTransCallPlanSampleProduct(ArrayList<mSample.mProductSample> callPlan) {
        boolean hasil = false;
        try {
            if (callPlan.size() > 0) {
                //cek if call plan will send
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TransCallPlanSampleProduk + " " +
                        "(" + iTransCallPlanSampleProduk.SampleProdukId
                        + "," + iTransCallPlanSampleProduk.SampleId
                        + "," + iTransCallPlanSampleProduk.ProductId
                        + "," + iTransCallPlanSampleProduk.ProductName
                        + "," + iTransCallPlanSampleProduk.ProductCode
                        + "," + iTransCallPlanSampleProduk.Kemasan
                        + "," + iTransCallPlanSampleProduk.Qty
                        + "," + iTransCallPlanSampleProduk.Note
                        + "," + iTransCallPlanSampleProduk.TypeRequest
                        + "," + iTransCallPlanSampleProduk.CreatedDate
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mSample.mProductSample cu : callPlan) {
                    Log.e("masuk isert demo", cu.getSampleProdukId() + "," + cu.getSampleId() + "," +
                            String.valueOf(cu.getProductId()) + "," + cu.getProductName() + "," +
                            String.valueOf(cu.getKemasan()) + "," + cu.getQty() + "," + cu.getNote() + "," + cu.getTypeRequest());
                    insert.bindString(1, cu.getSampleProdukId());
                    insert.bindString(2, cu.getSampleId());
                    insert.bindString(3, String.valueOf(cu.getProductId()));
                    insert.bindString(4, cu.getProductName());
                    insert.bindString(5, cu.getProductCode());
                    insert.bindString(6, cu.getKemasan());
                    insert.bindString(7, String.valueOf(cu.getQty()));
                    insert.bindString(8, cu.getNote());
                    insert.bindString(9, cu.getTypeRequest());
                    insert.bindString(10, cu.getCreatedDate());
                    insert.execute();
                }
                db.setTransactionSuccessful();
                hasil = true;

            } else {
                Log.e("isi call sample produk ", "tidak ada");
                hasil = true;
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (callPlan.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mSample.mProductSample> getTransCallPlanSampleProdukBySample(String sampleId, String typeRequest) {

        ArrayList<mSample.mProductSample> cList = new ArrayList<>();
        try {
            String[] projection = {
                    iTransCallPlanSampleProduk.SampleProdukId, iTransCallPlanSampleProduk.SampleId, iTransCallPlanSampleProduk.ProductId, iTransCallPlanSampleProduk.ProductName, iTransCallPlanSampleProduk.CreatedDate,
                    iTransCallPlanSampleProduk.Kemasan, iTransCallPlanSampleProduk.Qty, iTransCallPlanSampleProduk.Note, iTransCallPlanSampleProduk.TypeRequest, iTransCallPlanSampleProduk.ProductCode
            };
            String selection = iTransCallPlanSampleProduk.SampleId + " = ? AND lower(" + iTransCallPlanSampleProduk.TypeRequest + ")=?";
            String[] selectionArgs = {sampleId, typeRequest.toLowerCase()};
            String orderby = iTransCallPlanSampleProduk.SampleProdukId;
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_TransCallPlanSampleProduk, projection, selection, selectionArgs, null, null, orderby);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Log.e("isi prod", cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSampleProduk.SampleProdukId)) + "," + cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSampleProduk.SampleId)));
                        cList.add(new mSample.mProductSample(cursor.getString(cursor
                                .getColumnIndexOrThrow(iTransCallPlanSampleProduk.SampleProdukId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.SampleId)),
                                cursor.getInt(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.ProductId)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.ProductName)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.ProductCode)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.Kemasan)),
                                cursor.getDouble(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.Qty)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.Note)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.TypeRequest)),
                                cursor.getString(cursor
                                        .getColumnIndexOrThrow(iTransCallPlanSampleProduk.CreatedDate))
                        ));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            Log.e("err sample produk", e.getMessage());
        }
        return cList;
    }

    /*pesan*/
    public mPesan getPesanById(int id) {
        Cursor cursor = null;
        mPesan pesan = null;
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.dateSend, iPesan.dateRead, iPesan.refId,
                    iPesan.penerima, iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.statusSend
            };
            String selection = iPesan.id + " = ? ";
            String[] selectionArgs = {String.valueOf(id)};
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    pesan = new mPesan(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPengirim)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.pengirim)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPenerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.penerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.judul)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.isipesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.typePesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateRead)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.statusPesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.refId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.statusSend)) > 0
                    );
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return pesan;
    }

    public ArrayList<mPesan> getPesanByUnread(String unread) {
        Cursor cursor = null;
        ArrayList<mPesan> pesanList = new ArrayList<>();
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.penerima, iPesan.dateSend, iPesan.dateRead,
                    iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.statusSend, iPesan.refId
            };
            String selection = iPesan.statusPesan + " = ? ";
            String[] selectionArgs = {unread};
            String orderby = iPesan.dateSend;
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, orderby);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    pesanList.add(new mPesan(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPengirim)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.pengirim)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPenerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.penerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.judul)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.isipesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.typePesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateRead)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.statusPesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.refId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.statusSend)) > 0
                    ));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return pesanList;
    }

    public int getPesanByUnread(String unread, String salesId) {
        int hasil = 0;
        Cursor cursor = null;
        ArrayList<mPesan> pesanList = new ArrayList<>();
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.penerima, iPesan.dateSend, iPesan.dateRead,
                    iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.statusSend, iPesan.refId
            };
            String selection = iPesan.statusPesan + " = ?  AND " + iPesan.idPenerima + "= ?";
            String[] selectionArgs = {unread, salesId};
            String orderby = iPesan.dateSend;
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, orderby);
            if (cursor != null) {
                hasil = cursor.getCount();
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return hasil;
    }

    public int getPesanNewIdPesan(String idPesan, String idPengirim) {
        int hasil = 0;
        Cursor cursor = null;
        ArrayList<mPesan> pesanList = new ArrayList<>();
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.penerima, iPesan.dateSend, iPesan.dateRead,
                    iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.statusSend, iPesan.refId
            };
            String selection = iPesan.statusPesan + " = ?  AND " + iPesan.refId + "= ? AND " + iPesan.idPengirim + "!=?";
            String[] selectionArgs = {"new", idPesan, idPengirim};
            String orderby = iPesan.dateSend;
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, orderby);
            if (cursor != null) {
                hasil = cursor.getCount();
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return hasil;
    }

    public ArrayList<mPesan> getPesanAll(int salesId) {
        Cursor cursor = null;
        ArrayList<mPesan> pesanList = new ArrayList<>();
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.penerima, iPesan.dateSend, iPesan.dateRead,
                    iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.statusSend, iPesan.refId
            };
            String selection = iPesan.refId + " = ?  OR " + iPesan.refId + " = " + iPesan.id;
            String[] selectionArgs = {"0"};
            String orderby = iPesan.dateSend + " desc," + iPesan.statusPesan + " desc";
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, orderby);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    boolean newpesan = getPesanNewIdPesan(String.valueOf(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id))), String.valueOf(salesId)) > 0;
                    Log.e("isi pesan new", newpesan + "," + cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id)));
                    pesanList.add(new mPesan(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPengirim)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.pengirim)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPenerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.penerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.judul)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.isipesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.typePesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateRead)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.statusPesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.refId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.statusSend)) > 0,
                            newpesan
                    ));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return pesanList;
    }

    public ArrayList<mPesan> getPesanByRef(String ref) {
        Cursor cursor = null;
        ArrayList<mPesan> pesanList = new ArrayList<>();
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.penerima, iPesan.dateSend, iPesan.dateRead,
                    iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.refId, iPesan.statusSend
            };
            String selection = iPesan.refId + " = ? AND " + iPesan.refId + "!=" + iPesan.id;
            String[] selectionArgs = {String.valueOf(ref)};
            String orderby = iPesan.dateSend;
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, orderby);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    pesanList.add(new mPesan(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPengirim)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.pengirim)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPenerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.penerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.judul)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.isipesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.typePesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateRead)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.statusPesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.refId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.statusSend)) > 0
                    ));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return pesanList;
    }

    public ArrayList<mPesan> getPesanBySend(int send) {
        Cursor cursor = null;
        ArrayList<mPesan> pesanList = new ArrayList<>();
        try {
            String[] projection = {iPesan.id, iPesan.idPengirim, iPesan.pengirim, iPesan.idPenerima, iPesan.penerima, iPesan.dateSend, iPesan.dateRead,
                    iPesan.judul, iPesan.isipesan, iPesan.typePesan, iPesan.fcmid, iPesan.statusPesan, iPesan.refId, iPesan.statusSend
            };
            String selection = iPesan.statusSend + " = ? ";
            String[] selectionArgs = {String.valueOf(send)};
            String orderby = iPesan.dateSend;
            // @SuppressLint("Recycle")
            cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, orderby);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    pesanList.add(new mPesan(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iPesan.id)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPengirim)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.pengirim)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.idPenerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.penerima)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.judul)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.isipesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.fcmid)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.typePesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.dateRead)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.statusPesan)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iPesan.refId)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iPesan.statusSend)) > 0
                    ));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("err mtma", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return pesanList;
    }

    public boolean addPesan(mPesan pesan) {
        ContentValues values = new ContentValues();

        values.put(iPesan.id, pesan.getId());
        values.put(iPesan.idPengirim, pesan.getIdPengirim());
        values.put(iPesan.pengirim, pesan.getPengirim());
        values.put(iPesan.idPenerima, pesan.getIdPenerima());
        values.put(iPesan.penerima, pesan.getPenerima());
        values.put(iPesan.judul, pesan.getJudul());
        values.put(iPesan.isipesan, pesan.getIsiPesan());
        values.put(iPesan.fcmid, pesan.getFcmid());
        values.put(iPesan.typePesan, pesan.getTypePesan());
        values.put(iPesan.dateSend, pesan.getDateSend());
        values.put(iPesan.dateRead, pesan.getDateRead());
        values.put(iPesan.statusPesan, pesan.getStatusPesan());
        values.put(iPesan.refId, pesan.getRefId());
        values.put(iPesan.statusSend, pesan.getDateSend());

        return ((db.insert(TB_Pesan, null, values)) != -1) ? true : false;
    }

    public boolean updatePesan(mPesan pesan) {
        ContentValues values = new ContentValues();
        String selection = iPesan.id + " = ?";
        String[] selectionArgs = {String.valueOf(pesan.getId())};

        values.put(iPesan.id, pesan.getId());
        values.put(iPesan.idPengirim, pesan.getIdPengirim());
        values.put(iPesan.pengirim, pesan.getPengirim());
        values.put(iPesan.idPenerima, pesan.getIdPenerima());
        values.put(iPesan.penerima, pesan.getPenerima());
        values.put(iPesan.judul, pesan.getJudul());
        values.put(iPesan.isipesan, pesan.getIsiPesan());
        values.put(iPesan.fcmid, pesan.getFcmid());
        values.put(iPesan.typePesan, pesan.getTypePesan());
        values.put(iPesan.dateSend, pesan.getDateSend());
        values.put(iPesan.dateRead, pesan.getDateRead());
        values.put(iPesan.statusPesan, pesan.getStatusPesan());
        values.put(iPesan.refId, pesan.getRefId());
        values.put(iPesan.statusSend, pesan.getDateSend());

        return ((db.update(TB_Pesan, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean insertUpdatePesan(mPesan pesan) {
        String[] projection = {iPesan.id};
        String selection = iPesan.id + " = ? ";
        String[] selectionArgs = {String.valueOf(pesan.getId())};
        Cursor cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, null);
        boolean hasil;
        hasil = (cursor.getCount() > 0) ? true : false;
        cursor.close();
        //Log.e("isi pesan", String.valueOf(pesan.getId()) + "," + hasil);
        if (hasil) {
            hasil = updatePesan(pesan);
        } else {
            hasil = addPesan(pesan);
        }
        return hasil;
    }

    public boolean updatePesanFromServer(mPesan pesan) {
        ContentValues values = new ContentValues();
        String selection = iPesan.id + " = ? ";//And " + iPesan.dateRead + "=?";
        String[] selectionArgs = {String.valueOf(pesan.getId())};//, ""};

        values.put(iPesan.id, pesan.getId());
        values.put(iPesan.idPengirim, pesan.getIdPengirim());
        values.put(iPesan.pengirim, pesan.getPengirim());
        values.put(iPesan.idPenerima, pesan.getIdPenerima());
        values.put(iPesan.penerima, pesan.getPenerima());
        values.put(iPesan.judul, pesan.getJudul());
        values.put(iPesan.isipesan, pesan.getIsiPesan());
        values.put(iPesan.fcmid, pesan.getFcmid());
        values.put(iPesan.typePesan, pesan.getTypePesan());
        values.put(iPesan.dateSend, pesan.getDateSend());
        values.put(iPesan.dateRead, pesan.getDateRead());
        values.put(iPesan.statusPesan, pesan.getStatusPesan());
        values.put(iPesan.refId, pesan.getRefId());
        values.put(iPesan.statusSend, pesan.getDateSend());

        return ((db.update(TB_Pesan, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean insertUpdatePesanFromServer(mPesan pesan) {
        String[] projection = {iPesan.id, iPesan.dateRead};
        String selection = iPesan.id + " = ? ";
        String[] selectionArgs = {String.valueOf(pesan.getId())};
        Cursor cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, null);
        boolean hasil;
        hasil = (cursor.getCount() > 0) ? true : false;
        cursor.close();
        //Log.e("isi pesan from server", String.valueOf(pesan.getId()) + "," + hasil);
        if (hasil) {
            hasil = updatePesanFromServer(pesan);
        } else {
            hasil = addPesan(pesan);
        }
        return hasil;
    }

    public boolean cekPesan() {
        String[] projection = {iPesan.id};
        String selection = iPesan.statusPesan + " = ? and " + iPesan.refId + "=?";
        String[] selectionArgs = {"new", "0"};
        Cursor cursor = db.query(TB_Pesan, projection, selection, selectionArgs, null, null, null);
        boolean hasil;
        hasil = (cursor.getCount() > 0) ? true : false;
        cursor.close();
        return hasil;
    }

    //session
    public List<mSession> getSession() {
        List<mSession> listContacts = new ArrayList<mSession>();

        Cursor cursor = db.query(TB_Session, new String[]{iSession.id,
                        iSession.nama, iSession.status, iSession.nilai1, iSession.nilai2, iSession.nilai3, iSession.nilai4, iSession.nilai5, iSession.nilai6, iSession.nilai7, iSession.nilai8, iSession.nilai9},
                null, null, null, null, iSession.nama);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts
                            .add(new mSession(cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iSession.id)), cursor
                                    .getString(cursor
                                            .getColumnIndexOrThrow(iSession.nama)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iSession.status)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai1)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai2)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai3)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai4)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai5)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai6)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai7)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai8)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iSession.nilai9))
                            ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public boolean getSessionByStatus(String nama, int status) {
        String[] projection = {iSession.id};
        String selection = iSession.nama + " = ? and " + iSession.status + " = ?";
        String[] selectionArgs = {nama, String.valueOf(status)};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Session, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public mSession getSessionByVal(String val, int status) {
        mSession session = null;
        String[] projection = {iSession.id, iSession.nama, iSession.status, iSession.nilai1, iSession.nilai2,
                iSession.nilai3, iSession.nilai4, iSession.nilai5, iSession.nilai6,
                iSession.nilai7, iSession.nilai8, iSession.nilai9};
        String selection = iSession.nama + " = ? and " + iSession.status + " = ?";
        String[] selectionArgs = {val, String.valueOf(status)};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Session, projection, selection, selectionArgs, null, null, iSession.nama);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    session = new mSession(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iSession.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iSession.nama)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iSession.status)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai1)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai2)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai3)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai4)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai5)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai6)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai7)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai8)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai9))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return session;
    }

    public mSession getSessionByVal(String val) {
        mSession session = null;
        String[] projection = {iSession.id, iSession.nama, iSession.status, iSession.nilai1, iSession.nilai2,
                iSession.nilai3, iSession.nilai4, iSession.nilai5, iSession.nilai6,
                iSession.nilai7, iSession.nilai8, iSession.nilai9};
        String selection = iSession.nama + " = ?";
        String[] selectionArgs = {val};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Session, projection, selection, selectionArgs, null, null, iSession.nama);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    session = new mSession(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iSession.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iSession.nama)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iSession.status)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai1)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai2)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai3)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai4)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai5)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai6)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai7)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai8)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iSession.nilai9))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return session;
    }

    public boolean addSession(mSession session) {
        ContentValues values = new ContentValues();

        if (session.getId() != 0) {
            values.put(iSession.id, session.getId());
        }
        values.put(iSession.nama, session.getNama());
        values.put(iSession.status, session.getStatus());
        values.put(iSession.nilai1, session.getNilai1());
        values.put(iSession.nilai2, session.getNilai2());
        values.put(iSession.nilai3, session.getNilai3());
        values.put(iSession.nilai4, session.getNilai4());
        values.put(iSession.nilai5, session.getNilai5());
        values.put(iSession.nilai6, session.getNilai6());
        values.put(iSession.nilai7, session.getNilai7());
        values.put(iSession.nilai8, session.getNilai8());
        values.put(iSession.nilai9, session.getNilai9());

        return ((db.insert(TB_Session, null, values)) != -1) ? true : false;
    }

    public boolean updateSessionStatus(String nama, int status) {
        ContentValues values = new ContentValues();
        String selection = iSession.nama + " = ? ";
        String[] selectionArgs = {nama};

        //values.put(iSession.nama, session.getNama());
        values.put(iSession.status, status);

        return ((db.update(TB_Session, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean insertUpdateSessionStatus(mSession session, String nama) {
        insertUpdateSessionByNama(session);
        ContentValues values = new ContentValues();
        String selection = iSession.nama + " = ? ";
        String[] selectionArgs = {nama};

        //values.put(iSession.nama, session.getNama());
        values.put(iSession.status, session.getStatus());
        values.put(iSession.nilai1, session.getNilai1());
        values.put(iSession.nilai2, session.getNilai2());
        values.put(iSession.nilai3, session.getNilai3());
        values.put(iSession.nilai4, session.getNilai4());
        values.put(iSession.nilai5, session.getNilai5());
        values.put(iSession.nilai6, session.getNilai6());
        values.put(iSession.nilai7, session.getNilai7());
        values.put(iSession.nilai8, session.getNilai8());
        values.put(iSession.nilai9, session.getNilai9());
        // values.put(iSession.status, status);

        return ((db.update(TB_Session, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean updateSession(mSession session) {
        ContentValues values = new ContentValues();
        String selection = iSession.nama + " = ? ";
        String[] selectionArgs = {String.valueOf(session.getNama())};

        values.put(iSession.nama, session.getNama());
        values.put(iSession.status, session.getStatus());
        values.put(iSession.nilai1, session.getNilai1());
        values.put(iSession.nilai2, session.getNilai2());
        values.put(iSession.nilai3, session.getNilai3());
        values.put(iSession.nilai4, session.getNilai4());
        values.put(iSession.nilai5, session.getNilai5());
        values.put(iSession.nilai6, session.getNilai6());
        values.put(iSession.nilai7, session.getNilai7());
        values.put(iSession.nilai8, session.getNilai8());
        values.put(iSession.nilai9, session.getNilai9());

        return ((db.update(TB_Session, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean insertUpdateSessionByNama(mSession session) {
        boolean hasil = false;
        if (null != session.getNama()) {
            String[] projection = {iSession.id};
            String selection = iSession.nama + " = ? ";
            String[] selectionArgs = {session.getNama()};
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_Session, projection, selection, selectionArgs, null, null, null);
            //Log.e("isi session jumlah", cursor.getCount() + " xx " + session.getNama());
            try {
                hasil = (cursor.getCount() > 0) ? true : false;
                if (hasil) {
                    hasil = updateSession(session);
                } else {
                    hasil = addSession(session);
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return hasil;
    }

    //tracking picture
    public boolean InsertUpateAllTrackingPicture(ArrayList<mTrackingPicture> trackingPicture) {
        boolean hasil = false;
        try {
            if (trackingPicture.size() > 0) {

                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TrackingPicture + " " +
                        "(" + iTrackingPicture.TrackingPictureId
                        + "," + iTrackingPicture.PictureRef
                        + "," + iTrackingPicture.Picture
                        + "," + iTrackingPicture.StatusBattery
                        + "," + iTrackingPicture.Note
                        + "," + iTrackingPicture.CreatedDate
                        + "," + iTrackingPicture.CreatedBy
                        + "," + iTrackingPicture.StatusSend
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mTrackingPicture cu : trackingPicture) {
                    Log.e("trackingpictre", "dfd" + cu.getStatusBattery());
                    insert.bindString(1, cu.getTrackingPictureId());
                    insert.bindString(2, cu.getPictureRef());
                    insert.bindString(3, cu.getPicture());
                    insert.bindString(4, cu.getStatusBattery());
                    insert.bindString(5, cu.getNote());
                    insert.bindString(6, cu.getCreatedDate());
                    insert.bindString(7, cu.getCreatedBy());
                    insert.bindString(8, String.valueOf(cu.getStatusSend()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi tracking pitrue ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (trackingPicture.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public boolean InsertUpateAllTrackingPicture(mTrackingPicture trackingPicture) {
        boolean hasil = false;
        try {
            // Log.e("insert customer otherQty", "cust otherQty");
            db.beginTransaction();
            String sql = "Insert or Replace into " + TB_TrackingPicture + " " +
                    "(" + iTrackingPicture.TrackingPictureId
                    + "," + iTrackingPicture.PictureRef
                    + "," + iTrackingPicture.Picture
                    + "," + iTrackingPicture.StatusBattery
                    + "," + iTrackingPicture.Note
                    + "," + iTrackingPicture.CreatedDate
                    + "," + iTrackingPicture.CreatedBy
                    + "," + iTrackingPicture.StatusSend
                    + ") " +
                    "values" +
                    "(?,?,?,?,?,?,?,?)";
            SQLiteStatement insert = db.compileStatement(sql);
            if (trackingPicture != null) {
                Log.e("trackingpictre", trackingPicture.getTrackingPictureId() + "," + trackingPicture.getPictureRef() + "," + trackingPicture.getPicture() +
                        "," + trackingPicture.getStatusBattery() + "," + trackingPicture.getNote() + "," + trackingPicture.getCreatedDate() +
                        "," + trackingPicture.getCreatedBy() + "," + String.valueOf(trackingPicture.getStatusSend()));
                insert.bindString(1, trackingPicture.getTrackingPictureId());
                insert.bindString(2, trackingPicture.getPictureRef());
                insert.bindString(3, trackingPicture.getPicture());
                insert.bindString(4, trackingPicture.getStatusBattery());
                insert.bindString(5, trackingPicture.getNote());
                insert.bindString(6, trackingPicture.getCreatedDate());
                insert.bindString(7, trackingPicture.getCreatedBy());
                insert.bindString(8, String.valueOf(trackingPicture.getStatusSend()));
                insert.execute();
            }
            db.setTransactionSuccessful();
            hasil = true;
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
            hasil = false;
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            db.endTransaction();
        }
        return hasil;
    }

    public ArrayList<mTrackingPicture> getTrackingPictureAll() {
        ArrayList<mTrackingPicture> list = new ArrayList<>();
        String[] projection = {iTrackingPicture.TrackingPictureId, iTrackingPicture.PictureRef,
                iTrackingPicture.Picture, iTrackingPicture.StatusBattery,
                iTrackingPicture.Note, iTrackingPicture.CreatedDate, iTrackingPicture.StatusSend,
                iTrackingPicture.CreatedBy};
        String orderBy = iTrackingPicture.CreatedDate + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_TrackingPicture, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mTrackingPicture(cursor.getString(cursor
                            .getColumnIndexOrThrow(iTrackingPicture.TrackingPictureId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.PictureRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.Picture)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.StatusBattery)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.Note)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.StatusSend))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mTrackingPicture> getTrackingPictureAll(int statusSend) {
        ArrayList<mTrackingPicture> list = new ArrayList<>();
        String[] projection = {iTrackingPicture.TrackingPictureId, iTrackingPicture.PictureRef,
                iTrackingPicture.Picture, iTrackingPicture.StatusBattery,
                iTrackingPicture.Note, iTrackingPicture.CreatedDate, iTrackingPicture.StatusSend,
                iTrackingPicture.CreatedBy};
        String selection = iTrackingPicture.StatusSend + " = ? ";
        String[] selectionArgs = {String.valueOf(statusSend)};
        String orderBy = iTrackingPicture.CreatedDate + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_TrackingPicture, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mTrackingPicture(cursor.getString(cursor
                            .getColumnIndexOrThrow(iTrackingPicture.TrackingPictureId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.PictureRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.Picture)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.StatusBattery)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.Note)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.StatusSend))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mTrackingPicture> getTrackingPictureAllByRef(String pictureRef, int statusSend) {
        ArrayList<mTrackingPicture> list = new ArrayList<>();
        String[] projection = {iTrackingPicture.TrackingPictureId, iTrackingPicture.PictureRef,
                iTrackingPicture.Picture, iTrackingPicture.StatusBattery,
                iTrackingPicture.Note, iTrackingPicture.CreatedDate, iTrackingPicture.StatusSend,
                iTrackingPicture.CreatedBy};
        String selection = iTrackingPicture.StatusSend + " = ? AND " + iTrackingPicture.PictureRef + " = ?";
        String[] selectionArgs = {String.valueOf(statusSend), pictureRef};
        String orderBy = iTrackingPicture.CreatedDate + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_TrackingPicture, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mTrackingPicture(cursor.getString(cursor
                            .getColumnIndexOrThrow(iTrackingPicture.TrackingPictureId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.PictureRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.Picture)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.StatusBattery)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.Note)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.CreatedDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.CreatedBy)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTrackingPicture.StatusSend))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public boolean updateTrackingPictureStatus(String nama, int status) {
        ContentValues values = new ContentValues();
        String selection = iTrackingPicture.TrackingPictureId + " = ? ";
        String[] selectionArgs = {nama};

        //values.put(iSession.nama, session.getNama());
        values.put(iTrackingPicture.StatusSend, status);

        return ((db.update(TB_TrackingPicture, values, selection, selectionArgs)) == 1) ? true : false;
    }

    //tracking
    public List<mTracking> getTracking() {
        List<mTracking> listContacts = new ArrayList<>();
        String[] projection = {iTracking.id, iTracking.SalesmanId, iTracking.trackingId, iTracking.trackingType, iTracking.trackingDate, iTracking.createDate,
                iTracking.trackingTime, iTracking.trackingLat, iTracking.trackingLot, iTracking.trackingRef, iTracking.trackingStatus, iTracking.statusSend, iTracking.infoDevice};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Tracking, projection,
                null, null, null, null, iTracking.trackingType);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts
                            .add(new mTracking(cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTracking.id)), cursor
                                    .getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.SalesmanId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingType)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingDate)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingTime)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingLat)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingLot)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingRef)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingStatus)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iTracking.statusSend)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.createDate)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.infoDevice))
                            ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public ArrayList<mTracking> getTrackingByStatus(int statussend) {
        ArrayList<mTracking> listContacts = new ArrayList<>();
        String[] projection = {iTracking.id, iTracking.SalesmanId, iTracking.trackingId, iTracking.trackingType, iTracking.trackingDate, iTracking.createDate,
                iTracking.trackingTime, iTracking.trackingLat, iTracking.trackingLot, iTracking.trackingRef, iTracking.trackingStatus, iTracking.statusSend, iTracking.infoDevice};
        String selection = iTracking.statusSend + " = ? ";
        String[] selectionArgs = {String.valueOf(statussend)};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Tracking, projection,
                selection, selectionArgs, null, null, iTracking.trackingType);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    listContacts
                            .add(new mTracking(cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTracking.id)), cursor
                                    .getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.SalesmanId)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingType)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingDate)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingTime)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingLat)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingLot)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingRef)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.trackingStatus)),
                                    cursor.getInt(cursor
                                            .getColumnIndexOrThrow(iTracking.statusSend)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.createDate)),
                                    cursor.getString(cursor
                                            .getColumnIndexOrThrow(iTracking.infoDevice))
                            ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listContacts;
    }

    public boolean getTrackingByStatus(String nama, int status) {
        String[] projection = {iTracking.id};
        String selection = iTracking.trackingType + " = ? and " + iTracking.statusSend + " = ?";
        String[] selectionArgs = {nama, String.valueOf(status)};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Tracking, projection, selection, selectionArgs, null, null, null);
        boolean hsl = false;
        try {
            hsl = (cursor.getCount() > 0) ? true : false;
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return hsl;
    }

    public mTracking getTrackingByVal(String val, int status) {
        mTracking tracking = null;
        String[] projection = {iTracking.id, iTracking.SalesmanId, iTracking.trackingId, iTracking.trackingType, iTracking.trackingDate, iTracking.createDate,
                iTracking.trackingTime, iTracking.trackingLat, iTracking.trackingLot, iTracking.trackingRef, iTracking.trackingStatus, iTracking.statusSend, iTracking.infoDevice};
        String selection = iTracking.trackingType + " = ? and " + iTracking.statusSend + " = ?";
        String[] selectionArgs = {val, String.valueOf(status)};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Tracking, projection, selection, selectionArgs, null, null, iTracking.trackingType);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tracking = new mTracking(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iTracking.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingType)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingTime)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingLat)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingLot)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingStatus)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTracking.statusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.createDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.infoDevice))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return tracking;
    }

    public mTracking getTrackingByVal(String val) {
        mTracking tracking = null;
        String[] projection = {iTracking.id, iTracking.SalesmanId, iTracking.trackingId, iTracking.trackingType, iTracking.trackingDate, iTracking.createDate,
                iTracking.trackingTime, iTracking.trackingLat, iTracking.trackingLot, iTracking.trackingRef, iTracking.trackingStatus, iTracking.statusSend, iTracking.infoDevice};
        String selection = iTracking.trackingType + " = ?";
        String[] selectionArgs = {val};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Tracking, projection, selection, selectionArgs, null, null, iTracking.trackingType);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tracking = new mTracking(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iTracking.id)), cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.SalesmanId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingType)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingTime)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingLat)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingLot)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingRef)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.trackingStatus)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iTracking.statusSend)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.createDate)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iTracking.infoDevice))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return tracking;
    }

    public boolean addTracking(mTracking tracking) {
        ContentValues values = new ContentValues();

        if (tracking.getId() != 0) {
            values.put(iTracking.id, tracking.getId());
        }
        values.put(iTracking.trackingId, tracking.getTrackingId());
        values.put(iTracking.SalesmanId, tracking.getSalesmanId());
        values.put(iTracking.trackingType, tracking.getTrackingType());
        values.put(iTracking.trackingDate, tracking.getTrackingDate());
        values.put(iTracking.trackingTime, tracking.getTrackingTime());
        values.put(iTracking.trackingLat, tracking.getTrackingLat());
        values.put(iTracking.trackingLot, tracking.getTrackingLot());
        values.put(iTracking.trackingRef, tracking.getTrackingRef());
        values.put(iTracking.trackingStatus, tracking.getTrackingStatus());
        values.put(iTracking.statusSend, tracking.getStatusSend());
        values.put(iTracking.createDate, tracking.getCreateDate());
        values.put(iTracking.infoDevice, tracking.getInfoDevice());


        return ((db.insert(TB_Tracking, null, values)) != -1) ? true : false;
    }

    public boolean updateTrackingStatus(String trackingId, String salesId, int status) {
        ContentValues values = new ContentValues();
        String selection = iTracking.trackingId + " = ? AND " + iTracking.SalesmanId + " = ?";
        String[] selectionArgs = {trackingId, salesId};

        //values.put(iSession.nama, session.getNama());
        values.put(iTracking.statusSend, status);

        return ((db.update(TB_Tracking, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean updateTracking(mTracking tracking) {
        ContentValues values = new ContentValues();
        String selection = iTracking.trackingId + " = ? ";
        String[] selectionArgs = {tracking.getTrackingId()};

        values.put(iTracking.id, tracking.getId());
        values.put(iTracking.trackingId, tracking.getTrackingId());
        values.put(iTracking.SalesmanId, tracking.getSalesmanId());
        values.put(iTracking.trackingType, tracking.getTrackingType());
        values.put(iTracking.trackingDate, tracking.getTrackingDate());
        values.put(iTracking.trackingTime, tracking.getTrackingTime());
        values.put(iTracking.trackingLat, tracking.getTrackingLat());
        values.put(iTracking.trackingLot, tracking.getTrackingLot());
        values.put(iTracking.trackingRef, tracking.getTrackingRef());
        values.put(iTracking.trackingStatus, tracking.getTrackingStatus());
        values.put(iTracking.statusSend, tracking.getStatusSend());
        values.put(iTracking.infoDevice, tracking.getInfoDevice());

        return ((db.update(TB_Tracking, values, selection, selectionArgs)) == 1) ? true : false;
    }

    public boolean insertUpdateTracking(mTracking tracking) {
        boolean hasil = false;
        if (null != tracking.getTrackingType()) {
            String[] projection = {iTracking.id};
            String selection = iTracking.trackingType + " = ? ";
            String[] selectionArgs = {tracking.getTrackingType()};
            @SuppressLint("Recycle")
            Cursor cursor = db.query(TB_Tracking, projection, selection, selectionArgs, null, null, null);
            // Log.e("isi session tracking", cursor.getCount() + " xx " + tracking.getTrackingType());
            try {
                hasil = (cursor.getCount() > 0);
                if (hasil) {
                    hasil = updateTracking(tracking);
                } else {
                    hasil = addTracking(tracking);
                }
            } catch (Exception ex) {
                Log.e("err GL", "G" + ex.toString());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return hasil;
    }

    /*typenote*/
    public boolean InsertUpateAllBiCsType(ArrayList<mBiCsType> tNote) {
        boolean hasil = false;
        try {
            if (tNote.size() > 0) {
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_BiCsType + " " +
                        "(" + iBiCsType.BiCsTypeId
                        + "," + iBiCsType.BiCsTypeName
                        + "," + iBiCsType.BiCsTypeEmail
                        + "," + iBiCsType.BiCsTypeJenis
                        + "," + iBiCsType.BICsTypeStatus
                        + ") " +
                        "values" +
                        "(?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mBiCsType cu : tNote) {
                    insert.bindString(1, String.valueOf(cu.getBiCsTypeId()));
                    insert.bindString(2, cu.getBiCsTypeName());
                    insert.bindString(3, cu.getBiCsTypeEmail());
                    insert.bindString(4, cu.getBiCsTypeJenis());
                    insert.bindString(5, String.valueOf(cu.getBiCsTypeStatus()));
                    insert.execute();

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi type note ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (tNote.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mBiCsType> getBiCsTypeAll(String type) {
        ArrayList<mBiCsType> list = new ArrayList<>();
        String[] projection = {iBiCsType.BiCsTypeId, iBiCsType.BiCsTypeName,
                iBiCsType.BiCsTypeEmail, iBiCsType.BiCsTypeJenis, iBiCsType.BICsTypeStatus};
        String selection = iBiCsType.BICsTypeStatus + " = ? AND " + iBiCsType.BiCsTypeJenis + "= ?";
        String[] selectionArgs = {"1", type};
        String orderBy = iBiCsType.BiCsTypeName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_BiCsType, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mBiCsType(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iBiCsType.BiCsTypeId)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iBiCsType.BiCsTypeName)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iBiCsType.BiCsTypeEmail)),
                            cursor.getString(cursor
                                    .getColumnIndexOrThrow(iBiCsType.BiCsTypeJenis)),
                            cursor.getInt(cursor
                                    .getColumnIndexOrThrow(iBiCsType.BICsTypeStatus))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }


    /*unit*/
    public boolean InsertUpateAllUnit(ArrayList<mUnit> unit) {
        boolean hasil = false;
        try {
            if (unit.size() > 0) {
                // Log.e("insert customer otherQty", "cust otherQty");
                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_Unit + " " +
                        "(" + iUnit.UnitId
                        + "," + iUnit.UnitName
                        + "," + iUnit.Status
                        + ") " +
                        "values" +
                        "(?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mUnit cu : unit) {
                    if (cu.getUnitId() != null && !cu.getUnitId().equals("")) {
                        insert.bindString(1, cu.getUnitId());
                        insert.bindString(2, cu.getUnitName());
                        insert.bindString(3, String.valueOf(cu.getStatus()));
                        insert.execute();
                    }

                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi unit status ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (unit.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mUnit> getUnitAll() {
        ArrayList<mUnit> list = new ArrayList<>();
        String[] projection = {iUnit.UnitId, iUnit.UnitName, iUnit.Status};
        String orderBy = iUnit.UnitName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Unit, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mUnit(cursor.getString(cursor
                            .getColumnIndexOrThrow(iUnit.UnitId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iUnit.UnitName)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iUnit.Status))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mUnit> getUnitByStatus(String statusId) {
        ArrayList<mUnit> list = new ArrayList<>();
        String[] projection = {iUnit.UnitId, iUnit.UnitName, iUnit.Status};
        String selection = iUnit.Status + " = ? ";
        String[] selectionArgs = {statusId};
        String orderBy = iUnit.UnitName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Unit, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mUnit(cursor.getString(cursor
                            .getColumnIndexOrThrow(iUnit.UnitId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iUnit.UnitName)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iUnit.Status))

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public mUnit getUnitById(String Id) {
        mUnit list = null;
        String[] projection = {iUnit.UnitId, iUnit.UnitName, iUnit.Status};
        String selection = iUnit.UnitId + " = ? ";
        String[] selectionArgs = {Id};
        String orderBy = iUnit.UnitName + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Unit, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mUnit(cursor.getString(cursor
                            .getColumnIndexOrThrow(iUnit.UnitId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iUnit.UnitName)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iUnit.Status))
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    /*Todolist*/
    public boolean InsertUpateAllTodoList(ArrayList<mTodoList> todo) {
        boolean hasil = false;
        try {
            if (todo.size() > 0) {

                db.beginTransaction();
                String sql = "Insert or Replace into " + TB_TodoList + " " +
                        "(" + iTodoList.RecId
                        + "," + iTodoList.CustId
                        + "," + iTodoList.CustName
                        + "," + iTodoList.Category
                        + "," + iTodoList.Title
                        + "," + iTodoList.DocDate
                        + "," + iTodoList.Detail
                        + "," + iTodoList.Status
                        + "," + iTodoList.StatusDetail
                        + "," + iTodoList.CreatedDate
                        + "," + iTodoList.StatusRead
                        + "," + iTodoList.Reference
                        + "," + iTodoList.StatusId
                        + ") " +
                        "values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SQLiteStatement insert = db.compileStatement(sql);
                for (mTodoList cu : todo) {
                    Log.e("insert todo", "cus" + cu.isStatusRead() + "," + String.valueOf(cu.isStatusRead() ? 1 : 0));
                    insert.bindString(1, String.valueOf(cu.getRecId()));
                    insert.bindString(2, String.valueOf(cu.getCustId()));
                    insert.bindString(3, cu.getCustName());
                    insert.bindString(4, cu.getCategory());
                    insert.bindString(5, cu.getTitle());
                    insert.bindString(6, cu.getDocDate());
                    insert.bindString(7, cu.getDetail());
                    insert.bindString(8, cu.getStatus());
                    insert.bindString(9, cu.getStatusDetail());
                    insert.bindString(10, cu.getCreatedDate());
                    insert.bindString(11, String.valueOf(cu.isStatusRead() ? 1 : 0));
                    insert.bindString(12, cu.getReference());
                    insert.bindString(13, String.valueOf(cu.getStatusId()));
                    insert.execute();
                }
                db.setTransactionSuccessful();
                hasil = true;
            } else {
                Log.e("isi todolist status ", "tidak ada");
            }
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            Log.e("XML sql:", "err sql:" + ex.toString());
        } catch (Exception e) {
            Log.e("XML:", "err jv:" + e.toString());
            hasil = false;
        } finally {
            if (todo.size() > 0)
                db.endTransaction();
        }

        return hasil;
    }

    public ArrayList<mTodoList> getTodoListAll() {
        ArrayList<mTodoList> list = new ArrayList<>();
        String[] projection = {iTodoList.RecId, iTodoList.CustId, iTodoList.CustName, iTodoList.Category, iTodoList.Title, iTodoList.DocDate, iTodoList.StatusId,
                iTodoList.Detail, iTodoList.Status, iTodoList.StatusDetail, iTodoList.CreatedDate, iTodoList.StatusRead, iTodoList.Reference};
        String orderBy = iTodoList.CustName + " ASC," + iTodoList.Category + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_TodoList, projection, null, null, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new mTodoList(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iTodoList.RecId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Reference)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.CustId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.CustName)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Category)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Title)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.DocDate)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Detail)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.StatusId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Status)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.StatusDetail)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.CreatedDate)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.StatusRead)) > 1

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public ArrayList<mTodoList> getTodoListByCustId(String custId) {
        ArrayList<mTodoList> list = new ArrayList<>();
        String[] projection = {iTodoList.RecId, iTodoList.Reference, iTodoList.CustId, iTodoList.CustName, iTodoList.Category, iTodoList.Title, iTodoList.DocDate, iTodoList.StatusId,
                iTodoList.Detail, iTodoList.Status, iTodoList.StatusDetail, iTodoList.CreatedDate, iTodoList.StatusRead};
        String selection = iTodoList.CustId + " = ? ";
        String[] selectionArgs = {custId};
        String orderBy = iTodoList.CustName + " ASC," + iTodoList.Category + " ASC";
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_TodoList, projection, selection, selectionArgs, null, null, orderBy);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Log.e("get todo", "cust:" + cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.CustId)) + "," + cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Reference)));
                    list.add(new mTodoList(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iTodoList.RecId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Reference)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.CustId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.CustName)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Category)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Title)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.DocDate)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Detail)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.StatusId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Status)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.StatusDetail)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.CreatedDate)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.StatusRead)) > 0

                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public mTodoList getTodoListById(String Id) {
        mTodoList list = null;
        String[] projection = {iTodoList.RecId, iTodoList.Reference, iTodoList.CustId, iTodoList.CustName, iTodoList.Category, iTodoList.Title, iTodoList.DocDate,
                iTodoList.Detail, iTodoList.Status, iTodoList.StatusDetail, iTodoList.CreatedDate, iTodoList.StatusRead, iTodoList.StatusId};
        String selection = iTodoList.RecId + " = ? ";
        String[] selectionArgs = {Id};
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TB_Unit, projection, selection, selectionArgs, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list = new mTodoList(cursor.getInt(cursor
                            .getColumnIndexOrThrow(iTodoList.RecId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Reference)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.CustId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.CustName)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Category)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Title)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.DocDate)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Detail)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.StatusId)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.Status)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.StatusDetail)),
                            cursor.getString(cursor.getColumnIndexOrThrow(iTodoList.CreatedDate)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(iTodoList.StatusRead)) > 1
                    );
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("err GL", "G" + ex.toString());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return list;
    }

    public void delTodoListByCustId(String custId) {
        String selection = iTodoList.CustId + " != ?";
        String[] selectionArgs = {custId};
        db.delete(TB_TodoList, selection, selectionArgs);
    }

}
