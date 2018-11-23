using GeisaWebAndroid.Models;
using GeisaWebAndroid.Modelsg;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.ProsesDB
{
    public class spCallPlan
    {
        //customer
        public List<mCallPlan> addUpdateCallPlan(putCallPlanBindingModel data)
        {
            List<mCallPlan> items = new List<mCallPlan>();
            try
            {
                for (int i = 0; i < data.callplanlist.Count; i++)
                {
                    AddUpdateCallPlanSingle(data.callplanlist[i]);
                }
                String now = DateTime.Now.ToString("yyyy-MM-dd");
                items = getCallPlanBySalesIdByDate(data.salesId, now);
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(data);
                log.inserLog("addUpdateCallPlan", ex.ToString() + "\n " + json, data.salesId.ToString());
            }


            return items;
        }

        public List<mCallPlan> UpdateCallPlanStatus(putCallPlanBindingModel data)
        {
            List<mCallPlan> items = new List<mCallPlan>();
            try
            {
                for (int i = 0; i < data.callplanlist.Count; i++)
                {
                    int hsl = UpdateCallPlanStatusSingle(data.callplanlist[i]);
                    if (hsl > -1)
                    {
                        items.Add(data.callplanlist[i]);
                    }
                }
                // String now = DateTime.Now.ToString("yyyy-MM-dd");
                // items = getCallPlanBySalesIdByDate(data.salesId, now);
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(data);
                log.inserLog("addUpdateCallPlan", ex.ToString() + "\n " + json, data.salesId.ToString());
            }


            return items;
        }


        public int UpdateCallPlanStatusSingle(mCallPlan data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanUpdateStatus", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanDate", data.CallPlanDate);
                    sqlCmd.Parameters.AddWithValue("@CallPlanTypeId", data.CallPlanTypeId);
                    sqlCmd.Parameters.AddWithValue("@SalesmanId", data.SalesmanId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanStatusId", data.CallPlanStatusId);
                    string notes = "";
                    if (data.Notes != null)
                        notes = data.Notes;
                    sqlCmd.Parameters.AddWithValue("@Notes", notes);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", now);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return rowInserted;
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(data);
                log.inserLog("UpdateCallPlanStatusSingle", ex.ToString() + "\n " + json, data.CallPlanId.ToString());
                return -1;
            }

        }

        internal List<mCallPlanDemo> getDemoBySalesIdByDateBetween(string salesId, string dateFrom, string dateTo)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mCallPlanDemo> items = new List<mCallPlanDemo>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanDemoBySalesBetween", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@dateFrom", dateFrom);
                    cmd.Parameters.AddWithValue("@dateTo", dateTo);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCallPlanDemo item = new mCallPlanDemo();
                        item.DemoId = rdr["DemoId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.DemoTitle = rdr["DemoTitle"].ToString();
                        item.DemoDescription = rdr["DemoDescription"].ToString();
                        item.DemoPeserta = Int32.Parse(rdr["DemoPeserta"].ToString());
                        item.DemoStatusId = Int32.Parse(rdr["DemoStatusId"].ToString());
                        item.DemoStatusName = rdr["StatusName"].ToString();
                        item.DemoDate = DateTime.Parse(rdr["DemoDate"].ToString()).ToString("yyyy-MM-dd");
                        item.DemoResponse = rdr["DemoResponse"].ToString();
                        string tglaction = "-", tglawal = "";
                        tglawal = Convert.ToDateTime(rdr["ResponseDate"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawal.Equals("1900-01-01") && !tglawal.Equals("2000-01-01"))
                            tglaction = Convert.ToDateTime(rdr["ResponseDate"].ToString()).ToString("dd MMMM yyyy HH:mm:ss");
                        item.ResponseDate = tglaction;
                        item.ResponseBy = rdr["ResponseBy"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getDemoBySalesIdByDateBetween", ex.ToString(), salesId.ToString());
            }
            return items;
        }

        internal List<mSample> getCallPlanSampleBetween(string salesId, string dateFrom, string dateTo)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mSample> items = new List<mSample>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanSampleBySalesBetween", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@dateFrom", dateFrom);
                    cmd.Parameters.AddWithValue("@dateTo", dateTo);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mSample item = new mSample();
                        item.SampleId = rdr["SampleId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.SampleFor = rdr["SampleFor"].ToString();
                        item.SampleDate = Convert.ToDateTime(rdr["SampleDate"].ToString()).ToString("yyyy-MM-dd");
                        item.SampleStatusId = Int32.Parse(rdr["SampleStatusId"].ToString());
                        item.SampleStatus = rdr["StatusName"].ToString();
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd HH:mm:ss"); 
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        string tglactionre = "-", tglawalre = "";
                        tglawalre = Convert.ToDateTime(rdr["SampleReceivedDate"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawalre.Equals("1900-01-01") && !tglawalre.Equals("2000-01-01"))
                            tglactionre = Convert.ToDateTime(rdr["SampleReceivedDate"].ToString()).ToString("dd MMMM yyyy HH:mm:ss");
                        item.SampleReceivedDate = tglactionre;
                        item.CustPic = rdr["CustPic"].ToString();
                        item.CustPicJabatan = rdr["CustPicJabatan"].ToString();
                        item.CustPicHp = rdr["CustPicHp"].ToString();
                        item.Note = rdr["Note"].ToString();
                        item.ModifiedDate = Convert.ToDateTime(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd HH:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        string tglaction = "-", tglawal = "";
                        tglawal = Convert.ToDateTime(rdr["SampleResponseDate"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawal.Equals("1900-01-01") && !tglawal.Equals("2000-01-01"))
                            tglaction = Convert.ToDateTime(rdr["SampleResponseDate"].ToString()).ToString("dd MMMM yyyy HH:mm:ss");
                        item.SampleResponseDate = tglaction;
                        item.SampleResponseNote = rdr["SampleResponseNote"].ToString();
                      
                        item.ProductOfRequest = getSampleProduct(item.SampleId,"Request");
                        item.ProductOfRealisasi = getSampleProduct(item.SampleId, "Realisasi");
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getCallPlanSampleBetween", ex.ToString(), salesId.ToString());
            }
            return items;
        }

        internal List<mProductSample> getSampleProduct(string sampleId, string type)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mProductSample> items = new List<mProductSample>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallplanSampleById", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@SampleId", sampleId);
                    cmd.Parameters.AddWithValue("@Type", type);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mProductSample item = new mProductSample();
                        item.SampleProdukId = rdr["SampleProdukId"].ToString();
                        item.SampleId = rdr["SampleId"].ToString();
                        item.ProductId = Int32.Parse(rdr["ProductId"].ToString());
                        item.Kemasan = rdr["Kemasan"].ToString();
                        item.Qty = Double.Parse(rdr["Qty"].ToString());
                        item.Note = rdr["Note"].ToString();
                        item.TypeRequest = rdr["TypeRequest"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd HH:mm:ss");
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getSampleProduct", ex.ToString(), sampleId.ToString());
            }
            return items;
        }

        internal List<mComplain> getComplainBySalesIdByDateBetween(string salesId, string dateFrom, string dateTo)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mComplain> items = new List<mComplain>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanComplainBySalesBetween", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@dateFrom", dateFrom);
                    cmd.Parameters.AddWithValue("@dateTo", dateTo);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mComplain item = new mComplain();
                        item.ComplainId = rdr["ComplainId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.SafetyFood = Int32.Parse(rdr["IsFoodSafety"].ToString()) > 0;
                        item.QualityFood = Int32.Parse(rdr["IsQualityFood"].ToString()) > 0;
                        item.QualityApplication = Int32.Parse(rdr["IsQualityApp"].ToString()) > 0;
                        item.QuantityAll = Int32.Parse(rdr["IsQuantity"].ToString()) > 0;
                        item.PackagingAll = Int32.Parse(rdr["IsPackaging"].ToString()) > 0;
                        item.ComplainStatusId = Int32.Parse(rdr["ComplainStatusId"].ToString());
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.ComplainStatusName = rdr["StatusName"].ToString();
                        item.SampleSendDate = DateTime.Parse(rdr["SampleSendDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CustPic = rdr["CustPic"].ToString();
                        item.CustPicJabatan = rdr["CustPicJabatan"].ToString();
                        item.CustPicHp = rdr["CustPicHp"].ToString();
                        item.ComplainNote = rdr["ComplainNote"].ToString();
                        item.ComplainPriority = rdr["ComplainPriority"].ToString();
                        item.ComplainResponse = rdr["ComplainResponse"].ToString();
                        string tglaction = "-", tglawal = "";
                        tglawal = Convert.ToDateTime(rdr["ComplainResponseDate"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawal.Equals("1900-01-01") && !tglawal.Equals("2000-01-01"))
                            tglaction = Convert.ToDateTime(rdr["ComplainResponseDate"].ToString()).ToString("dd MMMM yyyy HH:mm:ss");
                        item.ComplainResponseDate = tglaction;
                        item.ComplainResponseBy = rdr["ComplainResponseBy"].ToString();
                        item.ProductId = rdr["ProductId"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getComplainBySalesIdByDateBetween", ex.ToString(), salesId.ToString());
            }
            return items;
        }

        public int AddUpdateCallPlanSingle(mCallPlan data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanDate", data.CallPlanDate);
                    sqlCmd.Parameters.AddWithValue("@CallPlanTypeId", data.CallPlanTypeId);
                    sqlCmd.Parameters.AddWithValue("@SalesmanId", data.SalesmanId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanStatusId", data.CallPlanStatusId);
                    string notes = "";
                    if (data.Notes != null)
                        notes = data.Notes;
                    sqlCmd.Parameters.AddWithValue("@Notes", notes);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", now);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return rowInserted;
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(data);
                log.inserLog("AddUpdateCallPlanSingle", ex.ToString() + "\n " + json, data.CallPlanId.ToString());
                return -1;
            }

        }

        public mBiAll AndroCallPlanNoteBySalesCallPlanId(string callPlanNoteId, string callPlanId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            mBiAll items = null;
            spSales sales = new spSales();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanNoteByCallPlanId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@callPlanNoteId", callPlanNoteId);
                    cmd.Parameters.AddWithValue("@callPlanId", callPlanId);
                    //cmd.Parameters.AddWithValue("@SalesId", salesId);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mBiAll item = new mBiAll();
                        item.CallPlanNoteId = rdr["CallPlanNoteId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.BiCsTypeId = Int32.Parse(rdr["NoteTypeId"].ToString());
                        item.BiCsTypeJenis = rdr["Category"].ToString();
                        item.BiCsTypeName = rdr["NoteTypeName"].ToString();
                        item.BiCsTypeEmail = rdr["Email"].ToString();
                        item.Notes1 = rdr["Notes1"].ToString();
                        item.Notes2 = rdr["Notes2"].ToString();
                        item.Notes3 = rdr["Notes3"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        item.SalesmanId = Int32.Parse(rdr["SalesmanId"].ToString());
                        item.CallPlanTypeId = rdr["CallPlanTypeId"].ToString();
                        item.CallPlanTypeName = rdr["CallPlanTypeName"].ToString();
                        item.CallPlanDate = DateTime.Parse(rdr["CallPlanDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.CustName = rdr["CustName"].ToString();
                        item.AliasName = rdr["AliasName"].ToString();
                        item.Address = rdr["Address"].ToString();

                        item.salesman = sales.SelectSalesById(item.SalesmanId.ToString());
                        items = item;
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroCallPlanNoteByCallPlanId", ex.ToString(), callPlanNoteId);
            }
            return items;
        }

        public List<mCallPlanNote> getCallPlanNoteBySalesIdByDateBetween(string salesId, string dateFrom, string dateTo)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mCallPlanNote> items = new List<mCallPlanNote>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanNoteBySalesDateBetween", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@dateFrom", dateFrom);
                    cmd.Parameters.AddWithValue("@dateTo", dateTo);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCallPlanNote item = new mCallPlanNote();
                        item.CallPlanNoteId = rdr["CallPlanNoteId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.BiCsTypeId = Int32.Parse(rdr["NoteTypeId"].ToString());
                        item.BiCsTypeName = rdr["CallPlanNoteStatusName"].ToString();
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.Notes1 = rdr["Notes1"].ToString();
                        item.Notes2 = rdr["Notes2"].ToString();
                        item.Notes3 = rdr["Notes3"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getCallPlanNoteBySalesIdByDateBetween", ex.ToString(), salesId.ToString());
            }
            return items;
        }

        public List<mCallPlan> getCallPlanNewUpdateBySalesIdByDateBetween(string salesId, string dateFrom, string dateTo)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mCallPlan> items = new List<mCallPlan>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanBySalesDateNewUpdateBetween", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@dateFrom", dateFrom);
                    cmd.Parameters.AddWithValue("@dateTo", dateTo);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCallPlan item = new mCallPlan();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.CallPlanDate = DateTime.Parse(rdr["CallPlanDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CallPlanTypeId = rdr["CallPlanTypeId"].ToString();
                        item.CallPlanTypeName = rdr["CallPlanTypeName"].ToString();
                        item.SalesmanId = Int32.Parse(rdr["SalesmanId"].ToString());
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.CallPlanStatusId = Int32.Parse(rdr["CallPlanStatusId"].ToString());
                        item.CallPlanStatusName = rdr["CallPlanStatusName"].ToString();
                        item.Notes = rdr["Notes"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getCallPlanNewUpdateBySalesIdByDate", ex.ToString(), salesId.ToString());
            }
            return items;
        }

        internal mComplainJoin AndroCallPlanComplainBySalesComplainId(string complainId, string callPlanId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            mComplainJoin items = null;
            spSales sales = new spSales();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanComplainByCallPlanId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@ComplainId", complainId);
                    cmd.Parameters.AddWithValue("@CallPlanId", callPlanId);
                    //cmd.Parameters.AddWithValue("@SalesId", salesId);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mComplainJoin item = new mComplainJoin();
                        item.ComplainId = rdr["ComplainId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.SafetyFood = Int32.Parse(rdr["IsFoodSafety"].ToString()) > 0;
                        item.QualityFood = Int32.Parse(rdr["IsQualityFood"].ToString()) > 0;
                        item.QualityApplication = Int32.Parse(rdr["IsQualityApp"].ToString()) > 0;
                        item.QuantityAll = Int32.Parse(rdr["IsQuantity"].ToString()) > 0;
                        item.PackagingAll = Int32.Parse(rdr["IsPackaging"].ToString()) > 0;
                        item.ComplainStatusId = Int32.Parse(rdr["ComplainStatusId"].ToString());
                        item.ComplainStatusName = rdr["StatusName"].ToString();
                        item.SampleSendDate = DateTime.Parse(rdr["SampleSendDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CustPic = rdr["CustPic"].ToString();
                        item.CustPicJabatan = rdr["CustPicJabatan"].ToString();
                        item.CustPicHp = rdr["CustPicHp"].ToString();
                        item.ComplainNote = rdr["ComplainNote"].ToString();
                        item.ComplainPriority = rdr["ComplainPriority"].ToString();
                        item.ComplainResponse = rdr["ComplainResponse"].ToString();
                        item.ComplainResponseDate = DateTime.Parse(rdr["ComplainResponseDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ComplainResponseBy = rdr["ComplainResponseBy"].ToString();
                        item.ProductId = rdr["ProductId"].ToString();
                        item.ProductIdName = rdr["ProductIdName"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        item.SalesmanId = Int32.Parse(rdr["SalesmanId"].ToString());
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.CustName = rdr["CustName"].ToString();
                        item.AliasName = rdr["AliasName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.CustCode = rdr["CustCode"].ToString();

                        item.salesman = sales.SelectSalesById(item.SalesmanId.ToString());
                        items = item;
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroCallPlanComplainBySalesComplainId", ex.ToString(), complainId);
            }
            return items;
        }

        public List<mCallPlan> getCallPlanNewUpdateBySalesIdByDate(string salesId, string date)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mCallPlan> items = new List<mCallPlan>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanBySalesDateNewUpdate", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@StatusNewUpdate", 1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCallPlan item = new mCallPlan();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.CallPlanDate = DateTime.Parse(rdr["CallPlanDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CallPlanTypeId = rdr["CallPlanTypeId"].ToString();
                        item.CallPlanTypeName = rdr["CallPlanTypeName"].ToString();
                        item.SalesmanId = Int32.Parse(rdr["SalesmanId"].ToString());
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.CallPlanStatusId = Int32.Parse(rdr["CallPlanStatusId"].ToString());
                        item.CallPlanStatusName = rdr["CallPlanStatusName"].ToString();
                        item.Notes = rdr["Notes"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getCallPlanNewUpdateBySalesIdByDate", ex.ToString(), salesId.ToString());
            }
            return items;
        }

        public List<mCallPlan> getCallPlanBySalesIdByDate(string salesId, string date)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mCallPlan> items = new List<mCallPlan>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCallPlanBySalesDate", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    cmd.Parameters.AddWithValue("@CallPlanDate", date);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCallPlan item = new mCallPlan();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.CallPlanDate = DateTime.Parse(rdr["CallPlanDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CallPlanTypeId = rdr["CallPlanTypeId"].ToString();
                        item.CallPlanTypeName = rdr["CallPlanTypeName"].ToString();
                        item.SalesmanId = Int32.Parse(rdr["SalesmanId"].ToString());
                        item.CustId = Int32.Parse(rdr["CustId"].ToString());
                        item.CallPlanStatusId = Int32.Parse(rdr["CallPlanStatusId"].ToString());
                        item.CallPlanStatusName = rdr["CallPlanStatusName"].ToString();
                        item.Notes = rdr["Notes"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = DateTime.Parse(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("getCallPlanBySalesIdByDate", ex.ToString(), salesId.ToString());
            }
            return items;
        }



        internal bool updateCallPlanStatus(string salesId, List<stringBindingModel> stringlist)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {
                    DataTable table = new DataTable();
                    table.Columns.Add("id", typeof(String));
                    SqlCommand cmd = new SqlCommand("AndroCallPlanUpdateStatusBySalesId", con);
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
                log.inserLog("updateCallPlanStatus", ex.ToString() + "\n " + json, salesId.ToString());
                hasil = false;
            }

            return hasil;
        }

        public int AddUpdateCallPlanNoteSingle(mCallPlanNote data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanNoteAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    sqlCmd.Parameters.AddWithValue("@CallPlanNoteId", data.CallPlanNoteId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@BiTypeId", data.BiCsTypeId);
                    sqlCmd.Parameters.AddWithValue("@Notes1", data.Notes1);
                    sqlCmd.Parameters.AddWithValue("@Notes2", data.Notes2);
                    sqlCmd.Parameters.AddWithValue("@Notes3", data.Notes3);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", now);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return rowInserted;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("AddUpdateCallPlanNoteSingle", ex.ToString() + "\n " + json, data.CallPlanId.ToString());
                    return -1;
                }
            }
        }

        public int AddUpdateCallPlanDemoSingle(mCallPlanDemo data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanDemoAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    sqlCmd.Parameters.AddWithValue("@DemoId", data.DemoId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@DemoTitle", data.DemoTitle);
                    sqlCmd.Parameters.AddWithValue("@DemoDescription", data.DemoDescription);
                    sqlCmd.Parameters.AddWithValue("@DemoPeserta", data.DemoPeserta);
                    sqlCmd.Parameters.AddWithValue("@DemoStatusId", data.DemoStatusId);
                    sqlCmd.Parameters.AddWithValue("@DemoDate", data.DemoDate);
                    sqlCmd.Parameters.AddWithValue("@DemoResponse", data.DemoResponse);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@ResponseDate", data.ResponseDate);
                    sqlCmd.Parameters.AddWithValue("@ResponseBy", data.ResponseBy);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", now);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return rowInserted;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("AddUpdateCallPlanDemoSingle", ex.ToString() + "\n " + json, data.CallPlanId.ToString());
                    return -1;
                }
            }
        }

        public int AddUpdateCallPlanComplainSingle(mComplain data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanComplainAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    sqlCmd.Parameters.AddWithValue("@ComplainId", data.ComplainId);
                    sqlCmd.Parameters.AddWithValue("@SafetyFood", data.SafetyFood);
                    sqlCmd.Parameters.AddWithValue("@QualityFood", data.QualityFood);
                    sqlCmd.Parameters.AddWithValue("@QualityApplication", data.QualityApplication);
                    sqlCmd.Parameters.AddWithValue("@QuantityAll", data.QuantityAll);
                    sqlCmd.Parameters.AddWithValue("@PackagingAll", data.PackagingAll);
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@ProductId", data.ProductId);
                    sqlCmd.Parameters.AddWithValue("@ProductName", data.ProductName);
                    sqlCmd.Parameters.AddWithValue("@ComplainStatusId", data.ComplainStatusId);
                    sqlCmd.Parameters.AddWithValue("@SampleSendDate", data.SampleSendDate);
                    sqlCmd.Parameters.AddWithValue("@CustPic", data.CustPic);
                    sqlCmd.Parameters.AddWithValue("@CustPicJabatan", data.CustPicJabatan);
                    sqlCmd.Parameters.AddWithValue("@CustPicHp", data.CustPicHp);
                    sqlCmd.Parameters.AddWithValue("@ComplainNote", data.ComplainNote);
                    sqlCmd.Parameters.AddWithValue("@ComplainPriority", data.ComplainPriority);
                    sqlCmd.Parameters.AddWithValue("@ComplainResponse", data.ComplainResponse);
                    sqlCmd.Parameters.AddWithValue("@ComplainResponseDate", data.ComplainResponseDate);
                    sqlCmd.Parameters.AddWithValue("@ComplainResponseBy", data.ComplainResponseBy);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", now);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return rowInserted;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("AddUpdateCallPlanComplainSingle", ex.ToString() + "\n " + json, data.CallPlanId.ToString());
                    return -1;
                }
            }
        }
        //transcallplan sample
        public int AddUpdateCallPlanSampleSingle(mSample data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanSampleAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@SampleId", data.SampleId);
                    sqlCmd.Parameters.AddWithValue("@CallPlanId", data.CallPlanId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@SampleFor", data.SampleFor);
                    sqlCmd.Parameters.AddWithValue("@SampleDate", data.SampleDate);
                    sqlCmd.Parameters.AddWithValue("@SampleStatusId", data.SampleStatusId);
                    sqlCmd.Parameters.AddWithValue("@SampleStatus", data.SampleStatus);
                    sqlCmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    sqlCmd.Parameters.AddWithValue("@SampleReceivedDate", data.SampleReceivedDate);
                    sqlCmd.Parameters.AddWithValue("@CustPic", data.CustPic);
                    sqlCmd.Parameters.AddWithValue("@CustPicJabatan", data.CustPicJabatan);
                    sqlCmd.Parameters.AddWithValue("@CustPicHp", data.CustPicHp);
                    sqlCmd.Parameters.AddWithValue("@Note", data.Note);
                    sqlCmd.Parameters.AddWithValue("@ModifiedDate", now);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", data.ModifiedBy);
                    sqlCmd.Parameters.AddWithValue("@SampleResponseDate", data.SampleResponseDate);
                    sqlCmd.Parameters.AddWithValue("@SampleResponseNote", data.SampleResponseNote);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    con.Close();
                    if (null != data.ProductOfRequest && data.ProductOfRequest.Count > 0)
                    {
                        foreach (mProductSample cs in data.ProductOfRequest)
                        {
                            rowInserted = AddUpdateCallPlanSampleProductSingle(cs);
                        }
                    }
                    if (null != data.ProductOfRealisasi && data.ProductOfRealisasi.Count > 0)
                    {
                        foreach (mProductSample cs in data.ProductOfRealisasi)
                        {
                            rowInserted = AddUpdateCallPlanSampleProductSingle(cs);
                        }
                    }
                    return rowInserted;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("AddUpdateCallPlanSampleSingle", ex.ToString() + "\n " + json, data.CallPlanId.ToString());
                    return -1;
                }
            }
        }

        public int AddUpdateCallPlanSampleProductSingle(mProductSample data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCallPlanSampleProductAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@SampleProdukId", data.SampleProdukId);
                    sqlCmd.Parameters.AddWithValue("@SampleId", data.SampleId);
                    sqlCmd.Parameters.AddWithValue("@ProductId", data.ProductId);
                    sqlCmd.Parameters.AddWithValue("@Kemasan", data.Kemasan);
                    sqlCmd.Parameters.AddWithValue("@Qty", data.Qty);
                    sqlCmd.Parameters.AddWithValue("@Note", data.Note);
                    sqlCmd.Parameters.AddWithValue("@ProductCode", data.ProductCode);
                    sqlCmd.Parameters.AddWithValue("@TypeRequest", data.TypeRequest);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    con.Close();
                    return rowInserted;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("AddUpdateCallPlanSampleProductSingle", ex.ToString() + "\n " + json, data.SampleId.ToString());
                    return -1;
                }
            }
        }

        internal List<mSample> SelectSampleNotFinishSalesCustomerId(string salesId, string custId)
        {
            List<mSample> items = new List<mSample>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroCallPlanSampleBySalesCustId", con);
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.AddWithValue("@SalesId", salesId);
                    cmd.Parameters.AddWithValue("@CustId", custId);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {

                        mSample item = new mSample();
                        item.SampleId = rdr["SampleId"].ToString();
                        item.CallPlanId = rdr["CallPlanId"].ToString();
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.SampleFor = rdr["SampleFor"].ToString();
                        item.SampleDate = rdr["SampleDate"].ToString();
                        item.SampleStatusId = Convert.ToInt32(rdr["SampleStatusId"].ToString());
                        item.SampleStatus = rdr["StatusName"].ToString();
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.SampleReceivedDate = Convert.ToDateTime(rdr["SampleReceivedDate"].ToString()).ToString("yyyy-MM-dd");
                        item.CustPic = rdr["CustPic"].ToString();
                        item.CustPicJabatan = rdr["CustPicJabatan"].ToString();
                        item.CustPicHp = rdr["CustPicHp"].ToString();
                        item.Note = rdr["Note"].ToString();
                        item.ModifiedDate = Convert.ToDateTime(rdr["ModifiedDate"].ToString()).ToString("yyyy-MM-dd");
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        // item.StatusSend = Convert.ToInt32(rdr["StatusSend"].ToString());

                        item.SampleResponseDate = Convert.ToDateTime(rdr["SampleResponseDate"].ToString()).ToString("yyyy-MM-dd");
                        item.SampleResponseNote = rdr["SampleResponseNote"].ToString();

                        item.ProductOfRequest = SelectSampleProductByType(item.SampleId, "request");
                        item.ProductOfRealisasi = SelectSampleProductByType(item.SampleId, "realisasi");
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectSampleNotFinishSalesCustomerId", ex.ToString(), salesId);
            }


            return items;
        }

        internal List<mProductSample> SelectSampleProductByType(string sampleId, string typerequest)
        {
            List<mProductSample> items = new List<mProductSample>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroCallPlanSampleProductBySalesCustId", con);
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.AddWithValue("@SampleId", sampleId);
                    cmd.Parameters.AddWithValue("@TypeRequest", typerequest);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mProductSample item = new mProductSample();
                        item.SampleProdukId = rdr["SampleProdukId"].ToString();
                        item.SampleId = rdr["SampleId"].ToString();
                        item.ProductId = Convert.ToInt32(rdr["ProductId"].ToString());
                        item.Kemasan = rdr["Kemasan"].ToString();
                        item.Qty = Convert.ToDouble(rdr["Qty"].ToString());
                        item.Note = rdr["Note"].ToString();
                        item.TypeRequest = rdr["TypeRequest"].ToString();
                        item.Selected = Convert.ToInt32(rdr["Selected"].ToString()) > 0;
                        item.ProductName = rdr["ProductName"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        // item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd");
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectSampleProductByType", ex.ToString(), sampleId);
            }


            return items;
        }

        internal List<mTodoList> SelectTodoListBySalesCustomerId(string salesId, string custId)
        {
            List<mTodoList> items = new List<mTodoList>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroTodoListBySalesCustId", con);
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.AddWithValue("@SalesId", salesId);
                    cmd.Parameters.AddWithValue("@CustId", custId);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mTodoList item = new mTodoList();
                        item.RecId = Convert.ToInt32(rdr["RecId"]);
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.Reference = rdr["Reference"].ToString();
                        item.CustName = rdr["CustName"].ToString();
                        item.Category = rdr["Category"].ToString();
                        item.Title = rdr["Title"].ToString();
                        item.DocDate = Convert.ToDateTime(rdr["DocDate"].ToString()).ToString("yyyy-MM-dd");
                        item.Detail = rdr["Detail"].ToString();
                        item.Status = rdr["Status"].ToString();
                        item.StatusDetail = rdr["StatusDetail"].ToString();
                        item.StatusRead = Convert.ToInt32(rdr["IsRead"].ToString()) > 0;
                        item.StatusId = Convert.ToInt32(rdr["TtdStatusId"].ToString());
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd");
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectTodoListBySalesCustomerId", ex.ToString(), salesId);
            }


            return items;
        }

        internal bool UpdateTodoList(mTodoList todo)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroTodoListAddUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@RecId", todo.RecId);
                    //sqlCmd.Parameters.AddWithValue("@CustId", todo.CustId);
                    //sqlCmd.Parameters.AddWithValue("@CustName", todo.CustName);
                    //sqlCmd.Parameters.AddWithValue("@Category", todo.Category);
                    //sqlCmd.Parameters.AddWithValue("@Title", todo.Title);
                    //sqlCmd.Parameters.AddWithValue("@DocDate", todo.DocDate);
                    //sqlCmd.Parameters.AddWithValue("@Detail", todo.Detail);
                    //sqlCmd.Parameters.AddWithValue("@StatusDetail", todo.StatusDetail);
                    //sqlCmd.Parameters.AddWithValue("@CreatedDate", todo.CreatedDate);
                    sqlCmd.Parameters.AddWithValue("@IsRead", Convert.ToInt32(todo.StatusRead ? 1 : 0));
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    con.Close();
                    return true;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(todo);
                    log.inserLog("UpdateTodoList", ex.ToString() + "\n " + json, todo.RecId.ToString());
                    return false;
                }
            }
        }

        internal bool updateTodoListStatusNew(string salesId, List<stringBindingModel> stringlist)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {
                    DataTable table = new DataTable();
                    table.Columns.Add("id", typeof(String));
                    SqlCommand cmd = new SqlCommand("AndroTodoListUpdateStatusNew", con);
                    cmd.CommandType = CommandType.StoredProcedure;

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
                log.inserLog("updateCallPlanStatus", ex.ToString() + "\n " + json, salesId.ToString());
                hasil = false;
            }

            return hasil;
        }
    }
}