using GeisaWebAndroid.Models;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.ProsesDB
{
    public class spSales
    {
        public List<mSales> SelectSales()
        {
            List<mSales> items = new List<mSales>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("ProductSelect", con);
                    cmd.CommandType = CommandType.StoredProcedure;


                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mSales item = new mSales();
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"]);
                        item.SalesmanName = rdr["SalesmanName"].ToString();
                        item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"]);
                        item.Imei = rdr["Imei"].ToString();
                        item.SPV = rdr["SPV"].ToString();
                        item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                        item.statusId = Convert.ToInt32(rdr["statusId"].ToString());
                        item.UserName = rdr["UserName"].ToString();
                        item.UserPass = rdr["UserPass"].ToString();
                        item.FcmId = rdr["FcmId"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());

                        items.Add(item);
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectSales", err, "0");
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        public mSales SelectSalesByUnamePassword(String uname, String pass)
        {
            mSales item = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroSalesByUnamePass", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@username";
                    par1.Value = uname;
                    cmd.Parameters.Add(par1);
                    SqlParameter par2 = new SqlParameter();
                    par2.ParameterName = "@pass";
                    par2.Value = pass;
                    cmd.Parameters.Add(par2);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        item = new mSales();
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"]);
                        item.SalesmanName = rdr["SalesmanName"].ToString();
                        item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"]);
                        item.Imei = rdr["Imei"].ToString();
                        item.SPV = rdr["SPV"].ToString();
                        item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                        item.statusId = Convert.ToInt32(rdr["statusId"].ToString());
                        item.UserName = rdr["UserName"].ToString();
                        item.UserPass = rdr["UserPass"].ToString();
                        item.FcmId = rdr["FcmId"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        // return item;
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectSalesByUnamePassword", err, uname);
                }
                finally
                {
                    con.Close();
                }


            }

            return item;
        }

        internal mSales SelectSalesByUname(string userlogin)
        {
            mSales item = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroSalesByUname", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@userlogin";
                    par1.Value = userlogin;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        item = new mSales();
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"]);
                        item.SalesmanName = rdr["SalesmanName"].ToString();
                        item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"]);
                        item.Imei = rdr["Imei"].ToString();
                        item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        item.UserName = rdr["UserName"].ToString();
                        item.UserPass = rdr["UserPass"].ToString();
                        item.FcmId = rdr["FcmId"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SignatureName = rdr["SignatureName"].ToString();
                        item.AllowApproval = Convert.ToInt32(rdr["AllowApproval"].ToString()) > 0;
                       // if (item.SpvId != 0 && item.SalesmanId != item.SpvId)
                       //     item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectSalesByUname", err, userlogin);
                }
                finally
                {
                    con.Close();
                    if (item != null)
                    {
                        if (item.SpvId != 0 && item.SalesmanId != item.SpvId)
                            item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                    }
                }


            }

            return item;
        }

        public mSales SelectSalesById(String salesId)
        {
            mSales item = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    try
                    {
                        SqlCommand cmd = new SqlCommand("AndroSalesById", con);
                        cmd.CommandType = CommandType.StoredProcedure;

                        SqlParameter par1 = new SqlParameter();
                        par1.ParameterName = "@SalesmanId";
                        par1.Value = salesId;
                        cmd.Parameters.Add(par1);
                        con.Open();

                        SqlDataReader rdr = cmd.ExecuteReader();
                        while (rdr.Read())
                        {
                            item = new mSales();
                            item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"]);
                            item.SalesmanName = rdr["SalesmanName"].ToString();
                            item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"]);
                            item.Imei = rdr["Imei"].ToString();
                            item.SPV = rdr["SPV"].ToString();
                            item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                            item.statusId = Convert.ToInt32(rdr["statusId"].ToString());
                            item.UserName = rdr["UserName"].ToString();
                            item.UserPass = rdr["UserPass"].ToString();
                            item.FcmId = rdr["FcmId"].ToString();
                            item.Email = rdr["Email"].ToString();
                            item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                            item.SignatureName = rdr["SignatureName"].ToString();
                            item.AllowApproval = Convert.ToInt32(rdr["AllowApproval"].ToString()) > 0;
                            //if (item.SpvId != 0)
                            //    item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                            // return item;
                        }
                    }
                    catch (Exception ex)
                    {
                        String err = ex.ToString();
                        spLog log = new spLog();
                        log.inserLog("SelectSalesById", err, salesId);
                    }
                    finally
                    {
                        con.Close();
                        if (item != null)
                        {
                            if (item.SpvId != 0 && item.SalesmanId != item.SpvId)
                                item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                        }
                    }


                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                spLog log = new spLog();
                log.inserLog("SelectSalesById", err, salesId);

            }
            
            return item;
        }

        public mSales SelectSalesmanById(String salesId)
        {
            mSales item = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroSalesmanById", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@SalesmanId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        item = new mSales();
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"]);
                        item.SalesmanName = rdr["SalesmanName"].ToString();
                        item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"]);
                        item.Imei = rdr["Imei"].ToString();
                        item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        item.UserName = rdr["UserName"].ToString();
                        item.UserPass = rdr["UserPass"].ToString();
                        item.FcmId = rdr["FcmId"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SignatureName = rdr["SignatureName"].ToString();
                        item.AllowApproval = Convert.ToInt32(rdr["AllowApproval"].ToString()) > 0;
                        // if (item.SpvId != 0)
                        //     item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectSalesmanById", err, salesId);
                }
                finally
                {
                    con.Close();
                    if (item != null)
                    {
                        if (item.SpvId != 0 && item.SalesmanId!=item.SpvId)
                            item.salesAtasan = SelectSPVById(item.SpvId.ToString());
                    }
                   
                }


            }

            return item;
        }
        public mSales SelectSPVById(String salesId)
        {
            mSales item = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroSalesmanById", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@SalesmanId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        item = new mSales();
                        item.SalesmanId = Convert.ToInt32(rdr["SalesmanId"]);
                        item.SalesmanName = rdr["SalesmanName"].ToString();
                        item.SalesmanLevelId = Convert.ToInt32(rdr["SalesmanLevelId"]);
                        item.Imei = rdr["Imei"].ToString();
                        item.SpvId = Convert.ToInt32(rdr["SpvId"].ToString());
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        item.UserName = rdr["UserName"].ToString();
                        item.UserPass = rdr["UserPass"].ToString();
                        item.FcmId = rdr["FcmId"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.SignatureName = rdr["SignatureName"].ToString();
                        item.AllowApproval = Convert.ToInt32(rdr["AllowApproval"].ToString()) > 0;
                        // if (item.SpvId != 0)
                        //     item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectSalesmanById", err, salesId);
                }
                finally
                {
                    con.Close();
                    if (item != null)
                    {
                        if (item.SpvId != 0 && item.SalesmanId != item.SpvId)
                            item.salesAtasan = SelectSalesmanById(item.SpvId.ToString());
                    }

                }


            }

            return item;
        }
        public bool UpdateSalesLogin(updateSalesLoginBindingModel data)
        {
            bool hasil = false;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroUserAndroUpdateLoginBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@SalesmanId", data.SalesmanId);
                    cmd.Parameters.AddWithValue("@UserName", data.UserName);
                    cmd.Parameters.AddWithValue("@UserPass", data.UserPass);
                    cmd.Parameters.AddWithValue("@Imei", data.Imei);
                    cmd.Parameters.AddWithValue("@Fcmid", data.Fcmid);
                    cmd.Parameters.AddWithValue("@OldPass", data.OldPass);
                    con.Open();
                    var hsl = cmd.ExecuteNonQuery();
                    hasil = true;
                }
                catch (Exception ex)
                {
                    String err = ex.Message;
                    spLog log = new spLog();
                    log.inserLog("UpdateSalesLogin", err, data.SalesmanId);
                    hasil = false;
                }
            }

            return hasil;
        }

        public mSales UpdateSalesDetail(string recId, string salesid, string fcmid, string imei, string useremail,string username,string userpass)
        {
            updateSalesLoginBindingModel sl = new updateSalesLoginBindingModel();
            sl.SalesmanId = salesid;
            sl.UserName = username;
            sl.UserPass = userpass;
            sl.Imei = imei;
            sl.Fcmid = fcmid;
            sl.OldPass = userpass;

            bool hasil = UpdateSalesLogin(sl);
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            if (hasil)
            {
                //update sales table
                try
                {
                    using (SqlConnection con = new SqlConnection(conStr))
                    {

                        SqlCommand cmd = new SqlCommand("AndroSalesUpdateBySalesId", con);
                        cmd.CommandType = CommandType.StoredProcedure;

                        cmd.Parameters.AddWithValue("@SalesmanId", salesid);
                        cmd.Parameters.AddWithValue("@Email", useremail);
                        con.Open();
                        var hsl = cmd.ExecuteNonQuery();
                        hasil = true;
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.Message;
                    spLog log = new spLog();
                    log.inserLog("UpdateSalesDetail", err,salesid);
                    hasil = false;
                }
            }
            mSales item = null;
            if (hasil)
            {
                item = SelectSalesByUnamePassword(username, userpass);
            }
            
            return item;
        }

    }
}