using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class putSalesBindingModel
    {
        [Required]
      //  [Display(Name = "uname")]
        public string uname { get; set; }

        [Required]
       // [StringLength(100, ErrorMessage = "The {0} must be at least {2} characters long.", MinimumLength = 6)]
       // [DataType(DataType.Password)]
       // [Display(Name = "pass")]
        public string pass { get; set; }

    }

    public class putCustomerUpdateStatusBindingModel
    {
        [Required]
        public string salesId { get; set; }

        [Required]
        public string customerId { get; set; }

    }
    public class updateSalesBindingModel
    {
        [Required]
        //  [Display(Name = "uname")]
        public string salesid { get; set; }
        [Required]
        public string fcmid { get; set; }
        [Required]
        public string imei { get; set; }
        [Required]
        public string useremail { get; set; }
        [Required]
        public string recId { get; set; }

    }
}