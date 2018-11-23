using GeisaWebAndroid.Modelsg;
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

    public class putStringListBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public List<stringBindingModel> StringList { get; set; }
    }

    public class putCallPlanBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public List<mCallPlan> callplanlist { get; set; }
    }

    public class putCallPlanNoteBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public List<mCallPlanNote> callplannotelist { get; set; }
    }
    public class putCallPlanDemoBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public List<mCallPlanDemo> callplandemolist { get; set; }
    }
    public class putCallPlanComplainBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public List<mComplain> callplancomplainlist { get; set; }
    }
    public class putCallPlanSampleBindingModel
    {
        [Required]
        public string salesId { get; set; }
        [Required]
        public List<mSample> callplansamplelist { get; set; }
    }

    public class custBindingModel
    {

        [Required]
        public string customerId { get; set; }

    }

    public class stringBindingModel
    {
        [Required]
        public string id { get; set; }
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