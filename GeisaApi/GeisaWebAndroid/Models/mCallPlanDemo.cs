using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mCallPlanDemo
    {
        public String DemoId { get; set; }
        public String CallPlanId { get; set; }
        public int CustId { get; set; }
        public String DemoTitle { get; set; }
        public String DemoDescription { get; set; }
        public int DemoPeserta { get; set; }
        public int DemoStatusId { get; set; }//status draft 0 ,inreview 1, open 2,close 3
        public String DemoStatusName { get; set; }
        public String DemoDate { get; set; }
        public String DemoResponse { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public String ResponseDate { get; set; }
        public String ResponseBy { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }
    }
}