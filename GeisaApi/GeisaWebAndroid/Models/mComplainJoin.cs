using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mComplainJoin
    {
        public String ComplainId { set; get; }
        public bool SafetyFood { set; get; }
        public bool QualityFood { set; get; }
        public bool QualityApplication { set; get; }
        public bool QuantityAll { set; get; }
        public bool PackagingAll { set; get; }
        public String CallPlanId { set; get; }
        public String ProductId { set; get; }
        public String ProductIdName { set; get; }
        public String ProductName { set; get; }
        public int ComplainStatusId { set; get; }//status draft 0 ,inreview 1, open 2,close 3
        public String ComplainStatusName { set; get; }
        public String SampleSendDate { set; get; }
        public String CustPic { set; get; }
        public String CustPicJabatan { set; get; }
        public String CustPicHp { set; get; }
        public String ComplainNote { set; get; }
        public String ComplainPriority { set; get; }
        public String ComplainResponse { set; get; }
        public String ComplainResponseDate { set; get; }
        public String ComplainResponseBy { set; get; }
        public String CreatedDate { set; get; }
        public String CreatedBy { set; get; }
        public String ModifiedDate { set; get; }
        public String ModifiedBy { set; get; }

        //customer
        public int CustId { get; set; }
        public String CustName { set; get; }
        public String CustCode { set; get; }
        public String AliasName { set; get; }
        public String Address { set; get; }

        //sales
        public int SalesmanId { get; set; }
        public mSales salesman { get; set; }
    }
}