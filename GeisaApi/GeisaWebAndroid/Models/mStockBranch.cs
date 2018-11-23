using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mStockBranch
    {
        public String ProductId { get; set; }
        public String ProductCode { get; set; }
        public String ProductName { get; set; }
        public String ProductNameDist { get; set; }
        public String Packing { get; set; }
        public String BranchId { get; set; }
        public String BranchName { get; set; }
        public String AreaCode { get; set; }
        public double Qty { get; set; }
        public String PrintDate { get; set; }
        
    }
}