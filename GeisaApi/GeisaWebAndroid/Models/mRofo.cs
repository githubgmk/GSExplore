using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mRofo
    {
        public String RofoId { get; set; }
        public int Year { get; set; }
        public int Month { get; set; }
        public int SalesmanId { get; set; }
        public int CustId { get; set; }
        public int DistBranchId { get; set; }
        public int ProductId { get; set; }
        public int PriceId { get; set; }
        public String ProductName { get; set; }
        public String ProductCode { get; set; }
        public int Qty { get; set; }
        public String UnitId { get; set; }
        public double Value { get; set; }
        public int StatusId { get; set; }
        public String StatusName { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }
        public int StatusSend { get; set; }
    }
}