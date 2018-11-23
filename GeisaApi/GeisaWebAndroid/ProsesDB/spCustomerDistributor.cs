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
    public class spCustomerDistributor
    {
        public List<mCustomerAndBranch> SelectCustomerAndDistributorBranchByCustomerId(String CustomerId)
        {
            List<mCustomerAndBranch> items = new List<mCustomerAndBranch>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerAndBranchBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@custId";
                    par1.Value = CustomerId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomerAndBranch item = new mCustomerAndBranch();
                        //String a = rdr["DistBranchId"].ToString();
                        item.CustomerDistBranchId = Convert.ToInt32(rdr["CustAndDistBranchId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"]);
                        item.CustCode = rdr["CustCode"].ToString();
                        item.PriceGroupId = rdr["PriceGroupId"].ToString();
                        item.PriceGroupName = rdr["PriceGroupName"].ToString();
                        item.DiscGroupId = rdr["DiscGroupId"].ToString();
                        item.DiscGroupName = rdr["DiscGroupName"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();

                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"]);
                        item.DistBranchName = rdr["DistBranchName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaCode = rdr["AreaCode"].ToString();
                        item.AreaName = rdr["AreaName"].ToString();
                        item.DistId = Convert.ToInt32(rdr["DistId"]);
                        item.AreaId = Convert.ToInt32(rdr["AreaId"]);
                        item.StatusId= Convert.ToInt32(rdr["StatusId"]);
                        items.Add(item);
                    }
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    log.inserLog("SelectCustomerAndDistributorBranchByCustomerId", ex.ToString(), CustomerId);
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        public mCustomerAndBranch SelectCustomerAndDistributorBranchById(String id)
        {
            mCustomerAndBranch items = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerAndBranchById", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@DistBranchId";
                    par1.Value = id;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomerAndBranch item = new mCustomerAndBranch();
                        //String a = rdr["DistBranchId"].ToString();
                        item.CustomerDistBranchId = Convert.ToInt32(rdr["CustAndDistBranchId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"]);
                        item.CustCode = rdr["CustCode"].ToString();
                        item.PriceGroupId = rdr["PriceGroupId"].ToString();
                        item.PriceGroupName = rdr["PriceGroupName"].ToString();
                        item.DiscGroupId = rdr["DiscGroupId"].ToString();
                        item.DiscGroupName = rdr["DiscGroupName"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();

                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"]);
                        item.DistBranchName = rdr["DistBranchName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.AreaCode = rdr["AreaCode"].ToString();
                        item.AreaName = rdr["AreaName"].ToString();
                        item.DistId = Convert.ToInt32(rdr["DistId"]);
                        item.AreaId = Convert.ToInt32(rdr["AreaId"]);
                        item.StatusId = Convert.ToInt32(rdr["StatusId"]);
                        item.TemplateEmailRegularAttach = rdr["TemplateEmailRegularAttach"].ToString();
                        item.TemplateEmailKhususAttach = rdr["TemplateEmailKhususAttach"].ToString();
                        items =item;
                    }
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    log.inserLog("SelectCustomerAndDistributorBranchByCustomerId", ex.ToString(), id);
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        public mCustomerAndBranch SelectCustomerAndDistributorBranchById(String custId,String branchId)
        {
            mCustomerAndBranch items = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroCustomerAndBranchByCustBranchId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@CustId";
                    par1.Value = custId;
                    cmd.Parameters.Add(par1);
                    SqlParameter par2 = new SqlParameter();
                    par2.ParameterName = "@DistBranchId";
                    par2.Value = branchId;
                    cmd.Parameters.Add(par2);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustomerAndBranch item = new mCustomerAndBranch();
                        //String a = rdr["DistBranchId"].ToString();
                        item.CustomerDistBranchId = Convert.ToInt32(rdr["CustAndDistBranchId"].ToString());
                        item.CustId = Convert.ToInt32(rdr["CustId"]);
                        item.CustCode = rdr["CustCode"].ToString();
                        item.PriceGroupId = rdr["PriceGroupId"].ToString();
                        item.PriceGroupName = rdr["PriceGroupName"].ToString();
                        item.DiscGroupId = rdr["DiscGroupId"].ToString();
                        item.DiscGroupName = rdr["DiscGroupName"].ToString();
                        item.CustIdAndro = rdr["CustIdAndro"].ToString();

                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"]);
                        item.DistBranchName = rdr["DistBranchName"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.EmailInternal = rdr["EmailInternal"].ToString();
                        item.AreaCode = rdr["AreaCode"].ToString();
                        item.AreaName = rdr["AreaName"].ToString();
                        item.DistId = Convert.ToInt32(rdr["DistId"]);
                        item.AreaId = Convert.ToInt32(rdr["AreaId"]);
                        item.StatusId = Convert.ToInt32(rdr["StatusId"]);
                        item.TemplateEmailRegularAttach = rdr["TemplateEmailRegularAttach"].ToString();
                        item.TemplateEmailKhususAttach = rdr["TemplateEmailKhususAttach"].ToString();
                        items = item;
                    }
                }catch(Exception ex)
                {
                    string e = ex.ToString();
                    spLog log = new spLog();
                    log.inserLog("SelectCustomerAndDistributorBranchById", ex.ToString(), custId);
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