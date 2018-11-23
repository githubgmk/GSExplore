using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mRofoTarget
    {
        public int SalesTargetId { get; set; }
        public int Year { get; set; }
        public int Month { get; set; }
        public int SalesmanId { get; set; }
        public double Value { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }
    }
}