using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class WfTrans
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
        public String FullName { get; set; }
        public String Email { get; set; }
        public String SalesmanId { get; set; }

    }
}