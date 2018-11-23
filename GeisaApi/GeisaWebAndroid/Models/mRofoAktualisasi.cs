using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mRofoAktualisasi
    {
        public String RofoAktualisasiId { get; set; }
        public int Year { get; set; }
        public int Month { get; set; }
        public String MonthName { get; set; }
        public int SalesmanId { get; set; }
        public double ValueRofo { get; set; }
        public double ValueRofoDraft { get; set; }
        public double ValueSales { get; set; }
        public double ValueTarget { get; set; }
        public String UpdatedDate { get; set; }
    }
}