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

    public class AddCustomerBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public int GroupCust { get; set; }

        [Required]
        public mCustomer customer { get; set; }
    }

    public class putCustomerUpdateStatusBindingModel
    {
        [Required]
        public string salesId { get; set; }

        [Required]
        public List<custBindingModel> customerlist { get; set; }
    }

    public class custBindingModel
    {

        [Required]
        public string customerId { get; set; }

    }


    public class updateSalesLoginBindingModel
    {
        [Required]
        public string SalesmanId { get; set; }
        [Required]
        public string Fcmid { get; set; }
        [Required]
        public string Imei { get; set; }
        [Required]
        public string UserName { get; set; }
        [Required]
        public string UserPass { get; set; }
        [Required]
        public string OldPass { get; set; }


    }

    public class trackingBindingModel
    {
        [Required]
        public string TrackingId { get; set; }
        [Required]
        public string SalesmanId { get; set; }
        [Required]
        public string TrackingType { get; set; }
        [Required]
        public string TrackingDate { get; set; }
        [Required]
        public string TrackingTime { get; set; }
        [Required]
        public string TrackingLat { get; set; }
        [Required]
        public string TrackingLot { get; set; }
        [Required]
        public string TrackingRef { get; set; }
        [Required]
        public string TrackingStatus { get; set; }
        [Required]
        public string CreateDate { get; set; }
        [Required]
        public string DeviceInfo { get; set; }

    }
}