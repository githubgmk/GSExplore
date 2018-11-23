using GeisaWebAndroid.Models;
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
    public class spCustomer
    {
        spCustomerDistributor customerDistributor;
        spMaster master;
        spSales sales;
        public spCustomer()
        {
            customerDistributor = new spCustomerDistributor();
            master = new spMaster();
            sales = new spSales();
        }

        //customer
        public List<mCustomer> SelectCustomerBySalesId(String salesId)
        {
            List<mCustomer> items = new List<mCustomer>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomer item = new mCustomer();

                        List<mCustomerAndBranch> customerAndBranch = customerDistributor.SelectCustomerAndDistributorBranchByCustomerId(rdr["CustId"].ToString());

                        item.CustId = rdr["CustId"].ToString();
                        item.CustGroupId = rdr["CustGroupId"].ToString();
                        item.CustName = rdr["CustName"].ToString();
                        item.AliasName = rdr["AliasName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Lat = Convert.ToDouble(rdr["Lat"].ToString());
                        item.Lng = Convert.ToDouble(rdr["Lng"].ToString());
                        item.Radius = Convert.ToDouble(rdr["Radius"].ToString());
                        item.Pic = rdr["Pic"].ToString();
                        item.PicJabatan = rdr["PicJabatan"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Hp = rdr["Hp"].ToString();
                        item.Website = rdr["Website"].ToString();
                        item.StsPkpId = rdr["StsPkpId"].ToString();
                        item.StsPkpName = rdr["StsPkpName"].ToString();
                        item.Npwp = rdr["Npwp"].ToString();
                        item.CreditLimit = Convert.ToDouble(rdr["CreditLimit"].ToString());
                        item.Top = rdr["Top"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CustById = rdr["CustById"].ToString();
                        item.CustByName = rdr["CustByName"].ToString();
                        item.JoinDate = rdr["JoinDate"].ToString();
                        item.EcTolerance = Convert.ToInt32(rdr["EcTolerance"].ToString());
                        item.FreqTypeId = rdr["FreqTypeId"].ToString();
                        item.FreqTypeName = rdr["FreqTypeName"].ToString();
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"]);
                        item.CustLevelId = Convert.ToInt32(rdr["CustLevelId"]);
                        item.CustStatusId = rdr["CustStatusId"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();
                        item.customerAndBranch = customerAndBranch;
                        items.Add(item);
                    }
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    log.inserLog("SelectCustomerBySalesId", ex.ToString(), salesId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        internal mCustomerClasification SelectCustomerClasificationByCustId(string custId)
        {
            mCustomerClasification items = new mCustomerClasification();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerClasificationByCustId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@CustId";
                    par1.Value = custId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomerClasification item = new mCustomerClasification();
                        item.RecId = Convert.ToInt32(rdr["RecId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"].ToString());
                        item.ChannelWayId = Convert.ToInt32(rdr["ChannelWayId"].ToString());
                        item.ChannelGradeId = Convert.ToInt32(rdr["ChannelGradeId"].ToString());
                        item.ChannelAppId = Convert.ToInt32(rdr["ChannelAppId"].ToString());
                        item.ChannelStagingId = Convert.ToInt32(rdr["ChannelStagingId"].ToString());
                        item.PeriodeStart = Convert.ToDateTime(rdr["PeriodeStart"].ToString()).ToString("yyyy-MM-dd");
                        item.PeriodeEnd = Convert.ToDateTime(rdr["PeriodeEnd"].ToString()).ToString("yyyy-MM-dd");
                        item.Active = Convert.ToInt32(rdr["Active"].ToString());
                        item.CreatedDate = Convert.ToDateTime(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss tt"); 
                        item.ChannelCode = rdr["ChannelCode"].ToString();
                        item.ChannelWayName = rdr["ChannelWayName"].ToString();
                        item.ChannelGroupId = Convert.ToInt32(rdr["ChannelGroupId"].ToString());
                        item.Description = rdr["Description"].ToString();
                        item.Status = Convert.ToInt32(rdr["Status"].ToString());
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"].ToString());
                        item.ChannelName = rdr["ChannelName"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Signature = rdr["Signature"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        item.ChannelGradeCode = rdr["ChannelGradeCode"].ToString();
                        item.ChannelGradeName = rdr["ChannelGradeName"].ToString();
                        item.ChannelGradeDescription = rdr["ChannelGradeDescription"].ToString();
                        item.ChannelAppCode = rdr["ChannelAppCode"].ToString();
                        item.ChannelAppName = rdr["ChannelAppName"].ToString();
                        item.ChannelStagingCode = rdr["ChannelStagingCode"].ToString();
                        item.ChannelStagingName = rdr["ChannelStagingName"].ToString();
                        item.ChannelStagingDescription = rdr["ChannelStagingDescription"].ToString();
                        item.ChannelStagingShareWalet = rdr["ChannelStagingShareWalet"].ToString();

                        item.StagingApproach = ChannelStagingApproach(item.ChannelStagingId);
                        item.Produk = ChannelProduk(item.ChannelAppId);
                        items = item;


                    }
                }
                catch (Exception ex)
                {
                    string err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectCustomerClasificationByCustId", ex.ToString(), custId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        //check again
        internal List<mCustomerClasification.ChannelStagingApproach> ChannelStagingApproach(int channelStagingId)
        {
            List<mCustomerClasification.ChannelStagingApproach> items = new List<mCustomerClasification.ChannelStagingApproach>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerStagingApproachByChannelStagingId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@ChannelStagingId";
                    par1.Value = channelStagingId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    bool sama = true;
                    String stagingFor = "";
                    mCustomerClasification.ChannelStagingApproach item = new mCustomerClasification.ChannelStagingApproach();
                    while (rdr.Read())
                    {

                        String stagingForNew = Convert.ToInt32(rdr["ChannelStagingId"].ToString()) + rdr["ChannelStagingFor"].ToString();

                        if (!stagingFor.Equals(stagingForNew))
                        {
                            if (item.Item != null && item.Item.Count > 0)
                                items.Add(item);
                            item = new mCustomerClasification.ChannelStagingApproach();
                            item.Item = new List<mCustomerClasification.ChannelStagingApproach.StagingItem>();

                            item.RecId = Convert.ToInt32(rdr["RecId"].ToString());
                            item.ChannelStagingItemId = Convert.ToInt32(rdr["ChannelStagingItemId"].ToString());
                            item.ChannelStagingId = Convert.ToInt32(rdr["ChannelStagingId"].ToString());
                            item.ChannelStagingFor = rdr["ChannelStagingFor"].ToString();
                            item.ChannelStagingApproachStatus = Convert.ToInt32(rdr["ChannelStagingApproachStatus"].ToString());
                            item.ChannelStagingCode = rdr["ChannelStagingCode"].ToString();
                            item.ChannelStagingName = rdr["ChannelStagingName"].ToString();
                            item.ChannelStagingDescription = rdr["ChannelStagingDescription"].ToString();
                            item.ChannelStagingShareWalet = rdr["ChannelStagingShareWalet"].ToString();

                            //add item first
                            mCustomerClasification.ChannelStagingApproach.StagingItem itemapproach = new mCustomerClasification.ChannelStagingApproach.StagingItem();
                            itemapproach.ChannelStagingItemId = Convert.ToInt32(rdr["ChannelStagingItemId"].ToString());
                            itemapproach.ChannelStagingItemCode = rdr["ChannelStagingItemCode"].ToString();
                            itemapproach.ChannelStagingItemName = rdr["ChannelStagingItemName"].ToString();
                            itemapproach.ChannelStagingItemDesc = rdr["ChannelStagingItemDesc"].ToString();
                            itemapproach.ChannelStagingItemStatus = Convert.ToInt32(rdr["ChannelStagingItemStatus"].ToString());
                            item.Item.Add(itemapproach);
                        }
                        else
                        {
                            mCustomerClasification.ChannelStagingApproach.StagingItem itemapproach = new mCustomerClasification.ChannelStagingApproach.StagingItem();
                            itemapproach.ChannelStagingItemId = Convert.ToInt32(rdr["ChannelStagingItemId"].ToString());
                            itemapproach.ChannelStagingItemCode = rdr["ChannelStagingItemCode"].ToString();
                            itemapproach.ChannelStagingItemName = rdr["ChannelStagingItemName"].ToString();
                            itemapproach.ChannelStagingItemDesc = rdr["ChannelStagingItemDesc"].ToString();
                            itemapproach.ChannelStagingItemStatus = Convert.ToInt32(rdr["ChannelStagingItemStatus"].ToString());
                            item.Item.Add(itemapproach);

                        }
                        stagingFor = stagingForNew;

                    }
                    if (item != null) items.Add(item);

                }
                catch (Exception ex)
                {
                    string err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("AndroCustomerStagingApproachByChannelStagingId", ex.ToString(), channelStagingId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }
        //check lagi
        internal List<mCustomerClasification.ChannelProduk> ChannelProduk(int channelAppId)
        {
            List<mCustomerClasification.ChannelProduk> items = new List<mCustomerClasification.ChannelProduk>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerChannelProdukByChannelAppId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@ChannelAppId";
                    par1.Value = channelAppId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {

                        mCustomerClasification.ChannelProduk item = new mCustomerClasification.ChannelProduk();
                        item.ChannelAppProdukRelasiId = Convert.ToInt32(rdr["ChannelAppProdukRelasiId"].ToString());
                        item.ChannelAppId = Convert.ToInt32(rdr["ChannelAppId"].ToString());
                        item.ProdukId = Convert.ToInt32(rdr["ProdukId"].ToString());
                        item.Status = Convert.ToInt32(rdr["Status"].ToString());
                        item.ProductName = rdr["ProductName"].ToString();
                        item.ProductSimpleDescription = rdr["ProductSimpleDescription"].ToString();

                        items.Add(item);


                    }
                }
                catch (Exception ex)
                {
                    string err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("AndroCustomerChannelProdukByChannelAppId", ex.ToString(), channelAppId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }
        internal bool ChangeCustSales(int custId, int salesId, int userId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCustomerUpdateSales", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@CustId", custId);
                    sqlCmd.Parameters.AddWithValue("@SalesId", salesId);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", userId);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return true;
                }
            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                spLog log = new spLog();

                log.inserLog("AndroCustomerUpdateSales", ex.ToString(), salesId.ToString());
                return false;
            }
        }

        internal bool ResetCustLokasi(int CustId, string ModifBy)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCustomerUpdateLokasi", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;


                    sqlCmd.Parameters.AddWithValue("@CustId", CustId);
                    sqlCmd.Parameters.AddWithValue("@ModifiedBy", ModifBy);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return true;
                }
            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                spLog log = new spLog();

                log.inserLog("AndroCustomerUpdateLokasi", ex.ToString(), CustId.ToString());
                return false;
            }
        }

        public mCustomer getCustomer(string salesId, string custId)
        {
            mCustomer items = new mCustomer();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerByCustomerSalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    SqlParameter par2 = new SqlParameter();
                    par2.ParameterName = "@idCust";
                    par2.Value = custId;
                    cmd.Parameters.Add(par2);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomer item = new mCustomer();
                        List<mCustomerAndBranch> customerAndBranch = customerDistributor.SelectCustomerAndDistributorBranchByCustomerId(rdr["CustId"].ToString());
                        item.CustId = rdr["CustId"].ToString();
                        item.CustGroupId = rdr["CustGroupId"].ToString();
                        item.CustName = rdr["CustName"].ToString();
                        item.AliasName = rdr["AliasName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Lat = Convert.ToDouble(rdr["Lat"].ToString());
                        item.Lng = Convert.ToDouble(rdr["Lng"].ToString());
                        item.Radius = Convert.ToDouble(rdr["Radius"].ToString());
                        item.Pic = rdr["Pic"].ToString();
                        item.PicJabatan = rdr["PicJabatan"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Hp = rdr["Hp"].ToString();
                        item.Website = rdr["Website"].ToString();
                        item.StsPkpId = rdr["StsPkpId"].ToString();
                        item.StsPkpName = rdr["StsPkpName"].ToString();
                        item.Npwp = rdr["Npwp"].ToString();
                        item.CreditLimit = Convert.ToDouble(rdr["CreditLimit"].ToString());
                        item.Top = rdr["Top"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CustById = rdr["CustById"].ToString();
                        item.CustByName = rdr["CustByName"].ToString();
                        item.JoinDate = rdr["JoinDate"].ToString();
                        item.EcTolerance = Convert.ToInt32(rdr["EcTolerance"].ToString());
                        item.FreqTypeId = rdr["FreqTypeId"].ToString();
                        item.FreqTypeName = rdr["FreqTypeName"].ToString();
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"]);
                        item.CustLevelId = Convert.ToInt32(rdr["CustLevelId"]);
                        item.CustStatusId = rdr["CustStatusId"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();
                        item.customerAndBranch = customerAndBranch;
                        item.channel = master.SelectChannelById(item.ChannelId.ToString());
                        items = item;
                    }
                }
                catch (Exception ex)
                {
                    string err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("getCustomer", ex.ToString(), salesId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        public mCustomerJoin getCustomerJoin(string salesId, string custId)
        {
            mCustomerJoin items = new mCustomerJoin();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerByCustomerSalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    SqlParameter par2 = new SqlParameter();
                    par2.ParameterName = "@idCust";
                    par2.Value = custId;
                    cmd.Parameters.Add(par2);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomerJoin item = new mCustomerJoin();
                        List<mCustomerAndBranch> customerAndBranch = customerDistributor.SelectCustomerAndDistributorBranchByCustomerId(rdr["CustId"].ToString());
                        item.CustId = rdr["CustId"].ToString();
                        item.CustGroupId = rdr["CustGroupId"].ToString();
                        item.CustName = rdr["CustName"].ToString();
                        item.AliasName = rdr["AliasName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Lat = Convert.ToDouble(rdr["Lat"].ToString());
                        item.Lng = Convert.ToDouble(rdr["Lng"].ToString());
                        item.Radius = Convert.ToDouble(rdr["Radius"].ToString());
                        item.Pic = rdr["Pic"].ToString();
                        item.PicJabatan = rdr["PicJabatan"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Hp = rdr["Hp"].ToString();
                        item.Website = rdr["Website"].ToString();
                        item.StsPkpId = rdr["StsPkpId"].ToString();
                        item.StsPkpName = rdr["StsPkpName"].ToString();
                        item.Npwp = rdr["Npwp"].ToString();
                        item.CreditLimit = Convert.ToDouble(rdr["CreditLimit"].ToString());
                        item.Top = rdr["Top"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CustById = rdr["CustById"].ToString();
                        item.CustByName = rdr["CustByName"].ToString();
                        item.JoinDate = rdr["JoinDate"].ToString();
                        item.EcTolerance = Convert.ToInt32(rdr["EcTolerance"].ToString());
                        item.FreqTypeId = rdr["FreqTypeId"].ToString();
                        item.FreqTypeName = rdr["FreqTypeName"].ToString();
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"]);
                        item.CustLevelId = Convert.ToInt32(rdr["CustLevelId"]);
                        item.CustStatusId = rdr["CustStatusId"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();
                        item.CreatedDate = DateTime.Parse(rdr["CreatedDate"].ToString()).ToString("yyyy-MM-dd");
                        item.customerAndBranch = customerAndBranch;
                        item.channel = master.SelectChannelById(item.ChannelId.ToString());
                        item.salesman = sales.SelectSalesmanById(item.SalesmanId.ToString());
                        items = item;
                    }
                }
                catch (Exception ex)
                {
                    string err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("getCustomer", ex.ToString(), salesId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        public mCustomerJoin AddNewCustomer(mCustomer data, String salesId, int GroupCust)
        {


            mCustomerJoin items = new mCustomerJoin();
            if (null != data && null != salesId)
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {
                    try
                    {
                        /*
                        SqlCommand sqlCmd = new SqlCommand();
                        sqlCmd.CommandType = CommandType.Text;
                        sqlCmd.CommandText = "INSERT INTO Cust " +
                                    "(CustGroupId,CustName,AliasName,Address,Lat,Lng,Radius,Pic,PicJabatan,Telp,Email,Hp,Website,StsPkpId,Npwp," +
                                    "CreditLimit,[Top],AreaId,SalesmanId,CustById,JoinDate,EcTolerance,FreqTypeId,ChannelId,CustStatusId,CustZoneId,CustLevelId,CustIdAndro)" +
                                    "Values (@CustGroupId,@CustName,@AliasName,@Address,@Lat,@Lng,@Radius,@Pic,@PicJabatan,@Telp,@Email,@Hp,@Website,@StsPkpId,@Npwp," +
                                    "@CreditLimit,@Top,@AreaId,@SalesmanId,@CustById,@JoinDate,@EcTolerance,@FreqTypeId,@ChannelId,@CustStatusId,@CustZoneId,@CustLevelId,@CustIdAndro)"+
                                    " SELECT SCOPE_IDENTITY()";
                        sqlCmd.Connection = con;

                        sqlCmd.Parameters.AddWithValue("@CustGroupId", data.CustGroupId);
                        sqlCmd.Parameters.AddWithValue("@CustName", data.CustName);
                        sqlCmd.Parameters.AddWithValue("@AliasName", data.AliasName);
                        sqlCmd.Parameters.AddWithValue("@Address", data.Address);
                        sqlCmd.Parameters.AddWithValue("@Lat", data.Lat);
                        sqlCmd.Parameters.AddWithValue("@Lng", data.Lng);
                        sqlCmd.Parameters.AddWithValue("@Radius", data.Radius);
                        sqlCmd.Parameters.AddWithValue("@Pic", data.Pic);
                        sqlCmd.Parameters.AddWithValue("@PicJabatan", data.PicJabatan);
                        sqlCmd.Parameters.AddWithValue("@Telp", data.Telp);
                        sqlCmd.Parameters.AddWithValue("@Email", data.Email);
                        sqlCmd.Parameters.AddWithValue("@Hp", data.Hp);
                        sqlCmd.Parameters.AddWithValue("@Website", data.Website);
                        sqlCmd.Parameters.AddWithValue("@StsPkpId", data.StsPkpId);
                        sqlCmd.Parameters.AddWithValue("@Npwp", data.Npwp);                        
                        sqlCmd.Parameters.AddWithValue("@CreditLimit", data.CreditLimit);
                        sqlCmd.Parameters.AddWithValue("@Top", data.Top);
                        sqlCmd.Parameters.AddWithValue("@AreaId", data.AreaId);
                        sqlCmd.Parameters.AddWithValue("@SalesmanId", data.SalesmanId);
                        sqlCmd.Parameters.AddWithValue("@CustById", data.CustById);
                        sqlCmd.Parameters.AddWithValue("@JoinDate", data.JoinDate);
                        sqlCmd.Parameters.AddWithValue("@EcTolerance", data.EcTolerance);
                        sqlCmd.Parameters.AddWithValue("@FreqTypeId", data.FreqTypeId);
                        sqlCmd.Parameters.AddWithValue("@ChannelId", data.ChannelId);
                        sqlCmd.Parameters.AddWithValue("@CustStatusId", data.CustStatusId);
                        sqlCmd.Parameters.AddWithValue("@CustZoneId", data.CustZoneId);
                        sqlCmd.Parameters.AddWithValue("@CustLevelId", data.CustLevelId);
                        sqlCmd.Parameters.AddWithValue("@CustIdAndro", data.CustIdAndro);
                        con.Open();
                        int rowInserted = sqlCmd.ExecuteNonQuery();
                        //int modified = (int) sqlCmd.ExecuteScalar();
                        */

                        SqlCommand cmd = new SqlCommand("AndroCustomerAdd", con);
                        cmd.CommandType = CommandType.StoredProcedure;

                        SqlParameter par1 = new SqlParameter();
                        par1.ParameterName = "@salesId";
                        par1.Value = salesId;
                        cmd.Parameters.Add(par1);

                        SqlParameter CustGroupId = new SqlParameter();
                        CustGroupId.ParameterName = "@CustGroupId";
                        CustGroupId.SqlDbType = SqlDbType.Int;
                        CustGroupId.Value = data.CustGroupId;
                        cmd.Parameters.Add(CustGroupId);
                        SqlParameter CustName = new SqlParameter();
                        CustName.ParameterName = "@CustName";
                        CustName.Value = data.CustName;
                        cmd.Parameters.Add(CustName);
                        SqlParameter AliasName = new SqlParameter();
                        AliasName.ParameterName = "@AliasName";
                        AliasName.Value = data.AliasName;
                        cmd.Parameters.Add(AliasName);
                        SqlParameter Address = new SqlParameter();
                        Address.ParameterName = "@Address";
                        Address.Value = data.Address;
                        cmd.Parameters.Add(Address);
                        SqlParameter Lat = new SqlParameter();
                        Lat.ParameterName = "@Lat";
                        Lat.SqlDbType = SqlDbType.Float;
                        Lat.Value = data.Lat;
                        cmd.Parameters.Add(Lat);
                        SqlParameter Lng = new SqlParameter();
                        Lng.ParameterName = "@Lng";
                        Lng.SqlDbType = SqlDbType.Float;
                        Lng.Value = data.Lng;
                        cmd.Parameters.Add(Lng);
                        SqlParameter Radius = new SqlParameter();
                        Radius.ParameterName = "@Radius";
                        Radius.Value = data.Radius;
                        cmd.Parameters.Add(Radius);
                        SqlParameter Pic = new SqlParameter();
                        Pic.ParameterName = "@Pic";
                        Pic.Value = data.Pic;
                        cmd.Parameters.Add(Pic);
                        SqlParameter PicJabatan = new SqlParameter();
                        PicJabatan.ParameterName = "@PicJabatan";
                        PicJabatan.Value = data.PicJabatan;
                        cmd.Parameters.Add(PicJabatan);
                        SqlParameter Telp = new SqlParameter();
                        Telp.ParameterName = "@Telp";
                        Telp.Value = data.Telp;
                        cmd.Parameters.Add(Telp);
                        SqlParameter Email = new SqlParameter();
                        Email.ParameterName = "@Email";
                        Email.Value = data.Email;
                        cmd.Parameters.Add(Email);
                        SqlParameter Hp = new SqlParameter();
                        Hp.ParameterName = "@Hp";
                        Hp.Value = data.Hp;
                        cmd.Parameters.Add(Hp);
                        SqlParameter Website = new SqlParameter();
                        Website.ParameterName = "@Website";
                        Website.Value = data.Website;
                        cmd.Parameters.Add(Website);
                        string pkp = "NONPKP";
                        if (!data.StsPkpId.Equals(""))
                            pkp = data.StsPkpId;
                        SqlParameter StsPkpId = new SqlParameter();
                        StsPkpId.ParameterName = "@StsPkpId";
                        StsPkpId.Value = pkp;
                        cmd.Parameters.Add(StsPkpId);
                        SqlParameter Npwp = new SqlParameter();
                        Npwp.ParameterName = "@Npwp";
                        Npwp.Value = data.Npwp;
                        cmd.Parameters.Add(Npwp);
                        SqlParameter CreditLimit = new SqlParameter();
                        CreditLimit.ParameterName = "@CreditLimit";
                        CreditLimit.Value = data.CreditLimit;
                        cmd.Parameters.Add(CreditLimit);
                        SqlParameter Top = new SqlParameter();
                        Top.ParameterName = "@Top";
                        Top.Value = data.Top;
                        cmd.Parameters.Add(Top);
                        SqlParameter AreaId = new SqlParameter();
                        AreaId.ParameterName = "@AreaId";
                        AreaId.Value = data.AreaId;
                        cmd.Parameters.Add(AreaId);
                        SqlParameter SalesmanId = new SqlParameter();
                        SalesmanId.ParameterName = "@SalesmanId";
                        SalesmanId.Value = data.SalesmanId;
                        cmd.Parameters.Add(SalesmanId);
                        SqlParameter CustById = new SqlParameter();
                        CustById.ParameterName = "@CustById";
                        CustById.Value = data.CustById;
                        cmd.Parameters.Add(CustById);
                        SqlParameter JoinDate = new SqlParameter();
                        JoinDate.ParameterName = "@JoinDate";
                        JoinDate.Value = data.JoinDate;
                        cmd.Parameters.Add(JoinDate);
                        SqlParameter EcTolerance = new SqlParameter();
                        EcTolerance.ParameterName = "@EcTolerance";
                        EcTolerance.Value = data.EcTolerance;
                        cmd.Parameters.Add(EcTolerance);
                        SqlParameter FreqTypeId = new SqlParameter();
                        FreqTypeId.ParameterName = "@FreqTypeId";
                        FreqTypeId.Value = data.FreqTypeId;
                        cmd.Parameters.Add(FreqTypeId);
                        SqlParameter ChannelId = new SqlParameter();
                        ChannelId.ParameterName = "@ChannelId";
                        ChannelId.Value = data.ChannelId;
                        cmd.Parameters.Add(ChannelId);
                        SqlParameter CustStatusId = new SqlParameter();
                        CustStatusId.ParameterName = "@CustStatusId";
                        CustStatusId.Value = data.CustStatusId;
                        cmd.Parameters.Add(CustStatusId);
                        SqlParameter CustZoneId = new SqlParameter();
                        CustZoneId.ParameterName = "@CustZoneId";
                        CustZoneId.Value = data.CustZoneId;
                        cmd.Parameters.Add(CustZoneId);
                        SqlParameter CustLevelId = new SqlParameter();
                        CustLevelId.ParameterName = "@CustLevelId";
                        CustLevelId.Value = data.CustLevelId;
                        cmd.Parameters.Add(CustLevelId);
                        SqlParameter CustIdAndro = new SqlParameter();
                        CustIdAndro.ParameterName = "@CustIdAndro";
                        CustIdAndro.Value = data.CustIdAndro;
                        cmd.Parameters.Add(CustIdAndro);

                        cmd.Parameters.AddWithValue("@GroupCust", GroupCust);

                        con.Open();
                        cmd.ExecuteNonQuery();
                        con.Close();


                        int idcust = GetCustIdByIdAndroAndSales(data.CustIdAndro, salesId);



                        //   object firstColumn = cmd.ExecuteScalar();

                        // int idcust = 0;
                        if (idcust >= 0)
                        {


                            for (int i = 0; i < data.customerAndBranch.Count; i++)
                            {
                                data.customerAndBranch[i].CustId = idcust;
                                AddCustAndBranch(data.customerAndBranch[i]);
                            }


                        }

                        items = getCustomerJoin(salesId, idcust.ToString());


                    }
                    catch (Exception ex)
                    {
                        String err = ex.Message;
                        spLog log = new spLog();
                        string json = JsonConvert.SerializeObject(data);
                        log.inserLog("AddNewCustomer", ex.ToString() + "\n " + json, data.SalesmanId.ToString());
                    }
                    finally
                    {
                        con.Close();
                    }


                }
            }

            return items;
        }

        private int AddCustAndBranch(mCustomerAndBranch data)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroCustomerAddAndDistBranch", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@DistBranchId", data.DistBranchId);
                    sqlCmd.Parameters.AddWithValue("@CustId", data.CustId);
                    sqlCmd.Parameters.AddWithValue("@CustCode", data.CustCode);
                    sqlCmd.Parameters.AddWithValue("@PriceGroupId", data.PriceGroupId);
                    sqlCmd.Parameters.AddWithValue("@DiscGroupId", data.DiscGroupId);
                    sqlCmd.Parameters.AddWithValue("@CustIdAndro", data.CustIdAndro);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();

                    return rowInserted;
                }
            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                spLog log = new spLog();
                string json = JsonConvert.SerializeObject(data);
                log.inserLog("AddCustAndBranch", ex.ToString() + "\n " + json, data.CustId.ToString());
                return -1;
            }

        }

        private int GetCustIdByIdAndroAndSales(String CustIdAndro, String salesId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroCustIdByAndroIdAndSalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@CustIdAndro", CustIdAndro);
                    cmd.Parameters.AddWithValue("@salesId", salesId);
                    var returnParameter = cmd.Parameters.Add("@ReturnVal", SqlDbType.Int);
                    returnParameter.Direction = ParameterDirection.ReturnValue;
                    con.Open();
                    cmd.ExecuteNonQuery();
                    int rowSelected = Int32.Parse(returnParameter.Value.ToString());
                    return rowSelected;
                }
            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                spLog log = new spLog();
                log.inserLog("GetCustIdByIdAndroAndSales", ex.ToString(), salesId.ToString());
                return -1;
            }

        }

        internal bool UpdateCustomerDownloadBySalesId(string salesId, List<custBindingModel> customerList)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {
                    DataTable table = new DataTable();
                    table.Columns.Add("customerId", typeof(Int32));
                    SqlCommand cmd = new SqlCommand("AndroCustomerUpdateStatusBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    foreach (custBindingModel cb in customerList)
                    {
                        table.Rows.Add(cb.customerId);
                    }

                    var pList = new SqlParameter("@listCustomer", SqlDbType.Structured);
                    pList.TypeName = "dbo.CustomerList";
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
                string json = JsonConvert.SerializeObject(customerList);
                log.inserLog("UpdateCustomerDownloadBySalesId", ex.ToString() + "\n " + json, salesId.ToString());
                hasil = false;

            }

            return hasil;
        }
        public List<mCustomer> SelectCustomerNewUpdateBySalesId(String salesId)
        {
            List<mCustomer> items = new List<mCustomer>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerNewUpdateBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomer item = new mCustomer();
                        List<mCustomerAndBranch> customerAndBranch = customerDistributor.SelectCustomerAndDistributorBranchByCustomerId(rdr["CustId"].ToString());
                        item.CustId = rdr["CustId"].ToString();
                        item.CustGroupId = rdr["CustGroupId"].ToString();
                        item.CustName = rdr["CustName"].ToString();
                        item.AliasName = rdr["AliasName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Lat = Convert.ToDouble(rdr["Lat"].ToString());
                        item.Lng = Convert.ToDouble(rdr["Lng"].ToString());
                        item.Radius = Convert.ToDouble(rdr["Radius"].ToString());
                        item.Pic = rdr["Pic"].ToString();
                        item.PicJabatan = rdr["PicJabatan"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Hp = rdr["Hp"].ToString();
                        item.Website = rdr["Website"].ToString();
                        item.StsPkpId = rdr["StsPkpId"].ToString();
                        item.StsPkpName = rdr["StsPkpName"].ToString();
                        item.Npwp = rdr["Npwp"].ToString();
                        item.CreditLimit = Convert.ToDouble(rdr["CreditLimit"].ToString());
                        item.Top = rdr["Top"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"].ToString());
                        item.CustById = rdr["CustById"].ToString();
                        item.CustByName = rdr["CustByName"].ToString();
                        item.JoinDate = rdr["JoinDate"].ToString();
                        item.EcTolerance = Convert.ToInt32(rdr["EcTolerance"].ToString());
                        item.FreqTypeId = rdr["FreqTypeId"].ToString();
                        item.FreqTypeName = rdr["FreqTypeName"].ToString();
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"]);
                        item.CustLevelId = Convert.ToInt32(rdr["CustLevelId"]);
                        item.CustStatusId = rdr["CustStatusId"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();
                        item.customerAndBranch = customerAndBranch;
                        items.Add(item);
                    }
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    log.inserLog("SelectCustomerNewUpdateBySalesId", ex.ToString(), salesId.ToString());
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }
    }
}