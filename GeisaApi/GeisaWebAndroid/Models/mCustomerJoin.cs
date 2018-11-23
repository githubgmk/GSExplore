using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mCustomerJoin
    {

        public String CustByName { set; get; }
        public String StsPkpName { set; get; }
        public String FreqTypeName { set; get; }
        public String CustId { set; get; }
        public String CustGroupId { set; get; }
        public String CustName { set; get; }
        public String AliasName { set; get; }
        public String Address { set; get; }
        public double Lat { set; get; }
        public double Lng { set; get; }
        public double Radius { set; get; }
        public String Pic { set; get; }
        public String PicJabatan { set; get; }
        public String Telp { set; get; }
        public String Email { set; get; }
        public String Hp { set; get; }
        public String Website { set; get; }
        public String StsPkpId { set; get; }
        public String Npwp { set; get; }
        public double CreditLimit { set; get; }
        public String Top { set; get; }
        public int AreaId { set; get; }
        public int SalesmanId { set; get; }
        public String CustById { set; get; }
        public String JoinDate { set; get; }
        public int EcTolerance { set; get; }
        public String FreqTypeId { set; get; }
        public int ChannelId { set; get; }
        public int CustLevelId { set; get; }
        public int CustZoneId { set; get; }
        public String DistBranchId { set; get; }
        public String CustStatusId { set; get; }
        public String CustIdAndro { set; get; }
        public List<mCustomerAndBranch> customerAndBranch { set; get; }
        public mChannel channel { get; set; }
        public mSales salesman { get; set; }

        public String CreatedDate { get; set; }
    }
}