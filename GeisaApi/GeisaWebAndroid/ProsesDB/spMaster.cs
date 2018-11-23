using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using GeisaWebAndroid.Models;
using System.Configuration;
using System.Data.SqlClient;
using System.Data;

namespace GeisaWebAndroid.ProsesDB
{
    public class spMaster
    {
        internal List<mArea> SelectAreaBySalesId(string salesId)
        {
            List<mArea> items = new List<mArea>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroAreaBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mArea item = new mArea();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.AreaName = rdr["AreaName"].ToString();
                        item.AreaCode = rdr["AreaCode"].ToString();
                        items.Add(item);
                    }
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    log.inserLog("SelectAreaBySalesId", ex.ToString(), salesId);
                }
                finally
                {
                    con.Close();
                }


            }

            return items;
        }

        internal List<mLevel> SelectLevelBySalesId(string salesId)
        {
            List<mLevel> items = new List<mLevel>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroLevelBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mLevel item = new mLevel();
                        item.CustLevelId = Convert.ToInt32(rdr["CustLevelId"].ToString());
                        item.CustLevelName = rdr["CustLevelName"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectLevelBySalesId", ex.ToString(), salesId);
            }
            
            return items;
        }

        internal List<mUnit> SelectUnitBySalesId(string salesId)
        {
            List<mUnit> items = new List<mUnit>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroUnitBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mUnit item = new mUnit();
                        item.UnitId = rdr["UnitId"].ToString();
                        item.UnitName = rdr["UnitName"].ToString();
                        item.Status = Convert.ToInt32(rdr["Status"].ToString());
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectUnitBySalesId", ex.ToString(), salesId);
            }

            return items;
        }

        internal List<mBiCsType> SelectBiCsTypeBySalesId(string salesId)
        {
            List<mBiCsType> items = new List<mBiCsType>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroBiTypeBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mBiCsType item = new mBiCsType();
                        item.BiCsTypeId = Convert.ToInt32(rdr["NoteTypeId"].ToString());
                        item.BiCsTypeName = rdr["NoteTypeName"].ToString();
                        item.BiCsTypeEmail = rdr["Email"].ToString();
                        item.BiCsTypeJenis = rdr["Category"].ToString();
                        item.BiCsTypeStatus = Convert.ToInt32(rdr["StatusId"].ToString());
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectBiCsTypeBySalesId", ex.ToString(), salesId);
            }
            
            return items;
        }

        internal List<mStockBranch> SelectProductStockBranch(string bulan, string tahun, string branchId)
        {
            List<mStockBranch> items = new List<mStockBranch>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroProductStockByBranch", con);
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.AddWithValue("@Bulan", bulan);
                    cmd.Parameters.AddWithValue("@Tahun", tahun);
                    cmd.Parameters.AddWithValue("@BranchId", branchId);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mStockBranch item = new mStockBranch();
                        item.ProductId = rdr["ProductId"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.ProductNameDist = rdr["ProductNameDist"].ToString();
                        item.Packing = rdr["Packing"].ToString();
                        item.BranchId = rdr["BranchId"].ToString();
                        item.BranchName = rdr["BranchName"].ToString();
                        item.AreaCode = rdr["AreaCode"].ToString();
                        item.Qty =Convert.ToDouble( rdr["Qty"].ToString());
                        item.PrintDate = Convert.ToDateTime(rdr["PrintDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectProductStockBranch", ex.ToString(), branchId);
            }

            return items;
        }

        internal List<mStock> SelectProductStockProduct(string bulan, string tahun, string branchId,string productId)
        {
            List<mStock> items = new List<mStock>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroProductStockByProduct", con);
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.AddWithValue("@Bulan", bulan);
                    cmd.Parameters.AddWithValue("@Tahun", tahun);
                    cmd.Parameters.AddWithValue("@BranchId", branchId);
                    cmd.Parameters.AddWithValue("@ProductId", productId);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mStock item = new mStock();
                        item.WarehouseId = rdr["WarehouseId"].ToString();
                        item.WarehouseName= rdr["WarehouseName"].ToString();
                        item.ProductId = rdr["ProductId"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.ProductName = rdr["ProductName"].ToString();
                        item.ProductNameDist = rdr["ProductNameDist"].ToString();
                        item.Packing = rdr["Packing"].ToString();
                        item.BranchId = rdr["BranchId"].ToString();
                        item.BranchName = rdr["BranchName"].ToString();
                        item.AreaCode = rdr["AreaCode"].ToString();
                        item.UnitConverter = Convert.ToDouble(rdr["UnitConverter"].ToString());
                        item.NetWeight = Convert.ToDouble(rdr["NetWeight"].ToString());
                        item.Qty = Convert.ToDouble(rdr["Qty"].ToString());
                        item.PrintDate = Convert.ToDateTime(rdr["PrintDate"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectProductStockProduct", ex.ToString(), branchId);
            }

            return items;
        }

        internal List<mChannel> SelectChannelBySalesId(string salesId)
        {
            List<mChannel> items = new List<mChannel>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroChannelBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mChannel item = new mChannel();
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"]);
                        item.ChannelName = rdr["ChannelName"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Signature = rdr["Signature"].ToString();
                        item.StatusId = Convert.ToInt32(rdr["StatusId"]);
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectChannelBySalesId", ex.ToString(), salesId);
            }
            
            return items;
        }

        internal mChannel SelectChannelById(string id)
        {
            mChannel items = null;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroChannelById", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@ChannelId";
                    par1.Value = id;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mChannel item = new mChannel();
                        item.ChannelId = Convert.ToInt32(rdr["ChannelId"]);
                        item.ChannelName = rdr["ChannelName"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Signature = rdr["Signature"].ToString();
                        item.StatusId = Convert.ToInt32(rdr["StatusId"]);
                        items = item;
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectChannelById", ex.ToString(), id);
            }
            
            return items;
        }

        internal List<mZone> SelectZoneBySalesId(string salesId)
        {
            List<mZone> items = new List<mZone>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroZoneBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mZone item = new mZone();
                        item.CustZoneId = Convert.ToInt32(rdr["CustZoneId"]);
                        item.CustZoneName = rdr["CustZoneName"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectChannelById", ex.ToString(), salesId);
            }
            
            return items;
        }

        internal List<mCustStatus> SelectCustStatusBySalesId(string salesId)
        {
            List<mCustStatus> items = new List<mCustStatus>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroCustStatusBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mCustStatus item = new mCustStatus();
                        item.CustStatusId = Convert.ToInt32(rdr["CustStatusId"]);
                        item.CustStatusName = rdr["CustStatusName"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectCustStatusBySalesId", ex.ToString(), salesId);
            }
            
            return items;
        }

        internal List<mDistributor> SelectDistributorBySalesId(string salesId)
        {
            List<mDistributor> items = new List<mDistributor>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroDistributorBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mDistributor item = new mDistributor();
                        item.DistId = Convert.ToInt32(rdr["DistId"]);
                        item.DistName = rdr["DistName"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Address = rdr["Address"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectDistributorBySalesId", ex.ToString(), salesId);
            }
            

            return items;
        }

        internal List<mDistBranch> SelectDistBranchBySalesId(string salesId)
        {
            List<mDistBranch> items = new List<mDistBranch>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroDistBranchBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesId";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mDistBranch item = new mDistBranch();
                        item.DistBranchId = Convert.ToInt32(rdr["DistBranchId"]);
                        item.DistBranchName = rdr["DistBranchName"].ToString();
                        item.Email = rdr["Email"].ToString();
                        item.Pic = rdr["Pic"].ToString();
                        item.Telp = rdr["Telp"].ToString();
                        item.Address = rdr["Address"].ToString();
                        item.DistId = Convert.ToInt32(rdr["DistId"].ToString());
                        item.AreaName = rdr["AreaName"].ToString();
                        item.AreaId = Convert.ToInt32(rdr["AreaId"].ToString());
                        item.AreaCode = rdr["AreaCode"].ToString();
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectDistBranchBySalesId", ex.ToString(), salesId);
            }
            

            return items;
        }


        internal List<mProduct> SelectProductBySalesId(string salesId)
        {
            List<mProduct> items = new List<mProduct>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroProductBySales", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesid";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mProduct item = new mProduct();
                        item.ProductId = Convert.ToInt32(rdr["ProductId"]);
                        item.ProductName = rdr["ProductName"].ToString();
                        item.Foto = rdr["Foto"].ToString();
                        item.ProductSimpleDescription = rdr["ProductSimpleDescription"].ToString();
                        item.RecIdProductMap = Convert.ToInt32(rdr["RecId"].ToString());
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.DistId = Convert.ToInt32(rdr["DistId"].ToString());
                        item.ProductNameDist = rdr["ProductNameDist"].ToString();
                        item.StatusId = Convert.ToInt32(rdr["StatusId"].ToString());
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectProductBySalesId", ex.ToString(), salesId);
            }
            
            return items;
        }

        internal List<mProductPriceDiskon> SelectProductPriceDiskonBySalesId(string salesId)
        {
            List<mProductPriceDiskon> items = new List<mProductPriceDiskon>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand cmd = new SqlCommand("AndroProductPriceDiskonBySales", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesid";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mProductPriceDiskon item = new mProductPriceDiskon();
                        item.RecId = Convert.ToInt32(rdr["RecId"]);
                        item.Relation = Convert.ToInt32(rdr["Relation"].ToString());
                        item.DistId = Convert.ToInt32(rdr["DistId"].ToString());
                        item.PriceType = Convert.ToInt32(rdr["PriceType"].ToString());
                        item.PriceDiscGroupId = rdr["PriceDiscGroupId"].ToString();
                        item.ProductCode = rdr["ProductCode"].ToString();
                        item.ProductNameDist = rdr["ProductNameDist"].ToString();
                        item.UnitId = rdr["UnitId"].ToString();
                        item.Price = Convert.ToDouble(rdr["Price"].ToString());
                        item.Disc1 = Convert.ToDouble(rdr["Disc1"].ToString());
                        item.Disc2 = Convert.ToDouble(rdr["Disc2"].ToString());
                        item.StartDate = rdr["StartDate"].ToString();
                        item.EndDate = rdr["EndDate"].ToString();
                        items.Add(item);
                    }

                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SelectProductPriceDiskonBySalesId", ex.ToString(), salesId);
            }
            

            return items;
        }

        internal List<mPromo> SelectPromoBySalesId(String salesId) // perubahan storeprocedure string salesId
        {
            List<mPromo> items = new List<mPromo>();
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {

                    SqlCommand cmd = new SqlCommand("PromoSelect", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    SqlParameter par1 = new SqlParameter();
                    par1.ParameterName = "@salesid";
                    par1.Value = salesId;
                    cmd.Parameters.Add(par1);
                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPromo item = new mPromo();
                        item.PromoId = Convert.ToInt32(rdr["PromoId"]);
                        item.PromoName = rdr["PromoName"].ToString();
                        item.StartDate = Convert.ToDateTime(rdr["StartDate"].ToString()).ToString("yyyy-MM-dd");
                        item.EndDate = Convert.ToDateTime(rdr["EndDate"].ToString()).ToString("yyyy-MM-dd");
                        item.DistId = Convert.ToInt32(rdr["DistId"].ToString());
                        //item.DistName = rdr["DistName"].ToString();
                        item.CustRelation = Convert.ToInt32(rdr["CustRelation"].ToString());
                        item.Cust = rdr["Cust"].ToString();
                        //item.CustName = rdr["CustName"].ToString();
                        item.ProductId = Convert.ToInt32(rdr["ProductId"].ToString());
                        //item.ProductName = rdr["ProductName"].ToString();
                        item.UnitId = rdr["UnitId"].ToString();
                        item.MinQty = Convert.ToInt32(rdr["MinQty"].ToString());
                        item.MinValue = Convert.ToInt32(rdr["MinValue"].ToString());
                        item.MultiplyQty = Convert.ToInt32(rdr["MultiPlyQty"].ToString());
                        item.ProductIdBonus = Convert.ToInt32(rdr["ProductIdBonus"].ToString());
                        //item.ProductBonusName = rdr["ProductBonusName"].ToString();
                        item.UnitIdBonus = rdr["UnitIdBonus"].ToString();
                        item.QtyBonus = Convert.ToInt32(rdr["QtyBonus"].ToString());
                        item.Notes = rdr["Notes"].ToString();
                        item.CreatedDate = rdr["CreatedDate"].ToString();
                        item.CreatedBy = rdr["CreatedBy"].ToString();
                        item.ModifiedDate = rdr["ModifiedDate"].ToString();
                        item.ModifiedBy = rdr["ModifiedBy"].ToString();
                        items.Add(item);
                    }

                }

                catch (Exception ex)
                {
                    spLog log = new spLog();
                    log.inserLog("SelectPromoBySalesId", ex.ToString(), salesId);
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