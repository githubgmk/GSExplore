using GeisaWebAndroid.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;

namespace GeisaWebAndroid.ProsesDB
{
    public class spPO : spLog
    {
        spCustomer spCust;
        spCustomerDistributor spCustDist;
        spSales sales;
        public spPO()
        {
            spCust = new spCustomer();
            spCustDist = new spCustomerDistributor();
            sales = new spSales();
        }

        private string NumberOnly(String data)
        {
            string str = "";
            Regex rgx = new Regex("[^a-zA-Z ~!@#$%^&*()+=<>?,./]");
            str = rgx.Replace(str, "");
            return str;
        }
        internal bool insertUpdatePO(mPO data)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {
                    
                    SqlCommand sqlCmd = new SqlCommand("AndroPOInsertUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@PoId", data.PoId);
                    sqlCmd.Parameters.AddWithValue("@PoCustNumberRef", data.PoCustNumberRef);
                    sqlCmd.Parameters.AddWithValue("@PoDate", data.PoDate);
                    sqlCmd.Parameters.AddWithValue("@DistBranchId", data.DistBranchId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@SalesmanId", data.SalesmanId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@PoById", data.PoById);
                    sqlCmd.Parameters.AddWithValue("@PoViaId", data.PoViaId);
                   
                    sqlCmd.Parameters.AddWithValue("@ShipDate", data.ShipDate);
                    sqlCmd.Parameters.AddWithValue("@EndPeriodeDate", data.EndPeriodeDate);
                    sqlCmd.Parameters.AddWithValue("@Mechanisme", data.Mechanisme);
                    sqlCmd.Parameters.AddWithValue("@ShipAddress", data.ShipAddress);
                    sqlCmd.Parameters.AddWithValue("@Disc1", data.Disc1);
                    sqlCmd.Parameters.AddWithValue("@Disc2", data.Disc2);
                    sqlCmd.Parameters.AddWithValue("@DiscCash", data.CashDisc);
                    sqlCmd.Parameters.AddWithValue("@isPP", data.isPP ? 1 : 0);
                    sqlCmd.Parameters.AddWithValue("@PicDist", data.PicDist);
                    sqlCmd.Parameters.AddWithValue("@PicCust", data.PicCust);
                    sqlCmd.Parameters.AddWithValue("@Notes", data.Notes);
                    sqlCmd.Parameters.AddWithValue("@Signature", data.Signature);
                    sqlCmd.Parameters.AddWithValue("@PoStatusId", data.PoStatusId);
                    sqlCmd.Parameters.AddWithValue("@PoStatusName", data.PoStatusName);
                    sqlCmd.Parameters.AddWithValue("@SoNo", data.SoNo);
                    sqlCmd.Parameters.AddWithValue("@SoDate", data.SoDate);
                    sqlCmd.Parameters.AddWithValue("@DoNo", data.DoNo);
                    sqlCmd.Parameters.AddWithValue("@DoDate", data.DoDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@ConfirmDate", data.ConfirmDate);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", data.ModifiedDate);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    sqlCmd.Parameters.AddWithValue("@isSellOut", data.isSellOut ? 1 : 0);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    if (rowInserted > 0)
                    {
                        if (data.poLines.Count > 0)
                        {
                            bool hsl = true;
                            foreach (mPoLine cu in data.poLines)
                            {
                                bool hs = insertUpdatePOLine(cu);
                                if (!hs)
                                {
                                    hsl = hs;
                                }
                            }
                            hasil = hsl;
                        }
                        else
                        {
                            hasil = true;
                        }
                        if (data.poLineOthers.Count > 0)
                        {
                            foreach (mPoLineOther cu in data.poLineOthers)
                            {
                                hasil = insertUpdatePOLineOther(cu);
                            }
                        }
                    }
                    else
                    {
                        hasil = false;
                    }
                }
            }
            catch (Exception ex)
            {
                hasil = false;
                string json = JsonConvert.SerializeObject(data);
                inserLog("insertUpdatePO", ex.ToString() + "\n " + json, data.SalesmanId.ToString());
            }

            return hasil;
        }

        internal mPO getPoById(string poId)
        {
            mPO data = null;

            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPoById", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@PoId", poId);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPO item = new mPO();
                        item.PoId = rdr["PoId"].ToString();
                        item.PoCustNumberRef = rdr["PoCustNumberRef"].ToString();
                        item.PoDate = rdr["PoDate"].ToString();
                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.PoById = rdr["PoById"].ToString();
                        item.PoByName = rdr["PoByName"].ToString();
                        item.PoViaId = rdr["PoViaId"].ToString();
                        item.PoViaName = rdr["PoViaName"].ToString();
                        item.ShipDate = rdr["ShipDate"].ToString();
                        item.EndPeriodeDate = rdr["EndPeriodeDate"].ToString();
                        item.Mechanisme = rdr["Mechanisme"].ToString();
                        item.ShipAddress = rdr["ShipAddress"].ToString();
                        item.Disc1 = Convert.ToDouble(rdr["Disc1"].ToString());
                        item.Disc2 = Convert.ToDouble(rdr["Disc2"].ToString());
                        item.CashDisc = Convert.ToDouble(rdr["DiscCash"].ToString());
                        item.isPP = Convert.ToInt32(rdr["isPP"].ToString()) > 0;
                        item.PicDist = rdr["PicDist"].ToString();
                        item.PicCust = rdr["PicCust"].ToString();
                        item.Notes = rdr["Notes"].ToString();
                        item.Signature = rdr["Signature"].ToString();
                        item.PoStatusId = Convert.ToInt32(rdr["PoStatusId"].ToString());
                        item.PoStatusName = rdr["PoStatusName"].ToString();
                        item.SoNo = rdr["SoNo"].ToString();
                        item.SoDate = rdr["SoDate"].ToString();
                        item.DoNo = rdr["DoNo"].ToString();
                        item.DoDate = rdr["DoDate"].ToString();
                        item.CreatedDate = rdr["CreatedDate"].ToString();
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ConfirmDate = rdr["ConfirmDate"].ToString();
                        item.ModifiedDate = rdr["ModifiedDate"].ToString();
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        item.KeteranganDetail = rdr["KeteranganDetail"].ToString();
                        item.isSellOut = Convert.ToInt32(rdr["isSellOut"].ToString()) > 0;

                        item.customer = spCust.getCustomer(item.SalesmanId.ToString(), item.CustId.ToString());
                        item.distBranch = spCustDist.SelectCustomerAndDistributorBranchById(item.CustId.ToString(), item.DistBranchId.ToString());
                        item.poLines = getPoLineById(item.PoId);
                        item.poLineOthers = getPoLineOtherById(item.PoId);
                        item.sales = sales.SelectSalesById(item.SalesmanId.ToString());

                        data = item;
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                inserLog("getPoById", err, poId);
            }

            return data;
        }

        internal List<mPoLine> getPoLineById(string poId)
        {
            List<mPoLine> data = new List<mPoLine>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPoLineById", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@PoId", poId);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPoLine item = new mPoLine();
                        item.RecIdTab = rdr["RecIdTab"].ToString();
                        item.PoId = rdr["PoId"].ToString();
                        item.ProductId = Convert.ToInt32(rdr["ProductId"].ToString());
                        item.Qty = Convert.ToDouble(rdr["Qty"].ToString());
                        item.UnitPrice = Convert.ToDouble(rdr["UnitPrice"].ToString());
                        item.UnitId = rdr["UnitId"].ToString();
                        item.PriceId = Convert.ToInt32(rdr["PriceId"].ToString());
                        item.PriceList = Convert.ToDouble(rdr["PriceList"].ToString());
                        item.DiscId = Convert.ToInt32(rdr["DiscId"].ToString());
                        item.Disc1 = Convert.ToDouble(rdr["Disc1"].ToString());
                        item.Disc2 = Convert.ToDouble(rdr["Disc2"].ToString());
                        item.Disc3 = Convert.ToDouble(rdr["Disc3"].ToString());
                        item.DiscRp = Convert.ToDouble(rdr["DiscRp"].ToString());
                        item.Point = Convert.ToDouble(rdr["Point"].ToString());
                        item.IncludePPN = Convert.ToInt32(rdr["IncludePPN"].ToString()) > 0;
                        item.PromoId = Convert.ToInt32(rdr["PromoId"].ToString());
                        item.RefRecIdTab = rdr["RefRecIdTab"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.CreatedDate = rdr["CreatedDate"].ToString();
                        item.ConfirmDate = rdr["ConfirmDate"].ToString();

                        data.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                inserLog("getPoLineById", err, poId);
                string e = ex.ToString();
            }

            return data;
        }

        internal List<mPoLineOther> getPoLineOtherById(string poId)
        {
            List<mPoLineOther> data = new List<mPoLineOther>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPoLineOtherById", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@PoId", poId);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPoLineOther item = new mPoLineOther();
                        item.RecIdTab = rdr["RecIdTab"].ToString();
                        item.PoId = rdr["PoId"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.Qty = Convert.ToDouble(rdr["Qty"].ToString());
                        item.Unit = rdr["Unit"].ToString();

                        data.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                inserLog("getPoLineOtherById", err, poId);
                string x = ex.ToString();
            }

            return data;
        }
        internal bool insertUpdatePOLine(mPoLine data)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPOLineInsertUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@RecIdTab", data.RecIdTab);
                    sqlCmd.Parameters.AddWithValue("@PoId", data.PoId);
                    sqlCmd.Parameters.AddWithValue("@ProductId", data.ProductId);
                    sqlCmd.Parameters.AddWithValue("@Qty", data.Qty);
                    sqlCmd.Parameters.AddWithValue("@UnitPrice", data.UnitPrice);
                    sqlCmd.Parameters.AddWithValue("@UnitId", data.UnitId);
                    sqlCmd.Parameters.AddWithValue("@PriceId", data.PriceId);
                    sqlCmd.Parameters.AddWithValue("@PriceList", data.PriceList);
                    sqlCmd.Parameters.AddWithValue("@DiscId", data.DiscId);
                    sqlCmd.Parameters.AddWithValue("@Disc1", data.Disc1);
                    sqlCmd.Parameters.AddWithValue("@Disc2", data.Disc2);
                    sqlCmd.Parameters.AddWithValue("@Disc3", data.Disc3);
                    sqlCmd.Parameters.AddWithValue("@DiscRp", data.DiscRp);
                    sqlCmd.Parameters.AddWithValue("@Point", data.Point);
                    sqlCmd.Parameters.AddWithValue("@IncludePPN", data.IncludePPN ? 1 : 0);
                    sqlCmd.Parameters.AddWithValue("@PromoId", data.PromoId);
                    sqlCmd.Parameters.AddWithValue("@RefRecIdTab", data.RefRecIdTab);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@ConfirmDate", data.ConfirmDate);
                    sqlCmd.Parameters.AddWithValue("@Selected", data.Selected ? 1 : 0);
                    sqlCmd.Parameters.AddWithValue("@ProductCode", data.ProductCode);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    if (data.poLineBonus != null)
                    {
                        hasil = insertUpdatePOLineBonus(data.poLineBonus);
                    }
                    else
                    {
                        hasil = true;
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                string json = JsonConvert.SerializeObject(data);
                inserLog("insertUpdatePOLine", err + "\n " + json, data.PoId);
                hasil = false;
            }

            return hasil;
        }

        internal bool insertUpdatePOLineBonus(mPoLine data)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPOLineInsertUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@RecIdTab", data.RecIdTab);
                    sqlCmd.Parameters.AddWithValue("@PoId", data.PoId);
                    sqlCmd.Parameters.AddWithValue("@ProductId", data.ProductId);
                    sqlCmd.Parameters.AddWithValue("@Qty", data.Qty);
                    sqlCmd.Parameters.AddWithValue("@UnitPrice", data.UnitPrice);
                    sqlCmd.Parameters.AddWithValue("@UnitId", data.UnitId);
                    sqlCmd.Parameters.AddWithValue("@PriceId", data.PriceId);
                    sqlCmd.Parameters.AddWithValue("@PriceList", data.PriceList);
                    sqlCmd.Parameters.AddWithValue("@DiscId", data.DiscId);
                    sqlCmd.Parameters.AddWithValue("@Disc1", data.Disc1);
                    sqlCmd.Parameters.AddWithValue("@Disc2", data.Disc2);
                    sqlCmd.Parameters.AddWithValue("@Disc3", data.Disc3);
                    sqlCmd.Parameters.AddWithValue("@DiscRp", data.DiscRp);
                    sqlCmd.Parameters.AddWithValue("@Point", data.Point);
                    sqlCmd.Parameters.AddWithValue("@IncludePPN", data.IncludePPN ? 1 : 0);
                    sqlCmd.Parameters.AddWithValue("@PromoId", data.PromoId);
                    sqlCmd.Parameters.AddWithValue("@RefRecIdTab", data.RefRecIdTab);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@ConfirmDate", data.ConfirmDate);
                    sqlCmd.Parameters.AddWithValue("@Selected", data.Selected ? 1 : 0);
                    sqlCmd.Parameters.AddWithValue("@ProductCode", data.ProductCode);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    return true;
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                string json = JsonConvert.SerializeObject(data);
                inserLog("insertUpdatePOLineBonus", err + "\n " + json, data.PoId);
                hasil = false;
            }

            return hasil;
        }

        internal List<mWfTrans> getSignByPoId(string poId)
        {
            List<mWfTrans> data = new List<mWfTrans>();

            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroSignatureApprovalPP", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@PoId", poId);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mWfTrans item = new mWfTrans();
                        item.RecId = Convert.ToInt32(rdr["RecId"].ToString());
                        item.WfTransId = Convert.ToInt32(rdr["WfTransId"].ToString());
                        item.RefId = rdr["RefId"].ToString();
                        item.Index = Convert.ToInt32(rdr["Index"].ToString());
                        item.UserId = rdr["UserId"].ToString();
                        item.StatusActionWf = Convert.ToInt32(rdr["StatusActionWf"].ToString());
                        string tglaction = "",tglawal="";
                        tglawal = Convert.ToDateTime(rdr["ActionDate"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawal.Equals("1900-01-01") && !tglawal.Equals("2000-01-01"))
                            tglaction = Convert.ToDateTime(rdr["ActionDate"].ToString()).ToString("dd MMMM yyyy");
                        item.ActionDate = tglaction;
                        item.Notes = rdr["Notes"].ToString();
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd");
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        item.SalesmanName = rdr["SalesmanName"].ToString();
                        item.Email = rdr["EmailUser"].ToString();
                        item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                        item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"].ToString());
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SignatureName = rdr["SignatureName"].ToString();
                        item.AllowApproval = Convert.ToInt32(rdr["AllowApproval"].ToString()) > 0;
                        data.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                inserLog("getSignByPoId", err, poId);
            }

            return data;
        }

        internal bool insertUpdatePOLineOther(mPoLineOther data)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPOLineOtherInsertUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@RecIdTab", data.RecIdTab);
                    sqlCmd.Parameters.AddWithValue("@PoId", data.PoId);
                    sqlCmd.Parameters.AddWithValue("@ProductCode", data.ProductCode);
                    sqlCmd.Parameters.AddWithValue("@ProductName", data.ProductName);
                    sqlCmd.Parameters.AddWithValue("@Qty", data.Qty);
                    sqlCmd.Parameters.AddWithValue("@Unit", data.Unit);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    return true;
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                string json = JsonConvert.SerializeObject(data);
                inserLog("SelectRofoBySalesIdYearMonth", err + "\n " + json, data.PoId);
                hasil = false;
            }

            return hasil;
        }

        internal List<mPO> getPoPPBySalesId(string salesId)
        {
            List<mPO> data = new List<mPO>();

            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPoPPBySalesId", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@SalesId", salesId);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPO item = new mPO();
                        item.PoId = rdr["PoId"].ToString();
                        item.PoCustNumberRef = rdr["PoCustNumberRef"].ToString();
                        item.PoDate = Convert.ToDateTime(rdr["PoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.PoById = rdr["PoById"].ToString();
                        item.PoByName = rdr["PoByName"].ToString();
                        item.PoViaId = rdr["PoViaId"].ToString();
                        item.PoViaName = rdr["PoViaName"].ToString();
                        item.ShipDate = Convert.ToDateTime(rdr["ShipDate"].ToString()).ToString("yyyy-MM-dd");
                        item.EndPeriodeDate = Convert.ToDateTime(rdr["EndPeriodeDate"].ToString()).ToString("yyyy-MM-dd");
                        item.Mechanisme = rdr["Mechanisme"].ToString();
                        item.ShipAddress = rdr["ShipAddress"].ToString();
                        item.Disc1 = Convert.ToDouble(rdr["Disc1"].ToString());
                        item.Disc2 = Convert.ToDouble(rdr["Disc2"].ToString());
                        item.CashDisc = Convert.ToDouble(rdr["DiscCash"].ToString());
                        item.isPP = Convert.ToInt32(rdr["isPP"].ToString()) > 0;
                        item.PicDist = rdr["PicDist"].ToString();
                        item.PicCust = rdr["PicCust"].ToString();
                        item.Notes = rdr["Notes"].ToString();
                        item.Signature = rdr["Signature"].ToString();
                        item.PoStatusId = Convert.ToInt32(rdr["PoStatusId"].ToString());
                        item.PoStatusName = rdr["PoStatusName"].ToString();
                        item.SoNo = rdr["SoNo"].ToString();
                        item.SoDate = Convert.ToDateTime(rdr["SoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.DoNo = rdr["DoNo"].ToString();
                        item.DoDate = Convert.ToDateTime(rdr["DoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ConfirmDate = Convert.ToDateTime(rdr["ConfirmDate"].ToString()).ToString("yyyy-MM-dd");
                        item.ModifiedDate = Convert.ToDateTime(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        item.KeteranganDetail = rdr["KeteranganDetail"].ToString(); 
                        item.isSellOut = Convert.ToInt32(rdr["isSellOut"].ToString()) > 0;
                        // item.customer = spCust.getCustomer(item.SalesmanId.ToString(), item.CustId.ToString());
                        //item.distBranch = spCustDist.SelectCustomerAndDistributorBranchById(item.CustId.ToString(), item.DistBranchId.ToString());
                        item.poLines = getPoLineById(item.PoId);
                        item.poLineOthers = getPoLineOtherById(item.PoId);
                        //item.sales = sales.SelectSalesById(item.SalesmanId.ToString());
                        data.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                inserLog("getPoPPBySalesId", err, salesId);
            }

            return data;
        }

        internal bool updatePoStatus(string salesId, List<stringBindingModel> stringlist)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {
                    DataTable table = new DataTable();
                    table.Columns.Add("id", typeof(String));
                    SqlCommand cmd = new SqlCommand("AndroPoUpdateStatusBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    foreach (stringBindingModel cb in stringlist)
                    {
                        table.Rows.Add(cb.id);
                    }

                    var pList = new SqlParameter("@liststring", SqlDbType.Structured);
                    pList.TypeName = "dbo.StringList";
                    pList.Value = table;
                    cmd.Parameters.Add(pList);

                    con.Open();
                    //hasil balikan belum ok
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        hasil = true;
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(stringlist);
                log.inserLog("updatePoStatus", ex.ToString() + "\n " + json, salesId.ToString());
                hasil = false;
            }

            return hasil;
        }
        //jangan lupa tambahkan query untuk cek tahun agar mengambil semua data yg sudah usang lebih dari 1 thn karena di yang lokal akan dihapus data yang sudah lebih dari setahun
        internal List<mPO> getPoNewUpdateBySalesIdBetween(string salesId, string dateFrom, string dateTo)
        {
            List<mPO> data = new List<mPO>();

            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPoUpdateBySalesIdBetween", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@SalesId", salesId);
                    sqlCmd.Parameters.AddWithValue("@dateFrom", dateFrom);
                    sqlCmd.Parameters.AddWithValue("@dateTo", dateTo);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPO item = new mPO();
                        item.PoId = rdr["PoId"].ToString();
                        item.PoCustNumberRef = rdr["PoCustNumberRef"].ToString();
                        item.PoDate = Convert.ToDateTime(rdr["PoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.PoById = rdr["PoById"].ToString();
                        item.PoByName = rdr["PoByName"].ToString();
                        item.PoViaId = rdr["PoViaId"].ToString();
                        item.PoViaName = rdr["PoViaName"].ToString();
                        item.ShipDate = Convert.ToDateTime(rdr["ShipDate"].ToString()).ToString("yyyy-MM-dd");
                        item.EndPeriodeDate = Convert.ToDateTime(rdr["EndPeriodeDate"].ToString()).ToString("yyyy-MM-dd");
                        item.Mechanisme = rdr["Mechanisme"].ToString();
                        item.ShipAddress = rdr["ShipAddress"].ToString();
                        item.Disc1 = Convert.ToDouble(rdr["Disc1"].ToString());
                        item.Disc2 = Convert.ToDouble(rdr["Disc2"].ToString());
                        item.CashDisc = Convert.ToDouble(rdr["DiscCash"].ToString());
                        item.isPP = Convert.ToInt32(rdr["isPP"].ToString()) > 0;
                        item.PicDist = rdr["PicDist"].ToString();
                        item.PicCust = rdr["PicCust"].ToString();
                        item.Notes = rdr["Notes"].ToString();
                        item.Signature = rdr["Signature"].ToString();
                        item.PoStatusId = Convert.ToInt32(rdr["PoStatusId"].ToString());
                        item.PoStatusName = rdr["PoStatusName"].ToString();
                        item.SoNo = rdr["SoNo"].ToString();
                        item.SoDate = Convert.ToDateTime(rdr["SoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.DoNo = rdr["DoNo"].ToString();
                        item.DoDate = Convert.ToDateTime(rdr["DoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ConfirmDate = Convert.ToDateTime(rdr["ConfirmDate"].ToString()).ToString("yyyy-MM-dd");
                        item.ModifiedDate = Convert.ToDateTime(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        item.KeteranganDetail = rdr["KeteranganDetail"].ToString();
                        item.isSellOut = Convert.ToInt32(rdr["isSellOut"].ToString()) > 0;
                        // item.customer = spCust.getCustomer(item.SalesmanId.ToString(), item.CustId.ToString());
                        //item.distBranch = spCustDist.SelectCustomerAndDistributorBranchById(item.CustId.ToString(), item.DistBranchId.ToString());
                        item.poLines = getPoLineById(item.PoId);
                        item.poLineOthers = getPoLineOtherById(item.PoId);
                        //item.sales = sales.SelectSalesById(item.SalesmanId.ToString());
                        data.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                inserLog("getPoPPBySalesId", err, salesId);
            }

            return data;
        }


        //get approval level WfTrans
        public WfTrans getWfTrans(String PoId)
        {
            WfTrans data = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand sqlCmd = new SqlCommand("AndroWfTransByPoId", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    sqlCmd.Parameters.AddWithValue("@PoId", PoId);

                    con.Open();
                    SqlDataReader rdr = sqlCmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        WfTrans item = new WfTrans();
                        item.RecId = Convert.ToInt32(rdr["RecId"].ToString());
                        item.WfTransId = Convert.ToInt32(rdr["WfTransId"].ToString());
                        item.RefId =rdr["RefId"].ToString();
                        item.Index = Convert.ToInt32(rdr["Index"].ToString());
                        item.UserId = rdr["UserId"].ToString();
                        item.StatusActionWf = Convert.ToInt32(rdr["StatusActionWf"].ToString());
                        item.ActionDate = Convert.ToDateTime(rdr["ActionDate"].ToString()).ToString("yyyy-MM-dd");
                        item.Notes = rdr["Notes"].ToString();
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd");
                        item.FullName = rdr["FullName"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.SalesmanId = rdr["SalesmanId"].ToString();

                        data=item;
                    }
                }
            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                spLog log = new spLog();
                log.inserLog("AndroWfTransByPoId", ex.ToString(), PoId);
            }
            return data;
        }
    }
}