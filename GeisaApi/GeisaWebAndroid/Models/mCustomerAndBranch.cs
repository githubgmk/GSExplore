using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mCustomerAndBranch
    {
        public int CustomerDistBranchId { set; get; }
        public int CustId { set; get; }
        public String CustCode { set; get; }
        public String PriceGroupId { set; get; }
        public String PriceGroupName { set; get; }
        public String DiscGroupId { set; get; }
        public String DiscGroupName { set; get; }
        public String CustIdAndro { set; get; }
        public int DistBranchId { set; get; }

        public String DistBranchName { set; get; }
        public String AreaCode { set; get; }
        public String AreaName { set; get; }
        public String Address { set; get; }
        public String Pic { set; get; }
        public String Telp { set; get; }
        public String Email { set; get; }
        public string EmailInternal { get; set; }
        public int DistId { set; get; }
        public int AreaId { set; get; }
        public int StatusId { set; get; }
        public String TemplateEmailRegularAttach { set; get; }
        public String TemplateEmailKhususAttach { set; get; }

    }
}