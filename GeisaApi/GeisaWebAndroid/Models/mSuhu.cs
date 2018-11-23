using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSuhu
    {
        public String machineId { get; set; }
        public String dateTaken { get; set; }
        public float temperaturValue { get; set; } 
        public bool statusSend { get; set; }
    }
}