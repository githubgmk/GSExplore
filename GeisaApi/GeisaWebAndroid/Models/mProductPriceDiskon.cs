using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mProductPriceDiskon
    {
        public int RecId { get; set; }
        public int Relation { get; set; }
        public int DistId { get; set; }
        public int PriceType { get; set; }
        public String PriceDiscGroupId { get; set; }
        public String ProductNameDist { get; set; }
        public String ProductCode { get; set; }
        public String UnitId { get; set; }
        public double Price { get; set; }
        public double Disc1 { get; set; }
        public double Disc2 { get; set; }
        public String StartDate { get; set; }
        public String EndDate { get; set; }
    }
}