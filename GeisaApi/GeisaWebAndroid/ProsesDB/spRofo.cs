using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using GeisaWebAndroid.Models;
using System.Configuration;
using System.Data.SqlClient;
using System.Data;
using Newtonsoft.Json;

namespace GeisaWebAndroid.ProsesDB
{
    public class spRofo
    {

        internal List<mRofo> addUpdateRofo(List<mRofo> data)
        {
            List<mRofo> items = new List<mRofo>();
            if (insertUpdateRofo(data))
            {
                if (data.Count > 0)
                    items = SelectRofoBySalesIdYearMonth(data[0].SalesmanId, data[0].Year, data[0].Month);
            }
            return items;
        }

        private bool insertUpdateRofo(List<mRofo> datalist)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
                foreach (mRofo data in datalist)
                {
                    using (SqlConnection con = new SqlConnection(conStr))
                    {

                        SqlCommand sqlCmd = new SqlCommand("AndroRofoInsertUpdate", con);
                        sqlCmd.CommandType = CommandType.StoredProcedure;

                        sqlCmd.Parameters.AddWithValue("@RofoId", data.RofoId);
                        sqlCmd.Parameters.AddWithValue("@Year", data.Year);
                        sqlCmd.Parameters.AddWithValue("@Month", data.Month);
                        sqlCmd.Parameters.AddWithValue("@SalesmanId", data.SalesmanId);
                        sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                        sqlCmd.Parameters.AddWithValue("@DistBranchId", data.DistBranchId);
                        sqlCmd.Parameters.AddWithValue("@ProductId", data.ProductId);
                        sqlCmd.Parameters.AddWithValue("@PriceId", data.PriceId);
                        sqlCmd.Parameters.AddWithValue("@ProductCode", data.ProductCode);
                        sqlCmd.Parameters.AddWithValue("@Qty", data.Qty);
                        sqlCmd.Parameters.AddWithValue("@UnitId", data.UnitId);
                        sqlCmd.Parameters.AddWithValue("@Value", data.Value);
                        if (data.StatusId == 0)
                        {
                            sqlCmd.Parameters.AddWithValue("@StatusId", 1);
                        }
                        else
                        {
                            sqlCmd.Parameters.AddWithValue("@StatusId", data.StatusId);
                        }
                        sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                        sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                        sqlCmd.Parameters.AddWithValue("@ModifiedDate", data.ModifiedDate);
                        sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);


                        con.Open();
                        int rowInserted = sqlCmd.ExecuteNonQuery();
                        hasil = true;
                    }
                }

                return hasil;

            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(datalist);
                log.inserLog("insertUpdateRofo", err + "\n " + json, "xx");
                hasil = false;
            }

            return hasil;
        }
        internal List<mRofoAktualisasi> getDataAktualisasi(string salesId, string year)
        {
            List<mRofoAktualisasi> items = new List<mRofoAktualisasi>();
            List<mRofoAktualisasi> data = getAktualisasi(salesId, year, 0);//cek berdasarkan target
            if (data != null && data.Count > 0)
            {
                items = data;
            }
            else
            {
                data = getAktualisasi(salesId, year, 1);//cek berdasarkan rofo
                if (data != null && data.Count > 0)
                {
                    items = data;
                }
                else
                {
                    items = getAktualisasi(salesId, year, 2);//cek berdasarkan value sales
                }
            }
            return items;
        }

        internal List<mRofoAktualisasi> getAktualisasi(string salesId, string year, int pilihan)
        {
            List<mRofoAktualisasi> items = new List<mRofoAktualisasi>();
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroRofoAktualisasiBySalesYear", con);
                    cmd.CommandType = CommandType.StoredProcedure;


                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@Year", year);
                    cmd.Parameters.AddWithValue("@TypeAktual", pilihan);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mRofoAktualisasi item = new mRofoAktualisasi();
                        string id = rdr["SalesmanId"].ToString() + rdr["Year"].ToString() + rdr["Month"].ToString();
                        item.RofoAktualisasiId = id;
                        item.Year = Convert.ToInt32(rdr["Year"].ToString());
                        item.Month = Convert.ToInt32(rdr["Month"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.ValueRofo = Convert.ToDouble(rdr["ValueRofo"].ToString());
                        item.ValueRofoDraft = Convert.ToDouble(rdr["ValueRofoDraft"].ToString());
                        item.ValueSales = Convert.ToDouble(rdr["ValueSales"].ToString());
                        item.ValueTarget = Convert.ToDouble(rdr["ValueTarget"].ToString());
                        item.UpdatedDate = rdr["UpdatedDate"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch(Exception ex)
            {
                String err = ex.ToString();
                spLog log = new spLog();
                log.inserLog("getAktualisasi", err, "xx");
            }
            

            return items;
        }

        internal List<mRofo> getRofo(string salesId, string year, string month)
        {
            List<mRofo> items = new List<mRofo>();

            items = SelectRofoBySalesIdYearMonth(Int32.Parse(salesId), Int32.Parse(year), Int32.Parse(month));

            return items;
        }

        internal List<mRofoTarget> getRofoTarget(string salesId, string year)
        {
            List<mRofoTarget> items = new List<mRofoTarget>();
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroRofoTargetBySalesYear", con);
                    cmd.CommandType = CommandType.StoredProcedure;


                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@Year", year);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mRofoTarget item = new mRofoTarget();
                        item.SalesTargetId = Convert.ToInt32(rdr["SalesTargetId"].ToString());
                        item.Year = Convert.ToInt32(rdr["Year"].ToString());
                        item.Month = Convert.ToInt32(rdr["Month"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.Value = Convert.ToDouble(rdr["Value"].ToString());
                        item.CreatedDate = rdr["CreatedDate"].ToString();
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = rdr["ModifiedDate"].ToString();
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch(Exception ex)
            {
                String err = ex.ToString();
                spLog log = new spLog();
                log.inserLog("getRofoTarget", err, salesId);
            }
            

            return items;
        }

        internal List<mRofo> SelectRofoBySalesIdYearMonth(int salesId, int year, int month)
        {
            List<mRofo> items = new List<mRofo>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroRofoBySalesYearMonth", con);
                    cmd.CommandType = CommandType.StoredProcedure;


                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@Year", year);
                    cmd.Parameters.AddWithValue("@Month", month);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mRofo item = new mRofo();
                        item.RofoId = rdr["RofoId"].ToString();
                        item.Year = Convert.ToInt32(rdr["Year"].ToString());
                        item.Month = Convert.ToInt32(rdr["Month"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"].ToString());
                        item.ProductId = Convert.ToInt32(rdr["ProductId"].ToString());
                        item.PriceId = Convert.ToInt32(rdr["PriceId"].ToString());
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.Qty = Convert.ToInt32(rdr["Qty"]);
                        item.UnitId = rdr["UnitId"].ToString();
                        item.Value = Convert.ToDouble(rdr["Value"].ToString());
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        item.StatusName = rdr["StatusName"].ToString();
                        item.CreatedDate = rdr["CreatedDate"].ToString();
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = rdr["ModifiedDate"].ToString();
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }

                }

                return items;
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                spLog log = new spLog();
                log.inserLog("SelectRofoBySalesIdYearMonth", err, salesId.ToString());
                return items;
            }

        }
    }
}