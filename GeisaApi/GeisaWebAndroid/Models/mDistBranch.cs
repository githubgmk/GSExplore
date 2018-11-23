using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mDistBranch
    {
        public int DistBranchId { get; set; }
        public String DistBranchName { get; set; }
        public String AreaCode { get; set; }
        public String AreaName { get; set; }
        public String Address { get; set; }
        public String Pic { get; set; }
        public String Telp { get; set; }
        public String Email { get; set; }
        public int DistId { get; set; }
        public int AreaId { get; set; }
        public int StatusId { get; set; }
        public String TemplateEmailRegularAttach { set; get; }
        public String TemplateEmailKhususAttach { set; get; }
    }
}