using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mPoLineOther
    {
        public String RecIdTab { get; set; }
        public String PoId { get; set; }
        public String ProductCode { get; set; }
        public String ProductName { get; set; }
        public double Qty { get; set; }
        public String Unit { get; set; }
        public int StatusSend { get; set; }
        public bool Selected { get; set; }
    }
}