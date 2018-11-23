using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mPoLine
    {
        public String RecIdTab { get; set; }
        public String PoId { get; set; }
        public int ProductId { get; set; }
        public double Qty { get; set; }
        public double UnitPrice { get; set; }
        public String UnitId { get; set; }
        public int PriceId { get; set; }
        public double PriceList { get; set; }

        public int DiscId { get; set; }
        public double Disc1 { get; set; }
        public double Disc2 { get; set; }
        public double Disc3 { get; set; }
        public double DiscRp { get; set; }
        public double Point { get; set; }
        public bool IncludePPN { get; set; }
        public String CreatedDate { get; set; }
        public String ConfirmDate { get; set; }

        public int StatusSend { get; set; }
        public bool Selected { get; set; }
        public String ProductName { get; set; }
        public String ProductCode { get; set; }
        public String RefRecIdTab { get; set; }
        public int PromoId { get; set; }
        public bool isDraft { get; set; }
        public mPoLine poLineBonus { get; set; }

    }
}