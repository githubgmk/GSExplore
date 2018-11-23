using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mProduct
    {
        public int ProductId { get; set; }
        public String ProductName { get; set; }
        public String ProductSimpleDescription { get; set; }
        public String Foto { get; set; }
        public int RecIdProductMap { get; set; }
        public String ProductCode { get; set; }
        public int DistId { get; set; }
        public String ProductNameDist { get; set; }
        public int StatusId { get; set; }
    }
}