using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mTracking
    {
        public String TrackingId { get; set; }
        public String SalesmanId { get; set; }
        public String TrackingType { get; set; }
        public String TrackingDate { get; set; }
        public String TrackingTime { get; set; }
        public String TrackingLat { get; set; }
        public String TrackingLot { get; set; }
        public String TrackingRef { get; set; }
        public String TrackingStatus { get; set; }
        public int StatusSend { get; set; }
        public String CreateDate { get; set; }
        public String InfoDevice { get; set; }
    }
}