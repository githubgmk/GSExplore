using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mChannel
    {
        public int ChannelId { get; set; }
        public String ChannelName { get; set; }
        public String Email { get; set; }
        public String Signature { get; set; }
        public String Pic { get; set; }
        public int StatusId { get; set; }
    }
}