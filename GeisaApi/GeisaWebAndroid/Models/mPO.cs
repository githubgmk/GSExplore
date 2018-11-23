using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mPO
    {
        public String PoId { get; set; }
        public String PoCustNumberRef { get; set; }
        public String PoDate { get; set; }
        public int DistBranchId { get; set; }
        public int CustId { get; set; }
        public int SalesmanId { get; set; }
        public String CallPlanId { get; set; }
        public String PoById { get; set; }
        public String PoByName { get; set; }
        public String PoViaId { get; set; }
        public String PoViaName { get; set; }
        public String ShipDate { get; set; }
        public String EndPeriodeDate { get; set; }
        public String Mechanisme { get; set; }
        public String ShipAddress { get; set; }
        public double Disc1 { get; set; }
        public double Disc2 { get; set; }
        public double CashDisc { get; set; }
        public bool isPP { get; set; }
        public String PicDist { get; set; }
        public String PicCust { get; set; }
        public String Notes { get; set; }
        public String Signature { get; set; }
        public int PoStatusId { get; set; }
        public String PoStatusName { get; set; }
        public String SoNo { get; set; }
        public String SoDate { get; set; }
        public String DoNo { get; set; }
        public String DoDate { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public String ConfirmDate { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }

        public List<mPoLine> poLines { get; set; }
        public List<mPoLineOther> poLineOthers { get; set; }

        public mCustomer customer { get; set; }
        public mCustomerAndBranch distBranch { get; set; }
        public mSales sales { get; set; }

        public String  KeteranganDetail { get; set; }

        public bool isSellOut { get; set; }
    }
}