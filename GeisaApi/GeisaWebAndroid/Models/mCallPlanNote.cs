using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mCallPlanNote
    {
        public String CallPlanNoteId { get; set; }
        public int BiCsTypeId { get; set; }
        public String BiCsTypeName { get; set; }
        public String CallPlanId { get; set; }
        public int CustId { get; set; }
        public String Notes1 { get; set; }
        public String Notes2 { get; set; }
        public String Notes3 { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }
    }
}