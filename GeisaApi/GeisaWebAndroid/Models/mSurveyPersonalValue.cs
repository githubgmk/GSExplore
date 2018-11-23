using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSurveyPersonalValue
    {
        public int PersonalId { get; set; }
        public String Title { get; set; }
        public bool Required { get; set; }
        public String Type { get; set; }
        public String Value { get; set; }
    }
}