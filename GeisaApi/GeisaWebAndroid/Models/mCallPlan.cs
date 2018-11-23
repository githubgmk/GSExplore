using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Modelsg
{
    public class mCallPlan
    {
        public String CallPlanId { get; set; }
        public String CallPlanDate { get; set; }
        public String CallPlanTypeId { get; set; }
        public String CallPlanTypeName { get; set; }
        public int SalesmanId { get; set; }
        public int CustId { get; set; }
        public int CallPlanStatusId { get; set; }//status draft 0 ,inreview 1, open 2,close 3
        public String CallPlanStatusName { get; set; }
        public String Notes { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }//sales id
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }
    }
}