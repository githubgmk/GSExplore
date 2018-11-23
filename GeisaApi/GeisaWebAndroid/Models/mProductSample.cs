using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mProductSample
    {

        public String SampleProdukId { get; set; }
        public String SampleId { get; set; }
        public int ProductId { get; set; }
        public String Kemasan { get; set; }
        public double Qty { get; set; }
        public String Note { get; set; }
        public String TypeRequest { get; set; }
        public bool Selected { get; set; }
        public String ProductName { get; set; }
        public String ProductCode { get; set; }
        public String CreatedDate { get; set; }
    }
}