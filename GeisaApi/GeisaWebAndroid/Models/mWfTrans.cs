using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mWfTrans
    {
        public int RecId { get; set; }
        public int WfTransId { get; set; }
        public String RefId { get; set; }
        public int Index { get; set; }
        public String UserId { get; set; }
        public int StatusActionWf { get; set; }
        public String ActionDate { get; set; }
        public String Notes { get; set; }
        public String CreatedDate { get; set; }
        public int SalesmanId { get; set; }
        public int StatusId { get; set; }
        public String SalesmanName { get; set; }
        public String Email { get; set; }
        public int SpvId { get; set; }
        public int SalesmanLevelId { get; set; }
        public int AreaId { get; set; }
        public String SignatureName { get; set; }
        public bool AllowApproval { get; set; }
    }
}