using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mBiAll
    {
        public String CallPlanNoteId { get; set; }
        public String Notes1 { get; set; }
        public String Notes2 { get; set; }
        public String Notes3 { get; set; }
        public String ProductName { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }

        //bitype
        public int BiCsTypeId { get; set; }
        public String BiCsTypeName { get; set; }
        public String BiCsTypeEmail { get; set; }
        public String BiCsTypeJenis { get; set; }

        //callplan
        public String CallPlanId { get; set; }
        public String CallPlanDate { get; set; }
        public String CallPlanTypeId { get; set; }
        public String CallPlanTypeName { get; set; }

        //customer
        public int CustId { get; set; }
        public String CustName { set; get; }
        public String AliasName { set; get; }
        public String Address { set; get; }

        //sales
        public int SalesmanId { get; set; }
        public mSales salesman { get; set; }



    }
}