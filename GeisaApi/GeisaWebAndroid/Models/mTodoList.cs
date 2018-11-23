using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mTodoList
    {
        public int RecId { get; set; }
        public int CustId { get; set; }
        public String Reference { get; set; }
        public String CustName { get; set; }
        public String Category { get; set; }
        public String Title { get; set; }
        public String DocDate { get; set; }
        public String Detail { get; set; }
        public int StatusId { get; set; }
        public String Status { get; set; }
        public String StatusDetail { get; set; }
        public String CreatedDate { get; set; }
        public bool StatusRead { get; set; }
    }
}