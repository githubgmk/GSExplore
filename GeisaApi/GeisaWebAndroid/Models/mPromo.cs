using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mPromo
    {
        public int PromoId { get; set; }
        public String PromoName{ get; set; }
        public String StartDate{ get; set; }
        public String EndDate{ get; set; }
        public int DistId{ get; set; }
        public String DistName{ get; set; }
        public int CustRelation{ get; set; }
        public String Cust{ get; set; }
        public String CustName{ get; set; }
        public int ProductId{ get; set; }
        public String ProductName{ get; set; }
        public String UnitId{ get; set; }
        public int MinQty{ get; set; }
        public int MinValue{ get; set; }
        public int MultiplyQty{ get; set; }
        public int ProductIdBonus{ get; set; }
        public String ProductBonusName{ get; set; }
        public String UnitIdBonus{ get; set; }
        public int QtyBonus{ get; set; }
        public String Notes{ get; set; }
        public String CreatedDate{ get; set; }
        public String CreatedBy{ get; set; }
        public String ModifiedDate{ get; set; }
        public String ModifiedBy{ get; set; }
    }
}