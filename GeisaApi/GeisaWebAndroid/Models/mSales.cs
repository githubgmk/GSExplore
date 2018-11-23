using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSales
    {
        public String UserName { get; set; }
        public String UserPass { get; set; }
        public int SalesmanId { get; set; }
        public String FcmId { get; set; }
        public String Imei { get; set; }
        public int StatusId { get; set; }
        public String SalesmanName { get; set; }
        public String Email { get; set; }
        public int SpvId { get; set; }
        public String SPV { get; set; }
        public int SalesmanLevelId { get; set; }
        public int AreaId { get; set; }
        public int statusId { get; set; }
        public int recId { get; set; }
        public String SignatureName { get; set; }
        public bool AllowApproval { get; set; }
        

        public mSales salesAtasan { get; set; }

    }
}